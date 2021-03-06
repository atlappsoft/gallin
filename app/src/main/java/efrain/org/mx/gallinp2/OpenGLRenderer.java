package efrain.org.mx.gallinp2;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import efrain.org.mx.gallinp2.glGrafica_Activity.Vectores;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by efrain on 15/02/16.
 */
public class OpenGLRenderer implements Renderer{

    private graficaCuadrantes3D cuadrante;
    private ArrayList<graficaPunto> punto;
    private Bundle info;
    private GLText glText;
    private GLText glEtiquetas;
    private Context context;
    private ArrayList<Vectores> puntos;
    public static float fov_degrees =  45f;
    public static float fov_radians =  fov_degrees / 180 * (float) Math.PI;
    public static float aspect;
    public static float camZ;

    public OpenGLRenderer(Bundle i, Context ctxt, float x, float y){
        info = i;
        context = ctxt;
    }

    public OpenGLRenderer(ArrayList<Vectores> vectores, Bundle i, Context ctxt, float x, float y){
        info = i;
        context = ctxt;
        puntos = vectores;
    }

    public float getCamZ(){ return camZ; }

    /*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.
         * microedition.khronos.opengles.GL10, javax.microedition.khronos.
         * egl.EGLConfig)
	 */
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background color to black ( rgba ).
        gl.glClearColor(0.2f, 0.6f, 1.0f, 0.5f);
        // Enable Smooth Shading, default not really needed.
        gl.glShadeModel(GL10.GL_SMOOTH);
        // Depth buffer setup.
        gl.glClearDepthf(1.0f);
        // Enables depth testing.
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // The type of depth testing to do.
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glDisable(GL10.GL_DITHER);
        // Really nice perspective calculations.
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_NICEST);


        glText = new GLText( gl, context.getAssets() );
        glText.load("Roboto-Regular.ttf", 18, 2, 2);  // Create Font (Height: 14 Pixels / X+Y Padding 2 Pixels)
        glEtiquetas = new GLText( gl, context.getAssets() );
        glEtiquetas.load("Roboto-Regular.ttf", 14, 2, 2);
        cuadrante = new graficaCuadrantes3D(info);
        punto = new ArrayList<>();

        /**
         * Si hay mas de un punto lo grafica de otra forma solo grafica el existente.
         */
        /*if (puntos != null && !puntos.isEmpty()){

        } else {
            punto.add(new graficaPunto(info));
        }*/
        Iterator iterVect = puntos.iterator();
        Vectores proxVect;

        while (iterVect.hasNext()){
            proxVect = (Vectores)iterVect.next();
            punto.add(new graficaPunto(proxVect));
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.
         * microedition.khronos.opengles.GL10)
     */
    public void onDrawFrame(GL10 gl) {

        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | //
                GL10.GL_DEPTH_BUFFER_BIT);
        // Replace the current matrix with the identity matrix
        gl.glLoadIdentity();
        // Translates 4 units into the screen.
        gl.glTranslatef(0, 0, -4);
        gl.glRotatef(30f, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(-25f, 0.0f, 1.0f, 0.0f);
        graficaPunto proxPunto = null;
        Iterator iterGrafPuntos = punto.iterator();
        graficaPunto puntoActual = (graficaPunto) iterGrafPuntos.next();

        cuadrante.draw(gl);
        while (iterGrafPuntos.hasNext()){
            proxPunto = (graficaPunto)iterGrafPuntos.next();
            proxPunto.draw(gl);
        }

        puntoActual.draw(gl);
        this.dibujaTexto(gl, puntoActual.getX_recurso());
        //punto.draw(gl);
        //botonSalvar.draw(gl);
        //this.dibujaTexto(gl, proxPunto.getX_recurso());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.
         * microedition.khronos.opengles.GL10, int, int)
     */
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        aspect =  (float) width / (float) height;
        camZ = height / 1 / (float) Math.tan(fov_radians / 2);
        float deepFactor = (width * height)/100;
        float deepFactor1 = camZ /3.1f;

        if (width == 0) { // Prevent A Divide By Zero By
            width = 1; // Making Height Equal One
        }
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl.glLoadIdentity();
        // Calculate The Aspect Ratio Of The Window
        //GLU.gluPerspective(gl, fov_degrees, aspect, camZ / 4, camZ * 10);
        gl.glOrthof(-270.0f,270.0f,-350.0f,350.0f,camZ / 5 , camZ * 10);
        GLU.gluLookAt(gl, 0, 0, deepFactor1, 0, 0, 0, 0, 1, 0); // move camera back
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();
    }

    private void dibujaTexto(GL10 gl, float rx){
        gl.glMatrixMode( GL10.GL_MODELVIEW );           // Activate Model View Matrix
        gl.glLoadIdentity();                            // Load Identity Matrix

        // enable texture + alpha blending
        // NOTE: this is required for text rendering! we could incorporate it into
        // the GLText class, but then it would be called multiple times (which impacts performance).
        gl.glEnable( GL10.GL_TEXTURE_2D);              // Enable Texture Mapping
        gl.glEnable( GL10.GL_BLEND );                   // Enable Alpha Blend
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);  // Set Alpha Blend Function

        // TEST: render the entire font texture
        gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);         // Set Color to Use
        //glText.drawTexture( (int)Width, (int)Heigth, 0 );            // Draw the Entire Texture

        // TEST: render some strings with the font
        glText.begin(1.0f, 1.0f, 1.0f, 1.0f);         // Begin Text Rendering (Set Color WHITE)
        glText.draw( "ACADEMIC LEVEL", -50.0f, 220.0f, 0.0f );              // Draw Test String
        glText.draw( "RESOURCES", 140.0f, -70.0f, 0.0f);
        glText.draw("VOCABULARY", -140.0f, -150.0f, 0.0f);
        if(rx < 0)
            glText.draw( "Want to change to positive quadrant? Read what follows!", -200.0f, 300.0f, 0.0f );
        if(rx > 0) {
            glText.draw("Congratualtions, you are beyond any search engine!", -200.0f, 320.0f, 0.0f);
            glText.draw("Improve your state by attending the library´s staff!", -200.0f, 300.0f, 0.0f);
        }
        //glText.draw("SAVE",0.0f,-300.0f,0.0f);

        glText.end();                                   // End Text Rendering

        // disable texture + alpha
        gl.glDisable( GL10.GL_BLEND );                  // Disable Alpha Blend
        gl.glDisable(GL10.GL_TEXTURE_2D);             // Disable Texture Mapping
    }
}
