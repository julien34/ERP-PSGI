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

import jdbc.DatabaseConnection;
import production.AjouterProduits;
import production.ModifierProduits;
import vente.FenetreVente;
import vente.InterfaceClients;
import vente.InterfaceDevis;

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
	private JPanel panelAchat = new JPanel();
		//Contenu du panneau achat
		private JButton fenetreVente = new JButton("Fenetre de vente");
		private JButton interfaceClient = new JButton("Interface de clients");
		private JButton interfaceDevis = new JButton("Interface de devis");	
	
	private JPanel panelVente = new JPanel();
		//Contenu du panneau vente
		private JPanel wrap = new JPanel(new BorderLayout(10,10));
		private JPanel panelTexteTop = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
		private JPanel panelInputCenter = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
		private JPanel panelBouttonBottom = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
		private JTextField champsPrenom = new JTextField();
		private JTextField champsNom = new JTextField();
		private JTextField champsAdresse = new JTextField();
		private String message = "Pour ajouter un fournisseur, remplissez les champs suivants :";
		private JLabel labelTitre = new JLabel(message);
		private JLabel labelPrenom = new JLabel("Prénom");
		private JLabel labelNom = new JLabel("Nom");
		private JLabel labelAdresse = new JLabel("Adresse");
		private JButton btnValider = new JButton("Valider");
		private String nomFournisseur, prenomFournisseur, adresseFournisseur;
	
	private JPanel panelProduction = new JPanel();
		//Contenu du panneau production
		private JPanel panelTable = new JPanel(new BorderLayout(10,10));
		private JPanel panelBouttons = new JPanel(new FlowLayout(FlowLayout.CENTER,40,0));
		private JButton ajouter = new JButton("Ajouter");
		private JButton modifier = new JButton("Modifier");
		private JButton supprimer = new JButton("Supprimer");	
		private JTable table;
		private JScrollPane scrollPane;
		private static DefaultTableModel model;
		private String[] columnNames = {"codeProduit","description","categorie","prixVente","prixAchat","udm"};
		private int produitChoisi = -1;
	
	public FenetrePrincipale()
	{
		
	}	
	
	/**
	 * Initialise la fenetre associée
	 */
	public void initFenetre()
	{
		//Paramétrage de la fenêtre
		setTitle("Projet PGSI");
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
		cards.add(panelAchat);		
		cards.add(panelVente);
		cards.add(panelProduction);	
		
		//Création du panel achat
		panelAchat.add(wrap);
		wrap.add("North",panelTexteTop);
		wrap.add("Center",panelInputCenter);
		wrap.add("South",panelBouttonBottom);
		panelTexteTop.add("North",labelTitre);
		panelInputCenter.add(labelPrenom);		
		panelInputCenter.add(champsPrenom);
		champsPrenom.setColumns(10);
		panelInputCenter.add(labelNom);
		panelInputCenter.add(champsNom);
		champsNom.setColumns(10);		
		panelInputCenter.add(labelAdresse);		
		panelInputCenter.add(champsAdresse);
		champsAdresse.setColumns(10);
		panelBouttonBottom.add("South",btnValider);
		
		//Création du panel vente
		panelVente.add(fenetreVente);
		panelVente.add(interfaceClient);
		panelVente.add(interfaceDevis);
		
		//Création du panel production
			//Remplir, configurer et créer la table
			model = new DefaultTableModel(DatabaseConnection.remplirListeProduits(),columnNames)
			{
	            Class[] types = { Integer.class, String.class, String.class, Float.class, Float.class, Float.class };

	            @Override
	            public Class getColumnClass(int columnIndex) 
	            {
	                return Integer.class;
	            }
	            
	            public boolean isCellEditable(int row, int column)
	            {  
	                return false;  
	            }
	        };
			table = new JTable(model);
			table.setAutoCreateRowSorter(true);
			table.getRowSorter().toggleSortOrder(0);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrollPane = new JScrollPane(table);
			table.setPreferredScrollableViewportSize(new Dimension(540, 224));			
			
			//Ajouter les élements
			panelProduction.add(panelTable);
			panelTable.add("North",scrollPane);
			panelTable.add("Center",panelBouttons);
			panelBouttons.add(ajouter);
			ajouter.setPreferredSize(new Dimension(140,26));
			panelBouttons.add(modifier);
			modifier.setPreferredSize(new Dimension(140,26));
			panelBouttons.add(supprimer);
			supprimer.setPreferredSize(new Dimension(140,26));
			
			//Désactiver les bouttons
			modifier.setEnabled(false);
			supprimer.setEnabled(false);
	}
	
	public void initHandlers()
	{
		//Handler des bouttons de menu
		achat.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panelAchat.show(true);
				panelVente.show(false);
				panelProduction.show(false);
			}
		});
		vente.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panelAchat.show(false);
				panelVente.show(true);
				panelProduction.show(false);
			}
		});
		production.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panelAchat.show(false);
				panelVente.show(false);
				panelProduction.show(true);
			}
		});		
		
		//Handler achat
			//Handler de validation d'ajout de fournisseur
			btnValider.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					recupereDonnees();
					if(DatabaseConnection.requete("CALL ajoutFournisseurs('"+nomFournisseur+"','"+prenomFournisseur+"','"+adresseFournisseur+"')")){
						effacerDonnees();
						afficherMessageOK();
					}
				}
			});
		
		//Handler vente
			fenetreVente.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{ new FenetreVente(framePrincipale); }
			});
			interfaceClient.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{ new InterfaceClients(framePrincipale); }
			});
			interfaceDevis.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{ new InterfaceDevis(framePrincipale); }
			});		
				
		//Handler production
			//Handler de sélection de ligne
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
			{
				public void valueChanged(ListSelectionEvent e) 
			    {
					if (e.getValueIsAdjusting()) return;			        
			        ListSelectionModel selection = (ListSelectionModel) e.getSource();
			        produitChoisi = selection.getMinSelectionIndex();//selection.getMinSelectionIndex();
			        
			        //Désactiver certains boutons si on ne sélectionne aucune ligne
			        modifier.setEnabled(!selection.isSelectionEmpty());
			        supprimer.setEnabled(!selection.isSelectionEmpty());
			    }
			});
			ajouter.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{ new AjouterProduits(framePrincipale); }
			});
			modifier.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{ 
					String value1 = String.valueOf(model.getValueAt(table.convertRowIndexToModel(produitChoisi), 0));
					String value2 = String.valueOf(model.getValueAt(table.convertRowIndexToModel(produitChoisi), 1));
					String value3 = String.valueOf(model.getValueAt(table.convertRowIndexToModel(produitChoisi), 2));
					String value4 = String.valueOf(model.getValueAt(table.convertRowIndexToModel(produitChoisi), 3));
					String value5 = String.valueOf(model.getValueAt(table.convertRowIndexToModel(produitChoisi), 4));
					String value6 = String.valueOf(model.getValueAt(table.convertRowIndexToModel(produitChoisi), 5));
					new ModifierProduits(framePrincipale,value1,value2,value3,value4,value5,value6); 
				}
			});
			supprimer.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if (produitChoisi != -1)
					{
						String value = String.valueOf(model.getValueAt(table.convertRowIndexToModel(produitChoisi), 0));
						
						if (DatabaseConnection.requete("DELETE FROM PRODUITS WHERE codeProduit = "+value) == true)
						{ model.removeRow(table.convertRowIndexToModel(produitChoisi)); }
					}
				}
			});
	}
	
	/**
	 * Met à jour la ligne modifiée par l'option de modification de produit
	 */
	public void raffraichirLigne(String value1, String value2, String value3, String value4, String value5, String value6)
	{
		int val1 = Integer.parseInt(value1);
		float val4 = Float.parseFloat(value4); 
		float val5 = Float.parseFloat(value5); 
		float val6 = Float.parseFloat(value6); 	
		int ligneChoisieTemp = produitChoisi;
		if (produitChoisi != -1)
		{			
			model.setValueAt(val1, table.convertRowIndexToModel(ligneChoisieTemp), 0);
			model.setValueAt(value2, table.convertRowIndexToModel(ligneChoisieTemp), 1);
			model.setValueAt(value3, table.convertRowIndexToModel(ligneChoisieTemp), 2);
			model.setValueAt(val4, table.convertRowIndexToModel(ligneChoisieTemp), 3);
			model.setValueAt(val5, table.convertRowIndexToModel(ligneChoisieTemp), 4);
			model.setValueAt(val6, table.convertRowIndexToModel(ligneChoisieTemp), 5);
		}
	}
	
	/**
	 * Met à jour la liste des produits dans la frame principale
	 */
	public void raffraichirListe(String value1, String value2, String value3, String value4, String value5, String value6)
	{
		int val1 = Integer.parseInt(value1);
		float val4 = Float.parseFloat(value4); 
		float val5 = Float.parseFloat(value5); 
		float val6 = Float.parseFloat(value6); 		
		Object[] obj = {val1,value2,value3,val4,val5,val6};
		model.addRow(obj);
	}
	
	//Récupère les champs
	private void recupereDonnees()
	{
		this.nomFournisseur = champsNom.getText();
		this.prenomFournisseur = champsPrenom.getText();
		this.adresseFournisseur = champsAdresse.getText();
	}
		
	//remet les champs à zéro
	private void effacerDonnees()
	{
		this.nomFournisseur = "";
		this.champsPrenom.setText("");
		this.prenomFournisseur = "";
		this.champsNom.setText("");
		this.adresseFournisseur = "";
		this.champsAdresse.setText("");
	}
		
	private void afficherMessageOK() 
	{
		this.message = "Fournisseur ajouté dans la base de données. \nPour ajouter à  nouveau un fournisseur, remplissez les champs suivants.";
		labelTitre.repaint();
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