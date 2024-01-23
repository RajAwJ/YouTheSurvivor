/**
 @author: Awais Javed
 */

package extra;

import templates.Tile;
import util.Constants;
import util.GameObject;
import util.Point3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EnvironmentCollisionsCheck {

    private HashMap< String, ArrayList< GameObject > > objectList;
    private HashMap< Tile, Point3f[] > tileList;

    public EnvironmentCollisionsCheck( HashMap< String, ArrayList< GameObject > > oL,
                                       HashMap< Tile, Point3f[] > tL ) {

        this.setObjectList( oL );
        this.tileList = tL;
    }

    public void checkForCollisions() {

        for ( String s: this.objectList.keySet() ) {

            if ( this.objectList.get( s ) != null ) {

                ArrayList< GameObject > obList = new ArrayList<>();

                for ( GameObject ob: this.objectList.get( s ) ) {

                    obList.add( this.collisionWithTile( ob ) );
                }

                this.objectList.replace( s, obList );
            }
        }
    }

    private GameObject collisionWithTile( GameObject ob ) {

        for ( Tile t: this.tileList.keySet() ) {

            for ( Point3f pos: this.tileList.get( t ) ) {

                Rectangle tileB = new Rectangle( ( int ) pos.getX(), ( int ) pos.getY(),
                        t.getWidth(), t.getHeight() );
                Rectangle obB = ob.getBounds();
                obB.grow( ( int ) -( obB.width * 0.40 ), ( int ) -( obB.height * 0.30 ) );

                if ( obB.intersects( tileB ) ) {

                    Rectangle intersection = obB.intersection( tileB );
                    if ( intersection.width != obB.width ) {

                        if ( intersection.getX() > obB.getX() ) {

                            ob.getCentre().setX( ob.getCentre().getX() - intersection.width );
                        } else {

                            ob.getCentre().setX( ob.getCentre().getX() + intersection.width );
                        }
                    }

                    else if ( intersection.height != obB.height ) {

                        if ( intersection.getY() > obB.getY() ) {

                            ob.getCentre().setY( ob.getCentre().getY() - intersection.height );
                        } else {

                            ob.getCentre().setY( ob.getCentre().getY() + intersection.height );
                        }
                    }

                    if ( !ob.textureLocation().equals( Constants.WalkerTexture ) &&
                            !ob.textureLocation().equals( Constants.PlayerTexture ) )
                        ob.setColliding( true );
                }
            }
        }

        return ob;
    }

    public void setObjectList( HashMap< String, ArrayList< GameObject > > oL ) {

        this.objectList = oL;
    }
    public HashMap< String, ArrayList< GameObject > > getObjectList() {

        return this.objectList;
    }

    public HashMap< Tile, Point3f[] > getTileList() {

        return this.tileList;
    }
}