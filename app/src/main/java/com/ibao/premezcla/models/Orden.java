package com.ibao.premezcla.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Orden implements Serializable {

    public final static int status_pendiente = 0;
    public final static int status_enproceso = 1;
    public final static int status_finalizada = 2;


    private int id; // "id": "2",
    private int idLote; //"idLote": 0
    private String fundoName; // "fundoName": "SAN LORENZO",
    private String cultivoName;// "cultivoName": "ARANDANO",
    private String variedadName; // "variedadName": "BILOXI",
    private String loteCode;//    "loteCode": "020501",
    private String ordenCode;//    "ordenCode": "00002106",
    //    "ordenDate": "2020-03-04",
    private String aplicacionDate;//    "aplicacionDate": "2020-03-06",
    private float phMin;//    "phMin": 0,
    private float phMax;//    "phMax": 0,
    private float ecRate;//    "ecRate": 0,
    private float ecTolerance;//    "ecTolerance": 0,
    //    "mojamiento": 500,
    private float tractorVel;//    "tractorVel": 0,
    private float rpm;//    "rpm": 0,
    private int boquillasCant;//    "boquillasCant": 0,
    private int tancadasProgramadas;//    "tancadasProgramadas": 1,
    private String status;
    private List<OrdenDetalle> ordenesDetalle;
    private List<Tancada> tancadas;


    public Orden() {
        this.id = 0;
        this.idLote = 0;
        this.fundoName = "";
        this.cultivoName = "";
        this.variedadName = "";
        this.loteCode = "";
        this.ordenCode = "";
        this.aplicacionDate = "";
        this.phMin = 0;
        this.phMax = 0;
        this.ecRate = 0;
        this.ecTolerance = 0;
        this.tractorVel = 0;
        this.rpm = 0;
        this.boquillasCant = 0;
        this.tancadasProgramadas = 0;
        this.status="";

        this.ordenesDetalle = new ArrayList<>();
        this.tancadas = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public String getFundoName() {
        return fundoName;
    }

    public void setFundoName(String fundoName) {
        this.fundoName = fundoName;
    }

    public String getCultivoName() {
        return cultivoName;
    }

    public void setCultivoName(String cultivoName) {
        this.cultivoName = cultivoName;
    }

    public String getVariedadName() {
        return variedadName;
    }

    public void setVariedadName(String variedadName) {
        this.variedadName = variedadName;
    }

    public String getLoteCode() {
        return loteCode;
    }

    public void setLoteCode(String loteCode) {
        this.loteCode = loteCode;
    }

    public String getOrdenCode() {
        return ordenCode;
    }

    public void setOrdenCode(String ordenCode) {
        this.ordenCode = ordenCode;
    }

    public String getAplicacionDate() {
        return aplicacionDate;
    }

    public void setAplicacionDate(String aplicacionDate) {
        this.aplicacionDate = aplicacionDate;
    }

    public float getPhMin() {
        return phMin;
    }

    public void setPhMin(float phMin) {
        this.phMin = phMin;
    }

    public float getPhMax() {
        return phMax;
    }

    public void setPhMax(float phMax) {
        this.phMax = phMax;
    }

    public float getEcRate() {
        return ecRate;
    }

    public void setEcRate(float ecRate) {
        this.ecRate = ecRate;
    }

    public float getEcTolerance() {
        return ecTolerance;
    }

    public void setEcTolerance(float ecTolerance) {
        this.ecTolerance = ecTolerance;
    }

    public float getTractorVel() {
        return tractorVel;
    }

    public void setTractorVel(float tractorVel) {
        this.tractorVel = tractorVel;
    }

    public float getRpm() {
        return rpm;
    }

    public void setRpm(float rpm) {
        this.rpm = rpm;
    }

    public int getBoquillasCant() {
        return boquillasCant;
    }

    public void setBoquillasCant(int boquillasCant) {
        this.boquillasCant = boquillasCant;
    }

    public int getTancadasProgramadas() {
        return tancadasProgramadas;
    }

    public void setTancadasProgramadas(int tancadasProgramadas) {
        this.tancadasProgramadas = tancadasProgramadas;
    }

    public List<OrdenDetalle> getOrdenesDetalle() {
        return ordenesDetalle;
    }

    public void setOrdenesDetalle(List<OrdenDetalle> ordenesDetalle) {
        this.ordenesDetalle = ordenesDetalle;
    }

    public List<Tancada> getTancadas() {
        return tancadas;
    }

    public void setTancadas(List<Tancada> tancadas) {
        this.tancadas = tancadas;
    }


    public int getCantComplete(){
        int tCompletas =0;
        for(Tancada t : tancadas){
            if(t.getProductosPesados().size()==ordenesDetalle.size()){
                tCompletas++;
            }
        }
        return tCompletas;
    }

    public boolean isComplete(){
        return getCantComplete() == tancadasProgramadas;
    }

    public boolean isNotComplete(){
        return getCantComplete() != tancadasProgramadas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCurrentProccess(){
        int resp = -1;
        switch (status){
            case "PENDIENTE" :
                resp = status_pendiente;
                break;
            case "EN PROCESO" :
                resp = status_enproceso;
                break;
            case "FINALIZADA" :
                resp = status_finalizada;
                break;
        }
        return resp;
    }
}
