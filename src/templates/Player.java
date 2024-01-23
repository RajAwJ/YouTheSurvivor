/**
 @author: Awais Javed
 */

package templates;

import java.awt.image.BufferedImage;

import extra.ExtractSprite;
import main.Controller;
import util.Constants;
import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Player extends GameObject {
	
	private Controller cInstance = Controller.getInstance();
	
	private Vector3f velocity;
	private int speed;
	private char prevKey;
	private boolean isKeyActive;
	private boolean attack;
	private boolean spaceKey;
	
	private int ammoLeft;
	private int scorerTaken;
	private boolean speedPower;
	
	BufferedImage[] sprites;
	
	public Player() {
		
		this.setCentre( new Point3f( ( Constants.SCREEN_WIDTH / 2 ), 
				( Constants.SCREEN_HEIGHT / 2 ), 0.0f ) );
		this.setWidth( Constants.PLAYER_WIDTH );
		this.setHeight( Constants.PLAYER_HEIGHT);
		
		velocity = new Vector3f();
		this.setSpeed( 4 );
		this.setTexture( Constants.PlayerTexture );
		
		this.setPreviousKey('w');
		this.setVelocity( new Vector3f( 0.0f, (float) ( (- Constants.STEP) - this.getSpeed() ), 0.0f ) );
		this.keyActive( false );
		
		this.setHealth( Constants.PlayerHealth );
		this.setAmmo( Constants.PlayerAmmo );
		this.setScorerTaken( 0 );
		
		extractSprite();
	}
	
	public Player( Point3f centre, int width, int height ) {
		
		this.setCentre( centre );
		this.setWidth( width );
		this.setHeight( height );
		
		velocity = new Vector3f();
		this.setSpeed( 4 );
		this.setTexture( "res/Swordsman1.png" );
		
		this.setPreviousKey('w');
		this.setVelocity( new Vector3f( 0.0f, (float) ( (- Constants.STEP) - this.getSpeed() ), 0.0f ) );
		this.keyActive( false );
		
		this.setHealth( Constants.PlayerHealth );
		this.setAmmo( Constants.PlayerAmmo );
		this.setSpeedPower( false );
		
		extractSprite();
	}
	
	public void playerLogic() {

		if ( this.getCInstance().isKeyAPressed() ) {
			
			this.setVelocity( new Vector3f( (float) ( (- Constants.STEP) - this.getSpeed() ), 0.0f, 0.0f ) );
			this.getCentre().ApplyVector( velocity );
			
			this.keyActive( true );
			this.setPreviousKey('a');
		}
		
		else if ( this.getCInstance().isKeyDPressed() ) {
			
			this.setVelocity( new Vector3f( (float) ( (+ Constants.STEP) + this.getSpeed() ), 0.0f, 0.0f ) );
			this.getCentre().ApplyVector( velocity );
			
			this.keyActive( true );
			this.setPreviousKey('d');
		}
		
		else if ( this.getCInstance().isKeySPressed() ) {
			
			this.setVelocity( new Vector3f( 0.0f, (float) ( (+ Constants.STEP) + this.getSpeed() ), 0.0f ) );
			this.getCentre().ApplyVector( velocity );
			
			this.keyActive( true );
			this.setPreviousKey('s');
		}
		
		else if ( this.getCInstance().isKeyWPressed() ) {
			
			this.setVelocity( new Vector3f( 0.0f, (float) ( (- Constants.STEP) - this.getSpeed() ), 0.0f ) );
			this.getCentre().ApplyVector( velocity );
			
			this.keyActive( true );
			this.setPreviousKey('w');
		}

		else if ( this.getCInstance().isKeyQPressed() ) {

			this.keyActive( true );
			this.setSpeedPower( true );
		}
		
		else if ( this.getCInstance().isSpaceKeyPressed() ) {
			
			this.keyActive( true );
			this.setAttacking( true );
			this.setSpaceKey( true );
			this.setAmmo( this.getAmmo() - 1 );
		}
		
		else if ( ( !this.getCInstance().isKeyAPressed() ) && ( !this.getCInstance().isKeyDPressed() ) &&
				( !this.getCInstance().isKeySPressed() ) && ( !this.getCInstance().isKeyWPressed() ) &&
				( !this.getCInstance().isKeyQPressed() )) {
			
			this.keyActive( false );
		}

		if ( this.getColliding() ) {

			this.setScorerTaken( this.getScorerTaken() + 1 );
			this.setColliding( false );
		}
	}
	
	private void extractSprite() {

		ExtractSprite es = new ExtractSprite( 
				this.textureLocation(), Constants.PlayerTextureRows, 
				Constants.PlayerTextureColumns, Constants.PlayerSpritePixel);
		
		this.sprites = es.getSprites();
	}

	public void setScorerTaken( int s ) {

		if ( this.getSpeedPower() )
			this.setSpeedPower( false );

		this.scorerTaken = s;
	}
	public int getScorerTaken() {

		return this.scorerTaken;
	}
	
	public BufferedImage[] getSprites() {
		
		return sprites;
	}
	
	private void setVelocity( Vector3f v ) {
		
		velocity = v;
	}
	
	public Vector3f getVelocity() {
		
		return velocity;
	}
	
	public void setSpeed( int s ) {
		
		speed = s;
	}
	
	public int getSpeed() {
		
		return speed;
	}
	
	private void keyActive( boolean b ) {
		
		isKeyActive = b;
	}
	
	private boolean getKeyActive() {
		
		return isKeyActive;
	}
	
	public char previousKey() {
		
		return prevKey;
	}
	
	private void setPreviousKey( char k ) {
		
		prevKey = k;
	}
	
	public void setAttacking( boolean b ) {

		if ( b && this.getSpeedPower() )
			this.setSpeedPower( false );

		attack = b;
	}
	
	public boolean isAttacking() {
		
		return attack;
	}
	
	public void setSpaceKey( boolean b ) {
		
		spaceKey = b;
	}
	
	public boolean getSpaceKey() {
		
		return spaceKey;
	}
	
	public void setAmmo( int i ) {

		if ( i >= 0 )
			this.ammoLeft = i;
	}
	
	public int getAmmo() {
		
		return this.ammoLeft;
	}

	public void setSpeedPower( boolean b ) {

		if ( !this.getSpeedPower() && b && ( this.getScorerTaken() >= 4 ) ) {

			this.setScorerTaken( this.getScorerTaken() - 4 );
			this.speedPower = true;
			this.setSpeed( 20 );
		}
		else if ( !b ) {

			this.speedPower = false;
			this.setSpeed( 4 );
		}
	}

	public boolean getSpeedPower() {

		return this.speedPower;
	}
	
	public int getSpriteDirectionLine() {
		
		int line = 0;

		if ( this.getKeyActive() ) {
			
			if ( this.isAttacking() ) {

				switch ( this.previousKey() ) {
				
					case 'a': line = ( Constants.PlayerTextureColumns * 3 ); break;
					case 'w': line = ( Constants.PlayerTextureColumns * 2 ); break;
					case 'd': line = ( Constants.PlayerTextureColumns * 0 ); break;
					case 's': line = ( Constants.PlayerTextureColumns * 1 ); break;
				}
				this.setAttacking( false );
			}
			else {
			
				switch ( this.previousKey() ) {
				
					case 'a': line = ( Constants.PlayerTextureColumns * 9 ); break;
					case 'w': line = ( Constants.PlayerTextureColumns * 11 ); break;
					case 'd': line = ( Constants.PlayerTextureColumns * 10 ); break;
					case 's': line = ( Constants.PlayerTextureColumns * 8 ); break;
				}
			}
		}
		else {
			
			switch ( this.previousKey() ) {
			
				case 'a': line = ( Constants.PlayerTextureColumns * 7 ); break;
				case 'w': line = ( Constants.PlayerTextureColumns * 6 ); break;
				case 'd': line = ( Constants.PlayerTextureColumns * 4 ); break;
				case 's': line = ( Constants.PlayerTextureColumns * 5 ); break;
			}
		}
		
		return line;
	}
	
	public Controller getCInstance() {
		
		return cInstance;
	}
}