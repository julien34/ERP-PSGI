package vente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class Livraison extends JDialog
{	
	private static FenetrePrincipale frame;
	

	
	
	JButton ButtonOk = new JButton("Valider");
	JButton ButtonAnnuler = new JButton("Annuler");
	
	
	JLabel ModePayement = new JLabel("Mode de Payement");
	JRadioButton Carte = new JRadioButton("Carte");
	JRadioButton Cheque = new JRadioButton("Cheque");
	JRadioButton Espece = new JRadioButton("Espece");	
	
	
	JLabel AdresseLivraison = new JLabel("Adresse de livraison");

	
	JLabel ChampVide = new JLabel("");
	JLabel CodePostal = new JLabel("CodePostal : ");
	JTextField ChampTextCodePostal = new JTextField("");
	JLabel Ville = new JLabel("Ville :  ");
	JTextField ChampTextVille = new JTextField("");
	JLabel Adresse = new JLabel("Adresse : ");
	JTextField ChampTextAdresse = new JTextField("");
	JLabel NumeroTelephone = new JLabel("Numero telephone client: ");
	JTextField ChampTextNumeroTelephone = new JTextField("");  	  
	
	JRadioButtonMenuItem LivraisonMenu;
	JLabel MethodeDeLivraison = new JLabel("Moyen de livraison ");
	JRadioButton PointRelais = new JRadioButton("Point-Relais");
	JRadioButton Livraison = new JRadioButton("Livraison");
	
	
	JButton BouttonValider = new JButton("Valider");
	JButton BouttonAnnuler = new JButton("Annuler");
	
	
	private Dimension dimensionBouton = new Dimension(100, 30);	
	private Dimension dimensionTextField = new Dimension (180 , 26);
	
	
	int ChoixModePayement = 0;
	int ChoixMoyenLivraison = 0;
	int Etat = 0;
	

	public Livraison(FenetrePrincipale frame)
	{
		super(frame,"Methode de livraison",true);
		this.frame = frame;
		initFenetre();
		initElements();
	}	

	
	
	public void initFenetre()
	{
		//Paramétrage de la fenêtre
		setName("Methode de livraison");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800,450);	
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void initElements()
	{
		JPanel panelTable = new JPanel(new GridLayout(2,1));
		JPanel panelHaut = new JPanel(new GridLayout(1,2));
		JPanel adresseLivraison = new JPanel(new GridLayout(5,2));
		JPanel ModeDePayement = new JPanel(new GridLayout(4,2));
		JPanel PanelMethodeLivraison = new JPanel(new GridLayout(6,1));
		JPanel PanelBoutton = new JPanel(new GridLayout(1, 2));
		
		JPanel panChampVide = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panAdresseLivraison = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panCodePostal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel panVille = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel panAdresse = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel panNumeroTelephone = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JPanel panModePayement = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panCarte = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panCheque = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panEspece = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JPanel panModeLivraison = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panPointRelais = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panLivraison = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JPanel panButtonValider = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panButtonAnnuler = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
				
				
		
		getContentPane().add(panelTable);
		
		panelTable.add(panelHaut);
		panelTable.add(PanelMethodeLivraison);
		panelHaut.add(adresseLivraison);
		panelHaut.add(ModeDePayement);
		
		adresseLivraison.add(panAdresseLivraison);
		adresseLivraison.add(panCodePostal);
		adresseLivraison.add(panVille); 
		adresseLivraison.add(panAdresse); 
		adresseLivraison.add(panNumeroTelephone);
	
		
		
		panAdresseLivraison.add(ChampVide);
		panAdresseLivraison.add(AdresseLivraison);
		panCodePostal.add(CodePostal);
		panCodePostal.add(ChampTextCodePostal);
		panVille.add(Ville); 
		panVille.add(ChampTextVille); 
		panAdresse.add(Adresse);
		panAdresse.add(ChampTextAdresse);
		panNumeroTelephone.add(NumeroTelephone);
		panNumeroTelephone.add(ChampTextNumeroTelephone);
		
		ChampTextVille.setPreferredSize(dimensionTextField);
		ChampTextCodePostal.setPreferredSize(dimensionTextField);
		ChampTextAdresse.setPreferredSize(dimensionTextField);
		ChampTextNumeroTelephone.setPreferredSize(dimensionTextField);
		
				
		ModeDePayement.add(panModePayement);
		ModeDePayement.add(panCarte);
		ModeDePayement.add(panCheque);
		ModeDePayement.add(panEspece);
		
		panModePayement.add(ModePayement);
		panCarte.add(Carte);
		panCheque.add(Cheque);
		panEspece.add(Espece);
		
		Carte.setSelected(true);
		Livraison.setSelected(true);
		
		PanelMethodeLivraison.add(new JSeparator(SwingConstants.HORIZONTAL));
		PanelMethodeLivraison.add(panModeLivraison);
		PanelMethodeLivraison.add(panPointRelais);
		PanelMethodeLivraison.add(panLivraison);
		PanelMethodeLivraison.add(PanelBoutton);

		PanelBoutton.add(panButtonValider);
		PanelBoutton.add(panButtonAnnuler);
		
		panModeLivraison.add(MethodeDeLivraison);
		panPointRelais.add(PointRelais);
		panLivraison.add(Livraison);
		panButtonValider.add(BouttonValider);
		panButtonAnnuler.add(BouttonAnnuler);
		
		BouttonValider.setPreferredSize(dimensionBouton);
		BouttonAnnuler.setPreferredSize(dimensionBouton);
		
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

		
			PointRelais.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	Livraison.setSelected(false);
			    }
			  });

			Livraison.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	PointRelais.setSelected(false);	
			    }
			  });
			
			
		
		ButtonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			CreerLivraison();
				
			}

		});
		BouttonAnnuler.addActionListener(frame ->dispose()); // quand bouton annuler appuy� action
   
		setVisible(true);
		
	}
	

	
	public void CreerLivraison(){
		//creer table avec -num_commande -num_client -adresse_livraison -moyen_livraison -methode_payement
	}
	
	public void initProduit(){
		
		
	}

	
   	 public void remplirProduit(){

   	 }


		

   	 
}


