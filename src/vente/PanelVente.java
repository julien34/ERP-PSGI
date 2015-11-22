package vente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelVente extends JPanel 
{
	private static FenetrePrincipale framePrincipale;
	
	private JButton fenetreVente = new JButton("Fenetre de vente");
	private JButton interfaceClient = new JButton("Interface de clients");
	private JButton interfaceDevis = new JButton("Interface de devis");	
	
	public PanelVente(FenetrePrincipale framePrincipale)
	{
		this.framePrincipale = framePrincipale;
		initElements();
		initHandlers();
	}

	private void initElements() 
	{
		add(fenetreVente);
		add(interfaceClient);
		add(interfaceDevis);
	}

	private void initHandlers() 
	{
		fenetreVente.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ new FenetreVente(framePrincipale); }
		});
		interfaceClient.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ new PanelClient(framePrincipale); }
		});
		interfaceDevis.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ new InterfaceDevis(framePrincipale); }
		});			
	}
}