package production.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import production.modele.Categorie;
import jdbc.DatabaseProduction;

/**
 * Ce panel permet d'ajouter une categorie.
 */
@SuppressWarnings("serial")
public class PanelAjouterCategorieProduit extends JDialog
{	
	/**
	 * Le panel sur lequel celui-ci est implenté.
	 */
	private PanelCategorieProduit panel_categorie;
	
	/**
	 * Le panel qui contient tous les autres éléments.
	 */
	private JPanel panel_grid = new JPanel(new GridLayout(5,3,4,4));
	private JPanel  principal = new JPanel(new GridLayout(15,8));
	private JPanel button = new JPanel(new BorderLayout(5,5));
	private JPanel txt = new JPanel (new BorderLayout(5,5));
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
	 * Bouton pour ajouter la catégorie.
	 */
	private JButton ajouter = new JButton("Ajouter");
	
	/**
	 * Bouton pour fermer ce panel.
	 */
	private JButton retour = new JButton("Retour");

	/**
	 * Le constructeur par défaut qui ajoute les elements du panel.
	 * @param panel_categorie Le panel sur lequel celui-ci est implenté.
	 */
	public PanelAjouterCategorieProduit(PanelCategorieProduit panel_categorie)
	{	 
		
		this.panel_categorie = panel_categorie;
		
	
		initFenetre();
		
		//Ajouter les élements	
		add(panel_grid);
		panel_grid.setLayout(new GridLayout());
		panel_grid.add(principal);
		
		principal.add(txt);
		principal.add(button);
		
		txt.add(nom_label);
		txt.add(nom);
		txt.add(parent_label);
		txt.add(parent);
		
		button.add(ajouter);
		button.add(retour);
	
		principal.add(nom_label);
		principal.add(nom);
		principal.add(new JPanel());
		principal.add(parent_label);
		principal.add(parent);

		principal.add(new JPanel());
		principal.add(new JPanel());
		principal.add(new JPanel());
		principal.add(new JPanel());
		principal.add(new JPanel());
		
		principal.add(new JPanel());
		principal.add(new JPanel());
		principal.add(new JPanel());
		principal.add(ajouter);
		principal.add(retour);
		
		//Action si click sur bouton
		ajouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Categorie parent_temp = null;
				String code_parent = "";
				for (Categorie p : DatabaseProduction.getListeCategorie())
				{
					if (p.getNom().equals(parent.getSelectedItem().toString()))
					{
						parent_temp = p;
						code_parent = String.valueOf(p.getCode());
						break;
					}
				}

				//On tente de rentrer un nouveau produit
				if (DatabaseProduction.procedure("CALL nouvelleCategorie ('"+nom.getText()+"', '"+code_parent+"')"))
				{
					//On recupere la clé primaire
					int code = DatabaseProduction.getSequence("SELECT seqcategorie.currval FROM dual");
					
					if (code != -1)
					{
						//Creation du produit
						Categorie categorie = new Categorie(code,nom.getText(),parent_temp);
						
						//Ajout du produit dans la liste
						DatabaseProduction.getListeCategorie().add(categorie);
						
						panel_categorie.raffraichirTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Une erreur interne est survenue, veuillez redémarrer le logiciel.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null, "Categorie ajoutée avec succès !", "Ajout de categorie", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Erreur d'ajout du categorie, vérifiez les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		
		remplirComboBox();
		
		panel_categorie.getRootPane().setDefaultButton(ajouter);
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
		this.setTitle("Ajouter une catégorie de Produit");
		this.setResizable(false);
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
}