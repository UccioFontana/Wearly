package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class Utente {

    private int id;
    private String email;
    private String password;
    private String nome;
    private String cognome;

    public Utente(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utente)) return false;
        Utente utente = (Utente) o;
        return id == utente.id && Objects.equals(email, utente.email) && Objects.equals(password, utente.password) && Objects.equals(nome, utente.nome) && Objects.equals(cognome, utente.cognome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, nome, cognome);
    }
}
