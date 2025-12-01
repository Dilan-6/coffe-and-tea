package controller;

import model.Egreso;
import model.Ingreso;
import model.Venta;
import repository.VentaRepository;
import utils.ValidatorsUtil;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class FinanzasController {
    private VentaRepository ventaRepository;
    private List<Ingreso> ingresos;
    private List<Egreso> egresos;

    public FinanzasController() {
        this.ventaRepository = VentaRepository.getInstancia();
        this.ingresos = new ArrayList<>();
        this.egresos = new ArrayList<>();
    }

    public void mostrarMenuFinanzas() {
        int opcion;
        do {
            String opcionStr = JOptionPane.showInputDialog(
                    "1. Ver ingresos por ventas\n" +
                            "2. Registrar ingreso\n" +
                            "3. Registrar egreso\n" +
                            "4. Ver resumen financiero\n" +
                            "Presione Cancelar para salir");

            if (opcionStr == null) {
                // Usuario presionó Cancelar
                JOptionPane.showMessageDialog(null, "Volviendo al menú principal");
                break;
            }

            try {
                opcion = Integer.parseInt(opcionStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opción inválida");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1:
                    mostrarIngresosPorVentas();
                    break;
                case 2:
                    registrarIngreso();
                    break;
                case 3:
                    registrarEgreso();
                    break;
                case 4:
                    mostrarResumenFinanciero();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
                    break;
            }
        } while (true);
    }

    public void mostrarIngresosPorVentas() {
        double totalVentas = ventaRepository.calcularTotalVentas();
        int cantidadVentas = ventaRepository.contarVentas();

        JOptionPane.showMessageDialog(null,
                "INGRESOS POR VENTAS\n\n" +
                        "Total de ventas: " + cantidadVentas + "\n" +
                        "Ingreso total: S/ " + totalVentas);
    }

    public void registrarIngreso() {
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripción del ingreso:");
        if (descripcion == null || descripcion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Operación cancelada");
            return;
        }

        String montoStr = JOptionPane.showInputDialog("Ingrese el monto:");
        if (montoStr == null || !ValidatorsUtil.validarDecimalPositivo(montoStr)) {
            JOptionPane.showMessageDialog(null, "Monto inválido");
            return;
        }

        double monto = Double.parseDouble(montoStr);
        Ingreso ingreso = new Ingreso(descripcion, monto);
        ingresos.add(ingreso);

        JOptionPane.showMessageDialog(null,
                "Ingreso registrado correctamente\n\n" +
                        "Descripción: " + descripcion + "\n" +
                        "Monto: S/ " + monto);
    }

    public void registrarEgreso() {
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripción del egreso:");
        if (descripcion == null || descripcion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Operación cancelada");
            return;
        }

        String montoStr = JOptionPane.showInputDialog("Ingrese el monto:");
        if (montoStr == null || !ValidatorsUtil.validarDecimalPositivo(montoStr)) {
            JOptionPane.showMessageDialog(null, "Monto inválido");
            return;
        }

        double monto = Double.parseDouble(montoStr);

        String categoria = JOptionPane.showInputDialog("Ingrese la categoría (opcional):");
        if (categoria == null) {
            categoria = "General";
        }

        Egreso egreso = new Egreso(descripcion, monto, categoria);
        egresos.add(egreso);

        JOptionPane.showMessageDialog(null,
                "Egreso registrado correctamente\n\n" +
                        "Descripción: " + descripcion + "\n" +
                        "Monto: S/ " + monto + "\n" +
                        "Categoría: " + categoria);
    }

    public void mostrarResumenFinanciero() {
        List<Venta> ventas = ventaRepository.obtenerTodas();
        double totalVentas = ventaRepository.calcularTotalVentas();
        double totalCostoVentas = calcularCostoTotalVentas();
        double totalIngresos = calcularTotalIngresos();
        double totalEgresosManuales = calcularTotalEgresos();
        double gananciaBruta = totalVentas - totalCostoVentas;
        double balance = totalVentas + totalIngresos - totalCostoVentas - totalEgresosManuales;

        StringBuilder registro = new StringBuilder();
        registro.append("REGISTRO FINANCIERO\n");
        registro.append("═══════════════════════════════════════\n\n");

        registro.append("INGRESOS:\n");
        registro.append("─────────────────────────────────────\n");
        registro.append("Ventas realizadas: ").append(ventas.size()).append("\n");
        registro.append("Total ingresos por ventas: S/ ").append(String.format("%.2f", totalVentas)).append("\n");
        registro.append("Otros ingresos: S/ ").append(String.format("%.2f", totalIngresos)).append("\n");
        registro.append("TOTAL INGRESOS: S/ ").append(String.format("%.2f", totalVentas + totalIngresos)).append("\n\n");

        registro.append("EGRESOS:\n");
        registro.append("─────────────────────────────────────\n");
        registro.append("Costo de productos vendidos: S/ ").append(String.format("%.2f", totalCostoVentas)).append("\n");
        registro.append("Egresos manuales registrados: S/ ").append(String.format("%.2f", totalEgresosManuales)).append("\n");
        registro.append("TOTAL EGRESOS: S/ ").append(String.format("%.2f", totalCostoVentas + totalEgresosManuales)).append("\n\n");

        registro.append("RESUMEN:\n");
        registro.append("─────────────────────────────────────\n");
        registro.append("Ganancia bruta (Ventas - Costos): S/ ").append(String.format("%.2f", gananciaBruta)).append("\n");
        registro.append("BALANCE FINAL: S/ ").append(String.format("%.2f", balance)).append("\n");

        JOptionPane.showMessageDialog(null, registro.toString());
    }

    private double calcularTotalIngresos() {
        return ingresos.stream()
                .mapToDouble(Ingreso::getMonto)
                .sum();
    }

    private double calcularTotalEgresos() {
        return egresos.stream()
                .mapToDouble(Egreso::getMonto)
                .sum();
    }

    private double calcularCostoTotalVentas() {
        List<Venta> ventas = ventaRepository.obtenerTodas();
        double costoTotal = 0.0;

        for (Venta venta : ventas) {
            for (Venta.DetalleVenta detalle : venta.getDetalles()) {
                double precioCosto = detalle.getProducto().getPrecioCosto();
                int cantidad = detalle.getCantidad();
                costoTotal += precioCosto * cantidad;
            }
        }

        return costoTotal;
    }
}
