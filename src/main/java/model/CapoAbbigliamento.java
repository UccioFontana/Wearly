package model;

import jakarta.persistence.*;

import java.io.Serializable;

public class CapoAbbigliamento {

    private int id;

    private int idUtente;
    private String descrizione;
    private String nome;
    private String categoria;
    private String stagione;
    private String colore;
    private String immagine;
    private String materiale;

    public CapoAbbigliamento() {
    }

    public CapoAbbigliamento(int utente, String descrizione, String nome, String categoria, String stagione, String colore, String immagine, String materiale) {
        this.idUtente = utente;
        this.descrizione = descrizione;
        this.nome = nome;
        this.categoria = categoria;
        this.stagione = stagione;
        this.colore = colore;
        this.immagine = immagine;
        this.materiale = materiale;
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

    public void setIdUtente(int utente) {
        this.idUtente = utente;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public String getStagione() {
        return stagione;
    }

    public void setStagione(String stagione) {
        this.stagione = stagione;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getMateriale() {
        return materiale;
    }

    public void setMateriale(String materiale) {
        this.materiale = materiale;
    }

    @Override
    public String toString() {
        return "CapoAbbigliamento{" +
                "id=" + id +
                ", idutente=" + idUtente +
                ", descrizione='" + descrizione + '\'' +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", stagione='" + stagione + '\'' +
                ", colore='" + colore + '\'' +
                ", immagine='" + immagine + '\'' +
                ", materiale='" + materiale + '\'' +
                '}';
    }
}
