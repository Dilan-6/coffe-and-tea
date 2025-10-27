
package controller;

import javax.swing.JOptionPane;

public class HistorialController {
    
    public void registrarHistorial(){
    
    String fecha = JOptionPane.showInputDialog(null, "Ingresa la fecha:");
    String detalle = JOptionPane.showInputDialog(null, "Ingresa el detalle:");
    
     if (fecha == null || fecha.trim().isEmpty() || detalle == null || detalle.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: Debes ingresar todos los campos.");
            return;
        }
                JOptionPane.showMessageDialog(null,
                "HISTORIAL REGISTRADO\n\n" +
                "Fecha: " + fecha + "\n" +
                "Detalle: " + detalle
        );
    
    }
}
