package achat.vues.popup.popTVA;

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

import achat.modeles.TVA;
import achat.vues.PanelTVA;
import jdbc.DatabaseConnection;

public class PopupModifTVA extends JDialog{
	
	private static final long serialVersionUID = 1L;
	private TVA tva;
	private int indice;
	
	private JButton btValider, btAnnuler;
	private JTextField txtNomTVA, txtTauxTVA;

	public PopupModifTVA(TVA t, int i){
		this.tva = t;
		this.indice = i;
		this.initElements();
		this.initFenetre();
		this.initEcouteurs();
	}


	/**
	 * Méthode qui crée une nouvelle fenetre avec des caractéristiques.
	 */
	private void initFenetre() {
		this.setTitle("Modifier une TVA");
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
		
		JPanel panelModifTVA = new JPanel(new GridLayout(6,1,5,5));
		JPanel panelNomTVA = new JPanel();
		JPanel panelTauxTVA = new JPanel();
		JPanel panelBoutons = new JPanel();

		JLabel lblNomTVA = new JLabel("Nom TVA : ");
		JLabel lblTauxTVA = new JLabel("Taux : ");

		this.txtNomTVA = new JTextField(10);
		this.txtTauxTVA = new JTextField(10);

		this.btValider = new JButton("Valider");
		this.btAnnuler = new JButton("Annuler");


		//On ajoute tout les components au panel :
		panelNomTVA.add(lblNomTVA);
		panelNomTVA.add(this.txtNomTVA);

		panelTauxTVA.add(lblTauxTVA);
		panelTauxTVA.add(this.txtTauxTVA);
		panelBoutons.add(this.btValider);
		panelBoutons.add(this.btAnnuler);

		panelModifTVA.add(panelNomTVA);
		panelModifTVA.add(panelTauxTVA);
		panelModifTVA.add(panelBoutons);

		this.add(panelModifTVA);

		this.txtNomTVA.setText(PopupModifTVA.this.tva.getNomTVA());
		this.txtTauxTVA.setText(Integer.toString((int)(PopupModifTVA.this.tva.getTauxTVA())));
		this.add(panelModifTVA);
	}
	
	
		
	/**
	 * Méthode qui initialise les écouteurs des boutons.
	 */
	private void initEcouteurs() {
		
		this.btValider.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Connection cn = DatabaseConnection.getCon();
				PreparedStatement pst;

				try {
					pst = cn.prepareStatement("UPDATE TVA SET nomTVA = ?, tauxTVA = ? WHERE refTVA = ?");
					pst.setString(1, PopupModifTVA.this.txtNomTVA.getText());
					pst.setString(2, PopupModifTVA.this.txtTauxTVA.getText());
					pst.setString(3, PopupModifTVA.this.tva.getRefTVA());
					pst.executeQuery();

					PopupModifTVA.this.tva.setNomTVA(txtNomTVA.getText());
					PopupModifTVA.this.tva.setTauxTVA(Double.parseDouble(txtTauxTVA.getText()));

					PanelTVA.setTableau(PopupModifTVA.this.tva,PopupModifTVA.this.indice);

					Window window = SwingUtilities.windowForComponent((Component)e.getSource());
					window.dispose();
					JOptionPane.showMessageDialog(null, "TVA modifiée avec succès.", "Modification de la TVA", JOptionPane.INFORMATION_MESSAGE);
					PanelTVA.setBt(false);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Erreur dans la saisie des informations", "Modification de la TVA", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}	
			}
		});

		
		this.btAnnuler.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Window window = SwingUtilities.windowForComponent((Component)e.getSource());
				window.dispose();
			}
		});
	}
}
