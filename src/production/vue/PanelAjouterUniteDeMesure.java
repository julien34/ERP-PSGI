package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import production.modele.UniteDeMesure;
import jdbc.DatabaseProduction;

/**
 * Ce panel permet d'ajouter une unité de mesure.
 */
@SuppressWarnings("serial")
public class PanelAjouterUniteDeMesure extends JPanel
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
	 * Bouton pour ajouter l'unité de mesure.
	 */
	private JButton ajouter = new JButton("Ajouter");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");

	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_unite_de_mesure Le panel sur lequel celui-ci est implenté.
	 */
	public PanelAjouterUniteDeMesure(PanelUniteDeMesure panel_unite_de_mesure)
	{	  
		this.panel_unite_de_mesure = panel_unite_de_mesure;
		
		//Ajouter les élements	
		add(panel_grid);
		panel_grid.add(nom_label);
		panel_grid.add(nom);
		panel_grid.add(new JPanel());
		panel_grid.add(new JPanel());
		
		panel_grid.add(ajouter);
		panel_grid.add(retour);
		
		//Action si click sur bouton
		ajouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//On tente de rentrer une nouvelle unité de mesure
				if (DatabaseProduction.procedure("CALL nouvelleUniteDeMesure('"+nom.getText()+"')"))
				{
					//On recupere la clé primaire
					int code = DatabaseProduction.getSequence("SELECT seqUniteDeMesure.currval FROM dual");
					
					if (code != -1)
					{
						//Creation de l'unite de mesure
						UniteDeMesure unite_de_mesure = new UniteDeMesure(code,nom.getText());
						
						//Ajout de l'unite de mesure dans la liste
						DatabaseProduction.getListeUdm().add(unite_de_mesure);
						
						panel_unite_de_mesure.raffraichirTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Une erreur interne est survenue, veuillez redémarrer le logiciel.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null, "Unite de mesure ajouté avec succès !", "Ajout d'unite de mesure", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Erreur d'ajout de l'unite de mesure, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		
		panel_unite_de_mesure.getRootPane().setDefaultButton(ajouter);
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