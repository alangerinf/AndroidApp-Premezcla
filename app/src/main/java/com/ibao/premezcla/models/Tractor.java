package com.ibao.premezcla.models;

public class Tractor {

    /*
    {
        "id": "1",
        "codTractor": "1",
        "nombreTractor": "TRACTOR FERRARI XYZ"
    }
    */
    private int id;
    private String codTractor;
    private String nombreTractor;

    public Tractor() {

        this.id = 0;
        this.codTractor = "";
        this.nombreTractor ="";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodTractor() {
        return codTractor;
    }

    public void setCodTractor(String codTractor) {
        this.codTractor = codTractor;
    }

    public String getNombreTractor() {
        return nombreTractor;
    }

    public void setNombreTractor(String nombreTractor) {
        this.nombreTractor = nombreTractor;
    }
}
