package es.upm.euitt.seguridad.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaseMenu {
    protected void printCiferMenu() {
        System.out.println("Introduzca el nombre del fichero a cifrar:");
    }

    protected void printDecryptMenu() {
        System.out.println("Introduzca el nombre del fichero a descrifrar:");
    }

    protected String getStrOpt() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {

            return br.readLine();
        } catch (IOException ioe) {
            System.out.println("IO error trying to read your name!");

            return "";
        }
    }
}
