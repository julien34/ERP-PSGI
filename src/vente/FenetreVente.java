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
	JPanel FenetrePrincipal = new JPanel(new GridLayout(3,1)); // 3 lignes 1 colones 
	JPanel FenetreHaut = new JPanel(new GridLayout(1,2,50,50)); // 1 ligne 2 colones
	JPanel Formulaire = new JPanel(new GridLayout(6, 1));// 6 lignes 1 colone
	JPanel ModeDePayement = new JPanel(new GridLayout(4, 2));//
	JPanel Commande = new JPanel(new GridLayout(3,1));
	JPanel ModifierTableau = new JPanel(new FlowLayout(FlowLayout.CENTER,40,0));
	JPanel Buttons = new JPanel(new GridLayout());	//ajout par la droite
	JPanel LesTotaux = new JPanel(new GridLayout(5,1));	
	JLabel InformationClient = new JLabel("");
	JLabel ModePayement = new JLabel("Mode de Payement");
	
	JLabel Rien = new JLabel("");
	JLabel RienButtons1 = new JLabel("");
	JLabel RienButtons2 = new JLabel("");
	JLabel RienButtons3 = new JLabel("");
	
	JLabel Nom = new JLabel("Nom : ");
	//JTextField ChampTextNom = new JTextField("");
	//String[] NomClient = {"Zetofrais","Bricot","Vegas","Fonfec","Formigli","Rembert","Ruiz","Manneli"};	
	
	private static ArrayList<Commande> liste = new ArrayList<Commande>();
	private static String[] titres = {"Nom","PrixUnitaire","Quantité","PrixTotal"};
	private static Object[][] tabFn;

	
	static JComboBox ComboBoxClient = new JComboBox();
	
	JLabel Prenom = new JLabel("");
	JTextField ChampTextPrenom = new JTextField("");
	
	JLabel Adresse = new JLabel("Adresse : ");
	JTextField ChampTextAdresse = new JTextField("");
	
	JLabel Email = new JLabel("Email : ");
	JTextField ChampTextEmail = new JTextField("");
	
	JLabel NumeroTelephone = new JLabel("NumeroTelephone : ");
	JTextField ChampTextNumeroTelephone = new JTextField("");  	  
	
	//ButtonGroup bg = new ButtonGroup();
	JRadioButton Carte = new JRadioButton("Carte");
	JRadioButton Cheque = new JRadioButton("Cheque");
	JRadioButton Espece = new JRadioButton("Espece");	
	
	
	JButton Valider = new JButton("Valider");	  
	JButton Annuler = new JButton("Annuler");
	
	
		
	JLabel TotalHT = new JLabel("Total HT : ");
	int valeurHT = 0 ;
	JTextField ChampTotalHT = new JTextField(""+valeurHT);	  
	JLabel TVA = new JLabel("TVA : ");
	double valeurTVA;// = 0.196*valeurHT;
	JTextField ChampTVA = new JTextField(""+valeurTVA);	  
	JLabel TotalTTC = new JLabel("Total TCC : ");
	double valeurTTC; //= valeurHT+valeurTVA;
	JTextField ChampTotalTTC = new JTextField(""+valeurTTC);	  
	JLabel RienTotaux1 = new JLabel("");
	JLabel RienTotaux2 = new JLabel("");
	JButton AjouterProduit = new JButton("Ajouter");
	JButton ModifierProduit = new JButton("Modifier");
	JButton SupprimerProduit = new JButton("Supprimer");
	
	JScrollPane scrollPane =  new JScrollPane();
	JTable tableCommande;
	

	
	
	int Etat = 0; //1 pour Particulier   2 pour Entreprise


	
		
	
	
	
	private final String[] nomColonnes = { "Ref","Nom","Quantite","PrixUnitaire","PrixHorsTaxe"};

	private static DefaultTableModel modelTableCommande = new DefaultTableModel(0,5){
		Class[] types = {Integer.class, String.class, String.class,Float.class,Float.class};
		
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
	

	
		private Dimension dimensionBouttons = new Dimension(140,26);
		private Dimension dimensionTextField = new Dimension (180 , 26);
		private Dimension dimensionTable = new Dimension(600, 150);

	   
	
	
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
		 catch(NullPointerException b){
			 b.printStackTrace();
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
		 catch(NullPointerException b){
			 b.printStackTrace();
		 }
		}
	 
	 
	 public void remplirInfoParticulier(){
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
			 catch(NullPointerException b){
				 b.printStackTrace();
			 }
	 }
	 
	 
	 public void remplirInfoEntreprise(){
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
			 catch(NullPointerException b){
				 b.printStackTrace();
			 }
	 }
 
	public void initElementParticulier(){
	/*
		JTextField ChampTextPrenom =  JTextField("");
		
		JTextField ChampTextAdresse =  JTextField("");
		
		JTextField ChampTextEmail =  JTextField("");
		
		JTextField ChampTextNumeroTelephone =  JTextField("");  	
		*/
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
	
	public void initElements(){
		
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
			
		tableCommande = new JTable(modelTableCommande);
		tableCommande.setAutoCreateRowSorter(true); //permet de trier les colonnes
		tableCommande.getRowSorter().toggleSortOrder(0);
		tableCommande.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(tableCommande);
		tableCommande.setPreferredScrollableViewportSize(dimensionTable);
		
		
		//add(Buttons,BorderLayout.SOUTH);
		add("NORTH",FenetreHaut);
		//	FenetrePrincipal.add(FenetreHaut); // FenetrePrincipal 1/1 prend fenetre haut
			FenetreHaut.add(Formulaire); //FenetreHaut prend formulaire  1/1
		
			//    */* position dans le tableau
			Formulaire.add(InformationClient); //  1/1
			Formulaire.add(Rien);// 1/1
			Formulaire.add(Nom);// 2/1
			//Formulaire.add(ChampTextNom);// 2/1
			
			Formulaire.add(ComboBoxClient);
			//remplirInfosClient();
			//ChampTextNom.setPreferredSize(dimensionTextField);
			Formulaire.add(Prenom); // 3/1
			Formulaire.add(ChampTextPrenom); // 3/1
			ChampTextPrenom.setEditable(false);
			Formulaire.add(Adresse); // 4/1
			Formulaire.add(ChampTextAdresse); // 4/1
			ChampTextAdresse.setEditable(false);
			Formulaire.add(Email);// 5/1
			Formulaire.add(ChampTextEmail);// 5/1
			ChampTextEmail.setEditable(false);
			Formulaire.add(NumeroTelephone);// 6/1
			Formulaire.add(ChampTextNumeroTelephone);// 6/1
			ChampTextNumeroTelephone.setEditable(false);
			FenetreHaut.add(ModeDePayement); // fenetre haut prend modedepayement 1/2
			ModeDePayement.add(ModePayement);// 1/1
			ModeDePayement.add(Carte);
			ModeDePayement.add(Cheque);
			ModeDePayement.add(Espece);
			Carte.setSelected(true);
			
			add("CENTER",Commande);
			
			Commande.add(scrollPane);
			
			
			
			Commande.add(LesTotaux);
			LesTotaux.add(TotalHT);
			LesTotaux.add(ChampTotalHT);
			ChampTotalHT.setPreferredSize(dimensionTextField);
			LesTotaux.add(TVA);
			LesTotaux.add(ChampTVA);
			LesTotaux.add(TotalTTC);
			LesTotaux.add(ChampTotalTTC);
			LesTotaux.add(AjouterProduit);
			LesTotaux.add(ModifierProduit);			
			LesTotaux.add(SupprimerProduit);
			Commande.add(ModifierTableau);
			ModifierTableau.add("CENTER",AjouterProduit);
			AjouterProduit.setPreferredSize(dimensionBouttons);
			ModifierTableau.add("CENTER",ModifierProduit);		
			ModifierProduit.setPreferredSize(dimensionBouttons);
			ModifierTableau.add("CENTER",SupprimerProduit);
			SupprimerProduit.setPreferredSize(dimensionBouttons);
			
			add("SOUTH",Buttons);

			Buttons.add(RienTotaux1);
			Buttons.add(RienTotaux2);
			Buttons.add(RienButtons1);
			Buttons.add(Valider);
			Buttons.add(Annuler);
			
			
			
			
			
			
			Carte.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	Cheque.setSelected(false);
			    	Espece.setSelected(false);
			    }
			});

				Cheque.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
			    	Carte.setSelected(false);
			    	Espece.setSelected(false);
					}
			    });

				Espece.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	Cheque.setSelected(false);
			    	Carte.setSelected(false);		
			    						
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
/*
	private ArrayList<Commande> getCommande() {
		try{
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT nom , prixVente FROM PRODUIT WHERE codecategorieclient = '2'");
			ResultSet rs =  pst.executeQuery();
			while(rs.next()){
				String ref = rs.getString("refFournisseur");
				String nomFn = rs.getString("nomFournisseur");
				String siret = rs.getString("siret");
				String tel = rs.getString("telFournisseur");
				String adresse = rs.getString("adresseFournisseur");
				String categorie = rs.getString("nomCategorie");

				liste.add(new Commande(ref, nomFn, siret, tel, adresse, categorie));
			}} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}
    
    
	public void setTableau(Commande f, int indice){
		liste.remove(indice);
		liste.add(indice,f);
		
		tabFn[indice][0] = f.nom;
		tabFn[indice][1] = f.siret;
		tabFn[indice][2] = f.tel;
		tabFn[indice][3] = f.adresse;
		tabFn[indice][4] = f.categorie;
		
		modele.setDataVector(tabFn,titres);
	}

	*/
void Envoyer(){

}

}