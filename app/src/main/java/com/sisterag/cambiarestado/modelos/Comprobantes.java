package com.sisterag.cambiarestado.modelos;

public class Comprobantes {
  private String idcomp;
  private String idcnte;
  private String fecha;
  private String sucursal, moneda;
  private String oficinao, oficinad;
  private String monto;
  private String envases;
  private String precintos;
  private String idusr;

  private String estatus;

  public Comprobantes(String idcomp, String fecha, String moneda, String oficinad, String monto, String sEstt, String idusr,String idcnt) {
    this.idcomp = idcomp;
    this.fecha = fecha;
    this.moneda = moneda;
    this.oficinad = oficinad;
    this.monto = monto;
    this.estatus = sEstt;
    this.idusr = idusr;
    this.idcnte = idcnt;
  }
  public Comprobantes(String idcomp, String sEstt, String idusr,String idcnt) {
    this.idcomp = idcomp;
    this.estatus = sEstt;
    this.idusr = idusr;
    this.idcnte = idcnte;
  }

  public String getEstatus() {
    return estatus;
  }

  public void setEstatus(String estatus) {
    this.estatus = estatus;
  }
  public String getIdcomp() {
    return this.idcomp;
  }

  public void setIdcomp(String idcomp) {
    this.idcomp = idcomp;
  }

  public String getIdcnte() {
    return idcnte;
  }

  public void setIdcnte(String idcnte) {
    this.idcnte = idcnte;
  }

  public String getFecha() {
    return this.fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
  }

  public String getSucursal() {
    return sucursal;
  }

  public void setSucursal(String sucursal) {
    this.sucursal = sucursal;
  }

  public String getMoneda() {
    return this.moneda;
  }

  public void setMoneda(String moneda) {
    this.moneda = moneda;
  }

  public String getOficinao() {
    return oficinao;
  }

  public void setOficinao(String oficinao) {
    this.oficinao = oficinao;
  }

  public String getOficinad() {
    return this.oficinad;
  }

  public void setOficinad(String oficinad) {
    this.oficinad = oficinad;
  }

  public String getMonto() {
    return this.monto;
  }

  public void setMonto(String monto) {
    this.monto = monto;
  }

  public String getEnvases() {
    return envases;
  }

  public void setEnvases(String envases) {
    this.envases = envases;
  }

  public String getPrecintos() {
    return precintos;
  }

  public void setPrecintos(String precintos) {
    this.precintos = precintos;
  }

  public String getIdusr() {
    return idusr;
  }

  public void setIdusr(String idusr) {
    this.idusr = idusr;
  }
}
