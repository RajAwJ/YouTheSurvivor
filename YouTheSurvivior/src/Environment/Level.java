/**
 @author: Awais Javed
 Student Number: 11543303
 */

package Environment;

import util.Constants;
import util.Point3f;

import java.util.Random;

public class Level {

    private Point3f[] pos;

    public Level( String s ) {

        if ( s.equals( "Practice" ) ) {

            this.setPos( this.setPractice_Environ() );
        }
        else if ( s.equals( "Easy" ) ) {

            this.setPos( this.setEasy_Environ() );
        }
        else if ( s.equals( "Medium" ) ) {

            this.setPos( this.setMedium_Environ() );
        }
        else if ( s.equals( "Hard" ) ) {

            this.setPos( this.setHard_Environ() );
        }
    }

    private Point3f[] setPractice_Environ() {

        Point3f[] wallPos = new Point3f[ 3 ];

        wallPos[ 0 ] = new Point3f( Constants.BOUNDARY_RIGHT / 3, Constants.BOUNDARY_BOTTOM / 3, 0 );
        wallPos[ 1 ] = new Point3f( Constants.BOUNDARY_RIGHT / 2, Constants.BOUNDARY_BOTTOM / 2, 0 );
        wallPos[ 2 ] = new Point3f( ( Constants.BOUNDARY_RIGHT * 2 ) / 3,
                ( Constants.BOUNDARY_BOTTOM * 2 ) / 3, 0 );

        return wallPos;
    }

    private Point3f[] setEasy_Environ() {

        Point3f[] wallPos = new Point3f[ 5 ];

        wallPos[ 0 ] = new Point3f( Constants.BOUNDARY_RIGHT / 3, Constants.BOUNDARY_BOTTOM / 3, 0 );
        wallPos[ 1 ] = new Point3f( ( Constants.BOUNDARY_RIGHT * 2 ) / 3, Constants.BOUNDARY_BOTTOM / 3, 0 );
        wallPos[ 2 ] = new Point3f( Constants.BOUNDARY_RIGHT / 3, ( Constants.BOUNDARY_BOTTOM * 2 ) / 3, 0 );
        wallPos[ 3 ] = new Point3f( Constants.BOUNDARY_RIGHT / 2, Constants.BOUNDARY_BOTTOM / 2, 0 );
        wallPos[ 4 ] = new Point3f( ( Constants.BOUNDARY_RIGHT * 2 ) / 3,
                ( Constants.BOUNDARY_BOTTOM * 2 ) / 3, 0 );

        return wallPos;
    }

    private Point3f[] setMedium_Environ() {

        int size = ( ( Constants.BOUNDARY_RIGHT - 100 ) - ( Constants.BOUNDARY_LEFT + 100 ) ) /
                ( Constants.TILE_BLOCKS_WIDTH * 3 );
        size = size * 2;
        Point3f[] wallPos = new Point3f[ size + 5 ];

        int j = 0;
        for ( int i = Constants.BOUNDARY_LEFT + 100; i < Constants.BOUNDARY_RIGHT - 100;
              i = i + ( Constants.TILE_BLOCKS_WIDTH * 3 ) ) {

            wallPos[ j ] = new Point3f( i, 200, 0 );
            wallPos[ j + 1 ] = new Point3f( i, Constants.BOUNDARY_BOTTOM - 200, 0 );
            j = j + 2;
        }
        wallPos[ j ] = new Point3f( Constants.BOUNDARY_RIGHT / 3, Constants.BOUNDARY_BOTTOM / 2, 0 );
        wallPos[ j + 1 ] = new Point3f( Constants.BOUNDARY_RIGHT / 2, Constants.BOUNDARY_BOTTOM / 2, 0 );
        wallPos[ j + 2 ] = new Point3f( ( Constants.BOUNDARY_RIGHT * 2 ) / 3,
                ( Constants.BOUNDARY_BOTTOM / 2 ), 0 );

        return wallPos;
    }

    private Point3f[] setHard_Environ() {

        Point3f[] wallPos = new Point3f[ 2 ];

        Random rand = new Random();
        int x = rand.nextInt( Constants.BOUNDARY_RIGHT - 30 );
        int y = rand.nextInt( Constants.BOUNDARY_BOTTOM - 30 );

        wallPos[ 0 ] = new Point3f( x, y, 0 );

        do {
            x = rand.nextInt( Constants.BOUNDARY_RIGHT - 30 );
            y = rand.nextInt( Constants.BOUNDARY_BOTTOM - 30 );
        } while( ( x == wallPos[ 0 ].getX() ) || ( y == wallPos[ 0 ].getY() ) );

        wallPos[ 1 ] = new Point3f( x, y, 0 );

        return wallPos;
    }

    private void setPos( Point3f[] p ) {

        this.pos = p;
    }

    public Point3f[] getPos() {

        return this.pos;
    }
}