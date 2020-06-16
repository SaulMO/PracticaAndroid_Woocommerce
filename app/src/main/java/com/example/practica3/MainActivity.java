package com.example.practica3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.practica3.adapters.CategoryAdapter;
import com.example.practica3.modelsCategory.Category;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryListener
{
    RecyclerView categoryList;
    Gson gson = new GsonBuilder().create();
    String url = "https://dev-topicoswebtecstore.pantheonsite.io/wp-json/wc/v3/products/categories";
    RecyclerView.LayoutManager layoutManager;
    List<Category> categories = new ArrayList<Category>();
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryList = findViewById(R.id.categoryList);
        queue = Volley.newRequestQueue(this);
        layoutManager = new LinearLayoutManager(this);
        categoryList.setLayoutManager(layoutManager);
        cargarCategorias();
    }

    private void cargarCategorias(){
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            if (response.length() > 0) {
                                categories = Arrays.asList(gson.fromJson(response.toString(), Category[].class));
                                categoryList.setAdapter(new CategoryAdapter(MainActivity.this,categories,MainActivity.this));
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
            String USERNAME = "ck_6677f9cd6a4b4f28bbf819209af593f4a1ffe05c";
            String PASSWORD = "cs_833671edd401f4859b9427ddf6247470da54a35b";
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = USERNAME+":"+PASSWORD;
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                return params;
            }
        };
        queue.add(arrayRequest);
    }

    @Override
    public void onCategoryClick(int position) {
        Log.d("TAG","onCategoryClick: clicked "+categories.get(position).getName());
        Intent intent = new Intent(this, CategoriaActivity.class);
        intent.putExtra("id",categories.get(position).getId());
        startActivity(intent);
    }
}