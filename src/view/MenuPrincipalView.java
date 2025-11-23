package view;

import javax.swing.JOptionPane;

public class MenuPrincipalView {

    public void mostrarMenu() {
        String[] opciones = {"Ventas", "Inventario", "Historial", "Finanzas"};
        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenido a CofeandTea ☕\nSeleccione una opción:",
                    "CofeandTea - Menú Principal",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (opcion) {
                case 0:
                    new VentaView().mostrar();
                    break;
                case 1:
                    new InventarioView().mostrarInventario();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Mostrando Historial de Ventas...");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Entrando a Finanzas...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Hasta pronto ☕");
                    break;
            }

        } while (opcion != 4 && opcion != JOptionPane.CLOSED_OPTION);

    }

}
