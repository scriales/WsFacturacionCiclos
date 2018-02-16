/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.sin.fac.ciclos.bl;

import gob.sin.fac.ciclos.to.InicioCicloRespuestaTO;
import gob.sin.fac.ciclos.to.InicioCicloTO;
import javax.ejb.Local;

/**
 *
 * @author Sergio Criales
 */
@Local
public interface CiclosLocal {
    public InicioCicloRespuestaTO iniciarCiclo(InicioCicloTO inicioCicloTO);
}
