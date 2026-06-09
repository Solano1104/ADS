package com.agendamento.medico.entity;

public class Medico {

    private long id;
    private String nome;
    private String crm;
    private String especialidade;
    private String telefone;
    private String email;
    private boolean disponivel = true;
    private boolean ativo = true;

    public Medico() {}

    public Medico(String nome, String crm, String especialidade, String telefone, String email) {
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
        this.telefone = telefone;
        this.email = email;
        this.disponivel = true;
        this.ativo = true;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public void inativar() { this.ativo = false; }

    @Override
    public String toString() {
        return String.format("Medico[id=%d, nome='%s', crm='%s', esp='%s', ativo=%b]",
                id, nome, crm, especialidade, ativo);
    }
}
