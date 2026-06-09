package com.agendamento.agendamento.repository;

import com.agendamento.agendamento.entity.Agendamento;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository {
    void save(Agendamento agendamento);
    Optional<Agendamento> findById(long id);
    List<Agendamento> findAll();
    List<Agendamento> findByPacienteId(long pacienteId);
    List<Agendamento> findByMedicoId(long medicoId);
    List<Agendamento> findByStatus(String status);
    List<Agendamento> findByData(String data);
    void update(Agendamento agendamento);
    void delete(long id);
}
