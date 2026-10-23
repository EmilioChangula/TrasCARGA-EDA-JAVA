/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class Proprietario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String contacto;
    private String documentoID; // BI / NUIT / passaporte

    public Proprietario(String nome, String contacto, String documentoID) {
        this.nome = nome;
        this.contacto = contacto;
        this.documentoID = documentoID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDocumentoID() {
        return documentoID;
    }

    public void setDocumentoID(String documentoID) {
        this.documentoID = documentoID;
    }

    @Override
    public String toString() {
        return "Proprietario{" + "nome=" + nome + ", contacto=" + contacto + ", documentoID=" + documentoID + '}';
    }

}
