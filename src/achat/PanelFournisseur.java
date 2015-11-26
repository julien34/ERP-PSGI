package achat;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import principal.FenetrePrincipale;
import jdbc.DatabaseConnection;

public class PanelFournisseur extends JPanel{
	
	private static FenetrePrincipale framePrincipale;
	
	private ResultSet rs;
	private Statement st;
	private Connection cn;
	
	private JTable tableau = new JTable(new DefaultTableModel());
	
	private ArrayList<Fournisseur> liste = new ArrayList<Fournisseur>();
	
	
	/**
	 * Constructeur avec la fenetre principale en parmètre la classe PanelAjoutFournisseur
	 * @throws SQLException 
	 */
	public PanelFournisseur(FenetrePrincipale f) {
		
		this.framePrincipale = f;
		
		JPanel panelBoutonsSud = new JPanel();//On créer un JPanel qui va recevoir les boutons
		JPanel panelGridCentre = new JPanel(new BorderLayout(10,10));//On créer le panel qui va recevoir la grid
		JPanel panelTitreNord = new JPanel();//On créer le panel qui va recevoir le titre
		
		//On créer nos composants
		JButton btnAjouter = new JButton("Ajouter");
		JButton btnModifier = new JButton("Modifier");
		JButton btnSupprimer = new JButton("Supprimer");
		JLabel titre = new JLabel("Gestion des fournisseurs");
		
		//On créer un modèle pour la grid
		JScrollPane scrollPane = new JScrollPane();
		
		//On ajoute les composants aux différents panels
		panelBoutonsSud.add(btnAjouter);
		panelBoutonsSud.add(btnModifier);
		panelBoutonsSud.add(btnSupprimer);
		panelTitreNord.add(titre);
		
		//On récupère l'ensemble des founisseurs présents dans la BDD
		try {
			this.getFournisseur();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//On remplit la JTable
		this.remplirTableau();
		
		//On ajoute le tableau au scrollpanel
		panelGridCentre.add(this.tableau);
		
		//On ajoute le scrollPane à la grid du centre
		//panelGridCentre.add(scrollPane);
		
		//On ajoute les panel à la frame principale
		this.add(panelTitreNord, BorderLayout.NORTH);
		this.add(panelGridCentre, BorderLayout.CENTER);
		this.add(panelBoutonsSud, BorderLayout.SOUTH);
	}
	
	
	/**
	 * Méthode qui retourne une ArrayList de tous les Fournisseurs présents dans la base de donnée.
	 * @return ArrayList<Fournisseur>
	 * @throws SQLException
	 */
	private ArrayList<Fournisseur> getFournisseur() throws SQLException{
		
		DatabaseConnection db = new DatabaseConnection();
		this.cn = db.getCon();
		this.st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		this.rs = st.executeQuery("SELECT * FROM Fournisseurs ORDER BY nomFournisseur");
		
		while(rs.next()){
			String ref = rs.getString("refFournisseur");
			String nomFn = rs.getString("nomFournisseur");
			String siret = rs.getString("siret");
			String tel = rs.getString("telFournisseur");
			String adresse = rs.getString("adresseFournisseur");

			this.liste.add(new Fournisseur(ref, nomFn, siret, tel, adresse));
		}

		return this.liste;
	}
	
	
	/**
	 * Méthode qui remplit la JTable à partir de l'arraylist récupérée
	 */
	private void remplirTableau(){
		String[] titres = {"Entreprise","N° SIRET","Adresse","N° Tél"};
		int lgTableau = this.liste.size();
		Object[][] tabFn = new Object[lgTableau][4];
		
		//On remplit le tableau d'objet avec les fn de l'arraylist
		for(Fournisseur fn : this.liste){
			tabFn[this.liste.indexOf(fn)][0] = fn.nom;
			tabFn[this.liste.indexOf(fn)][1] = fn.siret;
			tabFn[this.liste.indexOf(fn)][2] = fn.adresse;
			tabFn[this.liste.indexOf(fn)][3] = fn.tel;
		}
		
		
		//On attribue la grid au tableau courant
		this.tableau = new JTable(tabFn,titres);
	}

}
