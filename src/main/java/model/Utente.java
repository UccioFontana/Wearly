package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Utente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String email;
    private String password;
    private String nome;
    private String cognome;

    @OneToMany(mappedBy = "Utente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CapoAbbigliamento> capiAbbigliamento;

    public Utente(String email, String password, String nome, String cognome) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    public Utente() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public List<CapoAbbigliamento> getCapiAbbigliamento() {
        return capiAbbigliamento;
    }

    public void setCapiAbbigliamento(List<CapoAbbigliamento> capiAbbigliamento) {
        this.capiAbbigliamento = capiAbbigliamento;
    }

    @Override
    public String toString() {
        return "Utente: " +
                "id= " + id +
                ", email= " + email + '\'' +
                ", password= " + password + '\'' +
                ", nome= " + nome + '\'' +
                ", cognome= " + cognome + '\'' +
                ';';
    }
}
