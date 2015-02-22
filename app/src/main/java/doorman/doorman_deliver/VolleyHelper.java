package doorman.doorman_deliver;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ragar90 on 12/23/14.
 */
public class VolleyHelper {
    private static VolleyHelper INSTANCE;
    private RequestQueue requestQueue;
    private Context context;

    private VolleyHelper(Context context){
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleyHelper getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new VolleyHelper(context);
        }
        return INSTANCE;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static JsonArrayRequest getJsonArrayRequest(String url,Response.Listener<JSONArray> arrayListener){
        JsonArrayRequest request = new JsonArrayRequest(url,arrayListener,errorListener);
        return request;
    }

    public static JsonObjectRequest getJsonObjectRequest(int method, String url, JSONObject params, Response.Listener<JSONObject> listener){
        JsonObjectRequest request = new JsonObjectRequest(method,url,params,listener,errorListener);
        return request;
    }

    private static Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if(volleyError != null){
                String msg = volleyError.getMessage() == null ? "Some Error Happened" : volleyError.getMessage();
                Log.e("Volley Error", msg);
            }
        }
    };

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}