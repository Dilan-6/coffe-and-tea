package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venta {
    private int id;
    private Date fecha;
    private List<DetalleVenta> detalles;
    private double total;
    private String cliente;

    public Venta() {
        this.detalles = new ArrayList<>();
        this.fecha = new Date();
        this.total = 0.0;
    }

    public Venta(Date fecha, String cliente) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
        calcularTotal();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
        calcularTotal();
    }

    public void eliminarDetalle(int indice) {
        if (indice >= 0 && indice < detalles.size()) {
            detalles.remove(indice);
            calcularTotal();
        }
    }

    private void calcularTotal() {
        total = 0.0;
        for (DetalleVenta detalle : detalles) {
            total += detalle.getSubtotal();
        }
    }

    public static class DetalleVenta {
        private Producto producto;
        private int cantidad;
        private double precioUnitario;
        private double subtotal;

        public DetalleVenta(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.precioUnitario = producto.getPrecioUnitario();
            this.subtotal = cantidad * precioUnitario;
        }

        public Producto getProducto() {
            return producto;
        }

        public void setProducto(Producto producto) {
            this.producto = producto;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
            this.subtotal = cantidad * precioUnitario;
        }

        public double getPrecioUnitario() {
            return precioUnitario;
        }

        public void setPrecioUnitario(double precioUnitario) {
            this.precioUnitario = precioUnitario;
            this.subtotal = cantidad * precioUnitario;
        }

        public double getSubtotal() {
            return subtotal;
        }
    }
}
