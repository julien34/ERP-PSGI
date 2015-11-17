package vente;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import principal.FenetrePrincipale;

public class InterfaceDevis extends JDialog implements ActionListener{

	private InterfaceDevis frameVente;
	
	
	private JMenuBar menuBar = new JMenuBar();
	private JButton  fileMenu = new JButton ("Accueil");
	private JButton bt_enregistrer = new JButton("Enregistrer");
	private JButton Imprimer = new JButton("Imprimer");
	private JButton Ajouter = new JButton("Ajouter");
	private JButton Retirer = new JButton("Retirer");
	private JButton Ouvrir = new JButton("Ouvrir");
	//private JButton bt_enregistrer = new JButton("Enr");
	  
	  private JLabel Bon = new JLabel("Bon de Commande : ");
	  private JTextField ChampTextBon = new JTextField("");

	  private JLabel Client = new JLabel("Client : ");
	  private JScrollPane ChampTextClient = new JScrollPane();

	  private JLabel Code = new JLabel("Code : ");
	  private JTextField ChampTextCode = new JTextField("");

	  
	  private JLabel Date = new JLabel("Date : ");
	  private JTextField ChampTextDate= new JTextField("");

	  
	  private JLabel Montant = new JLabel("Montant : ");
	  private JTextField ChampTextMontant = new JTextField("");
	  
		private JLabel lbl_save = new JLabel ("Enregistrement reussi !");
		private JButton bt_OK = new JButton ("OK");
	  
	  Object[][] data ={
			  {1,1,1,1}
			 
	  };
	  
     String title[] = { "Ref","Designation","Quantité","PrixUnitaire"};
	  
	  JTable tableau = new JTable(data,title);
	  
	  
	  //Initialisation de la fenetre vente
	  public InterfaceDevis(FenetrePrincipale frame){
		  super(frame,"Interface devis",true);
		  initUI();		
	  }
    public void initUI() {
    	
   
    	setSize(500,400); // taille de la fenetre
    	setLocation(300,400); // position de la fenetre
    	
        final JFrame frame = new JFrame("Gestion de Commandes");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

     
        menuBar.add(fileMenu);
        menuBar.add(bt_enregistrer);
        bt_enregistrer.addActionListener(this);
        menuBar.add(Imprimer);
        menuBar.add(Ajouter);
        menuBar.add(Retirer);
        menuBar.add(Ouvrir);
        
         setJMenuBar(menuBar);
        JPanel FenetrePrincipal = new JPanel(new GridLayout(3, 1));
        JPanel FenetreHaut = new JPanel(new GridLayout(4,1,1,10 ));
        JPanel FenetreMilieu = new JPanel(new GridLayout(1, 1));
        JPanel FenetreBas = new JPanel(new GridLayout(3, 2));
             
        getContentPane().add(FenetrePrincipal);
  	    
        FenetrePrincipal.add(FenetreHaut);
        FenetrePrincipal.add(FenetreMilieu);
        FenetrePrincipal.add(FenetreBas);
        
        FenetreHaut.add(Bon);
        FenetreHaut.add(ChampTextBon);
  	    
        FenetreHaut.add(Client);
        FenetreHaut.add(ChampTextClient);
	    
        
        FenetreHaut.add(Code);
        FenetreHaut.add(ChampTextCode);
	    
        FenetreHaut.add(Date);
        FenetreHaut.add(ChampTextDate);
	    
       FenetreMilieu.add(new JScrollPane(tableau)); // affichage du tableau de commande
        
        
        FenetreBas.add(Montant);
        FenetreBas.add(ChampTextMontant);
	    
	  // FenetreMilieu.add(new JScrollPane(tableau)); // affichage du tableau de commande
	    
	   
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
    }
	
	public void actionPerformed(ActionEvent event) {

		if(event.getSource() == bt_enregistrer){
			enregistrer();
		}
	}
	

	private void enregistrer(){
		//code qui sauvegarde
		JFrame enregistrer = new JFrame ("Enregistrement");
		JPanel contenuSave = new JPanel (new GridLayout(3,3));
		enregistrer.setSize(500,300 );
		enregistrer.setLocationRelativeTo( null );
		enregistrer.setVisible( true );
		enregistrer.add(contenuSave);
		contenuSave.add(lbl_save);
		contenuSave.add(bt_OK);
		bt_OK.addActionListener(e -> enregistrer.setVisible(false));
	}
    
    /*public static void main(String[] args) {
    	
    	
		JFrame frame = new InterfaceDevis();
		//Commande.add(new JScrollPane());
		//frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
		
        
    }*/
}