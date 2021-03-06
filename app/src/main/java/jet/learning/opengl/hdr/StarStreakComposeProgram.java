package jet.learning.opengl.hdr;

import android.opengl.GLES20;

import com.nvidia.developer.opengl.utils.NvGLSLProgram;

import jet.learning.opengl.common.SimpleOpenGLProgram;

/**
 * Created by mazhen'gui on 2017/3/16.
 */

final class StarStreakComposeProgram extends SimpleOpenGLProgram{
    private int attribPos;
    private int attribTexcoord;

    public void init(){
        NvGLSLProgram program = NvGLSLProgram.createFromFiles("hdr_shaders/starStreakCompose.vert",
                "hdr_shaders/starStreakCompose.frag");

        program.enable();
        program.setUniform1i("sampler1", 0);
        program.setUniform1i("sampler2", 1);
        program.setUniform1i("sampler3", 2);
        program.setUniform1i("sampler4", 3);
        program.disable();

        attribPos = program.getAttribLocation("PosAttribute");
        attribTexcoord = program.getAttribLocation("TexAttribute");

        programID = program.getProgram();
    }

    @Override
    public final int getAttribPosition() { return attribPos;}
    @Override
    public final int getAttribTexCoord() { return attribTexcoord;}

    public final int getSampler1Unit()   { return 0+ GLES20.GL_TEXTURE0;}
    public final int getSampler2Unit()   { return 1+GLES20.GL_TEXTURE0;}
    public final int getSampler3Unit()   { return 2+GLES20.GL_TEXTURE0;}
    public final int getSampler4Unit()   { return 3+GLES20.GL_TEXTURE0;}
}
