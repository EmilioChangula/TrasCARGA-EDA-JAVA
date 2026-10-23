/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author user
 */
public class Carrinha extends Veiculo {

    private static final long serialVersionUID = 1L;

    public Carrinha(String NrChassi, String matricula, String marca, String modelo, double capacidade, Proprietario proprietario) {
        super(NrChassi, matricula, marca, modelo, capacidade, proprietario);
    }

    @Override
    public String getTipo() {
        return "Carrinha";
    }

}
