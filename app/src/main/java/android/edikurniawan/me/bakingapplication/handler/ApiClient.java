package android.edikurniawan.me.bakingapplication.handler;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;

/**
 * Created by Edi Kurniawan on 9/15/2017.
 */

public class ApiClient {

    private static ApiClient mInstance;
    // Step 1: private static variable of INSTANCE variable
    private static volatile ApiClient INSTANCE;

    // Step 2: private constructor
    private ApiClient() {
    }

    // Step 3: Provide public static getInstance() method returning INSTANCE after checking
    public static ApiClient getInstance() {

        // double-checking lock
        if(null == INSTANCE){

            // synchronized block
            synchronized (ApiClient.class) {
                if(null == INSTANCE){
                    INSTANCE = new ApiClient();
                }
            }
        }
        return INSTANCE;
    }


    private final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public ANRequest getListRecipe(){
        return AndroidNetworking.get(BASE_URL)
                .setPriority(Priority.HIGH)
                .build();

    }
}
