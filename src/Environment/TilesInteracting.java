/**
 @author: Awais Javed
 Student Number: 11543303
 */

package Environment;

import extra.ExtractSprite;
import templates.Tile;
import util.Constants;
import util.Point3f;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TilesInteracting {

    Level level;
    private HashMap<Tile, Point3f[]> tilePositions;
    Tile mockTile;
    Tile innerWall;

    Point3f[] posArray;

    public TilesInteracting( String s ) {

        tilePositions = new HashMap<>();
        level = new Level( s );

        this.initialiseTileObjects();

        this.setPositionsArray();
    }

    private void initialiseTileObjects() {

        mockTile = new Tile( true );
        mockTile.setCentre( new Point3f( 0, 0, 0 ) );

        mockTile.setSprite( this.extractTileSprites( Constants.TILE_KEY_BORDER ) );
        this.setTilePositions( mockTile, this.setBorderTilePos() );

        innerWall = new Tile( true );
        innerWall.setCentre( this.getLevel().getPos()[ 0 ] );
        innerWall.setWidth( Constants.TILE_BLOCKS_WIDTH );
        innerWall.setHeight( Constants.TILE_BLOCKS_HEIGHT );

        innerWall.setSprite( this.extractTileSprites( Constants.TILE_KEY_BLOCKS ) );
        this.setTilePositions( innerWall, this.getLevel().getPos() );
    }

    private BufferedImage extractTileSprites( String s ) {

        ExtractSprite es;
        BufferedImage bI;

        if (s.equals(Constants.TILE_KEY_BORDER)) {

            es = new ExtractSprite(
                    Constants.TileBorderTexture, Constants.TileBorderTextureRows,
                    Constants.TileBorderTextureColumns, Constants.TileBorderSpritePixel
            );
            bI = es.getSprites()[0];

            return bI;
        } else if (s.equals(Constants.TILE_KEY_BLOCKS)) {

            es = new ExtractSprite(
                    Constants.TileBlocksTexture, Constants.TileBlocksTextureRows,
                    Constants.TileBlocksTextureColumns, Constants.TileBlocksSpritePixel
            );
            bI = es.getSprites()[0];

            return bI;
        }

        return null;
    }

    private Point3f[] setBorderTilePos() {

        // Calculating number of border points for a map with width x and height y:
        // ((x-1)*4 + (y-1)*4)/2
        // Since the Screen width and height here are the same, so only need to do this for
        // one of them
        int numBorderPoints = (((Constants.BOUNDARY_RIGHT - 1) * 4) +
                ((Constants.BOUNDARY_BOTTOM - 1) * 4)) / Constants.TILE_WIDTH;
        //numBorderPoints = numBorderPoints + 5;
        Point3f[] borderPoints = new Point3f[numBorderPoints];

        // Format for storing coordinates:
        // String[a] = "x y"
        int i = 0;
        while (i < numBorderPoints) {

            // Inserting border coordinates for [0...Screen Width][0]
            for (int a = 0; a < Constants.BOUNDARY_RIGHT; a = (a + Constants.TILE_HEIGHT)) {

                borderPoints[i] = new Point3f(a, 0, 0);
                i++;

                if (i >= numBorderPoints)
                    break;
            }
            // Inserting border coordinates for [0][0...Screen Height]
            for (int b = 0; b < Constants.BOUNDARY_BOTTOM; b = (b + Constants.TILE_WIDTH)) {

                borderPoints[i] = new Point3f(0, b, 0);
                i++;
            }
            // Inserting border coordinates for [Screen Width][1...Screen Height]
            for (int c = 0; c < Constants.BOUNDARY_BOTTOM; c = (c + Constants.TILE_HEIGHT)) {

                borderPoints[i] = new Point3f((Constants.BOUNDARY_RIGHT - 1), c, 0);
                i++;
            }
            // Inserting border coordinates for [1...Screen Width-1][Screen Height]
            for (int d = 0; d < Constants.BOUNDARY_RIGHT; d = (d + Constants.TILE_WIDTH)) {

                borderPoints[i] = new Point3f(d, Constants.BOUNDARY_BOTTOM - 1, 0);
                i++;

                if (i >= numBorderPoints)
                    break;
            }
        }

        return borderPoints;
    }

    private void setTilePositions(Tile s, Point3f[] p) {

        this.tilePositions.put(s, p);
    }

    public HashMap<Tile, Point3f[]> getTilePositions() {

        return this.tilePositions;
    }

    private void setPositionsArray() {

        int size = 0;
        for ( Tile t: this.getTilePositions().keySet() ) {

            size += this.getTilePositions().get( t ).length;
        }

        this.posArray = new Point3f[ size ];
        int i = 0;
        for ( Tile t: this.getTilePositions().keySet() ) {

            for ( Point3f p: this.getTilePositions().get( t ) ) {

                this.posArray[ i ] = p;
                i++;
            }
        }
    }

    public Point3f[] getPositionsArray() {

        return this.posArray;
    }

    private Level getLevel() {

        return this.level;
    }
}