package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import production.modele.Emplacement;
import production.modele.Gamme;
import jdbc.DatabaseProduction;

/**
 * Ce panel permet d'ajouter une gamme.
 */
@SuppressWarnings("serial")
public class PanelAjouterGamme extends JPanel
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
	 * Bouton pour ajouter la gamme.
	 */
	private JButton ajouter = new JButton("Ajouter");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");

	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_gamme Le panel sur lequel celui-ci est implenté.
	 */
	public PanelAjouterGamme(PanelGamme panel_gamme)
	{	  
		this.panel_gamme = panel_gamme;
		
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
		panel_grid.add(ajouter);
		panel_grid.add(retour);
		
		//Action si click sur bouton
		ajouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Emplacement emplacement_temp = null;
				String code_emplacement = "";
				for (Emplacement p : DatabaseProduction.getListeEmplacement())
				{
					if (p.getNom().equals(emplacement.getSelectedItem().toString()))
					{
						emplacement_temp = p;
						code_emplacement = String.valueOf(p.getCode());
						break;
					}
				}

				//On tente de rentrer un nouveau produit
				if (DatabaseProduction.procedure("CALL nouvelleGamme ('"+nom.getText()+"', '"+code_emplacement+"')"))
				{
					//On recupere la clé primaire
					int code = DatabaseProduction.getSequence("SELECT seqgamme.currval FROM dual");
					
					if (code != -1)
					{
						//Creation du produit
						Gamme gamme = new Gamme(code,nom.getText(),emplacement_temp);
						
						//Ajout du produit dans la liste
						DatabaseProduction.getListeGamme().add(gamme);
						
						panel_gamme.raffraichirTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Une erreur interne est survenue, veuillez redémarrer le logiciel.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null, "Gamme ajoutée avec succès !", "Ajout de gamme", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Erreur d'ajout du gamme, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}		
			}
		});
		
		remplirComboBox();
		
		panel_gamme.getRootPane().setDefaultButton(ajouter);
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