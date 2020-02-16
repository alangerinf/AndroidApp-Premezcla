package com.ibao.premescla.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tancada  implements Serializable {

    /**
        'id'=> $idTancada_,
        'idOrden'=>  $idOrden_,
        'nroTancada'=>  $nroTancada_,
        'productosPesados' => $productoPesadoDAO->selectByIdTancada($idTancada_)
     */

    private int id;
    private int idOrden;
    private int nroTancada;
    private List<ProductoPesado> productosPesados;

    public Tancada() {
        this.id = 0;
        this.idOrden = 0;
        this.nroTancada = 0;
        this.productosPesados = new ArrayList<>();
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

    public int getNroTancada() {
        return nroTancada;
    }

    public void setNroTancada(int nroTancada) {
        this.nroTancada = nroTancada;
    }

    public List<ProductoPesado> getProductosPesados() {
        return productosPesados;
    }

    public void setProductosPesados(List<ProductoPesado> productosPesados) {
        this.productosPesados = productosPesados;
    }
}
