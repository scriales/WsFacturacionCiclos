/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.sin.fac.ciclos.xml;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import org.apache.xml.security.signature.XMLSignature;
import org.w3c.dom.Document;

/**
 *
 * @author Sergio Criales
 */
public interface FirmaXml {

    Document signEnveloping(Document xmlDoc, String keyName) throws Exception;

    PublicKey getPublicKey() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
            IOException;
    
    PrivateKey getPrivateKey() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException;

    XMLSignature extractDigitalSignature(Document doc) throws Exception;

    public static final class Factory {

        public static FirmaXml getFirmaXmlApache() {
            return FirmaXmlApache.getFirmaXmlApache();
        }
    }
}
