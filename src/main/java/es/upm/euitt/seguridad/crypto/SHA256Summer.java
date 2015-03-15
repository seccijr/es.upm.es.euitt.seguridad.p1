package es.upm.euitt.seguridad.crypto;

import java.io.InputStream;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.apache.commons.codec.binary.Hex;

public class SHA256Summer {

    public String hash(InputStream in) {
        String res = null;

        try {
            int noBytesRead = 0;
            SHA256Digest digest = new SHA256Digest();
            byte[] buf = new byte[16];

            while ((noBytesRead = in.read(buf)) >= 0) {
                digest.update(buf, 0, noBytesRead);
            }

            byte[] resBuf = new byte[digest.getDigestSize()];
            digest.doFinal(resBuf, 0);
            res = Hex.encodeHexString(resBuf);
        }
        catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }

        return res;
    }
}
