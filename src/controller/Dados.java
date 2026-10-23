/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author user
 */


public class Dados {

    public static final ProprietarioController proprietarios = new ProprietarioController();
    public static final VeiculoController veiculos = new VeiculoController();
    public static final ClienteController clientes = new ClienteController();
    public static final CarregamentoController carregamentos = new CarregamentoController();

    static {
        veiculos.relinkar(proprietarios);
        carregamentos.relinkar(veiculos, clientes);
    }

    private Dados() { }
}
