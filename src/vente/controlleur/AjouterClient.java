package vente.controlleur;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class AjouterClient extends JDialog{

	private static FenetrePrincipale frame;
	JPanel contenuPanel = new JPanel(new GridLayout(5,1));
	JPanel panelFlow1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelFlow2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelFlow3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelFlow4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelFlow5 = new JPanel(new FlowLayout(FlowLayout.CENTER));

	//elements dans la page d'ajout de nouveaux clients
	//Libeles du nom, prenom, email, tel
	JLabel lbl_id = new JLabel("Id");
	JLabel lbl_nom = new JLabel("Nom");
	JLabel lbl_prenom = new JLabel("Prenom");
	JLabel lbl_adresse = new JLabel("Adresse");
	JLabel lbl_email = new JLabel("Email");
	JLabel lbl_tel = new JLabel("Telephone");
	JLabel lbl_categorie = new JLabel("Categorie");
	JLabel lbl_vide = new JLabel(" ");

	//text box pour le nom, prenom, adresse, email, telephone du nouveau client
	JTextField txt_id = new JTextField(10);
	JTextField txt_nom = new JTextField(10);
	JTextField txt_prenom = new JTextField(10);
	JTextField txt_adresse = new JTextField(10);
	JTextField txt_email = new JTextField(10);
	JTextField txt_tel = new JTextField(10);
	JTextField txt_categorie = new JTextField(10);
	private Choice chCategorie;
	
	//Bouton de validation et bouton d'annulation de la saisie d'un nouveau client
	private JButton bt_valider = new JButton("Valider");
	private JButton bt_annuler = new JButton("Annuler");

	public AjouterClient(FenetrePrincipale frame){
		super(frame,"Ajouter un client");
		this.frame = frame;

		initFrame();
		initElement();
		initHandlers();
	}

	/**
	 * Initialise la fenetre
	 */
	public void initFrame(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(512,288);	
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	/**
	 * Initialise les elements
	 */
	public void initElement(){
		add(contenuPanel);
		contenuPanel.add(panelFlow1);
		contenuPanel.add(panelFlow2);
		contenuPanel.add(panelFlow3);
		contenuPanel.add(panelFlow4);
		contenuPanel.add(panelFlow5);
		/*panelFlow1.add(lbl_id);
		 panelFlow1.add(txt_id);*/
		chCategorie = new Choice();
		chCategorie.add("Particulier");
		chCategorie.add("Entreprise");
		panelFlow1.add(lbl_nom);
		panelFlow1.add(txt_nom);
		panelFlow1.add(lbl_prenom);
		panelFlow1.add(txt_prenom);
		panelFlow2.add(lbl_adresse);
		panelFlow2.add(txt_adresse);
		panelFlow2.add(lbl_email);
		panelFlow2.add(txt_email);
		panelFlow3.add(lbl_tel);
		panelFlow3.add(txt_tel);
		panelFlow3.add(lbl_categorie);
		panelFlow3.add(chCategorie);
		panelFlow4.add(lbl_vide);
		panelFlow4.add(lbl_vide);
		panelFlow4.add(lbl_vide);
		panelFlow4.add(lbl_vide);
		panelFlow5.add(bt_valider);
		panelFlow5.add(bt_annuler);
		setVisible(true);
	}

	/**
	 * gere les listeners
	 */
	public void initHandlers(){
		txt_id.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				super.keyReleased(e);
				if(txt_id.getText().length() > 0){
					bt_valider.setEnabled(true);
				}
				else bt_valider.setEnabled(false);
			}
		});

		bt_annuler.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});

		bt_valider.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String id = txt_id.getText();
				String nom = txt_nom.getText();
				String prenom = txt_prenom.getText();
				String adresse = txt_adresse.getText();
				String email = txt_email.getText();
				String tel = txt_tel.getText();
				String categorie = null;
				if(chCategorie.getSelectedItem().equals("Entreprise")) categorie = "2";
				else if(chCategorie.getSelectedItem().equals("Particulier"))categorie = "1";

				if(DatabaseConnection.requete("INSERT INTO VENTE_CLIENTS(idclient, nomclient, prenomclient, adresseclient, emailclient, telclient, codecategorieclient) VALUES (sequence_client.NEXTVAL,'"+nom+"','"+prenom+"','"+adresse+"','"+email+"','"+tel+"','"+categorie+"')")){
					frame.getPanelClient().remplirtableClient();
					setVisible(false);
				}
				else{
					System.out.println("La requete ne c'est pas executer correctement.");
				}
			}
		});
	}
}
