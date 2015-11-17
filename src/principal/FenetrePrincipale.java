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
	private static FenetrePrincipale framePrincipale;
	//Bouttons
	private JButton achat = new JButton("Achats");
	private JButton vente = new JButton("Ventes");
	private JButton production = new JButton("Production");	
	
	//Panels
	private JPanel selection = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
	private JPanel cards = new JPanel(new CardLayout());
	
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
		setTitle("Projet PSGI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960,540);	
		setLocationRelativeTo(null);
		setResizable(false);
		
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
		//Ajouter les élements
		add(selection,BorderLayout.NORTH);
		selection.setBorder(new EmptyBorder(0,0,35,0));
		selection.add(achat);
		selection.add(vente);
		selection.add(production);

		add(cards);		
		cards.add(panelCardsAchat);		
		cards.add(panelCardsVente);		
		cards.add(panelCardsProduction);	
		
		//Ajout de vos interfaces
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
		achat.addActionListener(new ActionListener()
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
		
		//Création de la fenêtre
		framePrincipale = new FenetrePrincipale();
		framePrincipale.initFenetre();
		framePrincipale.initElements();
		framePrincipale.initHandlers();
		framePrincipale.setVisible(true);
	}
}