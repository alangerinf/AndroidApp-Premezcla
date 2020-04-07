package com.ibao.premezcla.models;

import java.io.Serializable;

public class ProductoPesado  implements Serializable {

    /***
     *   'id' => $rowProductosPesados['ID_PRODUCTO_PESADO'],
     *   'idTancada' => $rowProductosPesados['ID_TANCADA'],
     *   'idOrdenDetalle' => $rowProductosPesados['ID_ORDEN_DETALLE'],
     *   'cantidadPesada' => $rowProductosPesados['CANTIDAD_PESADA'],
     *   'fechaPesada' => $rowProductosPesados['FECHA_PESADA']
     */
    private int id;
    private int idTancada;
    private int idOrdenDetalle;
    private float cantidadPesada;
    private String fechaPesada;

    private float dosis;
    private String units;
    private float toleranceRate;
    private String productName;
    private String productActive;
    private String productType;

    public ProductoPesado() {

        this.id = 0;
        this.idTancada = 0;
        this.idOrdenDetalle = 0;
        this.cantidadPesada = 0;
        this.fechaPesada = "";

        this.dosis = 0;
        this.toleranceRate = 0;
        this.units = "";
        this.productName = "";
        this.productActive = "";
        this.productType = "";

    }

    public float getDosis() {
        return dosis;
    }

    public void setDosis(float dosis) {
        this.dosis = dosis;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public float getToleranceRate() {
        return toleranceRate;
    }

    public void setToleranceRate(float toleranceRate) {
        this.toleranceRate = toleranceRate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductActive() {
        return productActive;
    }

    public void setProductActive(String productActive) {
        this.productActive = productActive;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public int getIdOrdenDetalle() {
        return idOrdenDetalle;
    }

    public void setIdOrdenDetalle(int idOrdenDetalle) {
        this.idOrdenDetalle = idOrdenDetalle;
    }

    public float getCantidadPesada() {
        return cantidadPesada;
    }

    public void setCantidadPesada(float cantidadPesada) {
        this.cantidadPesada = cantidadPesada;
    }

    public String getFechaPesada() {
        return fechaPesada;
    }

    public void setFechaPesada(String fechaPesada) {
        this.fechaPesada = fechaPesada;
    }
}
