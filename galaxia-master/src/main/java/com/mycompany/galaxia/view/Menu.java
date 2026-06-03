package com.mycompany.galaxia.view;

import com.mycompany.galaxia.controller.GestorAgencia;
import com.mycompany.galaxia.exception.GalaxiaException;
import com.mycompany.galaxia.model.enums.Rango;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona el Menu y la vista con el usuario.
 *
 * @author mfontana
 */
public class Menu {

    /**
     * Objeto auxiliar con métodos para pedir datos al usuario.
     */
    private AskData ask;
    /**
     * Objeto que contiene la lógica de la aplicación.
     */
    private GestorAgencia gestor;

    /**
     * Método de arranque de la aplicación.
     *
     * Primer método que se ejecuta al iniciar la aplicación. Muestra el menú y
     * pide opción al usuario.
     *
     * @throws IOException si hay algún problema al pedir datos (no debería
     * darse)
     */
    public void start() throws IOException {
        gestor = new GestorAgencia();
        ask = new AskData();
        boolean salir = false;
        do {
            try {
                mostrarMenu();
                int opcion = ask.askInt("Indica una opción: ");
                switch (opcion) {
                    case 1:
                        altaNave();
                        break;
                    case 2:
                        altaAstronauta();
                        break;
                    case 3:
                        llistatFlota();
                        break;
                    case 4:
                        infoNave();
                        break;
                    case 5:
                        borrarAstronauta();
                        break;
                    case 0:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción incorrecta.");
                }
            } catch (GalaxiaException ex) {
                System.out.println(ex.getMessage());
            }
        } while (!salir); // salir == false
        System.out.println("Chao pescao!");
    }

    private void borrarAstronauta() throws IOException, GalaxiaException {
        String nif = ask.askString("NIF de l'astronauta que vols esborrar: ").toUpperCase();
        gestor.borrarAstronauta(nif);
        System.out.println("Astronauta esborrat.");
    }
    
    private void infoNave() throws IOException, GalaxiaException {
        String nombre = ask.askString("Nombre de la nave: ").toUpperCase();
        System.out.println(gestor.infoNave(nombre));
        System.out.println();
    }

    /**
     * Pide datos y gestiona el alta de un astronauta
     *
     * @throws IOException si hay algún problema al pedir datos (no debería
     * darse)
     * @throws GalaxiaException si se produce algún error en el alta
     */
    private void altaAstronauta() throws IOException, GalaxiaException {
        String nif = ask.askString("NIF de l'astronauta: ").toUpperCase();
        String nombre = ask.askString("Nombre: ");
        List<String> rangos = List.of("PILOT", "CIENTIFIC", "ENGINYER");
        Rango rango = askRango();
        String nave = ask.askString("Nombre de la nave a la que quieres añadir el astronauta: ").toUpperCase();
        gestor.addAstronauta(nif, nombre, rango, nave);
        System.out.println("Astronauta registrado en la nave " + nave);
    }

    private Rango askRango() throws IOException {
        Rango rango = null;
        boolean error;
        do {
            String nombreRango = ask.askString("Rango: ").toUpperCase();
            try {
                rango = Rango.valueOf(nombreRango);
                error = false;
            } catch (IllegalArgumentException ex) {
                System.out.println("Rango erróneo. Debe ser: PILOT, CIENTIFIC o ENGINYER.");
                error = true;
            }
        } while (error);
        return rango;
    }

    /**
     * Muestra los datos de la flota.
     */
    private void llistatFlota() {
        String info = gestor.infoFlota();
        System.out.println(info);
    }

    /**
     * Pide datos y gestiona el alta de una nave.
     *
     * @throws IOException si hay algún problema al pedir datos (no debería
     * darse)
     * @throws GalaxiaException si se produce algún error en el alta
     */
    private void altaNave() throws IOException, GalaxiaException {
        String nombre = ask.askString("Nombre: ").toUpperCase();
        int capacidad = ask.askInt("Capacidad: ", "Debe ser mínimo 2", 2);
        gestor.addNave(nombre, capacidad);
        System.out.println("Nave registrada.");
    }

    /**
     * Muestra el menú de opciones de la aplicación.
     */
    private void mostrarMenu() {
        System.out.println("1. Alta Nave");
        System.out.println("2. Añadir astronauta");
        System.out.println("3. Llistat de la flota");
        System.out.println("4. Ver información de una nave");
        System.out.println("5. Borrar un astronauta");
        System.out.println("0. Salir");
    }
}
