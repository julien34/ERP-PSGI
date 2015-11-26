package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			Object[][] databaseData = new Object[longueurTableau][6];
			
			resultat = stat.executeQuery("SELECT * FROM PRODUITS");
			while(resultat.next())
			{
				databaseData[indexActuel][0] = resultat.getInt("codeProduit");
				databaseData[indexActuel][1] = resultat.getString("description");
				databaseData[indexActuel][2] = resultat.getString("categorie");
				databaseData[indexActuel][3] = resultat.getFloat("prixVente");
				databaseData[indexActuel][4] = resultat.getFloat("prixAchat");
				databaseData[indexActuel][5] = resultat.getFloat("udm");
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
	 * Méthode qui retourne la connection à la base de donnée.
	 * @return la connection de la base de donnée
	 */
	public Connection getCon(){
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
