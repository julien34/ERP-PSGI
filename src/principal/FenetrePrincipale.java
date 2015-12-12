package principal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import achat.PanelCommande;
import achat.PanelFournisseur;
import components.ButtonTabComponent;
import jdbc.DatabaseConnection;
import jdbc.FenetreConnexion;
import production.PanelProduits;
import vente.FenetreVente;
import vente.PanelClient;
import vente.InterfaceDevis;
import vente.PanelVente;

public class FenetrePrincipale extends JFrame
{
   	private static FenetrePrincipale framePrincipale;
   	
   	//Barre de menu
    JMenuBar menu = new JMenuBar();
    
    //Boutons de menu
    	//Base de données
        JMenu menuBdd = new JMenu("Base de données");
        JMenuItem menuBddConnexion = new JMenuItem("Connexion");
        JMenuItem menuBddDeconnexion = new JMenuItem("Deconnexion");
    
        //Achats
        JMenu menuAchats = new JMenu("Achats");
        JMenuItem menuAchatFournisseur = new JMenuItem("Fournisseurs");
        JMenuItem menuAchatCommande = new JMenuItem("Commandes");
        
        //Ventes
        JMenu menuVentes = new JMenu("Ventes");
       // JMenuItem menuVentesFenetreVente = new JMenuItem("Fenetre de vente");
        JMenu menuFenetreVente = new JMenu("Fenetre de vente");
        
        
        public static JMenuItem menuVenteEntreprise = new JMenuItem("Entreprise");
        public static JMenuItem menuVenteParticulier = new JMenuItem("Particulier");
        
        //sous menu entreprise , client
        
        
        JMenuItem menuVentesClients = new JMenuItem("Interface de clients");
        JMenuItem menuVentesDevis = new JMenuItem("Interface de devis");
        	        
        //Production
        JMenu menuProduction = new JMenu("Production");
        JMenuItem menuProductionProduits = new JMenuItem("Gérer les produits");
   	
    //Onglets
    private JTabbedPane onglets = new JTabbedPane();
   	
   	//Achat
   	private static PanelFournisseur panelFournisseur = null;
   	private static PanelCommande panelCommande = null;
   	
   	//Vente
   	private static PanelVente panelVente;
   	private static PanelClient panelClient;
   	private static InterfaceDevis InterfaceDevis;
   	private static FenetreVente fenetreVente;
   	
   	//Production
   	private static PanelProduits panelProduits;
   	
   	public FenetrePrincipale()
   	{
   		initFenetre();
   		initElements();
   		initHandlers();
   		setVisible(true);   		
   	}
   	
   	public void initFenetre()
   	{
   		//Paramétrage de la fenétre
   		setTitle("Projet PSGI");
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(960,750);
   		setLocationRelativeTo(null);
   		setResizable(true);
   	}
   	

   	public void initElements()
   	{
	   	//Ajouter les menus
	    	//Base de données
				menu.add(menuBdd);
				menuBdd.add(menuBddConnexion);
				menuBdd.add(menuBddDeconnexion);
	    
	        //Achats
				menu.add(menuAchats);
				menuAchats.add(menuAchatFournisseur);
				menuAchats.add(menuAchatCommande);
				

	        
	        //Ventes
				menu.add(menuVentes);
				menuVentes.add(menuFenetreVente);
				menuVentes.add(menuVentesClients);
				menuVentes.add(menuVentesDevis);
				
	        	menuFenetreVente.add(menuVenteEntreprise);
				menuFenetreVente.add(menuVenteParticulier);
	        //Production
				menu.add(menuProduction);
				menuProduction.add(menuProductionProduits);   
				
   		setJMenuBar(menu);
   		menuAchats.setEnabled(false);
   		menuVentes.setEnabled(false);
   		menuProduction.setEnabled(false);
   		menuBddDeconnexion.setEnabled(false);
   		
   		//Ajouter les onglets
   		add(onglets); 
   		
   		//Mise en mémoire des interfaces
	   		//Achats
   		
   		
	   		//Ventes
	   		panelClient = new PanelClient(framePrincipale);
	   		InterfaceDevis = new InterfaceDevis(framePrincipale);
	   		fenetreVente = new FenetreVente(framePrincipale);

	   		
	   		//Production
	   		panelProduits = new PanelProduits();
   	}
   	
   	public void initHandlers()
   	{
   		//Handler des bouttons de menu
	   		//Base de données
	   		menuBddConnexion.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				new FenetreConnexion(framePrincipale);
	   			}
	   		});
	   		menuBddDeconnexion.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				DatabaseConnection.disconnect();
	   				connexionClosed();
	   			}
	   		});	
	   		
	   		//Achats
	   		menuAchatFournisseur.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e){
	   				if(PanelFournisseur.getTableau().isEmpty()){
	   					ajouterOnglet("Fournisseurs", panelFournisseur = new PanelFournisseur(framePrincipale));
	   				}
	   				else {
	   					ajouterOnglet("Fournisseurs", panelFournisseur);
	   				}
	   			}
	   		});	
	   		
	   		menuAchatCommande.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ajouterOnglet("Commandes d'achat", panelCommande = new PanelCommande(framePrincipale));
				}
			});
	   		
	   		//Ventes
	   	/*	menuFenetreVente.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				ajouterOnglet("Gestion des ventes",fenetreVente);
	   			}
	   		});	*/
	   		
	   		menuVenteEntreprise.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				
	   				ajouterOnglet("Gestion des ventes",fenetreVente);
	   			}
	   		});	
	   		
	   		menuVenteParticulier.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				ajouterOnglet("Gestion des ventes",fenetreVente);
	   			}
	   		});	
	   		
	   		
	   		menuVentesClients.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				ajouterOnglet("Gestion des clients", panelClient);
	   			}
	   		});	
	   		menuVentesDevis.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{	   				
	   				ajouterOnglet("Gestion des Devis", InterfaceDevis);
	   			}
	   		});	
	   		
	   		
	   		
	   		
	   		
	   		//Production
	   		menuProductionProduits.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				ajouterOnglet("Gérer les produits",panelProduits);
	   				panelProduits.fillTable();
	   			}
	   		});	
   	}
   	
   	private void ajouterOnglet(String name, JPanel panel)
   	{
   		if (onglets.indexOfTab(name) < 0)
		{
			int i = onglets.getTabCount();
	   	    onglets.addTab(name, panel);
	   	    onglets.setTabComponentAt(i,new ButtonTabComponent(onglets));
	   	    onglets.setSelectedIndex(i);
		}
	}
   	
   	public static PanelProduits getPanelProduits()
   	{
   		return panelProduits;
   	}
   	
   	public PanelClient getPanelClient(){
		return panelClient;
   		
   	}
   	
   	public static InterfaceDevis getInterfaceDevis(){
		return InterfaceDevis;
   		
   	}

	public void connexionOpened()
	{
   		menuAchats.setEnabled(true);
   		menuVentes.setEnabled(true);
   		menuProduction.setEnabled(true);
   		menuBddDeconnexion.setEnabled(true);
	}

	public void connexionClosed()
	{
   		menuAchats.setEnabled(false);
   		menuVentes.setEnabled(false);
   		menuProduction.setEnabled(false);
   		menuBddDeconnexion.setEnabled(false);
   		onglets.removeAll();
	}
   	
   	public static void main(String[] args)
   	{	   		
   		//Création de la fenétre
   		framePrincipale = new FenetrePrincipale();
   		
   	}
}  

