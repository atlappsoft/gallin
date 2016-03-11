package efrain.org.mx.gallinp2;

import android.os.Bundle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by efrain on 16/02/16.
 */
public class graficaCuadrantes3D {
    private Bundle info;
    // Our vertices.
    private float vertices[] = {
            1.0f,  0.0f, 0.0f,  // Eje x
            0.0f, 1.0f, 0.0f,  // Eje y
            0.0f, 0.0f, 1.0f,  // Eje z
            0.0f,  0.0f, 0.0f,  // Origen
            -1.0f,  0.0f, 0.0f,  // Eje -x
            0.0f, -1.0f, 0.0f,  // Eje -y
            0.0f, 0.0f, -1.0f,  // Eje -z
    };
    // The order we like to connect them.
    private short[] indices = { 3, 0, 3, 1, 3, 2, 3, 4, 3, 5, 3, 6};
    // Our vertex buffer.
    private FloatBuffer vertexBuffer;
    // Our index buffer.
    private ShortBuffer indexBuffer;

    public graficaCuadrantes3D(Bundle i) {
        info = i;
        // a float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        // short is 2 bytes, therefore we multiply the number if
        // vertices with 2.
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    /**
     * Este metodo dibuja el espacio cartesiano de 3 dimensiones.
     * @param gl
     */
    public void draw(GL10 gl) {
        gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
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
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
                vertexBuffer);
        gl.glLineWidth(3.0f);
        gl.glDrawElements(GL10.GL_LINES, indices.length,
                GL10.GL_UNSIGNED_SHORT, indexBuffer);

        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // Disable face culling.
        gl.glDisable(GL10.GL_CULL_FACE);
    }
}