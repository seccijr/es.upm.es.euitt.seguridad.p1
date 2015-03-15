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
        byte[] key = new byte[32];
        SerpentCBC engine = new SerpentCBC(key);
        String inFileName;
        String outFileName;
        String keyName;

        switch (symOpt) {
            case VOLVER:
                break;
            case GENERAR_CLAVE:
                this.printKeyFileNameMenu();
                keyName = this.getStrOpt();
                engine.generateKey(keyName);
                break;
            case CIFRAR:
                this.printKeyFileNameMenu();
                keyName = this.getStrOpt();
                key = engine.restoreKey(keyName);
                this.printCiferMenu();
                inFileName = this.getStrOpt();
                this.printResultMenu();
                outFileName = this.getStrOpt();
                engine = new SerpentCBC(key);
                engine.encryptFile(inFileName, outFileName);
                break;
            case DESCIFRAR:
                this.printKeyFileNameMenu();
                keyName = this.getStrOpt();
                key = engine.restoreKey(keyName);
                this.printDecryptMenu();
                inFileName = this.getStrOpt();
                this.printResultMenu();
                outFileName = this.getStrOpt();
                engine = new SerpentCBC(key);
                engine.decryptFile(inFileName, outFileName);
                break;
            default:
                break;
        }

        return MainMenuOptEnum.MAIN;
    }
}
