package br.unipar.hospitalws.enums;

public enum EspecialidadeEnum {
    ORTOPEDIA("O"),
    CARDIOLOGIA("C"),
    GINECOLOGIA("G"),
    DERMATOLOGIA("D");
    
    String codigo;
    
    private EspecialidadeEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public static EspecialidadeEnum getEnumByCodigo(String codigo) {
        EspecialidadeEnum retorno = null;
        
        for (EspecialidadeEnum especialidade : EspecialidadeEnum.values()) {
            if (especialidade.getCodigo().equalsIgnoreCase(codigo)) {
                retorno = especialidade;
            }
        }
        
        return retorno;
    }
    
}
