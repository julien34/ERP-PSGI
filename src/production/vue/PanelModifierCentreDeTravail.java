package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.*;

import jdbc.DatabaseProduction;

/**
 * Panel de modification de centre de travail.
 *
 */
@SuppressWarnings("serial")
public class PanelModifierCentreDeTravail extends JPanel
{	
	/**
	 * Le panel sur lequel celui-ci est implenté.
	 */
	private PanelCentreDeTravail panel_centre_de_travail;

	/**
	 * Le panel qui contient tous les autres éléments.
	 */
	private JPanel panel_grid = new JPanel(new GridLayout(5,5,4,4));
	
	/**
	 * Label du champs nom.
	 */
	private JLabel nom_label = new JLabel("nom ");
	
	/**
	 * Champs texte contenant le nom du centre de travail.
	 */
	private JTextField nom = new JTextField(10);

	/**
	 * Label du champs type.
	 */
	private JLabel type_label = new JLabel("type ");
	
	/**
	 * Champs texte contenant le type du centre de travail.
	 */
	private JTextField type = new JTextField(10);
	
	/**
	 * Label de la capacité par cycle.
	 */
	private JLabel capacite_par_cycle_label = new JLabel("capacite par cycle ");
	
	/**
	 * Champs texte contenant la capacité par cycle.
	 */
	private JTextField capacite_par_cycle = new JTextField(10);
	
	/**
	 * Label du temps par cycle.
	 */
	private JLabel temps_par_cycle_label = new JLabel("temps par cycle");
	
	/**
	 * Champs texte contenant le temps par cycle.
	 */
	private JTextField temps_par_cycle = new JTextField(10);

	/**
	 * Le code de la categorie.
	 */
	private int code = -1;
	
	/**
	 * Bouton pour modifier le centre de travail.
	 */
	private JButton modifier = new JButton("Modifier");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");

	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_centre_de_travail Le panel sur lequel celui-ci est implenté
	 * @param code_arg Le code Le nom du centre de travail
	 * @param type_arg Le type du centre de travail
	 * @param capacite_par_cycle_arg La capacité par cycle du centre de travail
	 * @param temps_par_cycle_arg Le temps par cycle du centre de travail
	 */
	public PanelModifierCentreDeTravail(PanelCentreDeTravail panel_centre_de_travail, int code_arg, String nom_arg, String type_arg, String capacite_par_cycle_arg, String temps_par_cycle_arg)
	{
		this.panel_centre_de_travail = panel_centre_de_travail;
		code = code_arg;
		nom.setText(nom_arg);
		type.setText(type_arg);
		capacite_par_cycle.setText(capacite_par_cycle_arg);
		temps_par_cycle.setText(temps_par_cycle_arg);
				
		//Ajouter les élements	
		add(panel_grid);
		panel_grid.add(nom_label);
		panel_grid.add(nom);
		//panel_grid.add(new JPanel());
		panel_grid.add(type_label);
		panel_grid.add(type);

		panel_grid.add(capacite_par_cycle_label);
		panel_grid.add(capacite_par_cycle);
		//panel_grid.add(new JPanel());
		panel_grid.add(temps_par_cycle_label);
		panel_grid.add(temps_par_cycle);

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
					ResultSet rs_centre_de_travail = DatabaseProduction.getRsCentre();
					rs_centre_de_travail.beforeFirst();
					
					while (rs_centre_de_travail.next())
					{
						if (rs_centre_de_travail.getInt("code") == code)
						{
							rs_centre_de_travail.updateString("nom", nom.getText());
							rs_centre_de_travail.updateString("type", type.getText());
							rs_centre_de_travail.updateFloat("capaciteparcycle", Float.parseFloat(capacite_par_cycle.getText()));
							rs_centre_de_travail.updateFloat("tempsparcycle", Float.parseFloat(temps_par_cycle.getText()));						
							rs_centre_de_travail.updateRow();
						}
					}
										
					panel_centre_de_travail.raffraichirTable();
					
					JOptionPane.showMessageDialog(null, "Centre modifié avec succès !", "Modification de centre", JOptionPane.INFORMATION_MESSAGE);			
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erreur d'ajout du centre, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}								
			}
		});

		panel_centre_de_travail.getRootPane().setDefaultButton(modifier);
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