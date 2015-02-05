package Objekte;

public class VorstellungObjekt {

	// id zeit saal titel
	private String id;
	private String zeit;
	private String saal;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getZeit() {
		return zeit;
	}
	public void setZeit(String zeit) {
		this.zeit = zeit;
	}
	public String getSaal() {
		return saal;
	}
	public void setSaal(String saal) {
		this.saal = saal;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	private String titel;
	public VorstellungObjekt(String id, String zeit, String saal, String titel) {
		super();
		this.id = id;
		this.zeit = zeit;
		this.saal = saal;
		this.titel = titel;
	}
	
	public String toString(){
		String output = this.id + " | " + this.zeit + " | " +  this.saal + " | " + this.titel;
		return output;
	}
	
}
