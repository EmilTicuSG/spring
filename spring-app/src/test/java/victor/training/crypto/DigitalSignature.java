package victor.training.crypto;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

import java.security.*;

import static org.assertj.core.api.Assertions.assertThat;

public class DigitalSignature {

    @Test
    public void testAsymmetricSigningWithSignatureClasses() throws GeneralSecurityException, DecoderException {
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair keyPair = kpGen.generateKeyPair();
        Utils.printByteArray("private key", keyPair.getPrivate().getEncoded());
        Utils.printByteArray("public key", keyPair.getPublic().getEncoded());

        String data = "My Company is the best!!!";

        Signature signatureAlgorithm = Signature.getInstance("SHA256WithRSA");
        signatureAlgorithm.initSign(keyPair.getPrivate());
        signatureAlgorithm.update(data.getBytes());
        // TODO initSign(privateK), update, sign
        byte[] signature = signatureAlgorithm.sign();

        Utils.printByteArray("signature", signature);


        // verification on the other end of the channel ----
        String receivedData = "My Company is the WORST!!!";
//        String receivedData = "My Company is the worst!!!";

        Signature verificationAlgorithm = Signature.getInstance("SHA256WithRSA");
        verificationAlgorithm.initVerify(keyPair.getPublic());
        verificationAlgorithm.update(receivedData.getBytes());
        // TODO initVerify(publicK), update, verify

        boolean matches = verificationAlgorithm.verify(signature);

        assertThat(matches).as("Signature Matches").isTrue();
    }

}
