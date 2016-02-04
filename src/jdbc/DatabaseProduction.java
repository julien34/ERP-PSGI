package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import production.modele.Categorie;
import production.modele.CentreDeTravail;
import production.modele.Composition;
import production.modele.Emplacement;
import production.modele.Gamme;
import production.modele.Nomenclature;
import production.modele.Operation;
import production.modele.Produit;
import production.modele.UniteDeMesure;
import principal.FenetrePrincipale;

/**
 * Cette classe gere toutes les connections à la base de données
 * @author Mickael
 *
 */
public class DatabaseProduction 
{	
	static private PreparedStatement pst_gamme;
	static private PreparedStatement pst_operation;
	static private PreparedStatement pst_centre;
	static private PreparedStatement pst_produit;
	static private PreparedStatement pst_categorie;
	static private PreparedStatement pst_udm;
	static private PreparedStatement pst_emplacement;
	static private PreparedStatement pst_nomenclature;
	static private PreparedStatement pst_composition;
	static private PreparedStatement pst_parent;
	
	static private ResultSet rs_gamme;
	static private ResultSet rs_operation;
	static private ResultSet rs_centre;
	static private ResultSet rs_produit;
	static private ResultSet rs_categorie;
	static private ResultSet rs_udm;
	static private ResultSet rs_emplacement;
	static private ResultSet rs_nomenclature;
	static private ResultSet rs_composition;
	static private ResultSet rs_parent;
	
	static private ArrayList<Gamme> liste_gamme = new ArrayList<>();
	static private ArrayList<Operation> liste_operation = new ArrayList<>();
	static private ArrayList<CentreDeTravail> liste_centre = new ArrayList<>();
	static private ArrayList<Produit> liste_produit = new ArrayList<>();
	static private ArrayList<Categorie> liste_categorie = new ArrayList<>();
	static private ArrayList<UniteDeMesure> liste_udm = new ArrayList<>();
	static private ArrayList<Emplacement> liste_emplacement = new ArrayList<>();
	static private ArrayList<Nomenclature> liste_nomenclature = new ArrayList<>();
	static private ArrayList<Composition> liste_composition = new ArrayList<>();
	
	/**
	 * Charge les données des tables dans un objet
	 */
	public static void chargerClasse()
	{
		try
		{
			liste_gamme.clear();
			liste_operation.clear();
			liste_centre.clear();
			liste_produit.clear();
			liste_categorie.clear();
			liste_udm.clear();
			liste_emplacement.clear();
			liste_nomenclature.clear();
			liste_composition.clear();
			
			pst_gamme = DatabaseConnection.getCon().prepareStatement("SELECT g.* FROM Gamme g",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pst_operation = DatabaseConnection.getCon().prepareStatement("SELECT o.* FROM Operation o",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pst_centre = DatabaseConnection.getCon().prepareStatement("SELECT c.* from CentreDeTravail c",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pst_produit = DatabaseConnection.getCon().prepareStatement("SELECT p.* from Produit p",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pst_categorie = DatabaseConnection.getCon().prepareStatement("SELECT c.* from Categorie c",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pst_udm = DatabaseConnection.getCon().prepareStatement("SELECT u.* from UniteDeMesure u",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pst_emplacement = DatabaseConnection.getCon().prepareStatement("SELECT e.* from Emplacement e",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pst_nomenclature = DatabaseConnection.getCon().prepareStatement("SELECT n.* from Nomenclature n",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pst_composition = DatabaseConnection.getCon().prepareStatement("SELECT c.* from Composition c",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pst_parent = DatabaseConnection.getCon().prepareStatement("SELECT nom from categorie where code = ?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			//Charger les Emplacement (bloc simple)
			rs_emplacement = pst_emplacement.executeQuery();
			while(rs_emplacement.next()){
				Emplacement emplacement = new Emplacement(rs_emplacement.getInt("code"), rs_emplacement.getString("nom"));
				liste_emplacement.add(emplacement);
			}
			
			//Charger les UDM (bloc simple)
			rs_udm = pst_udm.executeQuery();
			while(rs_udm.next())
			{
				UniteDeMesure udm = new UniteDeMesure(rs_udm.getInt("code"), rs_udm.getString("nom"));
				liste_udm.add(udm);
			}

			//Charger les centres (bloc simple)
			rs_centre = pst_centre.executeQuery();
			while(rs_centre.next())
			{
				CentreDeTravail centre = new CentreDeTravail(rs_centre.getInt("code"), rs_centre.getString("nom"), rs_centre.getString("type"), rs_centre.getFloat("capaciteParCycle"), rs_centre.getFloat("tempsParCycle"));
				liste_centre.add(centre);
			}
			
			//Charger les categories (bloc simple)
			rs_categorie = pst_categorie.executeQuery();
			while(rs_categorie.next())
			{
				Categorie categorie_parent = null;
				Categorie categorie = new Categorie(rs_categorie.getInt("code"), rs_categorie.getString("nom"), categorie_parent);
				liste_categorie.add(categorie);
			}
			
			//Charger les parents			
			for (Categorie categorie : liste_categorie) 
			{				
				rs_categorie.beforeFirst();
				while(rs_categorie.next())
				{
					if(categorie.getCode() == rs_categorie.getInt("code"))
					{
						pst_parent.setInt(1, rs_categorie.getInt("parent"));
						rs_parent = pst_parent.executeQuery();
						String nom_parent = "";
						
						while(rs_parent.next())
						{
							nom_parent = rs_parent.getString("nom");
						}

						Categorie categorie_parent = new Categorie(rs_categorie.getInt("parent"), nom_parent, null);
						categorie.setParent(categorie_parent);
						rs_parent.afterLast();						
					}
				}
				
			}
			
			//Charger les produits (gros bloc)
			rs_produit = pst_produit.executeQuery();
			while(rs_produit.next())
			{
				//Affecter la categorie au produit
				Categorie categorie = null;
				for (Categorie categorie_temp : liste_categorie) {
					if(categorie_temp.getCode() == rs_produit.getInt("categorie")){
						categorie = categorie_temp;
					}
				}

				//Affecter l'unité de mesure au produit
				UniteDeMesure udm = null;
				for (UniteDeMesure udm_temp : liste_udm) {
					if(udm_temp.getNom().equals(rs_produit.getString("uniteDeMesure"))){
						udm = udm_temp;
					}
				}
				Produit produit = new Produit(rs_produit.getInt("code"), rs_produit.getString("nom"), categorie, rs_produit.getFloat("prixAchat"), rs_produit.getFloat("prixVente"), udm, rs_produit.getString("disponibilite"));
				liste_produit.add(produit);
				
			}

			//Charger les gammes (gros bloc)
			rs_gamme = pst_gamme.executeQuery();
			while(rs_gamme.next())
			{
				Emplacement emplacement = null;
				Gamme gamme = new Gamme(rs_gamme.getInt("code"), rs_gamme.getString("nom"), emplacement);
				//affecter un emplacement pour chaque gamme
				for (Emplacement unEmplacement : liste_emplacement) {
					if(unEmplacement.getCode() == rs_gamme.getInt("emplacement")){
						gamme.setEmplacement(unEmplacement);
					}
				}
				liste_gamme.add(gamme);
			}
			
			//Charger les operations
			rs_operation = pst_operation.executeQuery();
			while(rs_operation.next()){
								
				//Affecter la gamme a l'operation
				Gamme gamme = null;
				for (Gamme gamme_temp : liste_gamme) {
					if(gamme_temp.getCode() == rs_operation.getInt("gamme")){
						gamme = gamme_temp;
					}
				}
				
				//Affecter le centre de travail a l'operation
				CentreDeTravail centre = null;
				for (CentreDeTravail centre_temp : liste_centre) {
					if(centre_temp.getCode() == rs_operation.getInt("centre")){
						centre = centre_temp;
					}
				}
				Operation operation = new Operation(rs_operation.getInt("code"), rs_operation.getString("nom"), rs_operation.getInt("sequence"), rs_operation.getFloat("nombreCycle"), rs_operation.getFloat("nombreHeure"), gamme, centre);
				liste_operation.add(operation);
			}
			
			//charger les nomenclatures
			rs_nomenclature = pst_nomenclature.executeQuery();
			while(rs_nomenclature.next()){
				
				//Affecter la gamme a la nomenclature
				Gamme gamme = null;
				for(Gamme gamme_temp : liste_gamme){
					if(gamme_temp.getCode() == rs_nomenclature.getInt("gamme")){
						gamme = gamme_temp;
					}
				}
				//Affecter le produit a la nomenclature
				Produit produit = null;
				for(Produit produit_temp : liste_produit){
					if(produit_temp.getCode() == rs_nomenclature.getInt("produit")){
						produit = produit_temp;
					}
				}
				
				Nomenclature nomenclature = new Nomenclature(rs_nomenclature.getInt("code"), rs_nomenclature.getString("nom"), rs_nomenclature.getInt("quantite"), rs_nomenclature.getString("type"), gamme, produit);
				liste_nomenclature.add(nomenclature);
			}
			
			//charger les composition
			rs_composition = pst_composition.executeQuery();
			while(rs_composition.next()){
				
				Nomenclature nomenclatureA = null;
				Nomenclature nomenclatureB = null;
				
				for(Nomenclature nomenclature : liste_nomenclature){
					if(nomenclature.getCode() == rs_composition.getInt("nomenclatureA")){
						nomenclatureA = nomenclature;
					}
				}
				for(Nomenclature nomenclature : liste_nomenclature){
					if(nomenclature.getCode() == rs_composition.getInt("nomenclatureB")){
						nomenclatureB = nomenclature;
					}
				}
				Composition composition = new Composition(rs_composition.getInt("code"), nomenclatureA, nomenclatureB);
				liste_composition.add(composition);
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Le chargement des données a échoué !","Erreur",JOptionPane.ERROR_MESSAGE,null);
			e.printStackTrace();
		}
	}
	
	/**
	 * Retourne le resultset gamme
	 * @return rs_gamme
	 */
	public static ResultSet getRsGamme() 
	{
		return rs_gamme;
	}

	/**
	 * Retourne le resultset operation
	 * @return rs_operation
	 */
	public static ResultSet getRsOperation() 
	{
		return rs_operation;
	}

	/**
	 * Retourne le resultset centre
	 * @return rs_centre
	 */
	public static ResultSet getRsCentre() 
	{
		return rs_centre;
	}

	/**
	 * Retourne le resultset produit
	 * @return rs_produit
	 */
	public static ResultSet getRsProduit() 
	{
		return rs_produit;
	}

	/**
	 * Retourne le resultset categorie
	 * @return rs_categorie
	 */
	public static ResultSet getRsCategorie() 
	{
		return rs_categorie;
	}

	/**
	 * Retourne le resultset udm
	 * @return rs_udm
	 */
	public static ResultSet getRsUdm() 
	{
		return rs_udm;
	}
	
	/**
	 * Retourne le resultset emplacement
	 * @return rs_udm
	 */
	public static ResultSet getRsEmplacement() 
	{
		return rs_emplacement;
	}
	
	/**
	 * Retourne le resultset composition
	 * @return rs_udm
	 */
	public static ResultSet getRsComposition() 
	{
		return rs_composition;
	}
	
	/**
	 * Retourne le resultset nomenclature
	 * @return rs_udm
	 */
	public static ResultSet getRsNomenclature() 
	{
		return rs_nomenclature;
	}
	
	/**
	 * Permet d'obtenir la liste des gammes
	 * @return liste_gamme
	 */
	static public ArrayList<Gamme> getListeGamme()
	{
		return liste_gamme;
	}

	/**
	 * Permet d'obtenir la liste des operations
	 * @return liste_operation
	 */
	static public ArrayList<Operation> getListeOperation()
	{
		return liste_operation;
	}

	/**
	 * Permet d'obtenir la liste des centre de travail
	 * @return liste_centre
	 */
	static public ArrayList<CentreDeTravail> getListeCentreDeTravail()
	{
		return liste_centre;
	}

	/**
	 * Permet d'obtenir la liste des produits
	 * @return liste_produit
	 */
	static public ArrayList<Produit> getListeProduit()
	{
		return liste_produit;
	}

	/**
	 * Permet d'obtenir la liste des categories
	 * @return liste_categorie
	 */
	static public ArrayList<Categorie> getListeCategorie()
	{
		return liste_categorie;
	}

	/**
	 * Permet d'obtenir la liste des udm
	 * @return liste_udm
	 */
	static public ArrayList<UniteDeMesure> getListeUdm()
	{
		return liste_udm;
	}

	/**
	 * Permet d'obtenir la liste des emplacements
	 * @return liste_emplacement
	 */
	static public ArrayList<Emplacement> getListeEmplacement()
	{
		return liste_emplacement;
	}

	/**
	 * Permet d'obtenir la liste des nomenclatures
	 * @return liste_nomenclature
	 */
	static public ArrayList<Nomenclature> getListeNomenclature()
	{
		return liste_nomenclature;
	}

	/**
	 * Permet d'obtenir la liste des compositions
	 * @return liste_composition
	 */
	static public ArrayList<Composition> getListeComposition()
	{
		return liste_composition;
	}
	
	/**
	 * Permet d'utiliser une procedure.
	 * @param sql La requete utilisée
	 * @return True si la requete est un succes, sinon False
	 */
	static public boolean procedure(String sql)
	{
		try
		{
			PreparedStatement pst = DatabaseConnection.getCon().prepareStatement(sql);
			pst.executeQuery();
			pst.close();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Permet d'obtenir le numero de la sequence actuelle.
	 * @param sql La requete utilisée
	 * @return Le numero de la sequence actuelle
	 */
	static public int getSequence(String sql)
	{
		try
		{
			PreparedStatement pst = DatabaseConnection.getCon().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			rs.next();
			int code = rs.getInt(1);
			pst.close();
			return code;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Ferme la connexion au serveur SQL.
	 */
	static public void disconnect()
	{
		try
		{
			DatabaseConnection.getCon().close();
			FenetrePrincipale.connexionClosed();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Deconnection impossible !","Erreur",JOptionPane.ERROR_MESSAGE,null);
		}
	}
}
