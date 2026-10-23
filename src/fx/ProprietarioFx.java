/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fx;

import controller.Dados;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Proprietario;

public class ProprietarioFx implements Initializable {

    @FXML private TextField txtNome, txtContacto, txtDocumento;
    @FXML private Label lblMsg;
    @FXML private Button btnGuardar, btnActualizar, btnEliminar;
    @FXML private TableView<Proprietario> tabela;
    @FXML private TableColumn<Proprietario, String> colNome, colContacto, colDocumento;

    // Lista só para MOSTRAR na tabela; os dados vivem na DequeArray do controller
    private final ObservableList<Proprietario> dados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documentoID"));
        tabela.setItems(dados);

        // Ao clicar numa linha, o formulário enche-se
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, p) -> {
            if (p != null) {
                txtNome.setText(p.getNome());
                txtContacto.setText(p.getContacto());
                txtDocumento.setText(p.getDocumentoID());
                modoEdicao(true);
            }
        });

        carregar();
        modoEdicao(false);
    }

    @FXML
    private void guardar() {
        String nome = txtNome.getText().trim();
        String contacto = txtContacto.getText().trim();
        String doc = txtDocumento.getText().trim();

        if (nome.isEmpty() || contacto.isEmpty() || doc.isEmpty()) {
            lblMsg.setText("Preenche todos os campos.");
            return;
        }
        if (Dados.proprietarios.cadastrar(nome, contacto, doc)) {
            carregar();
            limpar();
            lblMsg.setText("Proprietário cadastrado.");
        } else {
            lblMsg.setText("Já existe um proprietário com esse documento.");
        }
    }

    @FXML
    private void actualizar() {
        Proprietario sel = tabela.getSelectionModel().getSelectedItem();
        if (sel == null) {
            lblMsg.setText("Selecciona um proprietário na tabela.");
            return;
        }
        String nome = txtNome.getText().trim();
        String contacto = txtContacto.getText().trim();
        if (nome.isEmpty() || contacto.isEmpty()) {
            lblMsg.setText("Preenche nome e contacto.");
            return;
        }
        if (Dados.proprietarios.actualizar(sel.getDocumentoID(), nome, contacto)) {
            carregar();
            limpar();
            lblMsg.setText("Proprietário actualizado.");
        } else {
            lblMsg.setText("Falhou a actualização.");
        }
    }

    @FXML
    private void eliminar() {
        Proprietario sel = tabela.getSelectionModel().getSelectedItem();
        if (sel == null) {
            lblMsg.setText("Selecciona um proprietário na tabela.");
            return;
        }
        if (Dados.veiculos.temProprietario(sel.getDocumentoID())) {
            lblMsg.setText("Não pode apagar: este proprietário tem veículos.");
            return;
        }
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,
                "Eliminar " + sel.getNome() + "?", ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText(null);
        Optional<ButtonType> resp = alerta.showAndWait();
        if (resp.isPresent() && resp.get() == ButtonType.YES) {
            Dados.proprietarios.remover(sel.getDocumentoID());
            carregar();
            limpar();
            lblMsg.setText("Proprietário eliminado.");
        }
    }

    @FXML
    private void limpar() {
        txtNome.clear();
        txtContacto.clear();
        txtDocumento.clear();
        tabela.getSelectionModel().clearSelection();
        modoEdicao(false);
    }

    // Copia a DequeArray para a lista da tabela
    private void carregar() {
        dados.clear();
        for (Proprietario p : Dados.proprietarios.listar()) {
            dados.add(p);
        }
    }

    // Em edição: o documento (chave) fica bloqueado e só Actualizar/Eliminar estão activos
    private void modoEdicao(boolean edicao) {
        txtDocumento.setDisable(edicao);
        btnGuardar.setDisable(edicao);
        btnActualizar.setDisable(!edicao);
        btnEliminar.setDisable(!edicao);
    }
}