package com.agendamento.paciente.service;

import com.agendamento.paciente.entity.Paciente;
import com.agendamento.paciente.repository.PacienteRepository;
import com.agendamento.compartilhado.persistence.RepositoryFactory;

import java.util.List;
import java.util.Optional;

public class PacienteService {

    private final PacienteRepository repo = RepositoryFactory.paciente();

    public Paciente cadastrar(String nome, String cpf, String dataNasc,
                               String telefone, String email) {
        repo.findByCpf(cpf).ifPresent(p -> { throw new IllegalStateException("CPF já cadastrado: " + cpf); });
        Paciente p = new Paciente(nome, cpf, dataNasc, telefone, email);
        repo.save(p);
        System.out.println("✔ Paciente cadastrado: " + p);
        return p;
    }

    public List<Paciente> listarTodos() { return repo.findAll(); }

    public Optional<Paciente> buscarPorId(long id) { return repo.findById(id); }

    public List<Paciente> buscarPorNome(String nome) { return repo.findByNome(nome); }

    public void atualizar(Paciente p) { repo.update(p); }

    public void inativar(long id) {
        Paciente p = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado: " + id));
        p.inativar();
        repo.update(p);
        System.out.println("✔ Paciente inativado: " + p);
    }
}
