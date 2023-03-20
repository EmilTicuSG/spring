package victor.training.crypto;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import static org.assertj.core.api.Assertions.assertThat;

public class DigitalSignatureJKS {

    @Test
    public void testAsymmetricSigningWithSignatureClasses() throws GeneralSecurityException, DecoderException, IOException {
        PrivateKey privateKey = getSenderPrivateKeyFromSenderJKS();

        String data = "My Company is the best!!!";

        Signature signatureAlgorithm = Signature.getInstance("SHA256WithRSA");
        signatureAlgorithm.initSign(privateKey);
        signatureAlgorithm.update(data.getBytes());
        byte[] signature = signatureAlgorithm.sign();

        Utils.printByteArray("signature", signature);


        // verification on the other end of the channel ----
        String receivedData = "My Company is the best!!!";
        PublicKey publicKey = getSenderCertificateFromRecipientJKS();

        Signature verificationAlgorithm = Signature.getInstance("SHA256WithRSA");
        verificationAlgorithm.initVerify(publicKey);
        verificationAlgorithm.update(receivedData.getBytes());

        boolean matches = verificationAlgorithm.verify(signature);

        assertThat(matches).as("Signature Matches").isTrue();
    }

    private static PrivateKey getSenderPrivateKeyFromSenderJKS() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        InputStream keystoreInputStream = DigitalSignatureJKS.class.getResourceAsStream("/security/a.jks");
        // TODO .load .getKey(a, storepass) as (PrivateKey)
        PrivateKey privateKey = null;

        Utils.printByteArray("private key", privateKey.getEncoded());
        return privateKey;
    }

    private static PublicKey getSenderCertificateFromRecipientJKS() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        InputStream keystoreInputStream = DigitalSignatureJKS.class.getResourceAsStream("/security/b.jks");
        // TODO KeyStore.getinstance(JKS), load using 'storepass' as password

        // TODO getCertificate(a).getPublicKey()
        Certificate certificate = null;

        Utils.printByteArray("public key", certificate.getPublicKey().getEncoded());
        return certificate.getPublicKey();
    }

}
