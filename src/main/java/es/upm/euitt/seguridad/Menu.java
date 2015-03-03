package es.upm.euitt.seguridad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {
    public void printMainMenu() {
        System.out.println("¿Qué tipo de criptografía desea utilizar?");
        System.out.println("1. Simétrica.");
        System.out.println("2. Asimétrica.");
        System.out.println("3. Salir.");
    }

    public void printSymetricMenu() {
        System.out.println("Elija una opción para CRIPTOGRAFÍA SIMÉTRICA:");
        System.out.println("0. Volver al menu anterior.");
        System.out.println("1. Generar clave.");
        System.out.println("2. Cifrado.");
        System.out.println("3. Descifrado.");
    }

    public void printCiferMenu() {
        System.out.println("Introduzca el nombre del fichero a cifrar:");
    }

    public void printDecryptMenu() {
        System.out.println("Introduzca el nombre del fichero a descrifrar:");
    }

    public MainMenuOptEnum requestMainOption() {
        try {
            int optInt = Integer.parseInt(this.getStrOpt());

            return MainMenuOptEnum.getOpt(optInt);
        } catch (NumberFormatException e) {

            return MainMenuOptEnum.ERRONEA;
        }
    }

    public SymetricMenuOptEnum requestSymetricOption() {
        try {
            int optInt = Integer.parseInt(this.getStrOpt());

            return SymetricMenuOptEnum.getOpt(optInt);
        } catch (NumberFormatException e) {

            return SymetricMenuOptEnum.ERRONEA;
        }
    }

    public MenuEnum mainMenu() {

        this.printMainMenu();
        MainMenuOptEnum mainOpt = this.requestMainOption();

        switch (mainOpt) {
            case SIMETRICA:

                return MenuEnum.SIMETRICO;
            case ASIMETRICA:

                return MenuEnum.ASIMETRICO;
            case SALIR:

                return MenuEnum.SALIR;
            default:

                return MenuEnum.MAIN;
        }
    }

    public MenuEnum symetricMenu() {

        this.printSymetricMenu();
        SymetricMenuOptEnum symOpt = this.requestSymetricOption();
        String fileName = null;
        SerpentCBC engine = new SerpentCBC();

        switch (symOpt) {
            case VOLVER:
                break;
            case GENERAR_CLAVE:
                KeyGenerator kg = new KeyGenerator();
                break;
            case CIFRAR:
                this.printCiferMenu();
                fileName = this.getStrOpt();
                engine.encryptFile(fileName);
                break;
            case DESCIFRAR:
                this.printDecryptMenu();
                fileName = this.getStrOpt();
                engine.decryptFile(fileName);
                break;
            default:
                break;
        }
        return MenuEnum.MAIN;
    }

    private String getStrOpt() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {

            return br.readLine();
        } catch (IOException ioe) {
            System.out.println("IO error trying to read your name!");

            return "";
        }
    }
}
