package efrain.org.mx.gallinp2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class glGrafica_Activity extends AppCompatActivity {

    private GALLINSurfaceView view;
    public static TextView mensaje;
    public static final String Lic = "Lic";
    public static final String Esp = "Esp";
    public static final String Maest = "Maest";
    public static final String Doc = "Doc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent encuestasAnt = getIntent();
        Bundle info = encuestasAnt.getExtras();
        String lic = getResources().getString(R.string.feedbacktype1);
        String esp = getResources().getString(R.string.feedbacktype2);
        String maest = getResources().getString(R.string.feedbacktype3);
        String doc = getResources().getString(R.string.feedbacktype4);
        info.putString(Lic,lic);
        info.putString(Esp,esp);
        info.putString(Maest,maest);
        info.putString(Doc,doc);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
        Point size = new Point();
        DisplayMetrics metricas = getResources().getDisplayMetrics();
        float dpix = metricas.xdpi;
        float dpiy = metricas.ydpi;
        Toast.makeText(glGrafica_Activity.this, "w: "+dpix+" h: "+dpiy,Toast.LENGTH_LONG).show();

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
