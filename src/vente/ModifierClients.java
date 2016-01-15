package vente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;
import vente.Client;

public class ModifierClients extends JDialog
{	
	private static FenetrePrincipale frame;
	private JPanel panelGrid = new JPanel(new GridLayout(5,1));
	private JPanel panelFlow1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panelFlow5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private String value1,value2,value3,value4,value5,value6,value7;
	private int codeClient;
	
	JButton retour = new JButton("Retour");	
	
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
	public ModifierClients(FenetrePrincipale frame, Client client)
	{	
		
		super(frame,"Modifier un client");
		this.frame = frame;
		initElements();
		initFrame();
		codeClient = Integer.parseInt(client.getIdClient());
		Nom.setText(client.getNomClient());
		Prenom.setText(client.getPrenomClient());
		Adresse.setText(client.getAdresseClient());
		Email.setText(client.getMailClient());
		Telephone.setText(client.getTelClient());
		Categorie.setText(client.getCategorie());
	}
	
	public void initFrame(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(512,288);	
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	public void initElements()
	{
		//Création des boutons
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
		
		retour.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		
		//Action si click sur bouton
				modifier.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						//Si la requete à réussie
						if(DatabaseConnection.requete("UPDATE vente_clients SET NOMCLIENT ='"+Nom.getText()+"', PRENOMCLIENT='"+Prenom.getText()+"', ADRESSECLIENT='"+Adresse.getText()+"', EMAILCLIENT='"+Email.getText()+"', TELCLIENT='"+Telephone.getText()+"', CODECATEGORIECLIENT='"+Integer.parseInt(Categorie.getText())+"' WHERE IDCLIENT='"+codeClient+"'"))
						{
							String idClient = Integer.toString(codeClient);
							FenetrePrincipale.getPanelClient().raffraichirLigne(idClient, Nom.getText(),Prenom.getText(),Adresse.getText(),Email.getText(),Telephone.getText(), Categorie.getText());
							JOptionPane.showMessageDialog(null, "Client modifié avec succès.", "Modification de Client", JOptionPane.INFORMATION_MESSAGE);
							setVisible(false);
						}
						else JOptionPane.showMessageDialog(null, "Erreur de modification du client. Vérifiez vos champs.", "Modification de client", JOptionPane.WARNING_MESSAGE);
					}
				});
	}
}