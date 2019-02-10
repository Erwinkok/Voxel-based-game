package Game;

import Chunks.Chunk;
import Chunks.ChunkMesh;
import Cube.Block;
import Entities.Camera;
import Entities.Entity;
import Models.CubeModel;
import Models.RawModel;
import Models.TexturedModel;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.MasterRenderer;
import Shaders.StaticShader;
import Textures.ModelTexture;
import Toolbox.PerlinNoiseGenerator;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainGameLoop {

    public static Loader loader1 = null;
    public static StaticShader shader1 = null;

    private static final int CHUNK_SIZE = 16;
    private static Vector3f cameraPosition = new Vector3f(0,0,0);
    private static final int WORLD_SIZE = 5 * CHUNK_SIZE;
    private static List<ChunkMesh> chunks = Collections.synchronizedList(new ArrayList<>());
    private static List<Vector3f> usedPositions = Collections.synchronizedList(new ArrayList<>());
    private static List<Entity> entities = new ArrayList<>();

    public static void main(String[] args) {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        loader1 = loader;
        StaticShader shader = new StaticShader();
        shader1 = shader;

        MasterRenderer renderer = new MasterRenderer();
        Camera camera = new Camera(new Vector3f(0, 0, 0), 0,0 ,0);

        RawModel model = loader.loadToVAO(CubeModel.vertices, CubeModel.indices, CubeModel.uvs);
        ModelTexture texture = new ModelTexture(loader.loadTexture("dirtTex"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        PerlinNoiseGenerator generator = new PerlinNoiseGenerator();

        new Thread(() -> {
            while(!Display.isCloseRequested()) {
                for(int x = (int) (cameraPosition.x - WORLD_SIZE) / CHUNK_SIZE; x < (cameraPosition.x + WORLD_SIZE) / CHUNK_SIZE; x++){
                    for (int z = (int) (cameraPosition.z - WORLD_SIZE) / CHUNK_SIZE; z < (cameraPosition.z + WORLD_SIZE) / CHUNK_SIZE; z++) {
                        Vector3f thisPosition = new Vector3f(x * CHUNK_SIZE, 0 * CHUNK_SIZE, z * CHUNK_SIZE);

                        if(!usedPositions.contains(thisPosition)) {
                            List<Block> blocks = new ArrayList<>();

                            for(int i = 0; i < CHUNK_SIZE; i++) {
                                for (int j = 0; j < CHUNK_SIZE; j++){
                                    blocks.add(new Block(i, (int) generator.generateHeight(i + (x * CHUNK_SIZE), j + (z * CHUNK_SIZE)), j, Block.TYPE.DIRT));
                                }
                            }
                            Chunk chunk = new Chunk(blocks, thisPosition);
                            ChunkMesh mesh = new ChunkMesh(chunk);

                            chunks.add(mesh);
                            usedPositions.add(thisPosition);
                        }
                    }
                }
            }
        }).start();

//        new Thread(() -> {
//            while(!Display.isCloseRequested()) {
//                for(int x = (int) (cameraPosition.x - WORLD_SIZE) / CHUNK_SIZE; x < (cameraPosition.x + WORLD_SIZE) / CHUNK_SIZE; x++){
//                    for (int z = (int) (cameraPosition.z) / CHUNK_SIZE; z < (cameraPosition.z + WORLD_SIZE) / CHUNK_SIZE; z++) {
//                        Vector3f thisPosition = new Vector3f(x * CHUNK_SIZE, 0 * CHUNK_SIZE, z * CHUNK_SIZE);
//
//                        if(!usedPositions.contains(thisPosition)) {
//                            List<Block> blocks = new ArrayList<>();
//
//                            for(int i = 0; i < CHUNK_SIZE; i++) {
//                                for (int j = 0; j < CHUNK_SIZE; j++){
//                                    blocks.add(new Block(i, (int) generator.generateHeight(i + (x * CHUNK_SIZE), j + (z * CHUNK_SIZE)), j, Block.TYPE.DIRT));
//                                }
//                            }
//                            Chunk chunk = new Chunk(blocks, thisPosition);
//                            ChunkMesh mesh = new ChunkMesh(chunk);
//
//                            chunks.add(mesh);
//                            usedPositions.add(thisPosition);
//                        }
//                    }
//                }
//            }
//        }).start();

        /*List<Block> blocks = new ArrayList<>();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                for (int z = 0; z < 10; z++) {
                    blocks.add(new Block(x, y, z, Block.TYPE.DIRT));
                }
            }
        }

        Chunk chunk = new Chunk(blocks, new Vector3f(0,0,0));
        ChunkMesh mesh = new ChunkMesh(chunk);

        RawModel rawModel = loader.loadToVAO(mesh.positions, mesh.uvs);
        TexturedModel texModel = new TexturedModel(rawModel, texture);
        Entity entity = new Entity(texModel, new Vector3f(0, 0, 0), 0,0,0,1);*/

        // Main Game Loop
        int index = 0;
        while (!Display.isCloseRequested()) {
            camera.move();
            cameraPosition = camera.getPosition();

            if (index < chunks.size()) {
                RawModel rawModel = loader.loadToVAO(chunks.get(index).positions, chunks.get(index).uvs);
                TexturedModel texModel = new TexturedModel(rawModel, texture);
                Entity entity = new Entity(texModel, chunks.get(index).chunk.origin, 0,0,0,1);
                entities.add(entity);

                chunks.get(index).positions = null;
                chunks.get(index).normals = null;
                chunks.get(index).uvs = null;

                index++;
            }

            for (Entity entity : entities) {

                Vector3f origin = entity.getPosition();

                int distX = (int) (cameraPosition.x - origin.x);
                int distZ = (int) (cameraPosition.z - origin.z);

                if (distX < 0) {
                    distX = -distX;
                }

                if (distZ < 0) {
                    distZ = -distZ;
                }

                if ((distX <= WORLD_SIZE) && (distZ <= WORLD_SIZE)) {
                    renderer.addEntity(entity);
                }
            }

//            renderer.addEntity(entity);
//
            renderer.render(camera);
            DisplayManager.updateDisplay();
        }
        DisplayManager.closeDisplay();
    }
}
