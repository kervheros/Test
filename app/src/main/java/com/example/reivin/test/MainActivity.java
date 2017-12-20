package com.example.reivin.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.crypto.spec.RC2ParameterSpec;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private GoogleApiClient api;
    private SignInButton signInButton;
    public static final int RC_SING_IN=9001;

    private EditText nombre;
    private EditText contraseña;
    private TextView info;

    private int cont = 3;
    public TextView registrar;
    private TextView notifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrar=(TextView)this.findViewById(R.id.lb_registro);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regi=new Intent(MainActivity.this, Registros.class);
                MainActivity.this.startActivity(regi);
            }
        });


     registerservice();
     registerevent();
    }






    private void registerevent() {
        signInButton =(SignInButton) this.findViewById(R.id.correo);
        signInButton.setOnClickListener(this);

    }

    private void registerservice() {
        GoogleSignInOptions option=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .build();
        api =new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API ,option)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.correo){
            Intent google=Auth.GoogleSignInApi.getSignInIntent(api);
            this.startActivityForResult(google, RC_SING_IN);
        }

    }
    @Override
    public void onActivityResult(int r1, int r2, Intent data){
        super.onActivityResult(r1, r2, data);
        if (r1 == RC_SING_IN){
            GoogleSignInResult result =Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            loadData(result);
        }
    }

    private void loadData(GoogleSignInResult result) {
        if (result.isSuccess()){
            go_access_permit();
            GoogleSignInAccount datos = result.getSignInAccount();
            TextView txt = (TextView)this.findViewById(R.id.mensaje);
            txt.setText(datos.getEmail());
        }else {
            Toast.makeText(this, "Error no se pudo iniciar secion", Toast.LENGTH_SHORT).show();
        }
    }

    private void go_access_permit() {
        Intent cont_per =new Intent(this, cont_permitido.class);
        startActivity(cont_per);
    }
}
