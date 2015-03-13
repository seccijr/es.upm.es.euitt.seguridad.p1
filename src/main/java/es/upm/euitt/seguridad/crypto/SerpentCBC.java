package es.upm.euitt.seguridad.crypto;

import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.engines.SerpentEngine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import java.lang.IllegalStateException;

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
