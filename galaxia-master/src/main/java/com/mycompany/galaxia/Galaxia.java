/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.galaxia;

import com.mycompany.galaxia.view.Menu;
import java.io.IOException;

/**
 * Ejemplo de aplicación con POO, ficheros y separación de capas.
 * 
 * <p>Implementación del ejercicio Galaxia de la colección de ejercicios de repaso 
 * para alumnado de DAM1 con: POO con relación entre clases, ficheros y separación de capas.
 * </p>
 *
 * @author mfontana
 */
public class Galaxia {

    // NUNCA THROW NINGUNA EXCEPTION EN EL MAIN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // SUSPENSO DIRECTO
    // NUNCA CATCH EXCEPTION GENÉRICO => SUSPENSO DIRECTO
    /**
     * Starts app.
     * 
     * @param args (actually don't use it)
     */
    public static void main(String[] args) {
        Menu m = new Menu();
        try {
            m.start();
        } catch (IOException ex) {
            System.out.println("ERROR INESPERADO o que no debería darse: " + ex.getMessage());
        }
    }
}
