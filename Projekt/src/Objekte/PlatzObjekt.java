package Objekte;

public class PlatzObjekt {

	private String reihe;
	private String nummer;
	
	public PlatzObjekt(String reihe, String nummer) {
		this.reihe = reihe;
		this.nummer = nummer;
	}
	public String getReihe() {
		return reihe;
	}
	public void setReihe(String reihe) {
		this.reihe = reihe;
	}
	public String getNummer() {
		return nummer;
	}
	public void setNummer(String nummer) {
		this.nummer = nummer;
	}
	
	public String toString(){
		return "Reihe: " + reihe + " | Nummer: " + nummer;
	}
	
	
}
