package model;

import java.time.LocalDate;

public class Ticket {

    private int id;
    private String titolo;
    private String descrizione;
    private String stato;
    private LocalDate dataCreazione;
    private int idUtente;
    private int idAmministratore;



    public Ticket(int idUtente, String titolo, String descrizione) {
        this.idUtente = idUtente;
        this.titolo = titolo;
        this.descrizione = descrizione;
    }
    public Ticket() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public LocalDate getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDate dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public int getIdAmministratore() {
        return idAmministratore;
    }

    public void setIdAmministratore(int idAmministratore) {
        this.idAmministratore = idAmministratore;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", stato='" + stato + '\'' +
                ", dataCreazione=" + dataCreazione +
                ", idUtente=" + idUtente +
                ", idAmministratore=" + idAmministratore +
                '}';
    }
}
