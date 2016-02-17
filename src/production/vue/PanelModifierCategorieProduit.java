package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;

import production.modele.Categorie;
import jdbc.DatabaseProduction;

/**
 * Panel de modification de categorie.
 *
 */
@SuppressWarnings("serial")
public class PanelModifierCategorieProduit extends JDialog
{	
	/**
	 * Le panel sur lequel celui-ci est implenté.
	 */
	private PanelCategorieProduit panel_categorie;

	/**
	 * Le panel qui contient tous les autres éléments.
	 */
	private JPanel panel_grid = new JPanel(new GridLayout(6,6));
	
	/**
	 * Label du champs nom.
	 */
	private JLabel nom_label = new JLabel("nom ");
	
	/**
	 * Champs texte contenant le nom de la catégorie.
	 */
	private JTextField nom = new JTextField(10);

	/**
	 * Label du champs parent.
	 */
	private JLabel parent_label = new JLabel("parent ");
	
	/**
	 * Modele de la combobox du parent.
	 */
	private DefaultComboBoxModel<String> model_parent = new DefaultComboBoxModel<String>();
	
	/**
	 * La liste des parents disponibles.
	 */
	private JComboBox<String> parent = new JComboBox<String>(model_parent);
	
	/**
	 * Le code de la categorie.
	 */
	private int code = -1;	

	/**
	 * Bouton pour modifier la catégorie.
	 */
	private JButton modifier = new JButton("Modifier");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");
	
	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_categorie Le panel sur lequel celui-ci est implenté
	 * @param code_arg Le code de la categorie.
	 * @param nom_arg Le nom de la categorie.
	 * @param parent_arg Le parent de la categorie.
	 */
	public PanelModifierCategorieProduit(PanelCategorieProduit panel_categorie, int code_arg, String nom_arg, String parent_arg)
	{
		this.panel_categorie = panel_categorie;
		code = code_arg;
		nom.setText(nom_arg);		
		
		initFenetre();
		//Ajouter les élements	
		add(panel_grid);
		panel_grid.add(nom_label);
		panel_grid.add(nom);
		panel_grid.add(new JPanel());
		panel_grid.add(parent_label);
		panel_grid.add(parent);

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
					ResultSet rs_categorie = DatabaseProduction.getRsCategorie();
					rs_categorie.beforeFirst();
					
					while (rs_categorie.next())
					{
						if (rs_categorie.getInt("code") == code)
						{
							DatabaseProduction.getRsCategorie().updateString("nom", nom.getText());
							for (Categorie categorie : DatabaseProduction.getListeCategorie()) {
								if(categorie.getNom().equals(parent.getSelectedItem().toString())){
									rs_categorie.updateInt("parent", categorie.getCode());
								}
							}
							rs_categorie.updateRow();
						}
					}
										
					panel_categorie.raffraichirTable();
					
					JOptionPane.showMessageDialog(null, "Categorie modifiée avec succès !", "Modification de categorie", JOptionPane.INFORMATION_MESSAGE);			
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erreur d'ajout de categorie, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}						
			}
		});
		
		remplirComboBox();
		parent.setSelectedIndex(model_parent.getIndexOf(parent_arg));
		
		panel_categorie.getRootPane().setDefaultButton(modifier);
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
    	model_parent.removeAllElements();
    	
    	ArrayList<Categorie> parents = DatabaseProduction.getListeCategorie();
    	
    	//Premier choix de parent vide par défaut si on ne souhaite pas avoir de parent
		model_parent.addElement("");

		//Passage d'array list en array. Les attributs des objets sont récupérés
		for (Categorie object : parents) 
		{
			model_parent.addElement(object.getNom());
		}

		repaint();
		revalidate();
    }
    private void initFenetre(){
		this.setTitle("Modifier une catégorie de Produit");
		this.setResizable(false);
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}