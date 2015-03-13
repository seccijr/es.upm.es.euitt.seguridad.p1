package es.upm.euitt.seguridad.crypto;

import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.InvalidCipherTextException;

public class RSAPKCS1Padded extends Encryptor {
    private AsymmetricBlockCipher encryptCipher;
    private AsymmetricBlockCipher decryptCipher;
    private RSAKeyParameters key;

    private byte[] buf = new byte[16];
    private byte[] obuf = new byte[512];

    public RSAPKCS1Padded(RSAKeyParameters key){
        this.key = key;
        InitCiphers(key);
    }

    private void InitCiphers(RSAKeyParameters key){
        encryptCipher = new PKCS1Encoding(new RSAEngine());
        encryptCipher.init(true, key);
        decryptCipher =  new PKCS1Encoding(new RSAEngine());
        decryptCipher.init(false, key);
    }

    @Override
    public void encrypt(InputStream in, OutputStream out) throws InvalidCipherTextException {
       try {
           int noBytesRead = 0;

           while ((noBytesRead = in.read(buf)) >= 0) {
               obuf = encryptCipher.processBlock(buf, 0, noBytesRead);
               out.write(obuf, 0, obuf.length);
           }
           out.flush();
       }
       catch (java.io.IOException e) {
           System.out.println(e.getMessage());
       }
    }

    @Override
    public void decrypt(InputStream in, OutputStream out) throws InvalidCipherTextException {
        try {
            int noBytesRead = 0;

            while ((noBytesRead = in.read(buf)) >= 0) {
                    obuf = decryptCipher.processBlock(buf, 0, noBytesRead);
                    out.write(obuf, 0, obuf.length);
            }
            out.flush();
        }
        catch (java.io.IOException e) {
             System.out.println(e.getMessage());
        }
    }
}
