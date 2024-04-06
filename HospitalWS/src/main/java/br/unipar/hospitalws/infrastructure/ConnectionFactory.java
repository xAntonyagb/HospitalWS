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
    
    private static final String RESOURCE_NAME = "PostgresResource2";
    private static Connection connection = null;
    private DataSource getDataSource;
    
    
    public static Connection getConnection() {
        if(connection == null) {
            ConnectionFactory.connection = openConnection();
            return openConnection();
        }
        else {
            return ConnectionFactory.connection;
        }
    }

    
    public static Connection openConnection() {
        Connection retorno = null;
        try {
            retorno = getDatasource().getConnection();
            retorno.setAutoCommit(false);
        } catch (Exception ex) {
            throw new DataBaseException(ex.getMessage());
        } 
        
        return retorno;
    }
    
    private static DataSource getDatasource() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup(RESOURCE_NAME);
    }
    
    public static void commit() {
        try {
            ConnectionFactory.connection.commit();
        } 
        catch (SQLException ex) {
            try {
                ConnectionFactory.connection.commit();
            } 
            catch (SQLException ex2) {
               throw new DataBaseException(ex2.getMessage());
            }
            
           throw new DataBaseException(ex.getMessage());
        }
    }
    
    public static void closeConnection() {
        try {
            if(ConnectionFactory.connection != null){
                ConnectionFactory.connection.close();
                ConnectionFactory.connection = null;
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
