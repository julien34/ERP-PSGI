package production.vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import production.modele.Categorie;
import jdbc.DatabaseProduction;

/**
 * Panel de la gestion de categories.
 *
 */
@SuppressWarnings("serial")
public class PanelCategorieProduit extends JPanel
{
	/**
	 * Le panel qui contient le tableau.
	 */
	private JPanel panel_table = new JPanel(new BorderLayout(10,10));
	
	/**
	 * Le panel qui contient les boutons.
	 */
	private JPanel panel_boutton = new JPanel(new FlowLayout(FlowLayout.CENTER,40,0));
	
	/**
	 * Le panel qui contient les panel d'ajout ou de modification.
	 */
	private JPanel panel_action = new JPanel();
	
	/**
	 * Bouton d'ajout de categorie.
	 */
	private JButton ajouter = new JButton("Ajouter");
	
	/**
	 * Bouton de modification de categorie.
	 */
	private JButton modifier = new JButton("Modifier");
	
	/**
	 * Bouton de suppression de categorie.
	 */
	private JButton supprimer = new JButton("Supprimer");	
	
	/**
	 * Le tableau qui contient les informations des categories.
	 */
	private JTable table;
	
	/**
	 * Le scrollpane du tableau.
	 */
	private JScrollPane scroll;
	
	/**
	 * La ligne du tableau qui est sélectionnée.
	 */
	private int ligne_choisie = -1;
	
	/**
	 * Le nom des colonnes du tableau.
	 */
	private final String[] nom_colonne = {"Code","Nom","Parent"};
	
	/**
	 * Le modele du tableau.
	 */
	private static DefaultTableModel model = new DefaultTableModel(0,3)
	{		
		//On ne peux pas toucher au contenu du tableau
        public boolean isCellEditable(int ligne, int colonne)
        {  
            return false;
        }
    };
    
    /**
     * Le sorter du tableau.
     */
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
    
    /**
     * Le panel d'ajout de categorie.
     */
    private PanelAjouterCategorieProduit panel_ajouter;
    
    /**
     * Le panel de modification de categorie.
     */
    private PanelModifierCategorieProduit panel_modifier;
	
    /**
     * Constructeur du panel contenant l'interface de gestion de categories.
     */
	public PanelCategorieProduit()
	{
		//Remplir, configurer et créer la table
		table = new JTable(model);
		table.setRowSorter(sorter);
		sorter.toggleSortOrder(1);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroll = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(540, 180));
		
		//Ajouter les éléments
		String[] filtre_colonne_nom = {"Nom","Parent"};
		String[] filtre_operateur_declencheur = {};
		add(new Recherche(sorter, filtre_colonne_nom, filtre_operateur_declencheur));
		add(panel_table);
		panel_table.add("North",scroll);
		panel_table.add("Center",panel_boutton);
		panel_table.add("South",panel_action);
		panel_boutton.add(ajouter);
		ajouter.setPreferredSize(new Dimension(140,26));
		panel_boutton.add(modifier);
		modifier.setPreferredSize(new Dimension(140,26));
		panel_boutton.add(supprimer);
		supprimer.setPreferredSize(new Dimension(140,26));
		    	
		//Désactiver les bouttons
		modifier.setEnabled(false);
		supprimer.setEnabled(false);
		
		creationListener();
	}

	/**
	 * Tous les listeners sont créés ici.
	 */
	public void creationListener()
	{
		//Listener de selection de ligne
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent e) 
		    {
				if (e.getValueIsAdjusting()) return;
				
		        ListSelectionModel selection = (ListSelectionModel) e.getSource();
		        ligne_choisie = selection.getMinSelectionIndex();
		        
		        //Désactiver certains boutons si on ne selectionne aucune ligne
		        modifier.setEnabled(!selection.isSelectionEmpty());
		        supprimer.setEnabled(!selection.isSelectionEmpty());
		    }
		});
		
		//Listener suppression de panel
		ActionListener vider = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ 
				panel_action.removeAll(); 
			}
		};
		
		//Listener creation de panel
		ajouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ 
				panel_ajouter = new PanelAjouterCategorieProduit(PanelCategorieProduit.this);
				panel_ajouter.getRetour().addActionListener(vider);
				
				panel_action.removeAll();
				panel_action.add(panel_ajouter);
				
				revalidate();
				repaint();
			}
		});
		modifier.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ 
				panel_modifier = new PanelModifierCategorieProduit(PanelCategorieProduit.this
						,(int) model.getValueAt(table.convertRowIndexToModel(ligne_choisie), 0)
						,String.valueOf(model.getValueAt(table.convertRowIndexToModel(ligne_choisie), 1))
						,String.valueOf(model.getValueAt(table.convertRowIndexToModel(ligne_choisie), 2)));
				panel_modifier.getRetour().addActionListener(vider);
				
				panel_action.removeAll();
				panel_action.add(panel_modifier);
				
				revalidate();
				repaint();
			}
		});
		supprimer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int option = JOptionPane.showConfirmDialog(null, "Confirmer la suppression de la categorie ?", "Suppression de categorie", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION)
				{
					panel_action.removeAll();
					if (ligne_choisie != -1)
					{
						try
						{
							int code = (int) model.getValueAt(table.convertRowIndexToModel(ligne_choisie), 0);
							
							raffraichirTable();
							
							ArrayList<Categorie> liste_categorie = DatabaseProduction.getListeCategorie();
							int index = 0;

							DatabaseProduction.getRsCategorie().beforeFirst();
							while (DatabaseProduction.getRsCategorie().next())
							{
								if (DatabaseProduction.getRsCategorie().getInt("code") == code)
								{									
									DatabaseProduction.getRsCategorie().deleteRow();
									liste_categorie.remove(index);
									raffraichirTable();
								}
								index++;
							}
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
							JOptionPane.showMessageDialog(null, "Suppression impossible !\nLes contraintes d'integrité referentielles ne sont peut-être pas respectées.", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
	}
	
	/**
	 * Retourne le modele du tableau.
	 * @return Le modele du tableau
	 */
	public DefaultTableModel getModel()
	{
		return model;
	}
	
	/**
	 * Met à jour la table.
	 */
	public void raffraichirTable()
	{
		DatabaseProduction.chargerClasse();
		ArrayList<Categorie> categories = DatabaseProduction.getListeCategorie();
		Object[][] data = new Object[categories.size()][3];
		int index = 0;

		//Passage d'array list en array. Les attributs des objets sont récupérés
		for (Categorie object : categories) 
		{
			data[index][0] = object.getCode();
			data[index][1] = object.getNom();
			try
			{
				data[index][2] = object.getParent().getNom();
			}
			catch (Exception e)
			{
				data[index][2] = "";
			}
			index++;
		}

		model.setRowCount(0);
		model.setDataVector(data, nom_colonne);
		sorter.toggleSortOrder(1);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		
		if (panel_ajouter != null)
		{
			panel_ajouter.remplirComboBox();
		}
		if (panel_modifier != null)
		{
			panel_modifier.remplirComboBox();
		}
		
		repaint();
		revalidate();
	}
}