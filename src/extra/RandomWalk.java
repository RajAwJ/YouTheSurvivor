/**
 @author: Awais Javed
 */

package extra;

import util.Vector3f;

public class RandomWalk {

	Vector3f velocity;
	
	public RandomWalk( ) {

		double randX, randY;
		
		randX = Math.random();
		randY = Math.random();
	
		double flipRand = Math.random();
		
		if ( flipRand > 0.5 ) {
			
			randX = -randX;
		}
		
		flipRand = Math.random();
		
		if ( flipRand < 0.5 ) {
			
			randY = -randY;
		}
		
		this.velocity = new Vector3f( ( float ) randX, ( float ) randY, ( float ) 0.0 );
	}
	
	public Vector3f getVelocity() {
		
		return this.velocity;
	}
}