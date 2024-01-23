/**
 @author: Awais Javed
 */

package templates;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import extra.ExtractSprite;
import extra.RandomWalk;
import extra.SeekTarget;
import util.Constants;
import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Walker extends GameObject {
	
	private BufferedImage[] sprites;
	private int diff = Constants.WalkerTextureWalkColumnsEnd - Constants.WalkerTextureWalkColumnsStart;
	
	private Point3f playerPos;
	private Rectangle playerBounds;
	
	private Vector3f suggestedVector;
	private boolean chasingTarget;
	
	private Vector3f velocity;
	private int speed, msCounter, maxWait;

	private Point3f[] posToAvoid;
	
	private boolean gameStart;
	
	public Walker( String state ) {

		this.setWidth( Constants.Walker_WIDTH );
		this.setHeight( Constants.Walker_HEIGHT );
		this.setTexture( Constants.WalkerTexture );
		
		this.extractSprite();
		this.setChasingTarget( false );
		
		this.setVelocity( new Vector3f( 0.0f, 0.0f, 0.0f ) );

		if ( state.equals( "Practice" ) )
			this.setSpeed( 1 );
		else
			this.setSpeed( Constants.STEP + 2 );
		this.maxWait = 100;
		this.msCounter = 0;
		
		this.setHealth( Constants.WalkerInitialHealth );
		this.setDamageCaused( Constants.WalkerDamage );
	}

	/**
	 * Method 'calculateOrigin()':
	 * Calculate a random origin of this Walker instance
	 */
	private void calculateOrigin() {
		
		Random rand = new Random();
		int rx = 0, ry = 0;

		boolean go = true;
		do {

			rx = rand.nextInt( Constants.BOUNDARY_RIGHT - Constants.TILE_WIDTH );
			ry = rand.nextInt( Constants.BOUNDARY_BOTTOM - Constants.TILE_HEIGHT );

			for ( Point3f p: this.getPosToAvoid() ) {

				if ( ( p.getX() == rx ) && ( p.getY() == ry ) )
					go = false;
			}

		} while( ( rx <= Constants.TILE_WIDTH ) || ( ry <= Constants.TILE_HEIGHT ) || !go );
		
		this.setCentre( new Point3f( (float) rx, (float) ry, 0.0f ) );
		this.setGameStart( true );
	}
	
	/**
	 * Method 'extractSprite()':
	 * Extract sprites from a given file, and put them in a BufferedImage array
	 */
	private void extractSprite() {

		ExtractSprite es = new ExtractSprite( 
				this.textureLocation(), Constants.WalkerTextureRows, 
				this.diff, Constants.WalkerSpritePixel, 
				Constants.WalkerTextureWalkColumnsStart, 'A', 
				2, 'M' );
		
		this.sprites = es.getSprites();
	}
	
	/**
	 * Method 'walkerLogic()':
	 * Walker functionality starts here.
	 */
	public void walkerLogic() {
		
		Rectangle r = this.getBounds();
		r.grow( 50, 50 );

		if ( r.intersects( this.getPlayerBounds() ) ) {

			this.setChasingTarget( true );
			SeekTarget sT = new SeekTarget( this.getCentre(), this.getBounds(), this.getPlayerPos(), this.getPlayerBounds() );
			
			if ( sT.getSuggestedVector() != null ) {
				
				this.setSuggestedVector( sT.getSuggestedVector() );
				this.getCentre().ApplyVector( this.getVelocity() );
			}
		}
		else {

			this.setChasingTarget( false );
			if ( ( this.msCounter != this.maxWait ) && ( !this.isGameStart() ) ) {
				
				this.msCounter++;
				
			} else {
				
				this.msCounter = 0;
				this.setGameStart( false );
				
				RandomWalk rW = new RandomWalk();
				this.setSuggestedVector( rW.getVelocity() );
			}

			this.checkBoundaryCollision();
			this.getCentre().ApplyVector( this.getVelocity() );
		}
	}

	
	public int getWalkerDirection() {
		
		int tmp = 0;
		
		if ( ( this.getVelocity().getX() == 0 ) && ( this.getVelocity().getY() < 0 ) ) {
			
			tmp = this.diff * 1;
			//tmp = 'w';
		}
		else if ( ( this.getVelocity().getX() < 0 ) && ( this.getVelocity().getY() == 0 ) ) {
			
			tmp = this.diff * 0; 
			//tmp = 'a';
		}
		else if ( ( this.getVelocity().getX() > 0 ) && ( this.getVelocity().getY() == 0 ) ) {
			
			tmp = this.diff * 2;
			//tmp = 'd';
		}
		else if ( ( this.getVelocity().getX() == 0 ) && ( this.getVelocity().getY() > 0 ) ) {
			
			tmp = this.diff * 3;
			//tmp = 's';
		}
		
		return tmp;
	}
	
	/**
	 * Method 'setPlayerVariables()':
	 * Method to set (setter method) 'Player' details, i.e. Player centre and Player bounds
	 */
	public void setPlayerVariables( Point3f p, Rectangle b ) {
		
		this.playerPos = p;
		this.playerBounds = b;
	}
	
	/**
	 * Method 'getPlayerPos()':
	 * Return (getter method) 'Player' centre point
	 */
	private Point3f getPlayerPos() {
		
		return this.playerPos;
	}
	
	/**
	 * Method 'getPlayerBounds()':
	 * Return (getter method) 'Player' bounds rectangle
	 */
	private Rectangle getPlayerBounds() {
		
		return this.playerBounds;
	}
	
	private void checkBoundaryCollision() {
		
		if ( ( ( this.getCentre().getX() + this.getWidth() ) >= Constants.BOUNDARY_RIGHT ) ||
				( ( this.getCentre().getY() + this.getHeight() ) >= Constants.BOUNDARY_BOTTOM ) ||
				( ( this.getCentre().getX() ) <= Constants.BOUNDARY_LEFT ) ||
				( ( this.getCentre().getY() ) <= Constants.BOUNDARY_TOP ) ) {
			
			this.setVelocity( this.getVelocity().NegateVector() );
		}
	}
	
	/**
	 * Method 'setSuggestedVector()':
	 * Sets (modified setter method) the velocity (direction & speed) of this 'Walker' instance
	 */
	private void setSuggestedVector( Vector3f v ) {
		
		if ( Math.abs( v.getX() ) > Math.abs( v.getY() ) ) {
			
			this.suggestedVector = ( new Vector3f( v.getX(), 0.0f, 0.0f ) );
			
			this.setVelocity( 
					( this.getSuggestedVector().getX() < 0 ) ? 
							new Vector3f( this.suggestedVector.getX() - this.getSpeed(), 0.0f, 0.0f ) : 
								new Vector3f( this.suggestedVector.getX() + this.getSpeed(), 0.0f, 0.0f ) 
			);
		}
		else {
			
			this.suggestedVector = ( new Vector3f( 0.0f, v.getY(), 0.0f ) );
			
			this.setVelocity( 
					( this.getSuggestedVector().getY() < 0 ) ? 
							new Vector3f( 0.0f, this.suggestedVector.getY() - this.getSpeed(), 0.0f ) : 
								new Vector3f( 0.0f, this.suggestedVector.getY() + this.getSpeed(), 0.0f ) 
			);
		}
	}
	
	/**
	 * Method 'getSuggestedVector()':
	 * Return (getter method) the suggested velocity vector for this 'Walker' instance
	 */
	private Vector3f getSuggestedVector() {
		
		return this.suggestedVector;
	}

	private void setChasingTarget( boolean b ) {

		this.chasingTarget = b;
	}

	public boolean getChasingTarget() {

		return this.chasingTarget;
	}
	
	public BufferedImage[] getSprites() {
		
		return this.sprites;
	}
	
	public void setVelocity( Vector3f v ) {
		
		this.velocity = v;
	}
	
	public Vector3f getVelocity() {
		
		return this.velocity;
	}
	
	public void setSpeed( int s ) {
		
		this.speed = s;
	}
	
	public int getSpeed() {
		
		return this.speed;
	}
	
	private void setGameStart( boolean b ) {
		
		this.gameStart = b;
	}
	
	private boolean isGameStart() {
		
		return this.gameStart;
	}

	public void setPosToAvoid( Point3f[] p ) {

		this.posToAvoid = p;
		this.calculateOrigin();
	}

	private Point3f[] getPosToAvoid() {

		return this.posToAvoid;
	}
}