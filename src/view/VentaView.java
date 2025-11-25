package view;

import controller.VentaController;

public class VentaView {

    private VentaController controller;
    private CarritoView carritoView;

    public VentaView() {
        this.controller = new VentaController();
        this.carritoView = new CarritoView(controller);
    }

    public void mostrar() {
        controller.mostrarMenuVentas();
    }

    public void mostrarCarrito() {
        carritoView.mostrarMenuCarrito();
    }
}
