package controller;

import model.Agendamento;
import model.Dentista;
import enums.StatusAgendamento;
import repository.AgendamentoRepositorio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Relatoriador {
    private AgendamentoRepositorio agendamentoRepo;

    public Relatoriador(AgendamentoRepositorio agendamentoRepo) {
        this.agendamentoRepo = agendamentoRepo;
    }

    public List<Agendamento> gerarRelatorioConsultas(Dentista dentista, LocalDateTime inicio, LocalDateTime fim) {
        List<Agendamento> todos = agendamentoRepo.listarTodos();

        return todos.stream()
                .filter(a -> a.getDentista().getCpf().equals(dentista.getCpf()) &&
                        a.getStatus() != StatusAgendamento.CANCELADO &&
                        (a.getDataHora().isAfter(inicio) || a.getDataHora().isEqual(inicio)) &&
                        (a.getDataHora().isBefore(fim) || a.getDataHora().isEqual(fim)))
                .collect(Collectors.toList());
    }

    public void relatorioGeral() {
        System.out.println("Total de Agendamentos no sistema: " + agendamentoRepo.listarTodos().size());
    }

}