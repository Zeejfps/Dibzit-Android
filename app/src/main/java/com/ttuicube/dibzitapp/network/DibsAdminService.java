package com.ttuicube.dibzitapp.network;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.POST;

/**
 * Created by Zeejfps on 10/12/17.
 */

public interface DibsAdminService {

    @POST
    Call<Response> dibsRoom();

}
