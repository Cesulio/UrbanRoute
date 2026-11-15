/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.urbanroute;

/**
 *
 * @author hluis
 */
public class AnalizadorRed {
    /**
     * Identifica componentes conexos (subredes) usando BFS.
     */
    public static Lista<Lista<String>> detectarSubredes(GrafoRed grafo) {
        Lista<Lista<String>> subredes = new Lista<>();
        Lista<String> ids = grafo.getIdsEstaciones();
        TablaHashEstaciones visitados = new TablaHashEstaciones();

        for (int i = 0; i < ids.getTamano(); i++) {
            String idActual = ids.obtener(i);
            if (visitados.buscar(idActual) == null) {
                Lista<String> componente = new Lista<>();
                Cola<String> cola = new Cola<>();

                cola.encolar(idActual);
                visitados.insertar(idActual, grafo.obtenerEstacion(idActual));

                while (!cola.estaVacia()) {
                    String actual = cola.desencolar();
                    componente.insertar(actual);

                    Lista<GrafoRed.Arista> adyacentes = grafo.obtenerAdyacentes(actual);
                    Nodo<GrafoRed.Arista> aNode = adyacentes.getCabeza();
                    while (aNode != null) {
                        String vecino = aNode.getDato().destinoId;
                        if (visitados.buscar(vecino) == null) {
                            visitados.insertar(vecino, grafo.obtenerEstacion(vecino));
                            cola.encolar(vecino);
                        }
                        aNode = aNode.getSiguiente();
                    }
                }
                subredes.insertar(componente);
            }
        }
        return subredes;
    }

    /**
     * Identifica los Hubs de transferencia basándose en la Centralidad de Grado.
     */
    public static String identificarHubPrincipal(GrafoRed grafo) {
        Lista<String> ids = grafo.getIdsEstaciones();
        String maxHub = "";
        int maxConexiones = -1;

        for (int i = 0; i < ids.getTamano(); i++) {
            String id = ids.obtener(i);
            Lista<GrafoRed.Arista> ady = grafo.obtenerAdyacentes(id);
            int grado = ady.getTamano();
            if (grado > maxConexiones) {
                maxConexiones = grado;
                maxHub = id;
            }
        }
        Estacion est = grafo.obtenerEstacion(maxHub);
        return est != null ? est.getNombre() + " (Grado: " + maxConexiones + ")" : "Ninguno";
    }

    /**
     * Implementación del algoritmo de Dijkstra para encontrar la ruta de menor tiempo entre dos estaciones.
     */
    public static String calcularRutaDijkstra(GrafoRed grafo, String idOrigen, String idDestino) {
        Lista<String> ids = grafo.getIdsEstaciones();
        if (grafo.obtenerEstacion(idOrigen) == null || grafo.obtenerEstacion(idDestino) == null) {
            return "Error: La estación de origen o destino no es válida.";
        }

        // Tablas auxiliares simuladas con TablaHashEstaciones para distancias y predecesores
        TablaHashEstaciones tablaDistancias = new TablaHashEstaciones();
        TablaHashEstaciones tablaPredecesores = new TablaHashEstaciones();
        TablaHashEstaciones visitados = new TablaHashEstaciones();

        // Inicializar distancias a "infinito" (representado por un número grande, ej: 999999)
        for (int i = 0; i < ids.getTamano(); i++) {
            String id = ids.obtener(i);
            // Creamos un objeto Estacion temporal donde capacidadPasajerosHora almacena la distancia acumulada
            int distInicial = id.equals(idOrigen) ? 0 : 999999;
            tablaDistancias.insertar(id, new Estacion(id, id, "", "", distInicial));
        }

        while (true) {
            // Encontrar el nodo no visitado con la menor distancia actual
            String nodoActual = null;
            int menorDistancia = 999999;

            for (int i = 0; i < ids.getTamano(); i++) {
                String id = ids.obtener(i);
                if (visitados.buscar(id) == null) {
                    Estacion estDist = tablaDistancias.buscar(id);
                    if (estDist != null && estDist.getCapacidadPasajerosHora() < menorDistancia) {
                        menorDistancia = estDist.getCapacidadPasajerosHora();
                        nodoActual = id;
                    }
                }
            }

            // Si no hay nodo accesible o llegamos a infinito, terminamos
            if (nodoActual == null || menorDistancia == 999999) break;

            // Si llegamos al destino, podemos detenernos
            if (nodoActual.equals(idDestino)) break;

            visitados.insertar(nodoActual, grafo.obtenerEstacion(nodoActual));

            // Relajar aristas vecinas
            Lista<GrafoRed.Arista> adyacentes = grafo.obtenerAdyacentes(nodoActual);
            Nodo<GrafoRed.Arista> aNode = adyacentes.getCabeza();
            while (aNode != null) {
                GrafoRed.Arista arista = aNode.getDato();
                String vecino = arista.destinoId;
                int peso = arista.pesoMinutos;

                Estacion estDistVecino = tablaDistancias.buscar(vecino);
                Estacion estDistActual = tablaDistancias.buscar(nodoActual);

                if (estDistVecino != null && estDistActual != null) {
                    int nuevaDist = estDistActual.getCapacidadPasajerosHora() + peso;
                    if (nuevaDist < estDistVecino.getCapacidadPasajerosHora()) {
                        estDistVecino.setCapacidadPasajerosHora(nuevaDist);
                        // Guardamos el predecesor almacenando el ID en la zona de la estación predecesora
                        tablaPredecesores.insertar(vecino, new Estacion(vecino, nodoActual, "", "", 0));
                    }
                }
                aNode = aNode.getSiguiente();
            }
        }

        // Verificar si se encontró ruta al destino
        Estacion estDestinoDist = tablaDistancias.buscar(idDestino);
        if (estDestinoDist == null || estDestinoDist.getCapacidadPasajerosHora() >= 999999) {
            return "No existe ruta conectada entre " + idOrigen + " y " + idDestino + ".";
        }

        // Reconstruir la ruta paso a paso desde el destino hasta el origen
        StringBuilder ruta = new StringBuilder();
        String actual = idDestino;
        int tiempoTotal = estDestinoDist.getCapacidadPasajerosHora();

        while (actual != null && !actual.equals(idOrigen)) {
            Estacion estObj = grafo.obtenerEstacion(actual);
            ruta.insert(0, " -> " + (estObj != null ? estObj.getNombre() : actual));
            Estacion pred = tablaPredecesores.buscar(actual);
            actual = (pred != null) ? pred.getNombre() : null;
        }
        
        Estacion estOrgObj = grafo.obtenerEstacion(idOrigen);
        ruta.insert(0, (estOrgObj != null ? estOrgObj.getNombre() : idOrigen));

        return "Tiempo total estimado: " + tiempoTotal + " minutos.\nTrayecto:\n" + ruta.toString();
    }
}
