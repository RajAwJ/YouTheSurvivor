/**
 @author: Awais Javed
 */

package templates;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import extra.ExtractSprite;
import extra.SeekTarget;
import util.Constants;
import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Seeker extends GameObject {
	
	private Vector3f velocity;
	private int speed;
	
	private Vector3f suggestedVector;
	BufferedImage[] sprites;
	
	private Point3f playerPos;
	private Rectangle playerBounds;

	public Seeker( String state ) {
		
		this.calculateOrigin();
		
		this.setWidth( Constants.Seeker_WIDTH );
		this.setHeight( Constants.Seeker_HEIGHT );
		this.setTexture( Constants.SeekerTexture );
		
		this.setHealth( Constants.SeekerInitialHealth );
		this.setDamageCaused( Constants.SeekerDamage );
		
		this.extractSprite();
		
		this.setVelocity( new Vector3f( 0.0f, 0.0f, 0.0f ) );

		if ( state.equals( "Practice" ) )
			this.setSpeed( ( 1 / 2 ) );
		else
			this.setSpeed( Constants.STEP );

	}
	
	private void calculateOrigin() {
		
		Random rand = new Random();
		
		int rx = rand.nextInt( Constants.SCREEN_WIDTH - 30 );
		int ry = rand.nextInt( Constants.SCREEN_HEIGHT - 30 );
		
		this.setCentre( new Point3f( (float) rx, (float) ry, 0.0f ) );
	}
	
	private void extractSprite() {
		
		ExtractSprite es = new ExtractSprite(
				this.textureLocation(), 
				Constants.SeekerTextureRows, 
				Constants.SeekerTextureColumns, 
				Constants.SeekerSpritePixel );
		
		this.sprites = es.getSprites();
	}
	
	public void seekerLogic() {
		
		SeekTarget sT = new SeekTarget( this.getCentre(), this.getBounds(),
				this.getPlayerPos(), this.getPlayerBounds() );
		
		if ( sT.getSuggestedVector() != null ) {

			this.setSuggestedVector(sT.getSuggestedVector());
			this.getCentre().ApplyVector( this.getVelocity() );
		}
	}

	public void setPlayerVariables( Point3f p, Rectangle b ) {

		this.playerPos = p;
		this.playerBounds = b;
	}
	
	private Point3f getPlayerPos() {
		
		return this.playerPos;
	}
	
	private Rectangle getPlayerBounds() {
		
		return this.playerBounds;
	}
	
	private void setSuggestedVector( Vector3f v ) {

		if ( this.getColliding() ) {

			if (this.getSuggestedVector().getX() == 0.0) {

				this.suggestedVector = (new Vector3f(v.getX(), 0.0f, 0.0f));

				this.setVelocity(
						(this.getSuggestedVector().getX() < 0) ?
								new Vector3f(this.suggestedVector.getX() - this.getSpeed(), 00f, 0.0f) :
								new Vector3f(this.suggestedVector.getX() + this.getSpeed(), 0.0f, 0.0f)
				);

			} else {

				this.suggestedVector = (new Vector3f(0.0f, v.getY(), 0.0f));

				this.setVelocity(
						(this.getSuggestedVector().getY() < 0) ?
								new Vector3f(0.0f, this.suggestedVector.getY() - this.getSpeed(), 0.0f) :
								new Vector3f(0.0f, this.suggestedVector.getY() + this.getSpeed(), 0.0f)
				);
			}
			this.setColliding( false );
		}
		else {

			if (Math.abs(v.getX()) > Math.abs(v.getY())) {

				this.suggestedVector = (new Vector3f(v.getX(), 0.0f, 0.0f));

				this.setVelocity(
						(this.getSuggestedVector().getX() < 0) ?
								new Vector3f(this.suggestedVector.getX() - this.getSpeed(), 00f, 0.0f) :
								new Vector3f(this.suggestedVector.getX() + this.getSpeed(), 0.0f, 0.0f)
				);
			} else {

				this.suggestedVector = (new Vector3f(0.0f, v.getY(), 0.0f));

				this.setVelocity(
						(this.getSuggestedVector().getY() < 0) ?
								new Vector3f(0.0f, this.suggestedVector.getY() - this.getSpeed(), 0.0f) :
								new Vector3f(0.0f, this.suggestedVector.getY() + this.getSpeed(), 0.0f)
				);
			}
		}
	}
	
	private Vector3f getSuggestedVector() {
		
		return this.suggestedVector;
	}

	public int getSeekerDirection() {
		
		int tmp = 0;
		
		if ( ( this.getVelocity().getX() == 0 ) && ( this.getVelocity().getY() < 0 ) ) {
			
			tmp = Constants.SeekerTextureColumns * 2;
			//tmp = 'w';
		}
		else if ( ( this.getVelocity().getX() < 0 ) && ( this.getVelocity().getY() == 0 ) ) {
			
			tmp = Constants.SeekerTextureColumns * 3;
			//tmp = 'a';
		}
		else if ( ( this.getVelocity().getX() > 0 ) && ( this.getVelocity().getY() == 0 ) ) {
			
			tmp = Constants.SeekerTextureColumns * 1;
			//tmp = 'd';
		}
		else if ( ( this.getVelocity().getX() == 0 ) && ( this.getVelocity().getY() > 0 ) ) {
			
			tmp = Constants.SeekerTextureColumns * 0;
			//tmp = 's';
		}
		return tmp;
	}

	public BufferedImage[] getSprites() {
		
		return this.sprites;
	}
	
	private void setVelocity( Vector3f v ) {
		
		this.velocity = v;
	}
	
	public Vector3f getVelocity() {
		
		return this.velocity;
	}
	
	private void setSpeed( int s ) {
		
		this.speed = s;
	}
	
	public int getSpeed() {
		
		return this.speed;
	}
}
