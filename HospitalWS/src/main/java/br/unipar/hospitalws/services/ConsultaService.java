package br.unipar.hospitalws.services;

import br.unipar.hospitalws.DTO.ConsultaDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.FormattingException;
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
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaService {

    private final Logger logger = Logger.getLogger("ConsultaService");
    
    private Connection connection = null;
    private ConsultaRepository consultaRepository = null;
    private MedicoRepository medicoRepository = null;
    private PacienteRepository pacienteRepository = null;
    
    private static final int HORA_ABERTURA = 7;
    private static final int HORA_FECHAMENTO = 19;

    //Metodos padrões
    public ConsultaDTO insertConsulta(ConsultaDTO consulta) {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.consultaRepository = new ConsultaRepository();
            
            ConsultaModel consultaModel = validaConsulta(ajustaConsulta(consulta));
            
            consultaModel.setIdConsulta(this.consultaRepository.insertConsulta(consultaModel));
            ConnectionFactory.commit();

            return ConsultaDTO.consultaDTOMapper(consultaModel);
        }
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(insertConsulta) "+ ex.getMessage());
            throw new DataBaseException("erro ao inserir uma nova consulta.");
        }
        catch(FormattingException ex) {
            logger.log(Level.SEVERE, "(insertConsulta) "+ ex.getMessage());
            throw new ValidationException("Erro ao inserir a consulta: por favor informe uma data válida!");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }

    public ConsultaDTO getConsultaById(int id) {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.consultaRepository = new ConsultaRepository();

            ConsultaModel retorno = this.consultaRepository.getConsultaById(id);
            ConnectionFactory.commit();
            
            return ConsultaDTO.consultaDTOMapper(retorno);
        }
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(getConsultaById) "+ ex.getMessage());
            throw new DataBaseException("erro ao pesquisar pela consulta.");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }

    public List<ConsultaDTO> getAllConsultas() {
        try{
            this.connection = ConnectionFactory.getConnection();
            this.consultaRepository = new ConsultaRepository();

            List<ConsultaDTO> retorno = new ArrayList<>();
            List<ConsultaModel> consulta = this.consultaRepository.getAllConsultas();
            ConnectionFactory.commit();

            for(ConsultaModel consultaModel : consulta) {
                retorno.add(ConsultaDTO.consultaDTOMapper(consultaModel));
            }

            return retorno;
        }
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(getAllConsultas) "+ ex.getMessage());
            throw new DataBaseException("erro ao pesquisar pelas consultas.");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }

    public ConsultaDTO updateConsulta(ConsultaDTO consulta) {
        try{
            this.connection = ConnectionFactory.getConnection();
            this.consultaRepository = new ConsultaRepository();

            ConsultaModel consultaModel = validaConsulta(ajustaConsulta(consulta));
            consultaModel = this.consultaRepository.updateConsulta(consultaModel);
            ConnectionFactory.commit();

            return ConsultaDTO.consultaDTOMapper(consultaModel);
        }
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(updateConsulta) "+ ex.getMessage());
            throw new DataBaseException("erro ao atualizar a consulta.");
        }
        catch(FormattingException ex) {
            logger.log(Level.SEVERE, "(updateConsulta) "+ ex.getMessage());
            throw new ValidationException("Erro ao atualizar a consulta: por favor informe uma data válida!");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }

    public ConsultaDTO cancelarConsulta(ConsultaDTO consulta) {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.consultaRepository = new ConsultaRepository();

            ConsultaModel consultaModel = ajustaConsulta(consulta);
            validaCancelamento(consultaModel);
            
            boolean retorno = this.consultaRepository.cancelarConsulta(consultaModel);
            ConnectionFactory.commit();
            
            if(retorno)
                throw new ValidationException("Erro ao cancelar consulta: não foi possivel encontrar essa consulta!");

            return ConsultaDTO.consultaDTOMapper(consultaModel);
        }
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(cancelarConsulta) "+ ex.getMessage());
            throw new DataBaseException("erro ao cancelar a consulta.");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    
    
    //Validações
    private ConsultaModel validaConsulta(ConsultaModel consulta) throws SQLException {
        this.medicoRepository = new MedicoRepository();
        this.pacienteRepository = new PacienteRepository();
        
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
    
    public void validaCancelamento(ConsultaModel consulta) throws SQLException {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime horarioConsulta;
        
        try {
            horarioConsulta = DateFormatterUtil.toLocalDate(
                    this.consultaRepository.getConsultaById(consulta.getIdConsulta())
                            .getHorarioConsulta()
            );
        } catch(Exception ex) {
            throw new ValidationException("Erro ao cancelar a consulta: Não foi possivel encontrar essa consulta");
        }
        
        LocalDateTime limiteCancelamento = horarioConsulta.minusHours(24);

        if (agora.isAfter(limiteCancelamento)) {
            throw new ValidationException("Erro ao cancelar a consulta: Uma consulta somente poderá ser cancelada com antecedência mínima de 24 horas");
        }
        if(consulta.getMotivoCancelamento() == null) {
            throw new ValidationException("Erro ao cancelar a consulta: Por favor informe um motivo de cancelamento válido");
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
        
        if(horarioComAntecedencia.isBefore(dataHoraConsulta)) {
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
    
    private boolean isMedicoDisponivel(ConsultaModel consulta) throws SQLException {
        LocalDateTime horario = DateFormatterUtil.toLocalDate(consulta.getHorarioConsulta());
        LocalDateTime inicioConsulta = null;
        LocalDateTime fimConsulta = null;

        Timestamp retornoMedico = this.consultaRepository.getHoraConsultaByIdMedico(consulta.getMedico().getIdMedico());

        if (retornoMedico == null) { // se não houver agendamento
            return true;
        } else { // Verificar o horário se houver
            inicioConsulta = DateFormatterUtil.toLocalDate(retornoMedico);
            fimConsulta = DateFormatterUtil.toLocalDate(retornoMedico).plusHours(1);

            return horario.isBefore(inicioConsulta) || horario.isAfter(fimConsulta);
        }
    }
    
    private boolean isPacienteDisponivel(ConsultaModel consulta) throws SQLException {
        LocalDateTime horario = DateFormatterUtil.toLocalDate(consulta.getHorarioConsulta());
        
        Timestamp retornoPaciente = this.consultaRepository.getHoraConsultaByIdPaciente(consulta.getPaciente().getIdPaciente());

        if (retornoPaciente == null) { // se não houver agendamento
            return true;
        } else { // Verificar se o paciente tem consulta hoje
            boolean isConsultaHoje = horario.getDayOfMonth() == DateFormatterUtil.toLocalDate(retornoPaciente).getDayOfMonth();
            return isConsultaHoje ? false : true;
        }
    }
    
    private boolean verificaDisponibilidade(ConsultaModel consulta) throws SQLException {
        if(!isMedicoDisponivel(consulta))
            throw new ValidationException("Erro ao marcar consulta: Esse médico já possui uma consulta marcada nesse horário. Tente marcar em outro horário!");
            
        if(!isPacienteDisponivel(consulta))
            throw new ValidationException("Erro ao marcar consulta: Esse paciente já possui o máximo de uma consulta marcada hoje!");
    
        return true;
    }
    
    private MedicoModel getMedicoAleatrio(LocalDateTime horaConsulta) throws SQLException { 
        ArrayList<MedicoModel> listMedicos = this.consultaRepository.getMedicosDisponiveis(horaConsulta);
        Random random = new Random();
        
        int posicao = random.nextInt(listMedicos.size());
        MedicoModel medicoRandom = listMedicos.get(posicao);

        return medicoRandom;
    }
    
}
