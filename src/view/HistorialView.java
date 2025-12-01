package view;

import controller.HistorialController;

public class HistorialView {

    private HistorialController controller;

    public HistorialView() {
        this.controller = new HistorialController();
    }

    public void mostrar() {
        controller.mostrarMenuHistorial();
    }
}
