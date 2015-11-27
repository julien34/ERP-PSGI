package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseConnection 
{	
	static private Connection con;
	static private Statement stat;
	
	public DatabaseConnection() 
	{
		
	}
	
	/**
	 * Ouvre la connexion avec le serveur SQL sp�cifi� par les 5 arguments suivants.
	 * @param ip adresse IP du serveur SQL
	 * @param port numero de port du serveur SQL
	 * @param sid identificateur de s�curit�
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
	 * Ex�cute la requ�te SQL pass�e en param�tre.
	 * @param sql La requ�te SQL � ex�cuter
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
	/**
	 * recupere une arraylist pour la comboBox
	 * @return ArrayList
	 */
	public static  ArrayList Combo() 
    { 
        ArrayList Array = new ArrayList(); 
        try 
        { 
        	ResultSet resultat = stat.executeQuery("SELECT * FROM CLIENTS"); 
            resultat.beforeFirst(); 
            int i = 0; 
            while (resultat.next()) 
            { 
                i++; 
                Array.add(resultat.getString(i)); 
             
            } 
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
