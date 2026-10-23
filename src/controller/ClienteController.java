/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Cliente;
import persistencia.Ficheiro;
import tad.DequeArray;

/**
 *
 * @author user
 */
public class ClienteController {

    private static final String FICHEIRO = "clientes.dat";
    private DequeArray<Cliente> lista;

    public ClienteController() {
        this.lista = Ficheiro.carregar(FICHEIRO);
    }
    public DequeArray<Cliente> listar() {
    return lista;
}

    public boolean cadastrar(String documentoCli, String nome, String contacto, String localizacao) {
        if (buscarPorDocumento(documentoCli) != null) {
            return false; // documento já existe
        }
        lista.addLast(new Cliente(documentoCli, nome, contacto, localizacao));
        Ficheiro.guardar(FICHEIRO, lista);
        return true;
    }

    public Cliente buscarPorDocumento(String documentoCli) {
        for (Cliente c : lista) {
            if (c.getDocumentoCli() != null && c.getDocumentoCli().equalsIgnoreCase(documentoCli)) {
                return c;
            }
        }
        return null;
    }

    public boolean actualizar(String documentoCli, String novoNome, String novoContacto, String novaLocalizacao) {
        Cliente c = buscarPorDocumento(documentoCli);
        if (c == null) {
            return false;
        }
        c.setNome(novoNome);
        c.setContacto(novoContacto);
        c.setLocalizacao(novaLocalizacao);
        Ficheiro.guardar(FICHEIRO, lista);
        return true;
    }

    public boolean remover(String documentoCli) {
        Cliente alvo = buscarPorDocumento(documentoCli);
        if (alvo == null) {
            return false;
        }

        DequeArray<Cliente> nova = new DequeArray<>();
        for (Cliente c : lista) {
            if (c != alvo) {
                nova.addLast(c);
            }
        }
        lista = nova;
        Ficheiro.guardar(FICHEIRO, lista);
        return true;
    }

}
