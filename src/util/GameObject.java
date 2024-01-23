/**
 @author: Awais Javed
 */

package util;

import java.awt.Rectangle;

public class GameObject {

	private int width;
	private int height;
	private Point3f centre;
	private boolean exists;
	private boolean colliding;
	private int damageCaused;
	private int health;
	
	private String textureLocation;
	
	public GameObject() {
		
		width = Constants.PLAYER_WIDTH;
		height = Constants.PLAYER_HEIGHT;
		
		centre = new Point3f(Constants.X_DEFAULT, Constants.Y_DEFAULT, 0);
		this.isExists( true );
		this.setColliding( false );
	}
	
	public GameObject(int x, int y, int w, int h, String t) {
	
		width = w;
		height = h;
		
		centre = new Point3f(x, y, 0);
		textureLocation = t;
	}
	
	public void setCentre(Point3f p) {
		
		this.centre = p;
	}
	
	public Point3f getCentre() {
		
		return this.centre;
	}

	public void setWidth(int w) {
		
		width = w;
	}

	public int getWidth() {
		
		return width;
	}

	public void setHeight(int h) {
		
		height = h;
	}

	public int getHeight() {
		
		return height;
	}
	
	public Rectangle getBounds() {
		
		return new Rectangle( (int) centre.getX() - (width / 2), (int) centre.getY() - (height / 2), width, height);
	}
	
	public void setTexture( String t ) {
		
		textureLocation = t;
	}
	
	public String textureLocation() {
		
		return textureLocation;
	}
	
	public void isExists( boolean b ) {
		
		this.exists = b;
	}
	
	public boolean doesExists() {
		
		return this.exists;
	}
	
	public void setColliding( boolean b ) {
		
		this.colliding = b;
	}
	
	public boolean getColliding() {
		
		return this.colliding;
	}
	
	public void setDamageCaused( int d ) {
		
		this.damageCaused = d;
	}
	
	public int getDamageCaused() {
		
		return this.damageCaused;
	}
	
	public void setHealth( int h ) {
		
		this.health = h;
	}
	
	public int getHealth() {
		
		return this.health;
	}
}
