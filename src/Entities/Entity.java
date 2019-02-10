package Entities;

import Models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Entity {
    TexturedModel model;
    Vector3f position;
    float xRotation, yRotation, zRotation;
    float scale;

    public Entity(TexturedModel model, Vector3f position, float xRotation, float yRotation, float zRotation, float scale) {
        this.model = model;
        this.position = position;
        this.xRotation = xRotation;
        this.yRotation = yRotation;
        this.zRotation = zRotation;
        this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.xRotation += dx;
        this.yRotation += dy;
        this.zRotation += dz;
    }

    public void increaseScale(float scale) {
       this.scale += scale;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getxRotation() {
        return xRotation;
    }

    public void setxRotation(float xRotation) {
        this.xRotation = xRotation;
    }

    public float getyRotation() {
        return yRotation;
    }

    public void setyRotation(float yRotation) {
        this.yRotation = yRotation;
    }

    public float getzRotation() {
        return zRotation;
    }

    public void setzRotation(float zRotation) {
        this.zRotation = zRotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
