package efrain.org.mx.gallinp2;

import android.os.Bundle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by efrain on 19/02/16.
 */
public class graficaPunto {

    private Bundle info;
    private String nivel;
    private String recurso;
    private String fuente_rec;
    private String fuente_voc;
    private String lic;
    private String esp;
    private String maest;
    private String doc;
    private float x_recurso;
    private float y_nivel;
    private float z_voc;
    private glGrafica_Activity.Vectores puntos;
    // Our vertices.
    private float vertices[] = {
            0.2f,  0.2f, 0.5f
    };
    // The order we like to connect them.
    private short[] indices = { 0 };
    // Our vertex buffer.
    private FloatBuffer vertexBuffer;
    // Our index buffer.
    private ShortBuffer indexBuffer;

    public graficaPunto(Bundle i){
        // a float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        info = i;
        nivel = info.getString(MainActivity.NIVEL);
        recurso = info.getString(LluviaActivity.RECURSO);
        fuente_voc = info.getString(vocabulario_Activity.FUENTE_VOC);
        fuente_rec = info.getString(LluviaActivity.FUENTE_REC);
        lic = info.getString(glGrafica_Activity.Lic);
        esp = info.getString(glGrafica_Activity.Esp);
        maest = info.getString(glGrafica_Activity.Maest);
        doc = info.getString(glGrafica_Activity.Doc);


        // short is 2 bytes, therefore we multiply the number if
        // vertices with 2.
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    public graficaPunto(glGrafica_Activity.Vectores pts){
        puntos = pts;

        // short is 2 bytes, therefore we multiply the number if
        // vertices with 2.
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    /**
     * Devuelve el valor de x del punto.
     * @return
     */
    public float getX_recurso(){
        return puntos.getX();
    }

    /**
     * Devuelve el valor y del punto.
     * @return
     */
    public float getY_nivel(){
        return puntos.getY();
    }

    /**
     * Devuelve el valor z del punto.s
     * @return
     */
    public float getZ_voc() {
        return puntos.getZ();
    }

    /**
     * Esta funcion dibuja el punto de interes en los cuadrantes.
     * @param gl
     */
    public void draw(GL10 gl) {

        //float puntoGallin[] = {x_recurso, y_nivel, z_voc};
        float puntoGallin[] = {puntos.getX(), puntos.getY(), puntos.getZ()};
        ByteBuffer vbb = ByteBuffer.allocateDirect(puntoGallin.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(puntoGallin);
        vertexBuffer.position(0);

        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        // Counter-clockwise winding.
        gl.glFrontFace(GL10.GL_CCW);
        // Enable face culling.
        gl.glEnable(GL10.GL_CULL_FACE);
        // What faces to remove with the face culling.
        gl.glCullFace(GL10.GL_BACK);

        // Enabled the vertices buffer for writing and to be used during
        // rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Specifies the location and data format of an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glPointSize(6.0f);
        gl.glDrawElements(GL10.GL_POINTS, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);

        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // Disable face culling.
        gl.glDisable(GL10.GL_CULL_FACE);

        // Dibuja las perpendiculares de los ejes ortogonales al punto.
        this.dibujaPerpenciulares(gl, puntos.getX(), puntos.getY(), puntos.getZ());
    }

    public void dibujaPerpenciulares(GL10 gl, float x, float y, float z){
        float perpendiculares[] = {
                x, 0f, 0f,
                x, 0f, z,
                0f, 0f, z,
                x, y ,z
        };

        short indicesPerp[] = {
                0,1,2,1,1,3
        };

        ByteBuffer vbb = ByteBuffer.allocateDirect(perpendiculares.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(perpendiculares);
        vertexBuffer.position(0);

        ByteBuffer ibb = ByteBuffer.allocateDirect(indicesPerp.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indicesPerp);
        indexBuffer.position(0);

        gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
        // Counter-clockwise winding.
        gl.glFrontFace(GL10.GL_CCW);
        // Enable face culling.
        gl.glEnable(GL10.GL_CULL_FACE);
        // What faces to remove with the face culling.
        gl.glCullFace(GL10.GL_BACK);

        // Enabled the vertices buffer for writing and to be used during
        // rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Specifies the location and data format of an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glPointSize(5.0f);
        gl.glDrawElements(GL10.GL_LINES, indicesPerp.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);

        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // Disable face culling.
        gl.glDisable(GL10.GL_CULL_FACE);
    }
}
