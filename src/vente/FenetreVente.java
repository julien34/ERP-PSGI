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
	
	private static ArrayList<Client> liste = new ArrayList<Client>();
	private static String[] titres = {"ID Client","NomClient","PrenomClient","AdresseClient","MailClient","N° Tél", "Catégorie"};
	private static Object[][] tabFn;

	
	JComboBox ComboBoxClient = new JComboBox();
	
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
	
	
	int Etat = 0; //1 pour entreprise   2 pour particulier


	
		
	
	
	
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
	
	/*  Object[][] data ={
			  {"Cho7","Chausette","4","20","80"},
			  {"Dtf6","Dentifrice","1","6","6"},
			  {"Sho6r","Chaussure","1","50","50"},
			  {"VV2","VoitureV2","2","140000","280000"}
	  };
	  String title[] = { "Ref","Nom","Quantite","PrixUnitaire","PrixHorsTaxe"};
	  
	  JTable tableau = new JTable(data,title);
	*/
	
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
	
	
	
	 public void remplirInfosClient(){
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT nomclient FROM VENTE_CLIENTS WHERE codecategorieclient = '1'");
			ResultSet rs =  pst.executeQuery();
			while(rs.next()){	
				
				String idclient = rs.getString("idclient");
				String nomclient = rs.getString("nomclient");
				String prenomclient = rs.getString("prenomclient");
				String telclient = rs.getString("telclient");
				String adresseclient = rs.getString("adresseclient");
				String categorie = rs.getString("codecateg");
				String mail = rs.getString("emailclient");
				
				liste.add(new Client(idclient, nomclient, prenomclient, adresseclient ,mail, telclient, categorie));

				
		     	ComboBoxClient.addItem(rs.getString(nomclient));
			

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
	
		
		InformationClient.setText("Information du particulier");
		Prenom.setText("Prenom : ");
	}
	
	public void initElementEntreprise(){
	    InformationClient.setText("Information de l'entreprise");
		Prenom.setText("Responsable : ");;
	}
	
	public void initElements(){
		
   		FenetrePrincipale.menuVenteEntreprise.addActionListener(new ActionListener()
   		{
   			public void actionPerformed(ActionEvent e)
   			{
   				initElementEntreprise();
   				
   				Etat = 1;

   			}
   		});
   		


   			
   			
   		
   		FenetrePrincipale.menuVenteParticulier.addActionListener(new ActionListener()
   		{
   			public void actionPerformed(ActionEvent e)
   			{
   				initElementParticulier();
   				Etat = 2;
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
			
			this.remplirInfosClient();
			Formulaire.add(ComboBoxClient);
			//ChampTextNom.setPreferredSize(dimensionTextField);
			Formulaire.add(Prenom); // 3/1
			Formulaire.add(ChampTextPrenom); // 3/1
			Formulaire.add(Adresse); // 4/1
			Formulaire.add(ChampTextAdresse); // 4/1
			Formulaire.add(Email);// 5/1
			Formulaire.add(ChampTextEmail);// 5/1
			Formulaire.add(NumeroTelephone);// 6/1
			Formulaire.add(ChampTextNumeroTelephone);// 6/1
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
			
			

			
			
			ComboBoxClient.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					
								
						ChampTextPrenom.setText("Juda");
						ChampTextAdresse.setText("5rueDuFrigo");
						ChampTextEmail.setText("juda.bricot@mail.osef");
						ChampTextNumeroTelephone.setText("0606060606");  	 
						

/*
		   			try {
		   				Connection cn = DatabaseConnection.getCon();
		   				PreparedStatement pst = cn.prepareStatement("SELECT c.refCommande, c.dateCommande, f.refFournisseur, f.nomFournisseur, SUM(p.prixProduit*lc.quantite) AS montantTotal FROM CommandesFournisseur c JOIN Fournisseurs f ON f.refFournisseur = c.refFournisseur JOIN LignesCommandeFournisseur lc ON lc.refCommande = c.refCommande JOIN ProduitsFournisseur p ON p.refProduit = lc.refProduit GROUP BY c.refCommande, c.dateCommande, f.refFournisseur, f.nomFournisseur ORDER BY c.dateCommande DESC");
		   				ResultSet rs = pst.executeQuery();
		   				
		   				
		   				while(rs.next()){

		   					String refCommande = rs.getString("refCommande");
		   					Date date = rs.getDate("dateCommande");
		   					String refFournisseur = rs.getString("refFournisseur");
		   					String nomFournisseur = rs.getString("nomFournisseur");
		   					String montantTotal = rs.getString("montantTotal")+"â‚¬";

		   					this.listeCommandes.add(new CommandesFournisseur(refCommande, date, refFournisseur, nomFournisseur, montantTotal));
		   				}
		   			} catch (SQLException e) {
		   				e.printStackTrace();
		   			}*/
				


								//	frame.getPanelClient().refreshListeTableClient(id, nom, prenom, adresse, email, tel, categorie);
						
						
						
									
		
					
				}
			});
			

			
			
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

				AjouterProduit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						 new AjouterProduitCommande(frame);
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
			  /*
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
*/
			    }

	

void Envoyer(){

}

}