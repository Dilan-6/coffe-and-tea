package model;

import java.util.Date;

public class Ingreso {
    private int id;
    private String descripcion;
    private double monto;
    private Date fecha;
    private Venta venta;

    public Ingreso() {
        this.fecha = new Date();
    }

    public Ingreso(String descripcion, double monto) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.fecha = new Date();
    }

    public Ingreso(String descripcion, double monto, Venta venta) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.venta = venta;
        this.fecha = new Date();
    }

    public int getId()  {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}
