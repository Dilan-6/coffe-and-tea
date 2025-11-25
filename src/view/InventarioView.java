package view;

import controller.InventarioController;

public class InventarioView {

    private InventarioController controller;

    public InventarioView() {
        this.controller = new InventarioController();
    }

    public void mostrarInventario() {
        controller.mostrarMenuInventario();
    }
}
