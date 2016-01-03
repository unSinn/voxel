package ch.ma3.voxel;

import android.os.Bundle;

import android.util.Log;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.*;
import ch.ma3.voxel.VoxelGame;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

public class AndroidLauncher extends CardBoardAndroidApplication implements CardBoardApplicationListener {

    public final String TAG = this.getClass().getSimpleName();

    private CardboardCamera cam;
    private VoxelGame game;

    private static final float Z_NEAR = 0.1f;
    private static final float Z_FAR = 1000.0f;
    private static final float CAMERA_Z = 0.01f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        game = new VoxelGame();

        cam = new CardboardCamera();
        cam.position.set(0f, 0f, CAMERA_Z);
        cam.lookAt(0, 0, 0);
        cam.near = Z_NEAR;
        cam.far = Z_FAR;

        game.cam = cam;

        initialize(this, config);

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    @Override
    public void onNewFrame(HeadTransform paramHeadTransform) {

    }

    @Override
    public void onDrawEye(Eye eye) {
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        cam.position.set(10f, 10f, 10f);

        // Apply the eye transformation to the camera.
        cam.setEyeViewAdjustMatrix(new Matrix4(eye.getEyeView()));

        float[] perspective = eye.getPerspective(Z_NEAR, Z_FAR);
        cam.setEyeProjection(new Matrix4(perspective));
        cam.update();

        game.render();
    }

    @Override
    public void onFinishFrame(Viewport paramViewport) {

    }

    @Override
    public void onRendererShutdown() {

    }

    @Override
    public void create() {
        game.create();
    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
    }

    @Override
    public void render() {
    }

    @Override
    public void pause() {
        game.pause();
    }

    @Override
    public void resume() {
        game.resume();
    }

    @Override
    public void dispose() {
        game.dispose();
    }
}
