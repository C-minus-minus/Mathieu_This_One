import processing.core.PApplet;
import processing.core.PVector;

public class Chunk {

    PApplet app;

    World world;

    Block[][][] blocks;
    int width,height,depth;

    SubChunk[] subChunks;

    TextureManager textureManager;
    PVector pos;

    int renderDistance = 60;

    public Chunk(PApplet app,TextureManager textureManager,PVector pos,World world){

        this.app = app;
        this.pos = pos;
        this.textureManager = textureManager;
        this.world = world;

        //  set up our blocks
        width = 16;
        height = 256;
        depth = 16;
        blocks = new Block[width][height][depth];
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                for(int z=0;z<depth;z++){
                    String type = app.noise((x+pos.x)*.08f,y*.08f,(z+pos.y)*.08f)>.5?"grass":"air";
                    blocks[x][y][z] = new Block(app,textureManager,type);
                }
            }
        }

        subChunks = new SubChunk[16];
        for(int i=0;i<16;i++){
            subChunks[i] = new SubChunk(this,i);
        }
        for(int i=0;i<16;i++){
            subChunks[i].genMeshes();
        }
    }

    public void draw(){

        PVector p = world.player.position;
        PVector newPlayer = new PVector(p.x/100,p.y/100,p.z/100);
        if(distance(newPlayer)<renderDistance){
            for(SubChunk s:subChunks){
                s.draw();
            }
        }
    }

    public double distance(PVector playerPos){
        double x1 = pos.x;
        double y1 = pos.y;
        double x2 = playerPos.x;
        double y2 = playerPos.z;
        return Math.sqrt( Math.pow(x1-x2,2) + Math.pow(y1-y2,2) );
    }

    public void setBlock(int x,int y, int z,String type){
        try{

            Block block = blocks[x][y][z];
            block.type = type;
            subChunks[y/16].genMeshes();
        }catch (Exception e){}
    }
}
