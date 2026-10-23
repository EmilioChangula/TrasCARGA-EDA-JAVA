package fx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Cliente;
import model.Proprietario;
import model.Veiculo;

public class PrincipalFx implements Initializable {

    @FXML private VBox menuLateral, subProprietario, subVeiculo, subCliente, subCarregamento;
    @FXML private StackPane areaConteudo;
    @FXML private Button btnDashboard,
            btnPropAdicionar, btnPropVer,
            btnVeicAdicionar, btnVeicVer,
            btnCliAdicionar, btnCliVer,
            btnCarrAdicionar, btnCarrVer, btnCarrConsultas;

    // Permite que uma tela peça a outra (ex.: "Editar" na lista abre o formulário)
    private static PrincipalFx instancia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instancia = this;
        abrir("/view/Dashboard.fxml", btnDashboard);
    }

    // ---------- Menu ----------
    @FXML private void abrirDashboard() { abrir("/view/Dashboard.fxml", btnDashboard); }

    @FXML private void alternarProprietario() { alternar(subProprietario); }
    @FXML private void abrirProprietarioAdicionar() { abrir("/view/ProprietarioForm.fxml", btnPropAdicionar); }
    @FXML private void abrirProprietarioVer() { abrir("/view/ProprietarioVer.fxml", btnPropVer); }

    @FXML private void alternarVeiculo() { alternar(subVeiculo); }
    @FXML private void abrirVeiculoAdicionar() { abrir("/view/VeiculoForm.fxml", btnVeicAdicionar); }
    @FXML private void abrirVeiculoVer() { abrir("/view/VeiculoVer.fxml", btnVeicVer); }

    @FXML private void alternarCliente() { alternar(subCliente); }
    @FXML private void abrirClienteAdicionar() { abrir("/view/ClienteForm.fxml", btnCliAdicionar); }
    @FXML private void abrirClienteVer() { abrir("/view/ClienteVer.fxml", btnCliVer); }

    @FXML private void alternarCarregamento() { alternar(subCarregamento); }
    @FXML private void abrirCarregamentoAdicionar() { abrir("/view/CarregamentoForm.fxml", btnCarrAdicionar); }
    @FXML private void abrirCarregamentoVer() { abrir("/view/CarregamentoVer.fxml", btnCarrVer); }
    @FXML private void abrirConsultas() { abrir("/view/Consultas.fxml", btnCarrConsultas); }

    @FXML private void sair() { Platform.exit(); }

    // ---------- Navegação entre telas ----------
    public static void mostrarProprietarioVer() {
        instancia.abrir("/view/ProprietarioVer.fxml", instancia.btnPropVer);
    }

    public static void editarProprietario(Proprietario p) {
        ProprietarioFormFx form = instancia.abrir("/view/ProprietarioForm.fxml", instancia.btnPropAdicionar);
        if (form != null) form.editar(p);
    }

    public static void mostrarVeiculoVer() {
        instancia.abrir("/view/VeiculoVer.fxml", instancia.btnVeicVer);
    }

    public static void editarVeiculo(Veiculo v) {
        VeiculoFormFx form = instancia.abrir("/view/VeiculoForm.fxml", instancia.btnVeicAdicionar);
        if (form != null) form.editar(v);
    }

    public static void mostrarClienteVer() {
        instancia.abrir("/view/ClienteVer.fxml", instancia.btnCliVer);
    }

    public static void editarCliente(Cliente c) {
        ClienteFormFx form = instancia.abrir("/view/ClienteForm.fxml", instancia.btnCliAdicionar);
        if (form != null) form.editar(c);
    }

    // ---------- Auxiliares ----------
    private void alternar(VBox sub) {
        boolean mostrar = !sub.isVisible();
        sub.setVisible(mostrar);
        sub.setManaged(mostrar);
    }

    // Carrega a tela na área da direita, marca o botão activo e devolve o controller da tela
    private <T> T abrir(String fxml, Button botao) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Node tela = loader.load();
            areaConteudo.getChildren().setAll(tela);
            limparActivo(menuLateral);
            botao.getStyleClass().add("ativo");
            return loader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void limparActivo(Node no) {
        no.getStyleClass().remove("ativo");
        if (no instanceof Parent) {
            for (Node filho : ((Parent) no).getChildrenUnmodifiable()) {
                limparActivo(filho);
            }
        }
    }
}