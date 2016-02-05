package vente;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class AjouterCommande extends JDialog{
	
	private static FenetrePrincipale frame;
	
	JPanel contenuPanel = new JPanel(new GridLayout(5,1));
	JPanel panelFlow1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelFlow2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelFlow3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelFlow4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelFlow5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	 JLabel lbl_numProd = new JLabel("Numero produit :");
	 JLabel lbl_nomProd = new JLabel("Nom produit :");
	 JLabel lbl_quantite = new JLabel("Quantite");

	
	 JTextField txt_numProd = new JTextField(10);
	 JTextField txt_nomProd = new JTextField(10);
	 JTextField txt_quantite = new JTextField(10);
	 
	 private JButton bt_valider = new JButton("Valider");
		private JButton bt_annuler = new JButton("Annuler");
		
		public AjouterCommande(FenetrePrincipale frame){
			super(frame,"Ajout d'un produit a la commande");
			this.frame = frame;
			
			initFrame();
			initElement();
			initHandlers();
		}

		public void initFrame(){
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setSize(512,288);	
			setLocationRelativeTo(null);
			setResizable(false);
			setVisible(true);
		}
		
		public void initElement(){
			add(contenuPanel);
			contenuPanel.add(panelFlow1);
			contenuPanel.add(panelFlow2);
			contenuPanel.add(panelFlow3);
			contenuPanel.add(panelFlow4);
			contenuPanel.add(panelFlow5);
			panelFlow1.add(lbl_numProd);
			panelFlow1.add(txt_numProd);
			panelFlow1.add(lbl_nomProd);
			panelFlow1.add(txt_nomProd);
			panelFlow2.add(lbl_quantite);
			panelFlow2.add(txt_quantite);

			panelFlow5.add(bt_valider);
			panelFlow5.add(bt_annuler);
			setVisible(true);
		}
		
		public void  initHandlers(){
			
			bt_valider.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
			String numProd = txt_numProd.getText();
			String nom = txt_nomProd.getText();
			String quantite = txt_quantite.getText();
			
			
			if(DatabaseConnection.requete("CALL procedure('"+numProd+"',"+nom+",'"+quantite+"')"))
			{
				FenetrePrincipale.getInterfaceDevis().raffraichirListe(numProd,nom,quantite);
				JOptionPane.showMessageDialog(null, "Produit ajout� avec succ�s.", "Ajout de produit", JOptionPane.INFORMATION_MESSAGE);
			}
			else JOptionPane.showMessageDialog(null, "Erreur d'ajout du produit. V�rifiez vos variables.", "Ajout de produit", JOptionPane.WARNING_MESSAGE);
		}
	});
			bt_annuler.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
			
		}
		/**
		 * Méthode qui change le fournisseur avec celui sélectionné dans la liste.
		 * @param f, le Fournisseur à changer.
		 */
		/*public static void getClient(Client cli){
			clienCommande = cli;
			txtRechercheClient.setText(cli.idclient + " " + cli.nomclient + " " + cli.prenomclient);
		}*/
}
