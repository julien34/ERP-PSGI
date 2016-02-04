package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import production.modele.Gamme;
import production.modele.Nomenclature;
import production.modele.Produit;
import jdbc.DatabaseProduction;

/**
 * Ce panel permet d'ajouter une nomenclature.
 */
@SuppressWarnings("serial")
public class PanelAjouterNomenclature extends JPanel
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
	 * Bouton pour ajouter la nomenclature.
	 */
	private JButton ajouter = new JButton("Ajouter");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");
	
	/**
	 * Le constructeur par défaut qui ajoute les elements du panel
	 */
	public PanelAjouterNomenclature(PanelNomenclature panel_nomenclature)
	{	  
		this.panel_nomenclature = panel_nomenclature;
		
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
		panel_grid.add(ajouter);
		panel_grid.add(retour);
		
		//Action si click sur bouton
		ajouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Gamme gamme_temp = null;
				for (Gamme g : DatabaseProduction.getListeGamme())
				{
					if (g.getNom() == gamme.getSelectedItem().toString())
					{
						gamme_temp = g;
						break;
					}
				}
				
				Produit produit_temp = null;
				for (Produit p : DatabaseProduction.getListeProduit())
				{
					if (p.getNom() == produit.getSelectedItem().toString())
					{
						produit_temp = p;
						break;
					}
				}
				
				//On tente de rentrer une nouvelle nomenclature
				if (DatabaseProduction.procedure("CALL nouvelleNomenclature('"+nom.getText()+"', "+Float.parseFloat(quantite.getText())+", '"+type.getText()+"', "+gamme_temp.getCode()+", "+produit_temp.getCode()+")"))
				{
					//On recupere la clé primaire
					int code = DatabaseProduction.getSequence("SELECT seqNomenclature.currval FROM dual");
					
					if (code != -1)
					{
						//Creation de la nomenclature
						Nomenclature nomenclature = new Nomenclature(code, nom.getText(), Float.parseFloat(quantite.getText()), type.getText(), gamme_temp, produit_temp);
						//Ajout de la nomenclature dans la liste
						DatabaseProduction.getListeNomenclature().add(nomenclature);
						
						panel_nomenclature.raffraichirTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Une erreur interne est survenue, veuillez redémarrer le logiciel.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null, "Nomenclature ajouté avec succès !", "Ajout de nomenclature", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Erreur d'ajout de la nomenclature, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		
		remplirComboBox();
		
		panel_nomenclature.getRootPane().setDefaultButton(ajouter);
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