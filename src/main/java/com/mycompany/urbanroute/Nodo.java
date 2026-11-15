/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.urbanroute;

/**
 *
 * @author César Prz
 * @param <T>
 */
public class Lista<T> {
    private Nodo<T> cabeza;
    private int tamano;
    
    public Lista() {
        this.cabeza = null;
        this.tamano = 0;
    }
    
    public void insertar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamano++;
    }
    
    public boolean eliminar(T dato) {
        if (cabeza == null) return false;
        if (cabeza.getDato().equals(dato)) {
            cabeza = cabeza.getSiguiente();
            tamano--;
            return true;
        }
        Nodo<T> actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().equals(dato)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                tamano--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
    
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamano) return null;
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }
    
    public boolean contiene(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)) return true;
            actual = actual.getSiguiente();
        }
        return false;
    }
    
    public int getTamano() { return tamano; }
    public boolean estaVacia() { return cabeza == null; }
    public Nodo<T> getCabeza() { return cabeza; }
}

