package com.ibao.premezcla.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable{

    private List<OrdenDetalle> ordenDetalleList;

    public Recipe(){
     ordenDetalleList = new ArrayList<>();
    }
    public Recipe(List<OrdenDetalle> _od){
        ordenDetalleList = _od;
    }
    public List<OrdenDetalle> getOrdenDetalleList() {
        return ordenDetalleList;
    }

    public void setOrdenDetalleList(List<OrdenDetalle> ordenDetalleList) {
        this.ordenDetalleList = ordenDetalleList;
    }
}
