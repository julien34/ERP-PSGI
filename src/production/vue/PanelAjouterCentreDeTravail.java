package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import production.modele.CentreDeTravail;
import jdbc.DatabaseProduction;

/**
 * Ce panel permet d'ajouter un centre de travail.
 */
@SuppressWarnings("serial")
public class PanelAjouterCentreDeTravail extends JPanel
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
	 * Bouton pour ajouter le centre de travail.
	 */
	private JButton ajouter = new JButton("Ajouter");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");

	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_centre_de_travail Le panel sur lequel celui-ci est implenté.
	 */
	public PanelAjouterCentreDeTravail(PanelCentreDeTravail panel_centre_de_travail)
	{	  
		this.panel_centre_de_travail = panel_centre_de_travail;
		
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
		panel_grid.add(ajouter);
		panel_grid.add(retour);
		
		//Action si click sur bouton
		ajouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//On tente de rentrer un nouveau centre
				if (DatabaseProduction.procedure("CALL nouveauCentre ('"+nom.getText()+"', '"+type.getText()+"', "+Float.parseFloat(capacite_par_cycle.getText())+", "+Float.parseFloat(temps_par_cycle.getText())+")"))
				{
					//On recupere la clé primaire
					int code = DatabaseProduction.getSequence("SELECT seqCentre.currval FROM dual");
					
					if (code != -1)
					{
						//Creation du centre
						CentreDeTravail centre_de_travail = new CentreDeTravail(code,nom.getText(),type.getText(),Float.parseFloat(capacite_par_cycle.getText()),Float.parseFloat(temps_par_cycle.getText()));
						
						//Ajout du centre dans la liste
						DatabaseProduction.getListeCentreDeTravail().add(centre_de_travail);
						
						panel_centre_de_travail.raffraichirTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Une erreur interne est survenue, veuillez redémarrer le logiciel.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null, "Centre ajouté avec succès !", "Ajout de centre", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Erreur d'ajout du centre, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		
		panel_centre_de_travail.getRootPane().setDefaultButton(ajouter);
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