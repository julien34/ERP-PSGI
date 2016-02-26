package jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import vente.model.Client;
import vente.model.Commande;

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
	
	/**
	 * (OBSOLETE) Remplissage de la liste des produits
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
				databaseData[indexActuel][0] = resultat.getString(2);
				
				int categorie_id = resultat.getInt(3);
				databaseData[indexActuel][1] = determinerCategorie(categorie_id);
				
				databaseData[indexActuel][2] = resultat.getFloat(4);
				databaseData[indexActuel][3] = resultat.getFloat(5);
				databaseData[indexActuel][4] = resultat.getString(6);
				databaseData[indexActuel][5] = resultat.getString(7);
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
	 * (OBSOLETE) Determine le code categorie
	 */
	public static String determinerCategorie(int categorie_id)
	{
		try
		{
			Statement stat = con.createStatement();
			ResultSet resultat = stat.executeQuery("SELECT nomcategorie FROM categorie where codecategorie = "+categorie_id);
			resultat.next();
			return resultat.getString(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	static public Object[][] remplirListeClient()
	{
		int longueurTableau = 0;
		int indexActuel = 0;
		String categ;
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
				if(resultat.getString("codecategorieclient").equals("1"))
					categ = "Particulier";
				else
					categ = "Entreprise";
				databaseData[indexActuel][6] = categ;
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
	
	static public ArrayList<Client> getClients()
	{
		int longueurTableau = 0;
		int indexActuel = 0;
		ArrayList<Client> clientBDD = new ArrayList<Client>();
		
		try
		{
			stat = con.createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM VENTE_CLIENTS ORDER BY idclient");
			while(rs.next())
			{
				Client client = new Client(rs.getString("idclient"),rs.getString("nomclient"), rs.getString("prenomclient"),rs.getString("adresseclient"), rs.getString("emailclient"),rs.getString("telclient"),rs.getString("codecategorieclient"));
				clientBDD.add(client);
			}
			rs.close();		
			stat.close();
			return clientBDD;
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
		String categ;	
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
				if(rs.getString("codecategorieclient").equals("1"))
					categ = "Particulier";
				else
					categ = "Entreprise";
				databaseData[indexActuel][6] = categ;
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
						if(rs.getString("codecategorieclient").equals("1"))
							categ = "Particulier";
						else
							categ = "Entreprise";
						databaseData[indexActuel][6] = categ;
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
