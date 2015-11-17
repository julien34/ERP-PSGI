package vente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.Normalizer.Form;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import principal.FenetrePrincipale;



public class FenetreVente extends JDialog{
	

	
	// Création des Objets 
	  private JLabel InformationClient = new JLabel("Information Client");
	  private JLabel ModePayement = new JLabel("Mode de Payement");
	  private JLabel Rien = new JLabel("");
	  private JLabel Nom = new JLabel("Nom : ");
	  private JTextField ChampTextNom = new JTextField("");
	  private JLabel Prenom = new JLabel("Prenom : ");
	  private JTextField ChampTextPrenom = new JTextField("");
	  private JLabel Adresse = new JLabel("Adresse : ");
	  private JTextField ChampTextAdresse = new JTextField("");
	  private JLabel Email = new JLabel("Email : ");
	  private JTextField ChampTextEmail = new JTextField("");
	  private JLabel NumeroTelephone = new JLabel("NumeroTelephone : ");
	  private JTextField ChampTextNumeroTelephone = new JTextField("");
	  private JCheckBox Carte = new JCheckBox("Carte");
	  private JCheckBox Cheque = new JCheckBox("Cheque");
	  private JCheckBox Espece = new JCheckBox("Espece");
	  private	JButton Valider = new JButton("Valider");	  
	  private	JButton Annuler = new JButton("Annuler");

	  //Création Tableau
	  Object[][] data ={
			  {"Cho7","Chausette","4","20","80"},
			  {"Dtf6","Dentifrice","1","6","6"},
			  {"Sho6r","Chaussure","1","50","50"},
			  {"VV2","VoitureV2","2","140000","280000"}
	  };
	  String title[] = { "Ref","Nom","Quantité","PrixUnitaire","PrixTotal"};
	  
	  JTable tableau = new JTable(data,title);
	  
	  
	  
	  public FenetreVente(FenetrePrincipale frame){
		  super(frame,"Fenetre vente",true);
		  initFenetre();
	  }
	  


void Envoyer(){
	//Quand boutton envoyer ecrire code
	
	
	System.exit(0);
}


	
public void initFenetre()
{
	setTitle("Vente"); //nom de la fenetre
	setSize(500,400); // taille de la fenetre
	setLocation(300,400); // position de la fenetre

	Annuler.addActionListener(frame -> System.exit(0)); // quand bouton annuler appuyé action
    Valider.addActionListener(frame -> Envoyer()); // quand bouton envoyé action
   
    Carte.setSelected(true);
    /*
    if(Carte.isSelected())
    {
    	Cheque.setSelected(false);
    	Espece.setSelected(false);
    }
    if(Cheque.isSelected())
    {
    	Carte.setSelected(false);
    	Espece.setSelected(false);
    }
    if(Espece.isSelected())
    {
    	Cheque.setSelected(false);
    	Carte.setSelected(false);
    }
    */
    
	JPanel FenetrePrincipal = new JPanel(new GridLayout(3,1,0,10)); // 3 lignes 1 colones 
	JPanel FenetreHaut = new JPanel(new GridLayout(1,2)); // 1 ligne 2 colones
	JPanel Formulaire = new JPanel(new GridLayout(6, 1));// 6 lignes 1 colone
	JPanel ModeDePayement = new JPanel(new GridLayout(5, 1));//5 lignes 1 colone
	JPanel Commande = new JPanel(new GridLayout(1,1));
	JPanel Buttons = new JPanel(new GridLayout(1,3));	//ajout par la droite
	
	getContentPane().add(FenetrePrincipal);	 // le getConten prend la fenetrePrincipal
	FenetrePrincipal.add(FenetreHaut); // FenetrePrincipal 1/1 prend fenetre haut
	FenetreHaut.add(Formulaire); //FenetreHaut prend formulaire  1/1
	
	//    */* position dans le tableau
	Formulaire.add(InformationClient); //  1/1
	Formulaire.add(Rien);// 1/1
	Formulaire.add(Nom);// 2/1
	Formulaire.add(ChampTextNom);// 2/1
	Formulaire.add(Prenom); // 3/1
	Formulaire.add(ChampTextPrenom); // 3/1
	Formulaire.add(Adresse); // 4/1
	Formulaire.add(ChampTextAdresse); // 4/1
	Formulaire.add(Email);// 5/1
	Formulaire.add(ChampTextEmail);// 5/1
	Formulaire.add(NumeroTelephone);// 6/1
	Formulaire.add(ChampTextNumeroTelephone);// 6/1
	

	FenetreHaut.add(ModeDePayement); // fenetre haut prend modedepayement 1/2
	ModeDePayement.add(ModePayement);// 1/1
	ModeDePayement.add(Carte);// 2/1
	ModeDePayement.add(Cheque);// 3/1
	ModeDePayement.add(Espece);// 4/1

	
	FenetrePrincipal.add(Commande);// fenetre principal prend Commande 2/1
	Commande.add(new JScrollPane(tableau)); // affichage du tableau de commande

	FenetrePrincipal.add(Buttons);// fenetre principal prend Buttons 3/1
	

	Buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
	Buttons.add(Valider);
	Buttons.add(Annuler);
	
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	setVisible(true);

}


	/*public static void main(String[] args){
		JFrame frame = new FenetreVente();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}*/
}