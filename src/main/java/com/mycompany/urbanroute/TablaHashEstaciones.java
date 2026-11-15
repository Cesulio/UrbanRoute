/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.urbanroute;

/**
 *
 * @author hluis
 */
public class TablaHashEstaciones {
    private static final int CAPACIDAD_INICIAL = 53;
    private Lista<EntradaHash>[] tabla;
    private int elementos;

    private static class EntradaHash {
        String clave;
        Estacion estacion;

        public EntradaHash(String clave, Estacion estacion) {
            this.clave = clave;
            this.estacion = estacion;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof EntradaHash) {
                return this.clave.equals(((EntradaHash) obj).clave);
            }
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public TablaHashEstaciones() {
        tabla = new Lista[CAPACIDAD_INICIAL];
        for (int i = 0; i < CAPACIDAD_INICIAL; i++) {
            tabla[i] = new Lista<>();
        }
        elementos = 0;
    }

    private int funcionHash(String clave) {
        int hash = 7;
        for (int i = 0; i < clave.length(); i++) {
            hash = hash * 31 + clave.charAt(i);
        }
        return Math.abs(hash) % tabla.length;
    }

    public void insertar(String clave, Estacion estacion) {
        int indice = funcionHash(clave);
        Lista<EntradaHash> bucket = tabla[indice];
        
        // Verificar si ya existe para actualizar
        Nodo<EntradaHash> actual = bucket.getCabeza();
        while (actual != null) {
            if (actual.getDato().clave.equals(clave)) {
                actual.getDato().estacion = estacion;
                return;
            }
            actual = actual.getSiguiente();
        }

        bucket.insertar(new EntradaHash(clave, estacion));
        elementos++;
    }

    public Estacion buscar(String clave) {
        int indice = funcionHash(clave);
        Lista<EntradaHash> bucket = tabla[indice];
        Nodo<EntradaHash> actual = bucket.getCabeza();
        while (actual != null) {
            if (actual.getDato().clave.equals(clave)) {
                return actual.getDato().estacion;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public boolean eliminar(String clave) {
        int indice = funcionHash(clave);
        Lista<EntradaHash> bucket = tabla[indice];
        Nodo<EntradaHash> actual = bucket.getCabeza();
        while (actual != null) {
            if (actual.getDato().clave.equals(clave)) {
                bucket.eliminar(actual.getDato());
                elementos--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public int getElementos() { return elementos; }
}
