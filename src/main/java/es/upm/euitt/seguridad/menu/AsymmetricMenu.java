package es.upm.euitt.seguridad.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import es.upm.euitt.seguridad.enumerators.MainMenuOptEnum;
import es.upm.euitt.seguridad.enumerators.AsymmetricMenuOptEnum;
import es.upm.euitt.seguridad.crypto.KeyPairManager;

public class AsymmetricMenu extends BaseMenu {
    AsymmetricCipherKeyPair keyPair = null;

    public void printAsymmetricMenu() {
        System.out.println("Elija una opción para CRIPTOGRAFÍA ASIMÉTRICA:");
        System.out.println("0. Volver al menu anterior.");
        System.out.println("1. Generar pareja de claves.");
        System.out.println("2. Cifrado.");
        System.out.println("3. Descifrado.");
        System.out.println("4. Firmar digitalmente.");
        System.out.println("5. Verificar firma digital.");
    }

    protected void printPublicKeyFileNameMenu() {
        System.out.println("Introduzca el nombre del fichero a donde quiere alojar la clave pública:");
    }

    protected void printPrivateKeyFileNameMenu() {
        System.out.println("Introduzca el nombre del fichero a donde quiere alojar la clave private:");
    }

    public AsymmetricMenuOptEnum requestAsymmetricOption() {
        try {
            int optInt = Integer.parseInt(this.getStrOpt());

            return AsymmetricMenuOptEnum.getOpt(optInt);
        } catch (NumberFormatException e) {

            return AsymmetricMenuOptEnum.ERRONEA;
        }
    }

    public MainMenuOptEnum asymetricMenu() {

        this.printAsymmetricMenu();
        AsymmetricMenuOptEnum symOpt = this.requestAsymmetricOption();
        String fileName = null;

        switch (symOpt) {
            case VOLVER:
                break;
            case GENERAR_CLAVE:
                this.printPublicKeyFileNameMenu();
                String publicKeyFileName = this.getStrOpt();
                this.printPrivateKeyFileNameMenu();
                String privateKeyFileName = this.getStrOpt();
                KeyPairManager keyManager = new KeyPairManager();
                keyManager.generateAndSave(publicKeyFileName, privateKeyFileName);
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
