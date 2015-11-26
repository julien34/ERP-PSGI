package production;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class AjouterProduits extends JDialog
{	
	private JPanel panelGrid = new JPanel(new GridLayout(5,1));
	private JPanel panelFlow1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	/**
	 * Le constructeur par d�faut fait appel � la fonction init
	 */
	public AjouterProduits()
	{
		setTitle("Ajouter un produit");
		setModal(true);
		initFenetre();
		initElements();
	}	
	
	/**
	 * Initialise la fenetre associ�e
	 */
	public void initFenetre()
	{
		//Param�trage de la fen�tre
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(512,288);	
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void initElements()
	{
		//Cr�ation des boutons		
		JLabel descriptionLabel = new JLabel("nom ");
		JTextField description = new JTextField(10);

		JLabel categorieLabel = new JLabel("categorie ");
		JTextField categorie = new JTextField(10);
		
		JLabel prixVenteLabel = new JLabel("prix de vente ");
		JTextField prixVente = new JTextField(10);

		JLabel prixAchatLabel = new JLabel("prix d'achat ");
		JTextField prixAchat = new JTextField(10);

		JLabel udmLabel = new JLabel("unit� de mesure ");
		JTextField udm = new JTextField(10);

		JLabel required = new JLabel("<html><font color='red'>* </font>champs obligatoire</html> ");
		JButton ajouter = new JButton("Ajouter");
		JButton retour = new JButton("Retour");
		
		JLabel error = new JLabel("");
		
		//Ajouter les �lements	
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
				String value2 = categorie.getText();
				String value3 = prixVente.getText();
				String value4 = prixAchat.getText();
				String value5 = udm.getText();
				
				//Si la requete � r�ussie
				if(DatabaseConnection.requete("CALL nouveauProduit ('"+value1+"','"+value2+"',"+Float.parseFloat(value3)+","+Float.parseFloat(value4)+","+Float.parseFloat(value5)+")"))
				{
					FenetrePrincipale.getPanelProduits().raffraichirListe(value1,value2,value3,value4,value5);
					JOptionPane.showMessageDialog(null, "Produit ajout� avec succ�s.", "Ajout de produit", JOptionPane.INFORMATION_MESSAGE);
				}
				else JOptionPane.showMessageDialog(null, "Erreur d'ajout du produit. V�rifiez vos variables.", "Ajout de produit", JOptionPane.WARNING_MESSAGE);
			}
		});
		retour.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});

		//Afficher la fen�tre
		setVisible(true);
	}
}