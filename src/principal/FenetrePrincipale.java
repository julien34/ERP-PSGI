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
import production.ModifierProduits;
import production.PanelProduits;
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
   		
   	//Production
   	private static PanelProduits panelProduits;
   	
   	public void initFenetre()
   	{
   		//Paramétrage de la fenêtre
   		setTitle("Projet PSGI");
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(960,540);	
   		setLocationRelativeTo(null);
   		setResizable(false);
   	}
   	
   	public JMenuBar createMenuBar() 
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

        return menu;
    }

   	public void initElements()
   	{
   		//Ajouter le menu
   		setJMenuBar(createMenuBar());
   		
   		//Ajouter les onglets
   		add(onglets); 
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
	   				
	   			}
	   		});	
	   		menuVentesClients.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				
	   			}
	   		});	
	   		menuVentesDevis.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				
	   			}
	   		});	
	   		
	   		//Production
	   		menuProductionProduits.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				ajouterOnglet("Gérer les produits",panelProduits = new PanelProduits(framePrincipale));
	   			}
	   		});	
   	}
   	
   	private void ajouterOnglet(String name, JPanel panel)
   	{
   		if (onglets.indexOfTab(name) < 0)
		{
			int i = onglets.getTabCount();
	   	    onglets.addTab(name,panel);
	   	    onglets.setTabComponentAt(i,new ButtonTabComponent(onglets));
	   	    onglets.setSelectedIndex(i);
		}
	}
   	
   	public PanelProduits getPanelProduits()
   	{
   		return panelProduits;
   	}
   	
   	public static void main(String[] args)
   	{	
   		//Connexion
   		DatabaseConnection.connect("162.38.222.149","1521","iut","licencepsgi","123");
   		
   		//Création de la fenêtre
   		framePrincipale = new FenetrePrincipale();
   		framePrincipale.initFenetre();
   		framePrincipale.initElements();
   		framePrincipale.initHandlers();
   		framePrincipale.setVisible(true);
   	}
}  

