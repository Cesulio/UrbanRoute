
package com.mycompany.urbanroute;

public class AnalizadorRed {

    private GrafoRed grafo;

    public AnalizadorRed(GrafoRed grafo) {
        this.grafo = grafo;
    }

    public String[] recorridoBFS(String idInicio) {
        int inicio = grafo.buscarIndice(idInicio);
        int n = grafo.getCantidad();

        if (inicio == -1) {
            return new String[0];
        }

        boolean[] visitado = new boolean[n];
        int[] cola = new int[n];
        String[] resultado = new String[n];

        int frente = 0;
        int fin = 0;
        int posicion = 0;

        cola[fin++] = inicio;
        visitado[inicio] = true;

        while (frente < fin) {
            int actual = cola[frente++];
            resultado[posicion++] = grafo.getEstacion(actual).getNombre();

            for (int i = 0; i < n; i++) {
                if (grafo.getPeso(actual, i) != Double.POSITIVE_INFINITY
                        && !visitado[i]) {
                    visitado[i] = true;
                    cola[fin++] = i;
                }
            }
        }

        return copiarResultado(resultado, posicion);
    }

    public String[] recorridoDFS(String idInicio) {
        int inicio = grafo.buscarIndice(idInicio);
        int n = grafo.getCantidad();

        if (inicio == -1) {
            return new String[0];
        }

        boolean[] visitado = new boolean[n];
        int[] pila = new int[n];
        String[] resultado = new String[n];

        int cima = 0;
        int posicion = 0;

        pila[cima++] = inicio;

        while (cima > 0) {
            int actual = pila[--cima];

            if (!visitado[actual]) {
                visitado[actual] = true;
                resultado[posicion++] = grafo.getEstacion(actual).getNombre();

                for (int i = n - 1; i >= 0; i--) {
                    if (grafo.getPeso(actual, i) != Double.POSITIVE_INFINITY
                            && !visitado[i]) {
                        pila[cima++] = i;
                    }
                }
            }
        }

        return copiarResultado(resultado, posicion);
    }

    public double[] dijkstra(String idInicio) {
        int inicio = grafo.buscarIndice(idInicio);
        int n = grafo.getCantidad();

        double[] distancias = new double[n];
        boolean[] visitado = new boolean[n];

        for (int i = 0; i < n; i++) {
            distancias[i] = Double.POSITIVE_INFINITY;
        }

        if (inicio == -1) {
            return distancias;
        }

        distancias[inicio] = 0;

        for (int i = 0; i < n; i++) {
            int actual = -1;
            double menor = Double.POSITIVE_INFINITY;

            for (int j = 0; j < n; j++) {
                if (!visitado[j] && distancias[j] < menor) {
                    menor = distancias[j];
                    actual = j;
                }
            }

            if (actual == -1) {
                break;
            }

            visitado[actual] = true;

            for (int j = 0; j < n; j++) {
                double peso = grafo.getPeso(actual, j);

                if (!visitado[j] && peso != Double.POSITIVE_INFINITY
                        && distancias[actual] + peso < distancias[j]) {
                    distancias[j] = distancias[actual] + peso;
                }
            }
        }

        return distancias;
    }

    public int[] calcularCentralidad() {
        int n = grafo.getCantidad();
        int[] centralidad = new int[n];

        for (int i = 0; i < n; i++) {
            int grado = 0;

            for (int j = 0; j < n; j++) {
                if (grafo.getPeso(i, j) != Double.POSITIVE_INFINITY) {
                    grado++;
                }

                if (grafo.getPeso(j, i) != Double.POSITIVE_INFINITY) {
                    grado++;
                }
            }

            centralidad[i] = grado;
        }

        return centralidad;
    }

    public Estacion obtenerEstacionMayorCentralidad() {
        int[] centralidad = calcularCentralidad();

        if (centralidad.length == 0) {
            return null;
        }

        int mayor = 0;

        for (int i = 1; i < centralidad.length; i++) {
            if (centralidad[i] > centralidad[mayor]) {
                mayor = i;
            }
        }

        return grafo.getEstacion(mayor);
    }

    private String[] copiarResultado(String[] original, int cantidad) {
        String[] resultado = new String[cantidad];

        for (int i = 0; i < cantidad; i++) {
            resultado[i] = original[i];
        }

        return resultado;
    }
}
