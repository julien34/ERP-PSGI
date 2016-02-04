package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import production.modele.Emplacement;
import jdbc.DatabaseProduction;

/**
 * Ce panel permet d'ajouter un emplacement.
 */
@SuppressWarnings("serial")
public class PanelAjouterEmplacement extends JPanel
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
	 * Bouton pour ajouter l'emplacement.
	 */
	private JButton ajouter = new JButton("Ajouter");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");

	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_emplacement Le panel sur lequel celui-ci est implenté.
	 */
	public PanelAjouterEmplacement(PanelEmplacement panel_emplacement)
	{	  
		this.panel_emplacement = panel_emplacement;
		
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
				//On tente de rentrer un nouvel emplacement
				if (DatabaseProduction.procedure("CALL nouveauEmplacement ('"+nom.getText()+"')"))
				{
					//On recupere la clé primaire
					int code = DatabaseProduction.getSequence("SELECT seqEmplacement.currval FROM dual");
					
					if (code != -1)
					{
						//Creation de l'emplacement
						Emplacement emplacement = new Emplacement(code,nom.getText());
						
						//Ajout de l'emplacement dans la liste
						DatabaseProduction.getListeEmplacement().add(emplacement);
						
						panel_emplacement.raffraichirTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Une erreur interne est survenue, veuillez redémarrer le logiciel.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null, "Emplacement ajouté avec succès !", "Ajout d'emplacement", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Erreur d'ajout de l'emplacement, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		
		panel_emplacement.getRootPane().setDefaultButton(ajouter);
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