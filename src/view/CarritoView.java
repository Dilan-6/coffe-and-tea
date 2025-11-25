package view;

import controller.VentaController;
import javax.swing.JOptionPane;

public class CarritoView {

    private VentaController ventaController;

    public CarritoView() {
        this.ventaController = new VentaController();
    }

    public CarritoView(VentaController ventaController) {
        this.ventaController = ventaController;
    }

    public void mostrarCarrito() {
        ventaController.mostrarCarrito();
    }

    public void mostrarTotal() {
        ventaController.mostrarTotal();
    }

    public void mostrarMenuCarrito() {
        String[] opciones = { "Ver carrito", "Ver total", "Agregar producto", "Eliminar producto", "Volver" };
        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                    null,
                    "Carrito de Compras 🛒\nSeleccione una opción:",
                    "CofeandTea - Carrito",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            switch (opcion) {
                case 0:
                    mostrarCarrito();
                    break;
                case 1:
                    mostrarTotal();
                    break;
                case 2:
                    ventaController.agregarProductoAlCarrito();
                    break;
                case 3:
                    ventaController.eliminarProductoDelCarrito();
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Volviendo al menú de ventas");
                    break;
            }
        } while (opcion != 4 && opcion != JOptionPane.CLOSED_OPTION);
    }
}
