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
import javax.swing.SwingUtilities;

import jdbc.DatabaseConnection;
import achat.Fournisseur;
import achat.PanelFournisseur;

public class PopupSupressionFournisseur extends JDialog{

	private Fournisseur fournisseur;
	private int indice;
	private JButton btnOui, btnNon;
	
	public PopupSupressionFournisseur(Fournisseur f, int position){
		this.fournisseur = f;
		this.indice = position;
		
		this.initElement();//On initie les éléments sur les JPanels
		this.initFenetre();//On créer la fenetre
		this.initEcouteurs();//On ajoute les écouteurs
		
	}

	/**
	 * Méthode qui initialise la fenetre
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
	 * Méthode qui initialise les élément sur les JPanels
	 */
	private void initElement() {
		
		//On créer les panels
		JPanel panelSupressionFournisseur = new JPanel(new GridLayout(3,1));
		JPanel panelEspaceHaut = new JPanel();
		JPanel panelTitre = new JPanel();
		JPanel panelBoutons = new JPanel();
		
		//On créer les composants 
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
		
		
		//On ajoute le panelSupression à la frame principale (de type JDialog)
		this.add(panelSupressionFournisseur);
	}
	
	
	/**
	 * Méthode qui initialise les écouteurs 
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
					PopupSupressionFournisseur.this.dispose();//On ferme la fenêtre
					JOptionPane.showMessageDialog(null, "Fournisseur supprimé avec succès","Vous venez de supprimer le fournisseur "+PopupSupressionFournisseur.this.fournisseur.getRef()+".",JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e1) {
					e1.printStackTrace();
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
