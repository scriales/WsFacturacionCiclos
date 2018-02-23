/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.sin.fac.ciclos.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author sergio
 */
public interface CifraXml {
    void cifrar(Document document, Element element, boolean encryptContent) throws Exception;
}
