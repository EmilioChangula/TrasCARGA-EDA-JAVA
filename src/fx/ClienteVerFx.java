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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Cliente;

public class ClienteVerFx implements Initializable {

    @FXML private TextField txtPesquisa;
    @FXML private Label lblMsg;
    @FXML private TableView<Cliente> tabela;
    @FXML private TableColumn<Cliente, String> colDocumento, colNome, colContacto, colLocalizacao;
    @FXML private TableColumn<Cliente, Void> colAccao;

    // Só para MOSTRAR; os dados vivem na DequeArray do controller
    private final ObservableList<Cliente> dados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documentoCli"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        colLocalizacao.setCellValueFactory(new PropertyValueFactory<>("localizacao"));

        // Larguras proporcionais
        colDocumento.prefWidthProperty().bind(tabela.widthProperty().multiply(0.18));
        colNome.prefWidthProperty().bind(tabela.widthProperty().multiply(0.22));
        colContacto.prefWidthProperty().bind(tabela.widthProperty().multiply(0.17));
        colLocalizacao.prefWidthProperty().bind(tabela.widthProperty().multiply(0.20));
        colAccao.prefWidthProperty().bind(tabela.widthProperty().multiply(0.20));

        // Botões Editar / Eliminar em cada linha
        colAccao.setCellFactory(col -> new TableCell<Cliente, Void>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final HBox caixa = new HBox(6, btnEditar, btnEliminar);

            {
                btnEditar.getStyleClass().add("btn-primary");
                btnEliminar.getStyleClass().add("btn-perigo");
                caixa.setAlignment(Pos.CENTER);
                btnEditar.setOnAction(e -> editar(getTableView().getItems().get(getIndex())));
                btnEliminar.setOnAction(e -> eliminar(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean vazio) {
                super.updateItem(item, vazio);
                setGraphic(vazio ? null : caixa);
            }
        });

        tabela.setItems(dados);
        txtPesquisa.textProperty().addListener((obs, antigo, novo) -> carregar());
        carregar();
    }

    @FXML
    private void pesquisar() {
        carregar();
    }

    // Copia a DequeArray para a lista da tabela, aplicando o filtro de pesquisa
    private void carregar() {
        String filtro = txtPesquisa.getText() == null ? "" : txtPesquisa.getText().trim().toLowerCase();
        dados.clear();
        for (Cliente c : Dados.clientes.listar()) {
            if (filtro.isEmpty() || c.getNome().toLowerCase().contains(filtro)) {
                dados.add(c);
            }
        }
    }

    private void editar(Cliente c) {
        PrincipalFx.editarCliente(c);
    }

    private void eliminar(Cliente c) {
        if (Dados.carregamentos.temCliente(c.getDocumentoCli())) {
            lblMsg.setText("Não pode apagar: " + c.getNome() + " tem carregamentos.");
            return;
        }
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,
                "Eliminar " + c.getNome() + "?", ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText(null);
        Optional<ButtonType> resp = alerta.showAndWait();
        if (resp.isPresent() && resp.get() == ButtonType.YES) {
            Dados.clientes.remover(c.getDocumentoCli());
            carregar();
            lblMsg.setText("Cliente eliminado.");
        }
    }
}
