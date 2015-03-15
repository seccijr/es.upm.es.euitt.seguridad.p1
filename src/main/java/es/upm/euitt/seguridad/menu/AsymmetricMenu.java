package es.upm.euitt.seguridad.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import es.upm.euitt.seguridad.enumerators.MainMenuOptEnum;
import es.upm.euitt.seguridad.enumerators.AsymmetricMenuOptEnum;
import es.upm.euitt.seguridad.crypto.KeyPairManager;
import es.upm.euitt.seguridad.crypto.RSAPKCS1Padded;
import org.bouncycastle.crypto.params.RSAKeyParameters;

public class AsymmetricMenu extends BaseMenu {

    public void printAsymmetricMenu() {
        System.out.println("Elija una opción para CRIPTOGRAFÍA ASIMÉTRICA:");
        System.out.println("0. Volver al menu anterior.");
        System.out.println("1. Generar pareja de claves.");
        System.out.println("2. Cifrado.");
        System.out.println("3. Descifrado.");
        System.out.println("4. Firmar digitalmente.");
        System.out.println("5. Verificar firma digital.");
    }

    protected void printIsPrivateMenu() {
        System.out.println("Es una clave privada?");
        System.out.println("1. Sí.");
        System.out.println("Cualquier otro caso. No.");
    }

    protected void toSignFileNameMenu() {
        System.out.println("Introduzca el nombre del fichero a firmar:");
    }

    protected void toVerifyFileNameMenu() {
        System.out.println("Introduzca el nombre del fichero que quiere verificar:");
    }

    protected void signatureFileNameMenu() {
        System.out.println("Introduzca el nombre del fichero con la firma:");
    }

    protected void printKeyFileNameMenu() {
        System.out.println("Introduzca el nombre del fichero para la clave:");
    }

    protected void printPublicKeyFileNameMenu() {
        System.out.println("Introduzca el nombre del fichero para la clave pública:");
    }

    protected void printPrivateKeyFileNameMenu() {
        System.out.println("Introduzca el nombre del fichero para la clave privada:");
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
        KeyPairManager keyManager = new KeyPairManager();
        String fileName = null;
        RSAPKCS1Padded cipher;
        String keyName;
        boolean isPrivate;
        RSAKeyParameters key;

        switch (symOpt) {
            case VOLVER:
                break;
            case GENERAR_CLAVE:
                this.printPublicKeyFileNameMenu();
                String publicKeyFileName = this.getStrOpt();
                this.printPrivateKeyFileNameMenu();
                String privateKeyFileName = this.getStrOpt();
                keyManager.generateAndSave(publicKeyFileName, privateKeyFileName);
                break;
            case CIFRAR:
                this.printKeyFileNameMenu();
                keyName = this.getStrOpt();
                this.printIsPrivateMenu();
                isPrivate = this.getStrOpt() == "1";
                this.printCiferMenu();
                fileName = this.getStrOpt();
                key = keyManager.restore(keyName, isPrivate);
                cipher = new RSAPKCS1Padded(key);
                cipher.encryptFile(fileName);
                break;
            case DESCIFRAR:
                this.printKeyFileNameMenu();
                keyName = this.getStrOpt();
                this.printIsPrivateMenu();
                isPrivate = this.getStrOpt() == "1";
                this.printDecryptMenu();
                fileName = this.getStrOpt();
                key = keyManager.restore(keyName, isPrivate);
                cipher = new RSAPKCS1Padded(key);
                cipher.decryptFile(fileName);
                break;
            case FIRMAR:
                this.printKeyFileNameMenu();
                keyName = this.getStrOpt();
                this.toSignFileNameMenu();
                fileName = this.getStrOpt();
                key = keyManager.restore(keyName, true);
                cipher = new RSAPKCS1Padded(key);
                cipher.signFile(fileName);
                break;
            case VERIFICAR:
                this.printKeyFileNameMenu();
                keyName = this.getStrOpt();
                this.toVerifyFileNameMenu();
                fileName = this.getStrOpt();
                key = keyManager.restore(keyName, false);
                cipher = new RSAPKCS1Padded(key);
                boolean verification = cipher.verifyFile(fileName);
                if (verification) System.out.println("Fichero coherente");
                else System.out.println("Fichero corrupto");
                break;
            default:
                break;
        }

        return MainMenuOptEnum.MAIN;
    }
}
