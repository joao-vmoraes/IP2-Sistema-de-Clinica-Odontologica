package clinica.controller;

import clinica.model.Dentista;
import clinica.model.Paciente;
import clinica.model.Procedimento;
import clinica.repository.DentistaRepositorio;
import clinica.repository.PacienteRepositorio;
import clinica.repository.ProcedimentoRepositorio;

public class Cadastrador {

    private PacienteRepositorio pacienteRepositorio;
    private DentistaRepositorio dentistaRepositorio;
    private ProcedimentoRepositorio procedimentoRepositorio;

    public Cadastrador(PacienteRepositorio pacienteRepo, DentistaRepositorio dentistaRepo, ProcedimentoRepositorio procedimentoRepo) {
        this.pacienteRepositorio = pacienteRepo;
        this.dentistaRepositorio = dentistaRepo;
        this.procedimentoRepositorio = procedimentoRepo;
    }

    // PACIENTE

    public void cadastrar(Paciente paciente) {
        if (paciente == null) {
            System.err.println("Erro: model.Paciente inválido.");
            return;
        }

        Paciente existente = pacienteRepositorio.buscarPorCpf(paciente.getCpf());
        if (existente == null) {
            pacienteRepositorio.salvar(paciente);
            System.out.println("model.Paciente " + paciente.getNome() + " cadastrado com sucesso.");
        } else {
            System.err.println("Erro: model.Paciente com CPF " + paciente.getCpf() + " já existe.");
        }
    }

    public void remover(Paciente paciente) {
        Paciente existente = pacienteRepositorio.buscarPorCpf(paciente.getCpf());
        if (existente != null) {
            pacienteRepositorio.deletar(existente);
            System.out.println("model.Paciente removido com sucesso.");
        } else {
            System.err.println("Erro: model.Paciente não encontrado para remoção.");
        }
    }

    public void atualizar(Paciente pacienteAtualizado) {
        Paciente existente = pacienteRepositorio.buscarPorCpf(pacienteAtualizado.getCpf());
        if (existente != null) {
            pacienteRepositorio.atualizar(pacienteAtualizado);
            System.out.println("Dados do paciente atualizados com sucesso.");
        } else {
            System.err.println("Erro: model.Paciente não encontrado para atualização.");
        }
    }

    // DENTISTA

    public void cadastrar(Dentista dentista) {
        if (dentista == null) {
            System.err.println("Erro: model.Dentista inválido.");
            return;
        }

        Dentista existente = dentistaRepositorio.buscarPorCpf(dentista.getCpf());
        if (existente == null) {
            dentistaRepositorio.salvar(dentista);
            System.out.println("model.Dentista " + dentista.getNome() + " cadastrado com sucesso.");
        } else {
            System.err.println("Erro: model.Dentista com CPF " + dentista.getCpf() + " já existe.");
        }
    }

    public void remover(Dentista dentista) {
        Dentista existente = dentistaRepositorio.buscarPorCpf(dentista.getCpf());
        if (existente != null) {
            dentistaRepositorio.deletar(existente);
            System.out.println("model.Dentista removido com sucesso.");
        } else {
            System.err.println("Erro: model.Dentista não encontrado.");
        }
    }

    public void atualizar(Dentista dentistaAtualizado) {
        Dentista existente = dentistaRepositorio.buscarPorCpf(dentistaAtualizado.getCpf());
        if (existente != null) {
            dentistaRepositorio.atualizar(dentistaAtualizado);
            System.out.println("Dados do dentista atualizados com sucesso.");
        } else {
            System.err.println("Erro: model.Dentista não encontrado para atualização.");
        }
    }

    // PROCEDIMENTOS

    public void cadastrar(Procedimento procedimento) {
        if (procedimento == null) {
            System.err.println("Erro: model.Procedimento inválido.");
            return;
        }
        procedimentoRepositorio.salvar(procedimento);
        System.out.println("model.Procedimento adicionado ao catálogo.");
    }

    public void remover(Procedimento procedimento) {
        if (procedimento != null) {
            procedimentoRepositorio.deletarProcedimento(procedimento);
            System.out.println("model.Procedimento removido do catálogo.");
        } else {
            System.err.println("Erro: model.Procedimento inválido.");
        }
    }
}