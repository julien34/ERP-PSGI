package principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import achat.vues.PanelCategorie;
import achat.vues.PanelCommande;
import achat.vues.PanelFournisseur;
import components.ButtonTabComponent;
import jdbc.DatabaseConnection;
import jdbc.FenetreConnexion;
//import production.PanelCategories;
import production.PanelGamme;
import production.PanelOperation;
import production.PanelProduits;
import production.PanelRecherche;
import vente.FenetreVente;
import vente.PanelClient;
import vente.PanelCommandes;
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
        JMenu menuAchats = new JMenu("Achats");//Menu achat de la barre
        JMenu menuAchatFournisseur = new JMenu("Fournisseurs");//Sous menu fournisseurs
        JMenuItem menuAchatAjoutFournisseur = new JMenuItem("Fournisseurs");//Item ajouter un fournisseur
        JMenuItem menuAchatAjoutCategorie = new JMenuItem("Catégories");//Item ajouter une catégorie de fournisseurs
        JMenuItem menuAchatCommande = new JMenuItem("Commandes");//Item commande
        
        //Ventes
        JMenu menuVentes = new JMenu("Ventes");
       // JMenuItem menuVentesFenetreVente = new JMenuItem("Fenetre de vente");
        JMenuItem menuCommandes = new JMenuItem("Commandes");
        

        
        //sous menu entreprise , client
        
        
        JMenuItem menuVentesClients = new JMenuItem("Interface de clients");
        JMenuItem menuVentesDevis = new JMenuItem("Interface de devis");
        	        
        //Production
        JMenu menuProduction = new JMenu("Production");
        JMenuItem menuProductionProduits = new JMenuItem("Gérer les produits");
        JMenuItem menuProductionCategories = new JMenuItem("G\u00e9rer les categories");
        JMenuItem menuProductionOperation = new JMenuItem("G\u00e9rer les operations");
        JMenuItem menuProductionGamme = new JMenuItem("G\u00e9rer les gammes");
        JMenuItem menuRechercheProduits = new JMenuItem("Rechercher produits");
    //Onglets
    private JTabbedPane onglets = new JTabbedPane();
   	
   	//Achat
   	private static PanelFournisseur panelFournisseur = null;
   	private static PanelCategorie panelCategorie = null;
   	public static PanelCommande panelCommande = null;
   	
   	//Vente
   	private static PanelVente panelVente;
   	private static PanelClient panelClient;
   	private static InterfaceDevis InterfaceDevis;
   	private static FenetreVente fenetreVente;
   	private static PanelCommandes panelCommandes;
   	
   	//Production
   	private static PanelProduits panelProduits;
    //private static PanelCategories panelCategories;
    private static PanelOperation panelOperation;
    private static PanelGamme panelGamme;
    private static  PanelRecherche panelRecherche;
    
   	public FenetrePrincipale()
   	{
   		initFenetre();
   		initElements();
   		initHandlers();
   		setVisible(true);
   		new FenetreConnexion(this);
   	}
   	
   	public void initFenetre()
   	{
   		//Paramétrage de la fenétre
   		setTitle("Projet PSGI");
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(900,900);
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
				menuAchatFournisseur.add(menuAchatAjoutCategorie);
				menuAchatFournisseur.add(menuAchatAjoutFournisseur);
				menuAchats.add(menuAchatFournisseur);
				menuAchats.add(menuAchatCommande);
				
	        //Ventes
				menu.add(menuVentes);
				menuVentes.add(menuVentesClients);
				menuVentes.add(menuVentesDevis);
				menuVentes.add(menuCommandes);

	        //Production
				menu.add(menuProduction);
				menuProduction.add(menuProductionProduits);
		        menuProduction.add(menuProductionCategories);
		        menuProduction.add(menuProductionOperation);
		        menuProduction.add(menuProductionGamme);
		        menuProduction.add(menuRechercheProduits);
		        
		        
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
	   		panelCommandes = new PanelCommandes(framePrincipale);
	   		//Production
	   		panelProduits = new PanelProduits();
	        //panelCategories = new PanelCategories();
	        panelOperation = new PanelOperation();
	        panelGamme = new PanelGamme();
	        panelRecherche = new PanelRecherche();
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
	   		menuAchatAjoutFournisseur.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e){
	   				//On vérifie que le tableau ne soit pas vide pour éviter de mettre 2 fois les données dedans
	   				if(PanelFournisseur.getTableau().isEmpty()){
	   					ajouterOnglet("Fournisseurs", panelFournisseur = new PanelFournisseur(framePrincipale));
	   				}
	   				else {
	   					ajouterOnglet("Fournisseurs", panelFournisseur);
	   				}
	   			}
	   		});	
	   		
	   		menuAchatAjoutCategorie.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
	   				//On vérifie que le tableau ne soit pas vide pour éviter de mettre 2 fois les données dedans
					if(PanelCategorie.getListeCategorie().isEmpty()){
						ajouterOnglet("Catégories fournisseurs", panelCategorie = new PanelCategorie(framePrincipale));
					}
					else{
						ajouterOnglet("Catégories fournisseurs", panelCategorie);
					}
				}
			});
	   		
	   		menuAchatCommande.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(PanelCommande.getListeCommande().isEmpty()){
						ajouterOnglet("Commandes d'achat", panelCommande = new PanelCommande(framePrincipale));
					}
					else{
						ajouterOnglet("Commandes d'achat", panelCommande);
					}
					
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

	   		
	   		menuCommandes.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				ajouterOnglet("Gestion des commandes",panelCommandes);
	   			}
	   		});	
	   		

	   		
	   		menuVentesClients.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	//   				FenetreVente.remplirInfosClient();
	   				PanelClient.remplirtableClient();
	   				
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
	   		
//	   	 this.menuProductionCategories.addActionListener(new ActionListener(){
//
//	            @Override
//	            public void actionPerformed(ActionEvent e2) {
//	                FenetrePrincipale.this.ajouterOnglet("G\u00e9rer les categories", panelCategories);
//	                panelCategories.fillTable();
//	            }
//	        });
	        this.menuProductionOperation.addActionListener(new ActionListener(){

	            @Override
	            public void actionPerformed(ActionEvent e2) {
	                FenetrePrincipale.this.ajouterOnglet("G\u00e9rer les operations", panelOperation);
	                panelOperation.fillTable();
	            }
	        });
	        this.menuProductionGamme.addActionListener(new ActionListener(){

	            @Override
	            public void actionPerformed(ActionEvent e2) {
	                FenetrePrincipale.this.ajouterOnglet("G\u00e9rer les gammes", panelGamme);
	                panelGamme.fillTable();
	            }
	        });
	        
	        menuRechercheProduits.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent e){
					ajouterOnglet("Recherche Produits",panelRecherche);
					panelRecherche.fillTable();
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
   	
//    public static PanelCategories getPanelCategories() {
//        return panelCategories;
//    }

    public static PanelOperation getPanelOperation() {
        return panelOperation;
    }

    public static PanelGamme getPanelGamme() {
        return panelGamme;
    }

   	
   	public static PanelClient getPanelClient(){
		return panelClient;
   		
   	}
   	
   	public static InterfaceDevis getInterfaceDevis(){
		return InterfaceDevis;
   		
   	}

	public void connexionOpened()
	{
		menuBddConnexion.setEnabled(false);
   		menuAchats.setEnabled(true);
   		menuVentes.setEnabled(true);
   		menuProduction.setEnabled(true);
   		menuBddDeconnexion.setEnabled(true);
	}

	public void connexionClosed()
	{
		menuBddConnexion.setEnabled(true);
   		menuAchats.setEnabled(false);
   		menuVentes.setEnabled(false);
   		menuProduction.setEnabled(false);
   		menuBddDeconnexion.setEnabled(false);
   		onglets.removeAll();
	}

	public JMenuItem getMenuBddConnexion() {
		return menuBddConnexion;
	}

	public void setMenuBddConnexion(JMenuItem menuBddConnexion) {
		this.menuBddConnexion = menuBddConnexion;
	}

	public static FenetrePrincipale getFramePrincipale() {
		return framePrincipale;
	}

	public static void setFramePrincipale(FenetrePrincipale framePrincipale) {
		FenetrePrincipale.framePrincipale = framePrincipale;
	}
	
   	public static void main(String[] args)
   	{	   		
   		System.setProperty( "file.encoding", "UTF-8" );
   		//Création de la fenétre
   		framePrincipale = new FenetrePrincipale();   		
   	}   	
}  

