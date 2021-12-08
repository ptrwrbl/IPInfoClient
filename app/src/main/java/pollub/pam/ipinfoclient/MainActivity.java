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


    public void sendButtonPressed(View view) {
        if(validateRequestData()) {
            handleAPIRequest();
        }
    }

    private boolean validateRequestData() {
        EditText firstOctet = (EditText) findViewById(R.id.IP_firstOctet);
        EditText secondOctet = (EditText) findViewById(R.id.IP_secondOctet);
        EditText thirdOctet = (EditText) findViewById(R.id.IP_thirdOctet);
        EditText forthOctet = (EditText) findViewById(R.id.IP_forthOctet);

        /* Checking if octets are empty */
        if (firstOctet.length() == 0) {
            firstOctet.setError("Input cannot be null");
            return false;
        }
        if (secondOctet.length() == 0) {
            secondOctet.setError("Input cannot be null");
            return false;
        }
        if (thirdOctet.length() == 0) {
            thirdOctet.setError("Input cannot be null");
            return false;
        }
        if (forthOctet.length() == 0) {
            forthOctet.setError("Input cannot be null");
            return false;
        }

        /* Checking if octets are between 0-255 */
        int firstOctetValue = Integer.parseInt(firstOctet.getText().toString());
        int secondOctetValue = Integer.parseInt(secondOctet.getText().toString());
        int thirdOctetValue = Integer.parseInt(thirdOctet.getText().toString());
        int forthOctetValue = Integer.parseInt(forthOctet.getText().toString());

        if (firstOctetValue < 0 || firstOctetValue > 255) {
            firstOctet.setError("Not valid ip number");
            return false;
        }

        if (secondOctetValue < 0 || secondOctetValue > 255) {
            secondOctet.setError("Not valid ip number");
            return false;
        }
        if (thirdOctetValue < 0 || thirdOctetValue > 255) {
            thirdOctet.setError("Not valid ip number");
            return false;
        }
        if (forthOctetValue < 0 || forthOctetValue > 255) {
            forthOctet.setError("Not valid ip number");
            return false;
        }

        return true;

    }

    private String prepareRequestData() {
        EditText firstOctet = (EditText) findViewById(R.id.IP_firstOctet);
        EditText secondOctet = (EditText) findViewById(R.id.IP_secondOctet);
        EditText thirdOctet = (EditText) findViewById(R.id.IP_thirdOctet);
        EditText forthOctet = (EditText) findViewById(R.id.IP_forthOctet);

        String IPAddress = firstOctet.getText().toString() + "." + secondOctet.getText().toString() + "." +
                thirdOctet.getText().toString() + "." + forthOctet.getText().toString();

        Log.i("-->",IPAddress);

        return IPAddress;
    }
    public void handleAPIRequest() {
        APIResponseHandler apiHandler = APIService.createService(APIResponseHandler.class, prepareRequestData());

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

    private void printAPIResults(APIResponseEntity apiResponse) {
        TextView resultField = (TextView) findViewById(R.id.API_requestResult);

        String formattedResult;

        if(apiResponse == null) formattedResult = "Request failed";
        else {
            formattedResult = "ip: "          + apiResponse.getIp()          + ",\n" +
                "hostname: "    + apiResponse.getHostname()    + ",\n" +
                "city: "        + apiResponse.getCity()        + ",\n" +
                "region: "      + apiResponse.getRegion()      + ",\n" +
                "country: "     + apiResponse.getCountry()     + ",\n" +
                "loc: "         + apiResponse.getLoc()         + ",\n" +
                "org: "         + apiResponse.getOrg()         + ",\n" +
                "postal: "      + apiResponse.getPostal()      + ",\n" +
                "timezone: "    + apiResponse.getTimezone()    + ",\n" +
                "readme: "      + apiResponse.getReadme();
        }
        resultField.setText(formattedResult);
    }
}