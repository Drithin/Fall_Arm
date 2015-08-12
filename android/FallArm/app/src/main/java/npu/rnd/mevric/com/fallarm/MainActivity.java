package npu.rnd.mevric.com.fallarm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONStringer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button regularPulseButton;
    ProgressDialog prgDialog;
    //String url = "http://54.69.184.6:8080/controller/register";
    String url = "http://52.26.176.189:8080/controller/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void addListenerOnButton() {

        button = (Button) findViewById(R.id.fallButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new HttpAsyncTask(getFallRequest()).execute("");
            }
        });

        regularPulseButton = (Button) findViewById(R.id.RegularPulseButton);
        regularPulseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new HttpAsyncTask(getRegularPulseRequest()).execute("");
            }
        });
    }
    public class HttpAsyncTask extends AsyncTask<String, Void, String> {
        int _code = -1;
        String payload;
        public HttpAsyncTask(String payload){
            this.payload = payload;
        }
        @Override
        protected String doInBackground(String... urls) {
            _code = POST(payload);
            return "done";
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Send message "+_code, Toast.LENGTH_LONG).show();
            //etResponse.setText(result);
        }
    }
    public  int POST(String json){
        int code = -1;

        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make POST request to the given URL
            HttpPost hp = new HttpPost(url);
            HttpEntity he = new StringEntity(json);
            hp.setEntity(he);
            HttpResponse httpResponse = httpclient.execute(hp);
            code = httpResponse.getStatusLine().getStatusCode();
            System.out.println(">>>>>> CODE "+code);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return code;
    }
    private String getFallRequest() {
        String v = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateandTime = sdf.format(new Date());

            JSONStringer jj=new JSONStringer();
            v = jj.object().key("device_id").value(getTelephone())
            .key("accelerator_x").value("1")
            .key("accelerator_y").value("1")
            .key("accelerator_z").value("1")
            .key("gyroscope_x").value(getV())
            .key("gyroscope_y").value(getV())
            .key("gyroscope_z").value(getV())
            .key("datetime").value(currentDateandTime)
                    .endObject()
                    .toString();

        } catch (Exception e ) {
            e.printStackTrace();;
        }
        System.out.println("JSON REQUEST : "+v);
        return v;
    }
    private String getRegularPulseRequest() {
        String v = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateandTime = sdf.format(new Date());

            JSONStringer jj=new JSONStringer();
            v = jj.object().key("device_id").value(getTelephone())
                    .key("accelerator_x").value(getV())
                    .key("accelerator_y").value(getV())
                    .key("accelerator_z").value(getV())
                    .key("gyroscope_x").value(getV())
                    .key("gyroscope_y").value(getV())
                    .key("gyroscope_z").value(getV())
                    .key("datetime").value(currentDateandTime)
                    .endObject()
                    .toString();

        } catch (Exception e ) {
            e.printStackTrace();;
        }
        System.out.println("JSON REQUEST : "+v);
        return v;
    }
    private String getTelephone(){
        TelephonyManager tMgr = (TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        return mPhoneNumber;
    }
    public String getV(){
        int i = ((int)(Math.random() * 100)) % 8;
        i = i == 0 ? 1 : i;
        return ""+i;
    }
}
