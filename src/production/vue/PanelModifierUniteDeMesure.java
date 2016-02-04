package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.*;

import jdbc.DatabaseProduction;

/**
 * Panel de modification d'unité de mesure.
 *
 */
@SuppressWarnings("serial")
public class PanelModifierUniteDeMesure extends JPanel
{	
	/**
	 * Le panel sur lequel celui-ci est implenté.
	 */
	private PanelUniteDeMesure panel_unite_de_mesure;
	
	/**
	 * Le panel qui contient tous les autres éléments.
	 */
	private JPanel panel_grid = new JPanel(new GridLayout(5,5,4,4));
	
	/**
	 * Label du champs nom.
	 */
	private JLabel nom_label = new JLabel("nom ");
	
	/**
	 * Champs texte contenant le nom de l'unité de mesure.
	 */
	private JTextField nom = new JTextField(10);

	/**
	 * Le code de la categorie.
	 */
	private int code = -1;
	
	/**
	 * Bouton pour modifier l'unité de mesure.
	 */
	private JButton modifier = new JButton("Modifier");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");
	
	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_unite_de_mesure Le panel sur lequel celui-ci est implenté
	 * @param code_arg Le code de l'unité de mesure
	 * @param nom_arg Le nom de l'unité de mesure
	 */
	public PanelModifierUniteDeMesure(PanelUniteDeMesure panel_unite_de_mesure, int code_arg, String nom_arg)
	{
		this.panel_unite_de_mesure = panel_unite_de_mesure;
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
					ResultSet rs_unite_de_mesure = DatabaseProduction.getRsUdm();
					rs_unite_de_mesure.beforeFirst();
					
					while (rs_unite_de_mesure.next())
					{
						if (rs_unite_de_mesure.getInt("code") == code)
						{
							rs_unite_de_mesure.updateString("nom", nom.getText());				
							rs_unite_de_mesure.updateRow();
						}
					}
										
					panel_unite_de_mesure.raffraichirTable();
					
					JOptionPane.showMessageDialog(null, "Unite de mesure modifié avec succès !", "Modification de l'unite de mesure", JOptionPane.INFORMATION_MESSAGE);			
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erreur d'ajout de l'unite de mesure, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}								
			}
		});

		panel_unite_de_mesure.getRootPane().setDefaultButton(modifier);
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