package com.agendamento.agendamento.entity;

public class Agendamento {

    public enum Status { PENDENTE, CONFIRMADO, CANCELADO, REALIZADO }

    private long id;
    private long pacienteId;
    private long medicoId;
    private long usuarioId;
    private String dataHora;
    private String motivo;
    private String status = Status.PENDENTE.name();
    private String observacoes;

    public Agendamento() {}

    public Agendamento(long pacienteId, long medicoId, long usuarioId, String dataHora, String motivo) {
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.usuarioId = usuarioId;
        this.dataHora = dataHora;
        this.motivo = motivo;
        this.status = Status.PENDENTE.name();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getPacienteId() { return pacienteId; }
    public void setPacienteId(long pacienteId) { this.pacienteId = pacienteId; }

    public long getMedicoId() { return medicoId; }
    public void setMedicoId(long medicoId) { this.medicoId = medicoId; }

    public long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(long usuarioId) { this.usuarioId = usuarioId; }

    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void confirmar() { this.status = Status.CONFIRMADO.name(); }
    public void cancelar(String motivo) { this.status = Status.CANCELADO.name(); this.observacoes = motivo; }
    public void realizar(String observacoes) { this.status = Status.REALIZADO.name(); this.observacoes = observacoes; }

    @Override
    public String toString() {
        return String.format("Agendamento[id=%d, dataHora='%s', paciente=%d, medico=%d, status=%s]",
                id, dataHora, pacienteId, medicoId, status);
    }
}
