import processing.core.PApplet;

public class Chunk {

    PApplet app;

    Block[][][] blocks;
    int width,height,depth;

    SubChunk[] subChunks;

    TextureManager textureManager;

    public Chunk(PApplet app){

        this.app = app;

        textureManager = new TextureManager(app);

        //  set up our blocks
        width = 16;
        height = 256;
        depth = 16;
        blocks = new Block[width][height][depth];
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                for(int z=0;z<depth;z++){
                    String type = app.noise(x*.08f,y*.08f,z*.08f)>.5?"grass":"air";
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

        for(SubChunk s:subChunks){
            s.draw();
        }

        if(true)return;

        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                for(int z=0;z<depth;z++){

                    //  if this block is air, skip it
                    if(blocks[x][y][z].type.equals("air")){
                        continue;
                    }
                    app.pushMatrix();
                    app.translate(x*100,y*100,z*100);

                    //  draw left face
                    if(x==0){
                        blocks[x][y][z].drawLeft();
                    }
                    else if(blocks[x-1][y][z].type.equals("air")){
                        blocks[x][y][z].drawLeft();
                    }

                    //  draw right face
                    if(x==width-1){
                        blocks[x][y][z].drawRight();
                    }
                    else if(blocks[x+1][y][z].type.equals("air")){
                        blocks[x][y][z].drawRight();
                    }

                    //  draw top face
                    if(y==0){
                        blocks[x][y][z].drawTop();
                    }
                    else if(blocks[x][y-1][z].type.equals("air")){
                        blocks[x][y][z].drawTop();
                    }

                    //  draw bottom face
                    if(y==height-1){
                        blocks[x][y][z].drawBottom();
                    }
                    else if(blocks[x][y+1][z].type.equals("air")){
                        blocks[x][y][z].drawBottom();
                    }

                    //  draw front face
                    if(z==0){
                        blocks[x][y][z].drawFront();
                    }
                    else if(blocks[x][y][z-1].type.equals("air")){
                        blocks[x][y][z].drawFront();
                    }

                    //  draw back face
                    if(z==depth-1){
                        blocks[x][y][z].drawBack();
                    }
                    else if(blocks[x][y][z+1].type.equals("air")){
                        blocks[x][y][z].drawBack();
                    }

                    app.popMatrix();
                }
            }
        }
    }
}
