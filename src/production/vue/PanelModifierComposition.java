package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;

import production.modele.Categorie;
import production.modele.Nomenclature;
import production.modele.UniteDeMesure;
import jdbc.DatabaseProduction;

/**
 * Panel de modification de composition.
 *
 */
@SuppressWarnings("serial")
public class PanelModifierComposition extends JPanel
{	
	/**
	 * Le panel sur lequel celui-ci est implenté.
	 */
	private PanelProduit panel_produit;

	/**
	 * Le panel qui contient tous les autres éléments.
	 */
	private JPanel panel_grid = new JPanel(new GridLayout(3,5,4,4));

	/**
	 * Label du champs nomenclature a.
	 */
	private JLabel nomenclature_a_label = new JLabel("Nomenclature A ");
	
	/**
	 * Modele de la combobox de la nomenclature a.
	 */
	private DefaultComboBoxModel<String> model_nomenclature_a = new DefaultComboBoxModel<String>();
	
	/**
	 * La liste des nomenclatures a disponibles.
	 */
	private JComboBox<String> nomenclature_a = new JComboBox<String>(model_nomenclature_a);

	/**
	 * Label du champs nomenclature b.
	 */
	private JLabel nomenclature_b_label = new JLabel("Nomenclature B ");
	
	/**
	 * Modele de la combobox de la nomenclature b.
	 */
	private DefaultComboBoxModel<String> model_nomenclature_b = new DefaultComboBoxModel<String>();
	
	/**
	 * La liste des nomenclatures b disponibles.
	 */
	private JComboBox<String> nomenclature_b = new JComboBox<String>(model_nomenclature_b);

	/**
	 * Le code de la categorie.
	 */
	private int code = -1;
	
	/**
	 * Bouton pour modifier la composition.
	 */
	private JButton modifier = new JButton("Modifier");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");

	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_composition Le panel sur lequel celui-ci est implenté
	 * @param code_arg Le code de la composition
	 * @param nomenclature_a_arg La nomenclature a de la composition
	 * @param nomenclature_b_arg La nomenclature b de la composition
	 */
	public PanelModifierComposition(PanelComposition panel_composition, int code_arg, String nomenclature_a_arg, String nomenclature_b_arg)
	{
		this.panel_produit = panel_produit;
		code = code_arg;
				
		//Ajouter les élements	
		add(panel_grid);
		panel_grid.add(nomenclature_a_label);
		panel_grid.add(nomenclature_a);
		panel_grid.add(new JPanel());
		panel_grid.add(nomenclature_b_label);
		panel_grid.add(nomenclature_b);

		panel_grid.add(new JPanel());
		panel_grid.add(new JPanel());
		panel_grid.add(new JPanel());
		panel_grid.add(new JPanel());
		panel_grid.add(new JPanel());
		
		panel_grid.add(new JPanel());
		panel_grid.add(new JPanel());
		panel_grid.add(new JPanel());
		panel_grid.add(modifier);
		panel_grid.add(retour);
		
		//Action si click sur bouton
		modifier.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{				
				try
				{				
					ResultSet rs_composition = DatabaseProduction.getRsComposition();
					rs_composition.beforeFirst();
					
					while (rs_composition.next())
					{
						if (rs_composition.getInt("code") == code)
						{
							
							ResultSet rs_nomenclature = DatabaseProduction.getRsNomenclature();
							rs_nomenclature.beforeFirst();
							
							while (rs_nomenclature.next())
							{
								if (rs_nomenclature.getString("nom").equals(nomenclature_a.getSelectedItem().toString()))
								{
									rs_composition.updateInt("nomenclatureA", rs_nomenclature.getInt("code"));
								}
							}
							
							rs_nomenclature.beforeFirst();
							
							while (rs_nomenclature.next())
							{
								if (rs_nomenclature.getString("nom").equals(nomenclature_b.getSelectedItem().toString()))
								{
									rs_composition.updateInt("nomenclatureB", rs_nomenclature.getInt("code"));
								}
							}
							
							
							rs_composition.updateRow();
						}
					}
										
					panel_composition.raffraichirTable();
					
					JOptionPane.showMessageDialog(null, "Composition modifié avec succès !", "Modification de composition", JOptionPane.INFORMATION_MESSAGE);			
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cette composition existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}								
			}
		});
		
		remplirComboBox();
		nomenclature_a.setSelectedIndex(model_nomenclature_a.getIndexOf(nomenclature_a_arg));
		nomenclature_b.setSelectedIndex(model_nomenclature_b.getIndexOf(nomenclature_b_arg));
		
		panel_composition.getRootPane().setDefaultButton(modifier);
	}		
	
	/**
	 * Retourne le bouton de retour.
	 * @return le bouton de retour
	 */
	public JButton getRetour()
	{
		return retour;
	}

	/**
	 * Méthode permettant de remplir les combobox non-prédéfinies.
	 */
    public void remplirComboBox()
    {
    	//Vidage de combobox
    	model_nomenclature_a.removeAllElements();
    	model_nomenclature_b.removeAllElements();
    	
    	ArrayList<Nomenclature> nomenclature_a = DatabaseProduction.getListeNomenclature();

		//Passage d'array list en array. Les attributs des objets sont récupérés
		for (Nomenclature object : nomenclature_a) 
		{
			model_nomenclature_a.addElement(object.getNom());
		}
		
    	ArrayList<Nomenclature> nomenclature_b = DatabaseProduction.getListeNomenclature();

		//Passage d'array list en array. Les attributs des objets sont récupérés
		for (Nomenclature object : nomenclature_b) 
		{
			model_nomenclature_b.addElement(object.getNom());
		}
		
		repaint();
		revalidate();
    }
}