package achat.vues.popup.popTVA;

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

import achat.modeles.Categorie;
import achat.modeles.Fournisseur;
import achat.modeles.TVA;
import achat.vues.PanelFournisseur;
import achat.vues.PanelTVA;
import achat.vues.popup.popFournisseur.PopupAjoutFournisseur;
import jdbc.DatabaseConnection;

public class PopupAjoutTVA extends JDialog {
		
		private static final long serialVersionUID = 1L;
		private JButton btValider, btAnnuler;
		private JTextField txtNomTVA, txtRefTVA, txtTauxTVA;
		//private static ArrayList<TVA> listeTVA;
		
		
		/**
		 * Constructeur de la popup.
		 */
		public PopupAjoutTVA(){
			
			this.initElements();
			this.initFenetre();
			this.initEcouteurs();
		}
		
		
		/**
		 * Méthode qui créer une nouvelle fenetre avec des caractéristiques.
		 */
		private void initFenetre() {
			this.setTitle("Ajouter une TVA");
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

			JPanel panelAjoutTVA = new JPanel(new GridLayout(6,1,5,5));
			JPanel panelNomTVA = new JPanel();
			JPanel panelTauxTVA = new JPanel();
			JPanel panelBoutons = new JPanel();
			
			JLabel lblNomTVA = new JLabel("Nom TVA : ");
			JLabel lblTauxTVA = new JLabel("Taux : ");
			
			this.txtNomTVA = new JTextField(10);
			this.txtTauxTVA = new JTextField(10);
			
			this.btValider = new JButton("Valider");
			this.btAnnuler = new JButton("Annuler");
			
			
			panelNomTVA.add(lblNomTVA);
			panelNomTVA.add(this.txtNomTVA);
			
			panelTauxTVA.add(lblTauxTVA);
			panelTauxTVA.add(this.txtTauxTVA);
			
			panelBoutons.add(this.btValider);
			panelBoutons.add(this.btAnnuler);
			
			panelAjoutTVA.add(panelNomTVA);
			panelAjoutTVA.add(panelTauxTVA);
			panelAjoutTVA.add(panelBoutons);
			
			this.add(panelAjoutTVA);
		}
		
		/**
		 * Méthode qui initialise les écouteurs des boutons.
		 */
		private void initEcouteurs() {
			this.btValider.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					Connection cn = DatabaseConnection.getCon();
					try {
						PreparedStatement pst = cn.prepareStatement("INSERT INTO TVA(refTVA, nomTVA, tauxTVA) VALUES(seqRefTVA.NEXTVAL,?,?)");
						
						pst.setString(1, PopupAjoutTVA.this.txtNomTVA.getText());
						pst.setString(2, PopupAjoutTVA.this.txtTauxTVA.getText());
						pst.executeQuery();
						TVA tva = new TVA(Integer.toString(PanelTVA.getReference_glob()), PopupAjoutTVA.this.txtNomTVA.getText(), Integer.parseInt(PopupAjoutTVA.this.txtTauxTVA.getText()));
						//PanelTVA.ajoutTVAListe(new TVA(Integer.toString(PanelTVA.getReference_glob()), PopupAjoutTVA.this.txtNomTVA.getText(), Integer.parseInt(PopupAjoutTVA.this.txtTauxTVA.getText())));
						PanelTVA.getAllTVA();
						PanelTVA.remplirTableau();
						Window window = SwingUtilities.windowForComponent((Component)e.getSource());
						window.dispose();

						JOptionPane.showMessageDialog(null, "TVA ajoutée avec succès","Vous venez d'ajouter la TVA "+txtNomTVA.getText()+".",JOptionPane.INFORMATION_MESSAGE);
						
					} catch(SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Erreur dans la saisie des informations", "Ajout de TVA", JOptionPane.WARNING_MESSAGE);
					}
				}
			});

			btAnnuler.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Window window = SwingUtilities.windowForComponent((Component)e.getSource());
					window.dispose();

				}
			});
		}
}
