package com.mycompany.galaxia.controller;

import com.mycompany.galaxia.exception.GalaxiaException;
import com.mycompany.galaxia.model.Astronauta;
import com.mycompany.galaxia.model.Nave;
import com.mycompany.galaxia.model.enums.Rango;
import com.mycompany.galaxia.persistence.FicheroGalaxia;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Lógica de la aplicación.
 *
 *
 * @author mfontana
 */
public class GestorAgencia {

    /**
     * Naves registradas en la aplicación.
     */
    private Map<String, Nave> flota;

    /**
     * Fichero para gestionar la persistencia de la aplicación.
     */
    private FicheroGalaxia fichero;

    /**
     * Inicializa fichero y lee datos para inicializar flota.
     *
     */
    public GestorAgencia() {
        fichero = new FicheroGalaxia();
        try {
            // Llegim naus del fitxer
            flota = fichero.readNavesFile();
        } catch (IOException | GalaxiaException ex) {
            // No debería darse. Si se da error con el fichero inicializamos las naves vacías.
            flota = new HashMap<>();
            
        }
    }
    
    public void borrarAstronauta(String nif) throws GalaxiaException, IOException {
        if (!existeAstronauta(nif)) {
            throw new GalaxiaException("No existe ningún astronauta con el nif indicado.");
        }
        for (Nave nave : flota.values()) {
            if (nave.existeAstronauta(nif)) {
                nave.borrarAstronauta(nif);
                fichero.writeTripulaciones(flota);
            }
        }
    }

    public String infoNave(String nombre) throws GalaxiaException {
        String info = "";
        if (flota.isEmpty()) {
            info = "No hay naves registradas";
        } else {
            if (!flota.containsKey(nombre)) {
                throw new GalaxiaException("No existe ninguna nave con ese nombre");
            }
            Nave n = flota.get(nombre);
            info += "Nave: " + nombre + "\n";
            info += "Capacidad: " + n.getCapacidad() + "\n";
            if (n.getTripulacion().isEmpty()) {
                info += "*** Sin tripulación ***\n";
            } else {
                info += "*** TRIPULACIÓN ***\n";
                for (Astronauta a : n.getTripulacion().values()) {
                    info += a.getNif() + " - " + a.getNombre() + " - " + a.getRango() + "\n";
                }
            }
        }
        return info;
    }

    /**
     * Añade un astronauta a la nave indicada.
     *
     * @param nif identificador del astronauta
     * @param nombre nombre y apellido
     * @param rango deber ser PILOT, ENGINYER o CIENTIFIC
     * @param nombreNave nave a la que se quiere añadir
     * @throws GalaxiaException si ya existe el astronauta, no existe la nave o
     * el astronauta no cabe en la nave
     */
    public void addAstronauta(String nif, String nombre, Rango rango, String nombreNave) throws GalaxiaException, IOException {
        if (!flota.containsKey(nombreNave)) {
            throw new GalaxiaException("No existe la nave indicada");
        }
        if (existeAstronauta(nif)) {
            throw new GalaxiaException("Ya existe un astronauta con el nif indicado");
        }
        Nave seleccionada = flota.get(nombreNave);
        Astronauta a = new Astronauta(nif, nombre, rango);
        seleccionada.addAstronauta(a);
        fichero.writeAstronauta(nombreNave, a);
    }

    /**
     * Comprueba si existe algún astronauta con el nif indicado.
     *
     * @param nif del astronauta que se quiere buscar
     * @return true si existe un astronauta con el nif indicado en alguna nave,
     * false en caso contrario
     */
    private boolean existeAstronauta(String nif) {
        for (Nave n : flota.values()) {
            if (n.existeAstronauta(nif)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve la información de la flota.
     *
     * @return información de la flota de naves
     */
    public String infoFlota() {
        String info = "";
        if (flota.isEmpty()) {
            info += "No hi ha naus registrades.";
        } else {
            info += "*** FLOTA DE NAUS ***\n";
            for (Nave n : flota.values()) {
                info += n.getNom() + " - Capacitat: " + n.getCapacidad() + "\n";
            }
            info += "Total naus: " + flota.size() + "\n";
        }
        return info;
    }

    /**
     * Añade una nave a la flota.
     *
     * <p>
     * Comprueba si la nave no existe, y si es así la añade a la flota. Cuando
     * se registra además la hace persistenee: llama al método correspondiente
     * de la clase FicheroGalaxia para que se añadan los datos al fichero.
     * </p>
     *
     * @param nombre de la nave
     * @param capacidad máxima de la nave
     * @throws IOException si hay algún error con los ficheros.
     * @throws GalaxiaException si ya existe una nave con ese nombre
     */
    public void addNave(String nombre, int capacidad) throws IOException, GalaxiaException {
        if (flota.containsKey(nombre)) {
            throw new GalaxiaException("Ya existe una nave con ese nombre");
        }
        Nave nuevaNave = new Nave(nombre, capacidad);
        flota.put(nombre, nuevaNave);
        fichero.writeNaveFile(nuevaNave);
    }

}
