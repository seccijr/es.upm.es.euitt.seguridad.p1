package es.upm.euitt.seguridad.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import es.upm.euitt.seguridad.enumerators.MainMenuOptEnum;
import es.upm.euitt.seguridad.enumerators.SymetricMenuOptEnum;
import es.upm.euitt.seguridad.crypto.SerpentCBC;

public class SymetricMenu extends BaseMenu {
    public void printSymetricMenu() {
        System.out.println("Elija una opción para CRIPTOGRAFÍA SIMÉTRICA:");
        System.out.println("0. Volver al menu anterior.");
        System.out.println("1. Generar clave.");
        System.out.println("2. Cifrado.");
        System.out.println("3. Descifrado.");
    }

    protected void printKeyFileNameMenu() {
        System.out.println("Introduzca el nombre del fichero para la clave:");
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
        String keyName;
        byte[] key;

        switch (symOpt) {
            case VOLVER:
                break;
            case GENERAR_CLAVE:
                this.printKeyFileNameMenu();
                fileName = this.getStrOpt();
                engine.generateKey(fileName);
                break;
            case CIFRAR:
                this.printKeyFileNameMenu();
                keyName = this.getStrOpt();
                key = engine.restoreKey(keyName);
                this.printCiferMenu();
                fileName = this.getStrOpt();
                engine = new SerpentCBC(key);
                engine.encryptFile(fileName);
                break;
            case DESCIFRAR:
                this.printKeyFileNameMenu();
                keyName = this.getStrOpt();
                key = engine.restoreKey(keyName);
                this.printDecryptMenu();
                fileName = this.getStrOpt();
                engine = new SerpentCBC(key);
                engine.decryptFile(fileName);
                break;
            default:
                break;
        }

        return MainMenuOptEnum.MAIN;
    }
}
