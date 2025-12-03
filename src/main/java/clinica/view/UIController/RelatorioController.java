package clinica.view.UIController;

import clinica.controller.Cadastrador; // MUDANÇA: Importar Cadastrador
import clinica.controller.Exportador;
import clinica.controller.Relatoriador;
import clinica.enums.TipoExportacao;
import clinica.model.Agendamento;
import clinica.model.Dentista;
import clinica.model.Pagamento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RelatorioController {

    @FXML private ComboBox<String> comboTipoRelatorio;
    @FXML private ComboBox<Dentista> comboDentista;
    @FXML private DatePicker dateInicio;
    @FXML private DatePicker dateFim;
    @FXML private ComboBox<TipoExportacao> comboFormato;
    @FXML private Button btnGerar;

    private Relatoriador relatoriador;
    private Cadastrador cadastrador; // MUDANÇA: Usar Cadastrador em vez de Manager
    private Exportador exportador;

    // MUDANÇA: Método de injeção atualizado
    public void setDependencies(Relatoriador relatoriador, Cadastrador cadastrador) {
        this.relatoriador = relatoriador;
        this.cadastrador = cadastrador;
        this.exportador = new Exportador();
        carregarCombos();
    }

    @FXML
    public void initialize() {
        comboTipoRelatorio.setItems(FXCollections.observableArrayList(
                "Consultas por Período",
                "Faturamento",
                "Pendências e Frequência"
        ));

        comboFormato.setItems(FXCollections.observableArrayList(TipoExportacao.values()));
        comboFormato.setValue(TipoExportacao.PDF);

        // Define conversor para mostrar apenas o nome do dentista no ComboBox
        comboDentista.setCellFactory(param -> new ListCell<Dentista>() {
            @Override
            protected void updateItem(Dentista item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNome());
            }
        });
        comboDentista.setButtonCell(new ListCell<Dentista>() {
            @Override
            protected void updateItem(Dentista item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNome());
            }
        });

        // Configura valores padrão de data
        dateInicio.setValue(LocalDate.now().withDayOfMonth(1));
        dateFim.setValue(LocalDate.now());
    }

    private void carregarCombos() {
        if (cadastrador != null) {
            // MUDANÇA: Chamada corrigida para o método que existe no Cadastrador
            List<Dentista> dentistas = cadastrador.listarDentistas();
            comboDentista.setItems(FXCollections.observableArrayList(dentistas));
        }
    }

    @FXML
    private void handleGerarRelatorio() {
        String tipoRelatorio = comboTipoRelatorio.getValue();
        TipoExportacao formato = comboFormato.getValue();

        if (tipoRelatorio == null || formato == null) {
            mostrarAlerta("Selecione o tipo de relatório e o formato.");
            return;
        }

        // Verificação simples de data
        if(dateInicio.getValue() == null || dateFim.getValue() == null){
            mostrarAlerta("Selecione as datas de início e fim.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Relatório");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(formato.toString(), "*." + formato.toString().toLowerCase())
        );
        File arquivo = fileChooser.showSaveDialog(new Stage());

        if (arquivo != null) {
            try {
                processarExportacao(tipoRelatorio, formato, arquivo);
                mostrarAlerta("Relatório exportado com sucesso!");
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro ao exportar: " + e.getMessage());
            }
        }
    }

    private void processarExportacao(String tipo, TipoExportacao formato, File arquivo) throws Exception {
        List<String> cabecalhos;
        List<List<String>> dados = new ArrayList<>();
        String titulo = tipo;

        LocalDateTime inicio = dateInicio.getValue().atStartOfDay();
        LocalDateTime fim = dateFim.getValue().atTime(23, 59, 59);

        switch (tipo) {
            case "Consultas por Período":
                cabecalhos = Arrays.asList("Data", "Paciente", "Dentista", "Procedimento", "Status");
                Dentista d = comboDentista.getValue();
                List<Agendamento> agendamentos = relatoriador.gerarRelatorioConsultas(d, inicio, fim);
                dados = relatoriador.prepararDadosAgendamento(agendamentos);
                if (d != null) titulo += " - Dr(a). " + d.getNome();
                break;

            case "Faturamento":
                cabecalhos = Arrays.asList("Data", "Valor", "Método", "Paciente");
                List<Pagamento> pagamentos = relatoriador.gerarRelatorioFinanceiro(inicio, fim);
                double total = relatoriador.calcularFaturamentoTotal(inicio, fim);

                for (Pagamento p : pagamentos) {
                    List<String> linha = new ArrayList<>();
                    linha.add(p.getDataPagamento().toString());
                    linha.add("R$ " + p.getValor());
                    linha.add(p.getMetodo().toString());
                    linha.add(p.getReferenciaAgendamento() != null ? p.getReferenciaAgendamento().getPaciente().getNome() : "Avulso");
                    dados.add(linha);
                }
                dados.add(Arrays.asList("TOTAL", "R$ " + total, "-", "-"));
                break;

            case "Pendências e Frequência":
                cabecalhos = Arrays.asList("Data", "Paciente", "Procedimento", "Status", "Pago?");
                List<Agendamento> pendentes = relatoriador.gerarRelatorioPendencias();
                for (Agendamento a : pendentes) {
                    List<String> linha = new ArrayList<>();
                    linha.add(a.getDataHora().toString());
                    linha.add(a.getPaciente().getNome());
                    linha.add(a.getProcedimento().getNome());
                    linha.add(a.getStatus().toString());
                    linha.add(a.isPago() ? "Sim" : "Não");
                    dados.add(linha);
                }
                break;

            default:
                throw new IllegalStateException("Tipo desconhecido");
        }

        exportador.exportar(arquivo, formato, titulo, cabecalhos, dados);
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Relatórios");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}