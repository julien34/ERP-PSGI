package achat.vues;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import achat.modeles.CommandesFournisseur;
import achat.modeles.TVA;
import achat.modeles.UneditableTableModel;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelTVA extends JPanel {

	private static final long serialVersionUID = 1L;
	public static ArrayList<TVA> listeTVA = new ArrayList<TVA>();
	private static Object[][] tabTVA;
	private static Object[] titres = {"Référence TVA", "Nom TVA", "Taux"};
	
	//On crée la JTable et son modèle
	private static JTable tableau = new JTable(new DefaultTableModel());
	private static UneditableTableModel modele = new UneditableTableModel(0,5);
	private static JScrollPane scrollPane  = new JScrollPane(tableau);
	
	//On crée les composants dont on va se servir dans plusieurs méthodes 
		private static JButton btNouveau, btModifier, btSupprimer;
		//private static JTextField txtRechercheCommande, txtRechercheFournisseur, txtRechercheMontant;
		private static JTextField txtRechercheNomTVA;
	
	public PanelTVA(FenetrePrincipale f) {
		getAllTVA();
	}
	
	/**
	 * Méthode qui récupère l'ensemble des TVA de la base de données et les ajoute dans une ArrayList.
	 */
	public static void getAllTVA(){
		listeTVA.clear();
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT t.refTVA, t.nomTVA, t.tauxTVA FROM TVA t ORDER BY tauxTVA");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				String ref, nom;
				double taux;
				
				ref = rs.getString("refTVA");
				nom = rs.getString("nomTVA");
				taux = rs.getDouble("tauxTVA");

				listeTVA.add(new TVA(ref, nom, taux));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui récupère l'ensemble des TVA de la base de données selon la recherche du champs et les ajoute dans une ArrayList.
	 */
	public static void getTVARecherche(){
		listeTVA.clear();
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT t.refTVA, t.nomTVA, t.tauxTVA FROM TVA t WHERE UPPER(t.nomTVA) LIKE UPPER(?)ORDER BY t.tauxTVA");
			pst.setString(1, "%"+PanelTVA.txtRechercheNomTVA.getText()+"%");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				String ref, nom;
				double taux;
				
				ref = rs.getString("refTVA");
				nom = rs.getString("nomTVA");
				taux = rs.getDouble("tauxTVA");

				listeTVA.add(new TVA(ref, nom, taux));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui met à jour le tableau des TVA.
	 */
	public static void maj(){
		int nbDeTVA = listeTVA.size();
		tabTVA = new Object[nbDeTVA][3];
		
		for(TVA tva : listeTVA){
			tabTVA[listeTVA.indexOf(tva)][0] = tva.getRefTVA();
			tabTVA[listeTVA.indexOf(tva)][1] = tva.getNomTVA();
			tabTVA[listeTVA.indexOf(tva)][2] = tva.getTauxTVA();
		}
		
		modele.setDataVector(tabTVA,titres);
	}
	
	
	/**
	 * Méthode qui remplit le tableau avec les valeurs de l'arraylist, mais aussi la JTable.
	 */
	public static void remplirTableau(){
		
		maj();
		
		tableau = new JTable(modele);
		tableau.setAutoCreateRowSorter(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setPreferredScrollableViewportSize(new Dimension(800, 224));

		scrollPane.setViewportView(tableau);
	}
	
	/**
	 * Méthode qui initialise les éléments de toute la page.
	 */
	private void initElements(){

		this.setLayout(new BorderLayout());

		JPanel panelRechercheNord = new JPanel();
		JPanel panelGrilleCentre = new JPanel(new GridLayout(2,1));
		JPanel panelGrille = new JPanel();
		JPanel panelBouton = new JPanel();
		
		JLabel lblRechercheNomTVA = new JLabel("Nom TVA : ");
		txtRechercheNomTVA = new JTextField(10);
		btNouveau = new JButton("Ajouter");
		btModifier = new JButton("Modifier");
		btSupprimer = new JButton("Supprimer");
		setBt(false);
		
		
		//On ajoute les composants aux panels
		panelRechercheNord.add(lblRechercheNomTVA);
		panelRechercheNord.add(txtRechercheNomTVA);
	
		panelGrille.add(scrollPane);
		panelBouton.add(btNouveau);
		panelBouton.add(btModifier);
		panelBouton.add(btSupprimer);
		
		panelGrilleCentre.add(panelGrille);
		panelGrilleCentre.add(panelBouton);

		this.add(panelRechercheNord, BorderLayout.NORTH);
		this.add(panelGrilleCentre, BorderLayout.CENTER);
		
	}
	
	/**
	 * Change la possibilité d'appuyer sur le bouton modifier et supprimer selon le paramètre.
	 * @param b, un booléen.
	 */
	public static void setBt(boolean b){
		btModifier.setEnabled(b);
		btSupprimer.setEnabled(b);
	}
}