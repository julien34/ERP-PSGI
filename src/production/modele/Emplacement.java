package production.modele;

import java.util.ArrayList;

public class Emplacement {
	private int code;
	private String nom;
	private ArrayList<Gamme> liste_gamme = new ArrayList<Gamme>();
	
	public Emplacement(int code, String nom) {
		super();
		this.code = code;
		this.nom = nom;
	}

	public Emplacement(int code, String nom, ArrayList<Gamme> liste_gamme) {
		super();
		this.code = code;
		this.nom = nom;
		this.liste_gamme = liste_gamme;
	}

	public int getCode() {
		return code;
	}

	public String getNom() {
		return nom;
	}

	public ArrayList<Gamme> getListeGamme() {
		return liste_gamme;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setListeGamme(ArrayList<Gamme> liste_gamme) {
		this.liste_gamme = liste_gamme;
	}
	
}
