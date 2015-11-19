package principal;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelPrincipal extends JPanel {

	//Création de l'image 
	ImageIcon logo = new ImageIcon("src/images/logo.png");
	Image image = logo.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
	
	public PanelPrincipal(FenetrePrincipale framePrincipale) {
		JLabel label = new JLabel();
		//label.setIcon(image);
		
		JLabel labelIntro = new JLabel("Bienvenue sur l'ERP PSGI");

		this.add(labelIntro);
		this.add(label);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image,0,0,null);
	}

}
