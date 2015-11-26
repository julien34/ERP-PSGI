package achat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import principal.FenetrePrincipale;
import jdbc.DatabaseConnection;

public class PanelFournisseur extends JPanel{
	
	private static FenetrePrincipale framePrincipale;
	
	private ResultSet rs;
	private Statement st;
	private Connection cn;
	
	private JScrollPane scrollPane;
	private JTable tableau = new JTable(new DefaultTableModel());
	
	private ArrayList<Fournisseur> liste = new ArrayList<Fournisseur>();
	
	
	/**
	 * Constructeur avec la fenetre principale en parmètre la classe PanelAjoutFournisseur
	 * @throws SQLException 
	 */
	public PanelFournisseur(FenetrePrincipale f) {
		
		this.framePrincipale = f;
		
		JPanel panelBoutonsSud = new JPanel(new FlowLayout(FlowLayout.CENTER,40,0));//On créer un JPanel qui va recevoir les boutons
		JPanel panelGridCentre = new JPanel(new BorderLayout(10,10));//On créer le panel qui va recevoir la grid
		
		//On créer nos composants
		JButton btnAjouter = new JButton("Ajouter");
		JButton btnModifier = new JButton("Modifier");
		JButton btnSupprimer = new JButton("Supprimer");
		
		btnModifier.setEnabled(false);
		btnSupprimer.setEnabled(false);
		
		//On ajoute les composants aux différents panels
		panelBoutonsSud.add(btnAjouter);
		panelBoutonsSud.add(btnModifier);
		panelBoutonsSud.add(btnSupprimer);
		
		//On récupère l'ensemble des founisseurs présents dans la BDD
		try {
			this.getFournisseur();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//On remplit la JTable
		this.remplirTableau();
		
		panelGridCentre.add("North",this.scrollPane);
		
		//On ajoute les panel à la frame principale
		this.add(panelGridCentre);
		this.add("South",panelBoutonsSud);
		
		
		//handler boutons :
		btnModifier.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupModifFournisseur popupModifFournisseur = new PopupModifFournisseur(PanelFournisseur.this.getListe().get(tableau.getSelectedRow()));
			}
		});
		
		//Handler sur al liste qui permet de "dégriser" un bouton
		this.tableau.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				btnModifier.setEnabled(true);
				btnSupprimer.setEnabled(true);
			}
		});;
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
	
	public ArrayList<Fournisseur> getListe(){
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
		
		UneditableTableModel modele = new UneditableTableModel(0,4);
		modele.setDataVector(tabFn,titres);
		
		this.tableau = new JTable(modele);
		
		this.tableau.setAutoCreateRowSorter(false);
		this.tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tableau.setPreferredScrollableViewportSize(new Dimension(540, 224));
		
		scrollPane = new JScrollPane(this.tableau);
		
		//On créer une listeSelectionModel pour savoir lequel on a sélectionné
		ListSelectionModel listeDeSelection = this.tableau.getSelectionModel();
		
		//Lors d'un double clic sur une ligne
		this.tableau.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()%2 == 0){
					PopupModifFournisseur popupModifFournisseur = new PopupModifFournisseur(PanelFournisseur.this.getListe().get(tableau.getSelectedRow()));
				}
			}
		});	
	}
	
	public Statement getSt(){
		return this.st;
	}
}
