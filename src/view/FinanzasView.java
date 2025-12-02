package view;

import controller.FinanzasController;

public class FinanzasView {

    private FinanzasController controller;

    public FinanzasView() {
        this.controller = new FinanzasController();
    }

    public void mostrar() {
        controller.mostrarMenuFinanzas();
    }
}
