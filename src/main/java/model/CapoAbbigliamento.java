package model;

import jakarta.persistence.*;

import java.io.Serializable;

public class CapoAbbigliamento {

    private int id;
    private int idUtente;
    private String nome;
    private String descrizione;
    private String materiale;
    private String colore;
    private String stile;
    private String stagione;
    private String stato;
    private String immagine;
    private String categoria;
    private String parteDelCorpo;

    public CapoAbbigliamento() {
    }

    public CapoAbbigliamento(int idUtente, String nome, String descrizione, String materiale, String colore, String stile, String stagione, String stato, String immagine, String categoria, String parteDelCorpo) {
        this.idUtente = idUtente;
        this.nome = nome;
        this.descrizione = descrizione;
        this.materiale = materiale;
        this.colore = colore;
        this.stile = stile;
        this.stagione = stagione;
        this.stato = stato;
        this.immagine = immagine;
        this.categoria = categoria;
        this.parteDelCorpo = parteDelCorpo;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getMateriale() {
        return materiale;
    }

    public void setMateriale(String materiale) {
        this.materiale = materiale;
    }

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public String getStile() {
        return stile;
    }

    public void setStile(String stile) {
        this.stile = stile;
    }

    public String getStagione() {
        return stagione;
    }

    public void setStagione(String stagione) {
        this.stagione = stagione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getParteDelCorpo() {
        return parteDelCorpo;
    }

    public void setParteDelCorpo(String parteDelCorpo) {
        this.parteDelCorpo = parteDelCorpo;
    }

    @Override
    public String toString() {
        return "CapoAbbigliamento{" +
                "id=" + id +
                ", idUtente=" + idUtente +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", materiale='" + materiale + '\'' +
                ", colore='" + colore + '\'' +
                ", stile='" + stile + '\'' +
                ", stagione='" + stagione + '\'' +
                ", stato='" + stato + '\'' +
                ", immagine='" + immagine + '\'' +
                ", categoria='" + categoria + '\'' +
                ", parteDelCorpo='" + parteDelCorpo + '\'' +
                '}';
    }
}