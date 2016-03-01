package vente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import achat.modeles.CommandesFournisseur;
import achat.modeles.Fournisseur;
import achat.modeles.UneditableTableModel;
import achat.vues.PanelCommande;
import achat.vues.popup.popCommande.PopupCommande;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;
import vente.model.Commande;
import vente.model.LignesCommande;

public class PanelCommandes extends JPanel{

	public static ArrayList<Commande> listeCommandes = new ArrayList<Commande>();
	private String commandeChoisit;
	private static Object[][] tabCo;
	private static Object[] titres = {"N� Commande","Acheteur","Date", "Montant", "Etat"};

	private static JTable tableau = new JTable(new DefaultTableModel());
	private static UneditableTableModel modele = new UneditableTableModel(0,5);
	private static JScrollPane scrollPane  = new JScrollPane(tableau);

	private static JButton btnNouveau, btnModifier, btnSupprimer;
	private static JTextField txtRechercheCommande, txtRechercheClient, txtRechercheMontant;

	private int index;

	//private Commande commande;
	private static JDateChooser jdcDate;
	private static String dateRecherche = "";


	public PanelCommandes(FenetrePrincipale f){

		//getCommande();
		//remplirTableau();

		this.initElements();
		initEcouteurs();
	}

	public static ArrayList<Commande> getListeCommande(){
		return listeCommandes;
	}

	public static void getCommande() {
		listeCommandes.clear();//On efface l'arraylist pour éviter d'ajouter une deuxième fois les éléments
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT co.idCommande, co.dateCommande,co.etatCommande, co.tauxTVA, co.remise, co.typePaiement, cli.idClient, cli.nomClient, SUM(p.prixVente*lc.quantite) AS montantTotal FROM vente_Commande co LEFT JOIN vente_clients cli ON cli.idClient = co.idClient LEFT JOIN vente_LigneCommande lc ON lc.idCommande = co.idCommande LEFT JOIN Produit p ON p.code = lc.codeProduit GROUP BY co.idCommande, co.dateCommande,co.etatCommande, co.tauxTVA, co.remise, co.typePaiement, cli.idClient, cli.nomCLient ORDER BY co.dateCommande DESC");
			ResultSet rs = pst.executeQuery();

			while(rs.next()){
				String refCommande, refFournisseur , nomFournisseur, montantTotal, etatCommande, typePaiement;
				float tauxTva, remise;
				Date date;

				refCommande = rs.getString("idCommande");
				date = rs.getDate("dateCommande");
				refFournisseur = rs.getString("idClient");
				nomFournisseur = rs.getString("nomClient");
				etatCommande = rs.getString("etatCommande");
				tauxTva = rs.getFloat("tauxTva");
				remise = rs.getFloat("remise");
				typePaiement = rs.getString("typePaiement");


				//Montant total non vide
				if (rs.getString("montantTotal") == null){
					montantTotal = "Pas de produits";
				}
				else {
					montantTotal = rs.getString("montantTotal")+" €";
				}

				listeCommandes.add(new Commande(refCommande, refFournisseur, nomFournisseur, date, etatCommande,montantTotal, tauxTva,remise,typePaiement ));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void remplirTableau(){

		maj();

		tableau = new JTable(modele);
		tableau.setAutoCreateRowSorter(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setPreferredScrollableViewportSize(new Dimension(800, 224));

		scrollPane.setViewportView(tableau);
	}

	public static void maj(){
		int nbDeCo = listeCommandes.size();//On calcule la taille de l'arrylist pour cr�er un tableau ad�quat
		tabCo = new Object[nbDeCo][5];//On cr�er le tableau de la taille r�cup�r�e 

		//On remplit ce dernier avec les CommandesFournisseur r�cup�r�es
		for(Commande cf : listeCommandes){
			tabCo[listeCommandes.indexOf(cf)][0] = cf.getIdCommande();
			tabCo[listeCommandes.indexOf(cf)][1] = cf.getNomClient();
			tabCo[listeCommandes.indexOf(cf)][2] = cf.getDate();
			tabCo[listeCommandes.indexOf(cf)][3] = cf.getMontantTotal();
			tabCo[listeCommandes.indexOf(cf)][4] = cf.getEtatCommande();
		}
		modele.setDataVector(tabCo,titres);
	}


	private void initElements(){

		this.setLayout(new BorderLayout());

		JPanel panelRechercheNord = new JPanel();

		JPanel panelGrilleCentre = new JPanel(new GridLayout(2,1));
		JPanel panelGrille = new JPanel();
		JPanel panelBouton = new JPanel();
		jdcDate = new JDateChooser();

		JLabel lblRechercheCommande = new JLabel("N� Commande : ");
		txtRechercheCommande = new JTextField(10);
		JLabel lblRechercheFournisseur = new JLabel("Client : ");
		txtRechercheClient = new JTextField(10);
		JLabel lblRechercheDate = new JLabel("Date : ");
		JLabel lblRechercheMontant = new JLabel("Montant : ");
		txtRechercheMontant = new JTextField(5);
		
		btnNouveau= new JButton("Nouveau");
		btnModifier = new JButton("Modifier");
		btnSupprimer = new JButton("Supprimer");



		panelRechercheNord.add(lblRechercheCommande);
		panelRechercheNord.add(txtRechercheCommande);
		panelRechercheNord.add(lblRechercheFournisseur);
		panelRechercheNord.add(txtRechercheClient);
		panelRechercheNord.add(lblRechercheDate);
		panelRechercheNord.add(jdcDate);
		panelRechercheNord.add(lblRechercheMontant);
		panelRechercheNord.add(txtRechercheMontant);

		panelGrille.add(scrollPane);
		panelBouton.add(btnNouveau);
		panelBouton.add(btnModifier);
		panelBouton.add(btnSupprimer);

		panelGrilleCentre.add(panelGrille);
		panelGrilleCentre.add(panelBouton);

		this.add(panelRechercheNord, BorderLayout.NORTH);
		this.add(panelGrilleCentre, BorderLayout.CENTER);
		setBtn(true);
	}  
	/**
	 * Méthode qui initialise les écouteurs.
	 */
	public void initEcouteurs(){

		tableau.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent e) 
			{
				if (e.getValueIsAdjusting()) return;			        
				ListSelectionModel selection = (ListSelectionModel) e.getSource();
				index = selection.getMinSelectionIndex();
				commandeChoisit = String.valueOf(modele.getValueAt(tableau.convertRowIndexToModel(index), 0));
				//D�sactiver certains boutons si on ne selectionne aucune ligne

				if(!selection.isSelectionEmpty()){
					setBtn(true);
				}
				else{
					setBtn(false);
				}
			}
		});


		btnNouveau.addActionListener(new ActionListener() {

			private long myId;

			public void actionPerformed(ActionEvent e) {
				Connection cn = DatabaseConnection.getCon();
				try {
					String sqlIdentifier = "select sequence_commandeVente.NEXTVAL from dual";

					PreparedStatement pst = cn.prepareStatement(sqlIdentifier);
					synchronized( this ) {
						ResultSet rs = pst.executeQuery();
						if(rs.next())
							myId = rs.getLong(1);
					}
				}catch (SQLException e1) {
					e1.printStackTrace();
				}
				try{
					PreparedStatement pst = cn.prepareStatement("INSERT INTO vente_commande(idcommande) VALUES(?)");
					pst.setInt(1 , (int)myId);
					pst.executeQuery();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				new FenetreVente((int)myId);
			}
		});


		//Bouton "Modifier"
		btnModifier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Commande co = listeCommandes.get(tableau.getSelectedRow());
				int idCo = Integer.parseInt(co.getIdCommande());
				new FenetreVente(idCo);
			}
		});

		btnSupprimer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Commande co = listeCommandes.get(tableau.getSelectedRow());
				int idCo = Integer.parseInt(co.getIdCommande());
				supprimerCom(idCo);

				if (index != -1)
				{
					modele.removeRow(tableau.convertRowIndexToModel(index));

				}
				else{
					System.out.println("Suppression de la commande interrompu");
				}
			}
		});
		
		txtRechercheCommande.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				getCommandeRecherche();
				maj();
			}
		});
		
		//Ecouteur de la Date
				jdcDate.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if("date".equals(evt.getPropertyName())){
							//initDate();//On change la date si elle est vide
							getCommandeRecherche();
							maj();
						}
					}
				});
				
				//Txt nom du client
				txtRechercheClient.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
					//	PanelCommande.this.initDate();//On change la date si elle est vide
						getCommandeRecherche();
						maj();
					}
				});
				
				//Txt du montant de la commande
				txtRechercheMontant.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						//PanelCommande.this.initDate();//On change la date si elle est vide
						getCommandeRecherche();
						maj();
					}
				});
	}
	
	public static void getCommandeRecherche(){
		
		listeCommandes.clear();//On efface l'arraylist pour éviter d'ajouter une deuxième fois les éléments
		
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT c.idcommande, c.dateCommande, c.etatCommande, c.tauxTVA, c.remise, c.typePaiement, cl.IDCLIENT, cl.NOMCLIENT, SUM(p.prixAchat*lc.quantite) AS montantTotal FROM vente_Commande c LEFT JOIN VENTE_CLIENTS cl ON cl.IDCLIENT = c.IDCLIENT LEFT JOIN vente_ligneCommande lc ON lc.idcommande = c.idcommande LEFT JOIN Produit p ON p.code = lc.codeProduit WHERE UPPER(c.idcommande) LIKE UPPER(?) AND UPPER(cl.NOMCLIENT) LIKE UPPER(?) AND c.dateCommande LIKE ? GROUP BY c.idcommande, c.dateCommande, c.etatCommande, c.tauxTVA, c.remise, c.typePaiement, cl.IDCLIENT, cl.NOMCLIENT HAVING COALESCE(SUM(p.prixAchat*lc.quantite),0) LIKE ? ORDER BY c.dateCommande DESC");
			pst.setString(1, "%"+ txtRechercheCommande.getText()+"%");
			pst.setString(2, "%"+txtRechercheClient.getText()+"%");
			pst.setString(3, "%"+dateRecherche+"%");
			pst.setString(4, "%"+txtRechercheMontant.getText()+"%");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				String refCommande, refFournisseur, nomFournisseur, montantTotal, etatCommande, typePaiement;
				double tauxTva, remise;
				Date date, dateLivr;
				
				refCommande = rs.getString("idCommande");
				date = rs.getDate("dateCommande");
				refFournisseur = rs.getString("idClient");
				nomFournisseur = rs.getString("nomClient");
				etatCommande = rs.getString("etatCommande");
				tauxTva = rs.getFloat("tauxTva");
				remise = rs.getFloat("remise");
				typePaiement = rs.getString("typePaiement");
				
				
				//Montant total non vide
				if (rs.getString("montantTotal") == null){
					montantTotal = "Pas de produits";
				}
				else {
					montantTotal = rs.getString("montantTotal")+" €";
				}

				listeCommandes.add(new Commande(refCommande, refFournisseur, nomFournisseur, date, etatCommande,montantTotal, tauxTva,remise,typePaiement ));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void supprimerCom(int id){
		Connection cn = DatabaseConnection.getCon();

		PreparedStatement pst;
		try {
			pst = cn.prepareStatement("DELETE FROM vente_commande WHERE idcommande = ?") ;
			pst.setInt(1 , id);
			pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	/**
	 * Change la possibilité d'appuyer sur le bouton modifier et annuler selon son paramètre.
	 * @param b, un booléen.
	 */
	public static void setBtn(boolean b){
		btnModifier.setEnabled(b);
		btnSupprimer.setEnabled(b);
	}
}
