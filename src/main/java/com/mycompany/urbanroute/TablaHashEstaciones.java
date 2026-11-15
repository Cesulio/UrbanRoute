
package com.mycompany.urbanroute;

public class TablaHashEstaciones {

    private Estacion[] tabla;
    private int cantidad;

    public TablaHashEstaciones() {
        tabla = new Estacion[11];
        cantidad = 0;
    }

    private int funcionHash(String id) {
        return Math.abs(id.hashCode() % tabla.length);
    }

    public boolean insertar(Estacion estacion) {
        if (estacion == null || estacion.getId() == null) {
            return false;
        }

        if (buscar(estacion.getId()) != null) {
            return false;
        }

        if ((cantidad + 1.0) / tabla.length > 0.7) {
            redimensionar();
        }

        int indice = funcionHash(estacion.getId());

        while (tabla[indice] != null) {
            indice = (indice + 1) % tabla.length;
        }

        tabla[indice] = estacion;
        cantidad++;
        return true;
    }

    public Estacion buscar(String id) {
        if (id == null) {
            return null;
        }

        int indice = funcionHash(id);
        int inicio = indice;

        while (tabla[indice] != null) {
            if (tabla[indice].getId().equals(id)) {
                return tabla[indice];
            }

            indice = (indice + 1) % tabla.length;

            if (indice == inicio) {
                break;
            }
        }

        return null;
    }

    private void redimensionar() {
        Estacion[] anterior = tabla;
        tabla = new Estacion[anterior.length * 2 + 1];
        cantidad = 0;

        for (Estacion estacion : anterior) {
            if (estacion != null) {
                insertar(estacion);
            }
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaVacia() {
        return cantidad == 0;
    }

    public Estacion[] obtenerEstaciones() {
        Estacion[] resultado = new Estacion[cantidad];
        int posicion = 0;

        for (Estacion estacion : tabla) {
            if (estacion != null) {
                resultado[posicion++] = estacion;
            }
        }

        return resultado;
    }
}
