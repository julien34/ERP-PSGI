package achat;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelAchat extends JPanel 
{
	private static FenetrePrincipale framePrincipale;
	
	private JPanel wrap = new JPanel(new BorderLayout(10,10));
	private JPanel panelTexteTop = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
	private JPanel panelInputCenter = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
	private JPanel panelBouttonBottom = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
	private JTextField champsPrenom = new JTextField();
	private JTextField champsNom = new JTextField();
	private JTextField champsAdresse = new JTextField();
	private String message = "Pour ajouter un fournisseur, remplissez les champs suivants :";
	private JLabel labelTitre = new JLabel(message);
	private JLabel labelPrenom = new JLabel("Pr�nom");
	private JLabel labelNom = new JLabel("Nom");
	private JLabel labelAdresse = new JLabel("Adresse");
	private JButton btnValider = new JButton("Valider");
	private String nomFournisseur, prenomFournisseur, adresseFournisseur, numSiretEntreprise;
	
	public PanelAchat(FenetrePrincipale framePrincipale)
	{
		this.framePrincipale = framePrincipale;
		initElements();
		initHandlers();
	}

	private void initElements() 
	{
		add(wrap);
		wrap.add("North",panelTexteTop);
		wrap.add("Center",panelInputCenter);
		wrap.add("South",panelBouttonBottom);
		panelTexteTop.add("North",labelTitre);
		panelInputCenter.add(labelPrenom);		
		panelInputCenter.add(champsPrenom);
		champsPrenom.setColumns(10);
		panelInputCenter.add(labelNom);
		panelInputCenter.add(champsNom);
		champsNom.setColumns(10);		
		panelInputCenter.add(labelAdresse);		
		panelInputCenter.add(champsAdresse);
		champsAdresse.setColumns(10);
		panelBouttonBottom.add("South",btnValider);
	}

	private void initHandlers() 
	{
		//Handler de validation d'ajout de fournisseur
		btnValider.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				recupereDonnees();
				if(DatabaseConnection.requete("CALL ajoutFournisseurs('"+nomFournisseur+"','"+prenomFournisseur+"','"+adresseFournisseur+"')")){
					effacerDonnees();
					afficherMessageOK();
				}
			}
		});
	}
	
	//R�cup�re les champs
	private void recupereDonnees()
	{
		this.nomFournisseur = champsNom.getText();
		this.prenomFournisseur = champsPrenom.getText();
		this.adresseFournisseur = champsAdresse.getText();
	}
		
	//remet les champs �z�ro
	private void effacerDonnees()
	{
		this.nomFournisseur = "";
		this.champsPrenom.setText("");
		this.prenomFournisseur = "";
		this.champsNom.setText("");
		this.adresseFournisseur = "";
		this.champsAdresse.setText("");
	}
		
	private void afficherMessageOK() 
	{
		this.message = "Fournisseur ajout� dans la base de donn�es. \nPour ajouter � nouveau un fournisseur, remplissez les champs suivants.";
		labelTitre.repaint();
	}
}