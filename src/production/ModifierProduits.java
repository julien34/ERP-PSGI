package production;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class ModifierProduits extends JDialog
{	
	private JPanel panelGrid = new JPanel(new GridLayout(5,1));
	private JPanel panelFlow1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private String value1,value2,value3,value4,value5,value6;
	
	/**
	 * Rend un champ null égal a un string vide
	 * @param s
	 * @return
	 */
	public String notNull(String s)
	{
		if(s == "null")
		return "";
		else return s;
	}
	
	/**
	 * Le constructeur par défaut fait appel à la fonction init
	 */
	public ModifierProduits(String val1, String val2, String val3, String val4, String val5, String val6)
	{
		setTitle("Modifier un produit");
		setModal(true);
		value1 = val1;
		
		value2 = val2;
		value2 = notNull(value2);
		
		value3 = val3;
		value3 = notNull(value3);
		
		value4 = val4;
		value4 = notNull(value4);
		
		value5 = val5;
		value5 = notNull(value5);
		
		value6 = val6;
		value6 = notNull(value6);
		
		initFenetre();
		initElements();
	}	
	
	/**
	 * Initialise la fenetre associée
	 */
	public void initFenetre()
	{
		//Paramétrage de la fenêtre
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(512,288);	
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void initElements()
	{
		//Création des boutons
		JLabel codeProduitLabel = new JLabel("code produit ");
		JTextField codeProduit = new JTextField(value1,10);
		codeProduit.setEditable(false);
		
		JLabel descriptionLabel = new JLabel("description ");
		JTextField description = new JTextField(value2,10);
		
		JLabel categorieLabel = new JLabel("categorie ");
		JTextField categorie = new JTextField(value3,10);
		
		JLabel prixVenteLabel = new JLabel("prix de vente ");
		JTextField prixVente = new JTextField(value4,10);
		
		JLabel prixAchatLabel = new JLabel("prix d'achat ");
		JTextField prixAchat = new JTextField(value5,10);
		
		JLabel udmLabel = new JLabel("unité de mesure ");
		JTextField udm = new JTextField(value6,10);
		
		JButton modifier = new JButton("Modifier");
		JButton retour = new JButton("Retour");			
		JLabel error = new JLabel("");	
		
		//Ajouter les élements	
		add(panelGrid);
		panelGrid.add(panelFlow1);
		panelGrid.add(panelFlow2);
		panelGrid.add(panelFlow3);
		panelGrid.add(panelFlow4);
		panelGrid.add(panelFlow5);
		
		panelFlow1.add(codeProduitLabel);
		panelFlow1.add(codeProduit);

		panelFlow1.add(descriptionLabel);
		panelFlow1.add(description);

		panelFlow2.add(categorieLabel);
		panelFlow2.add(categorie);

		panelFlow2.add(prixVenteLabel);
		panelFlow2.add(prixVente);
		
		panelFlow3.add(prixAchatLabel);
		panelFlow3.add(prixAchat);

		panelFlow3.add(udmLabel);
		panelFlow3.add(udm);
		
		panelFlow4.add(modifier);
		panelFlow4.add(retour);		
		panelFlow5.add(error);
		
		//Action si click sur bouton
		modifier.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String value1 = codeProduit.getText();
				String value2 = description.getText();
				String value3 = categorie.getText();
				String value4 = prixVente.getText();
				String value5 = prixAchat.getText();
				String value6 = udm.getText();
				
				//Si la requete à réussie
				if(DatabaseConnection.requete("UPDATE PRODUITS SET description = '"+value2+"', categorie = '"+value3+"', prixVente = "+value4+", prixAchat = "+value5+", udm = "+value6+" WHERE codeProduit = "+value1) == true)
				{
					FenetrePrincipale.getPanelProduits().raffraichirLigne(value1,value2,value3,value4,value5,value6);
					JOptionPane.showMessageDialog(null, "Produit modifié avec succès.", "Modification de produit", JOptionPane.INFORMATION_MESSAGE);
				}
				else JOptionPane.showMessageDialog(null, "Erreur de modification du produit. Vérifiez vos variables.", "Modification de produit", JOptionPane.WARNING_MESSAGE);
			}
		});
		retour.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});

		//Afficher la fenêtre
		setVisible(true);
	}
}