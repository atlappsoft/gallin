package efrain.org.mx.gallinp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static efrain.org.mx.gallinp2.MainActivity.NOMBRES;
import static efrain.org.mx.gallinp2.MainActivity.AP;
import static efrain.org.mx.gallinp2.MainActivity.AM;
import static efrain.org.mx.gallinp2.MainActivity.CORREOE;

public class GIPPMainActivity extends AppCompatActivity {

    public static final String GALLININFO = "gallininfo";

    TextView mensajeInicial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = Locale.getDefault().getDisplayLanguage();
        setContentView(R.layout.activity_gippmain);
        mensajeInicial = (TextView)findViewById(R.id.TextoInicial);
        mensajeInicial.setText(Html.fromHtml("<h3 style='text-align:center;'>Librarian Positioning System (LPS)</h3>"
                + "<div style='padding: 5px; text-align: center;'>Research Location Positioner</div>" +
                "<div style='padding: 5px; text-align: center;'>Through the following interactive \n" +
                "screens you will 3 facts: get self located\n" +
                "as a library user (in comparison to Google);\n" +
                "get to know the high qualified academic \n" +
                "databases of your field of knowledge, and\n" +
                "will be able to build a research purpose \n" +
                "and explode it.\n</div>"));
    }

    public void sendToEncuesta(View view){
        //SharedPreferences infoAnterior = getPreferences(Context.MODE_PRIVATE);
        Intent intent = new Intent(this,MainActivity.class);
        /*Toast.makeText(GIPPMainActivity.this,"Datos: "+NOMBRES+" "
                +CORREOE,Toast.LENGTH_SHORT).show();
        if(infoAnterior.contains(NOMBRES)
                & infoAnterior.contains(CORREOE)){
            intent = new Intent(this, InfoUsrActivity.class);
            Toast.makeText(GIPPMainActivity.this,"Datos encontrados",Toast.LENGTH_SHORT).show();

        } else {
            intent = new Intent(this, MainActivity.class);
            Toast.makeText(GIPPMainActivity.this,"No Datos",Toast.LENGTH_SHORT).show();
        }*/
        startActivity(intent);
    }
}
