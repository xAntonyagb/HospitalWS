package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.enums.MotivoCancelamentoEnum;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.ConsultaModel;
import br.unipar.hospitalws.models.MedicoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ConsultaRepository {

    private ConnectionFactory connectionFactory = new ConnectionFactory();
    Connection connection;
    
    public ConsultaRepository(Connection connection) throws SQLException {
        this.connection = connection;
    }
    
    public ConsultaModel insertConsulta(ConsultaModel consulta) {
        String sql = "INSERT INTO tb_consulta (id_medico, id_paciente, horario_consulta, st_cancelada) "
                + "VALUES (?, ?, ?, ?)";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, consulta.getMedico().getIdMedico());
            ps.setInt(2, consulta.getPaciente().getIdPaciente());
            ps.setTimestamp(3, consulta.getHorarioConsulta());
            ps.setBoolean(4, false);
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            if(rs.next()){
                return retornaConsultaInstace(rs);
            }
        } 
        catch (SQLException ex) {
            throw new DataBaseException("(insertConsulta) "+ ex.getMessage());
        } 
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
        }
        
        return consulta;
    }
    
    public ConsultaModel getConsultaById(int id) {
        String sql = "SELECT id_medico, id_paciente, horario_consulta, st_cancelada, id_motivo_cancelamento "
                + "FROM tb_consulta "
                + "WHERE id = ?";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if(rs.next()){
                return retornaConsultaInstace(rs);
            }
        } 
        catch (SQLException ex) {
            throw new DataBaseException("(getConsultaById) "+ ex.getMessage());
        } 
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
        }
        
        return null;
    }
    
    public ArrayList<ConsultaModel> getAllConsultas() {
        String sql = "SELECT id, id_medico, id_paciente, horario_consulta, st_cancelada, id_motivo_cancelamento FROM tb_consulta";
        ArrayList<ConsultaModel> listConsultas = new ArrayList<ConsultaModel>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                ConsultaModel consulta = retornaConsultaInstace(rs);
                listConsultas.add(consulta);
            }
            
        } 
        catch (SQLException ex) {
            throw new DataBaseException("(getAllConsultas) "+ ex.getMessage());
        } 
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
        }
        
        return listConsultas;
    }
    
    public ConsultaModel updateConsulta(ConsultaModel consulta) {
        String sql = "UPDATE tb_consulta SET "
                + "id_medico = ?, id_paciente = ?, horario_consulta = ?, st_cancelada = ?, id_motivo_cancelamento = ? "
                + "WHERE id = ? ";

        PreparedStatement ps = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            
            ps.setInt(1, consulta.getMedico().getIdMedico());
            ps.setInt(2, consulta.getPaciente().getIdPaciente());
            ps.setTimestamp(3, consulta.getHorarioConsulta());
            ps.setBoolean(4, consulta.isIsCancelada());
            ps.setInt(5, consulta.getMotivoCancelamento().getId());
            ps.setInt(6, consulta.getIdConsulta());
            
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DataBaseException("(updateConsulta) "+ ex.getMessage());
        } 
        finally {
            connectionFactory.closePreparedStatement(ps);
        }
        
        return consulta;
    }
    
    public boolean cancelarConsulta(ConsultaModel consulta) {
        String sql = "UPDATE tb_consulta SET st_cancelada = ?, id_motivo_cancelamento = ? "
                + "WHERE id = ? ";
       
        PreparedStatement ps = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setInt(2, consulta.getMotivoCancelamento().getId());
            
            int linhasDelete = ps.executeUpdate();
            return linhasDelete > 0;
        }  
        catch (SQLException ex) {
            throw new DataBaseException("(cancelarConsulta) "+ ex.getMessage());
        } 
        finally {
           connectionFactory.closePreparedStatement(ps);
        }
    }
    
    public Timestamp getHoraConsultaByIdMedico(int id) {
        String sql = "SELECT horario_consulta "
                + "FROM tb_consulta c "
                + "INNER JOIN tb_medico m ON c.id_medico = m.id "
                + "WHERE m.id = ?";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                return rs.getTimestamp("horario_consulta");
            }
            
        }  
        catch (SQLException ex) {
            throw new DataBaseException("(getHoraConsultaByIdMedico) "+ ex.getMessage());
        } 
        finally {
           connectionFactory.closeResultSet(rs);
           connectionFactory.closePreparedStatement(ps);
        }
        
        return null;
    }
    
    public Timestamp getHoraConsultaByIdPaciente(int id) {
        String sql = "SELECT horario_consulta "
                + "FROM tb_consulta c "
                + "INNER JOIN tb_paciente p ON c.id_paciente = p.id "
                + "WHERE p.id = ?";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                return rs.getTimestamp("horario_consulta");
            }
            
        } 
        catch (SQLException ex) {
            throw new DataBaseException("(getHoraConsultaByIdPaciente) "+ ex.getMessage());
        } 
        finally {
           connectionFactory.closeResultSet(rs);
           connectionFactory.closePreparedStatement(ps);
        }
        
        return null;
    }
    
    public ArrayList<MedicoModel> getMedicosDisponiveis(LocalDateTime horarioConsulta) {
        // Retorna todos os médicos que não estão na lista de médicos com o horário já marcado no horário de desejo
        String sql = "SELECT m.id " +
                     "FROM tb_medico m " +
                     "WHERE m.id NOT IN ( " +
                     "    SELECT c.id_medico " +
                     "    FROM tb_consulta c " +
                     "    WHERE ? BETWEEN c.horario_consulta AND (c.horario_consulta + INTERVAL '1 HOUR') " +
                     ")";

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<MedicoModel> listMedicos = new ArrayList<>();

        try {
            ps = this.connection.prepareStatement(sql);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(horarioConsulta));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(horarioConsulta));

            rs = ps.executeQuery();

            while (rs.next()) {
                MedicoModel medico = new MedicoModel();
                medico.setIdMedico(rs.getInt("id"));
                listMedicos.add(medico);
            }
        } 
        catch (SQLException ex) {
            throw new DataBaseException("(getMedicosDisponiveis) "+ ex.getMessage());
        } 
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
        }

        return listMedicos;
    }

    
    private ConsultaModel retornaConsultaInstace(ResultSet rs) {
        try {
            ConsultaModel consulta = new ConsultaModel();
            consulta.setIdConsulta(rs.getInt("id"));
            consulta.getMedico().setIdMedico((rs.getInt("id_medico")));
            consulta.getPaciente().setIdPaciente(rs.getInt("id_paciente"));
            consulta.setHorarioConsulta(rs.getTimestamp("horario_consulta"));
            consulta.setIsCancelada(rs.getBoolean("st_cancelada"));
            consulta.setMotivoCancelamento(MotivoCancelamentoEnum.getEnumById(rs.getInt("id_motivo_cancelamento")));

            return consulta;
        }
        catch (SQLException ex) {
            throw new DataBaseException("(retornaConsultaInstace) "+ ex.getMessage());
        }
    }
    
}
