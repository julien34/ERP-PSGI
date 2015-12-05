package achat;

import java.awt.Dimension;
import java.awt.ScrollPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelCommande extends JPanel{
	
	private ArrayList<CommandesFournisseur> listeCommandes = new ArrayList<CommandesFournisseur>();
	private Object[][] tabCo;
	private Object[] titres = {"N° Commande","Fournisseur","Date", "Montant"};
	
	//On créer la JTable et son modèle
	private static JTable tableau = new JTable(new DefaultTableModel());
	private static UneditableTableModel modele;
	private JScrollPane scrollPane;

	public PanelCommande(FenetrePrincipale f){
		
		//On récupère l'ensemble des founisseurs présents dans la BDD
		this.getCommande();
				
		//On remplit la JTable
		this.remplirTableau();
				
		//On initialise l'ensemble des composants sur le JPanel
		//this.initElements();
				
		//On initialise l'ensemble des écouteurs
		//this.initEcouteurs();
	}
	
	
	/**
	 * Méthode qui récupère l'ensemble des commandes de la base de données et les ajoute dans une ArrayList.
	 */
	private void getCommande(){
		
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT c.refCommande, c.dateCommande, f.refFournisseur, f.nomFournisseur, SUM(p.prixProduit*lc.quantite) AS montantTotal FROM CommandesFournisseur c JOIN Fournisseurs f ON f.refFournisseur = c.refFournisseur JOIN LignesCommandeFournisseur lc ON lc.refCommande = c.refCommande JOIN ProduitsFournisseur p ON p.refProduit = lc.refProduit GROUP BY c.refCommande, c.dateCommande, f.refFournisseur, f.nomFournisseur ORDER BY c.dateCommande DESC");
			ResultSet rs = pst.executeQuery();
			
			
			while(rs.next()){

				String refCommande = rs.getString("refCommande");
				Date date = rs.getDate("dateCommande");
				String refFournisseur = rs.getString("refFournisseur");
				String nomFournisseur = rs.getString("nomFournisseur");
				String montantTotal = rs.getString("montantTotal")+"€";

				this.listeCommandes.add(new CommandesFournisseur(refCommande, date, refFournisseur, nomFournisseur, montantTotal));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Méthode qui remplit le tableau avec les valeurs de l'arraylist, mais aussi la JTable.
	 */
	private void remplirTableau(){
		
		int nbDeCo = this.listeCommandes.size();//On calcule la taille de l'arrylist pour créer un tableau adéquat
		this.tabCo = new Object[nbDeCo][4];//On créer le tableau de la taille récupérée 
		
		//On remplit ce dernier avec les CommandesFournisseur récupérées
		for(CommandesFournisseur cf : this.listeCommandes){
			this.tabCo[this.listeCommandes.indexOf(cf)][0] = cf.getRefCommande();
			this.tabCo[this.listeCommandes.indexOf(cf)][1] = cf.getNomFourniseur();
			this.tabCo[this.listeCommandes.indexOf(cf)][2] = cf.getDate();
			this.tabCo[this.listeCommandes.indexOf(cf)][3] = cf.getMontantTotal();
		}
		
		
		modele = new UneditableTableModel(0,5);
		modele.setDataVector(tabCo,titres);
		
		tableau = new JTable(modele);
		
		tableau.setAutoCreateRowSorter(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setPreferredScrollableViewportSize(new Dimension(800, 224));
		
		scrollPane = new JScrollPane(tableau);
		this.add(scrollPane);
	}
}
