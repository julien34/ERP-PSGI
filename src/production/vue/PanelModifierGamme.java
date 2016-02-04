package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;

import production.modele.Emplacement;
import jdbc.DatabaseProduction;

/**
 * Panel de modification de gamme.
 *
 */
@SuppressWarnings("serial")
public class PanelModifierGamme extends JPanel
{	
	/**
	 * Le panel sur lequel celui-ci est implenté.
	 */
	private PanelGamme panel_gamme;
	
	/**
	 * Le panel qui contient tous les autres éléments.
	 */
	private JPanel panel_grid = new JPanel(new GridLayout(3,5,4,4));
	
	/**
	 * Label du champs nom.
	 */
	private JLabel nom_label = new JLabel("nom ");
	
	/**
	 * Champs texte contenant le nom de la gamme.
	 */
	private JTextField nom = new JTextField(10);
	
	/**
	 * Label du champs emplacement.
	 */
	private JLabel emplacement_label = new JLabel("emplacement ");
	
	/**
	 * Modele de la combobox de l'emplacement.
	 */
	private DefaultComboBoxModel<String> model_emplacement = new DefaultComboBoxModel<String>();
	
	/**
	 * La liste des emplacements disponibles.
	 */
	private JComboBox<String> emplacement = new JComboBox<String>(model_emplacement);
	
	/**
	 * Le code de la categorie.
	 */
	private int code = -1;
	
	/**
	 * Bouton pour modifier la gamme.
	 */
	private JButton modifier = new JButton("Modifier");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");

	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_composition Le panel sur lequel celui-ci est implenté
	 * @param code_arg Le code de la gamme
	 * @param nom_arg La nom de la gamme
	 * @param emplacement_arg L'emplacement de la gamme
	 */
	public PanelModifierGamme(PanelGamme panel_gamme, int code_arg, String nom_arg, String emplacement_arg)
	{
		this.panel_gamme = panel_gamme;
		code = code_arg;
		nom.setText(nom_arg);	
		
		//Ajouter les élements	
		add(panel_grid);
		panel_grid.add(nom_label);
		panel_grid.add(nom);
		panel_grid.add(new JPanel());
		panel_grid.add(emplacement_label);
		panel_grid.add(emplacement);

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
					ResultSet rs_gamme = DatabaseProduction.getRsGamme();
					rs_gamme.beforeFirst();
					
					while (rs_gamme.next())
					{
						if (rs_gamme.getInt("code") == code)
						{
							DatabaseProduction.getRsGamme().updateString("nom", nom.getText());
							for (Emplacement emplacements : DatabaseProduction.getListeEmplacement()) 
							{
								if(emplacements.getNom().equals(emplacement.getSelectedItem().toString()))
								{
									rs_gamme.updateInt("emplacement", emplacements.getCode());
								}
							}
							rs_gamme.updateRow();
						}
					}
										
					panel_gamme.raffraichirTable();
					
					JOptionPane.showMessageDialog(null, "Gamme modifiée avec succès !", "Modification de gamme", JOptionPane.INFORMATION_MESSAGE);			
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erreur d'ajout de categorie, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		
		remplirComboBox();
		emplacement.setSelectedIndex(model_emplacement.getIndexOf(emplacement_arg));
		
		panel_gamme.getRootPane().setDefaultButton(modifier);
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
    	model_emplacement.removeAllElements();
    	
    	ArrayList<Emplacement> emplacements = DatabaseProduction.getListeEmplacement();
    	
		//Passage d'array list en array. Les attributs des objets sont récupérés
		for (Emplacement object : emplacements) 
		{
			model_emplacement.addElement(object.getNom());
		}

		repaint();
		revalidate();
    }
}