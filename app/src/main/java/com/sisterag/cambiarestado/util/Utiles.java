package com.sisterag.cambiarestado.util;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utiles {

    public String formaFecha(String sFecha) {
        String[] dFecha = sFecha.split("-");
        String Fecha = dFecha[2]+"/"+dFecha[1]+"/"+dFecha[0];
        return Fecha;
    }
    public String CMayus(String sCad) {
        String[] cadena = sCad.split(" ");
        String sCadena="";
        for (int i=0; i<cadena.length; i++) {
            String Palabra = cadena[i];
            String Letra = Palabra.substring(0,1);
            String Letras = Palabra.substring(1);
            sCadena += Letra.toUpperCase() + Letras.toLowerCase() + " ";
        }
        return sCadena.trim();
    }

    public String FormaNum(String sCad) {
        DecimalFormat formato = new DecimalFormat("#,###.00");
        Double sMont = Double.valueOf(sCad);
        String sMnto = formato.format(sMont);
        return sMnto;
    }
    public String FormaNumCeros(String sCad) {
        DecimalFormat formato = new DecimalFormat("00000000");
        Double sMont = Double.valueOf(sCad);
        String sMnto = formato.format(sMont);
        return sMnto;
    }
}
