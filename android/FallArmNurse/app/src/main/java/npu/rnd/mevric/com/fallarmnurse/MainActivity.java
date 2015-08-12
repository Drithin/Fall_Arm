package npu.rnd.mevric.com.fallarmnurse;

import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    WebSocketClient mWebSocketClient;
    //String WS_URL = "ws://52.26.176.189:8081/receive";
    String WS_URL = "ws://172.19.2.178:8081/receive";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.imageView);
        displayRegularImage();
        connectWebSocket1();
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

    public void displayRegularImage(){
        iv.setImageResource(R.drawable.t2);
    }
    public void displayAlertImage(){
        iv.setImageResource(R.drawable.t4);
    }
    public class WebsocketAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return "done";
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), "Send message "+_code, Toast.LENGTH_LONG).show();
        }
    }
    private void connectWebSocket1() {
        URI uri;
        try {
            uri = new URI(WS_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        //mWebSocketClient = new WebSocketClient(uri) {
        mWebSocketClient = new WebSocketClient(uri, new Draft_17()){
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean flag = shouldDisplayAlert(message);
                        if(flag){
                            displayAlertImage();
                        } else {
                            displayRegularImage();
                        }
                        TextView textView = (TextView)findViewById(R.id.textView);
                        textView.setText(textView.getText() + "\n" + message);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("::: Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.i("::: Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }
    public boolean shouldDisplayAlert(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String device_id = jsonObject.getString("device_id");
            String accelerator_x = jsonObject.getString("accelerator_x");
            String accelerator_y = jsonObject.getString("accelerator_y");
            String accelerator_z = jsonObject.getString("accelerator_z");
            if(accelerator_x.equals("1") && accelerator_y.equals("1") && accelerator_z.equals("1")){
                return  true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
