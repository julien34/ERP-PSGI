package vente;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import principal.FenetrePrincipale;

/**
 * nouveau commentaire
 * @author Simon
 * Classes qui genere l'interface 
 */
public class InterfaceClients extends JDialog implements ActionListener{
	
	/**
	 * 
	 */
	
	private static InterfaceClients frameClient;
	
	/**
	 * GridLayout qui contine les informations des Clients
	 */
	Object[][] data ={
			  {"1","Jean","Sairien","10 rue de","jean@g.com","06 06 06 06 06"},
			  {"2","Harry","Cover","10 rue de","harry@g.fr","06 06 06 06 06"},
			  {"3","Boulet","Bill","10 rue de","boulet@g.net","06 06 06 06 06"},
			  {"4","Gandalf","LeGirs","10 rue de","gandalt@hob.modor","06 06 06 06 06"}
	  };
	String title[] = { "Id","Nom","Prenom","Adresse","Email","Telehpone"};
	JTable tableau = new JTable(data,title);
	
	/**
	 * boutons du menu
	 * fonction ajouter, sauvegarder, supprimer
	 */
	private JButton bt_ajouter = new JButton("New");
	private JButton bt_sauvegarder = new JButton("Save");
	private JButton bt_supprimer = new JButton("Delete");
	
	/**
	 * elements dans la page d'ajout de nouveaux clients
	 * Libeles du nom, prenom, email, tel
	 */
	private JLabel lbl_nom = new JLabel("Nom");
	private JLabel lbl_prenom = new JLabel("Prenom");
	private JLabel lbl_adresse = new JLabel("Adresse");
	private JLabel lbl_email = new JLabel("Email");
	private JLabel lbl_tel = new JLabel("Telephone");
	
	/**
	 * text box pour le nom, prenom, adresse, email, telephone du nouveau client
	 */
	private JTextField txt_nom = new JTextField("");
	private JTextField txt_prenom = new JTextField("");
	private JTextField txt_adresse = new JTextField("");
	private JTextField txt_email = new JTextField("");
	private JTextField txt_tel = new JTextField("");
	
	/**
	 * Bouton de validation et bouton d'annulation de la saisie d'un nouveau client
	 */
	private JButton bt_valide = new JButton("Valider");
	private JButton bt_annuler = new JButton("Annuler");
	
	/**
	 * libelle et bouton de la fenetre d'information de la sauvegarde
	 */
	private JLabel lbl_save = new JLabel ("Sauvegarde reussie !");
	private JButton bt_OK = new JButton ("OK");
	
	
	public InterfaceClients(FenetrePrincipale frame){
		super(frame,"Interface clients",true);
		init();
	}
	
	private void init() {
		JPanel contenu = new JPanel(new GridLayout(2,1));
		JPanel menu = new JPanel();
		JPanel subMenu = new JPanel();
		JPanel gridClient = new JPanel(new GridLayout(1,5));
		
		getContentPane().add(contenu);
		contenu.add(menu);
		contenu.add(gridClient);
		
		//Menu bar
		menu.setLayout(new BorderLayout());
		subMenu.add(BorderLayout.NORTH, bt_ajouter);
		bt_ajouter.addActionListener(this);
		subMenu.add(BorderLayout.NORTH, bt_sauvegarder);
		bt_sauvegarder.addActionListener(this);
		subMenu.add(BorderLayout.NORTH, bt_supprimer);
		menu.add(subMenu, BorderLayout.WEST);
		
		//GridClient
		  gridClient.add(new JScrollPane(tableau));
		  
		setSize(500,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * @pram event
	 * methode qui s'active lors que l'utilisateur effectue une action
	 */

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == bt_ajouter){
			fenetreAjoutClient();
		}
		if(event.getSource() == bt_sauvegarder){
			sauvegarder();
		}
	}
	
	/**
	 * Methode qui renvoie la fenetre de formulaire d'ajout d'un nouveau client
	 */
	public void fenetreAjoutClient (){
		   JFrame ajClient = new JFrame("Ajouter un client" );
		   JPanel contenuAJC = new JPanel(new GridLayout(6,2));
		   ajClient.add(contenuAJC);
		   
		   ajClient.setSize(500,300 );
		   ajClient.setLocationRelativeTo( null );
		   ajClient.setVisible( true );
		   
		   contenuAJC.add(lbl_nom);
		   contenuAJC.add(txt_nom);
		   
		   contenuAJC.add(lbl_prenom);
		   contenuAJC.add(txt_prenom);
		   
		   contenuAJC.add(lbl_adresse);
		   contenuAJC.add(txt_adresse);
		   
		   contenuAJC.add(lbl_email);
		   contenuAJC.add(txt_email);
		   
		   contenuAJC.add(lbl_tel);
		   contenuAJC.add(txt_tel);
		   
		   contenuAJC.add(bt_valide);
		   contenuAJC.add(bt_annuler);
		   bt_annuler.addActionListener(e -> ajClient.setVisible(false)); 
	}
	
	/**
	 * Methode qui sauvegarde dans la base de donnée les clients de la liste
	 * affiche une fenetre de dialogue qui informe que l'enregistrement c'est bien executer
	 */
	private void sauvegarder(){
		//code qui sauvegarde
		JFrame infoSauvegarde = new JFrame ("Sauvegarde");
		JPanel contenuSave = new JPanel (new GridLayout(3,3));
		infoSauvegarde.setSize(500,300 );
		infoSauvegarde.setLocationRelativeTo( null );
		infoSauvegarde.setVisible( true );
		infoSauvegarde.add(contenuSave);
		contenuSave.add(lbl_save);
		contenuSave.add(bt_OK);
		bt_OK.addActionListener(e -> infoSauvegarde.setVisible(false));
	}
	
	/**
	 * @param args
	 * affichage de la fenetre graphique de l'interface client
	 */
	/*public static void main(String[] args){
		InterfaceClients fenetreClient = new InterfaceClients();
		fenetreClient.setTitle("Gestion des clients");//Nom de la fenetre
		fenetreClient.setSize(500,500);//taille de la fenetre Clients
		fenetreClient.setLocation(400,400);
		fenetreClient.setLocationRelativeTo(null);//centre au milieu de l'ecran
		fenetreClient.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//Ferme la fenetre Clients mais pas l'application
		fenetreClient.setVisible(true); //rend visible la fenetre Clients
	}*/
}
