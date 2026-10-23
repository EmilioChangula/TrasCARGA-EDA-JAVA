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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.Proprietario;
import model.Veiculo;

public class VeiculoFormFx implements Initializable {

    @FXML private Label lblTitulo, lblMsg;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private ComboBox<Proprietario> cmbProprietario;
    @FXML private TextField txtChassi, txtMatricula, txtMarca, txtModelo, txtCapacidade;
    @FXML private Button btnGuardar, btnCancelar;

    private Veiculo emEdicao; // null = a adicionar

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTipo.getItems().addAll("Camião", "Carrinha");

        // Mostra "Nome (documento)" no ComboBox
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
    }

    // Chamado pela lista (Ver) quando se clica em "Editar"
    public void editar(Veiculo v) {
        emEdicao = v;
        cmbTipo.setValue(v.getTipo());
        txtChassi.setText(v.getNrChassi());
        txtMatricula.setText(v.getMatricula());
        cmbProprietario.setValue(v.getProprietario());
        txtMarca.setText(v.getMarca());
        txtModelo.setText(v.getModelo());
        txtCapacidade.setText(String.valueOf(v.getCapacidade()));

        // Tipo, matrícula (chave) e proprietário não se alteram
        cmbTipo.setDisable(true);
        txtMatricula.setDisable(true);
        cmbProprietario.setDisable(true);

        lblTitulo.setText("Editar Veículo");
        btnGuardar.setText("Actualizar");
        btnCancelar.setText("Cancelar");
    }

    @FXML
    private void guardar() {
        String tipo = cmbTipo.getValue();
        Proprietario dono = cmbProprietario.getValue();
        String chassi = txtChassi.getText().trim();
        String matricula = txtMatricula.getText().trim();
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();

        if (tipo == null || dono == null || chassi.isEmpty() || matricula.isEmpty()
                || marca.isEmpty() || modelo.isEmpty() || txtCapacidade.getText().trim().isEmpty()) {
            lblMsg.setText(Dados.proprietarios.listar().isEmpty()
                    ? "Cadastra primeiro um proprietário."
                    : "Preenche todos os campos.");
            return;
        }

        double capacidade;
        try {
            capacidade = Double.parseDouble(txtCapacidade.getText().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            lblMsg.setText("Capacidade inválida.");
            return;
        }
        if (capacidade <= 0) {
            lblMsg.setText("A capacidade tem de ser maior que 0.");
            return;
        }

        if (emEdicao == null) {
            boolean ok = tipo.equals("Camião")
                    ? Dados.veiculos.cadastrarCamiao(chassi, matricula, marca, modelo, capacidade, dono)
                    : Dados.veiculos.cadastrarCarrinha(chassi, matricula, marca, modelo, capacidade, dono);
            if (ok) {
                limparCampos();
                lblMsg.setText("Veículo cadastrado.");
            } else {
                lblMsg.setText("Não foi possível: matrícula ou chassi já existem.");
            }
        } else {
            if (Dados.veiculos.actualizar(chassi, emEdicao.getMatricula(), marca, modelo, capacidade)) {
                PrincipalFx.mostrarVeiculoVer(); // volta à lista
            } else {
                lblMsg.setText("Falhou: chassi já pertence a outro veículo.");
            }
        }
    }

    @FXML
    private void cancelar() {
        if (emEdicao != null) {
            PrincipalFx.mostrarVeiculoVer();
        } else {
            limparCampos();
            lblMsg.setText("");
        }
    }

    private void limparCampos() {
        cmbTipo.setValue(null);
        cmbProprietario.setValue(null);
        txtChassi.clear();
        txtMatricula.clear();
        txtMarca.clear();
        txtModelo.clear();
        txtCapacidade.clear();
    }
}