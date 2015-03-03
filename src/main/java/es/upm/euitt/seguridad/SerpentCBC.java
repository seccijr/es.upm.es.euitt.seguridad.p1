package es.upm.euitt.seguridad;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.engines.SerpentEngine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import javax.crypto.ShortBufferException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import java.lang.IllegalStateException;

public class SerpentCBC {
    PaddedBufferedBlockCipher encryptCipher;
    PaddedBufferedBlockCipher decryptCipher;

    byte[] buf = new byte[16];
    byte[] obuf = new byte[512];

    byte[] key = null;

    public SerpentCBC(){
        key = "SECRET_1SECRET_2SECRET_3".getBytes();
        InitCiphers();
    }
    public SerpentCBC(byte[] keyBytes){
        key = new byte[keyBytes.length];
        System.arraycopy(keyBytes, 0 , key, 0, keyBytes.length);
        InitCiphers();
    }

    private void InitCiphers(){
        encryptCipher = new PaddedBufferedBlockCipher(new CFBBlockCipher(new SerpentEngine(), 128));
        encryptCipher.init(true, new KeyParameter(key));
        decryptCipher =  new PaddedBufferedBlockCipher(new CFBBlockCipher(new SerpentEngine(), 128));
        decryptCipher.init(false, new KeyParameter(key));
    }

    public void encrypt(InputStream in, OutputStream out) throws ShortBufferException,
       IllegalBlockSizeException, BadPaddingException, DataLengthException,
       IllegalStateException, InvalidCipherTextException {
       try {
           int noBytesRead = 0;
           int noBytesProcessed = 0;

           while ((noBytesRead = in.read(buf)) >= 0) {
               noBytesProcessed = encryptCipher.processBytes(buf, 0, noBytesRead, obuf, 0);
               out.write(obuf, 0, noBytesProcessed);
           }
           noBytesProcessed = encryptCipher.doFinal(obuf, 0);
           out.write(obuf, 0, noBytesProcessed);
           out.flush();
       }
       catch (java.io.IOException e) {
           System.out.println(e.getMessage());
       }
    }

    public void decrypt(InputStream in, OutputStream out)
    throws ShortBufferException, IllegalBlockSizeException,  BadPaddingException,
            DataLengthException, IllegalStateException, InvalidCipherTextException {
        try {
            int noBytesRead = 0;
            int noBytesProcessed = 0;

            while ((noBytesRead = in.read(buf)) >= 0) {
                    noBytesProcessed = decryptCipher.processBytes(buf, 0, noBytesRead, obuf, 0);
                    out.write(obuf, 0, noBytesProcessed);
            }
            noBytesProcessed = decryptCipher.doFinal(obuf, 0);
            out.write(obuf, 0, noBytesProcessed);
            out.flush();
        }
        catch (java.io.IOException e) {
             System.out.println(e.getMessage());
        }
    }

    public void encryptFile(String fileName){
        try {

            File ifile = new File(fileName);
            File ofile = new File(fileName + ".encrypted");
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

    public void decryptFile(String fileName){
        try {

            File ifile = new File(fileName);
            File ofile = new File(fileName + ".decrypted");
            InputStream fis = new FileInputStream(ifile);
            OutputStream fos = new FileOutputStream(ofile);

            // Encrypt
            this.decrypt(fis, fos);

            fis.close();
            fos.close();
        }
        catch (Exception e) {
            System.out.println("");
        }
    }
}
