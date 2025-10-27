/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import controller.HistorialController;

public class HistorialView extends JFrame {
     
  public HistorialView() {

        setTitle("Historial (Solo Vista)");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnVerHistorial = new JButton("Registrar Historial");

        btnVerHistorial.addActionListener(e -> {
            HistorialController controller = new HistorialController();
            controller.registrarHistorial();
        });

        JPanel panel = new JPanel();
        panel.add(btnVerHistorial);
        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
}
