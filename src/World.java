import processing.core.PApplet;
import processing.core.PVector;

public class World {

    //  our player
    Player player;

    //  the chunks of the world
    Chunk[][] chunks;
    int width;
    int length;

    //  draws stuff
    PApplet app;

    //  gets textures
    TextureManager textureManager;

    public World(PApplet app){

        this.app = app;

        //  set up our texture
        textureManager = new TextureManager(app);

        //set up our player
        player = new Player(app);

        //  set up chunks
        width = 2;
        length = 2;
        chunks = new Chunk[width][length];
        for(int x=0;x<width;x++){
            for(int y=0;y<length;y++){
                PVector pos = new PVector(x,y);
                chunks[x][y] = new Chunk(app,textureManager,pos);
            }
        }

    }

    public void draw(){
        for(int x=0;x<width;x++){
            for(int y=0;y<length;y++){

                app.pushMatrix();
                app.translate(x*1600,0,y*1600);

                chunks[x][y].draw();

                app.popMatrix();
            }
        }
    }
}
