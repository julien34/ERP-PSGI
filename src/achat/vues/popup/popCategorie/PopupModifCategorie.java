package achat.vues.popup.popCategorie;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jdbc.DatabaseConnection;
import achat.modeles.Categorie;
import achat.vues.PanelCategorie;

public class PopupModifCategorie extends JDialog{
	private String nom, code;
	private int indice;
	private JTextField txtNomCategorie;
	private JButton btnAnnuler, btnValider;
	private JLabel lblNomCategorie;
	
	/**
	 * Constructeur avec une catégorie à modifier en paramètre.
	 * @param c, une catégorie à modifier.
	 */
	public PopupModifCategorie(Categorie c, int indice){
		this.code = c.getId();
		this.nom = c.getNom();
		this.indice = indice;
		
		this.initFenetre();
		this.initElements();
		this.initEcouteurs();
	}
	
	
	/**
	 * Méthode qui créer une nouvelle fenetre avec des caractéristiques.
	 */
	private void initFenetre(){
		this.setTitle("Modifier une catégorie de fournisseur");
		this.setResizable(false);
		this.setSize(500, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	/**
	 * Méthode qui initialise les composants sur les JPanels
	 */
	private void initElements(){
		
		//On défini le layout de la fenêtre
		this.setLayout(new GridLayout(2,1));
		
		
		//On créer les éléments
		JPanel panelTxt = new JPanel();
		JPanel panelBtn = new JPanel();
		
		this.btnAnnuler = new JButton("Annuler");
		this.btnValider = new JButton("Valider");
		this.lblNomCategorie = new JLabel("Nom de la catégorie : ");
		this.txtNomCategorie = new JTextField(15);
		this.txtNomCategorie.setText(this.nom);
		
		//On dispose les éléments sur la fenêtre
		panelTxt.add(this.lblNomCategorie);
		panelTxt.add(this.txtNomCategorie);
		panelBtn.add(this.btnValider);
		panelBtn.add(this.btnAnnuler);
		
		this.add(panelTxt);
		this.add(panelBtn);
	}
	
	
	/**
	 * Méthode qui initialise les écouteurs des boutons.
	 */
	private void initEcouteurs(){
		
		//Bouton annuler (ferme la fenetre)
		this.btnAnnuler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		//Bouton valider (envoie la requete, modifie le panelCategorie et ferme la fenetre)
		this.btnValider.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupModifCategorie.this.envoierequete();
				PanelCategorie.majTableauModif(new Categorie(PopupModifCategorie.this.code, PopupModifCategorie.this.nom), PopupModifCategorie.this.indice);
				PanelCategorie.getBtonModifier().setEnabled(false);
				PanelCategorie.getBtonSupprimer().setEnabled(false);
				dispose();
			}
		});
	}
	
	
	/**
	 * Méthode qui exécute la requete (modification de ligne dans la table)
	 */
	private void envoierequete(){
		this.nom = this.txtNomCategorie.getText();
		
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("UPDATE CategoriesFournisseur SET nomCategorie = ? WHERE RefCategorie = ?");
			pst.setString(1, this.nom);
			pst.setString(2, this.code);
			pst.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
