package com.agendamento.compartilhado.persistence;

import com.agendamento.usuario.repository.UsuarioRepository;
import com.agendamento.medico.repository.MedicoRepository;
import com.agendamento.paciente.repository.PacienteRepository;
import com.agendamento.agendamento.repository.AgendamentoRepository;

public class RepositoryFactory {

    private static UsuarioRepository usuarioRepository;
    private static MedicoRepository medicoRepository;
    private static PacienteRepository pacienteRepository;
    private static AgendamentoRepository agendamentoRepository;

    public static UsuarioRepository usuario() { if (usuarioRepository == null) usuarioRepository = new UsuarioRepositorySQLite(); return usuarioRepository; }
    public static MedicoRepository medico() { if (medicoRepository == null) medicoRepository = new MedicoRepositorySQLite(); return medicoRepository; }
    public static PacienteRepository paciente() { if (pacienteRepository == null) pacienteRepository = new PacienteRepositorySQLite(); return pacienteRepository; }
    public static AgendamentoRepository agendamento() { if (agendamentoRepository == null) agendamentoRepository = new AgendamentoRepositorySQLite(); return agendamentoRepository; }
}
