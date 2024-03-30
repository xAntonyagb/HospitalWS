package br.unipar.hospitalws.utils;

public class StringValidatorUtil {
    
    public static String ajustaNormalInput(String input) {
        if(input != null) {
            input = input.replaceAll("[^0-9a-zA-Z\\-,\\.]", "");
        }
        
        return input.isEmpty() ? null : input;
    }
    
    public static String ajustaNumberInput(String input) {
        if(input != null) {
            input = input.replaceAll("[^0-9]", "");
        }
        
        return input.isEmpty() ? null : input;
    }
    
}
