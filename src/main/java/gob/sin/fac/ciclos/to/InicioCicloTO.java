/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.sin.fac.ciclos.to;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sergio Criales
 */
@XmlRootElement
public class InicioCicloTO {
    private BigInteger nit;
    private String codigoCaracteristica;
    private BigInteger codigoActividadEconomica;
    private String tipoAutorizacion;
    private String usuario;
    private BigInteger nitEmisor;
    private Long paralela;
    private Long sucursal;

    public BigInteger getNit() {
        return nit;
    }

    public void setNit(BigInteger nit) {
        this.nit = nit;
    }

    public String getCodigoCaracteristica() {
        return codigoCaracteristica;
    }

    public void setCodigoCaracteristica(String codigoCaracteristica) {
        this.codigoCaracteristica = codigoCaracteristica;
    }

    public BigInteger getCodigoActividadEconomica() {
        return codigoActividadEconomica;
    }

    public void setCodigoActividadEconomica(BigInteger codigoActividadEconomica) {
        this.codigoActividadEconomica = codigoActividadEconomica;
    }

    public String getTipoAutorizacion() {
        return tipoAutorizacion;
    }

    public void setTipoAutorizacion(String tipoAutorizacion) {
        this.tipoAutorizacion = tipoAutorizacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public BigInteger getNitEmisor() {
        return nitEmisor;
    }

    public void setNitEmisor(BigInteger nitEmisor) {
        this.nitEmisor = nitEmisor;
    }

    public Long getParalela() {
        return paralela;
    }

    public void setParalela(Long paralela) {
        this.paralela = paralela;
    }

    public Long getSucursal() {
        return sucursal;
    }

    public void setSucursal(Long sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public String toString() {
        return "InicioCicloTO{" + "nit=" + nit + ", codigoCaracteristica=" + codigoCaracteristica + ", codigoActividadEconomica=" + codigoActividadEconomica + ", tipoAutorizacion=" + tipoAutorizacion + ", usuario=" + usuario + ", nitEmisor=" + nitEmisor + ", paralela=" + paralela + ", sucursal=" + sucursal + '}';
    }
}
