package Toolbox;

import Entities.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Maths {

    public static Matrix4f createTransformationMatrix(Vector3f translation, float xRotation, float yRotation, float zRotation, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(xRotation), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(yRotation), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(zRotation), new Vector3f(0, 0, 1), matrix, matrix);
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);

        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        Matrix4f.rotate((float) Math.toRadians(camera.getxRotation()), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getyRotation()), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getzRotation()), new Vector3f(0, 0, 1), matrix, matrix);
        Matrix4f.translate(new Vector3f(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z), matrix, matrix);

        return matrix;
    }
}
