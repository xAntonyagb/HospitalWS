package br.unipar.hospitalws.utils;

public class StringFormatterUtil {
    
    public static String ajustaNormalInput(String input) {
        if(input != null) {
            input = input.replaceAll("[^0-9a-zA-Z\\-,\\.]", "");
        }
        
        return input == null ? null : (input.isEmpty() ? null : input);
    }
    
    public static String ajustaNumberInput(String input) {
        if(input != null) {
            input = input.replaceAll("[^0-9]", "");
        }
        
        return input == null ? null : (input.isEmpty() ? null : input);
    }
    
}
