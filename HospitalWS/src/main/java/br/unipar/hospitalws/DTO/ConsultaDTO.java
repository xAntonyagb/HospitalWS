package br.unipar.hospitalws.DTO;

import br.unipar.hospitalws.enums.MotivoCancelamentoEnum;
import br.unipar.hospitalws.models.ConsultaModel;
import br.unipar.hospitalws.utils.DateFormatterUtil;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class ConsultaDTO {
    private int idMedico;
    private int idPaciente;
    private Date horarioConsulta;
    private MotivoCancelamentoEnum motivoCancelamento;
    private int id;

    public ConsultaDTO() { }

    public ConsultaDTO(int idMedico, int idPaciente, Date horarioConsulta, MotivoCancelamentoEnum motivoCancelamento, int id) {
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
        //consultaDTO.setHorarioConsulta(DateFormatterUtil.toLocalDate(consultaModel.getHorarioConsulta()));
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

    public Date  getHorarioConsulta() {
        return horarioConsulta;
    }

    public void setHorarioConsulta(Date  horarioConsulta) {
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
