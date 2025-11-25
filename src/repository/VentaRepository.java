package repository;

import model.Venta;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

public class VentaRepository {
    private static VentaRepository instancia;
    private List<Venta> ventas;
    private int siguienteId;

    private VentaRepository() {
        this.ventas = new ArrayList<>();
        this.siguienteId = 1;
    }

    public static synchronized VentaRepository getInstancia() {
        if (instancia == null) {
            instancia = new VentaRepository();
        }
        return instancia;
    }

    public List<Venta> obtenerTodas() {
        return new ArrayList<>(ventas);
    }

    public Venta buscarPorId(int id) {
        return ventas.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean agregarVenta(Venta venta) {
        if (venta == null) {
            return false;
        }
        venta.setId(siguienteId++);
        ventas.add(venta);
        return true;
    }

    public List<Venta> obtenerVentasPorFecha(Date fecha) {
        Calendar calFecha = Calendar.getInstance();
        calFecha.setTime(fecha);
        int año = calFecha.get(Calendar.YEAR);
        int mes = calFecha.get(Calendar.MONTH);
        int dia = calFecha.get(Calendar.DAY_OF_MONTH);

        return ventas.stream()
                .filter(v -> {
                    Calendar calVenta = Calendar.getInstance();
                    calVenta.setTime(v.getFecha());
                    return calVenta.get(Calendar.YEAR) == año &&
                            calVenta.get(Calendar.MONTH) == mes &&
                            calVenta.get(Calendar.DAY_OF_MONTH) == dia;
                })
                .collect(Collectors.toList());
    }

    public double calcularTotalVentas() {
        return ventas.stream()
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public double calcularTotalVentasPorFecha(Date fecha) {
        return obtenerVentasPorFecha(fecha).stream()
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public int contarVentas() {
        return ventas.size();
    }

    public List<Venta> obtenerUltimasVentas(int cantidad) {
        int inicio = Math.max(0, ventas.size() - cantidad);
        return new ArrayList<>(ventas.subList(inicio, ventas.size()));
    }
}
