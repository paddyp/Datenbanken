package Objekte;

public class ReservierungObjekt {

	private String id;
	private String titel;
	private String fsk;
	private String saal;
	private String zeit;
	private String plaetze;
	
	@Override
	public String toString() {
		return id + " | " + titel + " | "
				+ fsk + " | " + saal + " | " + zeit + " | "
				+ plaetze;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getFsk() {
		return fsk;
	}

	public void setFsk(String fsk) {
		this.fsk = fsk;
	}

	public String getSaal() {
		return saal;
	}

	public void setSaal(String saal) {
		this.saal = saal;
	}

	public String getZeit() {
		return zeit;
	}

	public void setZeit(String zeit) {
		this.zeit = zeit;
	}

	public String getPlaetze() {
		return plaetze;
	}

	public void setPlaetze(String plaetze) {
		this.plaetze = plaetze;
	}

	public ReservierungObjekt(String id, String titel, String fsk, String saal,
			String zeit, String plaetze) {
		this.id = id;
		this.titel = titel;
		this.fsk = fsk;
		this.saal = saal;
		this.zeit = zeit;
		this.plaetze = plaetze;
	}
	
	


}
