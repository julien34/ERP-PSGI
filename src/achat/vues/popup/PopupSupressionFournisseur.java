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
import achat.modeles.Fournisseur;
import achat.vues.PanelFournisseur;

public class PopupSupressionFournisseur extends JDialog{

	private Fournisseur fournisseur;
	private JButton btnOui, btnNon;
	
	public PopupSupressionFournisseur(Fournisseur f){
		this.fournisseur = f;
		
		this.initElement();//On initie les Ã©lÃ©ments sur les JPanels
		this.initFenetre();//On crÃ©er la fenetre
		this.initEcouteurs();//On ajoute les Ã©couteurs
		
	}

	/**
	 * MÃ©thode qui initialise la fenetre
	 */
	private void initFenetre() {
		this.setTitle("Suprression d'un fournisseur");
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
		
		//On crÃ©er les panels
		JPanel panelSupressionFournisseur = new JPanel(new GridLayout(3,1));
		JPanel panelEspaceHaut = new JPanel();
		JPanel panelTitre = new JPanel();
		JPanel panelBoutons = new JPanel();
		
		//On crÃ©er les composants 
		JLabel lblTitre = new JLabel("Voulez-vous supprimer le fournisseur "+this.fournisseur.getNom()+" ?");
		 this.btnOui = new JButton("Oui");
		 this.btnNon = new JButton("Non");
		
		//On ajoute les composant aux panels
		panelTitre.add(lblTitre);
		panelBoutons.add(btnOui);
		panelBoutons.add(btnNon);
		
		//On ajoute les panel au panelSuppression (qui est en grid layout)
		panelSupressionFournisseur.add(panelEspaceHaut);
		panelSupressionFournisseur.add(panelTitre);
		panelSupressionFournisseur.add(panelBoutons);
		
		
		//On ajoute le panelSupression Ã  la frame principale (de type JDialog)
		this.add(panelSupressionFournisseur);
	}
	
	
	/**
	 * MÃ©thode qui initialise les Ã©couteurs 
	 */
	public void initEcouteurs(){
		
		//Bouton "OUI"
		this.btnOui.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection cn = DatabaseConnection.getCon();
				
				try {
					PreparedStatement pst = cn.prepareStatement("DELETE FROM Fournisseurs WHERE refFournisseur = ?");
					pst.setString(1, PopupSupressionFournisseur.this.fournisseur.getRef());
					pst.executeQuery();
					PanelFournisseur.majTableauSuppr(PopupSupressionFournisseur.this.fournisseur);
					PopupSupressionFournisseur.this.dispose();//On ferme la fenÃªtre
					JOptionPane.showMessageDialog(null, "Fournisseur supprimé avec succès","Vous venez de supprimer le fournisseur "+PopupSupressionFournisseur.this.fournisseur.getRef()+".",JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,"Ce fournisseur est assigné à une ou plusieurs commande(s).","Erreur",JOptionPane.ERROR_MESSAGE);

					
				}

				
			}
		});
		
		
		//Bouton "NON"
		this.btnNon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupSupressionFournisseur.this.dispose();
				
			}
		});
	}
}
