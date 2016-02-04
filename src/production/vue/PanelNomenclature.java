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

import production.modele.Nomenclature;
import jdbc.DatabaseProduction;

/**
 * Panel de la gestion de nomenclatures
 * @author Mickael
 *
 */
@SuppressWarnings("serial")
public class PanelNomenclature extends JPanel
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
	 * Bouton d'ajout de produit.
	 */
	private JButton ajouter = new JButton("Ajouter");
	
	/**
	 * Bouton de modification de produit.
	 */
	private JButton modifier = new JButton("Modifier");
	
	/**
	 * Bouton de suppression de produit.
	 */
	private JButton supprimer = new JButton("Supprimer");	
	
	/**
	 * Le tableau qui contient les informations des produits.
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
	private final String[] nom_colonne = {"Code","Nom","Quantite","Type","Gamme","Produit"};
	
	/**
	 * Le modele du tableau.
	 */
	private static DefaultTableModel model = new DefaultTableModel(0,7)
	
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
     * Le panel d'ajout de nomenclature.
     */
    private PanelAjouterNomenclature panel_ajouter;
    
    /**
     * Le panel de modification de nomenclature.
     */
    private PanelModifierNomenclature panel_modifier;
	
    /**
     * Constructeur du panel contenant l'interface de gestion de nomenclature.
     */
	public PanelNomenclature()
	{
		//Remplir, configurer et créer la table
		table = new JTable(model);
		table.setRowSorter(sorter);
		sorter.toggleSortOrder(1);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroll = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(540, 180));
		
		//Ajouter les éléments
		String[] filtre_colonne_nom = {"Nom","Quantite","Type","Gamme","Produit"};
		String[] filtre_operateur_declencheur = {"Quantite"};
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
				panel_ajouter = new PanelAjouterNomenclature(PanelNomenclature.this);
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
				panel_modifier = new PanelModifierNomenclature(PanelNomenclature.this
						,(int) model.getValueAt(table.convertRowIndexToModel(ligne_choisie), 0)
						,String.valueOf(model.getValueAt(table.convertRowIndexToModel(ligne_choisie), 1))
						,String.valueOf(model.getValueAt(table.convertRowIndexToModel(ligne_choisie), 2))
						,String.valueOf(model.getValueAt(table.convertRowIndexToModel(ligne_choisie), 3))
						,String.valueOf(model.getValueAt(table.convertRowIndexToModel(ligne_choisie), 4))
						,String.valueOf(model.getValueAt(table.convertRowIndexToModel(ligne_choisie), 5)));
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
				int option = JOptionPane.showConfirmDialog(null, "Confirmer la suppression de la nomenclature ?", "Suppression de nomenclature", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION)
				{
					panel_action.removeAll();
					if (ligne_choisie != -1)
					{
						try
						{
							int code = (int) model.getValueAt(table.convertRowIndexToModel(ligne_choisie), 0);
							
							raffraichirTable();
							
							ArrayList<Nomenclature> liste_nomenclature = DatabaseProduction.getListeNomenclature();
							int index = 0;

							DatabaseProduction.getRsNomenclature().beforeFirst();
							while (DatabaseProduction.getRsNomenclature().next())
							{
								if (DatabaseProduction.getRsNomenclature().getInt("code") == code)
								{									
									DatabaseProduction.getRsNomenclature().deleteRow();
									liste_nomenclature.remove(index);
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
		ArrayList<Nomenclature> nomenclature = DatabaseProduction.getListeNomenclature();
		Object[][] data = new Object[nomenclature.size()][6];
		int index = 0;

		//Passage d'array list en array. Les attributs des objets sont récupérés
		for (Nomenclature object : nomenclature) 
		{
			data[index][0] = object.getCode();
			data[index][1] = object.getNom();
			data[index][2] = object.getQuantite();
			data[index][3] = object.getType();
			data[index][4] = object.getGamme().getNom();
			data[index][5] = object.getProduit().getNom();
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