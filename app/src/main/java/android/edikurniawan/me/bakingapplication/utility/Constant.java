package android.edikurniawan.me.bakingapplication.utility;

/**
 * Created by Edi Kurniawan on 9/18/2017.
 */

public class Constant {

    // Step 1: private static variable of INSTANCE variable
    private static volatile Constant INSTANCE;

    // Step 2: private constructor
    private Constant() {

    }

    // Step 3: Provide public static getInstance() method returning INSTANCE after checking
    public static Constant getInstance() {

        // double-checking lock
        if(null == INSTANCE){

            // synchronized block
            synchronized (Constant.class) {
                if(null == INSTANCE){
                    INSTANCE = new Constant();
                }
            }
        }
        return INSTANCE;
    }


    public final int TYPE_INGREDIENT = 1;
    public final int TYPE_STEPS = 2;
    public final int TYPE_TITLE = 3;




}

