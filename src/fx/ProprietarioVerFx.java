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
import model.Proprietario;

public class ProprietarioVerFx implements Initializable {

    @FXML private TextField txtPesquisa;
    @FXML private Label lblMsg;
    @FXML private TableView<Proprietario> tabela;
    @FXML private TableColumn<Proprietario, String> colNome, colContacto, colDocumento;
    @FXML private TableColumn<Proprietario, Void> colAccao;

    // Só para MOSTRAR; os dados vivem na DequeArray do controller
    private final ObservableList<Proprietario> dados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documentoID"));

        // Larguras proporcionais
        colNome.prefWidthProperty().bind(tabela.widthProperty().multiply(0.27));
        colContacto.prefWidthProperty().bind(tabela.widthProperty().multiply(0.20));
        colDocumento.prefWidthProperty().bind(tabela.widthProperty().multiply(0.20));
        colAccao.prefWidthProperty().bind(tabela.widthProperty().multiply(0.27));

        // Botões Editar / Eliminar em cada linha
        colAccao.setCellFactory(col -> new TableCell<Proprietario, Void>() {
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
        for (Proprietario p : Dados.proprietarios.listar()) {
            if (filtro.isEmpty() || p.getNome().toLowerCase().contains(filtro)) {
                dados.add(p);
            }
        }
    }

    private void editar(Proprietario p) {
        PrincipalFx.editarProprietario(p);
    }

    private void eliminar(Proprietario p) {
        if (Dados.veiculos.temProprietario(p.getDocumentoID())) {
            lblMsg.setText("Não pode apagar: " + p.getNome() + " tem veículos.");
            return;
        }
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,
                "Eliminar " + p.getNome() + "?", ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText(null);
        Optional<ButtonType> resp = alerta.showAndWait();
        if (resp.isPresent() && resp.get() == ButtonType.YES) {
            Dados.proprietarios.remover(p.getDocumentoID());
            carregar();
            lblMsg.setText("Proprietário eliminado.");
        }
    }
}
