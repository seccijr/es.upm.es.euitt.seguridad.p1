package es.upm.euitt.seguridad.crypto;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;

public class KeyPairGenerator {
    public boolean generate(String privateKeyFileName, String publicKeyFileName) throws NoSuchAlgorithmException {
        RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
        generator.init(new RSAKeyGenerationParameters(
            new BigInteger("10001", 16),
            SecureRandom.getInstance("SHA1PRNG"),
            1024,
            80
        ));

        AsymmetricCipherKeyPair keyPair = generator.generateKeyPair();

        return false;
    }
}
