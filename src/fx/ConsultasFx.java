/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fx;

import controller.Dados;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;
import model.Carregamento;
import model.Proprietario;
import model.Veiculo;
import tad.DequeArray;

public class ConsultasFx implements Initializable {

    @FXML private ComboBox<Proprietario> cmbProprietario;
    @FXML private ComboBox<Veiculo> cmbVeiculo;
    @FXML private TableView<Carregamento> tabelaProp;
    @FXML private TableColumn<Carregamento, String> colData, colCliente, colVeiculo,
            colQuantidade, colOrigem, colDestino, colValor;
    @FXML private Label lblResumoProp, lblTotalVeic, lblDetalheVeic;

    private final ObservableList<Carregamento> dadosProp = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ----- Transportes por proprietário (item 11) -----
        colData.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getData().format(Formato.DATA)));
        colCliente.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getNomeCliente()));
        colVeiculo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getMatricula()));
        colQuantidade.setCellValueFactory(cd -> new SimpleStringProperty(String.valueOf(cd.getValue().getQuantidade())));
        colOrigem.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getOrigem()));
        colDestino.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getDestino()));
        colValor.setCellValueFactory(cd -> new SimpleStringProperty(Formato.mzn(cd.getValue().getValor())));

        colData.prefWidthProperty().bind(tabelaProp.widthProperty().multiply(0.11));
        colCliente.prefWidthProperty().bind(tabelaProp.widthProperty().multiply(0.20));
        colVeiculo.prefWidthProperty().bind(tabelaProp.widthProperty().multiply(0.13));
        colQuantidade.prefWidthProperty().bind(tabelaProp.widthProperty().multiply(0.09));
        colOrigem.prefWidthProperty().bind(tabelaProp.widthProperty().multiply(0.15));
        colDestino.prefWidthProperty().bind(tabelaProp.widthProperty().multiply(0.15));
        colValor.prefWidthProperty().bind(tabelaProp.widthProperty().multiply(0.14));
        tabelaProp.setItems(dadosProp);

        cmbProprietario.setConverter(new StringConverter<Proprietario>() {
            @Override
            public String toString(Proprietario p) {
                return p == null ? "" : p.getNome() + " (" + p.getDocumentoID() + ")";
            }

            @Override
            public Proprietario fromString(String s) {
                return null;
            }
        });
        for (Proprietario p : Dados.proprietarios.listar()) {
            cmbProprietario.getItems().add(p);
        }
        cmbProprietario.valueProperty().addListener((obs, antigo, p) -> consultarProprietario(p));
        lblResumoProp.setText("Selecciona um proprietário.");

        // ----- Total por veículo (item 12) -----
        cmbVeiculo.setConverter(new StringConverter<Veiculo>() {
            @Override
            public String toString(Veiculo v) {
                return v == null ? "" : v.getMatricula() + " - " + v.getTipo()
                        + " (" + v.getProprietario().getNome() + ")";
            }

            @Override
            public Veiculo fromString(String s) {
                return null;
            }
        });
        for (Veiculo v : Dados.veiculos.listarTodos()) {
            cmbVeiculo.getItems().add(v);
        }
        cmbVeiculo.valueProperty().addListener((obs, antigo, v) -> consultarVeiculo(v));
    }

    private void consultarProprietario(Proprietario p) {
        dadosProp.clear();
        if (p == null) {
            lblResumoProp.setText("Selecciona um proprietário.");
            return;
        }
        DequeArray<Carregamento> resultado = Dados.carregamentos.listarPorProprietario(p.getDocumentoID());
        double total = 0;
        for (Carregamento c : resultado) {
            dadosProp.add(c);
            total += c.getValor();
        }
        lblResumoProp.setText(resultado.size() + " transporte(s) pelos veículos de "
                + p.getNome() + "  |  Total: " + Formato.mzn(total));
    }

    private void consultarVeiculo(Veiculo v) {
        if (v == null) {
            lblTotalVeic.setText("—");
            lblDetalheVeic.setText("Selecciona um veículo");
            return;
        }
        double total = Dados.carregamentos.totalPorVeiculo(v.getMatricula());

        int servicos = 0;
        for (Carregamento c : Dados.carregamentos.listarTodos()) {
            if (c.getVeiculo().getMatricula().equalsIgnoreCase(v.getMatricula())) {
                servicos++;
            }
        }
        lblTotalVeic.setText(Formato.mzn(total));
        lblDetalheVeic.setText("Total arrecadado pelo veículo " + v.getMatricula()
                + " (" + servicos + " serviço(s))");
    }
}