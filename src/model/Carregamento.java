/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author user
 */
public class Carregamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Cliente cliente;
    private Veiculo veiculo;
    private double quantidade; // toneladas
    private String origem;
    private String destino;
    private LocalDate data;
    private double valor;

    public Carregamento(Cliente cliente, Veiculo veiculo, double quantidade, String origem, String destino, LocalDate data, double valor) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.quantidade = quantidade;
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.valor = valor;
    }

        public Proprietario getProprietario() {
        return veiculo.getProprietario();
    }

    // Getters "planos" para as colunas das tabelas
    public String getNomeCliente() {
        return cliente.getNome();
    }

    public String getMatricula() {
        return veiculo.getMatricula();
    }
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Carregamento{" + "cliente=" + cliente + ", veiculo=" + veiculo + ", quantidade=" + quantidade + ", origem=" + origem + ", destino=" + destino + ", data=" + data + ", valor=" + valor + '}';
    }

}
