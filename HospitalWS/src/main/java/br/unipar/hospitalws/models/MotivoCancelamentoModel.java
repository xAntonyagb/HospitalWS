package br.unipar.hospitalws.models;

//Tem tabela no banco portanto fazer pq sim
public class MotivoCancelamentoModel {
    private int id;
    private char codMotivo;
    private String descMotivo;

    public MotivoCancelamentoModel() {
    }

    public MotivoCancelamentoModel(int idMotivo, char codMotivo, String descMotivo) {
        this.id = idMotivo;
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

    @Override
    public String toString() {
        return "MotivoCancelamentoModel{" + "id=" + id + ", codMotivo=" + codMotivo + ", descMotivo=" + descMotivo + '}';
    }
    
}
