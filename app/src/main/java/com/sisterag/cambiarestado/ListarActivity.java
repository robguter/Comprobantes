package com.sisterag.cambiarestado;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ListarActivity extends AppCompatActivity {

    private Activity mActivity;
    ListView lstView;
    Adapter adaptr;
    public static final String sIdUser = "IdUser";
    public static final String sIdClie = "IdClie";

    public static ArrayList<Comprobantes> compArrLst = new ArrayList<>();
    String url = EndPoint.base_url+"inicia/cargacomprob";
    Comprobantes mComprob;
    Usuarios mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.mActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        this.setTitle(R.string.title_comp_l);

        String strIdUser = getIntent().getStringExtra("IdUser");
        String strIdClie = getIntent().getStringExtra("IdClie");

        mUsers = new Usuarios();
        mUsers.setCliente(strIdClie);
        mUsers.setIdusr(Integer.parseInt(strIdUser));

        this.setTitle(R.string.title_comp);
        lstView = findViewById(R.id.list_mostrar);
        adaptr = new Adapter(this,compArrLst);
        lstView.setAdapter(adaptr);

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView vIdUsr=(TextView) view.findViewById(R.id.tvidusr);
                TextView vIdCte=(TextView) view.findViewById(R.id.tvidcte);
                TextView vIdCmp=(TextView) view.findViewById(R.id.tvidcomp);
                TextView vEstat=(TextView) view.findViewById(R.id.tvestatus);

                String sIdUsr = vIdUsr.getText().toString();
                String sIdCte = vIdCte.getText().toString();
                String sIdCmp = vIdCmp.getText().toString();
                String sEstt = vEstat.getText().toString();
                String sEstat = (sEstt.equals("P")) ? "Procesado" : (sEstt.equals("E")) ? "Enviado" : "Recibido";
/*

                mComprob = new Comprobantes(sIdCmp, sEstat, sIdUsr, sIdCte);
                mComprob.setIdcnte(sIdCte);
                mComprob.setIdusr(sIdUsr);
                mComprob.setIdcomp(sIdCmp);
                mComprob.setEstatus(sEstat);
*/
                //Log.d("FUNCIONA",sIdCte+" NO");
                Intent iAct = new Intent(mActivity, EstatusActivity.class);
                iAct.putExtra(EstatusActivity.sIdUser, sIdUsr);
                iAct.putExtra(EstatusActivity.sIdClie, sIdCte);
                iAct.putExtra(EstatusActivity.sIdComp, sIdCmp);
                iAct.putExtra(EstatusActivity.sStatus, sEstat);

                MuestraMsj("Datos Correctos");
                mActivity.startActivity(iAct);
                finish();
            }
        });
        listarDts();
    }

    private void listarDts() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Cargando");

        pd.show();
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    compArrLst.clear();
                    try {
                        //JSONObject jsnObj = new JSONObject(response);
                        //String sOk = jsnObj.getString("exito");
                        JSONArray jsnArr = new JSONArray(response);
                        if (jsnArr.length()>0) {
                            for (int i = 0; i < jsnArr.length(); i++) {
                                JSONObject jObj = jsnArr.getJSONObject(i);
                                String sIdCm = String.valueOf(jObj.getInt("idcomp"));
                                String sFech = jObj.getString("fecha");
                                String sMone = jObj.getString("moneda");
                                String sOfic = jObj.getString("oficinad");
                                String sMont = jObj.getString("monto");
                                String sStts = jObj.getString("estatus");
                                String sIdUs = jObj.getString("idusr");
                                String sIdCt = jObj.getString("idcnte");
                                //Log.d("OBJECLIE",sIdCt+" NO HAY");

                                mComprob = new Comprobantes(sIdCm, sFech, sMone, sOfic, sMont, sStts, sIdUs, sIdCt);

                                //Log.d("tag:", sIdCm);
                                compArrLst.add(mComprob);
                                adaptr.notifyDataSetChanged();
                            }
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
                    params.put("IdClie",mUsers.getCliente());
                    params.put("IdUser", String.valueOf(mUsers.getIdusr()));
                    return params;
                }
            };
        RequestQueue rqq = Volley.newRequestQueue(this);
        rqq.add(request);

    }
    /*public void onBackPressed() {

        super.onBackPressed();
        finish();
    }*/
    private boolean isValid(String sUsua, String sPasw) {
        if(sUsua.isEmpty() || sPasw.isEmpty()) {
            MuestraMsj("Datos incompletos");
            return false;
        }
        return true;
    }
    private void MuestraMsj(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }
}