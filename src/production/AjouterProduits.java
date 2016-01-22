package production;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class AjouterProduits extends JPanel
{	
	private JPanel panelGrid = new JPanel(new GridLayout(5,1));
	private JPanel panelFlow1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JButton retour = new JButton("Retour");
	DefaultComboBoxModel<String> modelCategorie;
	
	/**
	 * Le constructeur par défaut fait appel à la fonction init
	 */
	public AjouterProduits()
	{
		initElements();
	}	
	
	public JButton getRetour()
	{
		return retour;
	}

    public void remplirCategorie()
    {
    	DatabaseConnection.getCategorieProd(modelCategorie);
    	modelCategorie.removeElement("");
    }
	
	/**
	 * Initialise la fenetre associée
	 */
	public void initElements()
	{
		//Création des boutons		
		JLabel descriptionLabel = new JLabel("nom ");
		JTextField description = new JTextField(10);

	    JLabel categorieLabel = new JLabel("categorie ");
	    modelCategorie = new DefaultComboBoxModel<String>();
	    JComboBox<String> categorie = new JComboBox<String>(modelCategorie);
		
		JLabel prixVenteLabel = new JLabel("prix de vente ");
		JTextField prixVente = new JTextField(10);

		JLabel prixAchatLabel = new JLabel("prix d'achat ");
		JTextField prixAchat = new JTextField(10);

		JLabel udmLabel = new JLabel("unité de mesure ");
		JTextField udm = new JTextField(10);

		JLabel achatventeLabel = new JLabel("achat / vente ");
		String[] items = {"Achat","Vente","AchatVente"};
		JComboBox<String> achatvente = new JComboBox<String>(items);

		JLabel required = new JLabel("<html><font color='red'>* </font>champs obligatoire</html> ");
		JButton ajouter = new JButton("Ajouter");
		
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
		panelFlow3.add(achatventeLabel);
		panelFlow3.add(achatvente);
		
		panelFlow4.add(required);
		panelFlow4.add(ajouter);
		panelFlow4.add(retour);		
		panelFlow5.add(error);
		
		//Action si click sur bouton
		ajouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String value1 = description.getText();
				int value2 = DatabaseConnection.determinerCodeCategorie(categorie.getSelectedItem().toString());
				String value3 = prixVente.getText();
				String value4 = prixAchat.getText();
				String value5 = udm.getText();
				String value6 = achatvente.getSelectedItem().toString();
				
				//Si la requete à réussie
				if(DatabaseConnection.requete("CALL nouveauProduit ('"+value1+"','"+value2+"','"+Float.parseFloat(value3)+"','"+Float.parseFloat(value4)+"','"+Integer.parseInt(value5)+"','"+value6+"')"))
				{
					FenetrePrincipale.getPanelProduits().raffraichirListe(value1,String.valueOf(value2),value3,value4,value5,value6);
					JOptionPane.showMessageDialog(null, "Produit ajouté avec succès.", "Ajout de produit", JOptionPane.INFORMATION_MESSAGE);
				}
				else JOptionPane.showMessageDialog(null, "Erreur d'ajout du produit. Vérifiez les champs.", "Ajout de produit", JOptionPane.WARNING_MESSAGE);
			}
		});
	}
}