package com.agendamento.agendamento.service;

import com.agendamento.agendamento.entity.Agendamento;
import com.agendamento.agendamento.repository.AgendamentoRepository;
import com.agendamento.compartilhado.persistence.RepositoryFactory;

import java.util.List;
import java.util.Optional;

public class AgendamentoService {

    private final AgendamentoRepository repo = RepositoryFactory.agendamento();

    public Agendamento criarAgendamento(long pacienteId, long medicoId, long usuarioId,
                                       String dataHora, String motivo) {
        Agendamento a = new Agendamento(pacienteId, medicoId, usuarioId, dataHora, motivo);
        repo.save(a);
        System.out.println("✔ Agendamento criado: " + a);
        return a;
    }

    public List<Agendamento> listarTodos() { return repo.findAll(); }

    public List<Agendamento> listarPorData(String data) { return repo.findByData(data); }

    public List<Agendamento> listarPorPaciente(long pacienteId) { return repo.findByPacienteId(pacienteId); }

    public Optional<Agendamento> buscarPorId(long id) { return repo.findById(id); }

    public void confirmarAgendamento(long id) {
        Agendamento a = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));
        a.confirmar();
        repo.update(a);
        System.out.println("✔ Agendamento confirmado: " + a);
    }

    public void cancelarAgendamento(long id, String motivo) {
        Agendamento a = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));
        a.cancelar(motivo);
        repo.update(a);
        System.out.println("✔ Agendamento cancelado: " + a);
    }

    public void realizarConsulta(long id, String obs) {
        Agendamento a = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));
        a.realizar(obs);
        repo.update(a);
        System.out.println("✔ Consulta realizada: " + a);
    }
}
