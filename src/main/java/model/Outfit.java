package model;

import java.util.ArrayList;
import java.util.List;

public class Outfit {
    private int id;
    private String nome;
    private String descrizione;
    private List<CapoAbbigliamento> listaCapi;

    public Outfit() {
    }

    public Outfit(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.listaCapi = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<CapoAbbigliamento> getListaCapi() {
        return listaCapi;
    }

    public void setListaCapi(List<CapoAbbigliamento> listaCapi) {
        this.listaCapi = listaCapi;
    }
}
