package clinica.view.UIController;

import java.util.ArrayList;
import java.util.List;

import clinica.controller.Cadastrador;
import clinica.model.Dentista;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CadastroDentistaController {

    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtEndereco;
    @FXML private TextField txtEspecialidade;

    @FXML private VBox containerGradeHorarios; 

    private Cadastrador cadastrador;
    
    private List<ToggleButton> listaBotoesHorarios = new ArrayList<>();

    public void setDependencies(Cadastrador cadastrador) {
        this.cadastrador = cadastrador;
        inicializarGradeHorarios();
    }

    private void inicializarGradeHorarios() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);

        String[] diasSemana = {"Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"};
        int horaInicio = 8;
        int horaFim = 18;

        for (int i = 0; i < diasSemana.length; i++) {
            Label lblDia = new Label(diasSemana[i]);
            lblDia.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
            grid.add(lblDia, i + 1, 0);
        }

        for (int h = horaInicio; h <= horaFim; h++) {
            String horaStr = String.format("%02d:00", h);
            Label lblHora = new Label(horaStr);
            lblHora.setStyle("-fx-font-size: 11px;");
            grid.add(lblHora, 0, h - horaInicio + 1);

            for (int d = 0; d < diasSemana.length; d++) {
                ToggleButton btn = new ToggleButton();
                btn.setPrefSize(50, 30);
                
                String idData = diasSemana[d] + ";" + horaStr;
                btn.setUserData(idData);

                btn.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal) {
                        btn.setStyle("-fx-base: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;"); // Verde
                        btn.setText("OK");
                    } else {
                        btn.setStyle("-fx-base: #e0e0e0; -fx-text-fill: black;"); // Cinza claro
                        btn.setText("");
                    }
                });
                
                btn.setStyle("-fx-base: #e0e0e0;");

                listaBotoesHorarios.add(btn);
                grid.add(btn, d + 1, h - horaInicio + 1);
            }
        }

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(280); 
        scroll.setStyle("-fx-background-color: transparent;");

        if (containerGradeHorarios != null) {
            containerGradeHorarios.getChildren().clear();
            containerGradeHorarios.getChildren().add(scroll);
        }
    }

    @FXML
    private void acaoSalvar() {
        if (cadastrador == null) {
            mostrarAlerta("Erro Crítico", "O serviço Cadastrador não foi injetado!");
            return;
        }

        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();
        String endereco = txtEndereco.getText();
        String especialidade = txtEspecialidade.getText();

        if (nome.isEmpty() || cpf.isEmpty()) {
            mostrarAlerta("Atenção", "Nome e CPF são obrigatórios.");
            return;
        }

        List<String> horariosEscolhidos = new ArrayList<>();
        
        for (ToggleButton btn : listaBotoesHorarios) {
            if (btn.isSelected()) {
                horariosEscolhidos.add((String) btn.getUserData());
            }
        }

        if(!cpf.matches("\\d*\\d*\\d*.\\d*\\d*\\d*.\\d*\\d*\\d*-\\d*\\d*"))
        {
            mostrarAlerta("Atenção", "CPF Inválido");
            return;
        }

        if(!telefone.matches("\\d*"))
        {
            mostrarAlerta("Atenção", "Número Inválido");
            return;
        }

        if((!email.endsWith(".com") && !email.endsWith(".br") && !email.endsWith(".org")) || !email.contains("@"))
        {
            mostrarAlerta("Atenção", "Email Inválido");
            return;
        }

        if (horariosEscolhidos.isEmpty()) {
            mostrarAlerta("Atenção", "Selecione ao menos um horário de atendimento na tabela.");
            return;
        }

        try {
            // 3. Criação do Objeto
            // Usa o novo construtor simplificado (sem horários fixos)
            Dentista novoDentista = new Dentista(nome, cpf, telefone, email, endereco, especialidade);

            // Injeta a lista de horários processada
            novoDentista.configurarHorarios(horariosEscolhidos);

            // 4. Persistência
            cadastrador.cadastrar(novoDentista);

            mostrarAlerta("Sucesso", "Dentista " + nome + " cadastrado com sucesso!");
            limparCampos();

        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao cadastrar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtEmail.clear();
        txtEndereco.clear();
        txtEspecialidade.clear();
        
        // Reseta todos os botões da grade para cinza/desmarcado
        for(ToggleButton btn : listaBotoesHorarios) {
            btn.setSelected(false);
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}