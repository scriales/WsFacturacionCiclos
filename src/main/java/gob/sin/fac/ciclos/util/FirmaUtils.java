/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.sin.fac.ciclos.util;

import gob.sin.fac.ciclos.xml.FirmaXml;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.keys.content.KeyName;
import org.apache.xml.security.signature.ObjectContainer;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author sergio
 */
public class FirmaUtils {

    public static String verificarFirmaDigital(String mensajeXml) throws Exception {
        Document document = null;

        try {
            document = XmlUtils.getDomFromString(mensajeXml);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // En caso de que no se pueda transformar el mensaje xml a document
            String codError = "9001";
            String descError = "Servicio no pudo procesar mensaje, revise la estructura xml";
            throw new Exception(codError + " " + descError);
        }
        // En caso de que no se pueda transformar el mensaje xml a document

        FirmaXml firmaXmlApache = FirmaXml.Factory.getFirmaXmlApache();
        XMLSignature digitalSignature = null;

        try {
            digitalSignature = firmaXmlApache.extractDigitalSignature(document);
        } catch (Exception e) {
            // En caso de que no se pueda extraer la firma digital del document
            String codError = "9002";
            String descError = "Servicio no pudo procesar mensaje, no se pudo obtener firma digital del mensaje";
            throw new Exception(codError + " " + descError);
        }

        KeyInfo keyInfo = digitalSignature.getKeyInfo();

        KeyName itemKeyName = null;

        try {
            itemKeyName = keyInfo.itemKeyName(0);
        } catch (XMLSecurityException e) {
            // Este error se presenta si no se puede obtener el item que contiene el
            // keyName para hacer la verificación de firma digital
            String codError = "9003";
            String descError = "Servicio no pudo procesar mensaje, no se pudo obtener elemento keyName";
            throw new Exception(codError + " " + descError);
        }

        String keyName = itemKeyName.getKeyName();

        // Verificar el mensaje con la llave pública del solicitante
        boolean checkSignatureValue = false;

        try {
            String archivoProperties = "/media/sf_Data/Projects/agetic/params/public.properties";
            Properties properties = new Properties();
            File file = new File(archivoProperties);
            FileInputStream fis;

            try {
                fis = new FileInputStream(file);
                properties.load(fis);
            } catch (FileNotFoundException e) {
                System.out.println("Archivo de propiedades no encontrado " + archivoProperties + e);
                throw new Exception();
            } catch (IOException e) {
                System.out.println("Error al leer archivo de propiedades " + archivoProperties + e);
                throw new Exception();
            }

            String keystoreType = properties.getProperty("keystore.type");
            String keystoreFile = properties.getProperty("keystore.file");
            String keystorePass = properties.getProperty("keystore.pass");
            String privateKeyAlias = properties.getProperty("private.key.alias");
            String privateKeyPass = properties.getProperty("private.key.pass");
            String certificateAlias = properties.getProperty("certificate.alias");

            //KeyStoreInfo ksi = new KeyStoreInfo(keystoreType, keystoreFile, keystorePass, privateKeyAlias, privateKeyPass, certificateAlias);
            checkSignatureValue = digitalSignature.checkSignatureValue(firmaXmlApache.getPublicKey());
        } catch (XMLSignatureException e) {
            e.printStackTrace();
            String codError = "9005";
            String descError = "No se pudo realizar la verificación de firma digital";
            throw new Exception(codError + " " + descError);
        }

        if (!checkSignatureValue) {
            String codError = "9006";
            String descError = "Firma Digital no válida";
            throw new Exception(codError + " " + descError);
        }

        // Obtener el contenido firmado luego de la verificación
        ObjectContainer objectItem = digitalSignature.getObjectItem(0);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = null;

        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // Este error no debería ocurrir
            String codError = "9007";
            String descError = "Servicio no pudo procesar mensaje";
            throw new Exception(codError + " " + descError);
        }

        Document objectDocument = db.newDocument();
        objectDocument.setXmlStandalone(false);
        Node node = objectDocument.importNode(objectItem.getElement().getFirstChild(), true);
        objectDocument.appendChild(node);

        String objetoFirmado = null;

        try {
            objetoFirmado = XmlUtils.getStringFromDom(objectDocument);
        } catch (TransformerException e) {
            // Este error en caso de que el object del mensaje no pueda convertirse a
            // cadena, no debería ocurrir
            String codError = "9007";
            String descError = "Servicio no pudo procesar mensaje";
            throw new Exception(codError + " " + descError);
        }

        return objetoFirmado;
    }

    public static String firmarMensaje(String rutaBase, Document document, String keyName)
            throws Exception {
        //KeyStoreInfo generalPrivateKey = getGeneralPrivateKey(rutaBase, "private.properties");

        Document signEnveloping = firmarDocumento(document, keyName);

        String mensajeFirmado = stringFromDocument(signEnveloping);

        return mensajeFirmado;
    }

    public static Document firmarDocumento(Document document, String keyName) throws Exception {
        FirmaXml signer = FirmaXml.Factory.getFirmaXmlApache();

        try {
            Document signEnveloping = signer.signEnveloping(document, keyName);

            return signEnveloping;
        } catch (Exception e) {
            // En caso de que no se pueda firmar el mensaje xml
            String codError = "9008";
            String descError = "No se pudo realizar la firma digital";
            throw new Exception(codError + " " + descError);
        }
    }

    public static String stringFromDocument(Document document) throws Exception {
        try {
            String stringFromDom = XmlUtils.getStringFromDom(document);

            return stringFromDom;
        } catch (TransformerException e) {
            // Este error en caso de que el document no pueda convertirse a cadena, no
            // debería ocurrir
            System.out.println("Error de parseo de elemento object a cadena" + e);

            throw new Exception();
        }
    }
}
