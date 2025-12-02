package model;

import java.util.Date;

public class Boleta {
    private int id;
    private Venta venta;
    private Date fechaEmision;
    private String numeroBoleta;

    public Boleta() {
        this.fechaEmision = new Date();
    }

    public Boleta(Venta venta, String numeroBoleta) {
        this.venta = venta;
        this.numeroBoleta = numeroBoleta;
        this.fechaEmision = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getNumeroBoleta() {
        return numeroBoleta;
    }

    public void setNumeroBoleta(String numeroBoleta) {
        this.numeroBoleta = numeroBoleta;
    }
}
