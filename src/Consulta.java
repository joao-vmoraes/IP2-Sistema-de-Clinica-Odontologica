import java.time.LocalDateTime;

public class Consulta extends Atendimento {
    private StatusConsulta statusConsulta;
    private LocalDateTime dataHora;

    public Consulta(LocalDateTime dataHora, StatusConsulta statusConsulta, Dentista dentista, Paciente paciente, Procedimento procedimento) {
        this.dataHora = dataHora;
        this.statusConsulta = statusConsulta != null ? statusConsulta : StatusConsulta.PLANEJADO;
        this.dentista = dentista;
        this.paciente = paciente;
        this.procedimento = procedimento;
    }

    // Getters e Setters
    public Paciente getPaciente() { return this.paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public LocalDateTime getDataHora() { return this.dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public StatusConsulta getStatusConsulta() { return this.statusConsulta; }
    public void setStatusConsulta(StatusConsulta statusConsulta) { this.statusConsulta = statusConsulta; }

    public Procedimento getProcedimento() { return this.procedimento; }
    public void setProcedimento(Procedimento procedimento) { this.procedimento = procedimento; }

    public Dentista getDentista() { return this.dentista; }
    public void setDentista(Dentista dentista) { this.dentista = dentista; }

    public void atualizarConsulta(Paciente paciente, Dentista dentista, Procedimento procedimento, LocalDateTime dataHora, StatusConsulta statusConsulta) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.procedimento = procedimento;
        this.dataHora = dataHora;
        this.statusConsulta = statusConsulta;
    }

    public void atualizarStatus(StatusConsulta novoStatus) {
        // Lógica corrigida: Só bloqueia se JÁ estiver concluído
        if (this.statusConsulta == StatusConsulta.CONCLUIDO) {
            System.out.println("Erro: Procedimento concluído não pode ser alterado.");
            return;
        }
        this.statusConsulta = novoStatus;
    }
}