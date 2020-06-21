package cl.innovatti.geoappgoogle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cl.innovatti.geoappgoogle.R;
import cl.innovatti.geoappgoogle.activities.client.RegisterActivity;
import cl.innovatti.geoappgoogle.activities.driver.DriverRegisterActivity;
import cl.innovatti.geoappgoogle.includes.MyToolbar;

public class SelectOptionAuthActivity extends AppCompatActivity {
    Button mBtnLogin;
    Button mBtnRegister;
    SharedPreferences mPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        MyToolbar.show(this,"Seleccione una opción", true  );

        mBtnLogin = findViewById(R.id.btnLogin);
        mBtnRegister = findViewById(R.id.btnRegister);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToLogin();
            }
        });

        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToRegister();
            }
        });


    }

    private void goToLogin() {
        Intent intent = new Intent(SelectOptionAuthActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    private void goToRegister() {
        String tipoUsuario = mPref.getString( "user","" );

        if(tipoUsuario.equals("client")){
            Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);
            startActivity(intent);
        }else if (tipoUsuario.equals("driver")){
            Intent intent = new Intent(SelectOptionAuthActivity.this, DriverRegisterActivity.class);
            startActivity(intent);
        }

    }
}
