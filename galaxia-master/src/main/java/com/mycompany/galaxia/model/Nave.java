package com.mycompany.galaxia.model;

import com.mycompany.galaxia.Galaxia;
import com.mycompany.galaxia.exception.GalaxiaException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Entidad nave.
 *
 * @author mfontana
 */
public class Nave {

    /**
     * Nombre de la nave
     */
    private String nom;
    /**
     * Capacidad máxima de astronautas que puede tener
     */
    private int capacidad;
    /**
     * Lista de astronautas que están en la nave
     */
    private Map<String, Astronauta> tripulacion;

    /**
     * Crea una instancia de Nave.
     *
     * Inicializa los atributos de nombre y capacidad con los argumentos. La
     * lista de astronautas la inicializa vacía.
     *
     * @param nom nombre de la nava
     * @param capacidad capacidad máxima de astronautas que caben en la nave
     * @throws GalaxiaException si se intenta crear nave con menos de 2 de capacidad 
     */
    public Nave(String nom, int capacidad) throws GalaxiaException {
        this.nom = nom;
        if (capacidad < 2) {
            throw new GalaxiaException("La capacidad no puede ser menor a 2");
        }
        this.capacidad = capacidad;
        tripulacion = new HashMap();
    }

    /**
     * Get the value of nom
     *
     * @return the value of nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Get the value of capacidad
     *
     * @return the value of capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }

    public Map<String, Astronauta> getTripulacion() {
        return tripulacion;
    }
    
    public void borrarAstronauta(String nif) {
        if (tripulacion.containsKey(nif)) {
            tripulacion.remove(nif);
        }
    }

    /**
     * Añade un Astronauta a la tripulación de la nave.
     * 
     * Si cabe, añade el astronauta a la tripulación de la vave.
     * 
     * @param a Astronauta que se quiere añadir
     * @throws GalaxiaException si la nave está llena
     */
    public void addAstronauta(Astronauta a) throws GalaxiaException {
        if (capacidad <= tripulacion.size()) {
            throw new GalaxiaException("Nave llena. No se puede añadir astronauta.");
        }
        tripulacion.put(a.getNif(), a);
    }
    
    /**
     * Verifica si existe el Astronauta en la nave.
     * 
     * Verifica si existe un Astronauta con el nif pasado como argumento en la nave.
     * 
     * @param nif nif del Astronauta que se quiere buscar
     * @return true si existe un astronauta con el nif indicado, false en caso contrario
     */
    public boolean existeAstronauta(String nif) {
        return tripulacion.containsKey(nif);
    }

}
