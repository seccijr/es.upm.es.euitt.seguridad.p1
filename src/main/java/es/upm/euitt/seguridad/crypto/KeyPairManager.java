package es.upm.euitt.seguridad.crypto;

import java.io.PrintWriter;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.IOException;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.apache.commons.codec.binary.Hex;

public class KeyPairManager {
    public AsymmetricCipherKeyPair generate() throws NoSuchAlgorithmException {
        RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
        generator.init(new RSAKeyGenerationParameters(new BigInteger("10001", 16), SecureRandom.getInstance("SHA1PRNG"), 1024, 80));

        return generator.generateKeyPair();
    }

    public AsymmetricCipherKeyPair generateAndSave(String publicKeyPath, String privateKeyPath) {
        AsymmetricCipherKeyPair keyPair = null;

        try {
            keyPair = this.generate();
            this.savePair(keyPair, publicKeyPath, privateKeyPath);

        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return keyPair;
    }

    public void savePair(AsymmetricCipherKeyPair keyPair, String publicKeyPath, String privateKeyPath) {
        RSAKeyParameters publicKey = (RSAKeyParameters)keyPair.getPublic();
        RSAKeyParameters privateKey = (RSAKeyParameters)keyPair.getPrivate();

        this.writeKeyToFile(publicKey, publicKeyPath);
        this.writeKeyToFile(privateKey, privateKeyPath);
    }

    public void writeKeyToFile(RSAKeyParameters key, String path) {
        File keyFile = new File(path);
        String lineSep = System.getProperty("line.separator");
        PrintWriter keyWriter = null;

        try {
            if (!keyFile.exists()) {
                keyFile.createNewFile();
            }

            keyWriter = new PrintWriter(keyFile);
            String modulus = Hex.encodeHexString(key.getModulus().toByteArray());
            String exponent = Hex.encodeHexString(key.getExponent().toByteArray());

            keyWriter.write(modulus + lineSep + exponent);
            keyWriter.flush();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            if (keyWriter != null) {
                keyWriter.close();
            }
        }
    }

}
