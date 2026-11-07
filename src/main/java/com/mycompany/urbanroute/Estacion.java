
package com.mycompany.urbanroute;

/**
 * Representa una estación dentro de la red de transporte UrbanRoute.
 *
 * @author hluis
 */
public class Estacion {

    // Atributos de la estación
    private String id;
    private String nombre;
    private String linea;
    private String zona;
    private int capacidadPasajerosHora;

    /**
     * Constructor de la clase Estacion.
     *
     * @param id identificador de la estación
     * @param nombre nombre de la estación
     * @param linea línea de transporte
     * @param zona zona geográfica
     * @param capacidadPasajerosHora capacidad de pasajeros por hora
     */
    public Estacion(String id, String nombre, String linea,
                    String zona, int capacidadPasajerosHora) {

        this.id = id;
        this.nombre = nombre;
        this.linea = linea;
        this.zona = zona;
        this.capacidadPasajerosHora = capacidadPasajerosHora;
    }

    // Métodos para consultar los atributos

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLinea() {
        return linea;
    }

    public String getZona() {
        return zona;
    }

    public int getCapacidadPasajerosHora() {
        return capacidadPasajerosHora;
    }

    /**
     * Devuelve la información de la estación.
     *
     * @return descripción de la estación
     */
    @Override
    public String toString() {
        return "Estacion{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", linea='" + linea + '\'' +
                ", zona='" + zona + '\'' +
                ", capacidadPasajerosHora=" + capacidadPasajerosHora +
                '}';
    }
}
