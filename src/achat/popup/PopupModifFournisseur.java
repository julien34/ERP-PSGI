package achat.popup;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Window;
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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import achat.Fournisseur;
import achat.PanelFournisseur;
import jdbc.DatabaseConnection;

public class PopupModifFournisseur extends JDialog{
	
	private Fournisseur founisseur;
	private int indice;
	
	private JButton btnValider, btnAnnuler;
	private JTextField txtNom, txtSiret, txtTel, txtAdresse;

	public PopupModifFournisseur(Fournisseur f, int i){
		this.founisseur = f;
		this.indice = i;

		this.initElements();//On initie les éléments sur les JPanels
		this.initFenetre();//On créer la fenetre
		this.initEcouteurs();//On ajoute les écouteurs
	}


	/**
	 * Méthode qui créer une nouvelle fenetre avec des caractéristiques.
	 */
	private void initFenetre() {
		this.setTitle("Modifier un Fournisseur");
		this.setResizable(false);
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	
	/**
	 * Méthode qui initialise les composants sur les JPanels
	 */
	private void initElements(){
		
		//On créer un panel principal et les autres qui iront dedans
		JPanel panelModifFournisseur = new JPanel(new GridLayout(5,1,5,5));
		JPanel panelNom = new JPanel();
		JPanel panelSiret = new JPanel();
		JPanel panelTel = new JPanel();
		JPanel panelAdresse = new JPanel();
		JPanel panelBoutons = new JPanel();


		//On créer les label
		JLabel lblNom = new JLabel("Nom : ");
		JLabel lblSiret = new JLabel("Siret : ");
		JLabel lblTel = new JLabel("Tél : ");
		JLabel lblAdresse = new JLabel("Adresse : ");

		//On créer les JTextField
		this.txtNom = new JTextField(10);
		this.txtSiret = new JTextField(10);
		this.txtTel = new JTextField(10);
		this.txtAdresse = new JTextField(20);

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

		panelBoutons.add(this.btnValider);
		panelBoutons.add(this.btnAnnuler);

		//On ajoute les panel au panel en grid
		panelModifFournisseur.add(panelNom);
		panelModifFournisseur.add(panelSiret);
		panelModifFournisseur.add(panelTel);
		panelModifFournisseur.add(panelAdresse);
		panelModifFournisseur.add(panelBoutons);

		this.add(panelModifFournisseur);

		this.txtNom.setText(PopupModifFournisseur.this.founisseur.getNom());
		this.txtSiret.setText(PopupModifFournisseur.this.founisseur.getSiret());
		this.txtTel.setText(PopupModifFournisseur.this.founisseur.getTel());
		this.txtAdresse.setText(PopupModifFournisseur.this.founisseur.getAdresse());

		this.add(panelModifFournisseur);
	}
	
	/**
	 * Méthode qui initialise les écouteurs des boutons.
	 */
	private void initEcouteurs() {
		
		//Ecouteurs du bouton "valider"
		this.btnValider.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Connection cn = DatabaseConnection.getCon();
				PreparedStatement pst;

				try {
					pst = cn.prepareStatement("UPDATE Fournisseurs SET nomFournisseur = ?, siret = ?, telFournisseur = ?, adresseFournisseur = ? WHERE refFournisseur = ?");
					pst.setString(1, PopupModifFournisseur.this.txtNom.getText());
					pst.setString(2, PopupModifFournisseur.this.txtSiret.getText());
					pst.setString(3, PopupModifFournisseur.this.txtTel.getText());
					pst.setString(4, PopupModifFournisseur.this.txtAdresse.getText());
					pst.setString(5, PopupModifFournisseur.this.founisseur.getRef());

					pst.executeQuery();

					//Modifie le Founisseur courant
					PopupModifFournisseur.this.founisseur.setNom(txtNom.getText());
					PopupModifFournisseur.this.founisseur.setSiret(txtSiret.getText());
					PopupModifFournisseur.this.founisseur.setTel(txtTel.getText());
					PopupModifFournisseur.this.founisseur.setAdresse(txtAdresse.getText());

					//On rafraichit le tableau
					PanelFournisseur.setTableau(PopupModifFournisseur.this.founisseur,PopupModifFournisseur.this.indice);

					//On ferme la JDialog
					Window window = SwingUtilities.windowForComponent((Component)e.getSource());
					window.dispose();
					PanelFournisseur.setTableau(PopupModifFournisseur.this.founisseur, PopupModifFournisseur.this.indice);
					JOptionPane.showMessageDialog(null, "Fournisseur modifié avec succès.", "Modification de fournisseur", JOptionPane.INFORMATION_MESSAGE);

					//PanelFournisseur.this.doLayout();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Erreur dans la saisie des informations", "Modification de fournisseur", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}	
			}
		});

		
		//Ecouteur du bouton "annuler"
		this.btnAnnuler.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//On ferme la JDialog
				Window window = SwingUtilities.windowForComponent((Component)e.getSource());
				window.dispose();
			}
		});
	}
}