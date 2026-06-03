package com.mycompany.galaxia.persistence;

import com.mycompany.galaxia.exception.GalaxiaException;
import com.mycompany.galaxia.model.Astronauta;
import com.mycompany.galaxia.model.Nave;
import com.mycompany.galaxia.model.enums.Rango;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestiona la persistencia en ficheros del proyecto.
 *
 * @author mfontana
 */
public class FicheroGalaxia {

    /**
     * Fichero que apunta a la carpeta de datos.
     */
    private File carpeta;
    /**
     * Fichero que apunta a la ruta del fichero de naves.
     */
    private File ficheroFlota;
    /**
     * Fichero que apunta a la ruta del fichero de los astronautas.
     */
    private File ficheroTripulacion;

    /**
     * Inicializa los datos necesarios de la clase.
     *
     * Crea la instancia e inicializa rutas y ficheros necesarios para la
     * persistencia. Guarda los datos en la carpeta "galaxia". "flota.txt" para
     * las naves. "tripulacion.txt" para los astronautas
     *
     */
    public FicheroGalaxia() {
        String rutaCarpeta = "galaxia";
        String rutaFlota = rutaCarpeta + File.separator + "flota.txt";
        String rutaTripulacion = rutaCarpeta + File.separator + "tripulacion.txt";
        carpeta = new File(rutaCarpeta);
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
        ficheroFlota = new File(rutaFlota);
        ficheroTripulacion = new File(rutaTripulacion);
    }

    /**
     * Lee los datos de la flota.
     *
     * Lee el fichero "flota.txt" y devuelve un ArrayList con las naves leídas.
     *
     * @return flota de naves.
     * @throws IOException si hay algún error con los ficheros (no debería
     * darse)
     */
    public Map<String, Nave> readNavesFile() throws IOException, GalaxiaException {
        Map<String, Nave> naves = new HashMap<>();
        // Siempre al leer nos tenemos que asegurar de que el fichero existe
        if (ficheroFlota.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(ficheroFlota));
            String linea = "";
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String nombre = datos[0].toUpperCase();
                int capacidad = Integer.parseInt(datos[1]);
                naves.put(nombre, new Nave(nombre, capacidad));
            }
        }
        // Leemos ahora astronautas
        if (ficheroTripulacion.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(ficheroTripulacion));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String nombreNave = datos[0].toUpperCase();
                String nif = datos[1].toUpperCase();
                String nombreAstronauta = datos[2];
                Rango rango = Rango.valueOf(datos[3].toUpperCase());
                Nave n = naves.get(nombreNave);
                n.addAstronauta(new Astronauta(nif, nombreAstronauta, rango));
            }
        }
        return naves;
    }

    /**
     * Añade una nueva nave al fichero.
     *
     * Añade una línea al fichero "flota.txt" con los datos de la nave que se
     * pasa como argumento.
     *
     * @param n nave que se quiere guardar.
     * @throws IOException si hay algún error con los ficheros (no debería
     * darse)
     */
    public void writeNaveFile(Nave n) throws IOException {
        // Boolean true es para añadir
        BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroFlota, true));
        String linea = n.getNom() + "," + n.getCapacidad();
        bw.write(linea);
        bw.newLine();
        bw.close();
    }
    
    public void writeAstronauta(String nombreNave, Astronauta a) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroTripulacion, true));
        String linea = nombreNave + "," + a.getNif() + "," + a.getNombre() + "," + a.getRango().toString();
        bw.write(linea);
        bw.newLine();
        bw.close();
    }
    
    public void writeTripulaciones(Map<String, Nave> flota) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroTripulacion));
        for (Nave n : flota.values()) {
            for (Astronauta a : n.getTripulacion().values()) {
                String linea = n.getNom() + "," + a.getNif() + "," + a.getNombre() + "," + a.getRango().toString();
                bw.write(linea);
                bw.newLine();
            }
        }
        bw.close();
    }
 
}
