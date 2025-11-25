package repository;

import model.Boleta;
import java.util.ArrayList;
import java.util.List;

public class BoletaRepository {
    private static BoletaRepository instancia;
    private List<Boleta> boletas;
    private int siguienteId;

    private BoletaRepository() {
        this.boletas = new ArrayList<>();
        this.siguienteId = 1;
    }

    public static synchronized BoletaRepository getInstancia() {
        if (instancia == null) {
            instancia = new BoletaRepository();
        }
        return instancia;
    }

    public List<Boleta> obtenerTodas() {
        return new ArrayList<>(boletas);
    }

    public Boleta buscarPorId(int id) {
        return boletas.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Boleta buscarPorNumero(String numeroBoleta) {
        return boletas.stream()
                .filter(b -> b.getNumeroBoleta().equals(numeroBoleta))
                .findFirst()
                .orElse(null);
    }

    public boolean agregarBoleta(Boleta boleta) {
        if (boleta == null) {
            return false;
        }
        boleta.setId(siguienteId++);
        boletas.add(boleta);
        return true;
    }

    public int contarBoletas() {
        return boletas.size();
    }
}

