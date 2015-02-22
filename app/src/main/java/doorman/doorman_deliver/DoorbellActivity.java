package doorman.doorman_deliver;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DoorbellActivity extends ActionBarActivity {
    EditText skuTxt;
    Button doorbellBtn;
    ProgressDialog pd;
    View.OnClickListener doorbellListener = new View.OnClickListener(){

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            pd = ProgressDialog.show(DoorbellActivity.this, "Please wait...", "Tacking picture from door...");
            String skuNumber = skuTxt.getText().toString();
            String url = "http://doorman.azurewebsites.net/pkg/" + skuNumber;
            JsonObjectRequest getRequest = VolleyHelper.getJsonObjectRequest(Request.Method.POST,url,null,doorbellCallback);
            VolleyHelper.getInstance(DoorbellActivity.this).addToRequestQueue(getRequest);
        }
    };
    Response.Listener<JSONObject> doorbellCallback = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response) {
            try {
                String callUrl = response.getString("call_url");
                Log.d("WEB RCT URL", callUrl);
                pd.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(callUrl));
                intent.setPackage("com.android.chrome");
                startActivity(intent);
            } catch (Exception e) {
                Log.d("WEB RCT URL", "Something fail...");
                e.printStackTrace();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doorbell);
        skuTxt = (EditText) findViewById(R.id.skuTxt);
        doorbellBtn = (Button) findViewById(R.id.doorbellBtn);

        doorbellBtn.setOnClickListener(doorbellListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doorbell, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
