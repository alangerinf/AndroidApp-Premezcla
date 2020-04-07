package com.ibao.premezcla.models;

public class Fumigadora {

    /*
    {
        "id": "1",
        "codFumigadora": "1",
        "nombreFumigadora": "FUMIGADORA ESPACIAL ABD"
    }
    */
    private int id;
    private String codFumigadora;
    private String nombreFumigadora;

    public Fumigadora() {

        this.id = 0;
        this.codFumigadora = "";
        this.nombreFumigadora ="";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodFumigadora() {
        return codFumigadora;
    }

    public void setCodFumigadora(String codFumigadora) {
        this.codFumigadora = codFumigadora;
    }

    public String getNombreFumigadora() {
        return nombreFumigadora;
    }

    public void setNombreFumigadora(String nombreFumigadora) {
        this.nombreFumigadora = nombreFumigadora;
    }
}
