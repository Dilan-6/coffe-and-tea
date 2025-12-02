package utils;

public class ValidatorsUtil {

    public static boolean validarNombreProducto(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        return !nombre.matches(".*\\d.*");
    }

    public static boolean validarEnteroPositivo(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        if (!valor.matches("\\d+")) {
            return false;
        }
        int numero = Integer.parseInt(valor);
        return numero >= 0;
    }

    public static boolean validarDecimalPositivo(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        if (!valor.matches("\\d+(\\.\\d+)?")) {
            return false;
        }
        double numero = Double.parseDouble(valor);
        return numero >= 0;
    }

    public static boolean validarEntero(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        return valor.matches("-?\\d+");
    }

    public static boolean validarDecimal(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        return valor.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean validarNoVacio(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    public static boolean validarRango(int indice, int min, int max) {
        return indice >= min && indice < max;
    }

    public static int parseEntero(String valor) {
        if (!validarEnteroPositivo(valor)) {
            return -1;
        }
        return Integer.parseInt(valor);
    }

    public static double parseDecimal(String valor) {
        if (!validarDecimalPositivo(valor)) {
            return -1.0;
        }
        return Double.parseDouble(valor);
    }
}
