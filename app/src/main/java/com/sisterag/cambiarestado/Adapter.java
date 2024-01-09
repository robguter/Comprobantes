package com.sisterag.cambiarestado;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sisterag.cambiarestado.modelos.Comprobantes;
import com.sisterag.cambiarestado.util.Utiles;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter extends ArrayAdapter<Comprobantes> {

    Context contxt;
    List<Comprobantes> arrComp;
    public Adapter(@NonNull Context context, List<Comprobantes> arrCmp) {
        super(context, R.layout.lst_comprobantes, arrCmp);
        this.contxt = context;
        this.arrComp = arrCmp;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.contxt = parent.getContext();
        View view = LayoutInflater.from(contxt).inflate(R.layout.lst_comprobantes, null, true);

        TextView tvIdCm = view.findViewById(R.id.tvidcomp);
        TextView tvFech = view.findViewById(R.id.tvfecha);
        TextView tvMone = view.findViewById(R.id.tvmoned);
        TextView tvOfic = view.findViewById(R.id.tvoficd);
        TextView tvMont = view.findViewById(R.id.tvmonto);
        TextView tvEstt = view.findViewById(R.id.tvestatus);
        TextView tvIdCt = view.findViewById(R.id.tvidcte);
        TextView tvIdUs = view.findViewById(R.id.tvidusr);

        Utiles util = new Utiles();

        String sIdCm = util.FormaNumCeros(arrComp.get(position).getIdcomp());
        String sIdCt = arrComp.get(position).getIdcnte();
        String sIdUs = arrComp.get(position).getIdusr();

        String ff = util.formaFecha(arrComp.get(position).getFecha());
        String mn = util.CMayus(arrComp.get(position).getMoneda());
        String od = util.CMayus(arrComp.get(position).getOficinad());

        String sMnto = util.FormaNum(arrComp.get(position).getMonto());
        String sStts = arrComp.get(position).getEstatus();
        String sEstt = (sStts.equals("Procesado")) ? "P" : (sStts.equals("Enviado")) ? "E" : (sStts.equals("Recivido")) ? "R" : "";

        tvIdCm.setText(sIdCm);
        tvFech.setText(ff);
        tvMone.setText(mn);
        tvOfic.setText(od);
        tvMont.setText(sMnto);
        tvEstt.setText(sEstt);
        tvIdCt.setText(sIdCt);
        tvIdUs.setText(sIdUs);

        return view;
    }
}
