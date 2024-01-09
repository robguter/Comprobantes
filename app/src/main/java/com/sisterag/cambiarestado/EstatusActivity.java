package com.sisterag.cambiarestado;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sisterag.cambiarestado.modelos.Comprobantes;
import com.sisterag.cambiarestado.modelos.Usuarios;
import com.sisterag.cambiarestado.util.Utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EstatusActivity extends AppCompatActivity {

    public static ArrayList<Comprobantes> compArrLst = new ArrayList<>();
    String url = EndPoint.base_url+"inicia/estatuscomprob";
    Comprobantes mComprob;
    Comprobantes mCmpob;
    public static final String sIdClie = "IdClie";
    public static final String sIdUser = "IdUser";
    public static final String sIdComp = "IdComp";
    public static final String sStatus = "Estatu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatus);

        String strIdUser = getIntent().getStringExtra("IdUser");
        String strIdClie = getIntent().getStringExtra("IdClie");
        String strIdComp = getIntent().getStringExtra("IdComp");
        String strEstatu = getIntent().getStringExtra("Estatu");

        mComprob = new Comprobantes(strIdComp, strEstatu, strIdUser, strIdClie);
        mComprob.setIdcnte(strIdClie);
        mComprob.setIdusr(strIdUser);
        mComprob.setIdcomp(strIdComp);
        mComprob.setEstatus(strEstatu);

        this.setTitle(R.string.title_comp_e);

        cambiaEtts();
    }

    private void cambiaEtts() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Cargando");

        pd.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //compArrLst.clear();
                try {
                    JSONObject jsnObj = new JSONObject(response);
                    String sOk = jsnObj.getString("exito");
                    JSONArray jsnArr = jsnObj.getJSONArray("datos");
                    if (sOk.equals("1")) {

                        JSONObject jObj = jsnArr.getJSONObject(0);

                        Utiles util = new Utiles();
                        String sIdCm = util.FormaNumCeros(String.valueOf(jObj.getInt("idcomp")));
                        String sIdCt = jObj.getString("idcnte");
                        String sFech = util.formaFecha(jObj.getString("fecha"));
                        String sMone = util.CMayus(jObj.getString("moneda"));
                        String sOfic = util.CMayus(jObj.getString("oficinad"));
                        String sMont = util.FormaNum(jObj.getString("monto"));
                        String sStts = jObj.getString("estatus");
                        String sIdUs = jObj.getString("idusr");;

                        TextView tvIdCm = findViewById(R.id.tvidcomp);
                        TextView tvFech = findViewById(R.id.tvfecha);
                        TextView tvMone = findViewById(R.id.tvmoned);
                        TextView tvOfic = findViewById(R.id.tvoficd);
                        TextView tvMont = findViewById(R.id.tvmonto);
                        TextView tvEstt = findViewById(R.id.tvestatus);

                        tvIdCm.setText(sIdCm);
                        tvFech.setText(sFech);
                        tvMone.setText(sMone);
                        tvOfic.setText(sOfic);
                        tvMont.setText(sMont);
                        tvEstt.setText(sStts);

                        pd.dismiss();
                        //finish();
                    }else{
                        MuestraMsj(response);
                        pd.dismiss();
                    }
                } catch (JSONException e) {
                    Log.d("EL ERROR: ",response);
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MuestraMsj(error.getMessage());
                pd.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Log.d("CLIENTE",mComprob.getIdcnte());
                params.put("IdClie",mComprob.getIdcnte());
                params.put("IdUser", mComprob.getIdusr());
                params.put("IdComp",mComprob.getIdcomp());
                params.put("Estatu", mComprob.getEstatus());
                return params;
            }
        };
        RequestQueue rqq = Volley.newRequestQueue(this);
        rqq.add(request);
    }
    private void MuestraMsj(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }
}