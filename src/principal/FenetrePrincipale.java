package principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import production.vue.PanelCategorieProduit;
import production.vue.PanelCentreDeTravail;
import production.vue.PanelComposition;
import production.vue.PanelEmplacement;
import production.vue.PanelGamme;
import production.vue.PanelNomenclature;
import production.vue.PanelOperation;
import production.vue.PanelProduit;
import production.vue.PanelUniteDeMesure;
import achat.vues.PanelCategorie;
import achat.vues.PanelCommande;
import achat.vues.PanelFournisseur;
import components.ButtonTabComponent;
import jdbc.DatabaseConnection;
import jdbc.FenetreConnexion;
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
        static JMenuItem menuBddConnexion = new JMenuItem("Connexion");
        static JMenuItem menuBddDeconnexion = new JMenuItem("Deconnexion");
    
        //Achats
        static JMenu menuAchats = new JMenu("Achats");//Menu achat de la barre
        JMenu menuAchatFournisseur = new JMenu("Fournisseurs");//Sous menu fournisseurs
        static JMenuItem menuAchatAjoutFournisseur = new JMenuItem("Fournisseurs");//Item ajouter un fournisseur
        static JMenuItem menuAchatAjoutCategorie = new JMenuItem("Catégories");//Item ajouter une catégorie de fournisseurs
        static JMenuItem menuAchatCommande = new JMenuItem("Commandes");//Item commande
        
         //Ventes
        static JMenu menuVentes = new JMenu("Ventes");
       // JMenuItem menuVentesFenetreVente = new JMenuItem("Fenetre de vente");
        static JMenuItem menuCommandes = new JMenuItem("Commandes");
        

        
        //sous menu entreprise , client
        
        
        static JMenuItem menuVentesClients = new JMenuItem("Interface de clients");
        static JMenuItem menuVentesDevis = new JMenuItem("Interface de devis");
        	        
        //Production
        private static JMenu menu_production = new JMenu("Production");
        private JMenuItem menu_production_produit = new JMenuItem("Gérer les produits");
        private JMenuItem menu_production_categorie = new JMenuItem("Gérer les categories");
        private JMenuItem menu_production_operation = new JMenuItem("Gérer les operations");
        private JMenuItem menu_production_gamme = new JMenuItem("Gérer les gammes");
        private JMenuItem menu_production_centre_de_travail = new JMenuItem("Gérer les centres");
        private JMenuItem menu_production_composition = new JMenuItem("Gérer les compositions");
        private JMenuItem menu_production_emplacement = new JMenuItem("Gérer les emplacements");
        private JMenuItem menu_production_nomenclature = new JMenuItem("Gérer les nomenclatures");
        private JMenuItem menu_production_unite_de_mesure = new JMenuItem("Gérer les unite de mesure");
        
    //Onglets
    private static JTabbedPane onglets = new JTabbedPane();
   	
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
   	private PanelProduit panel_produit = new PanelProduit();
    private static PanelCategorieProduit panel_categorie_produit = new PanelCategorieProduit();
    private static PanelOperation panel_operation = new PanelOperation();
    private static PanelGamme panel_gamme = new PanelGamme();
    private static PanelCentreDeTravail panel_centre_de_travail = new PanelCentreDeTravail();
    private static PanelComposition panel_composition = new PanelComposition();
    private static PanelEmplacement panel_emplacement = new PanelEmplacement();
    private static PanelNomenclature panel_nomenclature = new PanelNomenclature();
    private static PanelUniteDeMesure panel_unite_de_mesure = new PanelUniteDeMesure();
    
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
				menu.add(menu_production);
				menu_production.add(menu_production_produit);
		        menu_production.add(menu_production_categorie);
		        menu_production.add(menu_production_operation);
		        menu_production.add(menu_production_gamme);
		        menu_production.add(menu_production_centre_de_travail);
		        menu_production.add(menu_production_composition);
		        menu_production.add(menu_production_emplacement);
		        menu_production.add(menu_production_nomenclature);
		        menu_production.add(menu_production_unite_de_mesure);
		        
		        
   		setJMenuBar(menu);
   		menuAchats.setEnabled(false);
   		menuVentes.setEnabled(false);
   		menu_production.setEnabled(false);
   		menuBddDeconnexion.setEnabled(false);
   		
   		//Ajouter les onglets
   		add(onglets); 
   		
   		//Mise en mémoire des interfaces
	   		//Achats
   			
   		
	   		//Ventes
	   		panelClient = new PanelClient(framePrincipale);
	   		InterfaceDevis = new InterfaceDevis(framePrincipale);
	   		panelCommandes = new PanelCommandes(framePrincipale);
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
	   				InterfaceDevis.setComboBoxClient();
	   				ajouterOnglet("Gestion des Devis", InterfaceDevis);
	   			}
	   		});	
	   		
	   		
	   		
	   		
	   		
	   		//Production
	   	//Production
	   		menu_production_produit.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				ajouterOnglet("Gérer les produits",panel_produit);
	   				panel_produit.raffraichirTable();
	   			}
	   		});
	   		menu_production_categorie.addActionListener(new ActionListener()
	   		{
	   			public void actionPerformed(ActionEvent e)
	   			{
	   				ajouterOnglet("Gérer les categories", panel_categorie_produit);
	   				panel_categorie_produit.raffraichirTable();
	   			}
	   		});	
	        menu_production_operation.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e) 
	            {
	   				ajouterOnglet("Gérer les opérations", panel_operation);
	   				panel_operation.raffraichirTable();
	            }
	        });
	        menu_production_gamme.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e) 
	            {
	   				ajouterOnglet("Gérer les gammes", panel_gamme);
	   				panel_gamme.raffraichirTable();
	            }
	        });
	        menu_production_centre_de_travail.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e) 
	            {
	            	ajouterOnglet("Gérer les centres de travail", panel_centre_de_travail);
	                panel_centre_de_travail.raffraichirTable();
	            }
	        });
	        menu_production_composition.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e) 
	            {
	            	ajouterOnglet("Gérer les compositions", panel_composition);
	                panel_composition.raffraichirTable();
	            }
	        });
	        menu_production_emplacement.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e) 
	            {
	                ajouterOnglet("Gérer les emplacements", panel_emplacement);
	                panel_emplacement.raffraichirTable();
	            }
	        });
	        menu_production_nomenclature.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e) 
	            {
	                ajouterOnglet("Gérer les nomenclatures", panel_nomenclature);
	                panel_nomenclature.raffraichirTable();
	            }
	        });
	        menu_production_unite_de_mesure.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e) 
	            {
	            	ajouterOnglet("Gérer les unites de mesure", panel_unite_de_mesure);
	                panel_unite_de_mesure.raffraichirTable();
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
   		menu_production.setEnabled(true);
   		menuBddDeconnexion.setEnabled(true);
	}

	public static void connexionClosed()
	{
		menuBddConnexion.setEnabled(true);
   		menuAchats.setEnabled(false);
   		menuVentes.setEnabled(false);
   		menu_production.setEnabled(false);
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

