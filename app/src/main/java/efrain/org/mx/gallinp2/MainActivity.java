package efrain.org.mx.gallinp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static efrain.org.mx.gallinp2.GIPPMainActivity.GALLININFO;

public class MainActivity extends AppCompatActivity {

    private Button continuar;
    private EditText nombres;
    private EditText apellidop;
    private EditText apellidom;
    private EditText email;
    private Spinner nivel;
    public static final String NOMBRES = "org.mx.gallinp2.NOMBRES";
    public static final String AP = "org.mx.gallinp2.AP";
    public static final String AM = "org.mx.gallinp2.AM";
    public static final String CORREOE = "org.mx.gallinp2.CORREOE";
    public static final String NIVEL = "org.mx.gallinp2.NIVEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        continuar = (Button)findViewById(R.id.contButton);
        nombres = (EditText)findViewById(R.id.nombres);
        apellidop = (EditText)findViewById(R.id.apaterno);
        apellidom = (EditText)findViewById(R.id.amaterno);
        email = (EditText)findViewById(R.id.correoe);
        nivel = (Spinner)findViewById(R.id.SpinnerFeedbackType);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        SharedPreferences infoAnterior =getApplicationContext().getSharedPreferences(GALLININFO, Context.MODE_PRIVATE);
        if(infoAnterior.contains(NOMBRES)
                & infoAnterior.contains(CORREOE)){
            nombres.setText(infoAnterior.getString(NOMBRES,""));
            apellidop.setText(infoAnterior.getString(AP,""));
            apellidom.setText(infoAnterior.getString(AM,""));
            email.setText(infoAnterior.getString(CORREOE,""));
            //nivel.setSelection();
        }
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendInfo(View view){
        // Verifica que la cadena se parezca a la de un correo electronico.
        Pattern pattern = Pattern.compile("\\w+\\.?\\w+@\\w+\\.\\w+");
        Matcher matcher = pattern.matcher((CharSequence) email.getText());

        if ((nombres.getText().toString()).isEmpty()){
            nombres.setError("This field is mandatory");
        } else if ((email.getText().toString()).isEmpty()){
            email.setError("This field is mandatory");
        } else if ( !matcher.find() ){
            Toast.makeText(MainActivity.this,"Enter valid e-mail please.",Toast.LENGTH_LONG).show();
        } else if (nivel.getSelectedItem().toString().isEmpty()){
            Toast.makeText(MainActivity.this,"Choose a level",Toast.LENGTH_LONG).show();
        } else {

            Intent intent = new Intent(this, LluviaActivity.class);
            intent.putExtra(NOMBRES,nombres.getText().toString());
            intent.putExtra(AP, apellidop.getText().toString());
            intent.putExtra(AM, apellidom.getText().toString());
            intent.putExtra(CORREOE, email.getText().toString());
            intent.putExtra(NIVEL,nivel.getSelectedItem().toString());
            startActivity(intent);
        }

    }

}
