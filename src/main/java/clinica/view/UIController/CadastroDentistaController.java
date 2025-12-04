package clinica.view.UIController;

import java.time.LocalTime;
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

    // Container vazio no FXML onde injetaremos a tabela via código
    @FXML private VBox containerGradeHorarios; 

    private Cadastrador cadastrador;
    
    // Lista auxiliar para guardar referência de todos os botões criados
    // Isso permite varrer a lista depois para saber o que foi selecionado
    private List<ToggleButton> listaBotoesHorarios = new ArrayList<>();

    /**
     * Método chamado pela classe principal para injetar o serviço de cadastro.
     * Aproveitamos esse momento para inicializar a grade visual.
     */
    public void setDependencies(Cadastrador cadastrador) {
        this.cadastrador = cadastrador;
        inicializarGradeHorarios();
    }

    /**
     * Cria dinamicamente a tabela de horários (GridPane) e a coloca na tela.
     */
    private void inicializarGradeHorarios() {
        GridPane grid = new GridPane();
        grid.setHgap(5); // Espaçamento horizontal
        grid.setVgap(5); // Espaçamento vertical
        grid.setAlignment(Pos.CENTER);

        // Configuração da Grade
        String[] diasSemana = {"Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"};
        int horaInicio = 8; 
        int horaFim = 18; 

        // 1. Cabeçalho (Dias da Semana)
        for (int i = 0; i < diasSemana.length; i++) {
            Label lblDia = new Label(diasSemana[i]);
            lblDia.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
            grid.add(lblDia, i + 1, 0); // Coluna i+1 (pois a 0 é reservada para a hora)
        }

        // 2. Construção das Linhas (Horários)
        for (int h = horaInicio; h <= horaFim; h++) {
            // Label da Hora na primeira coluna
            String horaStr = String.format("%02d:00", h);
            Label lblHora = new Label(horaStr);
            lblHora.setStyle("-fx-font-size: 11px;");
            grid.add(lblHora, 0, h - horaInicio + 1);

            // 3. Criação dos Botões (Células da grade)
            for (int d = 0; d < diasSemana.length; d++) {
                ToggleButton btn = new ToggleButton();
                btn.setPrefSize(50, 30);
                
                // Armazenamos os dados essenciais dentro do próprio botão
                // Formato: "DIA_DA_SEMANA;HORARIO" (Ex: "Seg;08:00")
                String idData = diasSemana[d] + ";" + horaStr;
                btn.setUserData(idData);

                // Lógica visual: Muda de cor quando selecionado
                btn.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal) {
                        btn.setStyle("-fx-base: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;"); // Verde
                        btn.setText("OK");
                    } else {
                        btn.setStyle("-fx-base: #e0e0e0; -fx-text-fill: black;"); // Cinza claro
                        btn.setText("");
                    }
                });
                
                // Aplica estilo inicial (desmarcado)
                btn.setStyle("-fx-base: #e0e0e0;");

                // Adiciona na lista de controle e na grade visual
                listaBotoesHorarios.add(btn);
                grid.add(btn, d + 1, h - horaInicio + 1);
            }
        }

        // Envolve a grid num ScrollPane para garantir que caiba na tela
        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(280); 
        scroll.setStyle("-fx-background-color: transparent;");

        // Adiciona ao VBox do FXML (limpa antes para evitar duplicidade se chamado 2x)
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

        // 1. Coleta de dados básicos
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

        // 2. Coleta da Grade de Horários
        List<String> horariosEscolhidos = new ArrayList<>();
        
        for (ToggleButton btn : listaBotoesHorarios) {
            if (btn.isSelected()) {
                // Recupera a String "Seg;08:00" que guardamos no setUserData
                horariosEscolhidos.add((String) btn.getUserData());
            }
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