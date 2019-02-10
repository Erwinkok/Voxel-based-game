package Entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position;
    private float xRotation;
    private float yRotation;
    private float zRotation;

    private float walkSpeed = 0.1f;
    private float turnSpeed = 0.5f;
    private float moveAt = 0;

    public Camera(Vector3f position, float xRotation, float yRotation, float zRotation) {
        this.position = position;
        this.xRotation = xRotation;
        this.yRotation = yRotation;
        this.zRotation = zRotation;
    }

    public void move() {
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
            moveAt = -walkSpeed;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
            moveAt = walkSpeed;
        } else {
            moveAt = 0;
        }

        xRotation += -Mouse.getDY() * turnSpeed;
        yRotation += Mouse.getDX() * turnSpeed;

        float dx = (float) -(moveAt * Math.sin(Math.toRadians(yRotation)));
        float dy = (float) (moveAt * Math.sin(Math.toRadians(xRotation)));;
        float dz = (float) (moveAt * Math.cos(Math.toRadians(yRotation)));;

        position.x += dx;
        position.y += dy;
        position.z += dz;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getxRotation() {
        return xRotation;
    }

    public float getyRotation() {
        return yRotation;
    }

    public float getzRotation() {
        return zRotation;
    }
}
