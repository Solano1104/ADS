package com.agendamento;

import com.agendamento.paciente.service.PacienteService;
import com.agendamento.medico.service.MedicoService;
import com.agendamento.usuario.service.UsuarioService;
import com.agendamento.agendamento.service.AgendamentoService;
import com.agendamento.paciente.entity.Paciente;
import com.agendamento.medico.entity.Medico;
import com.agendamento.usuario.entity.Usuario;
import com.agendamento.agendamento.entity.Agendamento;

import java.util.List;
import java.util.Optional;

public class ServiceFacade {

    private final PacienteService pacienteService = new PacienteService();
    private final MedicoService medicoService = new MedicoService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final AgendamentoService agendamentoService = new AgendamentoService();

    private static ServiceFacade instance;

    private ServiceFacade() {}

    public static ServiceFacade getInstance() {
        if (instance == null) instance = new ServiceFacade();
        return instance;
    }

    public Paciente cadastrarPaciente(String nome, String cpf, String dataNasc,
                                      String telefone, String email) {
        return pacienteService.cadastrar(nome, cpf, dataNasc, telefone, email);
    }

    public List<Paciente> listarPacientes() { return pacienteService.listarTodos(); }

    public Optional<Paciente> buscarPacientePorId(long id) { return pacienteService.buscarPorId(id); }

    public List<Paciente> buscarPacientesPorNome(String nome) { return pacienteService.buscarPorNome(nome); }

    public void inativarPaciente(long id) { pacienteService.inativar(id); }

    public Medico cadastrarMedico(String nome, String crm, String especialidade,
                                   String telefone, String email) {
        return medicoService.cadastrar(nome, crm, especialidade, telefone, email);
    }

    public List<Medico> listarMedicos() { return medicoService.listarTodos(); }

    public List<Medico> listarMedicosAtivos() { return medicoService.listarAtivos(); }

    public Optional<Medico> buscarMedicoPorId(long id) { return medicoService.buscarPorId(id); }

    public List<Medico> buscarMedicosPorEspecialidade(String esp) { return medicoService.buscarPorEspecialidade(esp); }

    public void inativarMedico(long id) { medicoService.inativar(id); }

    public Usuario cadastrarUsuario(String nome, String email, String senha, String perfil) {
        return usuarioService.cadastrar(nome, email, senha, perfil);
    }

    public Optional<Usuario> autenticar(String email, String senha) { return usuarioService.autenticar(email, senha); }

    public List<Usuario> listarUsuarios() { return usuarioService.listarTodos(); }

    public Agendamento criarAgendamento(long pacienteId, long medicoId, long usuarioId,
                                         String dataHora, String motivo) {
        return agendamentoService.criarAgendamento(pacienteId, medicoId, usuarioId, dataHora, motivo);
    }

    public void confirmarAgendamento(long id) { agendamentoService.confirmarAgendamento(id); }

    public void cancelarAgendamento(long id, String motivo) { agendamentoService.cancelarAgendamento(id, motivo); }

    public void realizarConsulta(long id, String obs) { agendamentoService.realizarConsulta(id, obs); }

    public List<Agendamento> listarAgendamentos() { return agendamentoService.listarTodos(); }

    public List<Agendamento> listarAgendamentosPorData(String data) { return agendamentoService.listarPorData(data); }

    public List<Agendamento> listarAgendamentosPorPaciente(long pacienteId) {
        return agendamentoService.listarPorPaciente(pacienteId);
    }

    public Optional<Agendamento> buscarAgendamentoPorId(long id) { return agendamentoService.buscarPorId(id); }
}
