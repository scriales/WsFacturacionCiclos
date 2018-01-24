/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.sin.fac.ciclos.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author sergio
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface WsCiclos {
    @WebMethod
    String iniciarCiclo(String mensaje);
}
