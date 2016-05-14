package br.com.projectws.agendastartup;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void sendWhatsAppMessageTo(String numero) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("mensagem", "minha mensagem..........");
        clipboard.setPrimaryClip(clip);

        Uri uri = Uri.parse("smsto:" + numero);

        Intent i = new Intent(Intent.ACTION_SENDTO, uri);

        i.setPackage("com.whatsapp");

        startActivity(i);
    }
}
