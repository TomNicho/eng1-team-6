package yorkpirates.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

enum BarrelType{
    GOLD,
    BROWN
}
public class Barrel extends Obstacle{

    public int damage;
    public BarrelType type;
    public Barrel(Array<Texture> frames, float fps, float x, float y, float width, float height, String team, int damage, BarrelType type) {
        super(frames, fps, x, y, width, height, team,damage);
        this.type = type;
        if(type == BarrelType.BROWN){
            if(YorkPirates.difficulty=="easy"){
                this.damage = 20;
            } else if(YorkPirates.difficulty=="medium"){
                this.damage = 40;
            } else if(YorkPirates.difficulty=="hard"){
                this.damage = 60;
            } 
        }else{
            this.damage = 0;
        }
        
    }
    
}
