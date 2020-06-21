package cl.innovatti.geoappgoogle.activities.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import cl.innovatti.geoappgoogle.R;
import cl.innovatti.geoappgoogle.activities.MainActivity;
import cl.innovatti.geoappgoogle.providers.AuthProvider;

public class MapClientActivity extends AppCompatActivity {
    Button btnCerrarSesion;
    AuthProvider mAuthProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_client);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        mAuthProvider = new AuthProvider();
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuthProvider.logOut();
                Intent intent = new Intent(MapClientActivity.this,   MainActivity.class );
                startActivity(intent);
                finish();
            }
        });
    }
}
