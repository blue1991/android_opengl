package jet.learning.opengl.samples;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

import com.nvidia.developer.opengl.app.NvSampleApp;
import com.nvidia.developer.opengl.utils.FieldControl;
import com.nvidia.developer.opengl.utils.GLES;
import com.nvidia.developer.opengl.utils.NvGLSLProgram;
import com.nvidia.developer.opengl.utils.NvImage;
import com.nvidia.developer.opengl.utils.NvLogger;

import javax.microedition.khronos.opengles.GL11;

/**
 * Created by mazhen'gui on 2016/12/21.
 */

public class Fisheye extends NvSampleApp {
    private int m_sourceTexture;
    private NvGLSLProgram m_Program;
    private float m_aspectRatio;
    private int m_DummyVAO;
    private boolean toonEnable = true;
    private float m_LastPointerX = 0.5f;

    private int texWidth, texHeight;
    private float m_Factor = 0.7f;

    @Override
    public void initUI() {
        if(mTweakBar != null)
            mTweakBar.addValue("Factor", new FieldControl(this, "m_Factor", FieldControl.CALL_FIELD), 0.0f, 1.0f, 0.02f, 0);
    }

    @Override
    protected void initRendering() {
        NvLogger.setLevel(NvLogger.INFO);
        m_Program = NvGLSLProgram.createFromFiles("shaders/Quad_VS.vert", "shaders/fisheye.frag");
        //load input texture
        NvImage m_sourceImage = NvImage.createFromDDSFile("textures/flower1024.dds");
        texWidth = m_sourceImage.getWidth();
        texHeight = m_sourceImage.getHeight();
        m_sourceTexture = m_sourceImage.updaloadTexture();
        GLES.checkGLError();

        GLES20.glBindTexture(GL11.GL_TEXTURE_2D, m_sourceTexture);
        GLES20.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GLES20.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GLES20.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        m_DummyVAO = GLES.glGenVertexArray();

        setTitle("Fisheye");

        Log.i("Fisheye", "initRendering done!");
    }

    @Override
    protected void reshape(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        m_aspectRatio = (float)height/(float)width;
        m_LastPointerX = width/2.0f;

        Log.i("Fisheye", "reshape done!");
    }

    @Override
    protected void draw() {
        GLES30.glBindVertexArray(m_DummyVAO);

//        uniform sampler2D iChannel0;
//        uniform vec2 iResolution;
//        uniform float factor;
//        uniform vec2 iMouse;
        m_Program.enable();
        m_Program.setUniform1i("iChannel0", 0);
        m_Program.setUniform2f("iResolution", getWidth(), getHeight());
        m_Program.setUniform1f("factor", m_Factor);

//        m_Program.setUniform1i("toon_enable", toonEnable?1:0);
//        m_Program.setUniform1f("edge_thres", 0.2f);
//        m_Program.setUniform1f("edge_thres2", 5.0f);

        if(isTouchDown(0)){
            m_LastPointerX = getTouchX(0);
        }
//        m_Program.setUniform1f("mouse_x_offset", m_LastPointerX/ getWidth());
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GL11.GL_TEXTURE_2D, m_sourceTexture);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        m_Program.disable();
        GLES30.glBindVertexArray(0);
    }
}
