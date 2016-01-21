package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class DatabaseConnection 
{	
	static private Connection con;
	static private Statement stat;
	
	public DatabaseConnection() 
	{
		
	}
	
	/**
	 * Ouvre la connexion avec le serveur SQL spécifie par les 5 arguments suivants.
	 * @param ip adresse IP du serveur SQL
	 * @param port numero de port du serveur SQL
	 * @param sid identificateur de sécurité
	 * @param user utilisateur
	 * @param password mot de passe
	 */
	static public boolean connect(String ip, String port, String sid, String user, String password)
	{
		try
		{
			con = DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":"+port+":"+sid,user,password);
			return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Exécute la requête SQL passée en paramètre.
	 * @param sql La requête SQL à exécuter
	 */
	static public boolean requete(String sql)
	{
		try
		{
			stat = con.createStatement();
			stat.executeUpdate(sql);
			stat.close();
			return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	
	  public static Object[][] remplirListeCategorie() {
	        int longueurTableau = 0;
	        int indexActuel = 0;
	        try {
	            stat = con.createStatement();
	            ResultSet resultat = stat.executeQuery("SELECT COUNT(*) FROM CATEGORIE");
	            while (resultat.next()) {
	                longueurTableau = resultat.getInt("COUNT(*)");
	            }
	            resultat.close();
	            Object[][] databaseData = new Object[longueurTableau][2];
	            resultat = stat.executeQuery("SELECT * FROM CATEGORIE");
	            while (resultat.next()) {
	                databaseData[indexActuel][0] = resultat.getString(2);
	                databaseData[indexActuel][1] = resultat.getString(3);
	                ++indexActuel;
	            }
	            resultat.close();
	            stat.close();
	            return databaseData;
	        }
	        catch (SQLException e) {
	            System.out.println(e.getMessage());
	            return null;
	        }
	    }

	    public static Object[][] remplirListeOperation() {
	        int longueurTableau = 0;
	        int indexActuel = 0;
	        try {
	            stat = con.createStatement();
	            ResultSet resultat = stat.executeQuery("SELECT COUNT(*) FROM operations");
	            while (resultat.next()) {
	                longueurTableau = resultat.getInt("COUNT(*)");
	            }
	            resultat.close();
	            Object[][] databaseData = new Object[longueurTableau][6];
	            resultat = stat.executeQuery("SELECT * FROM operations");
	            while (resultat.next()) {
	                databaseData[indexActuel][0] = resultat.getString(1);
	                databaseData[indexActuel][1] = resultat.getString(2);
	                databaseData[indexActuel][2] = resultat.getString(3);
	                databaseData[indexActuel][3] = resultat.getString(4);
	                databaseData[indexActuel][4] = resultat.getString(5);
	                databaseData[indexActuel][5] = resultat.getString(6);
	                ++indexActuel;
	            }
	            resultat.close();
	            stat.close();
	            return databaseData;
	        }
	        catch (SQLException e) {
	            System.out.println(e.getMessage());
	            return null;
	        }
	    }

	    public static Object[][] remplirListeGamme() {
	        int longueurTableau = 0;
	        int indexActuel = 0;
	        try {
	            stat = con.createStatement();
	            ResultSet resultat = stat.executeQuery("SELECT COUNT(*) FROM GAMME");
	            while (resultat.next()) {
	                longueurTableau = resultat.getInt("COUNT(*)");
	            }
	            resultat.close();
	            Object[][] databaseData = new Object[longueurTableau][3];
	            resultat = stat.executeQuery("SELECT * FROM GAMME");
	            while (resultat.next()) {
	                databaseData[indexActuel][0] = resultat.getString(1);
	                databaseData[indexActuel][1] = resultat.getString(2);
	                databaseData[indexActuel][2] = resultat.getString(3);
	                ++indexActuel;
	            }
	            resultat.close();
	            stat.close();
	            return databaseData;
	        }
	        catch (SQLException e) {
	            System.out.println(e.getMessage());
	            return null;
	        }
	    }
	
	/**
	 * 
	 * @return Object [][]
	 */
	static public Object[][] remplirListeProduits()
	{
		int longueurTableau = 0;
		int indexActuel = 0;
			
		try
		{
			stat = con.createStatement();
			ResultSet resultat = stat.executeQuery("SELECT COUNT(*) FROM PRODUITS");
			while(resultat.next())
			{
				longueurTableau = resultat.getInt("COUNT(*)");
			}
			resultat.close();
			Object[][] databaseData = new Object[longueurTableau][5];
			
			resultat = stat.executeQuery("SELECT * FROM PRODUITS");
			while(resultat.next())
			{
				databaseData[indexActuel][0] = resultat.getString(2);
				databaseData[indexActuel][1] = resultat.getString(3);
				databaseData[indexActuel][2] = resultat.getFloat(4);
				databaseData[indexActuel][3] = resultat.getFloat(5);
				databaseData[indexActuel][4] = resultat.getFloat(6);
				indexActuel++;
			}
			resultat.close();		
			stat.close();
			return databaseData;
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	static public Object[][] remplirListeClient()
	{
		int longueurTableau = 0;
		int indexActuel = 0;
			
		try
		{
			stat = con.createStatement();
			ResultSet resultat = stat.executeQuery("SELECT COUNT(*) FROM VENTE_CLIENTS");
			while(resultat.next())
			{
				longueurTableau = resultat.getInt("COUNT(*)");
			}
			resultat.close();
			Object[][] databaseData = new Object[longueurTableau][7];
			
			resultat = stat.executeQuery("SELECT * FROM VENTE_CLIENTS");
			while(resultat.next())
			{
				databaseData[indexActuel][0] = resultat.getString("idclient");
				databaseData[indexActuel][1] = resultat.getString("nomclient");
				databaseData[indexActuel][2] = resultat.getString("prenomclient");
				databaseData[indexActuel][3] = resultat.getString("adresseclient");
				databaseData[indexActuel][4] = resultat.getString("emailclient");
				databaseData[indexActuel][5] = resultat.getString("telclient");
				databaseData[indexActuel][6] = resultat.getString("codecategorieclient");
				indexActuel++;
			}
			resultat.close();		
			stat.close();
			return databaseData;
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	static public Object[][] rechercherListeClient(String Recherche)
	{
		int longueurTableau = 0;
		int indexActuel = 0;
			
		try
		{
			PreparedStatement pstat;
			pstat = con.prepareStatement("SELECT COUNT(*) FROM VENTE_CLIENTS WHERE UPPER(nomclient) LIKE  UPPER(?) OR idclient LIKE  ? ");
			pstat.setString(1,Recherche + "%");
			pstat.setString(2,Recherche + "%");
			ResultSet resultat = pstat.executeQuery();
			while(resultat.next())
			{
				longueurTableau = resultat.getInt("COUNT(*)");
			}
			
			Object[][] databaseData = new Object[longueurTableau][7];
			PreparedStatement pst;
			
			byte[] bytes = Recherche.getBytes();
		    for (int i = 0; i < bytes.length; i++) {
		        if (!Character.isDigit((char) bytes[i])) {
		            
		    
			pst = con.prepareStatement("SELECT * FROM vente_clients WHERE UPPER(nomclient) LIKE  UPPER(?) ");
			pst.setString(1,Recherche + "%");
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				databaseData[indexActuel][0] = rs.getString("idclient");
				databaseData[indexActuel][1] = rs.getString("nomclient");
				databaseData[indexActuel][2] = rs.getString("prenomclient");
				databaseData[indexActuel][3] = rs.getString("adresseclient");
				databaseData[indexActuel][4] = rs.getString("emailclient");
				databaseData[indexActuel][5] = rs.getString("telclient");
				databaseData[indexActuel][6] = rs.getString("codecategorieclient");
				indexActuel++;
			}
			longueurTableau = 2;
			rs.close();	
			resultat.close();
			stat.close();
			 return databaseData;
			
		        }
		        else{
		        	pst = con.prepareStatement("SELECT * FROM vente_clients WHERE idclient LIKE  ? ");
					pst.setString(1,Recherche + "%");
					ResultSet rs = pst.executeQuery();
					while(rs.next())
					{
						databaseData[indexActuel][0] = rs.getString("idclient");
						databaseData[indexActuel][1] = rs.getString("nomclient");
						databaseData[indexActuel][2] = rs.getString("prenomclient");
						databaseData[indexActuel][3] = rs.getString("adresseclient");
						databaseData[indexActuel][4] = rs.getString("emailclient");
						databaseData[indexActuel][5] = rs.getString("telclient");
						databaseData[indexActuel][6] = rs.getString("codecategorieclient");
						indexActuel++;
					}
					rs.close();		
					stat.close();
					 return databaseData;
		        }
		    }
		    return databaseData;
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	static public Object[][] remplirLigneCmd()
	{
		int longueurTableau = 0;
		int indexActuel = 0;
			
		try
		{
			stat = con.createStatement();
			ResultSet resultat = stat.executeQuery("SELECT COUNT(*) FROM lignecmd");
			while(resultat.next())
			{
				longueurTableau = resultat.getInt("COUNT(*)");
			}
			resultat.close();
			Object[][] databaseData = new Object[longueurTableau][5];
			
			resultat = stat.executeQuery("SELECT * FROM lignecmd");
			while(resultat.next())
			{
				databaseData[indexActuel][0] = resultat.getString("numLigne");
				databaseData[indexActuel][1] = resultat.getString("nomProduit");
				databaseData[indexActuel][2] = resultat.getString("quantite");
				databaseData[indexActuel][3] = resultat.getString("prixUni");
				databaseData[indexActuel][4] = resultat.getString("prixTotal");
				indexActuel++;
			}
			resultat.close();		
			stat.close();
			return databaseData;
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
    public static void getGamme(DefaultComboBoxModel<String> gamme){		
		try {
            stat = con.createStatement();
            ResultSet resultat = stat.executeQuery("SELECT nomgamme FROM GAMME");
            while (resultat.next()) {
            	gamme.addElement(resultat.getString(1));
                System.out.println(resultat.getString(1));
            }
            stat.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static DefaultComboBoxModel<String> getCentre(){
    	DefaultComboBoxModel<String> modelCentre = new DefaultComboBoxModel<String>();
    	//JComboBox<String> centreTravail = new JComboBox<String>();
		
		try {
            stat = con.createStatement();
            ResultSet resultat = stat.executeQuery("SELECT nomcentre FROM centretravail");
            while (resultat.next()) {
            	modelCentre.addElement(resultat.getString(1));
                //centreTravail.addItem(resultat.getString(1));
                //System.out.println(resultat.getString(1));
            }
            stat.close();
            return modelCentre;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return modelCentre;
        }
    }
	
    public static String getNomGamme(String value1, String value2) {
        try {
            stat = con.createStatement();
            ResultSet resultat = stat.executeQuery("SELECT nomGamme FROM gamme WHERE codegamme = '" + value1 + "' AND emplacementgamme = '" + value2 + "'");
            String nomGamme = "";
            if (resultat.next()) {
                nomGamme = resultat.getString(1);
            }
            stat.close();
            return nomGamme;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    
	
	/**
	 * Methode pour obtenir la cl� primaire de la table produits
	 */
	static public int getCodeProduit(String value1,String value2,String value3,String value4,String value5)
	{
		try
		{
			stat = con.createStatement();
			ResultSet resultat = stat.executeQuery("SELECT codeProduit FROM produits WHERE description = '"+value1+"' AND categorie = '"+value2+"' AND prixVente = "+Float.parseFloat(value3)+" AND prixAchat = "+Float.parseFloat(value4)+" AND udm = "+Float.parseFloat(value5));
			
			int codeProduit = -1;
			
			if(resultat.next())
			codeProduit = resultat.getInt(1);
			
			stat.close();
			
			return codeProduit;			
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			return -1;
		}
	}
	
	static public String getCodeClient(String value1,String value2,String value3,String value4,String value5, String value6)
	{
		try
		{
			stat = con.createStatement();
			ResultSet resultat = stat.executeQuery("SELECT IDCLIENT FROM vente_clients WHERE NOMCLIENT = '"+value1+"' AND PRENOMCLIENT = '"+value2+"' AND ADRESSECLIENT = "+(value3)+" AND EMAILCLIENT = "+(value4)+" AND TELCLIENT = "+(value5)+"AND CODECATEG = " +(value6));
			
			String codeClient = null;
			
			if(resultat.next())
				codeClient = resultat.getString(1);
			
			stat.close();
			
			return codeClient;			
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * recupere une arraylist pour la comboBox
	 * @return ArrayList
	 */
	public static  ArrayList Combo() 
    { 
        ArrayList Array = new ArrayList(); 
        try 
        { 
			stat = con.createStatement();
        	ResultSet resultat = stat.executeQuery("SELECT nomClient FROM CLIENTS"); 
            
        	resultat.beforeFirst(); 
            while (resultat.next()) 
            { 
                resultat.previous();
                Array.add(resultat.next()); 
             
            } 
            stat.close();
        } 
        catch (SQLException queryE) 
        { 
            System.out.println("Erreur de requ�te : " + queryE);
        } 
        return Array; 
    }
	
	
	/**
	 * Méthode qui retourne la connection à la base de donnée.
	 * @return la connection de la base de donnée
	 */
	static public Connection getCon(){
		return con;
	}
	
	
	/**
	 * Ferme la connexion au serveur SQL.
	 */
	static public void disconnect()
	{
		try
		{
			con.close();
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
