import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;

public class Mesher extends PApplet {

    //  mask board
    int maskW,maskH;
    int[][] mask;

    int screen;

    //  meshes
    ArrayList<Rectangle> meshes;

    public static void main(String[] args) {
        PApplet.main("Mesher");
    }

    public void settings(){
        size(800,800);

    }

    public void setup(){
        screen = 0;

        //  set up mask
        maskW = 10;
        maskH = 10;
        mask = new int[maskW][maskH];
        for(int x=0;x<maskW;x++){
            for(int y=0;y<maskH;y++){
                mask[x][y] = 0;
            }
        }

        this.meshes = new ArrayList<>();
    }

    public void draw(){

        if(screen==0){
            drawMask();
        }
        else{
            drawMesh();
        }
    }

    public void drawMask(){

        background(0);
        float cellWidth = width/(float)maskW;
        float cellHeight = height/(float)maskH;
        for(int x=0;x<maskW;x++){
            for(int y=0;y<maskH;y++){

                if(mask[x][y] == 1){
                    fill(0,255,0);
                }else{
                    fill(255,0,0);
                }

                rect(x*cellWidth,y*cellHeight,cellWidth,cellHeight);
            }
        }
    }

    public void drawMesh(){

        background(0);
        float cellWidth = width/(float)maskW;
        float cellHeight = height/(float)maskH;
        for(int i=0;i<meshes.size();i++){
            Rectangle mesh = meshes.get(i);
            fill(255,0,0);
            rect(mesh.x*cellWidth,mesh.y*cellHeight,mesh.width*cellWidth,mesh.height*cellHeight);
        }
    }

    public void genMesh(){

        //  clear meshes
        meshes.clear();

        //  create clone of mesh
        boolean[][] maskClone = new boolean[maskW][maskH];
        for(int x=0;x<maskW;x++) {
            for (int y = 0; y < maskH; y++) {
                maskClone[x][y] = mask[x][y]==0;
            }
        }


        for(int y=0;y<maskH;y++) {
            for (int x = 0; x < maskW; x++) {

                if(!maskClone[x][y]){
                    continue;
                }

                //  find length of rectangle
                int u=x,v=y,w=1,h=1;
                for(;u+w<maskW;w++){
                    if(!maskClone[u+w][v]){
                        break;
                    }
                }

                //  find height of rectangle
                for(;v+h<maskH;h++) {

                    boolean clear = true;
                    for(int a=u;a<u+w;a++){
                        if(!maskClone[a][v+h]){
                            clear = false;
                        }
                    }

                    if(!clear){
                        break;
                    }
                }

                //  add mesh
                meshes.add(new Rectangle(u,v,w,h));
                //System.out.println(u+" "+v+" "+w+" "+h);

                for(int a=u;a<u+w;a++) {
                    for (int b = v; b < v+h; b++) {
                        maskClone[a][b] = false;
                    }
                }
            }
        }
        System.out.println(meshes.size());
    }

    public void mouseDragged(){

        try {
            if (mousePressed) {
                if (mouseButton == LEFT) {
                    int x = (int) ((mouseX / (float) width) * maskW);
                    int y = (int) ((mouseY / (float) height) * maskH);
                    mask[x][y] = 1;
                }
                if (mouseButton == RIGHT) {
                    int x = (int) ((mouseX / (float) width) * maskW);
                    int y = (int) ((mouseY / (float) height) * maskH);
                    mask[x][y] = 0;
                }
            }
        }catch (Exception e){}
    }

    public void keyPressed(){
        if(key == 's'){
            screen = screen==1?0:1;
            genMesh();
        }
    }
}
