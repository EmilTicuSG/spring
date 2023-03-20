package victor.training.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SymmetricEncryption {

    @Test
    public void testSymmetricEncryption() throws GeneralSecurityException {
        // generate a symmetric encryption key
        // TODO KeyGenerator AES, init 192 bits
        Key key = null;
        Utils.printByteArray("key", key.getEncoded());

        // get a random Initialization Vector (IV) for the block symmetric encryption
        byte[] iv = generateRandomBytes(16);
        Utils.printByteArray("ivSpec", iv);

        //input
        byte[] input = "SORSIX ROCKS!".getBytes();
        Utils.printText("input", input);

        // encryption
        Cipher encrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // TODO init(ENCRYPT,key,iv), .doFinal

        byte[] encryptedOutput = null;
        Utils.printByteArray("encrypted output", encryptedOutput);

        // decryption
        // TODO idem as above, with DECRYPT
        byte[] decryptedOutput = null;
        Utils.printText("decrypted input", decryptedOutput);
    }

    private static byte[] generateRandomBytes(int size) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] random = new byte[size];
        secureRandom.nextBytes(random);
        return random;
    }
}
