/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.sin.fac.ciclos.ws;

import gob.sin.fac.ciclos.bl.CiclosLocal;
import gob.sin.fac.ciclos.to.InicioCicloRespuestaTO;
import gob.sin.fac.ciclos.to.InicioCicloTO;
import gob.sin.fac.ciclos.util.FirmaUtils;
import gob.sin.fac.ciclos.util.JaxbUtils;
import java.util.Arrays;
import javax.ejb.EJB;
import javax.jws.WebService;

/**
 *
 * @author Sergio Criales
 */
@WebService
public class WsCiclosImpl implements WsCiclos {

    @EJB
    private CiclosLocal ciclosLocal;

    @Override
    public String iniciarCiclo(String mensaje) {
        try {
            // Verificar la firma digital
            String xml = FirmaUtils.verificarFirmaDigital(mensaje);
            // Transformar el XML en objeto
            InicioCicloTO inicioCicloTO = JaxbUtils.unmarshall(xml, InicioCicloTO.class);
            // Enviar el objeto al negocio
            InicioCicloRespuestaTO inicioCicloRespuestaTO = ciclosLocal.iniciarCiclo(inicioCicloTO);
            // Transformar el objeto a XML
            String respuesta = JaxbUtils.marshall(inicioCicloRespuestaTO);

            return respuesta;
        } catch (Exception ex) {
            InicioCicloRespuestaTO inicioCicloRespuestaTO = new InicioCicloRespuestaTO();
            inicioCicloRespuestaTO.setEticket("");
            inicioCicloRespuestaTO.setMensajes(Arrays.asList(ex.getMessage()));
            String respuesta = "";
            try {
                respuesta = JaxbUtils.marshall(inicioCicloRespuestaTO);
            } catch (Exception ex1) {
                // Ignorar la excepcion, no ocurrirá
            }

            return respuesta;
        }
    }
}
