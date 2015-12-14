package achat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import achat.popup.PopupCommande;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JCalendarBeanInfo;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDayChooser;
import com.toedter.plaf.JCalendarTheme;

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelCommande extends JPanel{
	
	private ArrayList<CommandesFournisseur> listeCommandes = new ArrayList<CommandesFournisseur>();
	private Object[][] tabCo;
	private Object[] titres = {"N° Commande","Fournisseur","Date", "Montant"};
	
	//On créer la JTable et son modèle
	private static JTable tableau = new JTable(new DefaultTableModel());
	private static UneditableTableModel modele;
	private JScrollPane scrollPane;
	
	//On créer les composants que 'on va se servir dans plusieurs méthodes :
	private JButton btnNouveau, btnModifier, btnAnnuler;

	public PanelCommande(FenetrePrincipale f){
		
		//On récupère l'ensemble des founisseurs présents dans la BDD
		this.getCommande();
				
		//On remplit la JTable
		this.remplirTableau();
				
		//On initialise l'ensemble des composants sur le JPanel
		this.initElements();
				
		//On initialise l'ensemble des écouteurs
		this.initEcouteurs();
	}
	
	
	/**
	 * M�thode qui r�cup�re l'ensemble des commandes de la base de donn�es et les ajoute dans une ArrayList.
	 */
	private void getCommande(){
		
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT c.refCommande, c.dateCommande, f.refFournisseur, f.nomFournisseur, SUM(p.prixProduit*lc.quantite) AS montantTotal FROM CommandesFournisseur c JOIN Fournisseurs f ON f.refFournisseur = c.refFournisseur JOIN LignesCommandeFournisseur lc ON lc.refCommande = c.refCommande JOIN ProduitsFournisseur p ON p.refProduit = lc.refProduit GROUP BY c.refCommande, c.dateCommande, f.refFournisseur, f.nomFournisseur ORDER BY c.dateCommande DESC");
			ResultSet rs = pst.executeQuery();
			
			
			while(rs.next()){

				String refCommande = rs.getString("refCommande");
				Date date = rs.getDate("dateCommande");
				String refFournisseur = rs.getString("refFournisseur");
				String nomFournisseur = rs.getString("nomFournisseur");
				String montantTotal = rs.getString("montantTotal")+" �";

				this.listeCommandes.add(new CommandesFournisseur(refCommande, date, refFournisseur, nomFournisseur, montantTotal));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Méthode qui remplit le tableau avec les valeurs de l'arraylist, mais aussi la JTable.
	 */
	private void remplirTableau(){
		
		int nbDeCo = this.listeCommandes.size();//On calcule la taille de l'arrylist pour créer un tableau adéquat
		this.tabCo = new Object[nbDeCo][4];//On créer le tableau de la taille récupérée 
		
		//On remplit ce dernier avec les CommandesFournisseur récupérées
		for(CommandesFournisseur cf : this.listeCommandes){
			this.tabCo[this.listeCommandes.indexOf(cf)][0] = cf.getRefCommande();
			this.tabCo[this.listeCommandes.indexOf(cf)][1] = cf.getNomFourniseur();
			this.tabCo[this.listeCommandes.indexOf(cf)][2] = cf.getDate();
			this.tabCo[this.listeCommandes.indexOf(cf)][3] = cf.getMontantTotal();
		}
		
		
		modele = new UneditableTableModel(0,5);
		modele.setDataVector(tabCo,titres);
		
		tableau = new JTable(modele);
		
		tableau.setAutoCreateRowSorter(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setPreferredScrollableViewportSize(new Dimension(800, 224));
		
		this.scrollPane = new JScrollPane(tableau);
	}
	
	
	/**
	 * Méthode qui initialise les éléments de toute la page.
	 */
	private void initElements(){
		
		//on défini le layout du JPanel principal
		this.setLayout(new BorderLayout());
		
		//On créer les panels
		JPanel panelRechercheNord = new JPanel();
		
		JPanel panelGrilleCentre = new JPanel(new GridLayout(2,1));
		JPanel panelGrille = new JPanel();
		JPanel panelBouton = new JPanel();
		
		
		//On créer les composants
		JLabel lblRechercheCommande = new JLabel("N� Commande : ");
		JTextField txtRechercheCommande = new JTextField(10);
		JLabel lblRechercheFournisseur = new JLabel("Fournisseur : ");
		JTextField txtRechercheFournisseur = new JTextField(10);
		JLabel lblRechercheDate = new JLabel("Date : ");
		JDateChooser jdcDate = new JDateChooser();
		JLabel lblRechercheMontant = new JLabel("Montant : ");
		JTextField txtRechercheMontant = new JTextField(5);
		
		this.btnNouveau = new JButton("Nouveau");
		this.btnModifier = new JButton("Modifier");
		this.btnAnnuler = new JButton("Annuler");
		
		//On grise l'accès aux boutons modifier et annuler tant qu'une ligne n'est pas sélectionnée
		this.btnModifier.setEnabled(false);
		this.btnAnnuler.setEnabled(false);
		
		
		//On ajoute les composants aux panels
		panelRechercheNord.add(lblRechercheCommande);
		panelRechercheNord.add(txtRechercheCommande);
		panelRechercheNord.add(lblRechercheFournisseur);
		panelRechercheNord.add(txtRechercheFournisseur);
		panelRechercheNord.add(lblRechercheDate);
		panelRechercheNord.add(jdcDate);
		panelRechercheNord.add(lblRechercheMontant);
		panelRechercheNord.add(txtRechercheMontant);
		
		panelGrille.add(this.scrollPane);
		panelBouton.add(this.btnNouveau);
		panelBouton.add(this.btnModifier);
		panelBouton.add(this.btnAnnuler);
		
		panelGrilleCentre.add(panelGrille);
		panelGrilleCentre.add(panelBouton);
		
		//On ajoute les panels au panel principal
		this.add(panelRechercheNord, BorderLayout.NORTH);
		this.add(panelGrilleCentre, BorderLayout.CENTER);
		
	}
	
	
	/**
	 * Méthode qui initialise les écouteurs.
	 */
	private void initEcouteurs(){
		
		//Clic et double clic sur une ligne
		tableau.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				btnModifier.setEnabled(true);
				btnAnnuler.setEnabled(true);
				
				if (e.getClickCount()%2 == 0){
					new PopupCommande(listeCommandes.get(tableau.getSelectedRow()));
				}
			}
		});
		
		
		//Bouton "Nouveau"
		this.btnNouveau.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupCommande();
			}
		});
		
		
		//Bouton "Modifier"
		this.btnModifier.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupCommande(listeCommandes.get(tableau.getSelectedRow()));
			}
		});
	}
}
