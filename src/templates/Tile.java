/**
 @author: Awais Javed
 */

package templates;

import util.Constants;
import util.GameObject;

import java.awt.image.BufferedImage;

public class Tile extends GameObject {

    BufferedImage sprite;

    public Tile( boolean b ) {

        this.setWidth( Constants.TILE_WIDTH );
        this.setHeight( Constants.TILE_HEIGHT );
    }

    public void setSprite( BufferedImage sp ) {

        this.sprite = sp;
    }

    public BufferedImage getSprites() {

        return this.sprite;
    }
}