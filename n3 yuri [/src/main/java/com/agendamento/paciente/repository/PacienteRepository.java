package com.agendamento.paciente.repository;

import com.agendamento.paciente.entity.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository {
    void save(Paciente paciente);
    Optional<Paciente> findById(long id);
    Optional<Paciente> findByCpf(String cpf);
    List<Paciente> findAll();
    List<Paciente> findByNome(String nome);
    void update(Paciente paciente);
    void delete(long id);
}
