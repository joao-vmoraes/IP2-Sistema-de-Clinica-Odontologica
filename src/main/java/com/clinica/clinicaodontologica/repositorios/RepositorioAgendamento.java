package com.clinica.clinicaodontologica.repositorios;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.clinica.clinicaodontologica.classes.Agendamento;
import com.clinica.clinicaodontologica.classes.Dentista;
import com.clinica.clinicaodontologica.classes.Paciente;

public class RepositorioAgendamento {
    public static RepositorioAgendamento instance;

    private ArrayList<Agendamento> agendamentos = new ArrayList<>();
    
    public static RepositorioAgendamento getInstance() {
        if (instance == null)
        {
            instance = new RepositorioAgendamento();
        }
        return instance;
    }

    public RepositorioAgendamento()
    {
        agendamentos = new ArrayList<>();
    }

    public void cadastrar(Agendamento a)
    {
        if(a == null)
        {
            System.err.println("Agendamento nulo.");
            return;
        }

        if(agendamentos.contains(a))
        {
            System.err.println("Agendamento ja existe.");
            return;
        }

        agendamentos.add(a);
        System.err.println("Agendamento cadastrado com sucesso.");
    }

    public Agendamento getAgendamento(Paciente paciente, LocalDateTime data)
    {
        for(int i = 0; i < agendamentos.size(); i++)
        {
            if(agendamentos.get(i).getPaciente() == paciente && agendamentos.get(i).getDataHora() == data)
            {
                return agendamentos.get(i);
            }
        }
        System.err.println("Agendamento nao encontrado.");
        return null;
    }

    public Agendamento getAgendamento(Dentista dentista, LocalDateTime data)
    {
        for(int i = 0; i < agendamentos.size(); i++)
        {
            if(agendamentos.get(i).getDentista() == dentista && agendamentos.get(i).getDataHora() == data)
            {
                return agendamentos.get(i);
            }
        }
        System.err.println("Agendamento nao encontrado.");
        return null;
    }

    public Agendamento getAgendamento(Paciente paciente, Dentista dentista, LocalDateTime data)
    {
        for(int i = 0; i < agendamentos.size(); i++)
        {
            if(agendamentos.get(i).getPaciente() == paciente && agendamentos.get(i).getDentista() == dentista && agendamentos.get(i).getDataHora() == data)
            {
                return agendamentos.get(i);
            }
        }
        System.err.println("Agendamento nao encontrado.");
        return null;
    }

    public ArrayList<Agendamento> getListaCompleta()
    {
        return agendamentos;
    }
}
