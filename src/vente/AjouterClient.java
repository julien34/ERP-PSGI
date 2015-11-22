package vente;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import principal.FenetrePrincipale;

/**
 * @author Simon
 *
 */
public class AjouterClient extends JDialog{
	private static FenetrePrincipale frame;
	JPanel contenuPanel = new JPanel(new GridLayout(8,8));
	/**
	 * elements dans la page d'ajout de nouveaux clients
	 * Libeles du nom, prenom, email, tel
	 */
	private JLabel lbl_id = new JLabel("Id");
	private JLabel lbl_nom = new JLabel("Nom");
	private JLabel lbl_prenom = new JLabel("Prenom");
	private JLabel lbl_adresse = new JLabel("Adresse");
	private JLabel lbl_email = new JLabel("Email");
	private JLabel lbl_tel = new JLabel("Telephone");
	private JLabel lbl_categorie = new JLabel("Categorie");
	
	/**
	 * text box pour le nom, prenom, adresse, email, telephone du nouveau client
	 */
	private JTextField txt_id = new JTextField(10);
	private JTextField txt_nom = new JTextField(20);
	private JTextField txt_prenom = new JTextField(20);
	private JTextField txt_adresse = new JTextField(20);
	private JTextField txt_email = new JTextField(20);
	private JTextField txt_tel = new JTextField(10);
	private JTextField txt_categorie = new JTextField(10);
	
	/**
	 * Bouton de validation et bouton d'annulation de la saisie d'un nouveau client
	 */
	private JButton bt_valider = new JButton("Valider");
	private JButton bt_annuler = new JButton("Annuler");
	
	public AjouterClient(FenetrePrincipale frame){
		super(frame,"Ajouter un client");
		this.frame = frame;
		add(contenuPanel);
		init();
	}
	
	public void init(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700,500);	
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		contenuPanel.add(lbl_id);
		contenuPanel.add(txt_id);
		contenuPanel.add(lbl_nom);
		contenuPanel.add(txt_nom);
		contenuPanel.add(lbl_prenom);
		contenuPanel.add(txt_prenom);
		contenuPanel.add(lbl_adresse);
		contenuPanel.add(txt_adresse);
		contenuPanel.add(lbl_email);
		contenuPanel.add(txt_email);
		contenuPanel.add(lbl_tel);
		contenuPanel.add(txt_tel);
		contenuPanel.add(lbl_categorie);
		contenuPanel.add(txt_categorie);
		
		contenuPanel.add(bt_valider);
		contenuPanel.add(bt_annuler);
	}
}
