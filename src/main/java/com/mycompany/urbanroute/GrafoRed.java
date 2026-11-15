
package com.mycompany.urbanroute;

public class GrafoRed {

    private Estacion[] estaciones;
    private double[][] matriz;
    private int cantidad;

    public GrafoRed() {
        estaciones = new Estacion[10];
        matriz = new double[10][10];
        cantidad = 0;
        inicializarMatriz();
    }

    private void inicializarMatriz() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                matriz[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    private void ampliarCapacidad() {
        int nuevaCapacidad = estaciones.length * 2;
        Estacion[] nuevasEstaciones = new Estacion[nuevaCapacidad];
        double[][] nuevaMatriz = new double[nuevaCapacidad][nuevaCapacidad];

        for (int i = 0; i < nuevaCapacidad; i++) {
            for (int j = 0; j < nuevaCapacidad; j++) {
                nuevaMatriz[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        for (int i = 0; i < cantidad; i++) {
            nuevasEstaciones[i] = estaciones[i];

            for (int j = 0; j < cantidad; j++) {
                nuevaMatriz[i][j] = matriz[i][j];
            }
        }

        estaciones = nuevasEstaciones;
        matriz = nuevaMatriz;
    }

    public boolean agregarEstacion(Estacion estacion) {
        if (estacion == null || buscarIndice(estacion.getId()) != -1) {
            return false;
        }

        if (cantidad == estaciones.length) {
            ampliarCapacidad();
        }

        estaciones[cantidad] = estacion;
        cantidad++;
        return true;
    }

    public boolean agregarRuta(String origen, String destino, double peso) {
        int i = buscarIndice(origen);
        int j = buscarIndice(destino);

        if (i == -1 || j == -1 || peso < 0 || i == j) {
            return false;
        }

        matriz[i][j] = peso;
        return true;
    }

    public int buscarIndice(String id) {
        if (id == null) {
            return -1;
        }

        for (int i = 0; i < cantidad; i++) {
            if (estaciones[i].getId().equals(id)) {
                return i;
            }
        }

        return -1;
    }

    public Estacion buscarEstacion(String id) {
        int indice = buscarIndice(id);
        return indice == -1 ? null : estaciones[indice];
    }

    public Estacion getEstacion(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return null;
        }

        return estaciones[indice];
    }

    public double getPeso(int origen, int destino) {
        if (origen < 0 || origen >= cantidad
                || destino < 0 || destino >= cantidad) {
            return Double.POSITIVE_INFINITY;
        }

        return matriz[origen][destino];
    }

    public int getCantidad() {
        return cantidad;
    }

    public Estacion[] obtenerEstaciones() {
        Estacion[] resultado = new Estacion[cantidad];

        for (int i = 0; i < cantidad; i++) {
            resultado[i] = estaciones[i];
        }

        return resultado;
    }

    public double[][] obtenerMatriz() {
        double[][] copia = new double[cantidad][cantidad];

        for (int i = 0; i < cantidad; i++) {
            for (int j = 0; j < cantidad; j++) {
                copia[i][j] = matriz[i][j];
            }
        }

        return copia;
    }
}
