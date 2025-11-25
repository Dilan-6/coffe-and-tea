package controller;

import model.Boleta;
import model.Producto;
import model.Venta;
import repository.BoletaRepository;
import repository.ProductoRepository;
import repository.VentaRepository;
import utils.PrinterUtil;
import utils.ValidatorsUtil;
import javax.swing.JOptionPane;
import java.util.List;

public class VentaController {
    private ProductoRepository productoRepository;
    private VentaRepository ventaRepository;
    private BoletaRepository boletaRepository;
    private Venta ventaActual;

    public VentaController() {
        this.productoRepository = ProductoRepository.getInstancia();
        this.ventaRepository = VentaRepository.getInstancia();
        this.boletaRepository = BoletaRepository.getInstancia();
        this.ventaActual = new Venta();
    }

    public void mostrarMenuVentas() {
        String[] opciones = { "Agregar producto", "Eliminar producto", "Mostrar carrito",
                "Finalizar venta", "Salir" };
        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenido a Ventas ☕\nSeleccione una opción:",
                    "CofeandTea - Ventas",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            switch (opcion) {
                case 0:
                    agregarProductoAlCarrito();
                    break;
                case 1:
                    eliminarProductoDelCarrito();
                    break;
                case 2:
                    mostrarCarrito();
                    break;
                case 3:
                    finalizarVenta();
                    break;
                case 4:
                    if (ventaActual.getDetalles().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Volviendo al menú principal");
                    } else {
                        int confirmar = JOptionPane.showConfirmDialog(null,
                                "¿Desea cancelar la venta actual? Se perderán todos los productos agregados.",
                                "Confirmar cancelación",
                                JOptionPane.YES_NO_OPTION);
                        if (confirmar == JOptionPane.YES_OPTION) {
                            ventaActual = new Venta();
                            JOptionPane.showMessageDialog(null, "Venta cancelada. Volviendo al menú principal");
                        } else {
                            opcion = -1;
                        }
                    }
                    break;
            }
        } while (opcion != 4 && opcion != JOptionPane.CLOSED_OPTION);
    }

    public void agregarProductoAlCarrito() {
        List<Producto> productos = productoRepository.obtenerTodos();

        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos disponibles en el inventario.");
            return;
        }

        String listaProductos = construirListaProductosDisponibles(productos);
        String nombreProducto = JOptionPane.showInputDialog(
                "Productos disponibles:\n" + listaProductos + "\nIngrese el nombre del producto:");

        if (nombreProducto == null || nombreProducto.trim().isEmpty()) {
            return;
        }

        Producto producto = productoRepository.buscarPorNombre(nombreProducto.trim());

        if (producto == null) {
            JOptionPane.showMessageDialog(null, "Producto no encontrado");
            return;
        }

        if (producto.getStockActual() <= 0) {
            JOptionPane.showMessageDialog(null,
                    "Stock insuficiente.\nStock disponible: " + producto.getStockActual());
            return;
        }

        String cantidadStr = JOptionPane.showInputDialog(
                "Producto: " + producto.getNombre() + "\n" +
                        "Precio unitario: S/ " + producto.getPrecioUnitario() + "\n" +
                        "Stock disponible: " + producto.getStockActual() + "\n\n" +
                        "¿Cuántas unidades desea?");

        if (cantidadStr == null || !ValidatorsUtil.validarEnteroPositivo(cantidadStr)) {
            JOptionPane.showMessageDialog(null, "Cantidad inválida");
            return;
        }

        int cantidad = Integer.parseInt(cantidadStr);

        if (cantidad > producto.getStockActual()) {
            JOptionPane.showMessageDialog(null,
                    "Stock insuficiente.\nSolo quedan: " + producto.getStockActual() + " unidades");
            return;
        }

        Venta.DetalleVenta detalleExistente = buscarDetallePorProducto(producto);

        if (detalleExistente != null) {
            int nuevaCantidad = detalleExistente.getCantidad() + cantidad;
            if (nuevaCantidad > producto.getStockActual()) {
                JOptionPane.showMessageDialog(null,
                        "No hay suficiente stock. Ya tiene " + detalleExistente.getCantidad() +
                                " unidades en el carrito.\nStock disponible: " + producto.getStockActual());
                return;
            }
            detalleExistente.setCantidad(nuevaCantidad);
        } else {
            Venta.DetalleVenta nuevoDetalle = new Venta.DetalleVenta(producto, cantidad);
            ventaActual.agregarDetalle(nuevoDetalle);
        }

        producto.reducirStock(cantidad);
        productoRepository.actualizarProducto(producto);

        double subtotal = cantidad * producto.getPrecioUnitario();
        JOptionPane.showMessageDialog(null,
                "Agregado: " + cantidad + " " + producto.getNombre() + "\n" +
                        "Subtotal: S/ " + subtotal + "\n" +
                        "Stock restante: " + producto.getStockActual() + "\n" +
                        "Total del carrito: S/ " + ventaActual.getTotal());
    }

    public void eliminarProductoDelCarrito() {
        List<Venta.DetalleVenta> detalles = ventaActual.getDetalles();

        if (detalles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El carrito está vacío.");
            return;
        }

        String listaCarrito = construirListaCarrito(detalles);
        String opcionStr = JOptionPane.showInputDialog(
                "Productos en el carrito:\n" + listaCarrito + "\n" +
                        "Ingrese el número del producto a eliminar:");

        if (opcionStr == null || !ValidatorsUtil.validarEnteroPositivo(opcionStr)) {
            return;
        }

        int indice = Integer.parseInt(opcionStr) - 1;
        if (!ValidatorsUtil.validarRango(indice, 0, detalles.size())) {
            JOptionPane.showMessageDialog(null, "Opción inválida.");
            return;
        }

        Venta.DetalleVenta detalle = detalles.get(indice);
        Producto producto = detalle.getProducto();
        int cantidadEnCarrito = detalle.getCantidad();

        String cantidadStr = JOptionPane.showInputDialog(
                "Producto: " + producto.getNombre() + "\n" +
                        "Cantidad en carrito: " + cantidadEnCarrito + "\n\n" +
                        "¿Cuántas unidades desea eliminar?");

        if (cantidadStr == null || !ValidatorsUtil.validarEnteroPositivo(cantidadStr)) {
            return;
        }

        int cantidadEliminar = Integer.parseInt(cantidadStr);

        if (cantidadEliminar > cantidadEnCarrito) {
            JOptionPane.showMessageDialog(null,
                    "No puede eliminar más de lo que tiene en el carrito.");
            return;
        }

        producto.aumentarStock(cantidadEliminar);
        productoRepository.actualizarProducto(producto);

        if (cantidadEliminar == cantidadEnCarrito) {
            ventaActual.eliminarDetalle(indice);
        } else {
            detalle.setCantidad(cantidadEnCarrito - cantidadEliminar);
            ventaActual.setDetalles(detalles);
        }

        double resta = cantidadEliminar * producto.getPrecioUnitario();
        JOptionPane.showMessageDialog(null,
                "Producto eliminado correctamente.\n" +
                        "Restado del total: S/ " + resta + "\n" +
                        "Stock devuelto: " + cantidadEliminar + "\n" +
                        "Stock actual: " + producto.getStockActual() + "\n" +
                        "Total del carrito: S/ " + ventaActual.getTotal());
    }

    public void mostrarCarrito() {
        List<Venta.DetalleVenta> detalles = ventaActual.getDetalles();

        if (detalles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El carrito está vacío.");
            return;
        }

        String listaCarrito = construirListaCarrito(detalles);
        JOptionPane.showMessageDialog(null,
                listaCarrito + "\n" +
                "----------------------------------------\n" +
                "TOTAL: S/ " + String.format("%.2f", ventaActual.getTotal()));
    }

    public void mostrarTotal() {
        double total = ventaActual.getTotal();
        JOptionPane.showMessageDialog(null,
                "El total de la compra es: S/ " + total);
    }

    public void finalizarVenta() {
        if (ventaActual.getDetalles().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en el carrito.");
            return;
        }

        String cliente = JOptionPane.showInputDialog("Ingrese el nombre del cliente (opcional):");
        if (cliente == null) {
            cliente = "Cliente General";
        }
        ventaActual.setCliente(cliente.trim().isEmpty() ? "Cliente General" : cliente);

        if (ventaRepository.agregarVenta(ventaActual)) {
            String numeroBoleta = PrinterUtil.generarNumeroBoleta();
            Boleta boleta = new Boleta(ventaActual, numeroBoleta);
            boletaRepository.agregarBoleta(boleta);

            String textoBoleta = PrinterUtil.imprimirBoleta(boleta);
            
            int opcion = JOptionPane.showOptionDialog(
                    null,
                    "¡Venta finalizada exitosamente! ☕\n\n" + 
                    "¿Desea imprimir la boleta?",
                    "Venta Finalizada",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Imprimir", "Solo ver"},
                    "Imprimir");

            if (opcion == JOptionPane.YES_OPTION || opcion == 0) {
                PrinterUtil.imprimirEnDialogo("Boleta " + numeroBoleta, textoBoleta);
                PrinterUtil.imprimirEnConsola("\n" + textoBoleta);
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Resumen de venta:\n\n" + construirResumenVenta() + 
                        "\n\nNúmero de boleta: " + numeroBoleta);
            }

            ventaActual = new Venta();
        } else {
            JOptionPane.showMessageDialog(null, "Error al finalizar la venta.");
        }
    }

    private Venta.DetalleVenta buscarDetallePorProducto(Producto producto) {
        for (Venta.DetalleVenta detalle : ventaActual.getDetalles()) {
            if (detalle.getProducto().getId() == producto.getId()) {
                return detalle;
            }
        }
        return null;
    }

    private String construirListaProductosDisponibles(List<Producto> productos) {
        StringBuilder lista = new StringBuilder();
        lista.append("PRODUCTOS DISPONIBLES:\n\n");
        
        for (Producto producto : productos) {
            String nombre = producto.getNombre();
            if (nombre.length() > 25) {
                nombre = nombre.substring(0, 22) + "...";
            }
            lista.append(String.format("- %-25s | S/ %6.2f | Stock: %3d\n",
                    nombre, producto.getPrecioUnitario(), producto.getStockActual()));
        }
        return lista.toString();
    }

    private String construirListaCarrito(List<Venta.DetalleVenta> detalles) {
        StringBuilder lista = new StringBuilder();
        lista.append("CARRITO DE COMPRAS:\n\n");
        
        for (int i = 0; i < detalles.size(); i++) {
            Venta.DetalleVenta detalle = detalles.get(i);
            String nombre = detalle.getProducto().getNombre();
            if (nombre.length() > 22) {
                nombre = nombre.substring(0, 19) + "...";
            }
            lista.append(String.format("%2d. %-22s x%2d = S/ %7.2f\n",
                    i + 1, nombre, detalle.getCantidad(), detalle.getSubtotal()));
        }
        return lista.toString();
    }

    private String construirResumenVenta() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("Cliente: ").append(ventaActual.getCliente()).append("\n\n");
        resumen.append("Productos:\n");

        for (Venta.DetalleVenta detalle : ventaActual.getDetalles()) {
            resumen.append("- ").append(detalle.getProducto().getNombre())
                    .append(" x").append(detalle.getCantidad())
                    .append(" | S/ ").append(detalle.getSubtotal())
                    .append("\n");
        }

        resumen.append("\n─────────────────────────\n");
        resumen.append("TOTAL: S/ ").append(ventaActual.getTotal());

        return resumen.toString();
    }

}
