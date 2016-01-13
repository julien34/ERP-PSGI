package achat.popup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import jdbc.DatabaseConnection;
import achat.Fournisseur;

public class PopupCommandeSelectFournisseur extends JFrame{
	private static Fournisseur fn;
	private JButton btnValider, btnAnnuler;
	private ScrollPane scrollPane;
	private ArrayList<Fournisseur> liste = new ArrayList<Fournisseur>();
	private DefaultListModel<String> dLM = new DefaultListModel<String>();
	private JList<String> jList = new JList<String>(this.dLM);
	
	/**
	 * Constructeur sans paramètre de la classe.
	 */
	public PopupCommandeSelectFournisseur(){
		this.getFournisseur();//On récupère les fournisseurs sur la base de données
		this.initFenetre();//On initie la fenetre
		this.initComposants();//On initie les composants de la fenetre
		this.initEcouteurs();
	}
	
	
	/**
	 * Méthode qui initialise la fenetre d'ajout de fournisseur à une commande.
	 */
	private void initFenetre(){
		this.setTitle("Sélectionner un fournisseur à affecter à la commande");//Titre de la fenetre
		this.setSize(450, 200);//Taille de la fenetre
		
		Dimension dim = new Dimension(450,250);//Dimension pour dimension minimale
		this.setMinimumSize(dim);//On défini la taille minimale de la fenetre
		this.setLocationRelativeTo(null);//On centre la fenetre
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//fermeture lors du clic sur la croix rouge
		this.setVisible(true);//rendre la fenetre visible
	}
	
	
	/**
	 * Méthode qui initialise l'ensemble des composants.
	 */
	private void initComposants(){
		
		//Initialisation des différents panels
		JPanel panelPrincipal = new JPanel(new BorderLayout());//Principal
		JPanel panelListe = new JPanel();//Celui qui accueillera la liste
		JPanel panelBtn = new JPanel();//Celui qui accueillera les boutons
		
		//Initialisation des composants
		this.btnValider = new JButton("Valider");
		this.btnAnnuler = new JButton("Annuler");
		this.btnValider.setEnabled(false);//On grise le bouton valider
		this.scrollPane = new ScrollPane();
		this.scrollPane.setSize(450, 200);
		
		//Ajouts des composants aux différents panels
		this.scrollPane.add(this.jList);
		panelListe.add(scrollPane);
		panelBtn.add(this.btnValider);
		panelBtn.add(this.btnAnnuler);
		
		//On ajoute l'ensemble des panels à la frame
		panelPrincipal.add(panelListe, BorderLayout.CENTER);
		panelPrincipal.add(panelBtn, BorderLayout.SOUTH);
		this.add(panelPrincipal);
	}
	
	
	/**
	 * Méthode qui récupère l'ensemble des fournisseurs de la base de données et les ajoute dans l'arrayList.
	 */
	private void getFournisseur() {
		try{
			Connection cn = DatabaseConnection.getCon();
			Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM Fournisseurs f LEFT JOIN CategoriesFournisseur c ON f.categorieFournisseur = c.refCategorie ORDER BY nomFournisseur");
			
			while(rs.next()){
				String ref = rs.getString("refFournisseur");
				String nomFn = rs.getString("nomFournisseur");
				String siret = rs.getString("siret");
				String tel = rs.getString("telFournisseur");
				String adresse = rs.getString("adresseFournisseur");
				String categorie = rs.getString("nomCategorie");

				this.liste.add(new Fournisseur(ref, nomFn, siret, tel, adresse, categorie));
				this.dLM.addElement(ref+" - "+nomFn);
				
			}} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Méthode qui assigne le fournisseur sélectionné au fournisseur courant.
	 * @param f, un fournisseur.
	 * @return le fournisseur selectionné.
	 */
	private static void selectFournisseur(Fournisseur f){
		fn = f;
		PopupCommande.setFournisseur(f);
	}
	
	
	/**
	 * Méthode qui initialise les écouteurs.
	 */
	private void initEcouteurs(){
		
		//Bouton ajouter
		this.btnValider.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupCommandeSelectFournisseur.selectFournisseur(PopupCommandeSelectFournisseur.this.liste.get(PopupCommandeSelectFournisseur.this.jList.getSelectedIndex()));
				dispose();
			}
		});
		
		//Bouton annuler
		this.btnAnnuler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		//JListe
		this.jList.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				PopupCommandeSelectFournisseur.this.btnValider.setEnabled(true);//On active le bouton valider
				
				//Double clic
				if(e.getClickCount()%2==0){
					PopupCommandeSelectFournisseur.selectFournisseur(PopupCommandeSelectFournisseur.this.liste.get(PopupCommandeSelectFournisseur.this.jList.getSelectedIndex()));
					dispose();
				}
			}
		});
	}
}
