package Minecraft;

import RenderEngine.DisplayManager;
import RenderEngine.MasterRenderer;
import org.lwjgl.opengl.Display;

public class MainGameLoop {

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        MasterRenderer renderer = new MasterRenderer();

        while (!Display.isCloseRequested()) {

            renderer.prepare();

            DisplayManager.updateDisplay();

        }

        DisplayManager.closeDisplay();
    }
}
