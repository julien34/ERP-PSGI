package vente;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class ModifierClients extends JPanel
{	
	private JPanel panelGrid = new JPanel(new GridLayout(5,1));
	private JPanel panelFlow1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private String value1,value2,value3,value4,value5,value6,value7;
	private String codeClient;
	JButton retour = new JButton("Retour");	
	
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
	
	public void setVal(String val1, String val2, String val3, String val4, String val5, String val6)
	{
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
		
		value6 = val6;
		value6 = notNull(value6);
		
	
		
		codeClient = DatabaseConnection.getCodeClient(value1, value2, value3, value4, value5, value6);
	}
	
	/**
	 * Le constructeur par défaut fait appel à la fonction init
	 */
	public ModifierClients()
	{		
		initElements();
	}	
	
	public JButton getRetour()
	{
		return retour;
	}
	
	public void initElements()
	{
		//Création des boutons		
		JLabel lbl_Nom = new JLabel("Nom");
		JTextField Nom = new JTextField(value1,10);
		
		JLabel lbl_Prenom = new JLabel("Prenom");
		JTextField Prenom = new JTextField(value2,10);
		
		JLabel lbl_Adresse = new JLabel("Adresse");
		JTextField Adresse = new JTextField(value3,10);
		
		JLabel lbl_Email = new JLabel("Email");
		JTextField Email = new JTextField(value4,10);
		
		JLabel lbl_Telephone = new JLabel("Telephone");
		JTextField Telephone = new JTextField(value5,10);
		
		JLabel lbl_Categorie = new JLabel("Categorie");
		JTextField Categorie = new JTextField(value6,10);
		
		
		JButton modifier = new JButton("Modifier");	
		JLabel error = new JLabel("");	
		
		//Ajouter les élements	
		add(panelGrid);
		panelGrid.add(panelFlow1);
		panelGrid.add(panelFlow2);
		panelGrid.add(panelFlow3);
		panelGrid.add(panelFlow4);
		panelGrid.add(panelFlow5);

		panelFlow1.add(lbl_Nom);
		panelFlow1.add(Nom);

		panelFlow1.add(lbl_Prenom);
		panelFlow1.add(Prenom);

		panelFlow2.add(lbl_Adresse);
		panelFlow2.add(Adresse);
		
		panelFlow2.add(lbl_Email);
		panelFlow2.add(Email);

		panelFlow3.add(lbl_Telephone);
		panelFlow3.add(Telephone);
		
		panelFlow3.add(lbl_Categorie);
		panelFlow3.add(Categorie);
		
		panelFlow4.add(modifier);
		panelFlow4.add(retour);		
		panelFlow5.add(error);
		
		//Action si click sur bouton
		modifier.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String value1 = Nom.getText();
				String value2 = Prenom.getText();
				String value3 = Adresse.getText();
				String value4 = Email.getText();
				String value5 = Telephone.getText();
				String value6 = Categorie.getText();
				
				//Si la requete à réussie
				if(DatabaseConnection.requete("UPDATE vente_clients SET NOMCLIENT = '"+value1+"' AND PRENOMCLIENT = '"+value2+"' AND ADRESSECLIENT = "+(value3)+" AND EMAILCLIENT = "+(value4)+" AND TELCLIENT = "+(value5)+"AND CODECATEG = " +(value6)))
				{
					FenetrePrincipale.getPanelClient().raffraichirLigne(value1,value2,value3,value4,value5);
					JOptionPane.showMessageDialog(null, "Produit modifié avec succès.", "Modification de produit", JOptionPane.INFORMATION_MESSAGE);
				}
				else JOptionPane.showMessageDialog(null, "Erreur de modification du produit. Vérifiez vos champs.", "Modification de produit", JOptionPane.WARNING_MESSAGE);
			}
		});
	}
}