package cl.innovatti.geoappgoogle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import cl.innovatti.geoappgoogle.R;
import cl.innovatti.geoappgoogle.activities.client.MapClientActivity;
import cl.innovatti.geoappgoogle.activities.client.RegisterActivity;
import cl.innovatti.geoappgoogle.activities.driver.DriverRegisterActivity;
import cl.innovatti.geoappgoogle.activities.driver.MapDriverActivity;

public class MainActivity extends AppCompatActivity {
    Button mButtonImClient;
    Button mButtonImDriver;
    SharedPreferences mPref;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonImClient = findViewById(R.id.btnImClient);
        mButtonImDriver = findViewById(R.id.btnImDriver);
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        final SharedPreferences.Editor editor = mPref.edit();

        mButtonImClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("user","client");
                editor.apply();
                goToSelectAuth();
            }
        });

        mButtonImDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("user","driver");
                editor.apply();
                goToSelectAuth();
            }
        });
    }


    private void goToSelectAuth() {
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            String user = mPref.getString("user", "");

            if(user.equals("client")){
                Intent intent = new Intent(MainActivity.this, MapClientActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // CON ESTO EL USUARIO NO PUEDE VOLVER HACIA ATRÁS...
                startActivity(intent);
            }else if (user.equals("driver")){
                Intent intent = new Intent(MainActivity.this, MapDriverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // CON ESTO EL USUARIO NO PUEDE VOLVER HACIA ATRÁS...
                startActivity(intent);
            }
        }
    }
}
