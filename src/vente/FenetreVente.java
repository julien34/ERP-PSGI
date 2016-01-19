package vente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.beans.Statement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer.Form;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import achat.CommandesFournisseur;
import achat.Fournisseur;

import javax.swing.event.CaretEvent;

import java.lang.*;

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class FenetreVente extends JPanel{
	
	
	private static FenetrePrincipale frame;
	
	
	//Init des elements information client 
	
	JLabel InformationClient = new JLabel("Information Client");
	JLabel ChampVide = new JLabel("");
	JLabel ChampTextNom = new JLabel("Nom");
	 JComboBox ComboBoxClient = new JComboBox();
	JLabel Prenom = new JLabel("Responsable : ");
	 JTextField ChampTextPrenom = new JTextField("");
	JLabel Adresse = new JLabel("Adresse : ");
	 JTextField ChampTextAdresse = new JTextField("");
	JLabel Email = new JLabel("Email : ");
	 JTextField ChampTextEmail = new JTextField("");
	JLabel NumeroTelephone = new JLabel("NumeroTelephone : ");
	 JTextField ChampTextNumeroTelephone = new JTextField("");  	 
	
	private Dimension dimensionTextField = new Dimension (180 , 26);

	
	
	
	//init des elements tableau ? ? ? 
	
	
	private static ArrayList<Commande> liste = new ArrayList<Commande>();
	private static String[] titres = {"Nom","PrixUnitaire","Quantité","PrixTotal"};
	private static Object[][] tabFn;
	
	JScrollPane scrollPane =  new JScrollPane();
	JTable tableCommande;
	private static JTable tableLigne;
	private Dimension dimensionTable = new Dimension(600, 150);

	private final static  String[] nomColonnes = { "Ref","Nom","Quantite","PrixUnitaire","PrixHorsTaxe"};

	private static DefaultTableModel modelTableCommande = new DefaultTableModel(0,5){
		Class[] types = {String.class, String.class, int.class,Float.class,Float.class};
		
        @Override
        public Class getColumnClass(int columnIndex) 
        {
            return Integer.class;
        }
        
        @Override
        public boolean isCellEditable(int row, int column)
        {  
            return false;  
        }
	};

	
	public static void remplirTabLigneCmd(){
		
		modelTableCommande.setDataVector(DatabaseConnection.remplirLigneCmd(), nomColonnes);
		tableLigne.getRowSorter().toggleSortOrder(0);	
		tableLigne.getRowSorter().toggleSortOrder(1);	
	}
	
	
	
	
	//Init des elements comptables
	
	
	int TotalHorsTaxe = 0;
	
	JLabel TotalHT = new JLabel("Total HT : ");
	int valeurHT = TotalHorsTaxe;
	JTextField ChampTotalHT = new JTextField(""+valeurHT);	
	
	JLabel TVA = new JLabel("TVA : ");
	double valeurTVA;
	JTextField ChampTVA = new JTextField(""+valeurTVA);	 
	
	JLabel TotalTTC = new JLabel("Total TCC : ");
	double valeurTTC; 
	JTextField ChampTotalTTC = new JTextField(""+valeurTTC);
	
	
	
	//init boutons
	
	
	
	

	JButton AjouterProduit = new JButton("Ajouter");
	JButton ModifierProduit = new JButton("Modifier");
	JButton SupprimerProduit = new JButton("Supprimer");
	JButton Valider = new JButton("Valider");	  
	JButton Annuler = new JButton("Annuler");
	

	private Dimension dimensionBouton = new Dimension(100, 30);

	
	
	
	int Etat = 0;//1 pour Particulier   2 pour Entreprise
	

	
	public FenetreVente(FenetrePrincipale frame) {
	this.frame = frame;
	initElements();

	
	}
	
	
	public void remplirtableCommande(){
		modelTableCommande.setDataVector(DatabaseConnection.remplirListeClient(), nomColonnes);
		tableCommande.getRowSorter().toggleSortOrder(0);
	}

	
	 public  void remplirNomParticulier(){
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT nomclient FROM VENTE_CLIENTS WHERE codecategorieclient = '1'");
			ResultSet rs =  pst.executeQuery();
			while(rs.next()){	
				
		     	ComboBoxClient.addItem(rs.getString(1));			

				}
		
				rs.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}	
	}
	 
	 public  void remplirNomEntreprise(){
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT nomclient FROM VENTE_CLIENTS WHERE codecategorieclient = '2'");
			ResultSet rs =  pst.executeQuery();
			while(rs.next()){	
				

		     	ComboBoxClient.addItem(rs.getString(1));			

				}
		
				rs.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}	
	}
	 
	
	
	 public  void remplirInfoParticulier(){
			try {
				Connection cn = DatabaseConnection.getCon();
				PreparedStatement pst = cn.prepareStatement("SELECT * FROM VENTE_CLIENTS WHERE codecategorieclient = '1'");
				ResultSet rs =  pst.executeQuery();
				while(rs.next()){	
					
					int numeroclientrequete = rs.getRow();
					int numeroclientcombo = ComboBoxClient.getSelectedIndex();
					
					String idclient = rs.getString(1);
					String nomclient = rs.getString(2);
					String prenomclient = rs.getString(3);
					String adresseclient = rs.getString(4);
					String mail = rs.getString(5);
					String telclient = rs.getString(6);
					String categorie = rs.getString(7);

					if(numeroclientrequete-1 == numeroclientcombo)
					{
							ChampTextEmail.setText(mail);
							ChampTextAdresse.setText(adresseclient);
							ChampTextNumeroTelephone.setText(telclient);
							ChampTextPrenom.setText(prenomclient);
						
					}
					
				}
					rs.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}	
	 }
	 
	 
	 public  void remplirInfoEntreprise(){
			try {
				Connection cn = DatabaseConnection.getCon();
				PreparedStatement pst = cn.prepareStatement("SELECT * FROM VENTE_CLIENTS WHERE codecategorieclient = '2'");
				ResultSet rs =  pst.executeQuery();
				while(rs.next()){	
					
					int numeroclientrequete = rs.getRow();
					int numeroclientcombo = ComboBoxClient.getSelectedIndex();
					
					String idclient = rs.getString(1);
					String nomclient = rs.getString(2);
					String prenomclient = rs.getString(3);
					String adresseclient = rs.getString(4);
					String mail = rs.getString(5);
					String telclient = rs.getString(6);
					String categorie = rs.getString(7);

					if(numeroclientrequete-1 == numeroclientcombo)
					{
							ChampTextEmail.setText(mail);
							ChampTextAdresse.setText(adresseclient);
							ChampTextNumeroTelephone.setText(telclient);
							ChampTextPrenom.setText(prenomclient);
						
					}
					
				}
					rs.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}	
	 }

   	 public static int remplirHorsTaxe(){
  		 int PrixHorsTaxe = 0;
  		try {
 			Connection cn = DatabaseConnection.getCon();
 			PreparedStatement pst = cn.prepareStatement("SELECT prixtotal FROM lignecmd");
 			ResultSet rs =  pst.executeQuery();
 			while(rs.next()){	
 				PrixHorsTaxe = rs.getInt(1) + PrixHorsTaxe;
 				}
 			
 				rs.close();
 				
 		} catch (SQLException e) {
 		e.printStackTrace();
 		
 		
 		}
		return PrixHorsTaxe;	 
   	 }
	
	
	 
	public void initElementParticulier(){
		InformationClient.setText("Information du particulier");
		Prenom.setText("Prenom : ");
		ComboBoxClient.removeAllItems();
		remplirNomParticulier();
		
	}
	
	public void initElementEntreprise(){
	    InformationClient.setText("Information de l'entreprise");
		Prenom.setText("Responsable : ");;
		ComboBoxClient.removeAllItems();
		remplirNomEntreprise();
	}
	
	
	
	public void initElements()
	{
		
   		FenetrePrincipale.menuVenteEntreprise.addActionListener(new ActionListener()
   		{
   			public void actionPerformed(ActionEvent e)
   			{
				initElementEntreprise();
   				validate();
   				Etat = 2;
   			}
   		});
		
   		
   		FenetrePrincipale.menuVenteParticulier.addActionListener(new ActionListener()
   		{
   			public void actionPerformed(ActionEvent e)
   			{
   				initElementParticulier();
   				validate();
   				Etat = 1;
   			}
   		});	
		
		
		
   		tableLigne = new JTable(modelTableCommande);
   		tableLigne.setAutoCreateRowSorter(true); //permet de trier les colonnes
   		tableLigne.getRowSorter().toggleSortOrder(0);
   		tableLigne.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(tableLigne);
		tableLigne.setPreferredScrollableViewportSize(dimensionTable);
		
		

		JPanel panelTable = new JPanel(new GridLayout(4,1));
		JPanel panelHaut = new JPanel(new GridLayout(6,2));
		JPanel panelTableau = new JPanel(new GridLayout(1,1));
		JPanel panelMilieu = new JPanel(new GridLayout(3,2));
		JPanel panelBas = new JPanel(new GridLayout(2,1));
		
		JPanel panelBoutonCommande = new JPanel(new GridLayout(1,3));
		JPanel panelBoutonValiderAnnuler = new JPanel(new GridLayout(1,2));
		
		
		
		JPanel panInformationClient = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel panChampVide = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panComboNom = new JPanel(new  FlowLayout(FlowLayout.LEFT));
		JPanel panChampNom = new JPanel(new  FlowLayout(FlowLayout.RIGHT));
		JPanel panChampResponsable = new JPanel(new  FlowLayout(FlowLayout.LEFT));
		JPanel panResponsable = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel panChampAdresse = new JPanel(new  FlowLayout(FlowLayout.LEFT));
		JPanel panAdresse = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel panChampEmail = new JPanel(new  FlowLayout(FlowLayout.LEFT));
		JPanel panEmail = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel panChampTelephone = new JPanel(new  FlowLayout(FlowLayout.LEFT));
		JPanel panNumeroTelephone = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			
		
		JPanel panTableau = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JPanel panChampHT = new JPanel(new  FlowLayout(FlowLayout.LEFT));
		JPanel panHT = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel panChampTVA = new JPanel(new  FlowLayout(FlowLayout.LEFT));
		JPanel panTVA = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel panChampTTC = new JPanel(new  FlowLayout(FlowLayout.LEFT));
		JPanel panTTC = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JPanel panBoutonAjouter = new JPanel(new  FlowLayout(FlowLayout.RIGHT));
		JPanel panBoutonModifier = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panBoutonSupprimer = new JPanel(new  FlowLayout(FlowLayout.LEFT));
		JPanel panBoutonValider = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel panBoutonAnnuler = new JPanel(new  FlowLayout(FlowLayout.LEFT));
		
		
		
		
		add(panelTable);
		
		//Fenetre Principal
		panelTable.add(panelHaut);
		panelTable.add(panelTableau);
		panelTable.add(panelMilieu);
		panelTable.add(panelBas);
		
		//Partie Haute info client
		panelHaut.add(panInformationClient);
		panelHaut.add(panChampVide);
		panelHaut.add(panChampNom);
		panelHaut.add(panComboNom);
		panelHaut.add(panResponsable); 
		panelHaut.add(panChampResponsable); 
		panelHaut.add(panAdresse); 
		panelHaut.add(panChampAdresse); 
		panelHaut.add(panEmail);
		panelHaut.add(panChampEmail);
		panelHaut.add(panNumeroTelephone);
		panelHaut.add(panChampTelephone);

		//Tableau
		panelTableau.add(panTableau);

		
		//Partie milieu compta
		panelMilieu.add(panHT);
		panelMilieu.add(panChampHT);
		panelMilieu.add(panTVA);
		panelMilieu.add(panChampTVA);
		panelMilieu.add(panTTC);
		panelMilieu.add(panChampTTC);
		
		//separation en 2 parties pannel bouton 
		panelBas.add(panelBoutonCommande);
		panelBas.add(panelBoutonValiderAnnuler);
	
		//1er partie coommande
		panelBoutonCommande.add(panBoutonAjouter);
		panelBoutonCommande.add(panBoutonModifier);		
		panelBoutonCommande.add(panBoutonSupprimer);
	
		//2eme partie valider quitter
		panelBoutonValiderAnnuler.add(panBoutonValider);
		panelBoutonValiderAnnuler.add(panBoutonAnnuler);		
		
		// Insertion elements
		panInformationClient.add(InformationClient);
		panChampVide.add(ChampVide);
		panChampNom.add(ChampTextNom);
		panComboNom.add(ComboBoxClient);
		panChampResponsable.add(ChampTextPrenom);
		panResponsable.add(Prenom);
		panChampAdresse.add(ChampTextAdresse);
		panAdresse.add(Adresse);
		panChampEmail.add(ChampTextEmail);
		panEmail.add(Email);
		panChampTelephone.add(ChampTextNumeroTelephone);
		panNumeroTelephone.add(NumeroTelephone);
				
		//changement taille des textfield
		InformationClient.setPreferredSize(dimensionTextField);
		ChampTextPrenom.setPreferredSize(dimensionTextField);
		ChampTextAdresse.setPreferredSize(dimensionTextField);
		ChampTextEmail.setPreferredSize(dimensionTextField);
		ChampTextNumeroTelephone.setPreferredSize(dimensionTextField);

		
		panTableau.add(scrollPane);
		
		panChampHT.add(ChampTotalHT);
		panHT.add(TotalHT);
		panChampTVA.add(ChampTVA);
		panTVA.add(TVA);
		panChampTTC.add(ChampTotalTTC);
		panTTC.add(TotalTTC);
		
		ChampTotalHT.setPreferredSize(dimensionTextField);
		ChampTVA.setPreferredSize(dimensionTextField);
		ChampTotalTTC.setPreferredSize(dimensionTextField);
		
		panBoutonAjouter.add(AjouterProduit);
		panBoutonModifier.add(ModifierProduit);		
		panBoutonSupprimer.add(SupprimerProduit);
		panBoutonValider.add(Valider);
		panBoutonAnnuler.add(Annuler);
		
		AjouterProduit.setPreferredSize(dimensionBouton);
		ModifierProduit.setPreferredSize(dimensionBouton);
		SupprimerProduit.setPreferredSize(dimensionBouton);
		Valider.setPreferredSize(dimensionBouton);
		Annuler.setPreferredSize(dimensionBouton);
		
		listener();
		
		careupdatenew.equals(true);
		careupdatenew.equals(true);
		
		setVisible(true);
	}
	
	
	public void initProduit(){

		
	}

		
   	 public void remplirProduit(){

   	 }
   	 
		CaretListener careupdatenew = new CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent e) {
				
				/*JTextField text = (JTextField)e.getSource();
				System.out.println(text.getText());*/
				String aString = ChampTotalHT.getText();
				valeurHT = TotalHorsTaxe;

				valeurTVA = valeurHT * 0.196;
				ChampTVA.setText("" + valeurTVA);
				valeurTTC = valeurTVA+valeurHT;
				ChampTotalTTC.setText(""+ valeurTTC);

			}
		};

		
   	 public void listener()
   	 {
			
			Valider.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					
					 new Livraison(frame);

				}
			});
			


				ComboBoxClient.addActionListener(new ActionListener() {			
				public void actionPerformed(ActionEvent e) {
						if(Etat == 1)
							{
							
							remplirInfoParticulier();
							}
						if(Etat == 2)
						{
							remplirInfoEntreprise();
						}
					}
				});
				
				AjouterProduit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						 new AjouterProduitCommande(frame);
					}
				});
				
				
				ModifierProduit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						valeurHT =remplirHorsTaxe();
						
						ChampTotalHT.setText(""+valeurHT);
						valeurTVA = valeurHT * 0.196;
						ChampTVA.setText("" + valeurTVA);
						valeurTTC = valeurTVA+valeurHT;
						ChampTotalTTC.setText(""+ valeurTTC);


					}
				});
				
				SupprimerProduit.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub

					}
				});
				
				CaretListener careupdatenew = new CaretListener() {
					public void caretUpdate(javax.swing.event.CaretEvent e) {
						
						/*JTextField text = (JTextField)e.getSource();
						System.out.println(text.getText());*/
						String aString = ChampTotalHT.getText();
						valeurHT = Integer.parseInt(aString);

						valeurTVA = valeurHT * 0.196;
						ChampTVA.setText("" + valeurTVA);
						valeurTTC = valeurTVA+valeurHT;
						ChampTotalTTC.setText(""+ valeurTTC);

					}
				};
				

				ChampTotalHT.addCaretListener(careupdatenew);
				Annuler.addActionListener(frame -> System.exit(0)); // quand bouton annuler appuy� action
			    Valider.addActionListener(frame -> Envoyer()); // quand bouton envoy� action

   	 }
   	 
   	void Envoyer(){
   	}
   	

	 
}


