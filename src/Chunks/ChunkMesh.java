package Chunks;

import Cube.Block;
import Cube.Vertex;
import Models.CubeModel;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ChunkMesh {

    public float[] positions, uvs, normals;
    public Chunk chunk;
    private List<Vertex> vertices;
    private List<Float> positionsList;
    private List<Float> normalsList;
    private List<Float> uvsList;

    public ChunkMesh(Chunk chunk) {
        this.chunk = chunk;

        vertices = new ArrayList<>();
        positionsList = new ArrayList<>();
        normalsList = new ArrayList<>();
        uvsList = new ArrayList<>();

        buildMesh();
        populateLists();
    }

    public void update(Chunk chunk) {

        this.chunk = chunk;

        buildMesh();
        populateLists();

    }

    private void buildMesh() {
        //Loop through blocks in chunk and determine if chunk faces are visible

        for(Block block1 : chunk.blocks) {
            boolean px = false, nx = false, py = false, ny = false, pz = false, nz = false;

            for(Block block2 :chunk.blocks) {
                //PX
                if (((block1.x + 1) == (block2.x)) && ((block1.y) == (block2.y)) && ((block1.z) == (block2.z))) {
                    px = true;
                }

                //NX
                if (((block1.x - 1) == (block2.x)) && ((block1.y) == (block2.y)) && ((block1.z) == (block2.z))) {
                    nx = true;
                }

                //PY
                if (((block1.x) == (block2.x)) && ((block1.y + 1) == (block2.y)) && ((block1.z) == (block2.z))) {
                    py = true;
                }

                //NY
                if (((block1.x) == (block2.x)) && ((block1.y - 1) == (block2.y)) && ((block1.z) == (block2.z))) {
                    ny = true;
                }

                //PZ
                if (((block1.x) == (block2.x)) && ((block1.y) == (block2.y)) && ((block1.z + 1) == (block2.z))) {
                    pz = true;
                }

                //NZ
                if (((block1.x ) == (block2.x)) && ((block1.y) == (block2.y)) && ((block1.z - 1) == (block2.z))) {
                    nz = true;
                }
            }

            // Add visible faces to chunk mesh
            if (!px) {
                for (int i = 0; i < 6; i++) {
                    vertices.add(new Vertex(new Vector3f(CubeModel.PX_POS[i].x + block1.x, CubeModel.PX_POS[i].y + block1.y, CubeModel.PX_POS[i].z + block1.z), CubeModel.NORMALS[i], CubeModel.UV[i]));
                }
            }

            if (!nx) {
                for (int i = 0; i < 6; i++) {
                    vertices.add(new Vertex(new Vector3f(CubeModel.NX_POS[i].x + block1.x, CubeModel.NX_POS[i].y + block1.y, CubeModel.NX_POS[i].z + block1.z), CubeModel.NORMALS[i], CubeModel.UV[i]));
                }
            }

            if (!py) {
                for (int i = 0; i < 6; i++) {
                    vertices.add(new Vertex(new Vector3f(CubeModel.PY_POS[i].x + block1.x, CubeModel.PY_POS[i].y + block1.y, CubeModel.PY_POS[i].z + block1.z), CubeModel.NORMALS[i], CubeModel.UV[i]));
                }
            }

            if (!ny) {
                for (int i = 0; i < 6; i++) {
                    vertices.add(new Vertex(new Vector3f(CubeModel.NY_POS[i].x + block1.x, CubeModel.NY_POS[i].y + block1.y, CubeModel.NY_POS[i].z + block1.z), CubeModel.NORMALS[i], CubeModel.UV[i]));
                }
            }

            if (!pz) {
                for (int i = 0; i < 6; i++) {
                    vertices.add(new Vertex(new Vector3f(CubeModel.PZ_POS[i].x + block1.x, CubeModel.PZ_POS[i].y + block1.y, CubeModel.PZ_POS[i].z + block1.z), CubeModel.NORMALS[i], CubeModel.UV[i]));
                }
            }

            if (!nz) {
                for (int i = 0; i < 6; i++) {
                    vertices.add(new Vertex(new Vector3f(CubeModel.NZ_POS[i].x + block1.x, CubeModel.NZ_POS[i].y + block1.y, CubeModel.NZ_POS[i].z + block1.z), CubeModel.NORMALS[i], CubeModel.UV[i]));
                }
            }
        }
    }

    private void populateLists() {
        for(Vertex vertex : vertices) {
            positionsList.add(vertex.positions.x);
            positionsList.add(vertex.positions.y);
            positionsList.add(vertex.positions.z);

            uvsList.add(vertex.uvs.x);
            uvsList.add(vertex.uvs.y);

            normalsList.add(vertex.normals.x);
            normalsList.add(vertex.normals.y);
            normalsList.add(vertex.normals.z);
        }

        positions = new float[positionsList.size()];
        normals = new float[normalsList.size()];
        uvs = new float[uvsList.size()];

        for(int i = 0; i < positionsList.size(); i++)
            positions[i] = positionsList.get(i);

        for(int i = 0; i < uvsList.size(); i++)
            uvs[i] = uvsList.get(i);

        for(int i = 0; i < normalsList.size(); i++)
            normals[i] = normalsList.get(i);

        positionsList.clear();
        uvsList.clear();
        normalsList.clear();
    }
}
