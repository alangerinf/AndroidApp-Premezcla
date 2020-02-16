package com.ibao.premescla.models;

import java.io.Serializable;

public class OrdenDetalle implements Serializable {

/**
'id'=>  $row['ID_ORDEN_DETALLE'],
'idOrden'=>  $row['ID_ORDEN'],
'idProduct'=>  $row['ID_PRODUCTO'],
'productName'=>  utf8_encode($row['NOMBRE_PRODUCTO']),
'productActive'=>  utf8_encode($row['NOMBRE_PRODUCTO']),
'productType'=>  utf8_encode($row['TIPO_PRODUCTO']),
'units'=>  utf8_encode($row['UNIDAD_MEDIDA']),
'dosis'=>  $row['DOSIS_PRODUCTO'],
'toleranceRate'=>  $row['TOLERANCIA_DOSIS']
*/

    private int id;
    private int idOrden;
    private int idProduct;
    private String productName;
    private String productActive;
    private String productType;
    private String units;
    private float dosis;
    private float toleranceRate;


    public OrdenDetalle() {
        this.id = 0;
        this.idOrden = 0;
        this.idProduct = 0;
        this.productName = "";
        this.productActive = "";
        this.productType = "";
        this.units = "g";
        this.dosis = 0;
        this.toleranceRate = 0;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
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

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public float getDosis() {
        return dosis;
    }

    public void setDosis(float dosis) {
        this.dosis = dosis;
    }

    public float getToleranceRate() {
        return toleranceRate;
    }

    public void setToleranceRate(float toleranceRate) {
        this.toleranceRate = toleranceRate;
    }
}
