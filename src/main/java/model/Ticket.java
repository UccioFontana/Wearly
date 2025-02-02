package model;

public class Ticket {

    private int id;

    private int idUtente;

    private String oggetto;

    private String messaggio;

    public Ticket(int idUtente, String oggetto, String messaggio) {
        this.idUtente = idUtente;
        this.oggetto = oggetto;
        this.messaggio = messaggio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", idUtente=" + idUtente +
                ", oggetto='" + oggetto + '\'' +
                ", messaggio='" + messaggio + '\'' +
                '}';
    }
}
