package vente.model;

public class Client {
	String idclient, nomclient, prenomclient, adresseclient,mailclient,telclient,categorie;
	
	/**
	 * @return categorie
	 */
	public String getCategorie() {
		return categorie;
	}

	/**
	 * @param codecateg
	 */
	public void setCategorie(String codecateg) {
		this.categorie = codecateg;
	}

	/**
	 * @param idclient
	 */
	public void setidClient(String idclient) {
		this.idclient = idclient;
	}

	/**
	 * @param nomclient
	 */
	public void setNom(String nomclient) {
		this.nomclient = nomclient;
	}

	/**
	 * @param prenomclient
	 */
	public void setSiret(String prenomclient) {
		this.prenomclient = prenomclient;
	}

	/**
	 * @param telclient
	 */
	public void setTel(String telclient) {
		this.telclient = telclient;
	}

	/**
	 * @param adresseclient
	 */
	public void setAdresse(String adresseclient) {
		this.adresseclient = adresseclient;
	}

	/**
	 * @param mailclient
	 */
	public void setMailClient(String mailclient) {
		this.mailclient = mailclient;
	}
	
	/**
	 * @return idClient
	 */
	public String getIdClient() {
		return idclient;
	}

	/**
	 * @return nomClient
	 */
	public String getNomClient() {
		return nomclient;
	}

	/**
	 * @return prenomclient
	 */
	public String getPrenomClient() {
		return prenomclient;
	}

	/**
	 * @return telclient
	 */
	public String getTelClient() {
		return telclient;
	}

	/**
	 * @return adresseclient
	 */
	public String getAdresseClient() {
		return adresseclient;
	}

	/**
	 * @return mailclient
	 */
	public String getMailClient(){
		return mailclient;
	}
	
	/**
	 * @param idclient
	 * @param nomclient
	 * @param prenomclient
	 * @param adresseclient
	 * @param mailclient
	 * @param telclient
	 * @param codecateg
	 */
	public  Client(String idclient, String nomclient, String prenomclient, String adresseclient,String mailclient, String telclient, String codecateg){
		this.idclient = idclient;
		this.nomclient = nomclient;
		this.prenomclient = prenomclient;
		this.telclient = telclient;
		this.mailclient = mailclient;
		this.adresseclient = adresseclient;
		this.categorie = codecateg;	
	}

	public Client() {
		this.idclient = null;
		this.nomclient = null;
		this.prenomclient = null;
		this.telclient = null;
		this.mailclient = null;
		this.adresseclient = null;
		this.categorie = null;	
	}

	/**
	 * @param idCli
	 */
	public Client(String idCli) {
		idclient = idCli;
	}
	
	
}

