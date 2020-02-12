package com.ibao.premescla.models;

public class ProductoPesado {

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

    public ProductoPesado() {

        this.id = 0;
        this.idTancada = 0;
        this.idOrdenDetalle = 0;
        this.cantidadPesada = 0;
        this.fechaPesada = "";

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
