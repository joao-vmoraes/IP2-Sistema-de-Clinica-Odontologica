package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.model.Dentista;
import clinica.model.Procedimento;
import clinica.repository.DentistaRepositorio;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class CadastroProcedimentoController {

    @FXML private TextField txtNome;
    @FXML private TextField txtPreco;
    @FXML private TextField txtDuracao;
    @FXML private ComboBox<Dentista> cbDentista;

    private Cadastrador cadastrador;
    private DentistaRepositorio dentistaRepositorio;

    // Recebe as dependências e carrega a lista de dentistas no ComboBox
    public void setDependencies(Cadastrador cadastrador, DentistaRepositorio dentistaRepo) {
        this.cadastrador = cadastrador;
        this.dentistaRepositorio = dentistaRepo;
        carregarDentistas();
    }

    private void carregarDentistas() {
        if (dentistaRepositorio != null) {
            cbDentista.setItems(FXCollections.observableArrayList(dentistaRepositorio.listarTodos()));

            // Configura como o Dentista aparece na lista (Mostra Nome - CRO/CPF)
            cbDentista.setConverter(new StringConverter<Dentista>() {
                @Override
                public String toString(Dentista d) {
                    return d == null ? "" : d.getNome() + " (CPF: " + d.getCpf() + ")";
                }

                @Override
                public Dentista fromString(String string) {
                    return null; // Não usado na seleção
                }
            });
        }
    }

    @FXML
    private void acaoSalvar() {
        try {
            String nome = txtNome.getText();
            String precoStr = txtPreco.getText();
            String duracaoStr = txtDuracao.getText();
            Dentista dentistaSelecionado = cbDentista.getValue();

            // Validações
            if (nome.isEmpty() || precoStr.isEmpty() || duracaoStr.isEmpty() || dentistaSelecionado == null) {
                mostrarAlerta("Erro", "Preencha todos os campos e selecione um dentista.");
                return;
            }

            double preco = Double.parseDouble(precoStr.replace(",", "."));
            int duracao = Integer.parseInt(duracaoStr);

            // Cria e Salva
            Procedimento novoProc = new Procedimento(nome, preco, duracao, dentistaSelecionado);
            cadastrador.adicionarProcedimento(novoProc); // Método renomeado conforme seu Cadastrador atual

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
        txtPreco.clear();
        txtDuracao.clear();
        cbDentista.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}