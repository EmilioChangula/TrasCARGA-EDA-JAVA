package fx;

import controller.Dados;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Carregamento;

public class DashboardFx implements Initializable {

    @FXML private Label lblProprietarios, lblVeiculos, lblClientes, lblCarregamentos, lblTotal, lblUltimo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblProprietarios.setText(String.valueOf(Dados.proprietarios.listar().size()));
        lblVeiculos.setText(String.valueOf(Dados.veiculos.listarTodos().size()));
        lblClientes.setText(String.valueOf(Dados.clientes.listar().size()));
        lblCarregamentos.setText(String.valueOf(Dados.carregamentos.listarTodos().size()));

        double total = 0;
        for (Carregamento c : Dados.carregamentos.listarTodos()) {
            total += c.getValor();
        }
        lblTotal.setText(Formato.mzn(total));

        // Usa o peekLast() do TAD: espreita o último elemento sem o remover
        Carregamento ult = Dados.carregamentos.ultimo();
        if (ult != null) {
            lblUltimo.setText(ult.getNomeCliente() + " | " + ult.getMatricula() + " | "
                    + ult.getOrigem() + " → " + ult.getDestino() + " | " + Formato.mzn(ult.getValor()));
        }
    }
}