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
	 * Le constructeur par d�faut fait appel � la fonction init
	 */
	public FenetreConnexion(FenetrePrincipale frame)
	{
		super(frame,"Connexion",true);
		this.frame = frame;
		initFenetre();
		initElements();
	}	
	
	/**
	 * Initialise la fenetre associ�e
	 */
	public void initFenetre()
	{
		//Param�trage de la fen�tre
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400,250);	
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void initElements()
	{
		//Cr�ation des boutons
		JLabel labelIp = new JLabel("adresse ip ");
		JTextField textIp = new JTextField(10);
		
		JLabel labelPort = new JLabel("port ");
		JTextField textPort = new JTextField(10);

		JLabel labelSid = new JLabel("identifiant de s�curit� (SID) ");
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
		
		//Ajouter les �lements			
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
		
		//Handler v�rification champs
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
				//Si la requete � r�ussie
				if(DatabaseConnection.connect(array.get(0).getText(),array.get(1).getText(),array.get(2).getText(),array.get(3).getText(),array.get(4).getText()) == true)
				dispose();
				else error.setText("<html><font color=red>Connexion � la base de donn�es impossible !</html>");
			}
		});
		retour.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});

		//Afficher la fen�tre
		setVisible(true);
	}
}