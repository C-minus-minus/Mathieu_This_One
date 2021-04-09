import javafx.scene.layout.Pane;
import processing.core.PApplet;
import processing.core.PConstants;
import queasycam.QueasyCam;

public class Player extends QueasyCam {

    PApplet app;

    public Player(PApplet app){
        super(app);

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
}
