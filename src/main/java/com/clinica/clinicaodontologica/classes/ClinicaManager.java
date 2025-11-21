package com.clinica.clinicaodontologica.classes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.clinica.clinicaodontologica.enums.StatusAgendamento;
import com.clinica.clinicaodontologica.repositorios.RepositorioAgendamento;

public class ClinicaManager {
    public boolean marcarAgendamento(Paciente paciente, Dentista dentista, Procedimento procedimento, LocalDateTime dataHora, String sala) {
        //Bloquear agendamento de paciente com pagamento pendente (REQ24)
        if (paciente.getPossuiPagamentoPendente()) {
            System.out.println("Erro: Paciente possui pagamentos pendentes. Agendamento bloqueado.");
            return false;
        }
        
        //Não permitir agendamento em horário indisponível (REQ23)
        if (!verificarDisponibilidadeDentista(dentista, dataHora, procedimento.getDuracaoEmMinutos())) {
            System.out.println("Erro: Dentista indisponível no horário solicitado.");
            return false;
        }

        Agendamento novoAgendamento = new Agendamento(paciente, dentista, procedimento, dataHora, sala, StatusAgendamento.PLANEJADO);
        RepositorioAgendamento.instance.cadastrar(novoAgendamento);
        System.out.println("Consulta agendada com sucesso!");
        return true;
    }
    
    //Verifica se o dentista está livre (REQ23)
    private boolean verificarDisponibilidadeDentista(Dentista dentista, LocalDateTime dataHora, int duracao) {
        //Verificar folgas e ausências específicas
        if (dentista.getDiasDeFolga().contains(dataHora.getDayOfWeek()) ||
            dentista.estaAusente(dataHora.toLocalDate())) {
            return false;
        }
        
        //Verificar horário de trabalho fixo
        if (dataHora.toLocalTime().isBefore(dentista.getHorarioTrabalhoInicio()) ||
            dataHora.toLocalTime().plusMinutes(duracao).isAfter(dentista.getHorarioTrabalhoFim())) {
            return false;
        }
        
        //Verificar conflito com agendamentos existentes
        Agendamento a = RepositorioAgendamento.instance.getAgendamento(dentista, dataHora);
        if(a != null) {
            if (!a.isCancelado()) { 
                LocalDateTime fimExistente = a.getDataHora().plusMinutes(a.getProcedimento().getDuracaoEmMinutos());
                LocalDateTime fimNovo = dataHora.plusMinutes(duracao);
                if (dataHora.isBefore(fimExistente) && fimNovo.isAfter(a.getDataHora())) {
                    return false;
                }
            }
        }
        return true;
    }

    //Relatório (REQ16)
    public List<Agendamento> gerarRelatorioConsultas(Dentista dentista, LocalDateTime inicio, LocalDateTime fim) {
        return RepositorioAgendamento.instance.getListaCompleta().stream()
            .filter(a -> a.getDentista().equals(dentista) && 
                        !a.isCancelado() &&
                        a.getDataHora().isAfter(inicio) && 
                        a.getDataHora().isBefore(fim))
            .collect(Collectors.toList());
    }
}