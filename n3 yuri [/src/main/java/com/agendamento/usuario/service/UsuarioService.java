package com.agendamento.usuario.service;

import com.agendamento.usuario.entity.Usuario;
import com.agendamento.usuario.repository.UsuarioRepository;
import com.agendamento.compartilhado.persistence.RepositoryFactory;

import java.util.List;
import java.util.Optional;

public class UsuarioService {

    private final UsuarioRepository repo = RepositoryFactory.usuario();

    public Usuario cadastrar(String nome, String email, String senha, String perfil) {
        repo.findByEmail(email).ifPresent(u -> { throw new IllegalStateException("Email já cadastrado: " + email); });
        Usuario u = new Usuario(nome, email, senha, perfil);
        repo.save(u);
        System.out.println("✔ Usuário cadastrado: " + u);
        return u;
    }

    public Optional<Usuario> autenticar(String email, String senha) {
        return repo.findByEmail(email).filter(u -> u.getSenha().equals(senha) && u.isAtivo());
    }

    public List<Usuario> listarTodos() { return repo.findAll(); }
}
