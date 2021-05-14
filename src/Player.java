import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import queasycam.QueasyCam;

import java.lang.reflect.Field;

public class Player extends QueasyCam {

    PApplet app;
    PVector center;
    World world;

    int curX,curY,curZ;

    public Player(PApplet app,World world){
        super(app);
        super.draw();

        this.world = world;
        this.app = app;
        app.perspective(app.PI/3,app.width/(float)app.height,0.01f,100000);

        curX = -1;
        curY = -1;
        curZ = -1;
    }

    public void draw(){
        //  cast our ray
        castRay();

        //  draw our cursor
        if(curX!=-1){
            app.pushMatrix();
            app.translate((100*(curX/100)+52),100*(curY/100)+52,100*(curZ/100)+52);
            app.stroke(255,0,0);
            app.noFill();
            app.strokeWeight(10);
            app.box(104);
            app.popMatrix();
            app.pushMatrix();
            app.translate(position.x+(center.x-position.x)*1,position.y+(center.y-position.y)*1,position.z+(center.z-position.z)*1);
            app.noStroke();
            app.fill(0,0,255);
            app.hint(PConstants.DISABLE_DEPTH_TEST);
            app.sphere(.005f);
            app.popMatrix();
        }

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

        app.text("Pos: "+this.position.x+" "+this.position.y+" "+this.position.z,0,200);
        app.text("Pos: "+this.center.x+" "+this.center.y+" "+this.center.z,0,250);
        //  stop drawing HUD
        app.hint(PConstants.ENABLE_DEPTH_TEST);
        app.popMatrix();
        super.draw();
    }

    public void castRay(){

        //  get access to illegal variable
        try{

            Field field = getClass().getSuperclass().getDeclaredField("center");
            field.setAccessible(true);
            center = (PVector)field.get(this);

        }catch (Exception e){}

        boolean tmp = true;
        int tselX = (int)(position.x);//+(center.x-position.x));
        int tselY= (int)(position.y);//+(center.y-position.y));
        int tselZ = (int)(position.z);//+(center.z-position.z));
        for(int i=9;i<40000;i++){

            //  if type of block is grass
            if(world.getBlock(new PVector(tselX/100,tselY/100,tselZ/100)).equals("grass")){
                //  mark location of cursor
                curX = tselX;
                curY = tselY;
                curZ = tselZ;
                //  break
                tmp = false;
                break;
            }

            //  increment look position

            tselX = (int)(position.x+(center.x-position.x)/8*i);
            tselY= (int)(position.y+(center.y-position.y)/8*i);
            tselZ = (int)(position.z+(center.z-position.z)/8*i);
        }
        if(tmp){
            curX = -1;
            curY = -1;
            curZ = -1;
        }
    }
}
