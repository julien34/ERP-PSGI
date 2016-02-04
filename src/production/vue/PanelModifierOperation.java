package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;

import production.modele.CentreDeTravail;
import production.modele.Gamme;
import jdbc.DatabaseProduction;

/**
 * Panel de modification d'opération.
 *
 */
@SuppressWarnings("serial")
public class PanelModifierOperation extends JPanel
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
	 * Le code de la categorie.
	 */
	private int code = -1;
	
	/**
	 * Bouton pour modifier l'opération.
	 */
	private JButton modifier = new JButton("Modifier");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");

	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_operation Le panel sur lequel celui-ci est implenté
	 * @param code_arg Le code l'operation
	 * @param nom_arg Le nom de l'operation
	 * @param sequence_arg La séquence de l'operation
	 * @param nombre_cycle_arg Le nombre de cycle de l'operation
	 * @param nombre_heure_arg Le nombre d'heures de l'operation
	 * @param gamme_arg La gamme de l'operation
	 * @param centre_de_travail_arg Le centre de travail de l'operation
	 */
	public PanelModifierOperation(PanelOperation panel_operation, int code_arg, String nom_arg, int sequence_arg, float nombre_cycle_arg, float nombre_heure_arg, String gamme_arg, String centre_de_travail_arg)
	{
		this.panel_operation = panel_operation;
		code = code_arg;
		nom.setText(nom_arg);
		sequence.setText(String.valueOf(sequence_arg));
		nombre_cycle.setText(String.valueOf(nombre_cycle_arg));
		nombre_heure.setText(String.valueOf(nombre_heure_arg));

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
		panel_grid.add(modifier);
		panel_grid.add(retour);
		
		//Action si click sur bouton
		modifier.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{				
				try
				{
					ResultSet rs_operation = DatabaseProduction.getRsOperation();
					rs_operation.beforeFirst();
					
					while (rs_operation.next())
					{
						if (rs_operation.getInt("code") == code)
						{
							rs_operation.updateString("nom", nom.getText());
							rs_operation.updateInt("sequence", Integer.parseInt(sequence.getText()));
							rs_operation.updateFloat("nombrecycle", Float.parseFloat(nombre_cycle.getText()));
							rs_operation.updateFloat("nombreheure", Float.parseFloat(nombre_heure.getText()));
							
							ResultSet rs_gamme = DatabaseProduction.getRsGamme();
							rs_gamme.beforeFirst();

							while (rs_gamme.next())
							{
								if (rs_gamme.getString("nom").equals(gamme.getSelectedItem().toString()))
								{
									rs_operation.updateInt("gamme", rs_gamme.getInt("code"));
								}
							}
							
							ResultSet rs_centre = DatabaseProduction.getRsCentre();
							rs_centre.beforeFirst();

							while (rs_centre.next())
							{
								if (rs_centre.getString("nom").equals(centre_de_travail.getSelectedItem().toString()))
								{
									rs_operation.updateInt("centre", rs_centre.getInt("code"));
								}
							}
							
							rs_operation.updateRow();
						}
					}
										
					panel_operation.raffraichirTable();
					
					JOptionPane.showMessageDialog(null, "Opération modifiée avec succès !", "Modification d'opération", JOptionPane.INFORMATION_MESSAGE);			
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erreur d'ajout d'opération, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}					
			}
		});
		
		remplirComboBox();
		gamme.setSelectedIndex(model_gamme.getIndexOf(gamme_arg));
		centre_de_travail.setSelectedIndex(model_centre_de_travail.getIndexOf(centre_de_travail_arg));
				
		panel_operation.getRootPane().setDefaultButton(modifier);
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