package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.enums.MotivoCancelamentoEnum;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.ConsultaModel;
import br.unipar.hospitalws.models.MedicoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {

    Connection connection;
    
    public ConsultaRepository() throws SQLException {
        this.connection = ConnectionFactory.getConnection();
    }
    
    public int insertConsulta(ConsultaModel consulta) throws SQLException {
        String sql = "INSERT INTO tb_consulta (id_medico, id_paciente, horario_consulta, st_cancelada) "
                + "VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, consulta.getMedico().getIdMedico());
            ps.setInt(2, consulta.getPaciente().getIdPaciente());
            ps.setTimestamp(3, consulta.getHorarioConsulta());
            ps.setBoolean(4, false);
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()){
                    return rs.getInt("id");
                }
            } 
        }
        
        return 0;
    }
    
    public ConsultaModel getConsultaById(int id) throws SQLException {
        String sql = "SELECT id, id_medico, id_paciente, horario_consulta, st_cancelada, id_motivo_cancelamento "
                + "FROM tb_consulta "
                + "WHERE id = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
            
                if(rs.next()){
                    return retornaConsultaInstace(rs);
                }
            }
        } 
            
        return null;
    }
    
    public List<ConsultaModel> getAllConsultas() throws SQLException {
        String sql = "SELECT id, id_medico, id_paciente, horario_consulta, st_cancelada, id_motivo_cancelamento FROM tb_consulta";
        List<ConsultaModel> listConsultas = new ArrayList<ConsultaModel>();
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {

                while(rs.next()) {
                    ConsultaModel consulta = retornaConsultaInstace(rs);
                    listConsultas.add(consulta);
                }
            }            
        }
        
        return listConsultas;
    }
    
    public ConsultaModel updateConsulta(ConsultaModel consulta) throws SQLException {
        String sql = "UPDATE tb_consulta SET "
                + "id_medico = ?, id_paciente = ?, horario_consulta = ?, st_cancelada = ?, id_motivo_cancelamento = ? "
                + "WHERE id = ? ";

        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            
            ps.setInt(1, consulta.getMedico().getIdMedico());
            ps.setInt(2, consulta.getPaciente().getIdPaciente());
            ps.setTimestamp(3, consulta.getHorarioConsulta());
            ps.setBoolean(4, consulta.isIsCancelada());
            ps.setInt(6, consulta.getIdConsulta());
            
            if(consulta.getMotivoCancelamento() != null) {
                ps.setInt(5, consulta.getMotivoCancelamento().getId());
            }
            else {
                ps.setNull(5, Types.INTEGER);
            }
            
            ps.executeUpdate();
        }
        
        return consulta;
    }
    
    public boolean cancelarConsulta(ConsultaModel consulta) throws SQLException {
        String sql = "UPDATE tb_consulta SET st_cancelada = ?, id_motivo_cancelamento = ? "
                + "WHERE id = ? ";
       
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setBoolean(1, true);
            ps.setInt(2, consulta.getMotivoCancelamento().getId());
            ps.setInt(3, consulta.getIdConsulta());
            
            int linhasDelete = ps.executeUpdate();
            return linhasDelete > 0;
        }
    }
    
    public List<Timestamp> getHoraConsultaByIdMedico(int id) throws SQLException {
        String sql = "SELECT horario_consulta "
                + "FROM tb_consulta c "
                + "INNER JOIN tb_medico m ON c.id_medico = m.id "
                + "WHERE c.st_cancelada = false "
                + "AND m.id = ?";
        
        List<Timestamp> consultasAgendadas = new ArrayList<>();
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try(ResultSet rs = ps.executeQuery()) {
                
                while(rs.next()){
                    consultasAgendadas.add(rs.getTimestamp("horario_consulta"));
                }
            }
        }  
        
        return consultasAgendadas;
    }
    
    public Timestamp getHoraConsultaByIdPaciente(int id) throws SQLException {
        String sql = "SELECT horario_consulta "
                + "FROM tb_consulta c "
                + "INNER JOIN tb_paciente p ON c.id_paciente = p.id "
                + "WHERE p.id = ?";

        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                
                if(rs.next()){
                    return rs.getTimestamp("horario_consulta");
                }
            }
        } 
        
        return null;
    }
    
    // Retorna todos os médicos que não tenham consultas marcadas ou que conflitem com o horário de desejo
    public ArrayList<MedicoModel> getMedicosDisponiveis(Timestamp horarioConsulta) throws SQLException {
        String sql = 
                "SELECT m.id " // Selecione todos os médicos
                + "FROM tb_medico m "
                + "WHERE m.id NOT IN ( " // que não estiverem no subselect:
                
                    //Selecione todos os médicos da tb_consulta que:
                    + "SELECT c.id_medico "
                    + "FROM tb_consulta c "
                
                    // Tem o horario informado dentre o periodo de uma consulta já marcada
                    + "WHERE ? BETWEEN c.horario_consulta AND (c.horario_consulta + INTERVAL '1 HOUR') " // não precisa de cast pq já é do banco
                
                        // Ou quando o horário informado irá futuramente entrar em conflito com um periodo de uma consulta afrente
                        + "AND (CAST(? AS TIMESTAMP) + INTERVAL '1 HOUR') " // Cast para adicionar intervalo
                        + "BETWEEN c.horario_consulta AND (c.horario_consulta + INTERVAL '1 HOUR') "
                        + ")"
                + "AND m.st_ativo = true"; // Funcionando /o/
        
        
        ArrayList<MedicoModel> listMedicos = new ArrayList<>();

        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setTimestamp(1, horarioConsulta);
            ps.setTimestamp(2, horarioConsulta);
            
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    MedicoModel medico = new MedicoModel();
                    medico.setIdMedico(rs.getInt("id"));
                    listMedicos.add(medico);
                }
            }
        } 
        
        return listMedicos;
    }
    
    public void cancelarConsultaByIdPaciente(int id) throws SQLException {
        String sql = "UPDATE tb_consulta SET st_cancelada = ?, id_motivo_cancelamento = ? "
                + "WHERE id_paciente = ? ";
       
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setBoolean(1, true);
            ps.setInt(2, MotivoCancelamentoEnum.OUTROS.getId());
            ps.setInt(3, id);
            
            ps.executeUpdate();
        }
    }
    
    public void cancelarConsultaByIdMedico(int id) throws SQLException {
        String sql = "UPDATE tb_consulta SET st_cancelada = ?, id_motivo_cancelamento = ? "
                + "WHERE id_medico = ? ";
       
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setBoolean(1, true);
            ps.setInt(2, MotivoCancelamentoEnum.OUTROS.getId());
            ps.setInt(3, id);
            
            ps.executeUpdate();
        }
    }

    
    private ConsultaModel retornaConsultaInstace(ResultSet rs) throws SQLException {
        ConsultaModel consulta = new ConsultaModel();
        consulta.setIdConsulta(rs.getInt("id"));
        consulta.getMedico().setIdMedico(rs.getInt("id_medico"));
        consulta.getPaciente().setIdPaciente(rs.getInt("id_paciente"));
        consulta.setHorarioConsulta(rs.getTimestamp("horario_consulta"));
        consulta.setIsCancelada(rs.getBoolean("st_cancelada"));
        
        int idMotivoCancelamento = rs.getInt("id_motivo_cancelamento");
        consulta.setMotivoCancelamento(
                idMotivoCancelamento == 0 ? 
                        null : MotivoCancelamentoEnum.getEnumById(idMotivoCancelamento));

        return consulta;
    }
    
}
