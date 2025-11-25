package controller;

import model.Venta;
import repository.VentaRepository;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistorialController {
    private VentaRepository ventaRepository;

    public HistorialController() {
        this.ventaRepository = VentaRepository.getInstancia();
    }

    public void mostrarMenuHistorial() {
        int opcion;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "1. Ver todas las ventas\n" +
                            "2. Ver últimas ventas\n" +
                            "3. Buscar venta por ID\n" +
                            "4. Ver estadísticas\n" +
                            "5. Salir"));

            switch (opcion) {
                case 1:
                    mostrarTodasLasVentas();
                    break;
                case 2:
                    mostrarUltimasVentas();
                    break;
                case 3:
                    buscarVentaPorId();
                    break;
                case 4:
                    mostrarEstadisticas();
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Volviendo al menú principal");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
                    break;
            }
        } while (opcion != 5);
    }

    public void mostrarTodasLasVentas() {
        List<Venta> ventas = ventaRepository.obtenerTodas();

        if (ventas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay ventas registradas.");
            return;
        }

        String historial = construirHistorialVentas(ventas);
        JOptionPane.showMessageDialog(null, "HISTORIAL DE VENTAS\n\n" + historial);
    }

    public void mostrarUltimasVentas() {
        String cantidadStr = JOptionPane.showInputDialog("¿Cuántas ventas desea ver?");
        if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            List<Venta> ventas = ventaRepository.obtenerUltimasVentas(cantidad);

            if (ventas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay ventas registradas.");
                return;
            }

            String historial = construirHistorialVentas(ventas);
            JOptionPane.showMessageDialog(null, "ÚLTIMAS " + cantidad + " VENTAS\n\n" + historial);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cantidad inválida");
        }
    }

    public void buscarVentaPorId() {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID de la venta:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            Venta venta = ventaRepository.buscarPorId(id);

            if (venta == null) {
                JOptionPane.showMessageDialog(null, "Venta no encontrada.");
                return;
            }

            String detalle = construirDetalleVenta(venta);
            JOptionPane.showMessageDialog(null, "DETALLE DE VENTA #" + id + "\n\n" + detalle);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido");
        }
    }

    public void mostrarEstadisticas() {
        List<Venta> ventas = ventaRepository.obtenerTodas();

        if (ventas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay ventas registradas.");
            return;
        }

        int totalVentas = ventas.size();
        double totalIngresos = ventaRepository.calcularTotalVentas();
        double promedioVenta = totalIngresos / totalVentas;

        double ventaMaxima = ventas.stream().mapToDouble(Venta::getTotal).max().orElse(0.0);
        double ventaMinima = ventas.stream().mapToDouble(Venta::getTotal).min().orElse(0.0);

        StringBuilder estadisticas = new StringBuilder();
        estadisticas.append("ESTADÍSTICAS DE VENTAS\n\n");
        estadisticas.append("Total de ventas: ").append(totalVentas).append("\n");
        estadisticas.append("Ingresos totales: S/ ").append(totalIngresos).append("\n");
        estadisticas.append("Promedio por venta: S/ ").append(String.format("%.2f", promedioVenta)).append("\n");
        estadisticas.append("Venta más alta: S/ ").append(ventaMaxima).append("\n");
        estadisticas.append("Venta más baja: S/ ").append(ventaMinima);

        JOptionPane.showMessageDialog(null, estadisticas.toString());
    }

    private String construirHistorialVentas(List<Venta> ventas) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        StringBuilder historial = new StringBuilder();

        for (Venta venta : ventas) {
            historial.append("Venta #").append(venta.getId()).append("\n");
            historial.append("Fecha: ").append(sdf.format(venta.getFecha())).append("\n");
            historial.append("Cliente: ").append(venta.getCliente()).append("\n");
            historial.append("Total: S/ ").append(venta.getTotal()).append("\n");
            historial.append("─────────────────────────\n");
        }

        return historial.toString();
    }

    private String construirDetalleVenta(Venta venta) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        StringBuilder detalle = new StringBuilder();

        detalle.append("Fecha: ").append(sdf.format(venta.getFecha())).append("\n");
        detalle.append("Cliente: ").append(venta.getCliente()).append("\n\n");
        detalle.append("Productos:\n");

        for (Venta.DetalleVenta detalleVenta : venta.getDetalles()) {
            detalle.append("- ").append(detalleVenta.getProducto().getNombre())
                    .append(" x").append(detalleVenta.getCantidad())
                    .append(" | S/ ").append(detalleVenta.getSubtotal())
                    .append("\n");
        }

        detalle.append("\n─────────────────────────\n");
        detalle.append("TOTAL: S/ ").append(venta.getTotal());

        return detalle.toString();
    }

    public void registrarHistorial() {
        String fecha = JOptionPane.showInputDialog(null, "Ingresa la fecha:");
        String detalle = JOptionPane.showInputDialog(null, "Ingresa el detalle:");

        if (fecha == null || fecha.trim().isEmpty() || detalle == null || detalle.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: Debes ingresar todos los campos.");
            return;
        }

        JOptionPane.showMessageDialog(null,
                "HISTORIAL REGISTRADO\n\n" +
                        "Fecha: " + fecha + "\n" +
                        "Detalle: " + detalle);
    }
}
