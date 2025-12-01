package view;

import controller.VentaController;

public class VentaView {

    private VentaController controller;

    public VentaView() {
        this.controller = new VentaController();
    }

    public void mostrar() {
        controller.mostrarMenuVentas();
    }
}
