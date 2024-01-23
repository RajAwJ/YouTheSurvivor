/**
 @author: Awais Javed
 Student Number: 11543303
 */

package Environment;

import extra.ExtractSprite;
import util.Constants;
import util.Point3f;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TilesNonInteracting {

    private HashMap< BufferedImage, Point3f[] > tilePositions;

    public TilesNonInteracting() {

        tilePositions = new HashMap<>();

        tilePositions.put(
            this.extractTileSprites( Constants.TILE_KEY_FLOOR1 ),
            this.setTilePos( Constants.TILE_KEY_FLOOR1 )
        );
    }

    private BufferedImage extractTileSprites( String s ) {

        BufferedImage bI = null;
        ExtractSprite es;

        if ( s.equals( Constants.TILE_KEY_FLOOR1 ) ) {

            es = new ExtractSprite( Constants.TileFloor1Texture, Constants.TileFloor1TextureRows,
                    Constants.TileFloor1TextureColumns, Constants.TileFloor1SpritePixel );

            bI = es.getSprites()[0];
        }

        return bI;
    }

    private Point3f[] setTilePos( String s ) {

        Point3f[] posArr = null;

        if ( s.equals( Constants.TILE_KEY_FLOOR1 ) ) {

            int rows = Constants.SCREEN_HEIGHT / Constants.TILE_FLOOR1_HEIGHT;
            int columns = Constants.SCREEN_WIDTH / Constants.TILE_FLOOR1_WIDTH;

            posArr = new Point3f[ rows * columns ];
            int x = 0;

            for ( int i = 0; i < Constants.SCREEN_WIDTH; i = i + Constants.TILE_FLOOR1_WIDTH ) {

                for ( int j = 0; j < Constants.SCREEN_HEIGHT; j = j + Constants.TILE_FLOOR1_HEIGHT ) {

                    if ( x < posArr.length ) {

                        posArr[ x ] = new Point3f( i, j, 0 );
                        x++;
                    }
                }
            }
        }

        return posArr;
    }

    public HashMap< BufferedImage, Point3f[] > getTilePositions() {

        return this.tilePositions;
    }
}
