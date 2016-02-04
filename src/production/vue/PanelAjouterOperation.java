package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import production.modele.CentreDeTravail;
import production.modele.Gamme;
import production.modele.Operation;
import jdbc.DatabaseProduction;

/**
 * Ce panel permet d'ajouter une opération.
 */
@SuppressWarnings("serial")
public class PanelAjouterOperation extends JPanel
{		
	/**
	 * Le panel sur lequel celui-ci est implenté.
	 */
	private PanelOperation panel_operation;
	
	/**
	 * Le panel qui contient tous les autres éléments.
	 */
	private JPanel panel_grid = new JPanel(new GridLayout(5,5,4,4));
	
	/**
	 * Label du champs nom.
	 */
	private JLabel nom_label = new JLabel("nom ");
	
	/**
	 * Champs texte contenant le nom de l'opération.
	 */
	private JTextField nom = new JTextField(10);
	
	/**
	 * Label du champs sequence.
	 */
	private JLabel sequence_label = new JLabel("séquence ");
	
	/**
	 * Champs texte contenant le nom de la sequence.
	 */
	private JTextField sequence = new JTextField(10);
	
	/**
	 * Label du champs nombre de cycle.
	 */
	private JLabel nombre_cycle_label = new JLabel("nombre de cycles ");
	
	/**
	 * Champs texte contenant le nombre de cycle.
	 */
	private JTextField nombre_cycle = new JTextField(10);

	/**
	 * Label du champs nombre d'heures.
	 */
	private JLabel nombre_heure_label = new JLabel("nombre d'heures ");
	
	/**
	 * Champs texte contenant le nombre d'heures.
	 */
	private JTextField nombre_heure = new JTextField(10);

	/**
	 * Label du champs gamme.
	 */
	private JLabel gamme_label = new JLabel("gamme ");
	
	/**
	 * Modele de la combobox de la gamme.
	 */
	private DefaultComboBoxModel<String> model_gamme = new DefaultComboBoxModel<String>();
	
	/**
	 * La liste des gammes disponibles.
	 */
	private JComboBox<String> gamme = new JComboBox<String>(model_gamme);

	/**
	 * Label du champs centre de travail.
	 */
	private JLabel centre_de_travail_label = new JLabel("centre de travail ");
	
	/**
	 * Modele de la combobox du centre de travail.
	 */
	private DefaultComboBoxModel<String> model_centre_de_travail = new DefaultComboBoxModel<String>();
	
	/**
	 * La liste des centres de travail disponibles.
	 */
	private JComboBox<String> centre_de_travail = new JComboBox<String>(model_centre_de_travail);
	
	/**
	 * Bouton pour ajouter l'opération.
	 */
	private JButton ajouter = new JButton("Ajouter");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");

	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_operation Le panel sur lequel celui-ci est implenté.
	 */
	public PanelAjouterOperation(PanelOperation panel_operation)
	{	  
		this.panel_operation = panel_operation;
		
		//Ajouter les élements	
		add(panel_grid);
		panel_grid.add(nom_label);
		panel_grid.add(nom);
		panel_grid.add(new JPanel());
		panel_grid.add(sequence_label);
		panel_grid.add(sequence);

		panel_grid.add(nombre_cycle_label);
		panel_grid.add(nombre_cycle);
		panel_grid.add(new JPanel());
		panel_grid.add(nombre_heure_label);
		panel_grid.add(nombre_heure);

		panel_grid.add(gamme_label);
		panel_grid.add(gamme);
		panel_grid.add(new JPanel());
		panel_grid.add(centre_de_travail_label);
		panel_grid.add(centre_de_travail);

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
				Gamme gamme_temp = null;
				String code_gamme = "";
				for (Gamme p : DatabaseProduction.getListeGamme())
				{
					if (p.getNom().equals(gamme.getSelectedItem().toString()))
					{
						gamme_temp = p;
						code_gamme = String.valueOf(p.getCode());
						break;
					}
				}

				CentreDeTravail centre_temp = null;
				String code_centre = "";
				for (CentreDeTravail p : DatabaseProduction.getListeCentreDeTravail())
				{
					if (p.getNom().equals(centre_de_travail.getSelectedItem().toString()))
					{
						centre_temp = p;
						code_centre = String.valueOf(p.getCode());
						break;
					}
				}

				//On tente de rentrer un nouveau produit
				if (DatabaseProduction.procedure("CALL nouvelleOperation ('"+nom.getText()+"', "+Integer.parseInt(sequence.getText())+", "+Float.parseFloat(nombre_cycle.getText())+", "+Float.parseFloat(nombre_heure.getText())+", "+code_gamme+", "+code_centre+")"))
				{
					//On recupere la clé primaire
					int code = DatabaseProduction.getSequence("SELECT seqoperation.currval FROM dual");
					
					if (code != -1)
					{
						//Creation du produit
						Operation operation = new Operation(code,nom.getText(),Integer.parseInt(sequence.getText()),Float.parseFloat(nombre_cycle.getText()), Float.parseFloat(nombre_heure.getText()), gamme_temp, centre_temp);
						
						//Ajout du produit dans la liste
						DatabaseProduction.getListeOperation().add(operation);
						
						panel_operation.raffraichirTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Une erreur interne est survenue, veuillez redémarrer le logiciel.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null, "Opération ajoutée avec succès !", "Ajout d'opération", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Erreur d'ajout d'opération, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}		
			}
		});
		
		remplirComboBox();
		
		panel_operation.getRootPane().setDefaultButton(ajouter);
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
    	model_gamme.removeAllElements();
    	model_centre_de_travail.removeAllElements();
    	
    	ArrayList<Gamme> gammes = DatabaseProduction.getListeGamme();
    	
		//Passage d'array list en array. Les attributs des objets sont récupérés
		for (Gamme object : gammes) 
		{
			model_gamme.addElement(object.getNom());
		}
		
		ArrayList<CentreDeTravail> centres = DatabaseProduction.getListeCentreDeTravail();
    	
		//Passage d'array list en array. Les attributs des objets sont récupérés
		for (CentreDeTravail object : centres) 
		{
			model_centre_de_travail.addElement(object.getNom());
		}

		repaint();
		revalidate();
    }
}