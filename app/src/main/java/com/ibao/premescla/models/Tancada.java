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
    private float pH;
    private float conductividad;
    private List<ProductoPesado> productosPesados;


    private int idTractor;
    private int idConductor;

    public Tancada() {
        this.id = 0;
        this.idOrden = 0;
        this.nroTancada = 0;
        this.pH = 0f;
        this.conductividad = 0f;
        this.productosPesados = new ArrayList<>();
        this.idTractor = 0;
        this.idConductor = 0;

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

    public String parseToQR(){
        return "T"+id;
    }

    public static int getIdparseFromQR(String qr) throws NullPointerException{
        int id =0;
        try {
            if(qr.charAt(0)=='T'){
                id = Integer.parseInt(qr.substring(1));
            }else {
                new NullPointerException("QR no pertenece a una tancada");
            }
        }catch (Exception e){
            new NullPointerException("QR no válido");
        }
        return id;
    }

    public float getpH() {
        return pH;
    }

    public void setpH(float pH) {
        this.pH = pH;
    }

    public float getConductividad() {
        return conductividad;
    }

    public void setConductividad(float conductividad) {
        this.conductividad = conductividad;
    }

    public int getIdTractor() {
        return idTractor;
    }

    public void setIdTractor(int idTractor) {
        this.idTractor = idTractor;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }
}
