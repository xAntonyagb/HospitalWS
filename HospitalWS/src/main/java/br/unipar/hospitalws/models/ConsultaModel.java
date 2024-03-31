package br.unipar.hospitalws.models;

import br.unipar.hospitalws.DTO.ConsultaDTO;
import br.unipar.hospitalws.enums.MotivoCancelamentoEnum;
import br.unipar.hospitalws.utils.DateFormatterUtil;
import java.sql.Timestamp;

public class ConsultaModel {
    private PacienteModel paciente;
    private MedicoModel medico;
    private Timestamp horarioConsulta;
    private MotivoCancelamentoEnum motivoCancelamento;
    private int idMotivoCancelamento;
    private boolean isCancelada;
    private int idConsulta;

    public ConsultaModel() { }

    public ConsultaModel(PacienteModel paciente, MedicoModel medico, Timestamp horarioConsulta, MotivoCancelamentoEnum motivoCancelamento, boolean isCancelada, int id, int idConsulta) {
        this.paciente = paciente;
        this.medico = medico;
        this.horarioConsulta = horarioConsulta;
        this.motivoCancelamento = motivoCancelamento;
        this.isCancelada = isCancelada;
        this.idConsulta = idConsulta;
    }
    
    public static ConsultaModel consultaModelMapper(ConsultaDTO consultaDTO) {
         if (consultaDTO == null) {
            return null;
        }
         
        ConsultaModel consultaModel = new ConsultaModel();
        consultaModel.getMedico().setIdMedico(consultaDTO.getIdMedico());
        consultaModel.getPaciente().setIdPaciente(consultaDTO.getIdPaciente());
        consultaModel.setHorarioConsulta(DateFormatterUtil.toTimestamp(consultaDTO.getHorarioConsulta()));
        consultaModel.setMotivoCancelamento(consultaDTO.getMotivoCancelamento());
        consultaModel.setIdConsulta(consultaDTO.getId());
        
        return consultaModel;
    }
    
    //Getters e setters
    public PacienteModel getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteModel paciente) {
        this.paciente = paciente;
    }

    public MedicoModel getMedico() {
        return medico;
    }

    public void setMedico(MedicoModel medico) {
        this.medico = medico;
    }

    public Timestamp getHorarioConsulta() {
        return horarioConsulta;
    }

    public void setHorarioConsulta(Timestamp horarioConsulta) {
        this.horarioConsulta = horarioConsulta;
    }

    public MotivoCancelamentoEnum getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(MotivoCancelamentoEnum motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }

    public boolean isIsCancelada() {
        return isCancelada;
    }

    public void setIsCancelada(boolean isCancelada) {
        this.isCancelada = isCancelada;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public int getIdMotivoCancelamento() {
        return idMotivoCancelamento;
    }

    public void setIdMotivoCancelamento(int idMotivoCancelamento) {
        this.idMotivoCancelamento = idMotivoCancelamento;
    }

    @Override
    public String toString() {
        return "ConsultaModel{" + "paciente=" + paciente + ", medico=" + medico + ", horarioConsulta=" + horarioConsulta + ", motivoCancelamento=" + motivoCancelamento + ", idMotivoCancelamento=" + idMotivoCancelamento + ", isCancelada=" + isCancelada + ", idConsulta=" + idConsulta + '}';
    }


}
