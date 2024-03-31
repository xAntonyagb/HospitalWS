package br.unipar.hospitalws.enums;

import br.unipar.hospitalws.exceptions.ValidationException;

public enum MotivoCancelamentoEnum {
    //Mesma coisa do banco de dados
    PACIENTE_DESISTIU(1, 'P', "Paciente desistiu"),
    MEDICO_CANCELOU(2, 'M', "Médico cancelou"),
    OUTROS(3, 'O', "Outros");  
    
    int id;
    char codMotivo;
    String descMotivo;
    
    private MotivoCancelamentoEnum(int id, char codMotivo, String descMotivo) {
        this.id = id;
        this.codMotivo = codMotivo;
        this.descMotivo = descMotivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public char getCodMotivo() {
        return codMotivo;
    }

    public void setCodMotivo(char codMotivo) {
        this.codMotivo = codMotivo;
    }

    public String getDescMotivo() {
        return descMotivo;
    }

    public void setDescMotivo(String descMotivo) {
        this.descMotivo = descMotivo;
    }
    
    public static MotivoCancelamentoEnum getEnumByCodigo(char codigo) {
        for (MotivoCancelamentoEnum motivoCancelamento : MotivoCancelamentoEnum.values()) {
            if (motivoCancelamento.getCodMotivo() == codigo) {
                return motivoCancelamento;
            }
        }
        throw new ValidationException("Codigo de motivo de cancelamento inválido: " + codigo);
    }
    
    public static MotivoCancelamentoEnum getEnumById(int id) {
        for (MotivoCancelamentoEnum motivoCancelamento : MotivoCancelamentoEnum.values()) {
            if (motivoCancelamento.getId() == id) {
                return motivoCancelamento;
            }
        }
        throw new ValidationException("Id de motivo de cancelamento inválido: " + id);
    }
    
}
