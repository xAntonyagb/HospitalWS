package br.unipar.hospitalws.services;

import br.unipar.hospitalws.DTO.ConsultaDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.DataBasePessoaException;
import br.unipar.hospitalws.exceptions.FormattingException;
import br.unipar.hospitalws.exceptions.InternalException;
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
            throw new ValidationException("Erro ao inserir a consulta: Por favor informe uma data valida! (dd/MM/yyyy hh:mm:ss)");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(insertConsulta) Requisicao foi rejeitada pelo processo de validacao "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(insertConsulta) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("cancelarConsulta");
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
            
            if(retorno == null)
                throw new ValidationException("Erro ao pesquisar consulta: Não foi encontrado nenhum registro dessa consulta!");
            
            return ConsultaDTO.consultaDTOMapper(retorno);
        }
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(getConsultaById) "+ ex.getMessage());
            throw new DataBaseException("erro ao pesquisar pela consulta.");
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(getConsultaById) Um erro inesperado aconteceu: Nao foi possivel finalizar a execucao desse metodo. "+ ex.getMessage());
            throw new InternalException("cancelarConsulta");
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
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(getAllConsultas) Um erro inesperado aconteceu: Nao foi possivel finalizar a execucao desse metodo. "+ ex.getMessage());
            throw new InternalException("cancelarConsulta");
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
            throw new ValidationException("Erro ao atualizar a consulta: Por favor informe uma data valida! (dd/MM/yyyy hh:mm:ss)");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(updateConsulta) Requisição foi rejeitada pelo processo de validação "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(updateConsulta) Um erro inesperado aconteceu: Nao foi possivel finalizar a execucao desse metodo. "+ ex.getMessage());
            throw new InternalException("updateConsulta");
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
            boolean isCancelada = this.consultaRepository.cancelarConsulta(consultaModel);
            
            if(!isCancelada)
                throw new ValidationException("Erro ao cancelar consulta: Essa consulta nao foi agendada ou ja foi cancelada!");

            consultaModel = this.consultaRepository.getConsultaById(consulta.getId());
            ConnectionFactory.commit();
            
            return ConsultaDTO.consultaDTOMapper(consultaModel);
        }
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(cancelarConsulta) "+ ex.getMessage());
            throw new DataBaseException("erro ao cancelar a consulta.");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(cancelarConsulta) Requisição foi rejeitada pelo processo de validação "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(cancelarConsulta) Um erro inesperado aconteceu: Nao foi possivel finalizar a execucao desse metodo. "+ ex.getMessage());
            throw new InternalException("cancelarConsulta");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    
    
    //Validações
    private ConsultaModel validaConsulta(ConsultaModel consulta) throws SQLException, DataBasePessoaException {
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
        ConsultaModel retorno = this.consultaRepository.getConsultaById(consulta.getIdConsulta());
        LocalDateTime horarioConsulta;
        
        if(retorno != null) {
            horarioConsulta = DateFormatterUtil.toLocalDate(retorno.getHorarioConsulta());
        } else {
            throw new ValidationException("Erro ao cancelar a consulta: Não foi encontrado nenhum registro dessa consulta!");
        }
        
        LocalDateTime limiteCancelamento = horarioConsulta.minusHours(24);

        if (agora.isAfter(limiteCancelamento)) {
            throw new ValidationException("Erro ao cancelar a consulta: Uma consulta somente poderá ser cancelada com antecedência mínima de 24 horas");
        }
        if(consulta.getMotivoCancelamento() == null) {
            throw new ValidationException("Erro ao cancelar a consulta: Por favor informe um motivo de cancelamento válido (OUTROS, MEDICO_CANCELOU, PACIENTE_DESISTIU)");
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
        LocalDateTime horarioComAntecedencia = dataHoraConsulta.minusMinutes(30);
        
        if(LocalDateTime.now().isBefore(horarioComAntecedencia)) {
            return true;
        }
        else if(LocalDateTime.now().isAfter(dataHoraConsulta)) {
            throw new ValidationException("Erro ao marcar consulta: Por favor selecione uma data futura para a consulta.");
        }
        
        throw new ValidationException("Erro ao marcar consulta: Você não pode marcar uma consulta com menos de 30 minutos de antecedencia!");
    } 
    
    private boolean isPacienteAtivo(int id) throws SQLException {
        if(this.pacienteRepository.isPacienteAtivo(id)) {
            return true;
        }
        
        throw new ValidationException("Erro ao marcar consulta: Você não pode marcar uma consulta com um cadastro de paciente inativo!");
    } 
    
    private boolean isMedicoAtivo(int id) throws SQLException {
        if(this.medicoRepository.isMedicoAtivo(id)) {
            return true;
        }
        
        throw new ValidationException("Erro ao marcar consulta: Você não pode marcar uma consulta com um cadastro de médico inativo!");
    }
    
    private boolean isMedicoDisponivel(ConsultaModel consulta) throws SQLException {
        LocalDateTime horarioDesejado = DateFormatterUtil.toLocalDate(consulta.getHorarioConsulta());
        LocalDateTime inicioConsulta = null;
        LocalDateTime fimConsulta = null;

        List<Timestamp> retornoHorarios = this.consultaRepository.getHoraConsultaByIdMedico(consulta.getMedico().getIdMedico());

        if (retornoHorarios == null) { // se não houver agendamento algum -> disponivel
            return true;
        } 
        else { // Se não -> verificar os horários
            
            for(Timestamp agendametoMedico : retornoHorarios) { // Literando por todos os agendamentos desse médico
                //Declarando auxiliares
                inicioConsulta = DateFormatterUtil.toLocalDate(agendametoMedico);
                fimConsulta = inicioConsulta.plusHours(1);
                boolean isDuranteConsulta = false;

                
                //Verificar se o horario que o paciente deseja marcar está dentro de um horario já agendado
                isDuranteConsulta = !(horarioDesejado.isBefore(inicioConsulta) || horarioDesejado.isAfter(fimConsulta));
                boolean agendarDepoisDeOutraConsulta = horarioDesejado.isEqual(fimConsulta); //para permitir agendar consultas logo ao termino de outra
                
                if(isDuranteConsulta && !agendarDepoisDeOutraConsulta){
                    return false;
                }
                
                //Caso passe pela primeira validação, verificar se o horario que o paciente for marcar vai conflitar com um agendamento já existente
                LocalDateTime fimHorarioDesejado = horarioDesejado.plusHours(1);
                isDuranteConsulta = !(fimHorarioDesejado.isBefore(inicioConsulta) || fimHorarioDesejado.isAfter(fimConsulta));
                boolean agendarAntesDeOutraConsulta = fimHorarioDesejado.isEqual(inicioConsulta); //para permitir agendar consultas logo antes ao inicio de outra
                
                if(isDuranteConsulta && !agendarAntesDeOutraConsulta){
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isPacienteDisponivel(ConsultaModel consulta) throws SQLException {
        LocalDateTime horario = DateFormatterUtil.toLocalDate(consulta.getHorarioConsulta());
        
        Timestamp retornoPaciente = this.consultaRepository.getHoraConsultaByIdPaciente(consulta.getPaciente().getIdPaciente());

        if (retornoPaciente == null) { // se não houver agendamento
            return true;
        } else { // Verificar se o paciente tem consulta hoje
            boolean hasConsultaHoje = horario.getDayOfMonth() == DateFormatterUtil.toLocalDate(retornoPaciente).getDayOfMonth();
            return hasConsultaHoje ? false : true;
        }
    }
    
    private boolean verificaDisponibilidade(ConsultaModel consulta) throws SQLException {
        if(!isPacienteDisponivel(consulta))
            throw new ValidationException("Erro ao marcar consulta: Esse paciente já possui o máximo de uma consulta marcada hoje!");

        if(!isMedicoDisponivel(consulta))
            throw new ValidationException("Erro ao marcar consulta: Esse médico já possui uma consulta marcada nesse periodo de tempo. Tente marcar em outro horário!");
    
        return true;
    }
    
    private MedicoModel getMedicoAleatrio(LocalDateTime horaConsulta) throws SQLException { 
        ArrayList<MedicoModel> listMedicos = this.consultaRepository.getMedicosDisponiveis(DateFormatterUtil.toTimestamp(horaConsulta));
        Random random = new Random();
        
        int posicao = random.nextInt(listMedicos.size());
        MedicoModel medicoRandom = listMedicos.get(posicao);

        return medicoRandom;
    }
    
}
