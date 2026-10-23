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
public abstract class Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String NrChassi;
    private String matricula;
    private String marca;
    private String modelo;
    private double capacidade; // em toneladas
    private Proprietario proprietario;

    

    public Veiculo(String NrChassi, String matricula, String marca, String modelo, double capacidade, Proprietario proprietario) {
        this.NrChassi = NrChassi;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.proprietario = proprietario;
    }

    public abstract String getTipo();

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(double capacidade) {
        this.capacidade = capacidade;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public String getNrChassi() {
        return NrChassi;
    }

    public void setNrChassi(String NrChassi) {
        this.NrChassi = NrChassi;
    }
    

    @Override
    public String toString() {
        return getTipo() + " | " + NrChassi+ " | " + matricula + " | " + marca + " " + modelo
                + " | " + capacidade + " t | Dono: " + proprietario.getNome();
    }

}
