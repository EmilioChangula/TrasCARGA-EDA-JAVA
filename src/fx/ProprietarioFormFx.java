package fx;

import controller.Dados;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Proprietario;

public class ProprietarioFormFx implements Initializable {

    @FXML private Label lblTitulo, lblMsg;
    @FXML private TextField txtNome, txtContacto, txtDocumento;
    @FXML private Button btnGuardar, btnCancelar;

    private Proprietario emEdicao; // null = a adicionar

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    public void editar(Proprietario p) {
    emEdicao = p;
    txtNome.setText(p.getNome());
    txtContacto.setText(p.getContacto());
    txtDocumento.setText(p.getDocumentoID());
    txtDocumento.setDisable(true); // a chave não se altera
    lblTitulo.setText("Editar Proprietário");
    btnGuardar.setText("Actualizar");
    btnCancelar.setText("Cancelar");
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

        if (emEdicao == null) {
            if (Dados.proprietarios.cadastrar(nome, contacto, doc)) {
                limparCampos();
                lblMsg.setText("Proprietário cadastrado.");
            } else {
                lblMsg.setText("Já existe um proprietário com esse documento.");
            }
        } else {
            if (Dados.proprietarios.actualizar(emEdicao.getDocumentoID(), nome, contacto)) {
                PrincipalFx.mostrarProprietarioVer(); // volta à lista
            } else {
                lblMsg.setText("Falhou a actualização.");
            }
        }
    }

    @FXML
    private void cancelar() {
        if (emEdicao != null) {
            PrincipalFx.mostrarProprietarioVer();
        } else {
            limparCampos();
            lblMsg.setText("");
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtContacto.clear();
        txtDocumento.clear();
    }
}