package com.agendamento.agendamento.controller;

import com.agendamento.ServiceFacade;
import com.agendamento.agendamento.entity.Agendamento;
import com.agendamento.paciente.entity.Paciente;
import com.agendamento.medico.entity.Medico;
import com.agendamento.compartilhado.persistence.DatabaseConnection;

import java.util.*;

public class Main {

    private static final ServiceFacade svc = ServiceFacade.getInstance();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseConnection.inicializar();
        System.out.println("Bem-vindo ao Sistema de Agendamento de Pacientes.");
        System.out.println("Aqui você pode gerenciar pacientes, médicos, agendamentos e usuários.");
        System.out.println("O sistema usa SQLite para armazenar os dados localmente.");
        System.out.println();

        popularDadosIniciais();

        boolean rodando = true;
        while (rodando) {
            exibirMenuPrincipal();
            String opcao = sc.nextLine().trim();
            try {
                rodando = processarOpcao(opcao);
            } catch (Exception e) {
                System.out.println("✖ Erro: " + e.getMessage());
            }
        }

        try { DatabaseConnection.close(); } catch (Exception ignored) {}
        System.out.println("\nSistema encerrado. Até logo!");
    }

    private static void exibirMenuPrincipal() {
        System.out.println(" MENU PRINCIPAL");
        System.out.println(" [1] Gerenciar Pacientes");
        System.out.println(" [2] Gerenciar Médicos");
        System.out.println(" [3] Gerenciar Agendamentos");
        System.out.println(" [4] Gerenciar Usuários");
        System.out.println(" [5] Informações das Entidades");
        System.out.println(" [0] Sair");
        System.out.print("Opção: ");
    }

    private static boolean processarOpcao(String opcao) {
        return switch (opcao) {
            case "1" -> { menuPacientes(); yield true; }
            case "2" -> { menuMedicos();   yield true; }
            case "3" -> { menuAgendamentos(); yield true; }
            case "4" -> { menuUsuarios();  yield true; }
            case "5" -> { demoReflection(); yield true; }
            case "0" -> false;
            default  -> { System.out.println("Opção inválida."); yield true; }
        };
    }

    private static void menuPacientes() {
        System.out.println("\n── PACIENTES ──");
        System.out.println("[1] Cadastrar  [2] Listar  [3] Buscar por Nome  [4] Inativar  [0] Voltar");
        System.out.print("Opção: ");
        switch (sc.nextLine().trim()) {
            case "1" -> {
                System.out.print("Nome: "); String nome = sc.nextLine();
                System.out.print("CPF (somente números): "); String cpf = sc.nextLine();
                System.out.print("Data Nascimento (YYYY-MM-DD): "); String dn = sc.nextLine();
                System.out.print("Telefone: "); String tel = sc.nextLine();
                System.out.print("Email: "); String email = sc.nextLine();
                svc.cadastrarPaciente(nome, cpf, dn, tel, email);
            }
            case "2" -> svc.listarPacientes().forEach(System.out::println);
            case "3" -> {
                System.out.print("Nome: "); String nome = sc.nextLine();
                svc.buscarPacientesPorNome(nome).forEach(System.out::println);
            }
            case "4" -> {
                System.out.print("ID do paciente: "); long id = Long.parseLong(sc.nextLine());
                svc.inativarPaciente(id);
            }
        }
    }

    private static void menuMedicos() {
        System.out.println("\n── MÉDICOS ──");
        System.out.println("[1] Cadastrar  [2] Listar  [3] Por Especialidade  [4] Inativar  [0] Voltar");
        System.out.print("Opção: ");
        switch (sc.nextLine().trim()) {
            case "1" -> {
                System.out.print("Nome: "); String nome = sc.nextLine();
                System.out.print("CRM: "); String crm = sc.nextLine();
                System.out.print("Especialidade: "); String esp = sc.nextLine();
                System.out.print("Telefone: "); String tel = sc.nextLine();
                System.out.print("Email: "); String email = sc.nextLine();
                svc.cadastrarMedico(nome, crm, esp, tel, email);
            }
            case "2" -> svc.listarMedicos().forEach(System.out::println);
            case "3" -> {
                System.out.print("Especialidade: "); String esp = sc.nextLine();
                svc.buscarMedicosPorEspecialidade(esp).forEach(System.out::println);
            }
            case "4" -> {
                System.out.print("ID do médico: "); long id = Long.parseLong(sc.nextLine());
                svc.inativarMedico(id);
            }
        }
    }

    private static void menuAgendamentos() {
        System.out.println("\n── AGENDAMENTOS ──");
        System.out.println("[1] Novo  [2] Listar Todos  [3] Por Data  [4] Por Paciente");
        System.out.println("[5] Confirmar  [6] Cancelar  [7] Realizar  [0] Voltar");
        System.out.print("Opção: ");
        switch (sc.nextLine().trim()) {
            case "1" -> {
                System.out.print("ID Paciente: "); long pid = Long.parseLong(sc.nextLine());
                System.out.print("ID Médico: "); long mid = Long.parseLong(sc.nextLine());
                System.out.print("Data/Hora (YYYY-MM-DDTHH:MM): "); String dh = sc.nextLine();
                System.out.print("Motivo: "); String motivo = sc.nextLine();
                svc.criarAgendamento(pid, mid, 1L, dh, motivo);
            }
            case "2" -> {
                List<Agendamento> todos = svc.listarAgendamentos();
                if (todos.isEmpty()) { System.out.println("Nenhum agendamento."); break; }
                todos.forEach(a -> {
                    Optional<Paciente> p = svc.buscarPacientePorId(a.getPacienteId());
                    Optional<Medico> m = svc.buscarMedicoPorId(a.getMedicoId());
                    System.out.printf("  [%d] %s | Paciente: %s | Médico: %s | Status: %s%n",
                            a.getId(), a.getDataHora(),
                            p.map(Paciente::getNome).orElse("?"),
                            m.map(Medico::getNome).orElse("?"),
                            a.getStatus());
                });
            }
            case "3" -> {
                System.out.print("Data (YYYY-MM-DD): "); String data = sc.nextLine();
                svc.listarAgendamentosPorData(data).forEach(System.out::println);
            }
            case "4" -> {
                System.out.print("ID Paciente: "); long pid = Long.parseLong(sc.nextLine());
                svc.listarAgendamentosPorPaciente(pid).forEach(System.out::println);
            }
            case "5" -> {
                System.out.print("ID Agendamento: "); long id = Long.parseLong(sc.nextLine());
                svc.confirmarAgendamento(id);
            }
            case "6" -> {
                System.out.print("ID Agendamento: "); long id = Long.parseLong(sc.nextLine());
                System.out.print("Motivo do cancelamento: "); String motivo = sc.nextLine();
                svc.cancelarAgendamento(id, motivo);
            }
            case "7" -> {
                System.out.print("ID Agendamento: "); long id = Long.parseLong(sc.nextLine());
                System.out.print("Observações da consulta: "); String obs = sc.nextLine();
                svc.realizarConsulta(id, obs);
            }
        }
    }

    private static void menuUsuarios() {
        System.out.println("\n── USUÁRIOS ──");
        System.out.println("[1] Cadastrar  [2] Listar  [3] Autenticar  [0] Voltar");
        System.out.print("Opção: ");
        switch (sc.nextLine().trim()) {
            case "1" -> {
                System.out.print("Nome: "); String nome = sc.nextLine();
                System.out.print("Email: "); String email = sc.nextLine();
                System.out.print("Senha: "); String senha = sc.nextLine();
                System.out.print("Perfil (ADMIN/RECEPCIONISTA): "); String perfil = sc.nextLine();
                svc.cadastrarUsuario(nome, email, senha, perfil);
            }
            case "2" -> svc.listarUsuarios().forEach(System.out::println);
            case "3" -> {
                System.out.print("Email: "); String email = sc.nextLine();
                System.out.print("Senha: "); String senha = sc.nextLine();
                svc.autenticar(email, senha)
                        .ifPresentOrElse(
                                u -> System.out.println("✔ Autenticado: " + u),
                                () -> System.out.println("✖ Credenciais inválidas."));
            }
        }
    }

    private static void demoReflection() {
        System.out.println("\n╔══ DEMO: INFORMAÇÕES DAS ENTIDADES ══╗");
        System.out.println("Este demo foi simplificado para mostrar as entidades do sistema:");
        System.out.println("  - Paciente");
        System.out.println("  - Médico");
        System.out.println("  - Agendamento");
        System.out.println("  - Usuário");
        System.out.println("(A demonstração avançada por reflection foi removida para simplificar.)");
    }

    private static void popularDadosIniciais() {
        try {
            if (!svc.listarUsuarios().isEmpty()) return;

            System.out.println("\n→ Populando dados de exemplo...");

            svc.cadastrarUsuario("Admin", "admin@clinica.com", "123456", "ADMIN");

            Medico m1 = svc.cadastrarMedico("Dra. Ana Souza",  "CRM-12345", "Cardiologia", "(11)99001-0001", "ana@clinica.com");
            Medico m2 = svc.cadastrarMedico("Dr. Bruno Lima",  "CRM-67890", "Clínica Geral", "(11)99001-0002", "bruno@clinica.com");
            Medico m3 = svc.cadastrarMedico("Dra. Carla Reis", "CRM-11111", "Pediatria", "(11)99001-0003", "carla@clinica.com");

            Paciente p1 = svc.cadastrarPaciente("João da Silva",  "12345678901", "1985-03-15", "(11)98001-0001", "joao@email.com");
            Paciente p2 = svc.cadastrarPaciente("Maria Oliveira", "98765432100", "1992-07-22", "(11)98001-0002", "maria@email.com");
            Paciente p3 = svc.cadastrarPaciente("Carlos Mendes",  "45678912300", "1978-11-30", "(11)98001-0003", "carlos@email.com");

            Agendamento ag1 = svc.criarAgendamento(p1.getId(), m1.getId(), 1L, "2026-06-10T09:00", "Consulta de rotina");
            svc.criarAgendamento(p3.getId(), m3.getId(), 1L, "2026-06-11T14:00", "Check-up anual");

            svc.confirmarAgendamento(ag1.getId());

            System.out.println("✔ Dados de exemplo criados com sucesso!\n");
        } catch (Exception e) {
            System.out.println("(Dados já existentes, pulando população inicial)");
        }
    }
}
