/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.urbanroute;

/**
 *
 * @author hluis
 */
public class GrafoRed {
    
    public static class Arista {
        String destinoId;
        int pesoMinutos;

        public Arista(String destinoId, int pesoMinutos) {
            this.destinoId = destinoId;
            this.pesoMinutos = pesoMinutos;
        }
    }

    private TablaHashEstaciones estaciones;
    private Lista<String> listaIdsEstaciones;
    private TablaHashEstacionesAdyacencia adyacencias;

    public GrafoRed() {
        estaciones = new TablaHashEstaciones();
        listaIdsEstaciones = new Lista<>();
        adyacencias = new TablaHashEstacionesAdyacencia();
    }

    public void agregarEstacion(Estacion estacion) {
        estaciones.insertar(estacion.getid(), estacion);
        if (!listaIdsEstaciones.contiene(estacion.getid())) {
            listaIdsEstaciones.insertar(estacion.getid());
        }
    }

    public void agregarConexion(String origenId, String destinoId, int peso) {
        adyacencias.agregarArista(origenId, new Arista(destinoId, peso));
        adyacencias.agregarArista(destinoId, new Arista(origenId, peso));
    }

    public Estacion obtenerEstacion(String id) {
        return estaciones.buscar(id);
    }

    public Lista<String> getIdsEstaciones() {
        return listaIdsEstaciones;
    }

    public Lista<Arista> obtenerAdyacentes(String id) {
        return adyacencias.obtenerAristas(id);
    }

    private static class TablaHashEstacionesAdyacencia {
        private Lista<EntradaAdyacencia>[] tabla;

        private static class EntradaAdyacencia {
            String id;
            Lista<Arista> aristas;
            public EntradaAdyacencia(String id) {
                this.id = id;
                this.aristas = new Lista<>();
            }
        }

        @SuppressWarnings("unchecked")
        public TablaHashEstacionesAdyacencia() {
            tabla = new Lista[53];
            for (int i = 0; i < 53; i++) tabla[i] = new Lista<>();
        }

        private int hash(String clave) {
            return Math.abs(clave.hashCode()) % tabla.length;
        }

        public void agregarArista(String id, Arista arista) {
            int idx = hash(id);
            Lista<EntradaAdyacencia> bucket = tabla[idx];
            Nodo<EntradaAdyacencia> actual = bucket.getCabeza();
            EntradaAdyacencia entrada = null;
            while (actual != null) {
                if (actual.getDato().id.equals(id)) {
                    entrada = actual.getDato();
                    break;
                }
                actual = actual.getSiguiente();
            }
            if (entrada == null) {
                entrada = new EntradaAdyacencia(id);
                bucket.insertar(entrada);
            }
            
            Nodo<Arista> aAct = entrada.aristas.getCabeza();
            while (aAct != null) {
                if (aAct.getDato().destinoId.equals(arista.destinoId)) return;
                aAct = aAct.getSiguiente();
            }
            entrada.aristas.insertar(arista);
        }

        public Lista<Arista> obtenerAristas(String id) {
            int idx = hash(id);
            Lista<EntradaAdyacencia> bucket = tabla[idx];
            Nodo<EntradaAdyacencia> actual = bucket.getCabeza();
            while (actual != null) {
                if (actual.getDato().id.equals(id)) {
                    return actual.getDato().aristas;
                }
                actual = actual.getSiguiente();
            }
            return new Lista<>();
        }
    }
}
