package br.com.projectws.agendastartup.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.projectws.agendastartup.R;

public class ClienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        Button enviarBtn = (Button) findViewById(R.id.enviarBtn);

        assert enviarBtn != null;
        enviarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("mensagem", "minha mensagem..........");
                clipboard.setPrimaryClip(clip);

                Uri uri = Uri.parse("smsto:" + "+554299603082");

                Intent i = new Intent(Intent.ACTION_SENDTO, uri);

                i.setPackage("com.whatsapp");

                startActivity(i);
            }
        });
    }
}