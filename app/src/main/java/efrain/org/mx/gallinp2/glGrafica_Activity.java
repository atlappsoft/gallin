package efrain.org.mx.gallinp2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class glGrafica_Activity extends AppCompatActivity {

    private GALLINSurfaceView view;
    private TextView mensaje;
    private Drawable bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent encuestasAnt = getIntent();
        Bundle info = encuestasAnt.getExtras();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)

        FrameLayout frmLayout = new FrameLayout(this);
        RelativeLayout rlayout = new RelativeLayout(this);
        mensaje = new TextView(this);
        mensaje.setTextColor(Color.WHITE);
        mensaje.setTextSize(12.0f);
        bg = ContextCompat.getDrawable(this, R.drawable.background);
        view = new GALLINSurfaceView(info, this, mensaje, bg);

        frmLayout.addView(view);
        rlayout.addView(frmLayout);
        rlayout.addView(mensaje);
        setContentView(rlayout);

        //OpenGLRenderer1 glRender = new OpenGLRenderer1(encuestasAnt.getExtras(), this);
        String nivel = info.getString(MainActivity.NIVEL);
        String recurso = info.getString(LluviaActivity.RECURSO);
        String voc = info.getString(vocabulario_Activity.FUENTE_VOC);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        float rec = view.getRecurso();

        if (rec < 0) {
            mensaje.setText(Html.fromHtml("<div style='padding: 10px; text-align: center;'>" +
                    "Want to change to positive quadrant? Read what follows!" +
                    "</div>"));
        } else {
            mensaje.setText(Html.fromHtml("<div style='padding: 10px; text-align: center;'>" +
                    "Congratualtions, you are beyond any search engine! Improve your state by attending the library´s staff!" +
                    "</div>"));
        }
    }
}

class GALLINSurfaceView extends GLSurfaceView {

    //private OpenGLRenderer1 render = null;
    private OpenGLRenderer render = null;

    public GALLINSurfaceView(Bundle infoExtra, Context ctxt, TextView msj, Drawable dwb) {
        super(ctxt);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        //render = new OpenGLRenderer1(infoExtra, ctxt, dwb);
        render = new OpenGLRenderer(infoExtra, ctxt);
        setRenderer(render);
    }

    public float getRecurso() {
        float i = 0.0f;
        if (render != null)
            i =  render.getResultadoX();
        else
            i = 0.0f;
        return i;
    }


}
