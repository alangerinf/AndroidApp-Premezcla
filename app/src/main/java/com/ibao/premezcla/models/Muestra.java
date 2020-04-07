package com.ibao.premezcla.models;

import java.io.Serializable;

public class Muestra implements Serializable{

    //{
    // "id":"1",
    // "idTancada":"1",
    // "startMuestra":"2020-03-26 10:00",
    // "endMuestra":"2020-03-26 10:30",
    // "lines":2,"distancy":100,
    // "duration":"00:30",
    // "velocityMuestra":3.3333332538605,
    // "velocityTractor":0
    // }

    private int id;
    private int idTancada;
    private String startMuestra;
    private String endMuestra;
    private int lines;
    private String duration;
    private double velocityMuestra;
    private double velocityTractor;
    private double distancy;

    //esto no deberia esta aqui
    private int usuario;//es el id

    public Muestra() {
        this.id=0;
        this.startMuestra="";
        this.endMuestra="";
        this.lines=0;
        this.duration="";
        this.velocityMuestra=0d;
        this.velocityTractor=0d;
        this.idTancada=0;
        this.distancy=0d;
        this.usuario=0;
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

    public String getStartMuestra() {
        return startMuestra;
    }

    public void setStartMuestra(String startMuestra) {
        this.startMuestra = startMuestra;
    }

    public String getEndMuestra() {
        return endMuestra;
    }

    public void setEndMuestra(String endMuestra) {
        this.endMuestra = endMuestra;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getVelocityMuestra() {
        return velocityMuestra;
    }

    public void setVelocityMuestra(double velocityMuestra) {
        this.velocityMuestra = velocityMuestra;
    }

    public double getVelocityTractor() {
        return velocityTractor;
    }

    public void setVelocityTractor(double velocityTractor) {
        this.velocityTractor = velocityTractor;
    }

    public double getDistancy() {
        return distancy;
    }

    public void setDistancy(double distancy) {
        this.distancy = distancy;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }
}
