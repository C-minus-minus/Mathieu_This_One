import processing.core.PApplet;

public class VoxelGame extends PApplet {

    Player player;
    Chunk chunk;

    public static void main(String[] args) {
        PApplet.main("VoxelGame");
    }

    public void settings(){
        size(1000,800,P3D);
    }

    public void setup(){

        player = new Player(this);
        chunk = new Chunk(this);
    }

    public void draw(){

        background(0);
        chunk.draw();
    }
}
