package vente;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import jdbc.DatabaseConnection;
import achat.modeles.Fournisseur;

public class VenteSelectionClient extends JFrame{
	private static Fournisseur fn;
	private JButton btnValider, btnAnnuler;
	private ScrollPane scrollPane;
	private ArrayList<Fournisseur> liste = new ArrayList<Fournisseur>();
	private DefaultListModel<String> dLM = new DefaultListModel<String>();
	private JList<String> jList = new JList<String>(this.dLM);
	
	/**
	 * Constructeur sans param�tre de la classe.
	 */
	public VenteSelectionClient(){
		this.getClient();//On r�cup�re les Client sur la base de donn�es
		this.initFenetre();//On initie la fenetre
		this.initComposants();//On initie les composants de la fenetre
		this.initEcouteurs();
	}
	
	
	/**
	 * M�thode qui initialise la fenetre d'ajout de Client � une commande.
	 */
	private void initFenetre(){
		this.setTitle("S�lectionner un Client � affecter � la commande");//Titre de la fenetre
		this.setSize(450, 200);//Taille de la fenetre
		
		Dimension dim = new Dimension(450,250);//Dimension pour dimension minimale
		this.setMinimumSize(dim);//On d�fini la taille minimale de la fenetre
		this.setLocationRelativeTo(null);//On centre la fenetre
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//fermeture lors du clic sur la croix rouge
		this.setVisible(true);//rendre la fenetre visible
	}
	
	
	/**
	 * M�thode qui initialise l'ensemble des composants.
	 */
	private void initComposants(){
		
		//Initialisation des diff�rents panels
		JPanel panelPrincipal = new JPanel(new BorderLayout());//Principal
		JPanel panelListe = new JPanel();//Celui qui accueillera la liste
		JPanel panelBtn = new JPanel();//Celui qui accueillera les boutons
		
		//Initialisation des composants
		this.btnValider = new JButton("Valider");
		this.btnAnnuler = new JButton("Annuler");
		this.btnValider.setEnabled(false);//On grise le bouton valider
		this.scrollPane = new ScrollPane();
		this.scrollPane.setSize(450, 180);
		
		//Ajouts des composants aux diff�rents panels
		this.scrollPane.add(this.jList);
		panelListe.add(scrollPane);
		panelBtn.add(this.btnValider);
		panelBtn.add(this.btnAnnuler);
		
		//On ajoute l'ensemble des panels � la frame
		panelPrincipal.add(panelListe, BorderLayout.CENTER);
		panelPrincipal.add(panelBtn, BorderLayout.SOUTH);
		this.add(panelPrincipal);
	}
	
	
	/**
	 * M�thode qui r�cup�re l'ensemble des Clients de la base de donn�es et les ajoute dans l'arrayList.
	 */
	private void getClient() {

	}
	
	
	/**
	 * M�thode qui assigne le Client s�lectionn� au Client courant.
	 * @param f, un Client.
	 * @return le Client selectionn�.
	 */
	private static void selectClient(Client f){

	}
	
	
	/**
	 * M�thode qui initialise les �couteurs.
	 */
	private void initEcouteurs(){
		
		//Bouton ajouter
		this.btnValider.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			//	VenteSelectionClient.selectClient(VenteSelectionClient.this.liste.get(VenteSelectionClient.this.jList.getSelectedIndex()));
				dispose();
			}
		});
		
		//Bouton annuler
		this.btnAnnuler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		//JListe
		this.jList.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				VenteSelectionClient.this.btnValider.setEnabled(true);//On active le bouton valider
				
				//Double clic
				if(e.getClickCount()%2==0){
					//VenteSelectionClient.selectClient(VenteSelectionClient.this.liste.get(VenteSelectionClient.this.jList.getSelectedIndex()));
					dispose();
				}
			}
		});
	}
}
