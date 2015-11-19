package principal;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import achat.PanelAchat;
import jdbc.DatabaseConnection;
import production.AjouterProduits;
import production.ModifierProduits;
import production.PanelProduits;
import vente.FenetreVente;
import vente.InterfaceClients;
import vente.InterfaceDevis;
import vente.PanelVente;

public class FenetrePrincipale extends JFrame
{
	
	/***Déclaration des éléments graphiques***/
	private static FenetrePrincipale framePrincipale;
	
	//Barre des menus
	private JMenuBar barreDesMenus = new JMenuBar(); //On créer la barre
	
	//Menus
	private JMenu achat = new JMenu("Achats");
	private JMenu production = new JMenu("Production");
	private JMenu vente = new JMenu("Ventes");
	
	//Sous-menus d'achat
	private JMenuItem itemFournisseur = new JMenuItem("Fournisseurs");
	private JMenuItem itemDevisFournisseur = new JMenuItem("Devis");
	
	//Sous menus de la production
	private JMenuItem itemProduit = new JMenuItem("Produits");
	private JMenuItem itemStocks = new JMenuItem("Stock");
	
	//Sous menus des ventes
	private JMenuItem itemClient = new JMenuItem("Clients");
	private JMenuItem itemDevisClient = new JMenuItem("Devis");
	
	
	//Panels
	private JPanel selection = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
	private JPanel cards = new JPanel(new CardLayout());
	
	//Principal
	private JPanel panelCardsPrincipal = new JPanel();
	private static PanelPrincipal panelPrincipal;
	
	//Achat
	private JPanel panelCardsAchat = new JPanel();
	private static PanelAchat panelAchat;	
	
	//Vente
	private JPanel panelCardsVente = new JPanel();
	private static PanelVente panelVente;	
		
	//Production
	private JPanel panelCardsProduction = new JPanel();
	private static PanelProduits panelProduits;
	
	public FenetrePrincipale()
	{
		
	}	
	
	/**
	 * Initialise la fenetre associée
	 */
	public void initFenetre()
	{
		//Paramétrage de la fenêtre
		this.setTitle("Projet PSGI");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(960,540);	
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		//Fermeture de la connection lorsqu'on ferme la fenêtre
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run()
		    {
		    	DatabaseConnection.disconnect();
		    }
		});
	}
	
	/**
	 * Initialise les différents élements de la frame
	 */
	public void initElements()
	{
		/*Ajouter les élements*/
		
		//Ajout de la barre des menus à la frame 
		this.add(barreDesMenus);
		
		//Ajout des menus à la barre des menus 
		barreDesMenus.add(achat);
		barreDesMenus.add(vente);
		barreDesMenus.add(production);
		
		//Ajout des sous-menus aux menus 
		achat.add(itemFournisseur);
		achat.add(itemDevisFournisseur);
		
		production.add(itemProduit);
		
		vente.add(itemClient);
		vente.add(itemDevisClient);
		
		//Affichage de la barre des menus sur la frame
		this.setJMenuBar(barreDesMenus);

		add(cards);	
		cards.add(panelCardsPrincipal);
		cards.add(panelCardsAchat);		
		cards.add(panelCardsVente);		
		cards.add(panelCardsProduction);	
		
		//Ajout de vos interfaces
			//Principal
			panelCardsPrincipal.add(panelPrincipal = new PanelPrincipal(framePrincipale));
			//Achat
			panelCardsAchat.add(panelAchat = new PanelAchat(framePrincipale));
			//Vente
			panelCardsVente.add(panelVente = new PanelVente(framePrincipale));
			//Production
			panelCardsProduction.add(panelProduits = new PanelProduits(framePrincipale));
	}
	
	public void initHandlers()
	{
		//Handler des bouttons de menu
		itemFournisseur.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panelCardsAchat.show(true);
				panelCardsVente.show(false);
				panelCardsProduction.show(false);
			}
		});
		
		vente.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panelCardsAchat.show(false);
				panelCardsVente.show(true);
				panelCardsProduction.show(false);
			}
		});
		production.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panelCardsAchat.show(false);
				panelCardsVente.show(false);
				panelCardsProduction.show(true);
			}
		});		
	}
	
	public PanelProduits getPanelProduits()
	{
		return panelProduits;
	}
	
	public static void main(String[] args)
	{	
		//Connexion
		DatabaseConnection.connect("162.38.222.149","1521","iut","licencepsgi","123");
		
		//Cr�ation de la fen�tre
		framePrincipale = new FenetrePrincipale();
		framePrincipale.initFenetre();
		framePrincipale.initElements();
		framePrincipale.initHandlers();
		framePrincipale.setVisible(true);
	}
}