
package com.mycompany.urbanroute;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class GestorArchivos {

    public boolean importarEstaciones(File archivo, GrafoRed grafo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",", -1);

                if (datos.length != 5) {
                    continue;
                }

                Estacion estacion = new Estacion(
                        datos[0].trim(),
                        datos[1].trim(),
                        datos[2].trim(),
                        datos[3].trim(),
                        Integer.parseInt(datos[4].trim())
                );

                grafo.agregarEstacion(estacion);
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean importarRutas(File archivo, GrafoRed grafo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",", -1);

                if (datos.length != 3) {
                    continue;
                }

                grafo.agregarRuta(
                        datos[0].trim(),
                        datos[1].trim(),
                        Double.parseDouble(datos[2].trim())
                );
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean exportarEstaciones(File archivo, GrafoRed grafo) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(archivo))) {
            for (Estacion estacion : grafo.obtenerEstaciones()) {
                escritor.println(
                        estacion.getId() + ","
                        + estacion.getNombre() + ","
                        + estacion.getLinea() + ","
                        + estacion.getZona() + ","
                        + estacion.getCapacidadPasajerosHora()
                );
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean exportarRutas(File archivo, GrafoRed grafo) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(archivo))) {
            for (int i = 0; i < grafo.getCantidad(); i++) {
                for (int j = 0; j < grafo.getCantidad(); j++) {
                    double peso = grafo.getPeso(i, j);

                    if (peso != Double.POSITIVE_INFINITY) {
                        escritor.println(
                                grafo.getEstacion(i).getId() + ","
                                + grafo.getEstacion(j).getId() + ","
                                + peso
                        );
                    }
                }
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
