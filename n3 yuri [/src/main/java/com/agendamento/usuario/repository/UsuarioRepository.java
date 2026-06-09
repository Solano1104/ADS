package com.agendamento.usuario.repository;

import com.agendamento.usuario.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    void save(Usuario usuario);
    Optional<Usuario> findById(long id);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAll();
    void update(Usuario usuario);
    void delete(long id);
}
