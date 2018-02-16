/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.sin.fac.ciclos.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.InvalidParameterException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.xml.security.utils.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Sergio Criales
 */
public class XmlUtils {

    private XmlUtils() {
    }

    public static Document getDomFromString(String xmlSource) throws ParserConfigurationException,
            SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlSource)));
    }

    public static String getStringFromDom(Document document) throws TransformerException {
        if (null == document) {
            throw new InvalidParameterException("Objeto Document proporcionado es null.");
        }

        DOMSource domSource = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        transformer.transform(domSource, result);
        return writer.toString();
    }

    public static Element createDSctx(Document doc, String prefix,
            String namespace) {

        if ((prefix == null) || (prefix.trim().length() == 0)) {
            throw new IllegalArgumentException("You must supply a prefix");
        }

        Element ctx = doc.createElementNS(null, "namespaceContext");
        ctx.setAttributeNS(Constants.NamespaceSpecNS, "xmlns:" + prefix.trim(), namespace);

        return ctx;
    }
}
