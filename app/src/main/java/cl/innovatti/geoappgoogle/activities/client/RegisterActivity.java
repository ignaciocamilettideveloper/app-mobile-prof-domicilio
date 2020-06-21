package cl.innovatti.geoappgoogle.activities.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import cl.innovatti.geoappgoogle.R;
import cl.innovatti.geoappgoogle.activities.driver.DriverRegisterActivity;
import cl.innovatti.geoappgoogle.activities.driver.MapDriverActivity;
import cl.innovatti.geoappgoogle.includes.MyToolbar;
import cl.innovatti.geoappgoogle.model.Client;
import cl.innovatti.geoappgoogle.providers.AuthProvider;
import cl.innovatti.geoappgoogle.providers.ClientProvider;
import cl.innovatti.geoappgoogle.providers.DriverProviver;
import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {
    Button mBtnRegister;
    TextInputEditText mTxtNombreCompleto;
    TextInputEditText mTxtCorreo;
    TextInputEditText mTxtPassword;
    AlertDialog mDialog;
    AuthProvider mAuthProvider;
    ClientProvider mClientProvider;
    DriverProviver mDriverProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mBtnRegister = findViewById(R.id.btnRegister);
        mTxtNombreCompleto = findViewById(R.id.txtNombreCompleto);
        mTxtCorreo = findViewById(R.id.txtEmail);
        mTxtPassword = findViewById(R.id.txtPassword);
        MyToolbar.show(this,"Registro de Cliente", true  );

        mAuthProvider = new AuthProvider();
        mClientProvider = new ClientProvider();
        mDriverProvider = new DriverProviver();

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegister();
            }

            private void clickRegister() {
                final String nombre = mTxtNombreCompleto.getText().toString();
                final String email = mTxtCorreo.getText().toString();
                String password = mTxtPassword.getText().toString();
                mDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Espere un momento").build();



                if (!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    if (password.length() >= 6) {
                        mDialog.show();
                       register(nombre,email,password);
                    } else {
                        Toast.makeText(RegisterActivity.this, " la contraseña debe tener al menos 6 carácteres", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(RegisterActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();

                }

            }

            public void register(final String name, final String email, final String password){
                mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mDialog.hide();
                        if (task.isSuccessful()) {
                            String id = FirebaseAuth.getInstance().getUid();
                            Client cliente = new Client(id,name,email);
                            create(cliente);
                        }else {
                            Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }


                });
        }

        public void create(Client client){
                mClientProvider.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this, MapClientActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // CON ESTO EL USUARIO NO PUEDE VOLVER HACIA ATRÁS...
                                startActivity(intent);
                                // Toast.makeText(DriverRegisterActivity.this, "El registro se realizó exitosamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            Toast.makeText(RegisterActivity.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }

/*
            private void guardarUsuario(String id,String nombre, String email) {
                String selectedUser = mPref.getString("user", "");
                User user = new User();
                user.setEmail(email);
                user.setName(nombre);
                user.setId(id);

                if (selectedUser.equals("driver")) {
                    mdataBase.child("Users").child("Drivers").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RegisterActivity.this, "Fallo el Registro", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else if (selectedUser.equals("client")) {
                    mdataBase.child("Users").child("Clients").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RegisterActivity.this, "Fallo en el Registro", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }*/

        });
    }
}
