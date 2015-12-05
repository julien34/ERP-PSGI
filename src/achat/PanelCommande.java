package achat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPanel;

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelCommande extends JPanel{

	public PanelCommande(FenetrePrincipale f){
		
		//On récupère l'ensemble des founisseurs présents dans la BDD
		this.getCommande();
				
		//On remplit la JTable
		//this.remplirTableau();
				
		//On initialise l'ensemble des composants sur le JPanel
		//this.initElements();
				
		//On initialise l'ensemble des écouteurs
		//this.initEcouteurs();
	}
	
	
	private void getCommande(){
		
		try {
			Connection cn = DatabaseConnection.getCon();
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("");
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
