package efrain.org.mx.gallinp2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class glGrafica_Activity extends AppCompatActivity {

    private GALLINSurfaceView view;
    public static TextView mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent encuestasAnt = getIntent();
        Bundle info = encuestasAnt.getExtras();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
        Display dsp = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        dsp.getSize(size);
        //Toast.makeText(glGrafica_Activity.this, "w: "+size.x+" h: "+size.y,Toast.LENGTH_LONG).show();

        FrameLayout frmLayout = new FrameLayout(this);
        RelativeLayout rlayout = new RelativeLayout(this);
        mensaje = new TextView(this);
        mensaje.setTextColor(Color.WHITE);
        mensaje.setTextSize(12.0f);

        view = new GALLINSurfaceView(info, this, mensaje, size.x, size.y);

        rlayout.addView(frmLayout);
        frmLayout.addView(view);
        rlayout.addView(mensaje);
        setContentView(rlayout);

        String nivel = info.getString(MainActivity.NIVEL);
        String recurso = info.getString(LluviaActivity.RECURSO);
        String voc = info.getString(vocabulario_Activity.FUENTE_VOC);
    }

    @Override
    public void onResume(){
        super.onResume();
        view.onResume();
    }
}

class GALLINSurfaceView extends GLSurfaceView {

    //private OpenGLRenderer1 render = null;
    private OpenGLRenderer render = null;

    public GALLINSurfaceView(Bundle infoExtra, Context ctxt, TextView msj, float w, float h) {
        super(ctxt);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        //render = new OpenGLRenderer1(infoExtra, ctxt, dwb);
        render = new OpenGLRenderer(infoExtra, ctxt, w, h);
        setRenderer(render);
    }

    public float getRecurso() {
        return render.getResultadoX();
    }

}
