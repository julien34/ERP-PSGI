package vente;

public class Client {
	String idclient, nomclient, prenomclient, adresseclient,mailclient,telclient,codecateg;
	
	public String getCategorie() {
		return codecateg;
	}

	public void setCategorie(String codecateg) {
		this.codecateg = codecateg;
	}

	public void setidClient(String idclient) {
		this.idclient = idclient;
	}

	public void setNom(String nomclient) {
		this.nomclient = nomclient;
	}

	public void setSiret(String prenomclient) {
		this.prenomclient = prenomclient;
	}

	public void setTel(String telclient) {
		this.telclient = telclient;
	}

	public void setAdresse(String adresseclient) {
		this.adresseclient = adresseclient;
	}

	public void setMailClient(String mailclient) {
		this.mailclient = mailclient;
	}
	
	public String getIdClient() {
		return idclient;
	}

	public String getNomClient() {
		return nomclient;
	}

	public String getPrenomClient() {
		return prenomclient;
	}

	public String getTelClient() {
		return telclient;
	}

	public String getAdresseClient() {
		return adresseclient;
	}

	public String getMailClient(){
		return mailclient;
	}
	public  Client(String idclient, String nomclient, String prenomclient, String adresseclient,String mailclient, String telclient, String codecateg){
		this.idclient = idclient;
		this.nomclient = nomclient;
		this.prenomclient = prenomclient;
		this.telclient = telclient;
		this.mailclient = mailclient;
		this.adresseclient = adresseclient;
		this.codecateg = codecateg;	
	}
	
	
}

