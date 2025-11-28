package clinica.controller;

import clinica.model.Dentista;
import clinica.model.Paciente;
import clinica.model.Procedimento;
import clinica.repository.DentistaRepositorio;
import clinica.repository.PacienteRepositorio;
import clinica.repository.ProcedimentoRepositorio;

import java.time.LocalTime;
import java.util.List;

public class Cadastrador {

    private PacienteRepositorio pacienteRepositorio;
    private DentistaRepositorio dentistaRepositorio;
    private ProcedimentoRepositorio procedimentoRepositorio;

    public Cadastrador(PacienteRepositorio pacienteRepo, DentistaRepositorio dentistaRepo, ProcedimentoRepositorio procedimentoRepo) {
        this.pacienteRepositorio = pacienteRepo;
        this.dentistaRepositorio = dentistaRepo;
        this.procedimentoRepositorio = procedimentoRepo;
    }

    // --- PACIENTE ---

    public void cadastrar(Paciente paciente) {
        if (paciente == null) {
            System.err.println("Erro: Paciente inválido.");
            return;
        }
        Paciente existente = pacienteRepositorio.buscarPorCpf(paciente.getCpf());
        if (existente == null) {
            pacienteRepositorio.salvar(paciente);
            System.out.println("Paciente " + paciente.getNome() + " cadastrado com sucesso.");
        } else {
            System.err.println("Erro: Paciente com CPF " + paciente.getCpf() + " já existe.");
        }
    }

    public void atualizar(Paciente paciente) {
        pacienteRepositorio.atualizar(paciente);
    }

    public void remover(Paciente paciente) {
        pacienteRepositorio.deletar(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepositorio.listarTodos();
    }

    public Paciente buscarPacientePorCpf(String cpf) {
        return pacienteRepositorio.buscarPorCpf(cpf);
    }

    // --- DENTISTA ---

    public void cadastrar(Dentista dentista) {
        if (dentista == null) {
            System.err.println("Erro: Dentista inválido.");
            return;
        }
        Dentista existente = dentistaRepositorio.buscarPorCpf(dentista.getCpf());
        if (existente == null) {
            dentistaRepositorio.salvar(dentista);
            System.out.println("Dentista " + dentista.getNome() + " cadastrado com sucesso.");
        } else {
            System.err.println("Erro: Dentista com CPF " + dentista.getCpf() + " já existe.");
        }
    }

    public void atualizar(Dentista dentista) {
        dentistaRepositorio.atualizar(dentista);
    }

    public void remover(Dentista dentista) {
        dentistaRepositorio.deletar(dentista);
    }

    public List<Dentista> listarDentistas() {
        return dentistaRepositorio.listarTodos();
    }

    public List<Dentista> buscarDentistasPorDisponibilidade(LocalTime horario) {
        return dentistaRepositorio.buscarPorDisponibilidadeHorario(horario);
    }

    public List<Dentista> buscarDentistasPorCpfParcial(String termo) {
        return dentistaRepositorio.buscarPorParteCpf(termo);
    }

    // --- PROCEDIMENTO ---

    public void cadastrar(Procedimento procedimento) {
        if (procedimento == null) {
            System.err.println("Erro: Procedimento inválido.");
            return;
        }
        procedimentoRepositorio.salvar(procedimento);
        System.out.println("Procedimento adicionado ao catálogo.");
    }

    public void remover(Procedimento procedimento) {
        procedimentoRepositorio.deletarProcedimento(procedimento);
    }

    public List<Procedimento> listarProcedimentos() {
        return procedimentoRepositorio.listarTodos();
    }
}