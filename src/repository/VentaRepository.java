package repository;

import model.Venta;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Repositorio singleton para gestionar ventas
 * Comparte datos entre todas las partes del sistema
 */
public class VentaRepository {
    private static VentaRepository instancia;
    private List<Venta> ventas;
    private int siguienteId;

    private VentaRepository() {
        this.ventas = new ArrayList<>();
        this.siguienteId = 1;
    }

    /**
     * Obtiene la instancia única del repositorio (Singleton)
     * 
     * @return La instancia del repositorio
     */
    public static synchronized VentaRepository getInstancia() {
        if (instancia == null) {
            instancia = new VentaRepository();
        }
        return instancia;
    }

    /**
     * Obtiene todas las ventas
     * 
     * @return Lista de todas las ventas
     */
    public List<Venta> obtenerTodas() {
        return new ArrayList<>(ventas);
    }

    /**
     * Busca una venta por su ID
     * 
     * @param id El ID de la venta
     * @return La venta encontrada o null si no existe
     */
    public Venta buscarPorId(int id) {
        return ventas.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Agrega una nueva venta al repositorio
     * 
     * @param venta La venta a agregar
     * @return true si se agregó correctamente
     */
    public boolean agregarVenta(Venta venta) {
        if (venta == null) {
            return false;
        }
        venta.setId(siguienteId++);
        ventas.add(venta);
        return true;
    }

    /**
     * Obtiene las ventas de una fecha específica
     * 
     * @param fecha La fecha a buscar
     * @return Lista de ventas de esa fecha
     */
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

    /**
     * Calcula el total de todas las ventas
     * 
     * @return El total de todas las ventas
     */
    public double calcularTotalVentas() {
        return ventas.stream()
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    /**
     * Calcula el total de ventas de una fecha específica
     * 
     * @param fecha La fecha
     * @return El total de ventas de esa fecha
     */
    public double calcularTotalVentasPorFecha(Date fecha) {
        return obtenerVentasPorFecha(fecha).stream()
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    /**
     * Obtiene el número total de ventas
     * 
     * @return El número total de ventas
     */
    public int contarVentas() {
        return ventas.size();
    }

    /**
     * Obtiene las últimas N ventas
     * 
     * @param cantidad La cantidad de ventas a obtener
     * @return Lista de las últimas ventas
     */
    public List<Venta> obtenerUltimasVentas(int cantidad) {
        int inicio = Math.max(0, ventas.size() - cantidad);
        return new ArrayList<>(ventas.subList(inicio, ventas.size()));
    }
}
