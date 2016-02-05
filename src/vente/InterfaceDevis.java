package vente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class InterfaceDevis extends JPanel{

	//private InterfaceDevis frameVente;
	
	private static FenetrePrincipale framePrincipale;
	private ResultSet client;
	private Dimension dimensionBouttons = new Dimension(140,26);
	private JPanel contenu = new JPanel(new BorderLayout(10,10));
	
	private Dimension dimensionTable = new Dimension(540, 224);
	private JMenuBar menuBar = new JMenuBar();
	private JButton  fileMenu = new JButton ("Accueil");
	private JButton bt_enregistrer = new JButton("Enregistrer");
	private JButton Imprimer = new JButton("Imprimer");
	private JButton Ajouter = new JButton("Ajouter");
	private JButton Retirer = new JButton("Retirer");
	private JButton Ouvrir = new JButton("Ouvrir");
	//private JButton bt_enregistrer = new JButton("Enr");
	  private String query;
	  private JLabel Bon = new JLabel("Bon de Commande : ");
	  private JTextField ChampTextBon = new JTextField("");

	  private JLabel Client = new JLabel("Client : ");
	  private JComboBox<String> ChampTextClient = new JComboBox<String>();;
	  
	  private JScrollBar bar =new JScrollBar();
	  private JLabel Code = new JLabel("Code : ");
	  private JTextField ChampTextCode = new JTextField("");

	  
	  private JLabel Date = new JLabel("Date : ");
	  private JTextField ChampTextDate= new JTextField("");

	  
	  private JLabel Montant = new JLabel("Montant : ");
	  private JTextField ChampTextMontant = new JTextField("");
	  
		private JLabel lbl_save = new JLabel ("Enregistrement reussi !");
		private JButton bt_OK = new JButton ("OK");
		
		
	  
		private JTable tableDevis;
		private JScrollPane scrollPane;
		private final String[] Colonnes = {"description","quantit�","udm","prix"};
		private static DefaultTableModel modelTableDevis = new DefaultTableModel(0,4)
		{
			Class[] types = {String.class, Integer.class, Float.class, Float.class};
			
	        @Override
	        public Class<Integer> getColumnClass(int columnIndex) 
	        {
	            return Integer.class;
	        }
	        
	        @Override
	        public boolean isCellEditable(int row, int column)
	        {  
	            return false;  
	        }
		};
			/*  Object[][] data ={
			  {1,1,1,1}
			 
	  };
	  
     String title[] = { "Ref","Designation","Quantit�","PrixUnitaire"};
	  
	  JTable tableau = new JTable(data,title);*/
	  
		public void remplirtableDevis(){
			modelTableDevis.setDataVector(DatabaseConnection.remplirListeProduits(), Colonnes);
			tableDevis.getRowSorter().toggleSortOrder(0);
		}
		
	  //Initialisation de la fenetre devis
	  public InterfaceDevis(FenetrePrincipale framePrincipal){
	//	  super(frame,"Interface devis",true);
		  this.framePrincipale = framePrincipal;
		  initElements();
		  initHandlers();
	  }
	  
    public void initElements() {
    	
   
    	
        tableDevis = new JTable(modelTableDevis);
		tableDevis.setAutoCreateRowSorter(true); //permet de trier les colonnes
		tableDevis.getRowSorter().toggleSortOrder(0);
		tableDevis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(tableDevis);
		tableDevis.setPreferredScrollableViewportSize(dimensionTable);
	
		
		add(contenu);
        //menuBar.add(fileMenu);
        menuBar.add(bt_enregistrer);
        bt_enregistrer.setPreferredSize(dimensionBouttons);
        menuBar.add(Imprimer);
        menuBar.add(Ajouter);
        menuBar.add(Retirer);
        menuBar.add(Ouvrir);
        
        contenu.add(menuBar, BorderLayout.NORTH);
        JPanel FenetrePrincipal1 = new JPanel(new GridLayout(3, 1));
        JPanel FenetreHaut = new JPanel(new GridLayout(4,1,1,10 ));
        JPanel FenetreMilieu = new JPanel(new GridLayout(1, 1));
        JPanel FenetreBas = new JPanel(new GridLayout(3, 2));
             
        contenu.add(FenetrePrincipal1);
  	    
        FenetrePrincipal1.add(FenetreHaut);
        FenetrePrincipal1.add(FenetreMilieu);
        FenetrePrincipal1.add(FenetreBas);
        
        FenetreHaut.add(Bon);
        FenetreHaut.add(ChampTextBon);
  	    
        FenetreHaut.add(Client);
        FenetreHaut.add(ChampTextClient);
	    
        
        FenetreHaut.add(Code);
        FenetreHaut.add(ChampTextCode);
	    
        FenetreHaut.add(Date);
        FenetreHaut.add(ChampTextDate);
	    
      // FenetreMilieu.add(new JScrollPane(tableau)); // affichage du tableau de commande
        
        
        FenetreBas.add(Montant);
        FenetreBas.add(ChampTextMontant);
	    
	   FenetreMilieu.add(tableDevis); // affichage du tableau de commande
	    
	   
   
	 // ChampTextClient.addItem(DatabaseConnection.Combo());
    }
	

public void initHandlers(){
	
	bt_enregistrer.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			JFrame enregistrer = new JFrame ("Enregistrement");
			JPanel contenuSave = new JPanel (new GridLayout(3,3));
			enregistrer.setSize(500,300 );
			enregistrer.setLocationRelativeTo( null );
			enregistrer.setVisible( true );
			enregistrer.add(contenuSave);
			contenuSave.add(lbl_save);
			contenuSave.add(bt_OK);
			bt_OK.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					enregistrer.setVisible(false);	
			}
		}); 
		
	}
	});
	
	Ajouter.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			new AjouterCommande(framePrincipale);
			
		}
	}); 
}

	public void refreshListeTableDevis(String description, String udm, String prix){
		int quant = Integer.parseInt(udm);
		int prix1 = Integer.parseInt(prix);
		Object[] obj = {description, quant, prix1};
		modelTableDevis.addRow(obj);
	}
    
	
	
    /*public static void main(String[] args) {
    	
    	
		JFrame frame = new InterfaceDevis();
		//Commande.add(new JScrollPane());
		//frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
		
        
    }*/

	
	public void raffraichirListe(String numProd, String nom, String quantite)
	{
		int val1 = Integer.parseInt(numProd); 
		float val3 = Float.parseFloat(quantite); 		
		Object[] obj = {numProd,nom,quantite,};
		modelTableDevis.addRow(obj);
	}
	
	public void setComboBoxClient(){
		ArrayList<Client> clientBDD = DatabaseConnection.getClients();
		for(Client client: clientBDD){
			ChampTextClient.addItem(client.idclient + " : " + client.nomclient + " " + client.prenomclient);
		}
	}
}