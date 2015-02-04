package Objekte;

public class KundeObjekt {

	private String email;
	private String name;
	private String vorname;
	private String datum;
	private String plz;
	private String strasse;
	private String telefon;
	private String handy;
	private String passwort;
	


	public KundeObjekt(String email, String name, String vorname, String datum,
			String plz, String strasse, String telefon, String handy,
			String passwort) {
		this.email = email;
		this.name = name;
		this.vorname = vorname;
		this.datum = datum;
		this.plz = plz;
		this.strasse = strasse;
		this.telefon = telefon;
		this.handy = handy;
		this.passwort = passwort;
	}
	
	public String toString(){
		return email + " | " + name + " | " + vorname ;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getHandy() {
		return handy;
	}

	public void setHandy(String handy) {
		this.handy = handy;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
	public String getAll(){
		return email + " | "+  name + " | "+ vorname + System.lineSeparator() +  plz + " | "+strasse +" | "+ datum + System.lineSeparator()+ passwort;
	}
}
