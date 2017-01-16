package efrain.org.mx.gallinp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;
import static efrain.org.mx.gallinp2.MainActivity.NOMBRES;
import static efrain.org.mx.gallinp2.MainActivity.AP;
import static efrain.org.mx.gallinp2.MainActivity.AM;
import static efrain.org.mx.gallinp2.MainActivity.CORREOE;
import static efrain.org.mx.gallinp2.GIPPMainActivity.GALLININFO;

public class glGrafica_Activity extends AppCompatActivity {

    private GALLINSurfaceView view;
    public static Button saveGraph_btn;
    public static final String Lic = "Lic";
    public static final String Esp = "Esp";
    public static final String Maest = "Maest";
    public static final String Doc = "Doc";
    private final String llgFile = "llgFile.log";
    public static final String X_COOR = "x_coor";
    public static final String Y_COOR = "y_coor";
    public static final String Z_COOR = "z_coor";
    public static final String Z_SENTIDO = "z_sentido";
    private float x, y, z = 0;
    private String nivel;
    private String recurso;
    private String fuente_rec;
    private String fuente_voc;
    private String lic;
    private String esp;
    private String maest;
    private String doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent encuestasAnt = getIntent();
        Bundle info = encuestasAnt.getExtras();
        String lic = getResources().getString(R.string.feedbacktype1);
        String esp = getResources().getString(R.string.feedbacktype2);
        String maest = getResources().getString(R.string.feedbacktype3);
        String doc = getResources().getString(R.string.feedbacktype4);
        String x_ant = new String();
        String y_ant = new String();
        String z_ant = new String();
        String zsent_ant = new String();
        Boolean foundDatos =false;
        info.putString(Lic,lic);
        info.putString(Esp,esp);
        info.putString(Maest,maest);
        info.putString(Doc,doc);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
        Point size = new Point();
        /*DisplayMetrics metricas = getResources().getDisplayMetrics();
        Display screen = getWindowManager().getDefaultDisplay();*/

        // Verificacion si hay datos guardados.
        //SharedPreferences infoAnterior = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences infoAnterior = getApplicationContext().getSharedPreferences(GALLININFO, Context.MODE_PRIVATE);
        Toast.makeText(glGrafica_Activity.this,"Datos: "+NOMBRES+" "
                + CORREOE,Toast.LENGTH_SHORT).show();
        if( infoAnterior.contains(X_COOR)
                & infoAnterior.contains(Y_COOR)
                & infoAnterior.contains(Z_COOR)
                & infoAnterior.contains(Z_SENTIDO)){
            x_ant = infoAnterior.getString(X_COOR, "");
            y_ant = infoAnterior.getString(Y_COOR,"");
            z_ant = infoAnterior.getString(Z_COOR,"");
            zsent_ant = infoAnterior.getString(Z_SENTIDO, "");
            foundDatos = true;
            Toast.makeText(glGrafica_Activity.this,"Datos encontrados",Toast.LENGTH_SHORT).show();
        }

        FrameLayout frmLayout = new FrameLayout(this);
        LinearLayout rlayout = new LinearLayout(this);

        /**
         * Layout donde se colocan los controles sobre la grafica.
         */
        rlayout.setWeightSum(15.0f);
        rlayout.setOrientation(LinearLayout.VERTICAL);
        rlayout.setGravity(Gravity.BOTTOM);

        saveGraph_btn = new Button(rlayout.getContext());
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnparams.weight = 1.0f;
        btnparams.gravity = Gravity.CENTER;

        if(!foundDatos){
            //saveGraph_btn.setEnabled(false);
            //saveGraph_btn.setActivated(false);
            saveGraph_btn.setText(R.string.salva);
            saveGraph_btn.setTextSize(12.0f);
            saveGraph_btn.setOnClickListener(new guardaInfoOnClickListener(info,glGrafica_Activity.this, infoAnterior));
            saveGraph_btn.setLayoutParams(btnparams);

            rlayout.addView(saveGraph_btn);
        }

        /**
         * Se llama a la vista que crea la grafica 3D.
         */
        view = new GALLINSurfaceView(info, this, size.x, size.y);

        frmLayout.addView(view);
        frmLayout.addView(rlayout);
        /*rlayout.addView(frmLayout);
        frmLayout.addView(view);
        rlayout.addView(saveGraph);*/
        setContentView(frmLayout);

    }

    @Override
    public void onResume(){
        super.onResume();
        view.onResume();
    }

    /**
     * Save linguistic storm information on a file (academic level, resources, vocabulary).
     * NOT YET IMPLEMENTED
     */
    public void onSaveGraph(){
        String infollg = new String();
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(llgFile, Context.MODE_PRIVATE);
            outputStream.write(infollg.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Transforma las coordenadas en formato cadena de caracteres a coordenadas de numeros enteros.
     * @param db
     */
    private void transformaACoordenadas(Bundle db){
        nivel = db.getString(MainActivity.NIVEL);
        recurso = db.getString(LluviaActivity.RECURSO);
        fuente_voc = db.getString(vocabulario_Activity.FUENTE_VOC);
        fuente_rec = db.getString(LluviaActivity.FUENTE_REC);
        lic = db.getString(glGrafica_Activity.Lic);
        esp = db.getString(glGrafica_Activity.Esp);
        maest = db.getString(glGrafica_Activity.Maest);
        doc = db.getString(glGrafica_Activity.Doc);

        if(nivel.equals(lic)){
            y = 50.0f;
        } else if(nivel.equals(esp)){
            y = 100.0f;
        } else if(nivel.equals(maest)){
            y = 150.0f;
        } else if(nivel.equals(doc)){
            y = 190.0f;
        }


        switch (recurso){
            case "Books":
                x = 50.0f;
                break;
            case "Journals":
                x = 100.0f;
                break;
            case "Databases":
                x = 150.0f;
                break;
            case "Metasearch engines":
                x = 190.0f;
        }

        switch (fuente_voc){
            case "Controlled Vocabulary":
                z = 100.0f;
                break;
            case "Natural Language":
                z = -100.0f;
                break;
        }
        switch (fuente_rec){
            case "Google, Wikipedia, Yahoo, other search engines":
                x = x * -1;
                break;
        }
    }

    /**
     * Clase creada para implementar funcion de guardar informacion inicial del usuario de GALLIN
     * y sus resultados al usar la herramienta.
     *
     */
    class guardaInfoOnClickListener implements View.OnClickListener{

        Bundle infoPunto;
        Context originalContext;
        SharedPreferences preferences;

        public guardaInfoOnClickListener(Bundle inf, Context ctxt, SharedPreferences prfr){
            infoPunto = inf;
            originalContext = ctxt;
            preferences = prfr;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(originalContext,"Datos: "+NOMBRES+" "
                    +CORREOE,Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(glGrafica_Activity.X_COOR,infoPunto.getString(LluviaActivity.RECURSO));
            editor.putString(glGrafica_Activity.Y_COOR, infoPunto.getString(MainActivity.NIVEL));
            editor.putString(glGrafica_Activity.Z_COOR, infoPunto.getString(vocabulario_Activity.FUENTE_VOC));
            editor.putString(glGrafica_Activity.Z_SENTIDO, infoPunto.getString(LluviaActivity.FUENTE_REC));
            editor.putString(NOMBRES, infoPunto.getString(MainActivity.NOMBRES));
            editor.putString(AP, infoPunto.getString(MainActivity.AP));
            editor.putString(AM, infoPunto.getString(MainActivity.AM));
            editor.putString(CORREOE, infoPunto.getString(MainActivity.CORREOE));
            editor.commit();

            Toast.makeText(originalContext, "Guardando info", Toast.LENGTH_SHORT).show();
            v.setEnabled(false);
        }
    }
}

class GALLINSurfaceView extends GLSurfaceView {

    private OpenGLRenderer render = null;

    public GALLINSurfaceView(Bundle infoExtra, Context ctxt, float w, float h) {
        super(ctxt);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        render = new OpenGLRenderer(infoExtra, ctxt, w, h);
        setRenderer(render);
    }

    public GALLINSurfaceView(ArrayList<Integer> vectores, Bundle infoExtra, Context ctxt, float w, float h){
        super(ctxt);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        render = new OpenGLRenderer(infoExtra, ctxt, w, h);
        setRenderer(render);
    }

}
