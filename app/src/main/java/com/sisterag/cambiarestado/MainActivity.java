package com.sisterag.cambiarestado;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.sisterag.cambiarestado.modelos.Comprobantes;
import com.sisterag.cambiarestado.modelos.Usuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Activity mActivity;
    TextInputEditText txtUser, txtPasw;
    Button btnBusc;

    public static ArrayList<Comprobantes> compArrLst = new ArrayList<>();
    String url = EndPoint.base_url+"inicia/log_in";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle(R.string.title_comp_p);

        txtUser = findViewById(R.id.txtUsuario);
        txtPasw = findViewById(R.id.txtClave);
        btnBusc = findViewById(R.id.btnInicia);

        btnBusc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IniciaSsn();
            }
        });
    }

    private void IniciaSsn() {
        this.mActivity = this;
        final String sUser = txtUser.getText().toString().trim();
        final String sPasw = txtPasw.getText().toString().trim();
        Usuarios oUser = new Usuarios();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Cargando");

        if (isValid(sUser,sPasw)) {
            pd.show();
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsnObj = new JSONObject(response);
                        String sOk = jsnObj.getString("exito");
                        JSONArray jsnArr = jsnObj.getJSONArray("datos");
                        if (sOk.equals("1")) {

                            JSONObject jObj = jsnArr.getJSONObject(0);
                            int sIdUsr = jObj.getInt("idusr");
                            String sIdCte = jObj.getString("cliente");
                            oUser.setIdusr(sIdUsr);
                            oUser.setCliente(sIdCte);

                            Intent iAct = new Intent(mActivity, ListarActivity.class);
                            iAct.putExtra(ListarActivity.sIdUser, Integer.toString(oUser.getIdusr()));
                            iAct.putExtra(ListarActivity.sIdClie, oUser.getCliente());

                            MuestraMsj("Datos Correctos");
                            pd.dismiss();
                            finish();
                            mActivity.startActivity(iAct);
                        } else {
                            MuestraMsj(response);
                            pd.dismiss();
                        }
                    } catch (JSONException e) {
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
                    params.put("sUser",sUser);
                    params.put("sPasw",sPasw);
                    return params;
                }
            };
            RequestQueue rqq = Volley.newRequestQueue(this);
            rqq.add(request);
        }else{
            MuestraMsj("Datos Incompletos");
            return;
        }
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
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }
}