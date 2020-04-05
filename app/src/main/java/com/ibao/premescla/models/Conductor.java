package com.ibao.premescla.models;

import java.util.ArrayList;
import java.util.List;

public class Conductor {

    /*
        "id": "1",
        "idTancada": "270",
        "ordenCode": "00002146",
        "nombreConductor": "BENITO PEREZ",
    */

    private int id;
    private int idTancada;
    private String ordenCode;
    private String nombreConductor;

    private List<Tractor> tractores;
    private List<Fumigadora> fumigadoras;

    public Conductor() {
        this.id=0;
        this.idTancada=0;
        this.ordenCode ="";
        this.nombreConductor ="";
        this.tractores=new ArrayList<>();
        this.fumigadoras=new ArrayList<>();
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

    public String getOrdenCode() {
        return ordenCode;
    }

    public void setOrdenCode(String ordenCode) {
        this.ordenCode = ordenCode;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }


    public List<Tractor> getTractores() {
        return tractores;
    }

    public void setTractores(List<Tractor> tractores) {
        this.tractores = tractores;
    }

    public List<Fumigadora> getFumigadoras() {
        return fumigadoras;
    }

    public void setFumigadoras(List<Fumigadora> fumigadoras) {
        this.fumigadoras = fumigadoras;
    }


}
