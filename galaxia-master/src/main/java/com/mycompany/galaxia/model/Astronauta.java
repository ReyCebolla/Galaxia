
package com.mycompany.galaxia.model;

import com.mycompany.galaxia.model.enums.Rango;

/**
 * Entidad astronauta.
 * 
 *
 * @author mfontana
 */
public class Astronauta {
    /**
     * Identificador del astronauta
     */
    private String nif;
    
    /**
     * Nombre y apellidos del astronauta
     */
    private String nombre;
    
    /** 
     * Rango puede ser pilot, científic o enginyer
     */
    private Rango rango;

    /**
     * Crea una instància d'Astronauta.
     * 
     * Inicialitza els atributs amb els valors del constructor.
     * 
     * @param nif identificador de l'Astronauta
     * @param nombre nom i cognoms 
     * @param rango pilot, cientific o enginyer
     */
    public Astronauta(String nif, String nombre, Rango rango) {
        this.nif = nif;
        this.nombre = nombre;
        this.rango = rango;
    }

    /**
     * Get the value of nif
     *
     * @return the value of nif
     */
    public String getNif() {
        return nif;
    }
    
    /**
     * Get the value of nombre
     *
     * @return the value of nombre
     */
    public String getNombre() {
        return nombre;
    }

     /**
     * Get the value of rango
     *
     * @return the value of rango
     */
    public Rango getRango() {
        return rango;
    }
    
}
