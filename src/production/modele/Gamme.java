package production.modele;

import java.util.ArrayList;

public class Gamme {
	private int code;
	private String nom;
	private Emplacement emplacement;
	private ArrayList<Nomenclature> liste_nomenclature = new ArrayList<Nomenclature>();
	private ArrayList<Operation> liste_operation = new ArrayList<Operation>();
	
	public Gamme(int code, String nom, Emplacement emplacement) {
		super();
		this.nom = nom;
		this.code = code;
		this.emplacement = emplacement;
	}

	public String getNom() {
		return nom;
	}

	public int getCode() {
		return code;
	}

	public Emplacement getEmplacement() {
		return emplacement;
	}

	public ArrayList<Nomenclature> getListe_nomenclature() {
		return liste_nomenclature;
	}

	public ArrayList<Operation> getListe_operation() {
		return liste_operation;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setEmplacement(Emplacement emplacement) {
		this.emplacement = emplacement;
	}

	public void setListe_nomenclature(ArrayList<Nomenclature> liste_nomenclature) {
		this.liste_nomenclature = liste_nomenclature;
	}

	public void setListe_operation(ArrayList<Operation> liste_operation) {
		this.liste_operation = liste_operation;
	}

	
}
