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

public class AjouterProduitCommande extends JDialog
{	
	private static FenetrePrincipale frame;

	JLabel Produit = new JLabel("Produit");
	JComboBox ComboBoxProduit = new JComboBox();

	JLabel LQuantite = new JLabel("Qte");	
	JSpinner SpinQuantite = new JSpinner();
	int ValeurSpin =0;
	
	
	JButton ButtonOk = new JButton("Valider");
	JButton ButtonAnnuler = new JButton("Annuler");
	private JPanel panelTable = new JPanel(new BorderLayout(2,1));
	private JPanel panelProduit = new JPanel();
	private JPanel panelButton = new JPanel();


	public AjouterProduitCommande(FenetrePrincipale frame)
	{
		super(frame,"Ajouter un produit",true);
		this.frame = frame;
		initFenetre();
		initElements();
	}	
	
	/**
	 * Initialise la fenetre associée
	 */
	
	
	public void initFenetre()
	{
		//Paramétrage de la fenêtre
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400,120);	
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void initElements()
	{
		add(panelTable);
		panelTable.add("North",panelProduit);
		panelProduit.add(Produit);
		panelProduit.add(ComboBoxProduit);
		panelProduit.add(LQuantite);
		panelProduit.add(SpinQuantite);
		SpinQuantite.setPreferredSize(new Dimension(40,30));
		
		panelTable.add("South",panelButton);
		panelButton.add(ButtonOk);
		ButtonOk.setPreferredSize(new Dimension (100,26));
		panelButton.add(ButtonAnnuler);
		
		ButtonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ValeurSpin = (Integer) SpinQuantite.getValue();
				String nomProduitCombo = ComboBoxProduit.getName();
				System.out.println(nomProduitCombo);
				//System.out.println("valeur du spin : " + ValeurSpin);	
			
				try {
					Connection cn = DatabaseConnection.getCon();
					PreparedStatement pst = cn.prepareStatement("SELECT description FROM PRODUITVENTE");
					ResultSet rs =  pst.executeQuery();
					while(rs.next()){	

						}
				//FenetreVente.remplirHorsTaxe();
						rs.close();
				} catch (SQLException f) {
				f.printStackTrace();
				}	
				
			}
		});
		
		ButtonAnnuler.setPreferredSize(new Dimension (100,26));		
		ButtonAnnuler.addActionListener(frame ->dispose()); // quand bouton annuler appuy� action
   
		
		initProduit();		
		setVisible(true);
	}
	
	
	public void initProduit(){

		remplirProduit();
	}

		
   	 public void remplirProduit(){
 		try {
 			Connection cn = DatabaseConnection.getCon();
 			PreparedStatement pst = cn.prepareStatement("SELECT description FROM PRODUITVENTE");//PRODUITS ou PRODUITVENTE ( )
 			ResultSet rs =  pst.executeQuery();
 			while(rs.next()){	
 				
 		     	ComboBoxProduit.addItem(rs.getString(1)) ;			

 				}
 		
 				rs.close();
 		} catch (SQLException e) {
 		e.printStackTrace();
 		}	
   	 }
   	 

 	 

   	 
}


