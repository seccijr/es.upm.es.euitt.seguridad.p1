package es.upm.euitt.seguridad.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import es.upm.euitt.seguridad.enumerators.MainMenuOptEnum;
import es.upm.euitt.seguridad.enumerators.AsymetricMenuOptEnum;

public class AsymetricMenu extends BaseMenu {
    public void printAsymetricMenu() {
        System.out.println("Elija una opción para CRIPTOGRAFÍA ASIMÉTRICA:");
        System.out.println("0. Volver al menu anterior.");
        System.out.println("1. Generar pareja de claves.");
        System.out.println("2. Cifrado.");
        System.out.println("3. Descifrado.");
        System.out.println("4. Firmar digitalmente.");
        System.out.println("5. Verificar firma digital.");
    }

    public AsymetricMenuOptEnum requestAsymetricOption() {
        try {
            int optInt = Integer.parseInt(this.getStrOpt());

            return AsymetricMenuOptEnum.getOpt(optInt);
        } catch (NumberFormatException e) {

            return AsymetricMenuOptEnum.ERRONEA;
        }
    }

    public MainMenuOptEnum asymetricMenu() {

        this.printAsymetricMenu();
        AsymetricMenuOptEnum symOpt = this.requestAsymetricOption();
        String fileName = null;

        switch (symOpt) {
            case VOLVER:
                break;
            case GENERAR_CLAVE:
                break;
            case CIFRAR:
                this.printCiferMenu();
                fileName = this.getStrOpt();
                break;
            case DESCIFRAR:
                this.printDecryptMenu();
                fileName = this.getStrOpt();
                break;
            default:
                break;
        }

        return MainMenuOptEnum.MAIN;
    }
}
