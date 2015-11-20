package jdbc;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class FenetreConnexion extends JDialog
{	
	private static FenetrePrincipale frame;
	private JPanel panelWrap = new JPanel();
	private JPanel panelGrid = new JPanel(new GridLayout(6,2,5,5));
	private JPanel panelError = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private ArrayList<JTextField> array = new ArrayList<JTextField>();
	
	/**
	 * Le constructeur par défaut fait appel à la fonction init
	 */
	public FenetreConnexion(FenetrePrincipale frame)
	{
		super(frame,"Connexion",true);
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
		setSize(400,250);	
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void initElements()
	{
		//Création des boutons
		JLabel labelIp = new JLabel("adresse ip ");
		JTextField textIp = new JTextField(10);
		
		JLabel labelPort = new JLabel("port ");
		JTextField textPort = new JTextField(10);

		JLabel labelSid = new JLabel("identifiant de sécurité (SID) ");
		JTextField textSid = new JTextField(10);
		
		JLabel labelUser = new JLabel("utilisateur ");
		JTextField textUser = new JTextField(10);

		JLabel labelPass = new JLabel("mot de passe ");
		JTextField textPass = new JTextField(10);

		JButton connexion = new JButton("Connexion");
		JButton retour = new JButton("Retour");
		connexion.setEnabled(false);
		
		JLabel error = new JLabel("");
		
		//Ajout dans l'arraylist des textfield
		array.add(textIp);
		array.add(textPort);
		array.add(textSid);
		array.add(textUser);
		array.add(textPass);
		
		//Ajouter les élements			
		panelGrid.add(labelIp);
		panelGrid.add(textIp);

		panelGrid.add(labelPort);
		panelGrid.add(textPort);

		panelGrid.add(labelSid);
		panelGrid.add(textSid);

		panelGrid.add(labelUser);
		panelGrid.add(textUser);
		
		panelGrid.add(labelPass);
		panelGrid.add(textPass);
		
		panelGrid.add(connexion);
		panelGrid.add(retour);

		panelError.add(error);		

		add(panelWrap);
		panelWrap.add(panelGrid);
		panelWrap.add(panelError);
		
		//Handler vérification champs
		KeyAdapter listener = new KeyAdapter() 
		{
	        public void keyReleased(KeyEvent e) 
	        {
	            super.keyReleased(e);
	            boolean condition = true;
	            
	            for (JTextField t : array) 
	            {
	            	if(t.getText().length() <= 0)
	            	condition = false;
				}
	            
	            connexion.setEnabled(condition);
	        }
	    };
	    
	    for (JTextField t : array) 
        {
        	t.addKeyListener(listener);
		}
	    
		//Action si click sur bouton
		connexion.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{			
				//Si la requete à réussie
				if(DatabaseConnection.connect(array.get(0).getText(),array.get(1).getText(),array.get(2).getText(),array.get(3).getText(),array.get(4).getText()) == true)
				dispose();
				else error.setText("<html><font color=red>Connexion à la base de données impossible !</html>");
			}
		});
		retour.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});

		//Afficher la fenêtre
		setVisible(true);
	}
}