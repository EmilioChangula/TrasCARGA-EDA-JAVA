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
public class Cliente implements Serializable{
    private static final long serialVersionUID = 1L;

    private String documentoCli;
    private String nome;
    private String contacto;
    private String localizacao;

    public Cliente(String documentoCli, String nome, String contacto, String localizacao) {
        this.documentoCli= documentoCli;
        this.nome = nome;
        this.contacto = contacto;
        this.localizacao = localizacao;
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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDocumentoCli() {
        return documentoCli;
    }

    public void setDocumentoCli(String documentoCli) {
        this.documentoCli = documentoCli;
    }

    @Override
    public String toString() {
        return "Cliente{" + "documentoCli=" + documentoCli + ", nome=" + nome + ", contacto=" + contacto + ", localizacao=" + localizacao + '}';
    }

    
    
}
