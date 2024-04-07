package br.unipar.hospitalws.utils;

import br.unipar.hospitalws.exceptions.FormattingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateFormatterUtil {
    
    private static final Logger logger = Logger.getLogger("DateFormatterUtil");

    public static Timestamp toTimestamp(LocalDateTime data) {
        if(data == null)
            throw new FormattingException("Input nulo");
        
        try {
            return Timestamp.valueOf(data);
        } catch (Exception ex) {
            logger.log(Level.INFO, "(toTimestamp) Nao foi possivel converter para Timestamp: Retornando nulo");
            return null;
        }
     }

    public static Timestamp toTimestamp(String input) {
        if(input == null)
            throw new FormattingException("Input nulo");
        
        try {
            String novo = input.replaceAll("[^0-9]", "");
            if(novo.length() < 10)
                throw new Exception();
            
            novo = novo.replaceAll("(\\d{2})(\\d{2})(\\d{4})(\\d{2})(\\d{2})(\\d{2})", "$3-$2-$1 $4:$5:$6");
            return Timestamp.valueOf(novo);
        } catch (Exception ex) {
            logger.log(Level.INFO, "(toTimestamp) Nao foi possivel converter para Timestamp: Retornando nulo");
            return null;
        }
    }
    
    public static LocalDateTime toLocalDate(Timestamp data) {
        if(data == null)
            throw new FormattingException("Input nulo");
        
        try {
            return ajustaFormatoExibicao(data.toLocalDateTime());
        } catch (Exception ex) {
            logger.log(Level.INFO, "(toLocalDate) Nao foi possivel converter para LocalDateTime: Retornando nulo");
            return null;
        }
    }
    
    public static LocalDateTime toLocalDate(String data) {
        if(data == null)
            throw new FormattingException("Input nulo");
        
        try {
            String novo = data.replaceAll("[^0-9]", "");
            
            LocalDateTime objetoData = LocalDateTime.parse(
                    novo, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            );
            
            return ajustaFormatoExibicao(objetoData);
        } catch (Exception ex) {
            logger.log(Level.INFO, "(toLocalDate) Nao foi possivel converter para LocalDateTime: Retornando nulo");
            return null;
        }
    }
    
    public static LocalDateTime ajustaFormatoExibicao(LocalDateTime data) {
        if(data == null)
            return null;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dataFormatada = data.format(formatter);
        return LocalDateTime.parse(dataFormatada, formatter);
    }
    
    public static LocalDateTime apenasHora(LocalDateTime data) {
        if(data == null)
            return null;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormatada = data.format(formatter);
        return LocalDateTime.parse(horaFormatada, formatter);
    }
    
}
