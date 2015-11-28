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
	private String value1,value2,value3,value4,value5;
	private int codeProduit;
	
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
	public ModifierProduits(String val1, String val2, String val3, String val4, String val5)
	{
		setTitle("Modifier un produit");
		setModal(true);
		value1 = val1;
		value1 = notNull(value1);
		
		value2 = val2;
		value2 = notNull(value2);
		
		value3 = val3;
		value3 = notNull(value3);
		
		value4 = val4;
		value4 = notNull(value4);
		
		value5 = val5;
		value5 = notNull(value5);
		
		codeProduit = DatabaseConnection.getCodeProduit(value1,value2,value3,value4,value5);
		
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
		JLabel descriptionLabel = new JLabel("nom ");
		JTextField description = new JTextField(value1,10);
		
		JLabel categorieLabel = new JLabel("categorie ");
		JTextField categorie = new JTextField(value2,10);
		
		JLabel prixVenteLabel = new JLabel("prix de vente ");
		JTextField prixVente = new JTextField(value3,10);
		
		JLabel prixAchatLabel = new JLabel("prix d'achat ");
		JTextField prixAchat = new JTextField(value4,10);
		
		JLabel udmLabel = new JLabel("unité de mesure ");
		JTextField udm = new JTextField(value5,10);
		
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

		panelFlow1.add(descriptionLabel);
		panelFlow1.add(description);

		panelFlow1.add(categorieLabel);
		panelFlow1.add(categorie);

		panelFlow2.add(prixVenteLabel);
		panelFlow2.add(prixVente);
		
		panelFlow2.add(prixAchatLabel);
		panelFlow2.add(prixAchat);

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
				String value1 = description.getText();
				String value2 = categorie.getText();
				String value3 = prixVente.getText();
				String value4 = prixAchat.getText();
				String value5 = udm.getText();
				
				//Si la requete à réussie
				if(DatabaseConnection.requete("UPDATE PRODUITS SET description = '"+value1+"', categorie = '"+value2+"', prixVente = "+Float.parseFloat(value3)+", prixAchat = "+Float.parseFloat(value4)+", udm = "+Float.parseFloat(value5)+" WHERE codeProduit = "+codeProduit))
				{
					FenetrePrincipale.getPanelProduits().raffraichirLigne(value1,value2,value3,value4,value5);
					JOptionPane.showMessageDialog(null, "Produit modifié avec succès.", "Modification de produit", JOptionPane.INFORMATION_MESSAGE);
				}
				else JOptionPane.showMessageDialog(null, "Erreur de modification du produit. Vérifiez vos champs.", "Modification de produit", JOptionPane.WARNING_MESSAGE);
			}
		});
		retour.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});

		//Afficher la fenétre
		setVisible(true);
	}
}