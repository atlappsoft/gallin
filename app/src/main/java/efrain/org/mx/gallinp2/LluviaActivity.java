package efrain.org.mx.gallinp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LluviaActivity extends AppCompatActivity {

    private ListView myList;
    private Button getChoice;
    private RadioGroup recursos;
    private RadioGroup fuenteRec;
    private Intent intentMain;
    public static final String RECURSO = "org.mx.gallinp2.RECURSO";
    public static final String FUENTE_REC = "org.mx.gallinp2.FUENTE_REC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intentMain = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lluvia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recursos = (RadioGroup)findViewById(R.id.recursosRadioGroupss);
        fuenteRec = (RadioGroup)findViewById(R.id.fuenteRecRadioGroups);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void sendInfo(View view) {
        RadioButton libros = (RadioButton)findViewById(R.id.LibrosRadioButton);
        RadioButton revistas = (RadioButton)findViewById(R.id.RevistasRadioButton);
        RadioButton basesd = (RadioButton)findViewById(R.id.BasesDatosRadioButton);
        RadioButton metab = (RadioButton)findViewById(R.id.MetaBuscadoresRadioButton);
        int recurso = recursos.getCheckedRadioButtonId();

        RadioButton googleOtros = (RadioButton)findViewById(R.id.googleOtrosRadioButton);
        RadioButton bibliotecasDB = (RadioButton)findViewById(R.id.bibliotecasBDRadioButton);
        int fuenteRecursos = fuenteRec.getCheckedRadioButtonId();

        String recurso_select = new String();
        String fuenteRec_select = new String();

        if(recurso != libros.getId() & recurso != revistas.getId() & recurso != basesd.getId()
                & recurso != metab.getId()){

            Toast.makeText(LluviaActivity.this,"Elija un recurso",Toast.LENGTH_LONG).show();
        } else if(fuenteRecursos != googleOtros.getId() & fuenteRecursos != bibliotecasDB.getId()){

            Toast.makeText(LluviaActivity.this,"Elija una fuente de recursos",Toast.LENGTH_LONG).show();
        } else {
            switch (recurso){
                case R.id.LibrosRadioButton:
                    recurso_select = libros.getText().toString();
                    break;
                case R.id.RevistasRadioButton:
                    recurso_select = revistas.getText().toString();
                    break;
                case R.id.BasesDatosRadioButton:
                    recurso_select = basesd.getText().toString();
                    break;
                case R.id.MetaBuscadoresRadioButton:
                    recurso_select = metab.getText().toString();
                    break;
            }

            switch (fuenteRecursos){
                case R.id.googleOtrosRadioButton:
                    fuenteRec_select = googleOtros.getText().toString();
                    break;
                case R.id.bibliotecasBDRadioButton:
                    fuenteRec_select = bibliotecasDB.getText().toString();
                    break;
            }

            Intent intent = new Intent(this, vocabulario_Activity.class);
            intent.putExtra(RECURSO, recurso_select);
            intent.putExtra(FUENTE_REC, fuenteRec_select);
            intent.putExtras(intentMain.getExtras());
            startActivity(intent);
        }
    }

    public void sendBack(View view){

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
    }
}
