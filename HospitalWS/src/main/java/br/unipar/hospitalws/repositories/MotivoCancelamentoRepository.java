package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.MotivoCancelamentoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//Tem tabela no banco portanto fazer pq sim
public class MotivoCancelamentoRepository {

    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private Connection connection;

    public MotivoCancelamentoRepository(Connection connection) {
        this.connection = connection;
    }

    public MotivoCancelamentoModel insertMotivoCancelamento( MotivoCancelamentoModel motivo) {
        String sql = "INSERT INTO tb_motivo_cancelamento (cod_motivo, desc_motivo) VALUES (?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, Character.toString(motivo.getCodMotivo()));
            ps.setString(2, motivo.getDescMotivo());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                motivo = retornaMotivoCancelamentoInstance(rs);
            }
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
        }
        
        return motivo;
    }

    public ArrayList<MotivoCancelamentoModel> getAllMotivosCancelamento() {
        String sql = "SELECT id, cod_motivo, desc_motivo FROM tb_motivo_cancelamento";
        ArrayList<MotivoCancelamentoModel> listMotivos = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()) {
                MotivoCancelamentoModel consulta = retornaMotivoCancelamentoInstance(rs);
                listMotivos.add(consulta);
            }
        }
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
        }
        
        return listMotivos;
    }
    
    public MotivoCancelamentoModel getMotivoCancelamentoById(int id) {
        String sql = "SELECT id, cod_motivo, desc_motivo FROM tb_motivo_cancelamento WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        MotivoCancelamentoModel motivo = null;

        try {
            ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                motivo = retornaMotivoCancelamentoInstance(rs);
            }
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
        }

        return motivo;
    }
    
    public MotivoCancelamentoModel updateMotivoCancelamento(MotivoCancelamentoModel motivo) {
        String sql = "UPDATE tb_motivo_cancelamento SET cod_motivo = ?, desc_motivo = ? WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(motivo.getCodMotivo()));
            ps.setString(2, motivo.getDescMotivo());
            ps.setInt(3, motivo.getId());
            
            rs = ps.executeQuery();
            rs.next();
            return retornaMotivoCancelamentoInstance(rs);
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
        }
    }

    public boolean deleteMotivoCancelamento(int id) {
        String sql = "DELETE FROM tb_motivo_cancelamento WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            connectionFactory.closePreparedStatement(ps);
        }
    }
    
    private MotivoCancelamentoModel retornaMotivoCancelamentoInstance(ResultSet rs) throws SQLException {
        MotivoCancelamentoModel motivoCancelamento = new MotivoCancelamentoModel();
        motivoCancelamento.setId(rs.getInt("id"));
        motivoCancelamento.setCodMotivo(rs.getString("cod_motivo").charAt(0));
        motivoCancelamento.setDescMotivo(rs.getString("desc_motivo"));
        
        return motivoCancelamento;
    }

}