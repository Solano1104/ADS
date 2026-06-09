package com.agendamento.medico.repository;

import com.agendamento.medico.entity.Medico;
import java.util.List;
import java.util.Optional;

public interface MedicoRepository {
    void save(Medico medico);
    Optional<Medico> findById(long id);
    List<Medico> findAll();
    List<Medico> findByEspecialidade(String especialidade);
    List<Medico> findAtivos();
    void update(Medico medico);
    void delete(long id);
}
