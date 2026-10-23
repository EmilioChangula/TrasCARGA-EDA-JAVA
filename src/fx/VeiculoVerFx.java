/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fx;

import controller.Dados;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Veiculo;
import tad.DequeArray;

public class VeiculoVerFx implements Initializable {

    @FXML private ComboBox<String> cmbFiltro;
    @FXML private TextField txtPesquisa;
    @FXML private Label lblMsg;
    @FXML private TableView<Veiculo> tabela;
    @FXML private TableColumn<Veiculo, String> colTipo, colChassi, colMatricula, colMarca, colModelo, colProprietario;
    @FXML private TableColumn<Veiculo, Double> colCapacidade;
    @FXML private TableColumn<Veiculo, Void> colAccao;

    // Só para MOSTRAR; os dados vivem na DequeArray do controller
    private final ObservableList<Veiculo> dados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colChassi.setCellValueFactory(new PropertyValueFactory<>("nrChassi"));
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));
        colProprietario.setCellValueFactory(
                cd -> new SimpleStringProperty(cd.getValue().getProprietario().getNome()));

        // Sem ordenação por clique: a ordem que se vê é a da DequeArray
        tabela.getColumns().forEach(c -> c.setSortable(false));

        // Larguras proporcionais
        colTipo.prefWidthProperty().bind(tabela.widthProperty().multiply(0.08));
        colChassi.prefWidthProperty().bind(tabela.widthProperty().multiply(0.11));
        colMatricula.prefWidthProperty().bind(tabela.widthProperty().multiply(0.11));
        colMarca.prefWidthProperty().bind(tabela.widthProperty().multiply(0.10));
        colModelo.prefWidthProperty().bind(tabela.widthProperty().multiply(0.10));
        colCapacidade.prefWidthProperty().bind(tabela.widthProperty().multiply(0.10));
        colProprietario.prefWidthProperty().bind(tabela.widthProperty().multiply(0.14));
        colAccao.prefWidthProperty().bind(tabela.widthProperty().multiply(0.22));

        // Botões Editar / Eliminar em cada linha
        colAccao.setCellFactory(col -> new TableCell<Veiculo, Void>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final HBox caixa = new HBox(6, btnEditar, btnEliminar);

            {
                btnEditar.getStyleClass().add("btn-primary");
                btnEliminar.getStyleClass().add("btn-perigo");
                btnEditar.setStyle("-fx-font-size: 11px;");
                btnEliminar.setStyle("-fx-font-size: 11px;");
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

        cmbFiltro.getItems().addAll("Todos", "Camiões", "Carrinhas");
        cmbFiltro.setValue("Todos");
        cmbFiltro.valueProperty().addListener((obs, antigo, novo) -> {
            carregar();
            lblMsg.setText("Todos".equals(novo) ? "" : "Ordenado por capacidade crescente.");
        });
        txtPesquisa.textProperty().addListener((obs, antigo, novo) -> carregar());

        carregar();
    }

    @FXML
    private void pesquisar() {
        carregar();
    }

    // Copia a DequeArray certa para a tabela, aplicando a pesquisa
    private void carregar() {
        String filtro = txtPesquisa.getText() == null ? "" : txtPesquisa.getText().trim().toLowerCase();
        String tipo = cmbFiltro.getValue();

        DequeArray<Veiculo> origem;
        if ("Camiões".equals(tipo)) {
            origem = Dados.veiculos.listarCamioes();      // ordem crescente de capacidade
        } else if ("Carrinhas".equals(tipo)) {
            origem = Dados.veiculos.listarCarrinhas();    // ordem crescente de capacidade
        } else {
            origem = Dados.veiculos.listarTodos();
        }

        dados.clear();
        for (Veiculo v : origem) {
            if (filtro.isEmpty()
                    || v.getMatricula().toLowerCase().contains(filtro)
                    || v.getMarca().toLowerCase().contains(filtro)
                    || v.getModelo().toLowerCase().contains(filtro)) {
                dados.add(v);
            }
        }
    }

    private void editar(Veiculo v) {
        PrincipalFx.editarVeiculo(v);
    }

    private void eliminar(Veiculo v) {
        if (Dados.carregamentos.temVeiculo(v.getMatricula())) {
            lblMsg.setText("Não pode apagar: " + v.getMatricula() + " tem carregamentos.");
            return;
        }
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,
                "Eliminar o veículo " + v.getMatricula() + "?", ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText(null);
        Optional<ButtonType> resp = alerta.showAndWait();
        if (resp.isPresent() && resp.get() == ButtonType.YES) {
            Dados.veiculos.remover(v.getMatricula());
            carregar();
            lblMsg.setText("Veículo eliminado.");
        }
    }
}