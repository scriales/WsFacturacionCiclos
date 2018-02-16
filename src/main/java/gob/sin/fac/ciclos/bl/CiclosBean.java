/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.sin.fac.ciclos.bl;

import gob.sin.fac.ciclos.to.InicioCicloRespuestaTO;
import gob.sin.fac.ciclos.to.InicioCicloTO;
import java.util.Arrays;

/**
 *
 * @author Sergio Criales
 */
public class CiclosBean implements CiclosLocal {

    @Override
    public InicioCicloRespuestaTO iniciarCiclo(InicioCicloTO inicioCicloTO) {
        // TODO: llamar a la lógica de negocio propio del inicio de ciclo
        
        InicioCicloRespuestaTO inicioCicloRespuestaTO = new InicioCicloRespuestaTO();
        inicioCicloRespuestaTO.setEticket("llenar ticket aquí");
        inicioCicloRespuestaTO.setMensajes(Arrays.asList("mensaje1", "mensaje2", "mensaje3"));
        
        return inicioCicloRespuestaTO;
    }
}
