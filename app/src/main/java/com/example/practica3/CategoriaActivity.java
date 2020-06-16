package com.example.practica3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Base64;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.practica3.adapters.ProductAdapter;
import com.example.practica3.modelsProducts.Category;
import com.example.practica3.modelsProducts.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriaActivity extends AppCompatActivity
{
    RecyclerView productList;
    Gson gson = new GsonBuilder().create();
    String url = "https://dev-topicoswebtecstore.pantheonsite.io/wp-json/wc/v3/products";
    RecyclerView.LayoutManager layoutManager;
    List<Product> products = new ArrayList<Product>();
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        productList = findViewById(R.id.productos);
        queue = Volley.newRequestQueue(this);
        layoutManager = new LinearLayoutManager(this);
        productList.setLayoutManager(layoutManager);
        cargarProductos(this.getIntent().getIntExtra("id",-1));
    }

    private void cargarProductos(final int idCategoria){
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            if (response.length() > 0) {
                                products.clear();
                                List<Product> productsTemp = Arrays.asList(gson.fromJson(response.toString(),Product[].class));
                                List<Category> categories;
                                for (int i = 0; i < productsTemp.size() ; i++) {
                                    categories = productsTemp.get(i).getCategories();
                                    for (int h = 0; h < categories.size() ; h++) {
                                        if (categories.get(h).getId() == idCategoria){
                                            products.add(productsTemp.get(i));
                                        }
                                    }
                                }
                                productList.setAdapter(new ProductAdapter(CategoriaActivity.this, products));
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
                //add params <key,value>
                return params;
            }
            String USERNAME = "ck_6677f9cd6a4b4f28bbf819209af593f4a1ffe05c";
            String PASSWORD = "cs_833671edd401f4859b9427ddf6247470da54a35b";
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                //String creds = String.format("%s:%s","ck_f9592549c99e96604426ad6e030f0181a2ee6dff","cs_00324035b0788ce82497102deb2ee85bc98ac908");
                String creds = USERNAME+":"+PASSWORD;
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                return params;
            }
        };
        queue.add(arrayRequest);
    }
}