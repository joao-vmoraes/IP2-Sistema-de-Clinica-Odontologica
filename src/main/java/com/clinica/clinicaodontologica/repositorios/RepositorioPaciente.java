package com.clinica.clinicaodontologica.repositorios;

import java.util.ArrayList;

import com.clinica.clinicaodontologica.classes.Paciente;

public class RepositorioPaciente {
    public static RepositorioPaciente instance;

    private ArrayList<Paciente> pacientes = new ArrayList<>();
    
    public static RepositorioPaciente getInstance() {
        if (instance == null)
        {
            instance = new RepositorioPaciente();
        }
        return instance;
    }

    public RepositorioPaciente()
    {
        pacientes = new ArrayList<>();
    }

    public void cadastrar(Paciente p)
    {
        if(p == null)
        {
            System.err.println("Paciente nulo.");
            return;
        }

        if(pacientes.contains(p))
        {
            System.err.println("Paciente ja existe.");
            return;
        }

        pacientes.add(p);
        System.err.println("Paciente cadastrado com sucesso.");
    }

    public void cadastrar(String nome, String cpf, String telefone, String email, String endereco)
    {
        Paciente p = new Paciente(nome, cpf, telefone, email, endereco);
        pacientes.add(p);
        System.err.println("Paciente cadastrado com sucesso.");
    }

    //Ao invés de remover o paciente do repositório, acho melhor ter uma função nele que o seta como "cancelado" ou algo assim
    /*public void remover(Paciente p)
    {
        if(p == null)
        {
            System.err.println("Paciente nulo.");
            return;
        }

        if(!pacientes.contains(p))
        {
            System.err.println("Paciente nao registrado");
            return;
        }

        pacientes.remove(p);
        System.err.println("Paciente removido com sucesso.");
    }*/

    public Paciente getPacientePorNome(String nome)
    {
        for(int i = 0; i < pacientes.size(); i++)
        {
            if(pacientes.get(i).getNome().equals(nome))
            {
                return pacientes.get(i);
            }
        }
        System.err.println("Nome nao encontrado.");
        return null;
    }

    public Paciente getPacientePorCpf(String cpf)
    {
        for(int i = 0; i < pacientes.size(); i++)
        {
            if(pacientes.get(i).getCpf().equals(cpf))
            {
                return pacientes.get(i);
            }
        }
        System.err.println("Cpf nao encontrado.");
        return null;
    }

    public Paciente getPacientePorEmail(String email)
    {
        for(int i = 0; i < pacientes.size(); i++)
        {
            if(pacientes.get(i).getEmail().equals(email))
            {
                return pacientes.get(i);
            }
        }
        System.err.println("Email nao encontrado.");
        return null;
    }

    public ArrayList<Paciente> getListaCompleta()
    {
        return pacientes;
    }
}
