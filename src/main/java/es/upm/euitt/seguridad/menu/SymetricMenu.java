package es.upm.euitt.seguridad.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import es.upm.euitt.seguridad.enumerators.MainMenuOptEnum;
import es.upm.euitt.seguridad.enumerators.SymetricMenuOptEnum;
import es.upm.euitt.seguridad.crypto.SerpentCBC;
import es.upm.euitt.seguridad.crypto.KeyGenerator;

public class SymetricMenu extends BaseMenu {
    public void printSymetricMenu() {
        System.out.println("Elija una opción para CRIPTOGRAFÍA SIMÉTRICA:");
        System.out.println("0. Volver al menu anterior.");
        System.out.println("1. Generar clave.");
        System.out.println("2. Cifrado.");
        System.out.println("3. Descifrado.");
    }

    public SymetricMenuOptEnum requestSymetricOption() {
        try {
            int optInt = Integer.parseInt(this.getStrOpt());

            return SymetricMenuOptEnum.getOpt(optInt);
        } catch (NumberFormatException e) {

            return SymetricMenuOptEnum.ERRONEA;
        }
    }

    public MainMenuOptEnum symetricMenu() {

        this.printSymetricMenu();
        SymetricMenuOptEnum symOpt = this.requestSymetricOption();
        SerpentCBC engine = new SerpentCBC();
        String fileName;

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

        return MainMenuOptEnum.MAIN;
    }
}
