package efrain.org.mx.gallinp2;

import android.content.Intent;
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

        if ((nombres.getText().toString()).isEmpty()){
            nombres.setError("Este campo es obligatorio");
        } else if ((email.getText().toString()).isEmpty()){
            email.setError("Este campo es obligatorio");
        } else if (nivel.getSelectedItem().toString().isEmpty()){
            Toast.makeText(MainActivity.this,"Elige un nivel",Toast.LENGTH_LONG).show();
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

    /**
     * TEXTVALIDATOR
     * (Validador de texto, tomada de la pagina: http://stackoverflow.com/questions/2763022/android-how-can-i-validate-edittext-input/11838715#11838715)
     *
     * Clase abstracta que valida la informacion de las cajas de texto de editable.
     */
    public abstract class TextValidator implements TextWatcher {
        private final TextView textView;

        public TextValidator(TextView textView) {
            this.textView = textView;
        }

        public abstract void validate(TextView textView, String text);

        @Override
        final public void afterTextChanged(Editable s) {
            String text = textView.getText().toString();
            validate(textView, text);
        }

        @Override
        final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

        @Override
        final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
    }
}
