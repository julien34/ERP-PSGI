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
	 * Ouvre la connexion avec le serveur SQL spécifié par les 5 arguments suivants.
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
