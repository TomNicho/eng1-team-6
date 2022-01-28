package main.game.world.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import main.game.world.content.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;



public class Player extends Entity {

    private Sprite sprite;
    private int _health;
    private float _speed;
    private Vector2 position = new Vector2(0,0);
    private Texture boat;
    private TextureRegion boatregion;
    private float rotation = 0;
    
    public int GetHealth(){
        return _health;
    }
    public float GetSpeed(){
        return _speed;
    }
    public Sprite GetSprite(){
        return sprite;
    }
    public Vector2 GetPosition(){
        return position;
    }
    public Texture GetTexture(){
        return boat;
    }
    public TextureRegion GetTextureRegion(){
        return boatregion;
    }
    public float GetRotation(){
        return rotation;
    }

    public void TakeDamage(){}
    public void Shoot(){}

    public void RotateBy(float deg){
        rotation += (deg / (2 * Math.PI));
    }
    public Player(int health, float speed){
        boat = new Texture(Gdx.files.internal("core/assets/textures/boat.png"));
        boatregion = new TextureRegion(boat);
        sprite = new Sprite(boat, 32, 64);
        _health = health;
        _speed = speed;
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        position = new Vector2(0,0);
    }

    @Override
    public void dispose() {
        
    }
}
