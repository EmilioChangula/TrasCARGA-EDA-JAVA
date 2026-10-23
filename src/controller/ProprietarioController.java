/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Proprietario;
import persistencia.Ficheiro;
import tad.DequeArray;

/**
 *
 * @author user
 */
public class ProprietarioController {

    private static final String FICHEIRO = "proprietarios.dat";
    private DequeArray<Proprietario> lista;

    public ProprietarioController() {
        this.lista = Ficheiro.carregar(FICHEIRO);
    }

    public boolean cadastrar(String nome, String contacto, String documento) {
        if (buscarPorDocumento(documento) != null) {
            return false; // documento já existe
        }
        lista.addLast(new Proprietario(nome, contacto, documento));
        Ficheiro.guardar(FICHEIRO, lista);
        return true;
    }

    public Proprietario buscarPorDocumento(String documento) {
        for (Proprietario p : lista) {
            if (p.getDocumentoID().equalsIgnoreCase(documento)) {
                return p;
            }
        }
        return null;
    }

    public boolean actualizar(String documento, String novoNome, String novoContacto) {
        Proprietario p = buscarPorDocumento(documento);
        if (p == null) {
            return false;
        }
        p.setNome(novoNome);
        p.setContacto(novoContacto);
        Ficheiro.guardar(FICHEIRO, lista);
        return true;
    }

    public DequeArray<Proprietario> listar() {
        return lista;
    }

    public boolean remover(String documento) {
        Proprietario alvo = buscarPorDocumento(documento);
        if (alvo == null) {
            return false;
        }

        DequeArray<Proprietario> nova = new DequeArray<>();
        for (Proprietario p : lista) {
            if (p != alvo) {
                nova.addLast(p);
            }
        }
        lista = nova;
        Ficheiro.guardar(FICHEIRO, lista);
        return true;
    }

}
