package efrain.org.mx.gallinp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class GIPPMainActivity extends AppCompatActivity {

    TextView mensajeInicial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gippmain);
        mensajeInicial = (TextView)findViewById(R.id.TextoInicial);
        mensajeInicial.setText(Html.fromHtml("<h3 style='text-align:center;'>Librarian Positioning System (LPS)</h3>"
                + "<div style='padding: 5px; text-align: center;'>Research Location Positioner</div>" +
                "<div style='padding: 5px; text-align: center;'>Through the following interactive \n" +
                "screens you will 3 facts: get self located\n" +
                "as a library user (in comparison to Google);\n" +
                "get to know the high qualified academic \n" +
                "databases of your field of knowledge, and\n" +
                "will be able to build a resarch purpose \n" +
                "and explode it.\n</div>"));
    }

    public void sendToEncuesta(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
