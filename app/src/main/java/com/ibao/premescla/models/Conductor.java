package com.ibao.premescla.models;

public class Conductor {

    /*
    {
        "id":2,
        "idTancada":0,
        "documentNumber":"12345678",
        "name":"ALVARADO TRELLES, DOMINGO"
    }
    */

    private int id;
    private int idTancada;
    private String documentNumber;
    private String name;

    public Conductor() {
        this.id=0;
        this.idTancada=0;
        this.documentNumber="";
        this.name="";
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

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
