package Game;

import Entities.Camera;
import Entities.Entity;
import Models.AtlasCubeModel;
import Models.RawModel;
import Models.TexturedModel;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.MasterRenderer;
import Shaders.StaticShader;
import Textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainGameLoop {

    public static Loader loader1 = null;
    public static StaticShader shader1 = null;

    private static List<Chunk> chunks = Collections.synchronizedList(new ArrayList<>());
    private static Vector3f cameraPosition = new Vector3f(0,0,0);
    private static List<Vector3f> usedPositions = new ArrayList<>();

    private static final int WORLD_SIZE = 5 * 16;

    public static void main(String[] args) {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        loader1 = loader;
        StaticShader shader = new StaticShader();
        shader1 = shader;

        MasterRenderer renderer = new MasterRenderer();
        Camera camera = new Camera(new Vector3f(0, 0, 0), 0,0 ,0);

        RawModel model = loader.loadToVAO(AtlasCubeModel.vertices, AtlasCubeModel.indices, AtlasCubeModel.uvs);
        ModelTexture texture = new ModelTexture(loader.loadTexture("grassTex"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Display.isCloseRequested()) {
                    for(int x = (int) (cameraPosition.x - WORLD_SIZE) / 16; x < (int) (cameraPosition.x + WORLD_SIZE) / 16; x++){
                        for (int z = (int) (cameraPosition.z - WORLD_SIZE) / 16; z < (int) (cameraPosition.z + WORLD_SIZE) / 16; z++) {
                            Vector3f thisPosition = new Vector3f(x * 16, 0 * 16, z * 16);

                            if(!usedPositions.contains(thisPosition)) {
                                List<Entity> blocks = new ArrayList<>();

                                for(int i = 0; i < 16; i++) {
                                    for (int j = 0; j < 16; j++){
                                        blocks.add(new Entity(texturedModel, new Vector3f((x * 16) + i, 0, (z * 16) + j), 0, 0,0,1));
                                    }
                                }
                                chunks.add(new Chunk(blocks, thisPosition));
                                usedPositions.add(thisPosition);
                            }
                        }
                    }
                }
            }
        }).start();

        while (!Display.isCloseRequested()) {
            camera.move();
            cameraPosition = camera.getPosition();

            for (int i = 0; i < chunks.size(); i++){

                Vector3f origin = chunks.get(i).getOrigin();

                int distX = (int) (cameraPosition.x - origin.x);
                int distZ = (int) (cameraPosition.z - origin.z);

                if (distX < 0) {
                    distX = - distX;
                }

                if (distZ < 0) {
                    distZ = -distZ;
                }

                if((distX <= WORLD_SIZE) && (distZ <= WORLD_SIZE)){
                    for (int j = 0; j < chunks.get(i).getBlocks().size(); j++){
                        renderer.addEntity(chunks.get(i).getBlocks().get(j));
                    }
                }
            }

            renderer.render(camera);
            DisplayManager.updateDisplay();
        }
        DisplayManager.closeDisplay();
    }
}
