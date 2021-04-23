import javafx.scene.layout.Pane;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import queasycam.QueasyCam;

import java.lang.reflect.Field;

public class Player extends QueasyCam {

    PApplet app;
    PVector center;
    World world;

    public Player(PApplet app,World world){
        super(app);

        this.world = world;
        this.app = app;
        app.perspective(app.PI/3,app.width/(float)app.height,0.01f,100000);
    }

    public void draw(){
        super.draw();

        //  begin drawing our HUD
        app.pushMatrix();
        app.camera();
        app.hint(PConstants.DISABLE_DEPTH_TEST);

        //  draw fps on screen
        app.textSize(50);
        app.fill(255,0,0);
        app.text("FPS: "+(int)app.frameRate,0,50);

        //  draw mem on screen
        long heapSize = Runtime.getRuntime().totalMemory()/1000000;
        app.text("Mem: "+(int)heapSize+"MB",0,100);

        //  stop drawing HUD
        app.hint(PConstants.ENABLE_DEPTH_TEST);
        app.popMatrix();
    }

    public void castRay(){

        //  get access to illegal variable
        try{

            Field field = getClass().getSuperclass().getDeclaredField("center");
            field.setAccessible(true);
            center = (PVector)field.get(this);

        }catch (Exception e){}

        int tselX = (int)(position.x+(center.x-position.x));
        int tselY= (int)(position.y+(center.y-position.y));
        int tselZ = (int)(position.z+(center.z-position.z));
        for(int i=2;i<5000;i++){

            //  if type of block isn't air
                //  draw cursor on block
                //  break

            //  increment look position
            tselX = (int)(position.x+(center.x-position.x)*i);
            tselY= (int)(position.y+(center.y-position.y)*i);
            tselZ = (int)(position.z+(center.z-position.z)*i);
        }
    }
}
