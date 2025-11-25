package controller;

import model.Egreso;
import model.Ingreso;
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
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "1. Ver ingresos por ventas\n" +
                            "2. Registrar ingreso\n" +
                            "3. Registrar egreso\n" +
                            "4. Ver resumen financiero\n" +
                            "5. Salir"));

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
                case 5:
                    JOptionPane.showMessageDialog(null, "Volviendo al menú principal");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
                    break;
            }
        } while (opcion != 5);
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
        double totalVentas = ventaRepository.calcularTotalVentas();
        double totalIngresos = calcularTotalIngresos();
        double totalEgresos = calcularTotalEgresos();
        double balance = totalVentas + totalIngresos - totalEgresos;

        StringBuilder resumen = new StringBuilder();
        resumen.append("RESUMEN FINANCIERO\n\n");
        resumen.append("Ingresos por ventas: S/ ").append(totalVentas).append("\n");
        resumen.append("Otros ingresos: S/ ").append(totalIngresos).append("\n");
        resumen.append("Total ingresos: S/ ").append(totalVentas + totalIngresos).append("\n\n");
        resumen.append("Egresos: S/ ").append(totalEgresos).append("\n\n");
        resumen.append("─────────────────────────\n");
        resumen.append("BALANCE: S/ ").append(balance);

        JOptionPane.showMessageDialog(null, resumen.toString());
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
}
