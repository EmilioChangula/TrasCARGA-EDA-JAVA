/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fx;

import controller.Dados;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Cliente;

public class ClienteFormFx implements Initializable {

    @FXML private Label lblTitulo, lblMsg;
    @FXML private TextField txtDocumento, txtNome, txtContacto, txtLocalizacao;
    @FXML private Button btnGuardar, btnCancelar;

    private Cliente emEdicao; // null = a adicionar

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    // Chamado pela lista (Ver) quando se clica em "Editar"
    public void editar(Cliente c) {
    emEdicao = c;
    txtDocumento.setText(c.getDocumentoCli());
    txtNome.setText(c.getNome());
    txtContacto.setText(c.getContacto());
    txtLocalizacao.setText(c.getLocalizacao());
    txtDocumento.setDisable(true); // a chave não se altera
    lblTitulo.setText("Editar Cliente");
    btnGuardar.setText("Actualizar");
    btnCancelar.setText("Cancelar");
}

    @FXML
    private void guardar() {
        String doc = txtDocumento.getText().trim();
        String nome = txtNome.getText().trim();
        String contacto = txtContacto.getText().trim();
        String local = txtLocalizacao.getText().trim();

        if (doc.isEmpty() || nome.isEmpty() || contacto.isEmpty() || local.isEmpty()) {
            lblMsg.setText("Preenche todos os campos.");
            return;
        }

        if (emEdicao == null) {
            if (Dados.clientes.cadastrar(doc, nome, contacto, local)) {
                limparCampos();
                lblMsg.setText("Cliente cadastrado.");
            } else {
                lblMsg.setText("Já existe um cliente com esse documento.");
            }
        } else {
            if (Dados.clientes.actualizar(emEdicao.getDocumentoCli(), nome, contacto, local)) {
                PrincipalFx.mostrarClienteVer(); // volta à lista
            } else {
                lblMsg.setText("Falhou a actualização.");
            }
        }
    }

    @FXML
    private void cancelar() {
        if (emEdicao != null) {
            PrincipalFx.mostrarClienteVer();
        } else {
            limparCampos();
            lblMsg.setText("");
        }
    }

    private void limparCampos() {
        txtDocumento.clear();
        txtNome.clear();
        txtContacto.clear();
        txtLocalizacao.clear();
    }
}