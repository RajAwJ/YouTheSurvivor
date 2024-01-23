/**
 @author: Awais Javed
 */

package templates;

import java.awt.image.BufferedImage;
import java.util.Random;

import extra.ExtractSprite;
import util.Constants;
import util.GameObject;
import util.Point3f;

public class Scorer extends GameObject {

	BufferedImage[] sprites;
	private Point3f[] posToAvoid;
	
	public Scorer() {

		this.setWidth( Constants.Scorer_WIDTH );
		this.setHeight( Constants.Scorer_HEIGHT );
		
		this.setTexture( Constants.ScorerTexture );
		this.extractSprite();
	}
	
	private void calculateOrigin() {

		Random rand = new Random();
		int rx = 0, ry = 0;

		boolean go;
		do {

			go = true;
			rx = rand.nextInt(Constants.BOUNDARY_RIGHT - Constants.TILE_WIDTH );
			ry = rand.nextInt(Constants.BOUNDARY_BOTTOM - Constants.TILE_HEIGHT );

			for ( Point3f p: this.getPosToAvoid() ) {

				if ( ( ( rx >= p.getX() ) && ( rx <= ( p.getX() + Constants.TILE_BLOCKS_WIDTH ) ) )
						&& ( ( ry >= p.getY() ) && ( ry <= ( p.getY() + Constants.TILE_BLOCKS_HEIGHT ) ) ) ) {
					go = false; }
			}

		} while( ( rx <= Constants.TILE_WIDTH ) || ( ry <= Constants.TILE_HEIGHT ) || !go );
		
		this.setCentre( new Point3f( (float) rx, (float) ry, 0.0f ) );
	}
	
	private void extractSprite() {
		
		ExtractSprite es = new ExtractSprite( this.textureLocation(), Constants.ScorerTextureRows,
				Constants.ScorerTextureColumns, Constants.ScorerSpritePixel );
		
		this.sprites = es.getSprites();
	}
	
	public BufferedImage[] getSprites() {
		
		return this.sprites;
	}

	public void setPosToAvoid( Point3f[] p ) {

		this.posToAvoid = p;
		this.calculateOrigin();
	}

	private Point3f[] getPosToAvoid() {

		return this.posToAvoid;
	}
}