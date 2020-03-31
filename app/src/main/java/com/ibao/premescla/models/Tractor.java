package com.ibao.premescla.models;

public class Tractor {

    /*
    {
        "id":3,
        "idTancada":0,
        "placa":"ABC-223"
    }
    */
    private int id;
    private int idTancada;
    private String placa;

    public Tractor() {

        this.id = 0;
        this.idTancada = 0;
        this.placa ="";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTancada() {
        return idTancada;
    }

    public void setIdTancada(int idTancada) {
        this.idTancada = idTancada;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
