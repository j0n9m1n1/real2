package com.example.entitys.real.cloud_messaing;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + token);

        // 생성등록된 토큰을 개인 앱서버에 보내 저장해 두었다가 추가 뭔가를 하고 싶으면 할 수 있도록 한다.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.



    }
}
//TODO : notification_foreground(_nf), Bundle[{firebase_event_origin(_o)=fcm, firebase_screen_class(_sc)=ReportActivity, firebase_screen_id(_si)=5472043074165533764}] notification_receive(_nr), Bundle[{firebase_event_origin(_o)=fcm}]

