package es.upm.euitt.seguridad.crypto;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.apache.commons.io.IOUtils;

public class RSAPKCS1Padded extends Encryptor {
    private AsymmetricBlockCipher encryptCipher;
    private AsymmetricBlockCipher decryptCipher;
    private RSAKeyParameters key;

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
            int inputBlockSize = encryptCipher.getInputBlockSize();
            byte[] buf = new byte[inputBlockSize];

            while ((noBytesRead = in.read(buf)) >= 0) {
                byte[] obuf = encryptCipher.processBlock(buf, 0, noBytesRead);
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

            int inputBlockSize = decryptCipher.getInputBlockSize();
            byte[] buf = new byte[inputBlockSize];
            while ((noBytesRead = in.read(buf)) >= 0) {
                byte[] obuf = decryptCipher.processBlock(buf, 0, noBytesRead);
                out.write(obuf, 0, obuf.length);
            }
            out.flush();
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void sign(InputStream in, OutputStream out) throws InvalidCipherTextException {
        try {
            SHA256Summer summer = new SHA256Summer();
            String hash = summer.hash(in);
            byte[] messageBytes = hash.getBytes();
            byte[] hexEncodedCipher = encryptCipher.processBlock(messageBytes, 0, messageBytes.length);
            out.write(hexEncodedCipher, 0,  hexEncodedCipher.length);
            out.flush();
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public boolean verify(InputStream in, InputStream ss) throws InvalidCipherTextException {
        boolean result = false;

        try {
            SHA256Summer summer = new SHA256Summer();
            String hash = summer.hash(in);
            byte[] signature = IOUtils.toByteArray(ss);
            byte[] decryptedSignature = decryptCipher.processBlock(signature, 0, signature.length);

            result = hash.equals(new String(decryptedSignature));
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void signFile(String inFileName, String signatureFileName) {
        try {

            File ifile = new File(inFileName);
            File ofile = new File(signatureFileName);
            InputStream fis = new FileInputStream(ifile);
            OutputStream fos = new FileOutputStream(ofile);

            // Encrypt
            this.sign(fis, fos);

            fis.close();
            fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifyFile(String inFileName, String signatureFileName) {
        boolean result = false;

        try {

            File ifile = new File(inFileName);
            File ssfile = new File(signatureFileName);
            InputStream fis = new FileInputStream(ifile);
            InputStream ss = new FileInputStream(ssfile);

            // Encrypt
            result = this.verify(fis, ss);

            fis.close();
            ss.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

