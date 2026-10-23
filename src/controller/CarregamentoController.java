/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;
import model.Carregamento;
import model.Cliente;
import model.Proprietario;
import model.Veiculo;
import persistencia.Ficheiro;
import tad.DequeArray;

/**
 *
 * @author user
 */
public class CarregamentoController {

    private static final String FICHEIRO = "carregamentos.dat";
    private DequeArray<Carregamento> lista;

    public CarregamentoController() {
        this.lista = Ficheiro.carregar(FICHEIRO);
    }

    // Validação: quantidade não pode ultrapassar a capacidade do veículo
    public boolean validar(Veiculo veiculo, double quantidade) {
        return veiculo != null && quantidade > 0
                && quantidade <= veiculo.getCapacidade();
    }

    public boolean registar(Cliente cliente, Veiculo veiculo, double quantidade,
            String origem, String destino, LocalDate data, double valor) {
        if (cliente == null || !validar(veiculo, quantidade) || valor < 0) {
            return false;
        }
        lista.addLast(new Carregamento(cliente, veiculo, quantidade,
                origem, destino, data, valor));
        Ficheiro.guardar(FICHEIRO, lista);
        return true;
    }

    public DequeArray<Carregamento> listarTodos() {
        return lista;
    }

    // Transportes feitos pelos veículos de um proprietário
    public DequeArray<Carregamento> listarPorProprietario(String documento) {
        DequeArray<Carregamento> resultado = new DequeArray<>();
        for (Carregamento c : lista) {
            Proprietario p = c.getProprietario();
            if (p.getDocumentoID().equalsIgnoreCase(documento)) {
                resultado.addLast(c);
            }
        }
        return resultado;
    }

    // Total arrecadado por um veículo
    public double totalPorVeiculo(String matricula) {
        double total = 0;
        for (Carregamento c : lista) {
            if (c.getVeiculo().getMatricula().equalsIgnoreCase(matricula)) {
                total += c.getValor();
            }
        }
        return total;
    }
    // Para impedir apagar veículos ou clientes que já têm carregamentos

    public boolean temVeiculo(String matricula) {
        for (Carregamento c : lista) {
            if (c.getVeiculo().getMatricula().equalsIgnoreCase(matricula)) {
                return true;
            }
        }
        return false;
    }

    public boolean temCliente(String documentoCli) {
        for (Carregamento c : lista) {
            if (c.getCliente().getDocumentoCli().equalsIgnoreCase(documentoCli)) {
                return true;
            }
        }
        return false;
    }

    public void relinkar(VeiculoController vc, ClienteController cc) {
        for (Carregamento c : lista) {
            Veiculo v = vc.buscarPorMatricula(c.getVeiculo().getMatricula());
            if (v != null) {
                c.setVeiculo(v);
            }
            Cliente cl = cc.buscarPorDocumento(c.getCliente().getDocumentoCli());
            if (cl != null) {
                c.setCliente(cl);
            }
        }
    }
    public Carregamento ultimo() {
    return lista.peekLast();
}
}
