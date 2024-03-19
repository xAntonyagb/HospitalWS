package br.unipar.hospitalws.enums;

public enum EspecialidadeEnum {
    ORTOPEDIA('O'),
    CARDIOLOGIA('C'),
    GINECOLOGIA('G'),
    DERMATOLOGIA('D');
    
    char codigo;
    
    private EspecialidadeEnum(char codigo) {
        this.codigo = codigo;
    }

    public char getCodigo() {
        return codigo;
    }

    public void setCodigo(char codigo) {
        this.codigo = codigo;
    }
    
}
