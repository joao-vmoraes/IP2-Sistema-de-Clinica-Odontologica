package clinica.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import clinica.enums.StatusAgendamento;
import clinica.model.Agendamento;
import clinica.model.Dentista;
import clinica.model.Pagamento;
import clinica.repository.AgendamentoRepositorio;
import clinica.repository.PagamentoRepositorio;

public class Relatoriador {
    private AgendamentoRepositorio agendamentoRepo;
    private PagamentoRepositorio pagamentoRepo;

    public Relatoriador(AgendamentoRepositorio agendamentoRepo, PagamentoRepositorio pagamentoRepo) {
        this.agendamentoRepo = agendamentoRepo;
        this.pagamentoRepo = pagamentoRepo;
    }

    // REQ16: Relatório de Consultas por Dentista e Período
    public List<Agendamento> gerarRelatorioConsultas(Dentista dentista, LocalDateTime inicio, LocalDateTime fim) {
        List<Agendamento> todos = agendamentoRepo.listarTodos();
        return todos.stream()
                .filter(a -> (dentista == null || a.getDentista().getCpf().equals(dentista.getCpf())) &&
                        (a.getDataHora().isAfter(inicio) || a.getDataHora().isEqual(inicio)) &&
                        (a.getDataHora().isBefore(fim) || a.getDataHora().isEqual(fim)))
                .collect(Collectors.toList());
    }

    // REQ17: Dados de Faturamento (Total)
    public double calcularFaturamentoTotal(LocalDateTime inicio, LocalDateTime fim) {
        return pagamentoRepo.listarTodos().stream()
                .filter(p -> p.isConfirmado() &&
                        (p.getDataPagamento().isAfter(inicio) || p.getDataPagamento().isEqual(inicio)) &&
                        (p.getDataPagamento().isBefore(fim) || p.getDataPagamento().isEqual(fim)))
                .mapToDouble(Pagamento::getValor)
                .sum();
    }

    // REQ17: Lista de Pagamentos para Relatório Detalhado
    public List<Pagamento> gerarRelatorioFinanceiro(LocalDateTime inicio, LocalDateTime fim) {
        return pagamentoRepo.listarTodos().stream()
                .filter(p -> p.isConfirmado() &&
                        (p.getDataPagamento().isAfter(inicio) || p.getDataPagamento().isEqual(inicio)) &&
                        (p.getDataPagamento().isBefore(fim) || p.getDataPagamento().isEqual(fim)))
                .collect(Collectors.toList());
    }

    // REQ18: Agendamentos Pendentes (Não realizados ou Não pagos)
    public List<Agendamento> gerarRelatorioPendencias() {
        return agendamentoRepo.listarTodos().stream()
                .filter(a -> a.getStatus() == StatusAgendamento.PLANEJADO || !a.isPago())
                .collect(Collectors.toList());
    }

    // Auxiliar: Transforma Lista de Agendamentos em Matriz de Strings para exportação
    public List<List<String>> prepararDadosAgendamento(List<Agendamento> lista) {
        List<List<String>> dados = new ArrayList<>();
        for (Agendamento a : lista) {
            List<String> linha = new ArrayList<>();
            linha.add(a.getDataHora().toString());
            linha.add(a.getPaciente().getNome());
            linha.add(a.getDentista().getNome());
            linha.add(a.getProcedimento().getNome());
            linha.add(a.getStatus().toString());
            dados.add(linha);
        }
        return dados;
    }
}