package view;

import controller.HistorialController;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class HistorialView extends JFrame {

    private HistorialController controller;

    public HistorialView() {
        this.controller = new HistorialController();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Historial de Ventas");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton btnVerHistorial = new JButton("Ver Historial Completo");
        btnVerHistorial.addActionListener(e -> controller.mostrarMenuHistorial());

        JPanel panel = new JPanel();
        panel.add(btnVerHistorial);
        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
