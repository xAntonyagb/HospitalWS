package br.unipar.hospitalws.services;

import br.unipar.hospitalws.DTO.ConsultaDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.ConsultaModel;
import br.unipar.hospitalws.models.MedicoModel;
import br.unipar.hospitalws.models.PacienteModel;
import br.unipar.hospitalws.repositories.ConsultaRepository;
import br.unipar.hospitalws.repositories.MedicoRepository;
import br.unipar.hospitalws.repositories.PacienteRepository;
import br.unipar.hospitalws.utils.DateFormatterUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class ConsultaService {
    
    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private Connection connection = null;
    private ConsultaRepository consultaRepository = null;
    private MedicoRepository medicoRepository = null;
    private PacienteRepository pacienteRepository = null;
    
    private static final int HORA_ABERTURA = 7;
    private static final int HORA_FECHAMENTO = 19;

    public ConsultaService() {
        try {
            this.connection = connectionFactory.getConnection();
            this.consultaRepository = new ConsultaRepository(this.connection);
            this.medicoRepository = new MedicoRepository(this.connection);
            this.pacienteRepository = new PacienteRepository(this.connection);
        } catch (SQLException ex) {
            connectionFactory.closeConnection(connection);
            throw new DataBaseException(ex.getMessage());
        }
    }
    
    //Metodos padrões
    public ConsultaDTO insertConsulta(ConsultaDTO consulta) {
        try {
            if(this.connection == null)
                this.connection = this.connectionFactory.getConnection();
            
            ConsultaModel consultaModel = validaConsulta(ajustaConsulta(consulta));
            consultaModel = this.consultaRepository.insertConsulta(consultaModel);
            connectionFactory.commit(this.connection);

            return ConsultaDTO.consultaDTOMapper(consultaModel);
        }
        catch(DataBaseException ex) {
            this.connectionFactory.rollback(this.connection);
            throw ex;
        }
        finally {
            this.connectionFactory.closeConnection(this.connection);
        }
    }

    public ConsultaDTO getConsultaById(int id) {
        try {
            if(this.connection == null)
                this.connection = this.connectionFactory.getConnection();

            ConsultaModel retorno = this.consultaRepository.getConsultaById(id);
            connectionFactory.commit(this.connection);
            return ConsultaDTO.consultaDTOMapper(retorno);
        }
        catch(DataBaseException ex) {
            this.connectionFactory.rollback(this.connection);
            throw ex;
        }
        finally {
            this.connectionFactory.closeConnection(this.connection);
        }
    }

    public ArrayList<ConsultaDTO> getAllConsultas() {
        try{
            if(this.connection == null)
                this.connection = this.connectionFactory.getConnection();

            ArrayList<ConsultaDTO> retorno = new ArrayList<>();
            ArrayList<ConsultaModel> consulta = this.consultaRepository.getAllConsultas();
            this.connectionFactory.commit(this.connection);

            for(ConsultaModel consultaModel : consulta) {
                    retorno.add(ConsultaDTO.consultaDTOMapper(consultaModel));
            }

            return retorno;
        }
        catch(DataBaseException ex) {
            this.connectionFactory.rollback(this.connection);
            throw ex;
        }
        finally {
            this.connectionFactory.closeConnection(this.connection);
        }
    }

    public ConsultaDTO updateConsulta(ConsultaDTO consulta) {
        try{
            if(this.connection == null)
                this.connection = this.connectionFactory.getConnection();

            ConsultaModel consultaModel = validaConsulta(ajustaConsulta(consulta));
            consultaModel = this.consultaRepository.updateConsulta(consultaModel);
            this.connectionFactory.commit(this.connection);

            return ConsultaDTO.consultaDTOMapper(consultaModel);
        }
        catch(DataBaseException ex) {
            this.connectionFactory.rollback(this.connection);
            throw ex;
        }
        finally {
            this.connectionFactory.closeConnection(this.connection);
        }
    }

    public ConsultaDTO cancelarConsulta(ConsultaDTO consulta) {
        try {
            if(this.connection == null)
                this.connection = this.connectionFactory.getConnection();

            ConsultaModel consultaModel = ajustaConsulta(consulta);
            validaCancelamento(consultaModel);
            
            boolean retorno = this.consultaRepository.cancelarConsulta(consultaModel);
            this.connectionFactory.commit(this.connection);
            
            if(retorno)
                throw new DataBaseException("Erro ao cancelar: Não foi possivel encontrar essa consulta!");

            return ConsultaDTO.consultaDTOMapper(consultaModel);
        }
        catch(DataBaseException ex) {
            this.connectionFactory.rollback(this.connection);
            throw ex;
        }
        finally {
            this.connectionFactory.closeConnection(this.connection);
        }
    }
    
    
    
    //Validações
    private ConsultaModel validaConsulta(ConsultaModel consulta) {
        MedicoModel medico = this.medicoRepository.getMedicoById(consulta.getMedico().getIdMedico());
        PacienteModel paciente = this.pacienteRepository.getPacienteById(consulta.getPaciente().getIdPaciente());
        LocalDateTime dataHoraConsulta = DateFormatterUtil.toLocalDate(consulta.getHorarioConsulta());

        if(consulta.getHorarioConsulta() == null) {
            throw new ValidationException("Erro ao marcar consulta: Nenhum horário de consulta foi informado!");
        }
        if(paciente == null) {
           throw new ValidationException("Erro ao marcar consulta: O paciente informado não é válido!");
        }
        if(medico == null) {
            medico = getMedicoAleatrio(dataHoraConsulta);
            consulta.setMedico(medico);
        }
        consulta.setPaciente(paciente);
        
        isClinicaAberta(dataHoraConsulta);
        isAgendamentoComAntecedencia(dataHoraConsulta);
        isPacienteAtivo(consulta.getPaciente().getIdPaciente());
        isMedicoAtivo(consulta.getMedico().getIdMedico());
        verificaDisponibilidade(consulta);
        
        return consulta;
    }
    
    public void validaCancelamento(ConsultaModel consulta) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime horarioConsulta = consulta.getHorarioConsulta().toLocalDateTime();
        LocalDateTime limiteCancelamento = horarioConsulta.minusHours(24);

        if (agora.isAfter(limiteCancelamento)) {
            throw new ValidationException("Erro ao cancelar a consulta: Uma consulta somente poderá ser cancelada com antecedência mínima de 24 horas");
        }
        if(consulta.getMotivoCancelamento() == null) {
            throw new ValidationException("Erro ao cancelar a consulta: Porfavor informe um motivo de cancelamento válido");
        }
    }
    
    private ConsultaModel ajustaConsulta(ConsultaDTO consultaDTO) {
        return ConsultaModel.consultaModelMapper(consultaDTO);
    }
    
    private boolean isClinicaAberta(LocalDateTime dataHoraConsulta) {
        DayOfWeek diaSemana = dataHoraConsulta.getDayOfWeek();
        int hora = dataHoraConsulta.getHour();

        if (diaSemana.getValue() >= DayOfWeek.MONDAY.getValue() //Se o dia da semana é maior ou igual a segunda
                && diaSemana.getValue() <= DayOfWeek.SATURDAY.getValue() //Se o dia da semana menor ou igual a sábado
                && hora >= HORA_ABERTURA // Se a hora é maior ou igual a hora de abertura
                && hora < HORA_FECHAMENTO) { //Se a hora é menor do que a hora de fechamento
            
            return true;
        }
        
        throw new ValidationException("Erro ao marcar consulta: A clínica não estará em horário de funcionamento no horário informado!");
    }
    
    private boolean isAgendamentoComAntecedencia(LocalDateTime dataHoraConsulta) {
        LocalDateTime horarioComAntecedencia = LocalDateTime.now().minusMinutes(30);
        
        if(dataHoraConsulta.isBefore(horarioComAntecedencia)) {
            return true;
        } 
        
        throw new ValidationException("Erro ao marcar consulta: Você não pode marcar uma consulta com menos de 30 minutos de antecedencia!");
    } 
    
    private boolean isPacienteAtivo(int id) {
        if(this.pacienteRepository.isPacienteAtivo(id)) {
            return true;
        }
        
        throw new ValidationException("Erro ao marcar consulta: Você não pode marcar uma consulta com um cadastro de paciente inativo!");
    } 
    
    private boolean isMedicoAtivo(int id) {
        if(this.medicoRepository.isMedicoAtivo(id)) {
            return true;
        }
        
        throw new ValidationException("Erro ao marcar consulta: Você não pode marcar uma consulta com um cadastro de médico inativo!");
    }
    
    private boolean isMedicoDisponivel(ConsultaModel consulta) {
        LocalDateTime horario = DateFormatterUtil.toLocalDate(consulta.getHorarioConsulta());
        LocalDateTime inicioConsulta = null;
        LocalDateTime fimConsulta = null;

        LocalDateTime retornoMedico = DateFormatterUtil.toLocalDate(
                this.consultaRepository.getHoraConsultaByIdMedico(
                        consulta.getMedico().getIdMedico()
                ));

        if (retornoMedico == null) { // se não houver agendamento
            return true;
        } else { // Verificar o horário se houver
            inicioConsulta = retornoMedico;
            fimConsulta = retornoMedico.plusHours(1);

            return horario.isBefore(inicioConsulta) || horario.isAfter(fimConsulta);
        }
    }
    
    private boolean isPacienteDisponivel(ConsultaModel consulta) {
        LocalDateTime horario = DateFormatterUtil.toLocalDate(consulta.getHorarioConsulta());
        
        LocalDateTime retornoPaciente = DateFormatterUtil.toLocalDate(
                this.consultaRepository.getHoraConsultaByIdPaciente(
                        consulta.getPaciente().getIdPaciente()
                ));

        if (retornoPaciente == null) { // se não houver agendamento
            return true;
        } else { // Verificar se o paciente tem consulta hoje
            boolean isConsultaHoje = horario.getDayOfMonth() == retornoPaciente.getDayOfMonth();
            return isConsultaHoje ? false : true;
        }
    }
    
    private boolean verificaDisponibilidade(ConsultaModel consulta) {
        if(!isMedicoDisponivel(consulta))
            throw new ValidationException("Erro ao marcar consulta: Esse médico já possui uma consulta marcada hoje. Tente marcar em outro horário!");
            
        if(!isPacienteDisponivel(consulta))
            throw new ValidationException("Erro ao marcar consulta: Esse paciente já possui o máximo de uma consulta marcada hoje!");
    
        return true;
    }
    
    private MedicoModel getMedicoAleatrio(LocalDateTime horaConsulta) { 
        ArrayList<MedicoModel> listMedicos = this.consultaRepository.getMedicosDisponiveis(horaConsulta);
        Random random = new Random();
        
        int posicao = random.nextInt(listMedicos.size());
        MedicoModel medicoRandom = listMedicos.get(posicao);

        return medicoRandom;
    }
    
}
