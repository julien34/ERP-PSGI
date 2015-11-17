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
	private static FenetrePrincipale frame;
	JPanel panelGrid = new JPanel(new GridLayout(5,1));
	JPanel panelFlow1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelFlow2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelFlow3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelFlow4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelFlow5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	/**
	 * Le constructeur par défaut fait appel à la fonction init
	 */
	public AjouterProduits(FenetrePrincipale frame)
	{
		super(frame,"Ajouter un produit",true);
		this.frame = frame;
		initFenetre();
		initElements();
	}	
	
	/**
	 * Initialise la fenetre associée
	 */
	public void initFenetre()
	{
		//Paramétrage de la fenêtre
		setTitle("Ajouter un produit");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(512,288);	
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void initElements()
	{
		//Création des boutons
		JLabel codeProduitLabel = new JLabel("<html>code produit <font color='red'>*</html> ");
		JTextField codeProduit = new JTextField(10);
		
		JLabel descriptionLabel = new JLabel("description ");
		JTextField description = new JTextField(10);

		JLabel categorieLabel = new JLabel("categorie ");
		JTextField categorie = new JTextField(10);
		
		JLabel prixVenteLabel = new JLabel("prix de vente ");
		JTextField prixVente = new JTextField(10);

		JLabel prixAchatLabel = new JLabel("prix d'achat ");
		JTextField prixAchat = new JTextField(10);

		JLabel udmLabel = new JLabel("unité de mesure ");
		JTextField udm = new JTextField(10);

		JLabel required = new JLabel("<html><font color='red'>* </font>champs obligatoire</html> ");
		JButton ajouter = new JButton("Ajouter");
		JButton retour = new JButton("Retour");
		ajouter.setEnabled(false);
		
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
		
		panelFlow4.add(required);
		panelFlow4.add(ajouter);
		panelFlow4.add(retour);		
		panelFlow5.add(error);
		
		//Handler du champ clé primaire
		codeProduit.addKeyListener(new KeyAdapter() 
		{
	        public void keyReleased(KeyEvent e) 
	        {
	            super.keyReleased(e);
	            if(codeProduit.getText().length() > 0)
	            ajouter.setEnabled(true);
	            else ajouter.setEnabled(false);
	        }
	    });
		
		//Action si click sur bouton
		ajouter.addActionListener(new ActionListener()
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
				if(DatabaseConnection.requete("INSERT INTO PRODUITS(codeProduit,description,categorie,prixVente,prixAchat,udm) VALUES ("+value1+",'"+value2+"','"+value3+"',"+value4+","+value5+","+value6+")") == true)
				{
					frame.getPanelProduits().raffraichirListe(value1,value2,value3,value4,value5,value6);
					error.setText("<html><font color=lime>Ligne ajoutée !</html>");
				}
				else error.setText("<html><font color=red>Erreur d'ajout de ligne, vérifiez vos variables !</html>");
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