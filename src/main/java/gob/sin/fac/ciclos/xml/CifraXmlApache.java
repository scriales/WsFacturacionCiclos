/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.sin.fac.ciclos.xml;

import gob.sin.fac.ciclos.util.XmlUtils;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.transform.TransformerException;
import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.keys.KeyInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Sergio Criales
 */
public class CifraXmlApache implements CifraXml {

    private static final CifraXmlApache cifrador = new CifraXmlApache();

    static {
        org.apache.xml.security.Init.init();
    }

    private CifraXmlApache() {
    }

    public static CifraXml getDefaultInstance() {
        return cifrador;
    }

    @Override
    public void cifrar(Document document, Element element, boolean encryptContent) throws Exception {
        Key symmetricKey = generateDataEncryptionKey("AES");

        XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.TRIPLEDES_KeyWrap);
        keyCipher.init(XMLCipher.WRAP_MODE, generateKeyEncryptionKey());
        EncryptedKey encryptedKey = keyCipher.encryptKey(document, symmetricKey);

        XMLCipher xmlCipher = XMLCipher.getInstance(XMLCipher.AES_256);
        xmlCipher.init(XMLCipher.ENCRYPT_MODE, symmetricKey);

        EncryptedData encryptedData = xmlCipher.getEncryptedData();
        KeyInfo keyInfo = new KeyInfo(document);
        keyInfo.add(encryptedKey);
        encryptedData.setKeyInfo(keyInfo);

        xmlCipher.doFinal(document, element, true);
    }

    private SecretKey generateDataEncryptionKey(String algorithm)
            throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    private static SecretKey generateKeyEncryptionKey()
            throws Exception {
        String jceAlgorithmName = "DESede";
        KeyGenerator keyGenerator
                = KeyGenerator.getInstance(jceAlgorithmName);
        SecretKey keyEncryptKey = keyGenerator.generateKey();

        return keyEncryptKey;
    }

    public static void main(String[] args) {
        try {
            Document document = XmlUtils.getDomFromString("<Representante>\n"
                    + "<NombreVinculo>BARRIOS CALVETI JIMMY FELIX</NombreVinculo>\n"
                    + "<NumId id='miId'>00004446546</NumId>\n"
                    + "<IdClase>1</IdClase>\n"
                    + "<FecRegistro>20171222</FecRegistro>\n"
                    + "<NumDoc>0875/17</NumDoc>\n"
                    + "<FecDocumento>20171204</FecDocumento>\n"
                    + "<NoticiaDocumento>TESTIMONIO DE OTORGAMIENTO DE PODER ESPECIAL, AMPLIO Y SUFICIENTE TIPO\"D\" EN FAVOR DE JIMMY FELIX BARRIOS CALVETI. ENCARGADO DE AGENCIA VOLANTE</NoticiaDocumento>\n"
                    + "<TipoVinculo>REPRESENTANTE LEGAL</TipoVinculo>\n"
                    + "<IdLibro>13</IdLibro>\n"
                    + "<NumReg>00216199</NumReg>\n"
                    + "</Representante>");
            Element element = document.getDocumentElement();
            CifraXmlApache cifraXmlApache = new CifraXmlApache();
            System.out.println("Element: " + element);
            cifraXmlApache.cifrar(document, element, true);
            System.out.println(XmlUtils.getStringFromDom(document));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
