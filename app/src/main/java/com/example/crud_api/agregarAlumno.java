package com.example.crud_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crud_api.Entidad.Alumno;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class agregarAlumno extends AppCompatActivity {
    private EditText edtnombre,edtapellido,edtcorreo,edtedad,edtdireccion;
    private Button btnagregarAlumno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alumno);
        edtnombre = findViewById(R.id.edtnombre);
        edtapellido = findViewById(R.id.edtapellidos);
        edtcorreo = findViewById(R.id.edtcorreo);
        edtedad = findViewById(R.id.edtedad);
        edtdireccion = findViewById(R.id.edtdireccion);
        btnagregarAlumno =  findViewById(R.id.btnagregarAlumno);

        btnagregarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = edtnombre.getText().toString().trim();
                String apellido = edtapellido.getText().toString().trim();
                String correo = edtcorreo.getText().toString().trim();
                String edad = edtedad.getText().toString().trim();
                String direccion = edtdireccion.getText().toString().trim();


                if (nombre.isEmpty()){
                    Toast.makeText(agregarAlumno.this, "Rellenar el campo nombre", Toast.LENGTH_SHORT).show();
                } else if (apellido.isEmpty()) {
                    Toast.makeText(agregarAlumno.this, "Rellenar el campo apellido", Toast.LENGTH_SHORT).show();
                } else if (correo.isEmpty()) {
                    Toast.makeText(agregarAlumno.this, "Rellenar el campo correo", Toast.LENGTH_SHORT).show();
                } else if (edad.isEmpty()) {
                    Toast.makeText(agregarAlumno.this, "Rellenar el campo edad", Toast.LENGTH_SHORT).show();
                } else if (direccion.isEmpty()) {
                    Toast.makeText(agregarAlumno.this, "Rellenar el campo direccion", Toast.LENGTH_SHORT).show();
                }else {
                    Integer edadParseada = Integer.parseInt(edad);

                    Alumno alumno = new Alumno();
                    alumno.setNombre(nombre);
                    alumno.setApellido(apellido);
                    alumno.setCorreo(correo);
                    alumno.setEdad(edadParseada);
                    alumno.setDireccion(direccion);

                    RegistrarCliente(alumno);
                    Toast.makeText(agregarAlumno.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    edtnombre.setText("");
                    edtapellido.setText("");
                    edtcorreo.setText("");
                    edtedad.setText("");
                    edtdireccion.setText("");
                    Intent ventana = new Intent(getApplicationContext(), menu.class);
                    startActivity(ventana);
                }
            }
        });

    }
    public void RegistrarCliente(final Alumno alumno){
        String url = "http://10.0.2.2:7879/api/v1/alumnos";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }


            @Override
            public byte[] getBody() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("nombre", alumno.getNombre());
                    jsonObject.put("apellido", alumno.getApellido());
                    jsonObject.put("correo", alumno.getCorreo());
                    jsonObject.put("edad",alumno.getEdad());
                    jsonObject.put("direccion",alumno.getDireccion());

                    return jsonObject.toString().getBytes("utf-8");
                } catch (JSONException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
}