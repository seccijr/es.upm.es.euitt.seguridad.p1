package es.upm.euitt.seguridad.crypto;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.bouncycastle.crypto.InvalidCipherTextException;

public abstract class Encryptor {
    public abstract void encrypt(InputStream in, OutputStream out) throws InvalidCipherTextException;

    public abstract void decrypt(InputStream in, OutputStream out) throws InvalidCipherTextException;

    public void encryptFile(String inFileName, String outFileName) {
        try {

            File ifile = new File(inFileName);
            File ofile = new File(outFileName);
            InputStream fis = new FileInputStream(ifile);
            OutputStream fos = new FileOutputStream(ofile);

            // Encrypt
            this.encrypt(fis, fos);

            fis.close();
            fos.close();
        }
        catch (Exception e) {
            System.out.println("");
        }
    }

    public void decryptFile(String inFileName, String outFileName) {
        try {

            File ifile = new File(inFileName);
            File ofile = new File(outFileName);
            InputStream fis = new FileInputStream(ifile);
            OutputStream fos = new FileOutputStream(ofile);

            // Encrypt
            this.decrypt(fis, fos);

            fis.close();
            fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
