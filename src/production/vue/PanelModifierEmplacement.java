package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.*;

import jdbc.DatabaseProduction;

/**
 * Panel de modification d'emplacement.
 *
 */
@SuppressWarnings("serial")
public class PanelModifierEmplacement extends JPanel
{	
	/**
	 * Le panel sur lequel celui-ci est implenté.
	 */
	private PanelEmplacement panel_emplacement;
	
	/**
	 * Le panel qui contient tous les autres éléments.
	 */
	private JPanel panel_grid = new JPanel(new GridLayout(5,5,4,4));
	
	/**
	 * Label du champs nom.
	 */
	private JLabel nom_label = new JLabel("nom ");
	
	/**
	 * Champs texte contenant le nom de l'emplacement.
	 */
	private JTextField nom = new JTextField(10);

	/**
	 * Le code de la categorie.
	 */
	private int code = -1;
	
	/**
	 * Bouton pour modifier l'emplacement.
	 */
	private JButton modifier = new JButton("Modifier");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");

	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_composition Le panel sur lequel celui-ci est implenté
	 * @param code_arg Le code de l'emplacement
	 * @param nom_arg La nom de l'emplacement
	 */
	public PanelModifierEmplacement(PanelEmplacement panel_emplacement, int code_arg, String nom_arg)
	{
		this.panel_emplacement = panel_emplacement;
		code = code_arg;
		nom.setText(nom_arg);
				
		//Ajouter les élements	
		add(panel_grid);
		panel_grid.add(nom_label);
		panel_grid.add(nom);
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
					ResultSet rs_emplacement = DatabaseProduction.getRsEmplacement();
					rs_emplacement.beforeFirst();
					
					while (rs_emplacement.next())
					{
						if (rs_emplacement.getInt("code") == code)
						{
							rs_emplacement.updateString("nom", nom.getText());				
							rs_emplacement.updateRow();
						}
					}
										
					panel_emplacement.raffraichirTable();
					
					JOptionPane.showMessageDialog(null, "Emplacement modifié avec succès !", "Modification de l'emplacement", JOptionPane.INFORMATION_MESSAGE);			
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erreur d'ajout de l'emplacement, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}								
			}
		});

		panel_emplacement.getRootPane().setDefaultButton(modifier);
	}		
	
	/**
	 * Retourne le bouton de retour.
	 * @return le bouton de retour
	 */
	public JButton getRetour()
	{
		return retour;
	}
}