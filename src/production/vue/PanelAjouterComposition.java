package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import production.modele.Composition;
import production.modele.Nomenclature;
import jdbc.DatabaseProduction;

/**
 * Ce panel permet d'ajouter une composition.
 */
@SuppressWarnings("serial")
public class PanelAjouterComposition extends JPanel
{		
	/**
	 * Le panel sur lequel celui-ci est implenté.
	 */
	private PanelComposition panel_composition;

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
	 * Bouton pour ajouter la composition.
	 */
	private JButton ajouter = new JButton("Ajouter");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");

	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_composition Le panel sur lequel celui-ci est implenté.
	 */
	public PanelAjouterComposition(PanelComposition panel_composition)
	{	  
		this.panel_composition = panel_composition;
		
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
		panel_grid.add(ajouter);
		panel_grid.add(retour);
		
		//Action si click sur bouton
		ajouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				Nomenclature nomenclature_a_temp = null;
				for (Nomenclature n : DatabaseProduction.getListeNomenclature())
				{
					if (n.getNom() == nomenclature_a.getSelectedItem().toString())
					{
						nomenclature_a_temp = n;
						break;
						
					}
				}
				
				Nomenclature nomenclature_b_temp = null;
				for (Nomenclature n : DatabaseProduction.getListeNomenclature())
				{
					if (n.getNom() == nomenclature_b.getSelectedItem().toString())
					{
						nomenclature_b_temp = n;
						break;
						
					}
				}
				
				
				//On tente de rentrer une nouvelle composition
				if (DatabaseProduction.procedure("CALL nouvelleComposition ('"+nomenclature_a_temp.getCode()+"', '"+nomenclature_b_temp.getCode()+"')"))
				{
					//On recupere la clé primaire
					int code = DatabaseProduction.getSequence("SELECT seqComposition.currval FROM dual");
					
					if (code != -1)
					{
						//Creation de la composition
						Composition composition = new Composition(code, nomenclature_a_temp, nomenclature_b_temp);

						//Ajout du produit dans la liste
						DatabaseProduction.getListeComposition().add(composition);
						
						panel_composition.raffraichirTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Une erreur interne est survenue, veuillez redémarrer le logiciel.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null, "Composition ajouté avec succès !", "Ajout de composition", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Cette composition existe déjà, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		
		remplirComboBox();
		
		panel_composition.getRootPane().setDefaultButton(ajouter);
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