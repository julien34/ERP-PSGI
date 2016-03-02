package vente.model;

public class Categories {
	
	/**
	 * id, une String qui représente la clé primaire de la catégorie.
	 * nom, une String qui reorésente le nom de la catégorie.
	 * idparent, qui représente la clé étrangère de la catégorie parente.
	 */
	private String id, nom, idParent;
	
	/**
	 * Constructeur qui permet de créer une catégorie avec un id et un nom.
	 * @param id, l'id de la catégorie.
	 * @param nom, le nom de la catégorie.
	 */
	public Categories(String id, String nom){
		this.nom = nom;
		this.id = id;
	}
	
	
	/**
	 * Méthode get sur l'ID courante.
	 * @return l'id courante.
	 */
	public String getId() {
		return this.id;
	}

	
	/**
	 * Méthode set qui permet de changer l'id courante.
	 * @param id, un ID.
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	 * Méthode get sur le nom courant.
	 * @return le nom courant.
	 */
	public String getNom() {
		return this.nom;
	}

	
	/**
	 * Méthode set qui permet de changer le nom courant.
	 * @param nom, un nom.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	
	/**
	 * Méthode get sur l'ID parent courante.
	 * @return l'id de la catégorie parent courante.
	 */
	public String getIdParent() {
		return this.idParent;
	}

	/**
	 * Méthode set qui permet de changer l'id parent courante.
	 * @param idparent, un ID parent.
	 */
	public void setIdParent(String idParent) {
		this.idParent = idParent;
	}
	
}