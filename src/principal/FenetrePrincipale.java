package principal;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import achat.PanelAchat;
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
        JMenuItem menuAchatAjouterFournisseur = new JMenuItem("Ajouter un fournisseur");
        
        //Ventes
        JMenu menuVentes = new JMenu("Ventes");
        JMenuItem menuVentesFenetreVente = new JMenuItem("Fenetre de vente");
        JMenuItem menuVentesClients = new JMenuItem("Interface de clients");
        JMenuItem menuVentesDevis = new JMenuItem("Interface de devis");
        	        
        //Production
        JMenu menuProduction = new JMenu("Production");
        JMenuItem menuProductionProduits = new JMenuItem("Gérer les produits");
   	
    //Onglets
    private JTabbedPane onglets = new JTabbedPane();
   	
   	//Achat
   	private static PanelAchat panelAchat;	
   	
   	//Vente
   	private static PanelVente panelVente;
   	private static PanelClient panelClient;
   		
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
   		//Paramétrage de la fenêtre
   		setTitle("Projet PSGI");
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(960,540);	
   		setLocationRelativeTo(null);
   		setResizable(false);
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
				menuAchats.add(menuAchatAjouterFournisseur);
	        
	        //Ventes
				menu.add(menuVentes);
				menuVentes.add(menuVentesFenetreVente);
				menuVentes.add(menuVentesClients);
				menuVentes.add(menuVentesDevis);
	        	        
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
	   		
	   		//Production
	   		panelProduits = new PanelProduits(framePrincipale);
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
	   		menuAchatAjouterFournisseur.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				ajouterOnglet("Créer un fournisseur",panelAchat = new PanelAchat(framePrincipale));
	   			}
	   		});	
	   		
	   		//Ventes
	   		menuVentesFenetreVente.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				new FenetreVente(framePrincipale);
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
	   				new InterfaceDevis(framePrincipale);
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
   	
   	public PanelProduits getPanelProduits()
   	{
   		return panelProduits;
   	}
   	
   	public PanelClient getPanelClient(){
		return panelClient;
   		
   	}
   	
   	public static void main(String[] args)
   	{	   		
   		//Création de la fenêtre
   		framePrincipale = new FenetrePrincipale();
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
}  

