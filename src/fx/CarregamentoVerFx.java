package fx;

import controller.Dados;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Carregamento;
import tad.DequeArray;

public class CarregamentoVerFx implements Initializable {

    @FXML private TextField txtPesquisa;
    @FXML private Label lblMsg;
    @FXML private TableView<Carregamento> tabela;
    @FXML private TableColumn<Carregamento, String> colData, colCliente, colVeiculo,
            colProprietario, colQuantidade, colOrigem, colDestino, colValor;

    // Só para MOSTRAR; os dados vivem na DequeArray do controller
    private final ObservableList<Carregamento> dados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colData.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getData().format(Formato.DATA)));
        colCliente.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getNomeCliente()));
        colVeiculo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getMatricula()));
        colProprietario.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getProprietario().getNome()));
        colQuantidade.setCellValueFactory(cd -> new SimpleStringProperty(String.valueOf(cd.getValue().getQuantidade())));
        colOrigem.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getOrigem()));
        colDestino.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getDestino()));
        colValor.setCellValueFactory(cd -> new SimpleStringProperty(Formato.mzn(cd.getValue().getValor())));

        colData.prefWidthProperty().bind(tabela.widthProperty().multiply(0.09));
        colCliente.prefWidthProperty().bind(tabela.widthProperty().multiply(0.16));
        colVeiculo.prefWidthProperty().bind(tabela.widthProperty().multiply(0.11));
        colProprietario.prefWidthProperty().bind(tabela.widthProperty().multiply(0.15));
        colQuantidade.prefWidthProperty().bind(tabela.widthProperty().multiply(0.08));
        colOrigem.prefWidthProperty().bind(tabela.widthProperty().multiply(0.13));
        colDestino.prefWidthProperty().bind(tabela.widthProperty().multiply(0.13));
        colValor.prefWidthProperty().bind(tabela.widthProperty().multiply(0.13));

        tabela.setItems(dados);
        txtPesquisa.textProperty().addListener((obs, antigo, novo) -> carregar());
        carregar();
    }

    @FXML
    private void pesquisar() {
        carregar();
    }

    // Percorre a DequeArray do fim para o início: mais recente primeiro
    private void carregar() {
        String filtro = txtPesquisa.getText() == null ? "" : txtPesquisa.getText().trim().toLowerCase();
        DequeArray<Carregamento> lista = Dados.carregamentos.listarTodos();

        dados.clear();
        double total = 0;
        for (int i = lista.size() - 1; i >= 0; i--) {
            Carregamento c = lista.get(i);
            if (filtro.isEmpty()
                    || c.getNomeCliente().toLowerCase().contains(filtro)
                    || c.getMatricula().toLowerCase().contains(filtro)) {
                dados.add(c);
                total += c.getValor();
            }
        }
        lblMsg.setText(dados.size() + " carregamento(s)  |  Total: " + Formato.mzn(total));
    }
}