package cl.innovatti.geoappgoogle.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import cl.innovatti.geoappgoogle.R;
import cl.innovatti.geoappgoogle.activities.client.MapClientActivity;
import cl.innovatti.geoappgoogle.activities.client.RegisterActivity;
import cl.innovatti.geoappgoogle.activities.driver.DriverRegisterActivity;
import cl.innovatti.geoappgoogle.activities.driver.MapDriverActivity;
import cl.innovatti.geoappgoogle.includes.MyToolbar;
import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonLogin;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    AlertDialog mDialog;
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        MyToolbar.show(LoginActivity.this,"Login de usuario", true  );
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mTextInputEmail = findViewById(R.id.textInputCorreo);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mButtonLogin =  findViewById(R.id.btnLogin);
        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);


        mDialog = new SpotsDialog.Builder().setContext(LoginActivity.this).setMessage("Espere un momento").build();
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }

            private void login() {
                String email = mTextInputEmail.getText().toString();
                String password = mTextInputPassword.getText().toString();


                if(!email.isEmpty() && !password.isEmpty()){
                    mDialog.show();
                    if(password.length() >= 6){
                        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        String tipoUsuario = mPref.getString( "user","" );

                                        if(tipoUsuario.equals("client")){
                                            Intent intent = new Intent(LoginActivity.this , MapClientActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // CON ESTO EL USUARIO NO PUEDE VOLVER HACIA ATRÁS...
                                            startActivity(intent);
                                        }else if (tipoUsuario.equals("driver")){
                                            Intent intent = new Intent(LoginActivity.this, MapDriverActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // CON ESTO EL USUARIO NO PUEDE VOLVER HACIA ATRÁS...
                                            startActivity(intent);
                                        }

                                    }else{
                                        Toast.makeText(LoginActivity.this, "La contraseña o el password son incorrectos", Toast.LENGTH_SHORT).show();
                                    }
                                   mDialog.dismiss();

                            }
                        });
                    }else{
                        Toast.makeText(LoginActivity.this, "La contraseña debe tener 6 o más carácteres", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "La contraseña y el email son obligatorios", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
