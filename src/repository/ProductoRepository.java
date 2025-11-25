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

    /**
     * Obtiene la instancia única del repositorio (Singleton)
     * 
     * @return La instancia del repositorio
     */
    public static synchronized ProductoRepository getInstancia() {
        if (instancia == null) {
            instancia = new ProductoRepository();
        }
        return instancia;
    }

    /**
     * Inicializa el repositorio con algunos productos de ejemplo
     */
    private void inicializarProductos() {
        agregarProducto(new Producto("Café", 10, 5, 5.0));
        agregarProducto(new Producto("Capuchino", 8, 4, 8.0));
    }

    /**
     * Obtiene todos los productos
     * 
     * @return Lista de todos los productos
     */
    public List<Producto> obtenerTodos() {
        return new ArrayList<>(productos);
    }

    /**
     * Busca un producto por su ID
     * 
     * @param id El ID del producto
     * @return El producto encontrado o null si no existe
     */
    public Producto buscarPorId(int id) {
        return productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca un producto por su nombre (case insensitive)
     * 
     * @param nombre El nombre del producto
     * @return El producto encontrado o null si no existe
     */
    public Producto buscarPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    /**
     * Agrega un nuevo producto al repositorio
     * 
     * @param producto El producto a agregar
     * @return true si se agregó correctamente, false si ya existe un producto con ese nombre
     */
    public boolean agregarProducto(Producto producto) {
        if (producto == null) {
            return false;
        }
        // Verificar si ya existe un producto con el mismo nombre
        if (buscarPorNombre(producto.getNombre()) != null) {
            return false;
        }
        producto.setId(siguienteId++);
        productos.add(producto);
        return true;
    }

    /**
     * Actualiza un producto existente
     * 
     * @param producto El producto actualizado
     * @return true si se actualizó correctamente, false si no existe
     */
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

    /**
     * Elimina un producto del repositorio
     * 
     * @param id El ID del producto a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    public boolean eliminarProducto(int id) {
        return productos.removeIf(p -> p.getId() == id);
    }

    /**
     * Obtiene productos que necesitan reposición (stock por debajo del mínimo)
     * 
     * @return Lista de productos que necesitan reposición
     */
    public List<Producto> obtenerProductosConStockBajo() {
        return productos.stream()
                .filter(Producto::necesitaReposicion)
                .collect(Collectors.toList());
    }

    /**
     * Verifica si existe un producto con el nombre dado
     * 
     * @param nombre El nombre a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeProducto(String nombre) {
        return buscarPorNombre(nombre) != null;
    }

    /**
     * Obtiene el número total de productos
     * 
     * @return El número total de productos
     */
    public int contarProductos() {
        return productos.size();
    }
}
