package com.ibao.premescla.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Orden implements Serializable {

/**
'id'=> $idOrden,
'idLote'=> $rowOrden['ID_LOTE'],
'fundoName'=> utf8_encode($rowOrden['NOMBRE_FUNDO']),
'cultivoName'=> utf8_encode($rowOrden['NOMBRE_CULTIVO']),
'variedadName'=> utf8_encode($rowOrden['NOMBRE_VARIEDAD']),
'loteCode'=> utf8_encode($rowOrden['CODIGO_LOTE']),
'ordenCode'=> utf8_encode($rowOrden['CODIGO_ORDEN']),
'ordenDate'=> utf8_encode($rowOrden['FECHA_ORDEN']),
'aplicacionDate'=> utf8_encode($rowOrden['FECHA_APLICACION']),
'phMin'=> $rowOrden['MIN_PH'],
'phMax'=> $rowOrden['MAX_PH'],
'ecRate'=> $rowOrden['EC'],
'ecTolerance'=> $rowOrden['TOLERANCIA_EC'],
'tractorVel'=> $rowOrden['VELOCIDAD_TRACTOR'],
'rpm'=> $rowOrden['RPM'],
'boquillasCant'=> $rowOrden['NRO_BOQUILLAS'],
'tancadasProgramadas'=> $rowOrden['NRO_TANCADAS_PROGRAMAS']
*/

    private int id;
    private int idLote;
    private String fundoName;
    private String cultivoName;
    private String variedadName;
    private String loteCode;
    private String ordenCode;
    private String aplicacionDate;
    private float phMin;
    private float phMax;
    private float ecRate;
    private float ecTolerance;
    private float tractorVel;
    private float rpm;
    private int boquillasCant;
    private int tancadasProgramadas;

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

}
