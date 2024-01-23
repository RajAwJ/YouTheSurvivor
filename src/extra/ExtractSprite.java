/**
 @author: Awais Javed
 */

package extra;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ExtractSprite {

	BufferedImage[] sprites;
	String textureLocation;
	int rows, columns, spritePixel;
	
	public ExtractSprite( String l, int r, int c, int p, int xMod, char xM, int yMod, char yM ) {
		
		if ( ( xM != 'A' ) && ( yM != 'A' ) && ( xM != 'M' ) && ( yM != 'M' ) ) {

			System.out.println( "Wrong Input." );
		}
		else {
			
			textureLocation = l;
			rows = r;
			columns = c;
			spritePixel = p;
			
			try {
				
				BufferedImage bigImg = ImageIO.read( new File( this.textureLocation ) );
				sprites = new BufferedImage[ rows * columns ];
				
				for ( int i = 0; i < rows; i++ ) {
					
					for ( int j = 0; j < columns; j++ ) {

						sprites[ ( i * columns ) + j ] = bigImg.getSubimage(
								
								( xM == 'A' ) ? 
										( ( j + xMod ) * spritePixel ) : 
											( ( j * xMod ) * spritePixel ),
								
								( yM == 'A' ) ? 
										( ( i + yMod ) * spritePixel ) : 
											( ( i * yMod ) * spritePixel), 
								
								spritePixel, 
								spritePixel );
					}
				}
			}
			catch ( IOException e ) {
				
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param l : Path of the texture
	 * @param r : Number of rows to be scanned
	 * @param c : Number of columns to be scanned
	 * @param p : Number of pixels of a sprite
	 */
	public ExtractSprite( String l, int r, int c, int p ) {
		
		this.textureLocation = l;
		this.rows = r;
		this.columns = c;
		this.spritePixel = p;
		
		try {
		
			BufferedImage bigImg = ImageIO.read( new File( this.textureLocation ) );
			sprites = new BufferedImage[ this.rows * this.columns ];
			

			for ( int i = 0; i < this.rows; i++ ) {
				
				for ( int j = 0; j < this.columns; j++ ) {
					
					sprites[ ( i * this.columns ) + j ] = bigImg.getSubimage(
							j * this.spritePixel, 
							i * this.spritePixel, 
							this.spritePixel, 
							this.spritePixel );
				}
			} 
		}
		catch ( IOException e ) {
			
			e.printStackTrace();
		}
	}
	
	public BufferedImage[] getSprites() {
		
		return this.sprites;
	}
}