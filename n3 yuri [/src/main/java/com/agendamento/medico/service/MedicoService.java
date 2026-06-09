package com.agendamento.medico.service;

import com.agendamento.medico.entity.Medico;
import com.agendamento.medico.repository.MedicoRepository;
import com.agendamento.compartilhado.persistence.RepositoryFactory;

import java.util.List;
import java.util.Optional;

public class MedicoService {

    private final MedicoRepository repo = RepositoryFactory.medico();

    public Medico cadastrar(String nome, String crm, String especialidade,
                             String telefone, String email) {
        Medico m = new Medico(nome, crm, especialidade, telefone, email);
        repo.save(m);
        System.out.println("✔ Médico cadastrado: " + m);
        return m;
    }

    public List<Medico> listarTodos() { return repo.findAll(); }

    public List<Medico> listarAtivos() { return repo.findAtivos(); }

    public Optional<Medico> buscarPorId(long id) { return repo.findById(id); }

    public List<Medico> buscarPorEspecialidade(String esp) { return repo.findByEspecialidade(esp); }

    public void atualizar(Medico m) { repo.update(m); }

    public void inativar(long id) {
        Medico m = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Médico não encontrado: " + id));
        m.inativar();
        repo.update(m);
        System.out.println("✔ Médico inativado: " + m);
    }
}
