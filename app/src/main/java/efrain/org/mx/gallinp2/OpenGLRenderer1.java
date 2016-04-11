package efrain.org.mx.gallinp2;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by efrain on 15/02/16.
 */
public class OpenGLRenderer1 implements Renderer{

    private graficaCuadrantes3D cuadrante;
    private graficaPunto punto;
    private Bundle info;
    private GLText glText;
    private Context context;
    private float resultadoX;
    private Drawable drawable;

    public static float fov_degrees =  45f;
    public static float fov_radians =  fov_degrees / 180 * (float) Math.PI;
    public static float aspect;
    public static float camZ;

    public OpenGLRenderer1(Bundle i, Context ctxt, Drawable dwb){

        info = i;
        context = ctxt;
        drawable = dwb;
    }


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
        gl.glClearColor(0.0f, 0.7f, 0.7f, 0.5f);
        // Enable Smooth Shading, default not really needed.
        gl.glShadeModel(GL10.GL_SMOOTH);
        // Depth buffer setup.
        gl.glClearDepthf(1.0f);
        // Enables depth testing.
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // The type of depth testing to do.
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Really nice perspective calculations.
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_NICEST);

        // Create the GLText
        glText = new GLText( gl, context.getAssets() );

        // Load the font from file (set size + padding), creates the texture
        // NOTE: after a successful call to this the font is ready for rendering!
        glText.load( "Roboto-Regular.ttf", 15, 2, 2 );  // Create Font (Height: 14 Pixels / X+Y Padding 2 Pixels)
        cuadrante = new graficaCuadrantes3D(info);
        punto = new graficaPunto(info);
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
        //glTexto.dibujaTexto();
        gl.glRotatef(30f, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(-25f, 0.0f, 1.0f, 0.0f);
        //gl.glRotatef(45f,0.0f,0.0f,1.0f);

        cuadrante.draw(gl);
        //punto.draw(gl);
        resultadoX = punto.getX_recurso();
        //gltext.dibujaEtiquetas();
        this.dibujaTexto(gl);


    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.
         * microedition.khronos.opengles.GL10, int, int)
     */
    /*public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f,
                (float) width / (float) height,
                0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();
    }*/

    public float getResultadoX(){
        return resultadoX;
    }

    private void dibujaTexto(GL10 gl){
        // Redraw background color
        //gl.glClear( GL10.GL_COLOR_BUFFER_BIT );

        // Set to ModelView mode
        gl.glMatrixMode( GL10.GL_MODELVIEW );           // Activate Model View Matrix
        gl.glLoadIdentity();                            // Load Identity Matrix

        // enable texture + alpha blending
        // NOTE: this is required for text rendering! we could incorporate it into
        // the GLText class, but then it would be called multiple times (which impacts performance).
        gl.glEnable( GL10.GL_TEXTURE_2D );              // Enable Texture Mapping
        gl.glEnable( GL10.GL_BLEND );                   // Enable Alpha Blend
        gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );  // Set Alpha Blend Function

        // TEST: render the entire font texture
        //gl.glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );         // Set Color to Use
        //glText.drawTexture( width, height, 0 );            // Draw the Entire Texture

        // TEST: render some strings with the font
        glText.begin(1.0f, 1.0f, 1.0f, 1.0f);         // Begin Text Rendering (Set Color WHITE)
        //glText.draw( "Test String :)", 0, 0, 0 );          // Draw Test String
        //glText.draw( "Line 1", 50, 50, 0 );                // Draw Test String
        glText.draw( "Line 2", 100, 100, 0 );              // Draw Test String
        glText.end();                                   // End Text Rendering

   /*   glText.begin( 0.0f, 0.0f, 1.0f, 1.0f );         // Begin Text Rendering (Set Color BLUE)
      glText.draw( "More Lines...", 50, 150, 0 );        // Draw Test String
      glText.draw( "The End.", 50, 150 + glText.getCharHeight(), 0 );  // Draw Test String
      glText.end();                                   // End Text Rendering
     */

        /*glText.begin( 1.0f, 0.0f, 0.0f, 1.0f );         // Begin Text Rendering (Set Color RED)
        glText.draw( "zoom out !", -200,0, -800 );        // Draw Test String
        glText.draw( "zoom in !", -50,-50, 600 );        // Draw Test String

        glText.end();*/

        // disable texture + alpha
        gl.glDisable( GL10.GL_BLEND );                  // Disable Alpha Blend
        gl.glDisable( GL10.GL_TEXTURE_2D );             // Disable Texture Mapping
    }

    public void onSurfaceChanged(GL10 gl, int x, int y) {

        aspect =  (float) x / (float) y;
        camZ = y / 2 / (float) Math.tan(fov_radians / 2);


        if (x == 0) { // Prevent A Divide By Zero By
            x = 1; // Making Height Equal One
        }
        gl.glViewport(0, 0, x, y); // Reset The Current Viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select The Projection Matrix
        gl.glLoadIdentity(); // Reset The Projection Matrix

        // Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(gl, fov_degrees, aspect, camZ / 10, camZ * 10);
        //GLU.gluOrtho2D(gl, 0, x, 0, y);
        GLU.gluLookAt(gl, 0, 0, camZ, 0, 0, 0, 0, 1, 0); // move camera back

        gl.glMatrixMode(GL10.GL_MODELVIEW); // Select The Modelview Matrix
        gl.glLoadIdentity(); // Reset The Modelview Matrix
    }

}
