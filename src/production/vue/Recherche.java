package production.vue;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * Panel de recherche permettant de filter un tableau.
 * 
 */
@SuppressWarnings("serial")
public class Recherche extends JPanel
{
	/**
	 * Panel de la barre de recherche.
	 */
	private JPanel panel_recherche_barre = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	/**
	 * Label de la barre de recherche.
	 */
	private JLabel recherche_label = new JLabel("Recherche : ");
	
	/**
	 * Champs texte contenant les mots à filtrer
	 */
	private JTextField recherche_texte = new JTextField(20);
	
	/**
	 * Panel des filtres de recherche.
	 */
	private JPanel panel_recherche_filtre = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	/**
	 * Label de la liste des filtres de recherche.
	 */
	private JLabel filtre_colonne_label = new JLabel("Filtrer par : ");
	
	/**
	 * Liste des colonnes à filtrer.
	 */
	private JComboBox<String> filtre_colonne = new JComboBox<String>();
	
	/**
	 * Label de la liste des opérateurs.
	 */
	private JLabel filtre_operateur_label = new JLabel("    Opérateur : ");
	
	/**
	 * Opérateurs prédéfinis.
	 */
	private String[] filtre_operateur_nom = {"=",">",">=","<","<="};
	
	/**
	 * Liste des noms de colonnes qui déclenchent l'activation des filtres (colonnes contenant des nombres).
	 */
	private String[] filtre_operateur_declencheur;
	
	/**
	 * Liste des opérateurs.
	 */
	private JComboBox<String> filtre_operateur = new JComboBox<String>(filtre_operateur_nom);
	
	/**
	 * Le sorter de la table associée.
	 */
	private TableRowSorter<DefaultTableModel> sorter;
	
	/**
	 * Constructeur qui permet de mettre en place les outils de recherche dans la table.
	 * @param sorter Le sorter de la table associée
	 * @param colonnes Les colonnes de la table associée
	 * @param operateur_declencheur Les colonnes de la table associée qui vont déclencher l'activation des opérateurs (colonnes contenant des nombres)
	 */
	public Recherche(TableRowSorter<DefaultTableModel> sorter, String[] colonnes, String[] operateur_declencheur) 
	{
		//Remplissage combobox
		for (String nom : colonnes) 
		{
			filtre_colonne.addItem(nom);
		}
		
		this.sorter = sorter;
		filtre_operateur_declencheur = operateur_declencheur;
		
		//Filtrage à la creation
		filtrer();
    	
    	//Ajouter les éléments
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(panel_recherche_barre);
		panel_recherche_barre.add(recherche_label);
		panel_recherche_barre.add(recherche_texte);

		add(panel_recherche_filtre);
		panel_recherche_filtre.add(filtre_colonne_label);
		panel_recherche_filtre.add(filtre_colonne);
		panel_recherche_filtre.add(filtre_operateur_label);
		panel_recherche_filtre.add(filtre_operateur);

		
		//Listener de recherche
		recherche_texte.addKeyListener(new KeyAdapter() 
		{		
			public void keyTyped(KeyEvent e) 
			{
				filtrer();
			}	
					
			public void keyPressed(KeyEvent e) 
			{
				filtrer();
			}	
					
			public void keyReleased(KeyEvent e) 
			{
				filtrer();
			}
		});
		
		ActionListener changement_combo_box = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				filtrer();
			}
		};
		filtre_colonne.addActionListener(changement_combo_box);
		filtre_operateur.addActionListener(changement_combo_box);
	}	

	/**
	 * Filtre la table associée en gérant les opératuers et le contenu à filtrer.
	 */
	public void filtrer()
	{
		String text = recherche_texte.getText();
		int index = filtre_colonne.getSelectedIndex()+1;
		boolean condition = false;
		
		for (String nom : filtre_operateur_declencheur) 
		{
			if (filtre_colonne.getSelectedItem().toString().equals(nom))
			{
				condition = true;
				break;
			}
		}
		
		filtre_operateur_label.setEnabled(condition);
		filtre_operateur.setEnabled(condition);
		
		if (text.length() == 0) 
        {
        	sorter.setRowFilter(null);
        } 
        else 
        {
        	if (filtre_operateur.isEnabled())
        	{
        		ArrayList<RowFilter<Object,Object>> filters = null;
	        	switch (filtre_operateur.getSelectedItem().toString())
	        	{
	        		case "=":
	        			sorter.setRowFilter(RowFilter.regexFilter(text, index));
	        		break;
	        		case ">":
	        			sorter.setRowFilter(RowFilter.numberFilter(ComparisonType.AFTER, Float.parseFloat(text),index));
	        		break;
	        		case ">=":
	        			filters = new ArrayList<RowFilter<Object,Object>>(2);
	        			filters.add(RowFilter.regexFilter(text, index));
	        			filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Float.parseFloat(text),index));
	        			sorter.setRowFilter(RowFilter.orFilter(filters));
	        		break;
	        		case "<":
	        			sorter.setRowFilter(RowFilter.numberFilter(ComparisonType.BEFORE, Float.parseFloat(text),index));
	        		break;
	        		case "<=":
	        			filters = new ArrayList<RowFilter<Object,Object>>(2);
	        			filters.add(RowFilter.regexFilter(text, index));
	        			filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Float.parseFloat(text),index));
	        			sorter.setRowFilter(RowFilter.orFilter(filters));
	        		break;
	        	}
        	}
        	else
        	{
        		sorter.setRowFilter(RowFilter.regexFilter("(?i)"+text, index));
        	}
        }
	}
}