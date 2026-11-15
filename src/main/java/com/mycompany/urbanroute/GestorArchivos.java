/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.urbanroute;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author hluis
 */
public class GestorArchivos {
    
    public static boolean cargarDatos(GrafoRed grafo, String rutaDiccionario, String rutaGrafo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaDiccionario))) {
            String linea = br.readLine(); // Saltar cabecera
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 5) {
                    Estacion est = new Estacion(
                        partes[0].trim(),
                        partes[1].trim(),
                        partes[2].trim(),
                        partes[3].trim(),
                        Integer.parseInt(partes[4].trim())
                    );
                    grafo.agregarEstacion(est);
                }
            }
        } catch (Exception e) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(rutaGrafo))) {
            String linea = br.readLine(); // Saltar cabecera
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 3) {
                    String origen = partes[0].trim();
                    String destino = partes[1].trim();
                    int peso = Integer.parseInt(partes[2].trim());
                    grafo.agregarConexion(origen, destino, peso);
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
}


        } catch (Exception e) {
            return false;
        }
    }
}
