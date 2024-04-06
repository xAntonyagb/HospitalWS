package br.unipar.hospitalws.DTO;

import br.unipar.hospitalws.enums.MotivoCancelamentoEnum;
import br.unipar.hospitalws.models.ConsultaModel;

public class ConsultaDTO {
    private int idMedico;
    private int idPaciente;
    private String horarioConsulta;
    private MotivoCancelamentoEnum motivoCancelamento;
    private int id;

    public ConsultaDTO() { }

    public ConsultaDTO(int idMedico, int idPaciente, String horarioConsulta, MotivoCancelamentoEnum motivoCancelamento, int id) {
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.horarioConsulta = horarioConsulta;
        this.motivoCancelamento = motivoCancelamento;
        this.id = id;
    }

    public static ConsultaDTO consultaDTOMapper(ConsultaModel consultaModel) {
        if (consultaModel == null) {
            return null;
        }
        
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setIdMedico(consultaModel.getMedico().getIdMedico());
        consultaDTO.setIdPaciente(consultaModel.getPaciente().getIdPaciente());
        consultaDTO.setHorarioConsulta(consultaModel.getHorarioConsulta().toString()); //teste
        consultaDTO.setMotivoCancelamento(consultaModel.getMotivoCancelamento());
        consultaDTO.setId(consultaModel.getIdConsulta());
        
        return consultaDTO;
    }
    
    //Getters e setters
    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getHorarioConsulta() {
        return horarioConsulta;
    }

    public void setHorarioConsulta(String  horarioConsulta) {
        this.horarioConsulta = horarioConsulta;
    }

    public MotivoCancelamentoEnum getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(MotivoCancelamentoEnum motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ConsultaDTO{" + "idMedico=" + idMedico + ", idPaciente=" + idPaciente + ", horarioConsulta=" + horarioConsulta + ", motivoCancelamento=" + motivoCancelamento + ", id=" + id + '}';
    }

}
