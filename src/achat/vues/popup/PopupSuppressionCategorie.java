package achat.vues.popup;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import jdbc.DatabaseConnection;
import achat.modeles.Categorie;
import achat.vues.PanelCategorie;

public class PopupSuppressionCategorie extends JDialog{
	
	private String code, nom;
	private int indice;
	private JButton btnAnnuler, btnValider;
	private JLabel lblNomCategorie;
	
	/**
	 * Constructeur avec une cat�gorie � supprimer en param�tre.
	 * @param c, une cat�gorie � modifier.
	 */
	public PopupSuppressionCategorie(Categorie c, int indice){
		this.code = c.getId();
		this.nom = c.getNom();
		this.indice = indice;
		
		this.initFenetre();
		this.initElements();
		this.initEcouteurs();
	}

	
	/**
	 * M�thode qui initialise la frame de suppression d'une cat�gorie fournisseurs.
	 */
	private void initFenetre() {
		this.setTitle("Supprimer une cat�gorie de fournisseur");
		this.setResizable(false);
		this.setSize(500, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);	
	}
	
	
	/**
	 * M�thode qui initialise les �l�ments du panel.
	 */
	private void initElements() {

		//On d�fini le layout de la fen�tre
		this.setLayout(new GridLayout(2,1));
		
		
		//On cr�er les �l�ments
		JPanel panelTxt = new JPanel();
		JPanel panelBtn = new JPanel();
		
		this.btnAnnuler = new JButton("Annuler");
		this.btnValider = new JButton("Valider");
		this.lblNomCategorie = new JLabel("Voulez-vous vraiment supprimer cette cat�gorie ?");
		
		//On dispose les �l�ments sur la fen�tre
		panelTxt.add(this.lblNomCategorie);
		panelBtn.add(this.btnValider);
		panelBtn.add(this.btnAnnuler);
		
		this.add(panelTxt);
		this.add(panelBtn);
	}
	
	
	/**
	 * M�thode qui initialise les �couteurs des boutons.
	 */
	private void initEcouteurs() {
		
		//Bouton valider
		this.btnValider.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupSuppressionCategorie.this.envoierequete();
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
	}
	
	
	/**
	 * M�thode qui ex�cute la requete (modification de ligne dans la table).
	 */
	private void envoierequete(){

		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("DELETE FROM CategoriesFournisseur WHERE RefCategorie = ?");
			pst.setString(1, this.code);
			pst.executeQuery();
			
			//On retire la cat�gorie du tableau, et on grise les deux bouton "modifier" et "supprimer"
			PanelCategorie.majTableauSuppr(new Categorie(PopupSuppressionCategorie.this.code, PopupSuppressionCategorie.this.nom), PopupSuppressionCategorie.this.indice);
			PanelCategorie.getBtonSupprimer().setEnabled(false);
			PanelCategorie.getBtonModifier().setEnabled(false);
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "La cat�gorie est associ�e � un ou plusieurs fournisseur(s).", "Erreur", JOptionPane.ERROR_MESSAGE);

		}
	}
}
