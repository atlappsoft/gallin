package efrain.org.mx.gallinp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class vocabulario_Activity extends AppCompatActivity {
    private RadioGroup fuentes;
    private Intent intentFuentes;
    public static final String FUENTE_VOC = "org.mx.gallinp2.FUENTE_VOC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intentFuentes = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulario_);
        fuentes = (RadioGroup)findViewById(R.id.fuentesRadioGroup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void sendInfo(View view){
        RadioButton voc_control = (RadioButton)findViewById(R.id.vocabulario_controlado);
        RadioButton leng_nat = (RadioButton)findViewById(R.id.lenguaje_natural);
        int opc = fuentes.getCheckedRadioButtonId();
        String fuente_select = new String();

        if(opc != voc_control.getId() & opc != leng_nat.getId()){
            Toast.makeText(vocabulario_Activity.this, "Choose a value", Toast.LENGTH_LONG).show();
        } else {
            switch (opc){
                case R.id.vocabulario_controlado:
                    fuente_select = voc_control.getText().toString();
                    break;
                case R.id.lenguaje_natural:
                    fuente_select = leng_nat.getText().toString();
                    break;
            }
            Intent intent = new Intent(this, glGrafica_Activity.class);
            intent.putExtra(FUENTE_VOC,fuente_select);
            intent.putExtras(intentFuentes.getExtras());
            startActivity(intent);
        }

    }

}
