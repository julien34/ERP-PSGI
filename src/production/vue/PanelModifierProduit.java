package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;

import production.modele.Categorie;
import production.modele.UniteDeMesure;
import jdbc.DatabaseProduction;

/**
 * Panel de modification de produit.
 *
 */
@SuppressWarnings("serial")
public class PanelModifierProduit extends JPanel
{	
	/**
	 * Le panel sur lequel celui-ci est implenté.
	 */
	private PanelProduit panel_produit;
	
	/**
	 * Le panel qui contient tous les autres éléments.
	 */
	private JPanel panel_grid = new JPanel(new GridLayout(5,5,4,4));
	
	/**
	 * Label du champs nom.
	 */
	private JLabel nom_label = new JLabel("nom ");
	
	/**
	 * Champs texte contenant le nom du produit.
	 */
	private JTextField nom = new JTextField(10);

	/**
	 * Label du champs categorie.
	 */
	private JLabel categorie_label = new JLabel("categorie ");
	
	/**
	 * Modele de la combobox de la categorie.
	 */
	private DefaultComboBoxModel<String> model_categorie = new DefaultComboBoxModel<String>();
	
	/**
	 * La liste des categories disponibles.
	 */
	private JComboBox<String> categorie = new JComboBox<String>(model_categorie);

	/**
	 * Label du champs prix d'achat.
	 */
	private JLabel prix_achat_label = new JLabel("prix d'achat ");
	
	/**
	 * Champs texte contenant le prix d'achat.
	 */
	private JTextField prix_achat = new JTextField(10);

	/**
	 * Label du champs prix de vente.
	 */
	private JLabel prix_vente_label = new JLabel("prix de vente ");
	
	/**
	 * Champs texte contenant le prix de vente.
	 */
	private JTextField prix_vente = new JTextField(10);

	/**
	 * Label du champs unité de mesure.
	 */
	private JLabel udm_label = new JLabel("unite de mesure ");
	
	/**
	 * Modele de la combobox de l'unité de mesure.
	 */
	private DefaultComboBoxModel<String> model_udm = new DefaultComboBoxModel<String>();
	
	/**
	 * La liste des unité de mesure disponibles.
	 */
	private JComboBox<String> udm = new JComboBox<String>(model_udm);

	/**
	 * Label du champs disponibilité.
	 */
	private JLabel disponibilite_label = new JLabel("achat / vente ");
	
	/**
	 * Les disponibilités prédéfinies.
	 */
	private final String[] items = {"Achat","Vente","Achat et vente"};
	
	/**
	 * Modele de la liste des disponibilités.
	 */
	private DefaultComboBoxModel<String> model_disponibilite = new DefaultComboBoxModel<String>(items);
	
	/**
	 * La liste des parents disponibles.
	 */
	private JComboBox<String> disponibilite = new JComboBox<String>(items);

	/**
	 * Le code de la produit.
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
	 * @param panel_produit Le panel sur lequel celui-ci est implenté
	 * @param code_arg Le code du produit
	 * @param nom_arg Le nom du produit
	 * @param categorie_arg La categorie du produit
	 * @param prix_achat_arg Le prix d'achat du produit
	 * @param prix_vente_arg Le prix de vente du produit
	 * @param unite_de_mesure_arg L'unité de mesure du produit
	 * @param disponibilite_arg La disponibilité du produit
	 */
	public PanelModifierProduit(PanelProduit panel_produit, int code_arg, String nom_arg, String categorie_arg, String prix_achat_arg, String prix_vente_arg, String unite_de_mesure_arg, String disponibilite_arg)
	{
		this.panel_produit = panel_produit;
		code = code_arg;
		nom.setText(nom_arg);
		prix_vente.setText(prix_vente_arg);
		prix_achat.setText(prix_achat_arg);
				
		//Ajouter les élements	
		add(panel_grid);
		panel_grid.add(nom_label);
		panel_grid.add(nom);
		panel_grid.add(new JPanel());
		panel_grid.add(categorie_label);
		panel_grid.add(categorie);

		panel_grid.add(prix_achat_label);
		panel_grid.add(prix_achat);
		panel_grid.add(new JPanel());
		panel_grid.add(prix_vente_label);
		panel_grid.add(prix_vente);

		panel_grid.add(udm_label);
		panel_grid.add(udm);
		panel_grid.add(new JPanel());
		panel_grid.add(disponibilite_label);
		panel_grid.add(disponibilite);

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
					ResultSet rs_produit = DatabaseProduction.getRsProduit();
					rs_produit.beforeFirst();
					
					while (rs_produit.next())
					{
						if (rs_produit.getInt("code") == code)
						{
							rs_produit.updateString("nom", nom.getText());
							
							ResultSet rs_categorie = DatabaseProduction.getRsCategorie();
							rs_categorie.beforeFirst();
							
							while (rs_categorie.next())
							{
								if (rs_categorie.getString("nom").equals(categorie.getSelectedItem().toString()))
								{
									rs_produit.updateInt("categorie", rs_categorie.getInt("code"));
								}
							}
							
							rs_produit.updateFloat("prixachat", Float.parseFloat(prix_achat.getText()));
							rs_produit.updateFloat("prixvente", Float.parseFloat(prix_vente.getText()));
							rs_produit.updateString("unitedemesure", udm.getSelectedItem().toString());							
							rs_produit.updateString("disponibilite", disponibilite.getSelectedItem().toString());
							
							rs_produit.updateRow();
						}
					}
										
					panel_produit.raffraichirTable();
					
					JOptionPane.showMessageDialog(null, "Produit modifié avec succès !", "Modification de produit", JOptionPane.INFORMATION_MESSAGE);			
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erreur d'ajout du produit, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}								
			}
		});
		
		remplirComboBox();
		categorie.setSelectedIndex(model_categorie.getIndexOf(categorie_arg));
		udm.setSelectedIndex(model_udm.getIndexOf(unite_de_mesure_arg));
		disponibilite.setSelectedIndex(model_disponibilite.getIndexOf(disponibilite_arg));
		
		panel_produit.getRootPane().setDefaultButton(modifier);
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
    	model_categorie.removeAllElements();
    	model_udm.removeAllElements();
    	
    	ArrayList<Categorie> categories = DatabaseProduction.getListeCategorie();

		//Passage d'array list en array. Les attributs des objets sont récupérés
		for (Categorie object : categories) 
		{
			model_categorie.addElement(object.getNom());
		}
		
    	ArrayList<UniteDeMesure> unites = DatabaseProduction.getListeUdm();

		//Passage d'array list en array. Les attributs des objets sont récupérés
		for (UniteDeMesure object : unites) 
		{
			model_udm.addElement(object.getNom());
		}
		
		repaint();
		revalidate();
    }
}