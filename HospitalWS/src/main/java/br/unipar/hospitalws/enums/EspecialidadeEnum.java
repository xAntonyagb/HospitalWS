package br.unipar.hospitalws.enums;

import br.unipar.hospitalws.exceptions.ValidationException;

public enum EspecialidadeEnum {
    ORTOPEDIA(1, "O"),
    CARDIOLOGIA(2, "C"),
    GINECOLOGIA(3, "G"),
    DERMATOLOGIA(4, "D");
    
    int id;
    String codigo;
    
    private EspecialidadeEnum(int id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public static EspecialidadeEnum getEnumByCodigo(String codigo) {
        for (EspecialidadeEnum especialidade : EspecialidadeEnum.values()) {
            if (especialidade.getCodigo().equalsIgnoreCase(codigo)) {
                return especialidade;
            }
        }
        throw new ValidationException("Código de especialidade inválido: " + codigo);
    }
    
    public static EspecialidadeEnum getEnumById(int id) {
        for (EspecialidadeEnum especialidade : EspecialidadeEnum.values()) {
            if (especialidade.getId() == id) {
                return especialidade;
            }
        }
        throw new ValidationException("Id de especialidade inválido: " + id);
    }
    
}
