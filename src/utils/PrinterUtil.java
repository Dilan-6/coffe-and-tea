package utils;

import model.Boleta;
import model.Venta;
import java.text.SimpleDateFormat;

public class PrinterUtil {

    private static int numeroBoletaContador = 1;

    public static String generarNumeroBoleta() {
        String numero = String.format("BOL-%04d", numeroBoletaContador++);
        return numero;
    }

    public static String imprimirBoleta(Boleta boleta) {
        Venta venta = boleta.getVenta();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        StringBuilder boletaTexto = new StringBuilder();
        
        boletaTexto.append("═══════════════════════════════════\n");
        boletaTexto.append("         COFEANDTEA ☕\n");
        boletaTexto.append("═══════════════════════════════════\n");
        boletaTexto.append("Boleta N°: ").append(boleta.getNumeroBoleta()).append("\n");
        boletaTexto.append("Fecha: ").append(sdf.format(boleta.getFechaEmision())).append("\n");
        boletaTexto.append("Cliente: ").append(venta.getCliente()).append("\n");
        boletaTexto.append("───────────────────────────────────\n");
        boletaTexto.append("PRODUCTOS:\n");
        boletaTexto.append("───────────────────────────────────\n");
        
        for (Venta.DetalleVenta detalle : venta.getDetalles()) {
            String nombre = detalle.getProducto().getNombre();
            int cantidad = detalle.getCantidad();
            double precioUnitario = detalle.getPrecioUnitario();
            double subtotal = detalle.getSubtotal();
            
            boletaTexto.append(String.format("%-15s", nombre));
            boletaTexto.append(String.format("x%2d", cantidad));
            boletaTexto.append(String.format("  S/ %6.2f", precioUnitario));
            boletaTexto.append(String.format("  S/ %7.2f", subtotal));
            boletaTexto.append("\n");
        }
        
        boletaTexto.append("───────────────────────────────────\n");
        boletaTexto.append(String.format("%-25s", "TOTAL:"));
        boletaTexto.append(String.format("S/ %7.2f", venta.getTotal()));
        boletaTexto.append("\n");
        boletaTexto.append("═══════════════════════════════════\n");
        boletaTexto.append("     ¡Gracias por su compra!\n");
        boletaTexto.append("═══════════════════════════════════\n");
        
        return boletaTexto.toString();
    }

    public static void imprimirEnConsola(String texto) {
        System.out.println(texto);
    }

    public static void imprimirEnDialogo(String titulo, String texto) {
        javax.swing.JOptionPane.showMessageDialog(null, texto, titulo, 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
}
