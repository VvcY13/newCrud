package com.example.crud_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crud_api.Entidad.Alumno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class menu extends AppCompatActivity {
    private EditText id,nombre,apellido,correo,edad,direccion;
    private Button btnagregar,btnbuscarporId,btneditar,btneliminar;
    private ListView lista;
    private List<Alumno> listaAlumnos;
    private ArrayAdapter<Alumno> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //asignamos--------sigan viendo !!!!!
        lista = findViewById(R.id.lstlista);
        btnagregar = findViewById(R.id.btnAgregar);
        btnbuscarporId = findViewById(R.id.btnBuscarPorId);
        btneditar = findViewById(R.id.btnEditar);
        btneliminar = findViewById(R.id.btnEliminar);
        id = findViewById(R.id.edtid);
        nombre = findViewById(R.id.edtnombreCli);
        apellido = findViewById(R.id.edtapellidoCli);
        correo = findViewById(R.id.edtcorreoCli);
        edad = findViewById(R.id.edtedad);
        direccion = findViewById(R.id.edtdireccion);

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = id.getText().toString().trim();
                if (ID.isEmpty()) {
                    Toast.makeText(menu.this, "Ingresar el campo Id", Toast.LENGTH_SHORT).show();
                }else {
                    int identificador = Integer.parseInt(ID);
                    EliminarAlumno(identificador);

                    id.setText("");
                    nombre.setText("");
                    apellido.setText("");
                    correo.setText("");
                    edad.setText("");
                    direccion.setText("");
                }
            }
        });

        btneditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = id.getText().toString().trim();
                String Nombre = nombre.getText().toString().trim() ;
                String Apellido = apellido.getText().toString().trim();
                String Correo = correo.getText().toString().trim();
                String Edad = edad.getText().toString().trim();
                String Direccion = direccion.getText().toString().trim();
                if (ID.isEmpty()){
                    Toast.makeText(menu.this, "Ingresar el campo Id", Toast.LENGTH_SHORT).show();
                } else if (Nombre.isEmpty()) {
                    Toast.makeText(menu.this, "Ingresar el campo Nombre", Toast.LENGTH_SHORT).show();
                } else if (Apellido.isEmpty()) {
                    Toast.makeText(menu.this, "Ingresar el campo Apellido", Toast.LENGTH_SHORT).show();
                } else if (Correo.isEmpty()) {
                    Toast.makeText(menu.this, "Ingresar el campo Correo", Toast.LENGTH_SHORT).show();
                } else if (Edad.isEmpty()) {
                    Toast.makeText(menu.this, "Ingresar el campo Edad", Toast.LENGTH_SHORT).show();
                } else if (Direccion.isEmpty()) {
                    Toast.makeText(menu.this, "Ingresar el campo Direccion", Toast.LENGTH_SHORT).show();
                }else {
                    int identificador = Integer.parseInt(ID);
                    int edadParseada = Integer.parseInt(Edad);
                    editarPorId(identificador,Nombre,Apellido,Correo,edadParseada,Direccion);
                }
            }
        });

        btnbuscarporId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = id.getText().toString().trim();
                if (ID.isEmpty()){
                    Toast.makeText(menu.this, "Ingresar el campo Id", Toast.LENGTH_SHORT).show();
                }else {
                    int identificador = Integer.parseInt(ID);
                    porId(identificador);
                }
            }
        });

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ventana = new Intent(getApplicationContext(), agregarAlumno.class);
                startActivity(ventana);
            }
        });

        listaAlumnos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaAlumnos);
        lista.setAdapter(adapter);
        leerTodos();
    }
    private void leerTodos(){
        String url = "http://10.0.2.2:7879/api/v1/alumnos";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Respuesta API", response);
                try {
                    JSONArray alumnosArray = new JSONArray(response);

                    for (int i = 0;i<alumnosArray.length();i++){
                        JSONObject alumnosObject = alumnosArray.getJSONObject(i);
                        Alumno alumno = new Alumno();
                        alumno.setIdAlumno(alumnosObject.getInt("estudianteId"));
                        alumno.setNombre(alumnosObject.getString("nombre"));
                        alumno.setApellido(alumnosObject.getString("apellido"));
                        alumno.setCorreo(alumnosObject.getString("correo"));
                        alumno.setDireccion(alumnosObject.getString("direccion"));
                        alumno.setEdad(alumnosObject.getInt("edad"));
                        listaAlumnos.add(alumno);
                    }
                    adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postRequest);
    }
    public void porId(final int id){
        String url = "http://10.0.2.2:7879/api/v1/alumnos/"+id;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta API", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    int IdEstudiante = jsonResponse.getInt("estudianteId");
                    String Nombre = jsonResponse.getString("nombre");
                    String Apellido = jsonResponse.getString("apellido");
                    String Correo = jsonResponse.getString("correo");
                    int Edad = jsonResponse.getInt("edad");
                    String Direccion = jsonResponse.getString("direccion");

                    nombre.setText(Nombre);
                    apellido.setText(Apellido);
                    correo.setText(Correo);
                    edad.setText(String.valueOf(Edad));
                    direccion.setText(Direccion);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postRequest);
    }
    public void editarPorId(final int id, final String nombre, final String apellido, final String correo, final int edad, final String direccion){
        String url = "http://10.0.2.2:7879/api/v1/alumnos/"+id;
        StringRequest postRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response != null) {
                        Log.d("Respuesta API", response);
                        Toast.makeText(getApplicationContext(), "Alumno actualizado correctamente", Toast.LENGTH_SHORT).show();
                    boolean alumnoEncontrado = false;
                    for (int i = 0; i < listaAlumnos.size(); i++) {
                    if (listaAlumnos.get(i).getIdAlumno() == id) {
                        listaAlumnos.get(i).setNombre(nombre);
                        listaAlumnos.get(i).setApellido(apellido);
                        listaAlumnos.get(i).setCorreo(correo);
                        listaAlumnos.get(i).setEdad(edad);
                        listaAlumnos.get(i).setDireccion(direccion);
                        alumnoEncontrado = true;
                        break;
                    }
                }

                if (!alumnoEncontrado) {
                    Alumno alumnoActualizado = new Alumno();
                    alumnoActualizado.setIdAlumno(id);
                    alumnoActualizado.setNombre(nombre);
                    alumnoActualizado.setApellido(apellido);
                    alumnoActualizado.setCorreo(correo);
                    alumnoActualizado.setEdad(edad);
                    alumnoActualizado.setDireccion(direccion);
                    listaAlumnos.add(alumnoActualizado);
                }
                adapter.notifyDataSetChanged();
                    } else {
                        Log.e("Respuesta API", "La respuesta del servidor es nula");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
                Toast.makeText(getApplicationContext(), "Error al actualizar el alumno", Toast.LENGTH_SHORT).show();
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
                    jsonObject.put("estudianteId",id);
                    jsonObject.put("nombre", nombre );
                    jsonObject.put("apellido", apellido);
                    jsonObject.put("correo", correo);
                    jsonObject.put("edad",edad);
                    jsonObject.put("direccion",direccion);

                    return jsonObject.toString().getBytes("utf-8");
                } catch (JSONException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
        adapter.notifyDataSetChanged();
    }
    public void EliminarAlumno(final int idAlumno){
        String url = "http://10.0.2.2:7879/api/v1/alumnos/"+idAlumno;
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                for (int i = 0; i < listaAlumnos.size(); i++) {
                    if (listaAlumnos.get(i).getIdAlumno() == idAlumno) {
                        listaAlumnos.remove(i);
                        break;
                    }
                }
                Toast.makeText(menu.this, "Alumno eliminado correctamente.", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null && error.getMessage() != null) {
                    Log.e("error", error.getMessage());
                } else {
                    Log.e("error", "Error desconocido al eliminar el alumno.");
                }
                Toast.makeText(menu.this, "Error al eliminar el alumno.", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(deleteRequest);
        adapter.notifyDataSetChanged();
    }
}