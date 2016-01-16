package achat.popup;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;

import jdbc.DatabaseConnection;

import com.toedter.calendar.JDateChooser;

import achat.CommandesFournisseur;
import achat.Fournisseur;
import achat.LignesCommande;
import achat.PanelCommande;
import achat.UneditableTableModel;

public class PopupCommande extends JDialog {
	
	private static Fournisseur fournisseur = new Fournisseur();
	private CommandesFournisseur commande;
	private static ArrayList<LignesCommande> listeLignesCommande = new ArrayList<LignesCommande>();
	private static Object[][] tabLignesCo;
	private static Object[] titres = {"Code","Description","Catégorie","Prix HT","Qté","Total HT"};
	
	private JLabel lblMontantTotalHt, lblMontantRemise, lblMontantTva, lblMontantTtc;
	private static JLabel lblFournisseurCode;
	private JSpinner txtRemise;
	private JButton btnCalculerTotal, btnValider, btnAnnuler, btnRechercher, btnAjouter, btnModifier, btnSupprimer;
	private JDateChooser jdcDateLivr, jdcDate;
	private Choice chTauxTva, chPaiement;
	private PopupCommandeSelectFournisseur fenetreSelectFn;
		
	//On créer la JTable et son modèle
	private static UneditableTableModel modele = new UneditableTableModel(0,5);
	private static JTable tableau = new JTable(modele);
	private JScrollPane scrollPane;
	
	
	/**
	 * Constructeur vide. Créer une nouvelle commande.
	 */
	public PopupCommande(){
		listeLignesCommande.clear();
		maj();//On mets à jour pour éviter que la table ne se remplisse avec les produits de la dernière commande
		this.initFenetre();
		this.initElements();
		this.initEcouteurs();
		
		//On grise les boutons pour ajouter/modifier/supprimer un produits
		this.btnAjouter.setEnabled(false);
		this.btnModifier.setEnabled(false);
		this.btnSupprimer.setEnabled(false);
	}
	
	
	/**
	 * Constructeur avec en paramètre une commande de type CommandesFournisseur. Modifie une commande existante.
	 * @param cmd, une commande de type CommandesFournisseur. Modifie la commande passée en paramètre.
	 */
	public PopupCommande(CommandesFournisseur cmd){
		listeLignesCommande.clear();
		this.commande = cmd;
		this.initFenetre();
		this.initElements();
		this.initEcouteurs();
		this.getProduitsCommande();
		this.preRemplir();
	}
	
	
	/**
	 * Méthode qui initie la fenetre popup.
	 */
	private void initFenetre(){
		
		//On donne un titre selon la provenance du clic (si cmd en paramètre alors modification)
		if(this.commande == null){
			this.setTitle("Nouvelle commande d'achat");
		}
		else {
			this.setTitle("Modification de la commande d'achat n°"+this.commande.getRefCommande()+" du "+this.commande.getDate());
		}
		
		this.setSize(850, 650);
		Dimension dim = new Dimension(850,650);
		this.setMinimumSize(dim);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	/**
	 * Méthode qui initialise l'ensemble de tous les panels et composants de la fenetre.
	 */
	private void initElements(){
		
		//On défini le fournisseur s'il est présent dans la commande si non vide
		if(this.commande == null || this.commande.getRefFournisseur() == null){
			fournisseur = new Fournisseur();
		}
		else{
			fournisseur = new Fournisseur(this.commande.getRefFournisseur(), this.commande.getNomFourniseur());
		}
		
		//On créer les différents panels du haut
		this.setLayout(new GridLayout(2, 1));//On défini la popup avec un layout en grid

		JPanel panelGrille = new JPanel(new BorderLayout());//Panel du haut
		JPanel panelGrilleNord = new JPanel();
		JPanel panelGrilleCentre = new JPanel();
		JPanel panelGrilleSud = new JPanel();
		
		//On créer et ajoute les composants du panelGrilleNord
		JLabel lblFournisseur = new JLabel("Fournisseur (code) : ");
		lblFournisseurCode = new JLabel("Aucun fournisseur sélectionné");
		this.btnRechercher = new JButton("Rechercher");
		JLabel lblDate = new JLabel("Date : ");
		this.jdcDate = new JDateChooser();
		this.jdcDate.setEnabled(false);
		
		panelGrilleNord.add(lblFournisseur);
		panelGrilleNord.add(lblFournisseurCode);
		panelGrilleNord.add(btnRechercher);
		panelGrilleNord.add(lblDate);
		panelGrilleNord.add(jdcDate);
		
		//On créer et on ajoute les composants du panelGrilleCentre
		modele.setDataVector(tabLignesCo,titres);
		tableau = new JTable(modele);
		tableau.setAutoCreateRowSorter(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setPreferredScrollableViewportSize(new Dimension(800, 200));
		this.scrollPane = new JScrollPane(tableau);
		panelGrilleCentre.add(scrollPane);
		
		//On créer et on ajout les composants du panelGrilleSud
		this.btnAjouter = new JButton("Ajouter");
		this.btnModifier = new JButton("Modifier");
		this.btnSupprimer = new JButton("Supprimer");
		this.btnCalculerTotal = new JButton("Calculer Total");
		
		panelGrilleSud.add(this.btnAjouter);
		panelGrilleSud.add(this.btnModifier);
		panelGrilleSud.add(this.btnSupprimer);
		panelGrilleSud.add(this.btnCalculerTotal);
		
		
		//On créer les différents panels du bas
		JPanel panelParametrage = new JPanel(new GridLayout(2,1));//panel du bas
		JPanel panelParametrageCentre = new JPanel(new GridLayout(1,2));
		JPanel panelParametrageCentreGauche = new JPanel(new GridLayout(4,1));
		JPanel gauche1 = new JPanel();
		JPanel gauche2 = new JPanel();
		JPanel gauche3 = new JPanel();
		JPanel gauche4 = new JPanel();
		JPanel panelParametrageCentreDroite = new JPanel(new GridLayout(4,2));
		JPanel panelParametrageSud = new JPanel();
		
		//panelParametrageCentreGauche
		JLabel lblTauxTva = new JLabel("Taux TVA : "); 
		this.chTauxTva = new Choice();
		this.chTauxTva.add("0.0");
		this.chTauxTva.add("5.5");
		this.chTauxTva.add("10.0");
		this.chTauxTva.add("20.0");
		JLabel prctTva = new JLabel("%");
		JLabel lblRemise = new JLabel("Remise : "); 
		JLabel lblPrCent = new JLabel("%");
		this.txtRemise = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.5)); //valeur - min - max - saut;
		JLabel lblDateLivr = new JLabel("Date de livraison : ");
		this.jdcDateLivr = new JDateChooser();
		JLabel lblPaiement = new JLabel("Type de paiement : ");
		this.chPaiement = new Choice();
		chPaiement.add("Chèque");
		chPaiement.add("Espèces");
		chPaiement.add("Virement");
		
		gauche1.add(lblTauxTva);
		gauche1.add(chTauxTva);
		gauche1.add(prctTva);
		gauche2.add(lblRemise);
		gauche2.add(txtRemise);
		gauche2.add(lblPrCent);
		gauche3.add(lblDateLivr);
		gauche3.add(this.jdcDateLivr);
		gauche4.add(lblPaiement);
		gauche4.add(chPaiement);
		
		panelParametrageCentreGauche.add(gauche1);
		panelParametrageCentreGauche.add(gauche2);
		panelParametrageCentreGauche.add(gauche3);
		panelParametrageCentreGauche.add(gauche4);
		
		panelParametrageCentreGauche.setBorder(BorderFactory.createTitledBorder("Parametrage"));
		panelParametrageCentre.add(panelParametrageCentreGauche);
		
		
		//panelParametragecentreDroite
		JLabel lblTotalHt = new JLabel("Total HT : "); 
		this.lblMontantTotalHt = new JLabel();
		JLabel lblRemise2 = new JLabel("Remise : ");
		this.lblMontantRemise = new JLabel();
		JLabel lblTva = new JLabel("TVA : ");
		this.lblMontantTva = new JLabel();
		JLabel lblTotalTtc = new JLabel("Total TTC : ");
		this.lblMontantTtc = new JLabel();
		
		
		panelParametrageCentreDroite.add(lblTotalHt);
		panelParametrageCentreDroite.add(lblMontantTotalHt);
		panelParametrageCentreDroite.add(lblRemise2);
		panelParametrageCentreDroite.add(lblMontantRemise);
		panelParametrageCentreDroite.add(lblTva);
		panelParametrageCentreDroite.add(lblMontantTva);
		panelParametrageCentreDroite.add(lblTotalTtc);
		panelParametrageCentreDroite.add(lblMontantTtc);
		
		panelParametrageCentreDroite.setBorder(BorderFactory.createTitledBorder("Total"));
		panelParametrageCentre.add(panelParametrageCentreDroite);
		
		
		//On créer et ajoute les composants du panelParametrageSud
		this.btnValider = new JButton("Valider");
		this.btnAnnuler = new JButton("Annuler");
		panelParametrageSud.add(btnValider);
		panelParametrageSud.add(btnAnnuler);
		
		//On ajoute les composants à  leur panel respectifs
		panelGrille.add(panelGrilleNord, BorderLayout.NORTH);
		panelGrille.add(panelGrilleCentre, BorderLayout.CENTER);
		panelGrille.add(panelGrilleSud, BorderLayout.SOUTH);
		
		panelParametrage.add(panelParametrageCentre);
		panelParametrage.add(panelParametrageSud);
		
		//On ajoute les panels à la fenêtre
		this.add(panelGrille);
		this.add(panelParametrage);
	}
	
	
	/**
	 * Méthode qui remplit le tableau avec les données présentes dans l'arrayList.
	 */
	public static void maj(){
		int nouvelleLongueur = listeLignesCommande.size();
		tabLignesCo = new Object[nouvelleLongueur][6];
		
		for(LignesCommande lc : listeLignesCommande){
			tabLignesCo[listeLignesCommande.indexOf(lc)][0] = lc.getRefProduit();
			tabLignesCo[listeLignesCommande.indexOf(lc)][1] = lc.getNomproduit();
			tabLignesCo[listeLignesCommande.indexOf(lc)][2] = lc.getCategorieProduit();
			tabLignesCo[listeLignesCommande.indexOf(lc)][3] = lc.getpHT()+"€";
			tabLignesCo[listeLignesCommande.indexOf(lc)][4] = lc.getQte();
			tabLignesCo[listeLignesCommande.indexOf(lc)][5] = lc.getTotal()+"€";
		}
		
		modele.setDataVector(tabLignesCo, titres);
	}
	
	
	/**
	 * Méthode qui initialise les écouteurs.
	 */
	private void initEcouteurs(){
		
		//Bouton pour calculer le total de la commande
		this.btnCalculerTotal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupCommande.this.calculerTotal();
			}
		});
		
		
		//Bouton Ajouter
		this.btnAjouter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupCommandeAjoutLigne(PopupCommande.this.commande.getRefCommande());
			}
		});
		
		
		//Bouton pour valider une commande
		this.btnValider.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupCommande.this.ajouterCommande(PopupCommande.this.commande);
			}
		});
		
		
		//Bouton pour fermer la page (annuler)
		this.btnAnnuler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		//Bouton de recherche de fournisseur
		this.btnRechercher.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupCommande.this.fenetreSelectFn = new PopupCommandeSelectFournisseur();
			}
		});
	}
	
	
	/**
	 * Méthode qui récupère l'ensemble des produits qui sont dans la commande.
	 */
	private void getProduitsCommande(){
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT * FROM LignesCommandeFournisseur lc JOIN Produits p ON p.codeproduit = lc.refProduit JOIN Categorie c ON c.codeCategorie = p.categorie WHERE refCommande = ?");
			pst.setString(1, PopupCommande.this.commande.getRefCommande());
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				String refProduit = rs.getString("refProduit");
				String nomProduit = rs.getString("description");
				String categorieProduit = rs.getString("nomCategorie");
				double pHT = rs.getDouble("prixAchat");
				int qte = rs.getInt("quantite");
				double total = pHT*qte;
				
				PopupCommande.listeLignesCommande.add(new LignesCommande(refProduit, nomProduit, categorieProduit, pHT, qte, total));
			}
			
			maj();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Méthode qui remplit les différents label avec les caractéristiques de la commande.
	 */
	private void preRemplir(){
		
		//On remplit le nom du fournisseur
		if(fournisseur.getRef().equals("")){
			lblFournisseurCode.setText("Sélectionner un founisseur");
		}
		else{
			lblFournisseurCode.setText(fournisseur.getNom()+" ("+fournisseur.getRef()+")");
		}
		
		//On remplit la date
		this.jdcDate.setDate(this.commande.getDate());
		
		//On selectionne de base le montant de TVA approprié à la commande (si non 0%)
		if(this.commande.getTauxTva() != null){
			this.chTauxTva.select(String.valueOf(this.commande.getTauxTva()));
		}
		
		//On selectionne de base le type de paiement
		if(this.commande.getTypePaiement() != null){
			this.chPaiement.select(this.commande.getTypePaiement());
		}
		
		//On affiche le taux remise de base
		if(this.commande.getRemise() != null){
			this.txtRemise.setValue((Double) this.commande.getRemise());
		}
		
		//On remplit la date de livraison
		if(this.commande.getDateLivr() != null){
			this.jdcDateLivr.setDate(this.commande.getDateLivr());
		}
		
		//On effectue les calculs
		this.calculerTotal();
	}
	
	
	/**
	 * Méthode qui calcule le total de la commande.
	 */
	private void calculerTotal(){
		
		//On calcule le total
		double total = 0;
		for(LignesCommande lc : listeLignesCommande){
			total += lc.getTotal();
		}
		this.lblMontantTotalHt.setText(total+"€");
		
		//On calcule la remise par rapport au total

		 Double remise = (Double) this.txtRemise.getValue()/100;
		 remise *= total;
		
		this.lblMontantRemise.setText("-"+String.valueOf(remise)+"€");
		
		//On calcule le montant de la TVA par rapport au taux
		double montantTva = 0;
		montantTva = (Double.valueOf(this.chTauxTva.getSelectedItem())/100)*(total-remise);
		this.lblMontantTva.setText(String.valueOf(montantTva)+"€");
		
		this.lblMontantTtc.setText(String.valueOf(total-remise+montantTva)+"€");
	}
	
	
	/**
	 * Méthode qui ajoute dans la base de donnée une nouvelle commande (vide).
	 */
	private void ajouterCommande(CommandesFournisseur c){
		
		//On vérifie si la commande est dans l'arraylist des commandes
		if(PanelCommande.getListeCommande().contains(c)){
			
			if(this.commande.getTauxTva() == null){
				this.chTauxTva.select("0.0");//TVA à 0 si vide
			}
			
			if(this.jdcDateLivr.getDate() == null || this.jdcDateLivr.getDate().before(this.jdcDate.getDate())){
				this.jdcDateLivr.setDate(this.commande.getDate());//Date de livraison = date de création si vide ou antérieur à la création
			}
			
			this.calculerTotal();//On calcule el total de la commande
			
			try {
				Connection cn = DatabaseConnection.getCon();
				PreparedStatement pst = cn.prepareStatement("UPDATE CommandesFournisseur SET dateCommande = ?, refFournisseur = ?, tauxTVA = ?, remise = ?, dateLivr = ?, typepaiement = ? WHERE refCommande = ?");
				pst.setDate(1, new Date(this.jdcDate.getDate().getTime()));
				pst.setString(2, fournisseur.getRef());
				pst.setDouble(3, Double.valueOf(this.chTauxTva.getSelectedItem()));
				pst.setDouble(4, (Double) this.txtRemise.getValue());
				//pst.setDouble(4, 14.4);
				pst.setDate(5, new Date(this.jdcDateLivr.getDate().getTime()));
				pst.setString(6, this.chPaiement.getSelectedItem());
				pst.setString(7, c.getRefCommande());
				pst.executeQuery();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Si non on la créer
		else {
			
			this.calculerTotal();//On calcule el total de la commande
			
			try {
				Connection cn = DatabaseConnection.getCon();
				PreparedStatement pst = cn.prepareStatement("INSERT INTO CommandesFournisseur(refCommande, dateCommande, refFournisseur, etatCommande, tauxTVA, remise, dateLivr, typepaiement) VALUES(seqRefCommande.NEXTVAL,CURRENT_DATE,?,?,?,?,?,?)");
				pst.setString(1, fournisseur.getRef());
				pst.setString(2, "En cours");
				pst.setDouble(3, Double.valueOf(this.chTauxTva.getSelectedItem()));
				pst.setDouble(4, (Double) this.txtRemise.getValue());
				
				if(this.jdcDateLivr.getDate() == null){
					Calendar cal = Calendar.getInstance(); 
					Date date = new Date(cal.getTimeInMillis());
					pst.setDate(5, date);
				}
				else{
					pst.setDate(5, new Date(this.jdcDateLivr.getDate().getTime()));
				}
				
				pst.setString(6, this.chPaiement.getSelectedItem());
				pst.executeQuery();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		PanelCommande.getCommande();
		PanelCommande.maj();
		PanelCommande.setBtn(false);
		setFournisseur(new Fournisseur());
		dispose();
	}
	
	
	/**
	 * Méthode qui change le fournisseur avec celui sélectionné dans la liste.
	 * @param f, le Fournisseur à changer.
	 */
	public static void setFournisseur(Fournisseur f){
		lblFournisseurCode.setText(f.getNom()+" ("+f.getRef()+")");
		fournisseur = f;
	}
	
	
	/**
	 * Métode qui rajoute à la commande un produit avec une quantité.
	 * @param lc, une ligne de commande à ajouter à la ommande.
	 */
	public static void addArrayListLigneCommande(LignesCommande lc){
		listeLignesCommande.add(lc);
	}
}
