package assessor.android.com.dummyassessorapp.SignIn;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import assessor.android.com.dummyassessorapp.AsssessorAttendance.Welcome_page;
import assessor.android.com.dummyassessorapp.GlobalAccess.SessionManager;
import assessor.android.com.dummyassessorapp.R;

public class SplashScreen extends AppCompatActivity {
SessionManager session;
    String[] permission ={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
    TextView ttv;
    int perm,perm1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
       // goNext();
        perm = ContextCompat.checkSelfPermission(this,  Manifest.permission.ACCESS_FINE_LOCATION);
        perm1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (perm != PackageManager.PERMISSION_GRANTED || perm1
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(permission, 7882);
        } else {
            goNext();

        }
        }
    private void goNext() {
        session = new SessionManager();
        Thread thread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    String status = session.getPreferences(SplashScreen.this, "status");
                    Intent ii = new Intent(SplashScreen.this, SignInAct.class);
                    startActivity(ii);
                   /* if (status.equals("1")) {
                        Intent intent = new Intent(SplashScreen.this, Welcome_page.class);
                        startActivity(intent);
                    } else {
                        Intent i = new Intent(SplashScreen.this, SignInAct.class);
                        startActivity(i);
                    }*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        goNext();
    }

}
