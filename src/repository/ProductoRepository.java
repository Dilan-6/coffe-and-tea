package repository;

import model.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoRepository {
    private static ProductoRepository instancia;
    private List<Producto> productos;
    private int siguienteId;

    private ProductoRepository() {
        this.productos = new ArrayList<>();
        this.siguienteId = 1;
        inicializarProductos();
    }

    public static synchronized ProductoRepository getInstancia() {
        if (instancia == null) {
            instancia = new ProductoRepository();
        }
        return instancia;
    }

    private void inicializarProductos() {
        // Bebidas calientes
        agregarProducto(new Producto("Café", 20, 10, 5.0));
        agregarProducto(new Producto("Capuchino", 15, 8, 8.0));
        agregarProducto(new Producto("Latte", 15, 8, 9.0));
        agregarProducto(new Producto("Mocha", 12, 6, 10.0));
        agregarProducto(new Producto("Té", 18, 10, 4.5));
        agregarProducto(new Producto("Chocolate Caliente", 10, 5, 7.0));
        agregarProducto(new Producto("Americano", 15, 8, 5.5));
        agregarProducto(new Producto("Espresso", 20, 10, 4.0));

        // Bebidas frías
        agregarProducto(new Producto("Café Helado", 12, 6, 7.5));
        agregarProducto(new Producto("Frappé", 10, 5, 9.5));
        agregarProducto(new Producto("Limonada", 15, 8, 6.0));
        agregarProducto(new Producto("Jugo de Naranja", 12, 6, 5.5));

        // Postres
        agregarProducto(new Producto("Brownie", 8, 4, 6.5));
        agregarProducto(new Producto("Cheesecake", 6, 3, 12.0));
        agregarProducto(new Producto("Muffin", 10, 5, 5.0));
        agregarProducto(new Producto("Croissant", 12, 6, 4.5));
        agregarProducto(new Producto("Torta de Chocolate", 5, 2, 15.0));
        agregarProducto(new Producto("Alfajor", 15, 8, 3.5));

        // Snacks
        agregarProducto(new Producto("Sandwich", 10, 5, 8.0));
        agregarProducto(new Producto("Galletas", 20, 10, 2.5));
        agregarProducto(new Producto("Pan con Mantequilla", 15, 8, 3.0));
    }

    public List<Producto> obtenerTodos() {
        return new ArrayList<>(productos);
    }

    public Producto buscarPorId(int id) {
        return productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Producto buscarPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    public boolean agregarProducto(Producto producto) {
        if (producto == null) {
            return false;
        }
        if (buscarPorNombre(producto.getNombre()) != null) {
            return false;
        }
        producto.setId(siguienteId++);
        productos.add(producto);
        return true;
    }

    public boolean actualizarProducto(Producto producto) {
        if (producto == null) {
            return false;
        }
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == producto.getId()) {
                productos.set(i, producto);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarProducto(int id) {
        return productos.removeIf(p -> p.getId() == id);
    }

    public List<Producto> obtenerProductosConStockBajo() {
        return productos.stream()
                .filter(Producto::necesitaReposicion)
                .collect(Collectors.toList());
    }

    public boolean existeProducto(String nombre) {
        return buscarPorNombre(nombre) != null;
    }

    public int contarProductos() {
        return productos.size();
    }
}
