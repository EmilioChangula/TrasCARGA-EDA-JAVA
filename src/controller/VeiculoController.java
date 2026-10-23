/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Camiao;
import model.Carrinha;
import model.Proprietario;
import model.Veiculo;
import persistencia.Ficheiro;
import tad.DequeArray;

/**
 *
 * @author user
 */
public class VeiculoController {

    private static final String FICHEIRO = "veiculos.dat";
    private DequeArray<Veiculo> lista;

    public VeiculoController() {
        this.lista = Ficheiro.carregar(FICHEIRO);
    }

    public boolean cadastrarCamiao(String NrChassi, String matricula, String marca, String modelo,
            double capacidade, Proprietario dono) {
        if (buscarPorMatricula(matricula) != null || dono == null || capacidade <= 0) {
            return false;
        }
        lista.addLast(new Camiao(NrChassi, matricula, marca, modelo, capacidade, dono));
        Ficheiro.guardar(FICHEIRO, lista);
        return true;
    }

    public boolean cadastrarCarrinha(String NrChassi, String matricula, String marca, String modelo,
            double capacidade, Proprietario dono) {
        if (buscarPorMatricula(matricula) != null || dono == null || capacidade <= 0) {
            return false;
        }
        lista.addLast(new Carrinha(NrChassi,matricula, marca, modelo, capacidade, dono));
        Ficheiro.guardar(FICHEIRO, lista);
        return true;
    }

    public Veiculo buscarPorMatricula(String matricula) {
        for (Veiculo v : lista) {
            if (v.getMatricula().equalsIgnoreCase(matricula)) {
                return v;
            }
        }
        return null;
    }
    public Veiculo buscarPorChassi(String nrChassi) {
    for (Veiculo v : lista) {
        if (v.getNrChassi() != null && v.getNrChassi().equalsIgnoreCase(nrChassi)) {
            return v;
        }
    }
    return null;
}

    public boolean actualizar(String NrChassi,String matricula, String marca, String modelo, double capacidade) {
        
        Veiculo v = buscarPorMatricula(matricula);
        if (v == null || capacidade <= 0) {
            return false;
        }
        v.setNrChassi(NrChassi);
        v.setMarca(marca);
        v.setModelo(modelo);
        v.setCapacidade(capacidade);
        Ficheiro.guardar(FICHEIRO, lista);
        return true;
    }

    // Lista só camiões, por capacidade crescente
    public DequeArray<Veiculo> listarCamioes() {
        return ordenadosPorCapacidade(true);
    }

    // Lista só carrinhas, por capacidade crescente
    public DequeArray<Veiculo> listarCarrinhas() {
        return ordenadosPorCapacidade(false);
    }

    private DequeArray<Veiculo> ordenadosPorCapacidade(boolean camioes) {
        DequeArray<Veiculo> resultado = new DequeArray<>();

        for (Veiculo v : lista) {
            if ((v instanceof Camiao) != camioes) {
                continue;
            }

            // inserção ordenada (sem usar Collections.sort)
            int pos = 0;
            while (pos < resultado.size()
                    && resultado.get(pos).getCapacidade() <= v.getCapacidade()) {
                pos++;
            }
            DequeArray<Veiculo> aux = new DequeArray<>();
            // guarda os que ficam depois da posição
            while (resultado.size() > pos) {
                aux.addFirst(resultado.removeLast());
            }
            resultado.addLast(v);
            while (!aux.isEmpty()) {
                resultado.addLast(aux.removeFirst());
            }
        }
        return resultado;
    }

    public DequeArray<Veiculo> listarTodos() {
        return lista;
    }

    public boolean remover(String matricula) {
        Veiculo alvo = buscarPorMatricula(matricula);
        if (alvo == null) {
            return false;
        }

        DequeArray<Veiculo> nova = new DequeArray<>();
        for (Veiculo v : lista) {
            if (v != alvo) {
                nova.addLast(v);
            }
        }
        lista = nova;
        Ficheiro.guardar(FICHEIRO, lista);
        return true;
    }

// Para impedir apagar um proprietário que ainda tem veículos
    public boolean temProprietario(String documento) {
        for (Veiculo v : lista) {
            if (v.getProprietario().getDocumentoID().equalsIgnoreCase(documento)) {
                return true;
            }
        }
        return false;
    }

// Liga cada veículo ao proprietário actual (ver nota abaixo)
    public void relinkar(ProprietarioController pc) {
        for (Veiculo v : lista) {
            Proprietario atual = pc.buscarPorDocumento(v.getProprietario().getDocumentoID());
            if (atual != null) {
                v.setProprietario(atual);
            }
        }
    }
}
