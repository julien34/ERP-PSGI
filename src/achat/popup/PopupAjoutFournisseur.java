package achat.popup;

import java.awt.Choice;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import achat.Fournisseur;
import achat.PanelFournisseur;
import achat.Categorie;
import jdbc.DatabaseConnection;

public class PopupAjoutFournisseur extends JDialog{
	
	private JButton btnValider, btnAnnuler;
	private JTextField txtNom, txtSiret, txtTel, txtAdresse;
	private Choice chCategorie;
	private static ArrayList<Categorie> listeCategorie;
	
	
	/**
	 * Constructeur de la popup.
	 */
	public PopupAjoutFournisseur(){
		
		this.initElements();//On initie les éléments sur les JPanels
		this.initFenetre();//On créer la fenetre
		this.initEcouteurs();//On ajoute les écouteurs
	}
	
	
	/**
	 * Méthode qui créer une nouvelle fenetre avec des caractéristiques.
	 */
	private void initFenetre() {
		
		this.setTitle("Ajouter un Fournisseur");
		this.setResizable(false);
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	
	/**
	 * Méthode qui initialise les composants sur les JPanels
	 */
	private void initElements() {
		
		//On créer un panel principal et les autres qui iront dedans
		JPanel panelAjoutFournisseur = new JPanel(new GridLayout(6,1,5,5));
		JPanel panelNom = new JPanel();
		JPanel panelSiret = new JPanel();
		JPanel panelTel = new JPanel();
		JPanel panelAdresse = new JPanel();
		JPanel panelCategorie = new JPanel();
		JPanel panelBoutons = new JPanel();
		
		
		//On créer les label
		JLabel lblNom = new JLabel("Nom : ");
		JLabel lblSiret = new JLabel("Siret : ");
		JLabel lblTel = new JLabel("Tél : ");
		JLabel lblAdresse = new JLabel("Adresse : ");
		JLabel lblCategorie = new JLabel("Catégorie : ");
		
		//On créer les JTextField
		this.txtNom = new JTextField(10);
		this.txtSiret = new JTextField(10);
		this.txtTel = new JTextField(10);
		this.txtAdresse = new JTextField(10);
		
		//On créer le choix de la catégorie et on récupère ces dernieres sur la BDD
		this.chCategorie = new Choice();
		this.initCategories();
		
		//on créer les boutons OK et annuler
		this.btnValider = new JButton("Valider");
		this.btnAnnuler = new JButton("Annuler");
		
		
		//On ajoute tout les components au panel :
		panelNom.add(lblNom);
		panelNom.add(this.txtNom);
		
		panelSiret.add(lblSiret);
		panelSiret.add(this.txtSiret);
		
		panelTel.add(lblTel);
		panelTel.add(this.txtTel);
		
		panelAdresse.add(lblAdresse);
		panelAdresse.add(this.txtAdresse);
		
		panelCategorie.add(lblCategorie);
		panelCategorie.add(this.chCategorie);
		
		panelBoutons.add(this.btnValider);
		panelBoutons.add(this.btnAnnuler);
		
		//On ajoute les panel au panel en grid
		panelAjoutFournisseur.add(panelNom);
		panelAjoutFournisseur.add(panelSiret);
		panelAjoutFournisseur.add(panelTel);
		panelAjoutFournisseur.add(panelAdresse);
		panelAjoutFournisseur.add(panelCategorie);
		panelAjoutFournisseur.add(panelBoutons);
		
		this.add(panelAjoutFournisseur);
	}
	
	
	
	/**
	 * Méthode qui initialise les catégories de fournisseurs
	 */
	private void initCategories(){
		Connection cn = DatabaseConnection.getCon();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM CategoriesFournisseur ORDER BY nomCategorie");
			
			//On créer une ArrayList qui récupère l'ensemble des Catégories
			listeCategorie = new ArrayList<Categorie>();
			
			//On boucle dans la base de donnée
			while (rs.next()){
				listeCategorie.add(new Categorie(rs.getString("refCategorie"), rs.getString("nomCategorie")));
			}
			
			//On boucle dans la liste
			for (Categorie l : listeCategorie){
				this.chCategorie.addItem(l.getNom());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Méthode qui initialise les écouteurs des boutons.
	 */
	private void initEcouteurs() {
		//Ecouteur du bouton "valider"
		this.btnValider.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Connection cn = DatabaseConnection.getCon();

				try {
					PreparedStatement pst = cn.prepareStatement("INSERT INTO Fournisseurs(refFournisseur, nomFournisseur, siret, telFournisseur, adresseFournisseur, categorieFournisseur) Values(seqRefFournisseur.NEXTVAL,?,?,?,?,?)");

					//On récupère le numéro de la catégorie depuis l'Arraylist :
					String idCategorie = "0";
					for (Categorie categorie : listeCategorie) {
						if (categorie.getNom().equals(chCategorie.getSelectedItem())){
							idCategorie = categorie.getId();
						}
					}
					
					pst.setString(1, PopupAjoutFournisseur.this.txtNom.getText());
					pst.setString(2, PopupAjoutFournisseur.this.txtSiret.getText());
					pst.setString(3, PopupAjoutFournisseur.this.txtTel.getText());
					pst.setString(4, PopupAjoutFournisseur.this.txtAdresse.getText());
					pst.setString(5, idCategorie);

					pst.executeQuery();

					//On cherche quelle est la référence du fn ajouté
					PreparedStatement pst2 = cn.prepareStatement("SELECT * FROM Fournisseurs WHERE nomFournisseur = ? AND siret = ? AND telFournisseur = ? AND adresseFournisseur = ?");

					pst2.setString(1, PopupAjoutFournisseur.this.txtNom.getText());
					pst2.setString(2, PopupAjoutFournisseur.this.txtSiret.getText());
					pst2.setString(3, PopupAjoutFournisseur.this.txtTel.getText());
					pst2.setString(4, PopupAjoutFournisseur.this.txtAdresse.getText());

					ResultSet rs = pst2.executeQuery();

					//On trouve la refFournisseur qui a été attribué
					String ref = null;
					while(rs.next()){
						ref = rs.getString("refFournisseur");
					}

					//on créer un nouveau fournisseur pour l'ajouter à la liste
					Fournisseur f = new Fournisseur(ref,txtNom.getText(),txtSiret.getText(),txtTel.getText(),txtAdresse.getText(), chCategorie.getSelectedItem());

					PanelFournisseur.majTableau(f);

					Window window = SwingUtilities.windowForComponent((Component)e.getSource());
					window.dispose();

					//On affiche un message de validation
					JOptionPane.showMessageDialog(null, "Fournisseur ajouté avec succès","Vous venez d'ajouter le fournisseur "+txtNom.getText()+".",JOptionPane.INFORMATION_MESSAGE);


				} catch(SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erreur dans la saisie des informations", "Ajout de fournisseur", JOptionPane.WARNING_MESSAGE);
				}
			}
		});


		//Ecouteur du bouton "Annuler"
		btnAnnuler.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Window window = SwingUtilities.windowForComponent((Component)e.getSource());
				window.dispose();

			}
		});
	}
}
