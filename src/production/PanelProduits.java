package production;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import jdbc.DatabaseConnection;

public class PanelProduits extends JPanel 
{
	private JPanel panelTable = new JPanel(new BorderLayout(10,10));
	private JPanel panelBouttons = new JPanel(new FlowLayout(FlowLayout.CENTER,40,0));
	private JButton ajouter = new JButton("Ajouter");
	private JButton modifier = new JButton("Modifier");
	private JButton supprimer = new JButton("Supprimer");	
	private JTable table;
	private JScrollPane scrollPane;
	private final String[] columnNames = {"nom","categorie","prix de vente","prix d'achat","udm","achat/vente"};
	private static DefaultTableModel model = new DefaultTableModel(0,6)
	{
        Class[] types = { String.class, String.class, Float.class, Float.class, String.class, String.class };

        @Override
        public Class getColumnClass(int columnIndex) 
        {
            return Integer.class;
        }
        
        @Override
        public boolean isCellEditable(int row, int column)
        {  
            return false;  
        }
    };
	private int produitChoisi = -1;
	private AjouterProduits ajouter_p = new AjouterProduits();
	private ModifierProduits modifier_p = new ModifierProduits();
	
	public void fillTable()
	{
		model.setDataVector(DatabaseConnection.remplirListeProduits(), columnNames);
		table.getRowSorter().toggleSortOrder(0);
	}
	
	public PanelProduits()
	{
		initElements();
		initHandlers();
	}

	private void initElements() 
	{
		//Remplir, configurer et créer la table
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(540, 224));			
		
		//Ajouter les élements
		add(panelTable);
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
		//Handler de selection de ligne
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent e) 
		    {
				if (e.getValueIsAdjusting()) return;			        
		        ListSelectionModel selection = (ListSelectionModel) e.getSource();
		        produitChoisi = selection.getMinSelectionIndex();
		        
		        //Désactiver certains boutons si on ne selectionne aucune ligne
		        modifier.setEnabled(!selection.isSelectionEmpty());
		        supprimer.setEnabled(!selection.isSelectionEmpty());
		    }
		});
		ajouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ 
				panelTable.remove(modifier_p);
				panelTable.add("South",ajouter_p);
				ajouter_p.remplirCategorie();
				revalidate();
				repaint();
			}
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
				panelTable.remove(ajouter_p);
				modifier_p.setVal(value1,value2,value3,value4,value5,value6);
				panelTable.add("South",modifier_p);
				revalidate();
				repaint();
			}
		});
		supprimer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int option = JOptionPane.showConfirmDialog(null, "Confirmer la suppression du produit ?", "Suppression de produit", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION)
				{
					if (produitChoisi != -1)
					{
						String value1 = String.valueOf(model.getValueAt(table.convertRowIndexToModel(produitChoisi), 0));
						String value2 = String.valueOf(model.getValueAt(table.convertRowIndexToModel(produitChoisi), 1));
						String value3 = String.valueOf(model.getValueAt(table.convertRowIndexToModel(produitChoisi), 2));
						String value4 = String.valueOf(model.getValueAt(table.convertRowIndexToModel(produitChoisi), 3));
						String value5 = String.valueOf(model.getValueAt(table.convertRowIndexToModel(produitChoisi), 4));
						
						int codeProduit = DatabaseConnection.getCodeProduit(value1,value2,value3,value4,value5);
						
						if (DatabaseConnection.requete("DELETE FROM PRODUITS WHERE codeProduit = "+codeProduit))
						{ model.removeRow(table.convertRowIndexToModel(produitChoisi)); }
					}
				}
			}
		});	
		ajouter_p.getRetour().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ panelTable.remove(ajouter_p); }
		});
		modifier_p.getRetour().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ panelTable.remove(modifier_p); }
		});
	}
	
	/**
	 * Met à jour la ligne modifiée par l'option de modification de produit
	 */
	public void raffraichirLigne(String value1, String value2, String value3, String value4, String value5, String value6)
	{
		float val3 = Float.parseFloat(value3);
		float val4 = Float.parseFloat(value4);
		float val5 = Float.parseFloat(value5);
		int ligneChoisieTemp = produitChoisi;
		if (produitChoisi != -1)
		{			
			model.setValueAt(value1, table.convertRowIndexToModel(ligneChoisieTemp), 0);
			model.setValueAt(value2, table.convertRowIndexToModel(ligneChoisieTemp), 1);
			model.setValueAt(val3, table.convertRowIndexToModel(ligneChoisieTemp), 2);
			model.setValueAt(val4, table.convertRowIndexToModel(ligneChoisieTemp), 3);
			model.setValueAt(val5, table.convertRowIndexToModel(ligneChoisieTemp), 4);
			model.setValueAt(value6, table.convertRowIndexToModel(ligneChoisieTemp), 5);
		}
	}
	
	/**
	 * Met à jour la liste des produits dans la frame principale
	 */
	public void raffraichirListe(String value1, String value2, String value3, String value4, String value5, String value6)
	{
		String val2 = DatabaseConnection.determinerCategorie(Integer.parseInt(value2));
		float val3 = Float.parseFloat(value3);
		float val4 = Float.parseFloat(value4);
		int val5 = Integer.parseInt(value5);
		Object[] obj = {value1,val2,val3,val4,val5,value6};
		model.addRow(obj);
	}
}
