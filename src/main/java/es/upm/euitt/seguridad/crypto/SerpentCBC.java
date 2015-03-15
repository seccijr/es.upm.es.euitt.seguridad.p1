package es.upm.euitt.seguridad.crypto;

import java.io.File;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.engines.SerpentEngine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import java.lang.IllegalStateException;
import java.util.Random;
import org.apache.commons.codec.binary.Hex;

public class SerpentCBC extends Encryptor {
    PaddedBufferedBlockCipher encryptCipher;
    PaddedBufferedBlockCipher decryptCipher;

    private byte[] buf = new byte[16];
    private byte[] obuf = new byte[512];

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

    public void generateKey(String fileName) {
        try {
            File file = new File(fileName);
            FileOutputStream os = new FileOutputStream(file);
            byte[] r = new byte[32];
            Random rand = new Random();
            rand.nextBytes(r);
            System.out.println("Clave generadad: " + new String(r));
            byte[] rHex = Hex.encodeHexString(r).getBytes();
            os.write(rHex, 0, rHex.length);
            os.flush();
            os.close();

            System.out.println("Clave guardada: " + new String(rHex));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] restoreKey(String fileName) {
        byte[] r = new byte[32];

        try {
            File file = new File(fileName);
            FileInputStream in = new FileInputStream(file);
            int noBytesRead = 0;
            byte[] buf = new byte[16];

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            while ((noBytesRead = in.read(buf)) >= 0) {
                outputStream.write(buf);
            }
            byte[] rHex = outputStream.toByteArray();
            System.out.println("Clave leida: " + new String(rHex));
            Hex hex = new Hex();
            r = hex.decode(rHex);
            System.out.println("Clave recuperada: " + new String(r));

            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    private void InitCiphers(){
        encryptCipher = new PaddedBufferedBlockCipher(new CFBBlockCipher(new SerpentEngine(), 128));
        encryptCipher.init(true, new KeyParameter(key));
        decryptCipher =  new PaddedBufferedBlockCipher(new CFBBlockCipher(new SerpentEngine(), 128));
        decryptCipher.init(false, new KeyParameter(key));
    }

    @Override
    public void encrypt(InputStream in, OutputStream out) throws  InvalidCipherTextException {
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
        catch (DataLengthException e) {}
        catch (IllegalStateException e) {}
    }

    @Override
    public void decrypt(InputStream in, OutputStream out) throws InvalidCipherTextException {
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
        catch (DataLengthException e) {}
        catch (IllegalStateException e) {}
    }
}
