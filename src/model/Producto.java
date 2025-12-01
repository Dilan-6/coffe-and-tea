package model;

public class Producto {
    private int id;
    private String nombre;
    private int stockActual;
    private int stockMinimo;
    private double precioUnitario; 
    private double precioCosto; 

    public Producto() {
    }

    public Producto(String nombre, int stockActual, int stockMinimo, double precioUnitario, double precioCosto) {
        this.nombre = nombre;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.precioUnitario = precioUnitario;
        this.precioCosto = precioCosto;
    }

    public Producto(int id, String nombre, int stockActual, int stockMinimo, double precioUnitario, double precioCosto) {
        this.id = id;
        this.nombre = nombre;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.precioUnitario = precioUnitario;
        this.precioCosto = precioCosto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public boolean necesitaReposicion() {
        return stockActual < stockMinimo;
    }

    public boolean reducirStock(int cantidad) {
        if (cantidad > 0 && cantidad <= stockActual) {
            stockActual -= cantidad;
            return true;
        }
        return false;
    }

    public void aumentarStock(int cantidad) {
        if (cantidad > 0) {
            stockActual += cantidad;
        }
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
                "Stock actual: " + stockActual + "\n" +
                "Stock mínimo: " + stockMinimo + "\n" +
                "Precio venta: " + precioUnitario + "\n" +
                "Precio costo: " + precioCosto;
    }
}
