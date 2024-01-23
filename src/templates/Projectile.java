/**
 @author: Awais Javed
 */

package templates;

import java.awt.image.BufferedImage;

import extra.ExtractSprite;
import util.Constants;
import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Projectile extends GameObject {

	private BufferedImage[] sprites;
	private Vector3f velocity;
	private int spriteIterator;
	private int diff;
	private char callingKey;
	private boolean explosion;
	
	public Projectile( Point3f centre, int width, int height ) {
		
		this.setCentre( centre );
		this.setWidth( width );
		this.setHeight( height );
		
		diff = Constants.ProjectileTextureColumnsEnd - Constants.ProjectileTextureColumnsStart;
		this.setSpriteIterator( diff - 1 );
		
		this.setTexture( Constants.ProjectileTexture );
		extractSprite();
		
		this.setDamageCaused( Constants.ProjectileDamage );
		this.setExplosion( false );		
	}
	
	private void extractSprite() {
		
		ExtractSprite es = new ExtractSprite( this.textureLocation(), Constants.ProjectileTextureRows, 
				this.diff, Constants.ProjectileSpritePixel, Constants.ProjectileTextureColumnsStart, 
				'A', 0, ' ');
		
		this.sprites = es.getSprites();
	}
	
	private void extractExplosionSprite() {
		
		ExtractSprite es = new ExtractSprite( this.textureLocation(), Constants.ProjectileTextureRows, 
				Constants.ProjectileTextureColumnsStart, Constants.ProjectileSpritePixel );
		
		this.sprites = es.getSprites();
	}
	
	public void projectileLogic() {
		
		this.getCentre().ApplyVector( this.getVelocity() );
		
		if ( ( this.getColliding() || this.checkBoundaryCollision() ) && !this.getExplosion() )
			this.collisionDetection();
		
		if ( ( this.getExplosion() ) && ( this.getSpriteIterator() == 0 ) ) {
			
			this.resetIterator();
			this.isExists( false );
		}		
		else if ( this.getSpriteIterator() == 0 )
			this.resetIterator();
		else
			this.setSpriteIterator( this.getSpriteIterator() - 1 );
	}
	
	private boolean checkBoundaryCollision() {

		boolean c = false;
		if ( ( this.getCentre().getX() <= Constants.BOUNDARY_LEFT ) || ( this.getCentre().getX() >= Constants.BOUNDARY_RIGHT )
				|| ( this.getCentre().getY() <= Constants.BOUNDARY_TOP ) || ( this.getCentre().getY() >= Constants.BOUNDARY_BOTTOM ) ) {

			c = true;
		}
		
		return c;
	}
	
	public BufferedImage[] getSprites() {
		
		return sprites;
	}
	
	public void setVelocity( Vector3f v ) {
		
		this.velocity = v;
	}
	
	public Vector3f getVelocity() {
		
		return this.velocity;
	}
	
	public void setCallingKey( char c ) {
		
		callingKey = c;
	}
	
	public char getCallingKey() {
		
		return callingKey;
	}
	
	public void setSpriteIterator( int i ) {
		
		spriteIterator = i;
	}
	
	public int getSpriteIterator() {
		
		return spriteIterator;
	}
	
	public void resetIterator() {
		
		this.setSpriteIterator( diff - 1 );
	}
	
	private void setExplosion( boolean b ) {
		
		this.explosion = b;
	}
	
	public boolean getExplosion() {
		
		return this.explosion;
	}
	
	private void collisionDetection() {

		this.setExplosion( true );
		this.extractExplosionSprite();
		this.setSpriteIterator( Constants.ProjectileTextureColumnsStart - 1 );
	}
}
