package pollub.pam.ipinfoclient;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIResponseHandler {
    @GET("geo")
    Call<APIResponseEntity> getIPInfo();
}