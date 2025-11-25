package controller;

import model.Producto;
import repository.ProductoRepository;
import utils.ValidatorsUtil;
import javax.swing.JOptionPane;
import java.util.List;

public class InventarioController {
    private ProductoRepository productoRepository;

    public InventarioController() {
        this.productoRepository = ProductoRepository.getInstancia();
    }

    public void mostrarMenuInventario() {
        int opcion;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "1. Agregar producto\n" +
                            "2. Modificar producto\n" +
                            "3. Ver productos\n" +
                            "4. Ver productos con stock bajo\n" +
                            "5. Salir"));

            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    modificarProducto();
                    break;
                case 3:
                    mostrarProductos();
                    break;
                case 4:
                    mostrarProductosConStockBajo();
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Volviendo al menú principal");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
                    break;
            }
        } while (opcion != 5);
    }

    public void agregarProducto() {
        String nombre = solicitarNombreProducto();
        if (nombre == null) {
            return; // Usuario canceló
        }

        // Verificar si ya existe
        if (productoRepository.existeProducto(nombre)) {
            JOptionPane.showMessageDialog(null, "Ya existe un producto con ese nombre");
            return;
        }

        int stockActual = solicitarStock("Ingrese el stock actual");
        if (stockActual < 0) {
            return; // Usuario canceló o valor inválido
        }

        int stockMinimo = solicitarStock("Ingrese el stock mínimo");
        if (stockMinimo < 0) {
            return; // Usuario canceló o valor inválido
        }

        double precio = solicitarPrecio();
        if (precio < 0) {
            return; // Usuario canceló o valor inválido
        }

        Producto producto = new Producto(nombre, stockActual, stockMinimo, precio);

        if (productoRepository.agregarProducto(producto)) {
            JOptionPane.showMessageDialog(null,
                    "Producto registrado correctamente\n\n" + producto.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar el producto");
        }
    }

    public void modificarProducto() {
        List<Producto> productos = productoRepository.obtenerTodos();

        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados aún.");
            return;
        }

        // Mostrar lista de productos
        String lista = construirListaProductos(productos);
        String opcionStr = JOptionPane.showInputDialog(
                "¿Qué producto quiere modificar?\n" + lista);

        if (opcionStr == null || !ValidatorsUtil.validarEnteroPositivo(opcionStr)) {
            JOptionPane.showMessageDialog(null, "Operación cancelada o valor inválido.");
            return;
        }

        int indice = Integer.parseInt(opcionStr) - 1;
        if (!ValidatorsUtil.validarRango(indice, 0, productos.size())) {
            JOptionPane.showMessageDialog(null, "Opción inválida.");
            return;
        }

        Producto producto = productos.get(indice);
        modificarAtributoProducto(producto);
    }

    private void modificarAtributoProducto(Producto producto) {
        String modificacion = JOptionPane.showInputDialog(
                "¿Qué parte quiere modificar?\n(nombre / stock / minimo / precio)\n\n" +
                        producto.toString());

        if (modificacion == null) {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
            return;
        }

        switch (modificacion.toLowerCase()) {
            case "nombre":
                String nuevoNombre = solicitarNombreProducto();
                if (nuevoNombre != null) {
                    producto.setNombre(nuevoNombre);
                    productoRepository.actualizarProducto(producto);
                    JOptionPane.showMessageDialog(null,
                            "Producto modificado correctamente.\n" + producto.toString());
                }
                break;

            case "stock":
                int nuevoStock = solicitarStock("Introduzca nuevo stock actual");
                if (nuevoStock >= 0) {
                    producto.setStockActual(nuevoStock);
                    productoRepository.actualizarProducto(producto);
                    JOptionPane.showMessageDialog(null,
                            "Producto modificado correctamente.\n" + producto.toString());
                }
                break;

            case "minimo":
                int nuevoMin = solicitarStock("Introduzca nuevo stock mínimo");
                if (nuevoMin >= 0) {
                    producto.setStockMinimo(nuevoMin);
                    productoRepository.actualizarProducto(producto);
                    JOptionPane.showMessageDialog(null,
                            "Producto modificado correctamente.\n" + producto.toString());
                }
                break;

            case "precio":
                double nuevoPrecio = solicitarPrecio();
                if (nuevoPrecio >= 0) {
                    producto.setPrecioUnitario(nuevoPrecio);
                    productoRepository.actualizarProducto(producto);
                    JOptionPane.showMessageDialog(null,
                            "Producto modificado correctamente.\n" + producto.toString());
                }
                break;

            default:
                JOptionPane.showMessageDialog(null,
                        "Opción no válida.\nSolo se acepta:\n- nombre\n- stock\n- minimo\n- precio");
                break;
        }
    }

    public void mostrarProductos() {
        List<Producto> productos = productoRepository.obtenerTodos();

        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados.");
            return;
        }

        String lista = construirListaProductos(productos);
        JOptionPane.showMessageDialog(null, "Lista de Productos:\n\n" + lista);
    }

    public void mostrarProductosConStockBajo() {
        List<Producto> productosBajo = productoRepository.obtenerProductosConStockBajo();

        if (productosBajo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos con stock bajo.");
            return;
        }

        String lista = construirListaProductos(productosBajo);
        JOptionPane.showMessageDialog(null, "Productos con Stock Bajo:\n\n" + lista);
    }

    private String solicitarNombreProducto() {
        String nombre;
        do {
            nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto");

            if (nombre == null) {
                return null; // Usuario canceló
            }

            if (!ValidatorsUtil.validarNombreProducto(nombre)) {
                JOptionPane.showMessageDialog(null,
                        "Nombre inválido. No debe contener números ni estar vacío.");
            }
        } while (!ValidatorsUtil.validarNombreProducto(nombre));

        return nombre;
    }

    private int solicitarStock(String mensaje) {
        String stockStr;
        do {
            stockStr = JOptionPane.showInputDialog(mensaje);

            if (stockStr == null) {
                return -1; // Usuario canceló
            }

            if (!ValidatorsUtil.validarEnteroPositivo(stockStr)) {
                JOptionPane.showMessageDialog(null, "No se introdujo un valor válido");
            }
        } while (!ValidatorsUtil.validarEnteroPositivo(stockStr));

        return Integer.parseInt(stockStr);
    }

    private double solicitarPrecio() {
        String precioStr;
        do {
            precioStr = JOptionPane.showInputDialog("Ingrese el precio unitario");

            if (precioStr == null) {
                return -1.0; // Usuario canceló
            }

            if (!ValidatorsUtil.validarDecimalPositivo(precioStr)) {
                JOptionPane.showMessageDialog(null, "No se introdujo un valor válido");
            }
        } while (!ValidatorsUtil.validarDecimalPositivo(precioStr));

        return Double.parseDouble(precioStr);
    }

    private String construirListaProductos(List<Producto> productos) {
        StringBuilder lista = new StringBuilder();
        lista.append("LISTA DE PRODUCTOS\n\n");
        
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            String nombre = producto.getNombre();
            if (nombre.length() > 20) {
                nombre = nombre.substring(0, 17) + "...";
            }
            lista.append(String.format("%2d. %-20s | Stock: %3d | Min: %3d | S/ %6.2f\n",
                    i + 1, nombre, producto.getStockActual(), 
                    producto.getStockMinimo(), producto.getPrecioUnitario()));
        }
        return lista.toString();
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.obtenerTodos();
    }

    public Producto buscarProductoPorNombre(String nombre) {
        return productoRepository.buscarPorNombre(nombre);
    }
}
