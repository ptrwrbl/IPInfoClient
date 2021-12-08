package pollub.pam.ipinfoclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String prepareAPIData() {
        EditText firstOctet = (EditText) findViewById(R.id.IP_firstOctet);
        EditText secondOctet = (EditText) findViewById(R.id.IP_secondOctet);
        EditText thirdOctet = (EditText) findViewById(R.id.IP_thirdOctet);
        EditText forthOctet = (EditText) findViewById(R.id.IP_forthOctet);

        String IPAddress = firstOctet.getText().toString() + "." + secondOctet.getText().toString() + "." +
                thirdOctet.getText().toString() + "." + forthOctet.getText().toString();

        Log.i("-->",IPAddress);

        return IPAddress;
    }

    public void handleAPIRequest(View view) {
        APIResponseHandler apiHandler = APIService.createService(APIResponseHandler.class, prepareAPIData());

        Call<APIResponseEntity> apiCall = apiHandler.getIPInfo();

        apiCall.enqueue(new Callback<APIResponseEntity>() {
            @Override
            public void onResponse(Call<APIResponseEntity> call, Response<APIResponseEntity> response) {
                printAPIResults(response.body());
            }

            @Override
            public void onFailure(Call<APIResponseEntity> call, Throwable t) {
                printAPIResults(null);
            }
        });
    }

    private void printAPIResults(APIResponseEntity info) {
        TextView textView = (TextView) findViewById(R.id.API_requestResult);

        String s;

        if(info == null) s = "Request failed";
        else {
            s = "ip: " + info.getIp() + "\n" +
                    "hostname: " + info.getHostname() + "\n" +
                    "city: " + info.getCity();
        }
        textView.setText(s);
    }
}