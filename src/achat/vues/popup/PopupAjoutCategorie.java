package achat.vues.popup;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import achat.modeles.Categorie;
import achat.vues.PanelCategorie;
import jdbc.DatabaseConnection;

public class PopupAjoutCategorie extends JDialog{
	
	private JButton btnValider, btnAnnuler;
	private JTextField txtNomCategorie;
	private JLabel lblNomcategorie;
	private JPanel panelPrincipal, panelBtn, panelTxt;
	
	
	/**
	 * Constructeur de la popup.
	 */
	public PopupAjoutCategorie(){
		this.initFenetre();
		this.initElement();
		this.initEcouteurs();
	}
	
	/**
	 * Méthode qui créer une nouvelle fenetre avec des caractéristiques.
	 */
	private void initFenetre(){
		this.setTitle("Ajouter une catégorie de Fournisseur");
		this.setResizable(false);
		this.setSize(500, 175);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	/**
	 * Méthode qui initialise les composants sur les JPanels
	 */
	private void initElement(){
		
		//Initiation des composants et panels
		this.btnValider = new JButton("Valider");
		this.btnAnnuler = new JButton("Annuler");
		this.txtNomCategorie = new JTextField(15);
		this.lblNomcategorie = new JLabel("Nom de la catégorie : ");
		this.panelBtn = new JPanel();
		this.panelTxt = new JPanel();
		this.panelPrincipal = new JPanel(new GridLayout(2,2));
		
		//Ajout des composants aux panels
		
		
		this.panelTxt.add(this.lblNomcategorie);
		this.panelTxt.add(this.txtNomCategorie);
		this.panelPrincipal.add(this.panelTxt);
		
		this.panelBtn.add(this.btnValider);
		this.panelBtn.add(this.btnAnnuler);
		this.panelPrincipal.add(this.panelBtn);
		
		this.add(this.panelPrincipal);
	}
	
	
	/**
	 * Méthode qui initialise les écouteurs des boutons.
	 */
	private void initEcouteurs(){
		
		//Bouton annuler ferme la popup
		this.btnAnnuler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		//Bouton valider envoie la requete
		btnValider.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Connection cn = DatabaseConnection.getCon();
					PreparedStatement pst = cn.prepareStatement("INSERT INTO CategoriesFournisseur(refCategorie, nomCategorie) VALUES(seqRefCategorieFournisseur.NEXTVAL, ?)");
					pst.setString(1, PopupAjoutCategorie.this.txtNomCategorie.getText());
					pst.executeQuery();
					dispose();
					
					
					//On cherche le code attribué lors de l'ajout
					Statement stat = DatabaseConnection.getCon().createStatement();
					ResultSet result = stat.executeQuery("SELECT last_number FROM user_sequences WHERE sequence_name = 'SEQREFCATEGORIEFOURNISSEUR'");
					result.next();
					String seqCodePersonne = result.getString("LAST_NUMBER");
					
					//On ajoute la catégorie à l'arraylist
					PanelCategorie.majTableau(new Categorie(seqCodePersonne, PopupAjoutCategorie.this.txtNomCategorie.getText()));
					
					//On affiche un message de validation
					JOptionPane.showMessageDialog(null, "Catégorie de fournisseur ajouté avec succès","Vous venez d'ajouter la catégorie fournisseur "+txtNomCategorie.getText()+".",JOptionPane.INFORMATION_MESSAGE);

					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}
