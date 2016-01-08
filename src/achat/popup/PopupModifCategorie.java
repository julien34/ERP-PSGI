package achat.popup;

import java.awt.BorderLayout;
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
import achat.Categorie;
import achat.PanelCategorie;

public class PopupModifCategorie extends JDialog{
	private String nom, code;
	private int indice;
	private JTextField txtNomCategorie;
	private JButton btnAnnuler, btnValider;
	private JLabel lblNomCategorie;
	
	/**
	 * Constructeur avec une cat�gorie � modifier en param�tre.
	 * @param c, une cat�gorie � modifier.
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
	 * M�thode qui cr�er une nouvelle fenetre avec des caract�ristiques.
	 */
	private void initFenetre(){
		this.setTitle("Modifier une cat�gorie de fournisseur");
		this.setResizable(false);
		this.setSize(500, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	/**
	 * M�thode qui initialise les composants sur les JPanels
	 */
	private void initElements(){
		
		//On d�fini le layout de la fen�tre
		this.setLayout(new GridLayout(2,1));
		
		
		//On cr�er les �l�ments
		JPanel panelTxt = new JPanel();
		JPanel panelBtn = new JPanel();
		
		this.btnAnnuler = new JButton("Annuler");
		this.btnValider = new JButton("Valider");
		this.lblNomCategorie = new JLabel("Nom de la cat�gorie : ");
		this.txtNomCategorie = new JTextField(15);
		this.txtNomCategorie.setText(this.nom);
		
		//On dispose les �l�ments sur la fen�tre
		panelTxt.add(this.lblNomCategorie);
		panelTxt.add(this.txtNomCategorie);
		panelBtn.add(this.btnValider);
		panelBtn.add(this.btnAnnuler);
		
		this.add(panelTxt);
		this.add(panelBtn);
	}
	
	
	/**
	 * M�thode qui initialise les �couteurs des boutons.
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
				dispose();
				PanelCategorie.getBtonModifier().setEnabled(false);
			}
		});
	}
	
	
	/**
	 * M�thode qui ex�cute la requete (modification de ligne dans la table)
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
