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
    private double mojamiento;
    private List<ProductoPesado> productosPesados;
    private List<Muestra> muestras;
    private String estadoTancada;

    private String codeLote;
    private int idLote;

    private String nombreTractor;
    private String nombreConductor;


    private String fechaQRTancada;
    private String fechaVaceadoMezcla;
    private String fechaTrasladoCampo;
    private String fechaInicioAplicacion;
    private String fechaFinAplicacion;
    private String fechaRetornoProducto;

    private String ph;
    private String fechaPh;

    private String conductividad;
    private String fechaConductividad;

    private int usuario;


    public Tancada() {
        this.id = 0;
        this.idOrden = 0;
        this.nroTancada = 0;
        this.mojamiento=0;
        this.productosPesados = new ArrayList<>();
        this.muestras = new ArrayList<>();
        this.codeLote = "";
        this.estadoTancada = "";

        this.nombreTractor= "";
        this.nombreConductor= "";

        this.fechaQRTancada= "";
        this.fechaVaceadoMezcla= "";
        this.fechaTrasladoCampo= "";
        this.fechaInicioAplicacion= "";
        this.fechaFinAplicacion= "";
        this.fechaRetornoProducto= "";

        this.ph= "";
        this.fechaPh= "";

        this.conductividad= "";
        this.fechaConductividad= "";



        this.usuario=0;
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

    public List<Muestra> getMuestras() {
        return muestras;
    }

    public void setMuestras(List<Muestra> muestras) {
        this.muestras = muestras;
    }

    public double getMojamiento() {
        return mojamiento;
    }

    public void setMojamiento(double mojamiento) {
        this.mojamiento = mojamiento;
    }

    public String getCodeLote() {
        return codeLote;
    }

    public void setCodeLote(String codeLote) {
        this.codeLote = codeLote;
    }

    public String getEstadoTancada() {
        return estadoTancada;
    }

    public void setEstadoTancada(String estadoTancada) {
        this.estadoTancada = estadoTancada;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public String getNombreTractor() {
        return nombreTractor;
    }

    public void setNombreTractor(String nombreTractor) {
        this.nombreTractor = nombreTractor;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getFechaQRTancada() {
        return fechaQRTancada;
    }

    public void setFechaQRTancada(String fechaQRTancada) {
        this.fechaQRTancada = fechaQRTancada;
    }

    public String getFechaVaceadoMezcla() {
        return fechaVaceadoMezcla;
    }

    public void setFechaVaceadoMezcla(String fechaVaceadoMezcla) {
        this.fechaVaceadoMezcla = fechaVaceadoMezcla;
    }

    public String getFechaTrasladoCampo() {
        return fechaTrasladoCampo;
    }

    public void setFechaTrasladoCampo(String fechaTrasladoCampo) {
        this.fechaTrasladoCampo = fechaTrasladoCampo;
    }

    public String getFechaInicioAplicacion() {
        return fechaInicioAplicacion;
    }

    public void setFechaInicioAplicacion(String fechaInicioAplicacion) {
        this.fechaInicioAplicacion = fechaInicioAplicacion;
    }

    public String getFechaFinAplicacion() {
        return fechaFinAplicacion;
    }

    public void setFechaFinAplicacion(String fechaFinAplicacion) {
        this.fechaFinAplicacion = fechaFinAplicacion;
    }

    public String getFechaRetornoProducto() {
        return fechaRetornoProducto;
    }

    public void setFechaRetornoProducto(String fechaRetornoProducto) {
        this.fechaRetornoProducto = fechaRetornoProducto;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getFechaPh() {
        return fechaPh;
    }

    public void setFechaPh(String fechaPh) {
        this.fechaPh = fechaPh;
    }

    public String getConductividad() {
        return conductividad;
    }

    public void setConductividad(String conductividad) {
        this.conductividad = conductividad;
    }

    public String getFechaConductividad() {
        return fechaConductividad;
    }

    public void setFechaConductividad(String fechaConductividad) {
        this.fechaConductividad = fechaConductividad;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }
}
