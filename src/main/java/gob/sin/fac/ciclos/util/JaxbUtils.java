/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.sin.fac.ciclos.util;

import gob.sin.fac.ciclos.to.InicioCicloRespuestaTO;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author sergio
 */
public class JaxbUtils {

    private JaxbUtils() {
    }

    public static <T> String marshall(T object) throws Exception {
        if (null == object) {
            throw new Exception("Objeto a transformar es nulo");
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", true);

            Writer writer = new StringWriter();

            marshaller.marshal(object, writer);

            return writer.toString();
        } catch (JAXBException e) {
            throw new Exception("Error en la transformación de objeto a representación xml");
        }
    }

    public static <T> T unmarshall(String xmlDocument, Class<T> clazz)
            throws Exception {
        if (null == xmlDocument || null == clazz) {
            throw new Exception("Parámetros no deben ser nulos");
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            InputStream inputStream = new ByteArrayInputStream(xmlDocument.getBytes());

            T object = (T) unmarshaller.unmarshal(inputStream);

            return object;
        } catch (JAXBException e) {
            throw new Exception("Error en la transformación de mensaje xml a objetos");
        }
    }

    public static void main(String[] args) throws Exception {
        InicioCicloRespuestaTO inicioCicloRespuestaTO = new InicioCicloRespuestaTO();
        inicioCicloRespuestaTO.setEticket("asdfghjkl");
        inicioCicloRespuestaTO.setMensajes(Arrays.asList("123", "234"));

        String xml = marshall(inicioCicloRespuestaTO);
        System.out.println("XML: " + xml);
        
        InicioCicloRespuestaTO objeto = unmarshall(xml, InicioCicloRespuestaTO.class);
        System.out.println("OBJETO: " + objeto);
    }
}
