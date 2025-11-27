package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.model.Procedimento;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class CadastroProcedimentoController {

    @FXML private TextField txtNome;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtPreco;
    @FXML private TextField txtDuracao;

    private Cadastrador cadastrador;

    // Recebe as dependências e carrega a lista de dentistas no ComboBox
    public void setDependencies(Cadastrador cadastrador) {
        this.cadastrador = cadastrador;
    }

    @FXML
    private void acaoSalvar() {
        try {
            String nome = txtNome.getText();
            String descricao = txtDescricao.getText();
            String precoStr = txtPreco.getText();
            String duracaoStr = txtDuracao.getText();

            // Validações
            if (nome.isEmpty() || precoStr.isEmpty() || duracaoStr.isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos.");
                return;
            }

            double preco = Double.parseDouble(precoStr.replace(",", "."));
            int duracao = Integer.parseInt(duracaoStr);

            // Cria e Salva
            Procedimento novoProc;
            if(descricao == null)
                novoProc = new Procedimento(nome, preco, duracao);
            else
                novoProc = new Procedimento(nome, descricao, preco, duracao);

            cadastrador.cadastrar(novoProc);

            mostrarAlerta("Sucesso", "Procedimento cadastrado com sucesso!");
            limparCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato", "Preço e Duração devem ser números válidos.");
        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao salvar: " + e.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtDescricao.clear();
        txtPreco.clear();
        txtDuracao.clear();
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}