
package view;

import javax.swing.JOptionPane;

public class VentaView {

    public void mostrar() {
        String[] inventario = { "cafe", "capuchino" };
        double[] precio = { 5, 8 };
        int[] stock = { 4, 4 };
        String[] opciones = { "Agregar producto", "Eliminar producto", "Mostrar total", "Salir" };

        int[] vendido = new int[inventario.length];

        int opcion;
        double total = 0;

        do {

            opcion = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenido a Ventas ☕\nSeleccione una opción:",
                    "CofeandTea - Ventas",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            switch (opcion) {

                case 0:
                    String productoAgregar = JOptionPane.showInputDialog("Producto a agregar:");
                    boolean encontradoAgregar = false;

                    for (int i = 0; i < inventario.length; i++) {
                        if (productoAgregar.equalsIgnoreCase(inventario[i])) {
                            encontradoAgregar = true;

                            int cantidad = Integer.parseInt(
                                    JOptionPane.showInputDialog(
                                            "Stock actual: " + stock[i]
                                                    + "\n¿Cuántas unidades desea?"));

                            if (cantidad <= stock[i]) {

                                stock[i] -= cantidad;
                                vendido[i] += cantidad;

                                double subtotal = cantidad * precio[i];
                                total += subtotal;

                                JOptionPane.showMessageDialog(null,
                                        "Agregado: " + cantidad + " " + inventario[i]
                                                + "\nSubtotal: S/ " + subtotal
                                                + "\nStock restante: " + stock[i]);

                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Stock insuficiente.\nSolo quedan: " + stock[i]);
                            }
                        }
                    }

                    if (!encontradoAgregar) {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado");
                    }

                    break;

                case 1:
                    String productoEliminar = JOptionPane.showInputDialog("Producto a eliminar:");
                    boolean encontradoEliminar = false;

                    for (int i = 0; i < inventario.length; i++) {

                        if (productoEliminar.equalsIgnoreCase(inventario[i])) {
                            encontradoEliminar = true;

                            if (vendido[i] == 0) {
                                JOptionPane.showMessageDialog(null,
                                        "No ha comprado este producto aún.");
                                break;
                            }

                            int cantidadEliminar = Integer.parseInt(
                                    JOptionPane.showInputDialog(
                                            "Ha comprado: " + vendido[i]
                                                    + "\n¿Cuántas unidades desea eliminar?"));

                            if (cantidadEliminar <= vendido[i]) {

                                vendido[i] -= cantidadEliminar;
                                stock[i] += cantidadEliminar;

                                double resta = cantidadEliminar * precio[i];
                                total -= resta;

                                JOptionPane.showMessageDialog(null,
                                        "Producto eliminado correctamente."
                                                + "\nRestado del total: S/ " + resta
                                                + "\nStock actual: " + stock[i]);

                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "No puede eliminar más de lo que compró.");
                            }
                        }
                    }

                    if (!encontradoEliminar) {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                    }

                    break;

                case 2:
                    JOptionPane.showMessageDialog(null,
                            "El total de la compra es: S/ " + total);
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null,
                            "Volviendo al menu principal");
                    break;
            }

        } while (opcion != 3 && opcion != JOptionPane.CLOSED_OPTION);

    }
}