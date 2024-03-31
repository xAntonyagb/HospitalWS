package br.unipar.hospitalws.infrastructure;

import br.unipar.hospitalws.exceptions.DataBaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionFactory {
    
    private static final String RESOURCE_NAME = "postegresResource2";
    
    private DataSource getDataSource;
    
    private DataSource getDatasource() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup(RESOURCE_NAME);
    }
    
    public Connection getConnection() {
        Connection retorno = null;
        try {
            retorno = getDatasource().getConnection();
            retorno.setAutoCommit(false);
        } catch (Exception ex) {
            throw new DataBaseException(ex.getMessage());
        } 
        
        return retorno;
    }
    
    public void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
           throw new DataBaseException(ex.getMessage());
        }
    }
    
    public void commit(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException ex) {
           throw new DataBaseException(ex.getMessage());
        }
    }
    
    public void closeConnection(Connection connection) {
        try {
            if(connection != null){
                connection.close();
            }
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
    }
    
    public void closePreparedStatement(PreparedStatement ps) {
        try {
            if(ps != null) {
                ps.close();
            }
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
    }
    
    public void closeResultSet(ResultSet rs) {
        try {
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
    }
    
}
