package br.unipar.hospitalws.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatterUtil {

    public static Timestamp toTimestamp(LocalDateTime data) {
        return Timestamp.valueOf(data);
    }
    
    public static LocalDateTime toLocalDate(Timestamp data) {
        return ajustaFormatoExibicao(data.toLocalDateTime());
    }
    
    public static LocalDateTime ajustaFormatoExibicao(LocalDateTime data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dataFormatada = data.format(formatter);
        return LocalDateTime.parse(dataFormatada, formatter);
    }
    
    public static LocalDateTime apenasHora(LocalDateTime data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormatada = data.format(formatter);
        return LocalDateTime.parse(horaFormatada, formatter);
    }
    
}
