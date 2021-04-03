import processing.core.PShape;

import java.awt.*;
import java.util.ArrayList;

public class SubChunk {

    Chunk parent;
    int index;

    ArrayList<PShape> meshes;

    public SubChunk(Chunk parent, int index) {
        this.parent = parent;
        this.index = index;

        meshes = new ArrayList<>();
    }

    public void draw(){
        for(PShape s:meshes){
            parent.app.shape(s);
        }
    }

    public void genMeshes(){

        for(int z=0;z<16;z++){
            boolean[][] fMask = new boolean[16][16];
            for(int x=0;x<16;x++){
                for(int y=0;y<16;y++){

                    fMask[x][y] = true;
                    //  if this block isn't air
                    if(!parent.blocks[x][y+index*16][z].type.equals("air")){

                        //  if chunk border
                        if(z==0){
                            fMask[x][y] = false;
                        }
                        //  if block in front of this block is air
                        else if(parent.blocks[x][y+index*16][z-1].type.equals("air")){
                            fMask[x][y] = false;
                        }
                    }
                }
            }

            ArrayList<Rectangle> fMesh = maskToMesh(fMask);
            for(Rectangle r:fMesh){
                PShape pShape = parent.app.createShape();
                pShape.beginShape();
                pShape.noStroke();
                pShape.texture(parent.textureManager.front);

                pShape.vertex(r.x*100,r.y*100+index*1600,z*100,0,0);
                pShape.vertex((r.x+r.width)*100,r.y*100+index*1600,z*100,r.width*384,0);
                pShape.vertex((r.x+r.width)*100,(r.y+r.height)*100+index*1600,z*100,r.width*384,r.height*384);
                pShape.vertex((r.x)*100,(r.y+r.height)*100+index*1600,z*100,0,r.height*384);

                pShape.endShape();
                meshes.add(pShape);
            }
        }

    }

    public ArrayList<Rectangle> maskToMesh(boolean[][] mask){

        ArrayList<Rectangle> meshList = new ArrayList<>();

        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){

                //  if this is marked
                if(mask[x][y]){
                    continue;
                }

                //  find width of rectangle that fits
                int startX = x;
                int endX = startX;
                for(;endX<16&&!mask[endX][y];endX++);
                endX--;

                //  find height of rectangle that fits
                int startY = y;
                int endY = startY;
                boolean clear = true;
                for(;endY<16&&clear;endY++){
                    for(int i=startX;i<=endX;i++) {
                        if (mask[i][endY]) {
                            clear = false;
                            endY--;
                            break;
                        }
                    }
                }
                endY--;

                //  find rectangle from points
                Rectangle r = new Rectangle(startX,startY,(endX-startX)+1,(endY-startY)+1);
                meshList.add(r);

                //  mark cells as visited
                for(int i=startX;i<=endX;i++){
                    for(int a=startY;a<=endY;a++){
                        mask[i][a] = true;
                    }
                }
            }
        }
        return meshList;
    }
}
