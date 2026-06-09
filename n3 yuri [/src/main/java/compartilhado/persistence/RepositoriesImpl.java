package compartilhado.persistence;

import com.agendamento.usuario.entity.Usuario;
import com.agendamento.medico.entity.Medico;
import com.agendamento.paciente.entity.Paciente;
import com.agendamento.agendamento.entity.Agendamento;
import com.agendamento.usuario.repository.UsuarioRepository;
import com.agendamento.medico.repository.MedicoRepository;
import com.agendamento.paciente.repository.PacienteRepository;
import com.agendamento.agendamento.repository.AgendamentoRepository;

import java.util.List;
import java.util.Optional;

class UsuarioRepositorySQLite extends BaseRepository<Usuario> implements UsuarioRepository {
    public UsuarioRepositorySQLite() { super(Usuario.class); }

    @Override public Optional<Usuario> findByEmail(String email) {
        return findWhere("email = ?", email).stream().findFirst();
    }
}

class MedicoRepositorySQLite extends BaseRepository<Medico> implements MedicoRepository {
    public MedicoRepositorySQLite() { super(Medico.class); }

    @Override public List<Medico> findByEspecialidade(String especialidade) {
        return findWhere("especialidade LIKE ?", "%" + especialidade + "%");
    }

    @Override public List<Medico> findAtivos() { return findWhere("ativo = 1"); }
}

class PacienteRepositorySQLite extends BaseRepository<Paciente> implements PacienteRepository {
    public PacienteRepositorySQLite() { super(Paciente.class); }

    @Override public Optional<Paciente> findByCpf(String cpf) { return findWhere("cpf = ?", cpf).stream().findFirst(); }

    @Override public List<Paciente> findByNome(String nome) { return findWhere("nome LIKE ?", "%" + nome + "%"); }
}

class AgendamentoRepositorySQLite extends BaseRepository<Agendamento> implements AgendamentoRepository {
    public AgendamentoRepositorySQLite() { super(Agendamento.class); }

    @Override public List<Agendamento> findByPacienteId(long id) { return findWhere("paciente_id = ?", id); }

    @Override public List<Agendamento> findByMedicoId(long id) { return findWhere("medico_id = ?", id); }

    @Override public List<Agendamento> findByStatus(String status) { return findWhere("status = ?", status); }

    @Override public List<Agendamento> findByData(String data) { return findWhere("data_hora LIKE ?", data + "%"); }
}
