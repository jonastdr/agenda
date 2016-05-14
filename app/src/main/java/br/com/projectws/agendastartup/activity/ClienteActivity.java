package br.com.projectws.agendastartup.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.projectws.agendastartup.R;
import br.com.projectws.agendastartup.model.Cliente;

public class ClienteActivity extends AppCompatActivity {

    TextView nome, telefone;

    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        nome = (TextView) findViewById(R.id.nomeTextView);
        telefone = (TextView) findViewById(R.id.telefoneTextView);

        cliente = getIntent().getParcelableExtra("cliente");

        nome.setText(cliente.getNome());
        telefone.setText(cliente.getTelefone());

        Button enviarBtn = (Button) findViewById(R.id.enviarBtn);

        assert enviarBtn != null;
        enviarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("mensagem", "minha mensagem..........");
                clipboard.setPrimaryClip(clip);

                Uri uri = Uri.parse("smsto:" + "+55" + cliente.getTelefone());

                Intent i = new Intent(Intent.ACTION_SENDTO, uri);

                i.setPackage("com.whatsapp");

                startActivity(i);
            }
        });
    }
}
