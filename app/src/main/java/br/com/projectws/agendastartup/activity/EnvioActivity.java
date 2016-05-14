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

public class EnvioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio);

        Button enviarBtn = (Button) findViewById(R.id.enviarBtn);

        assert enviarBtn != null;
        enviarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarWhatsApp("+5599603082");
            }
        });
    }

    public void enviarWhatsApp(String numero) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("mensagem", "minha mensagem..........");
        clipboard.setPrimaryClip(clip);

        Uri uri = Uri.parse("smsto:" + numero);

        Intent i = new Intent(Intent.ACTION_SENDTO, uri);

        i.setPackage("com.whatsapp");

        startActivity(i);
    }
}
