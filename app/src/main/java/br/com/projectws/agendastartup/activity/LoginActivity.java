package br.com.projectws.agendastartup.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.projectws.agendastartup.R;

public class LoginActivity extends AppCompatActivity {

    EditText loginEdtTxt;
    EditText passEdtTxt;

    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEdtTxt = (EditText) findViewById(R.id.loginEdtTxt);
        passEdtTxt = (EditText) findViewById(R.id.passEdtTxt);

        loginBtn = (Button) findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }
        });
    }

    /**
     * Faz o login
     */
    public void logar() {
        if(loginEdtTxt.getText().toString().equals("user") && passEdtTxt.getText().toString().equals("12345")) {
            Intent i = new Intent(this, MainActivity.class);

            Toast.makeText(LoginActivity.this, "Login efetuado com sucesso.", Toast.LENGTH_SHORT).show();

            startActivity(i);
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);

            dialog.setTitle("Falha ao realizar login");
            dialog.setMessage("Usuário e/ou senha inválido.");

            dialog.setNeutralButton("OK", null);

            dialog.show();
        }
    }
}