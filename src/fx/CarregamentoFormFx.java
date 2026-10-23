/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fx;

import controller.Dados;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.Cliente;
import model.Veiculo;

public class CarregamentoFormFx implements Initializable {

    @FXML private Label lblMsg;
    @FXML private ComboBox<Cliente> cmbCliente;
    @FXML private ComboBox<Veiculo> cmbVeiculo;
    @FXML private TextField txtProprietario, txtCapacidade, txtQuantidade, txtOrigem, txtDestino, txtValor;
    @FXML private DatePicker dpData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbCliente.setConverter(new StringConverter<Cliente>() {
            @Override
            public String toString(Cliente c) {
                return c == null ? "" : c.getNome() + " (" + c.getDocumentoCli() + ")";
            }

            @Override
            public Cliente fromString(String s) {
                return null;
            }
        });

        cmbVeiculo.setConverter(new StringConverter<Veiculo>() {
            @Override
            public String toString(Veiculo v) {
                return v == null ? "" : v.getMatricula() + " - " + v.getTipo()
                        + " (" + v.getCapacidade() + " t)";
            }

            @Override
            public Veiculo fromString(String s) {
                return null;
            }
        });

        for (Cliente c : Dados.clientes.listar()) {
            cmbCliente.getItems().add(c);
        }
        for (Veiculo v : Dados.veiculos.listarTodos()) {
            cmbVeiculo.getItems().add(v);
        }

        // Ao escolher o veículo, mostra o proprietário e a capacidade
        cmbVeiculo.valueProperty().addListener((obs, antigo, v) -> {
            if (v == null) {
                txtProprietario.clear();
                txtCapacidade.clear();
            } else {
                txtProprietario.setText(v.getProprietario().getNome());
                txtCapacidade.setText(v.getCapacidade() + " t");
            }
        });

        dpData.setValue(LocalDate.now());
    }

    @FXML
    private void guardar() {
        Cliente cliente = cmbCliente.getValue();
        Veiculo veiculo = cmbVeiculo.getValue();
        LocalDate data = dpData.getValue();
        String origem = txtOrigem.getText().trim();
        String destino = txtDestino.getText().trim();

        if (cliente == null || veiculo == null || data == null || origem.isEmpty()
                || destino.isEmpty() || txtQuantidade.getText().trim().isEmpty()
                || txtValor.getText().trim().isEmpty()) {
            boolean semBase = Dados.clientes.listar().isEmpty() || Dados.veiculos.listarTodos().isEmpty();
            erro(semBase ? "Cadastra primeiro pelo menos um cliente e um veículo."
                         : "Preenche todos os campos.");
            return;
        }

        double quantidade, valor;
        try {
            quantidade = Double.parseDouble(txtQuantidade.getText().trim().replace(',', '.'));
            valor = Double.parseDouble(txtValor.getText().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            erro("Quantidade ou valor inválido.");
            return;
        }
        if (quantidade <= 0) {
            erro("A quantidade tem de ser maior que 0.");
            return;
        }
        if (valor < 0) {
            erro("O valor não pode ser negativo.");
            return;
        }

        // Item 9: a quantidade não pode ultrapassar a capacidade do veículo
        if (!Dados.carregamentos.validar(veiculo, quantidade)) {
            erro("Quantidade (" + quantidade + " t) ultrapassa a capacidade do veículo ("
                    + veiculo.getCapacidade() + " t).");
            return;
        }

        if (Dados.carregamentos.registar(cliente, veiculo, quantidade, origem, destino, data, valor)) {
            String dono = veiculo.getProprietario().getNome();
            limparCampos();
            ok("Carregamento registado. Proprietário do veículo: " + dono + ".");
        } else {
            erro("Não foi possível registar o carregamento.");
        }
    }

    @FXML
    private void limpar() {
        limparCampos();
        lblMsg.setText("");
    }

    private void limparCampos() {
        cmbCliente.setValue(null);
        cmbVeiculo.setValue(null);
        txtQuantidade.clear();
        txtOrigem.clear();
        txtDestino.clear();
        txtValor.clear();
        dpData.setValue(LocalDate.now());
    }

    private void erro(String texto) {
        lblMsg.setStyle("-fx-text-fill: #c0392b;");
        lblMsg.setText(texto);
    }

    private void ok(String texto) {
        lblMsg.setStyle("-fx-text-fill: #2e7d32;");
        lblMsg.setText(texto);
    }
}