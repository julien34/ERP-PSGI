package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;

import production.modele.Gamme;
import production.modele.Produit;
import jdbc.DatabaseProduction;

/**
 * Panel d'ajout de nomenclature
 * @author Nicolas
 *
 */
@SuppressWarnings("serial")
public class PanelModifierNomenclature extends JPanel
{	
	/**
	 * Le panel sur lequel celui-ci est implenté.
	 */
	private PanelNomenclature panel_nomenclature;
	
	/**
	 * Le panel qui contient tous les autres éléments.
	 */
	private JPanel panel_grid = new JPanel(new GridLayout(5,5,4,4));
	
	/**
	 * Label du champs nom.
	 */
	private JLabel nom_label = new JLabel("nom ");
	
	/**
	 * Champs texte contenant le nom de la nomenclature.
	 */
	private JTextField nom = new JTextField(10);

	/**
	 * Label du champs quantite.
	 */
	private JLabel quantite_label = new JLabel("quantite ");
	
	/**
	 * Champs texte contenant la quantite de la nomenclature.
	 */
	private JTextField quantite = new JTextField(10);
	
	/**
	 * Label du champs type.
	 */
	private JLabel type_label = new JLabel("type ");
	
	/**
	 * Champs texte contenant le type de la nomenclature.
	 */
	private JTextField type = new JTextField(10);
	
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
	 * Label du champs produit.
	 */
	private JLabel produit_label = new JLabel("produit ");
	
	/**
	 * Modele de la combobox du produit.
	 */
	private DefaultComboBoxModel<String> model_produit = new DefaultComboBoxModel<String>();
	
	/**
	 * La liste des produits disponibles.
	 */
	private JComboBox<String> produit = new JComboBox<String>(model_produit);

	/**
	 * Le code de la nomenclature
	 */
	private int code = -1;

	/**
	 * Bouton pour modifier le produit.
	 */
	private JButton modifier = new JButton("Modifier");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");
	
	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param PanelNomenclature Le panel sur lequel celui-ci est implenté
	 * @param code_arg Le code de la nomenclature
	 * @param nom_arg Le nom de la nomenclature
	 * @param quantite_arg La quantite de la nomenclature
	 * @param type_arg le type de la nomenclature
	 * @param gamme_arg la gamme de la nomenclature
	 * @param produit_arg le produit de la nomenclature
	 */
	public PanelModifierNomenclature(PanelNomenclature panel_nomenclature, int code_arg, String nom_arg, String quantite_arg, String type_arg, String gamme_arg, String produit_arg)
	{
		this.panel_nomenclature = panel_nomenclature;
		code = code_arg;
		nom.setText(nom_arg);
		quantite.setText(quantite_arg);
		type.setText(type_arg);
				
		//Ajouter les élements	
		add(panel_grid);
		panel_grid.add(nom_label);
		panel_grid.add(nom);
		panel_grid.add(new JPanel());
		panel_grid.add(quantite_label);
		panel_grid.add(quantite);

		panel_grid.add(type_label);
		panel_grid.add(type);
		panel_grid.add(new JPanel());
		panel_grid.add(gamme_label);
		panel_grid.add(gamme);

		panel_grid.add(produit_label);
		panel_grid.add(produit);
		panel_grid.add(new JPanel());
		panel_grid.add(new JPanel());
		panel_grid.add(new JPanel());

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
					ResultSet rs_nomenclature = DatabaseProduction.getRsNomenclature();
					rs_nomenclature.beforeFirst();
					
					while (rs_nomenclature.next())
					{
						if (rs_nomenclature.getInt("code") == code)
						{
							rs_nomenclature.updateString("nom", nom.getText());
							
							ResultSet rs_gamme = DatabaseProduction.getRsGamme();
							rs_gamme.beforeFirst();
							
							while (rs_gamme.next())
							{
								if (rs_gamme.getString("nom").equals(gamme.getSelectedItem().toString()))
								{
									rs_nomenclature.updateInt("gamme", rs_gamme.getInt("code"));
								}
							}
							
							ResultSet rs_produit = DatabaseProduction.getRsProduit();
							rs_produit.beforeFirst();
							
							while (rs_produit.next())
							{
								if (rs_produit.getString("nom").equals(produit.getSelectedItem().toString()))
								{
									rs_nomenclature.updateInt("produit", rs_produit.getInt("code"));
								}
							}
							
							rs_nomenclature.updateFloat("quantite", Float.parseFloat(quantite.getText()));
							rs_nomenclature.updateString("type", type.getText());
							
							rs_nomenclature.updateRow();
						}
					}
										
					panel_nomenclature.raffraichirTable();
					
					JOptionPane.showMessageDialog(null, "Nomenclature modifié avec succès !", "Modification de nomenclature", JOptionPane.INFORMATION_MESSAGE);			
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erreur d'ajout de la nomenclature, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}								
			}
		});
		
		remplirComboBox();
		gamme.setSelectedIndex(model_gamme.getIndexOf(gamme_arg));
		produit.setSelectedIndex(model_produit.getIndexOf(produit_arg));
		
		panel_nomenclature.getRootPane().setDefaultButton(modifier);
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
    	model_produit.removeAllElements();
    	
    	ArrayList<Gamme> gammes = DatabaseProduction.getListeGamme();

		//Passage d'array list en array. Les attributs des objets sont récupérés
		for (Gamme object : gammes) 
		{
			model_gamme.addElement(object.getNom());
		}
		
    	ArrayList<Produit> produits = DatabaseProduction.getListeProduit();

		//Passage d'array list en array. Les attributs des objets sont récupérés
		for (Produit object : produits) 
		{
			model_produit.addElement(object.getNom());
		}
		
		repaint();
		revalidate();
    }
}