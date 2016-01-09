package achat.popup;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import jdbc.DatabaseConnection;

import com.toedter.calendar.JDateChooser;

import achat.CommandesFournisseur;
import achat.LignesCommande;
import achat.UneditableTableModel;

public class PopupCommande extends JDialog {
	
	private CommandesFournisseur commande;
	private ArrayList<LignesCommande> listeLignesCommande = new ArrayList<LignesCommande>();
	private Object[][] tabLignesCo;
	private Object[] titres = {"Code","Description","Cat�gorie","Prix HT","Qt�","Total HT"};
	
	private JLabel lblFournisseurCode, lblMontantTotalHt;
	private JButton btnCalculerTotal;
	
	//On cr�er la JTable et son mod�le
	private static JTable tableau = new JTable(new DefaultTableModel());
	private static UneditableTableModel modele = new UneditableTableModel(0,5);
	private JScrollPane scrollPane;
	
	
	/**
	 * Constructeur vide. Cr�er une nouvelle commande.
	 */
	public PopupCommande(){
		this.initFenetre();
		this.initElements();
		this.initEcouteurs();
	}
	
	
	/**
	 * Constructeur avec en param�tre une commande de type CommandesFournisseur. Modifie une commande existante.
	 * @param cmd, une commande de type CommandesFournisseur. Modifie la commande pass�e en param�tre.
	 */
	public PopupCommande(CommandesFournisseur cmd){
		this.commande = cmd;
		this.initFenetre();
		this.initElements();
		this.initEcouteurs();
		this.getProduitsCommande();
	}
	
	
	/**
	 * M�thode qui initie la fenetre popup.
	 */
	private void initFenetre(){
		
		//On donne un titre selon la provenance du clic (si cmd en paramètre alors modification)
		if(this.commande == null){
			this.setTitle("Nouvelle commande d'achat");
		}
		else {
			this.setTitle("Modification de la commande d'achat n�"+this.commande.getRefCommande()+" du "+this.commande.getDate()+" ("+this.commande.getNomFourniseur()+")");
		}
		
		this.setSize(850, 650);
		Dimension dim = new Dimension(850,650);
		this.setMinimumSize(dim);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	/**
	 * M�thode qui initialise l'ensemble de tous les panels et composants de la fenetre.
	 */
	private void initElements(){
		
		//On cr�er les diff�rents panels du haut
		this.setLayout(new GridLayout(2, 1));//On d�fini la popup avec un layout en grid

		JPanel panelGrille = new JPanel(new BorderLayout());//Panel du haut
		JPanel panelGrilleNord = new JPanel();
		JPanel panelGrilleCentre = new JPanel();
		JPanel panelGrilleSud = new JPanel();
		
		//On cr�er et ajoute les composants du panelGrilleNord
		JLabel lblFournisseur = new JLabel("Fournisseur (code) : ");
		this.lblFournisseurCode = new JLabel("Aucun fournisseur s�lectionn�");
		JButton btnRechercher = new JButton("Rechercher");
		JLabel lblDate = new JLabel("Date : ");
		JDateChooser jdcDate = new JDateChooser();
		
		panelGrilleNord.add(lblFournisseur);
		panelGrilleNord.add(lblFournisseurCode);
		panelGrilleNord.add(btnRechercher);
		panelGrilleNord.add(lblDate);
		panelGrilleNord.add(jdcDate);
		
		//On cr�er et on ajoute les composants du panelGrilleCentre
		modele.setDataVector(tabLignesCo,titres);
		tableau = new JTable(modele);
		tableau.setAutoCreateRowSorter(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setPreferredScrollableViewportSize(new Dimension(800, 200));
		this.scrollPane = new JScrollPane(tableau);
		panelGrilleCentre.add(scrollPane);
		
		//On cr�er et on ajout les composants du panelGrilleSud
		JButton btnAjouter = new JButton("Ajouter");
		JButton btnModifier = new JButton("Modifier");
		JButton btnSupprimer = new JButton("Supprimer");
		this.btnCalculerTotal = new JButton("Calculer Total");
		
		panelGrilleSud.add(btnAjouter);
		panelGrilleSud.add(btnModifier);
		panelGrilleSud.add(btnSupprimer);
		panelGrilleSud.add(btnCalculerTotal);
		
		
		//On cr�er les diff�rents panels du bas
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
		Choice chTauxTva = new Choice();
		chTauxTva.add("5,5%");
		chTauxTva.add("10%");
		chTauxTva.add("20%");
		JLabel lblRemise = new JLabel("Remise : "); 
		JLabel lblPrCent = new JLabel("%");
		JTextField txtRemise = new JTextField(10);
		JLabel lblDateLivr = new JLabel("Date de livraison : ");
		JDateChooser jdcDateLivr = new JDateChooser();
		JLabel lblPaiement = new JLabel("Type de paiement : ");
		Choice chPaiement = new Choice();
		chPaiement.add("Ch�que");
		chPaiement.add("Esp�ces");
		chPaiement.add("Virement");
		
		gauche1.add(lblTauxTva);
		gauche1.add(chTauxTva);
		gauche2.add(lblRemise);
		gauche2.add(txtRemise);
		gauche2.add(lblPrCent);
		gauche3.add(lblDateLivr);
		gauche3.add(jdcDateLivr);
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
		JLabel lblMontantRemise = new JLabel();
		JLabel lblTva = new JLabel("TVA : ");
		JLabel lblMontantTva = new JLabel();
		JLabel lblTotalTtc = new JLabel("Total TTC : ");
		JLabel lblMontantTtc = new JLabel();
		
		
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
		
		
		//On cr�er et ajoute les composants du panelParametrageSud
		JButton btnValider = new JButton("Valider");
		JButton btnAnnuler = new JButton("Annuler");
		panelParametrageSud.add(btnValider);
		panelParametrageSud.add(btnAnnuler);
		
		//On ajoute les composants � leur panel respectifs
		panelGrille.add(panelGrilleNord, BorderLayout.NORTH);
		panelGrille.add(panelGrilleCentre, BorderLayout.CENTER);
		panelGrille.add(panelGrilleSud, BorderLayout.SOUTH);
		
		panelParametrage.add(panelParametrageCentre);
		panelParametrage.add(panelParametrageSud);
		
		//On ajoute les panels � la fen�tre
		this.add(panelGrille);
		this.add(panelParametrage);
	}
	
	
	/**
	 * M�thode qui remplit le tableau avec les donn�es pr�sentes dans l'arrayList.
	 */
	private void maj(){
		int nouvelleLongueur = this.listeLignesCommande.size();
		this.tabLignesCo = new Object[nouvelleLongueur][6];
		
		for(LignesCommande lc : this.listeLignesCommande){
			this.tabLignesCo[listeLignesCommande.indexOf(lc)][0] = lc.getRefProduit();
			this.tabLignesCo[listeLignesCommande.indexOf(lc)][1] = lc.getNomproduit();
			this.tabLignesCo[listeLignesCommande.indexOf(lc)][2] = lc.getCategorieProduit();
			this.tabLignesCo[listeLignesCommande.indexOf(lc)][3] = lc.getpHT()+"�";
			this.tabLignesCo[listeLignesCommande.indexOf(lc)][4] = lc.getQte();
			this.tabLignesCo[listeLignesCommande.indexOf(lc)][5] = lc.getTotal()+"�";
		}
		
		modele.setDataVector(tabLignesCo, titres);
	}
	
	
	/**
	 * M�thode qui initialise les �couteurs.
	 */
	private void initEcouteurs(){
		this.btnCalculerTotal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupCommande.this.calculerTotal();
			}
		});
	}
	
	
	/**
	 * M�thode qui r�cup�re l'ensemble des produits qui sont dans la commande.
	 */
	private void getProduitsCommande(){
		this.lblFournisseurCode.setText(this.commande.getNomFourniseur()+" ("+this.commande.getRefFournisseur()+")");
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
				
				PopupCommande.this.listeLignesCommande.add(new LignesCommande(refProduit, nomProduit, categorieProduit, pHT, qte, total));
			}
			
			this.maj();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * M�thode qui calcule le total de la commande.
	 */
	private void calculerTotal(){
		float total = 0;
		for(LignesCommande lc : this.listeLignesCommande){
			total += lc.getTotal();
		}
		
		this.lblMontantTotalHt.setText(total+" �");
	}
}
