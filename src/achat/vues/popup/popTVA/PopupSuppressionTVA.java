package achat.vues.popup.popTVA;

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

import achat.modeles.TVA;
import achat.vues.PanelTVA;
import jdbc.DatabaseConnection;

public class PopupSuppressionTVA extends JDialog {

	private static final long serialVersionUID = 1L;
	private TVA tva;
	private JButton btOui, btNon;
	
	public PopupSuppressionTVA(TVA tva){
		this.tva = tva;
		this.initElement();
		this.initFenetre();
		this.initEcouteurs();	
	}

	/**
	 * Méthode qui initialise la fenetre
	 */
	private void initFenetre() {
		this.setTitle("Suppression d'une TVA");
		this.setResizable(false);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * MÃ©thode qui initialise les Ã©lÃ©ment sur les JPanels
	 */
	private void initElement() {
		JPanel panelSuppressionTVA = new JPanel(new GridLayout(3,1));
		JPanel panelEspaceHaut = new JPanel();
		JPanel panelTitre = new JPanel();
		JPanel panelBoutons = new JPanel();

		JLabel lblTitre = new JLabel("Voulez-vous supprimer la TVA "+this.tva.getNomTVA()+" ?");
		 this.btOui = new JButton("Oui");
		 this.btNon = new JButton("Non");
		
		panelTitre.add(lblTitre);
		panelBoutons.add(btOui);
		panelBoutons.add(btNon);
		panelSuppressionTVA.add(panelEspaceHaut);
		panelSuppressionTVA.add(panelTitre);
		panelSuppressionTVA.add(panelBoutons);
		
		this.add(panelSuppressionTVA);
	}
	
	
	/**
	 * MÃ©thode qui initialise les Ã©couteurs 
	 */
	public void initEcouteurs(){

		this.btOui.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection cn = DatabaseConnection.getCon();
				
				try {
					PreparedStatement pst = cn.prepareStatement("DELETE FROM TVA WHERE refTVA = ?");
					pst.setString(1, PopupSuppressionTVA.this.tva.getRefTVA());
					pst.executeQuery();
					PanelTVA.majTableauSuppr(PopupSuppressionTVA.this.tva);
					PopupSuppressionTVA.this.dispose();
					JOptionPane.showMessageDialog(null, "TVA supprimée avec succès","Vous venez de supprimer la TVA "+PopupSuppressionTVA.this.tva.getRefTVA()+".",JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,"Cette TVA est assignée à une ou plusieurs commande(s).","Erreur",JOptionPane.ERROR_MESSAGE);
				}

				
			}
		});
		
		this.btNon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupSuppressionTVA.this.dispose();
			}
		});
	}
}