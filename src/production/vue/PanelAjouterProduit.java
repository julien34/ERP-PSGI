package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import production.modele.Categorie;
import production.modele.Produit;
import production.modele.UniteDeMesure;
import jdbc.DatabaseProduction;

/**
 * Ce panel permet d'ajouter un produit.
 */
@SuppressWarnings("serial")
public class PanelAjouterProduit extends JPanel
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
	 * La liste des parents disponibles.
	 */
	private JComboBox<String> disponibilite = new JComboBox<String>(items);
	
	/**
	 * Bouton pour ajouter le produit.
	 */
	private JButton ajouter = new JButton("Ajouter");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");

	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_produit Le panel sur lequel celui-ci est implenté.
	 */
	public PanelAjouterProduit(PanelProduit panel_produit)
	{	  
		this.panel_produit = panel_produit;
		
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
		panel_grid.add(ajouter);
		panel_grid.add(retour);
		
		//Action si click sur bouton
		ajouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Categorie categorie_temp = null;
				for (Categorie c : DatabaseProduction.getListeCategorie())
				{
					if (c.getNom() == categorie.getSelectedItem().toString())
					{
						categorie_temp = c;
						break;
					}
				}
				
				UniteDeMesure udm_temp = null;
				for (UniteDeMesure u : DatabaseProduction.getListeUdm())
				{
					if (u.getNom() == udm.getSelectedItem().toString())
					{
						udm_temp = u;
						break;
					}
				}
				
				//On tente de rentrer un nouveau produit
				if (DatabaseProduction.procedure("CALL nouveauProduit ('"+nom.getText()+"', '"+categorie_temp.getCode()+"', "+Float.parseFloat(prix_achat.getText())+", "+Float.parseFloat(prix_vente.getText())+", '"+udm_temp.getNom()+"', '"+disponibilite.getSelectedItem().toString()+"')"))
				{
					//On recupere la clé primaire
					int code = DatabaseProduction.getSequence("SELECT seqproduit.currval FROM dual");
					
					if (code != -1)
					{
						//Creation du produit
						Produit produit = new Produit(code,nom.getText(),categorie_temp,Float.parseFloat(prix_vente.getText()),Float.parseFloat(prix_achat.getText()),udm_temp,disponibilite.getSelectedItem().toString());
						
						//Ajout du produit dans la liste
						DatabaseProduction.getListeProduit().add(produit);
						
						panel_produit.raffraichirTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Une erreur interne est survenue, veuillez redémarrer le logiciel.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null, "Produit ajouté avec succès !", "Ajout de produit", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Erreur d'ajout du produit, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		
		remplirComboBox();
		
		panel_produit.getRootPane().setDefaultButton(ajouter);
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