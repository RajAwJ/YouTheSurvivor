/**
 @author: Awais Javed
 */

package extra;

import java.awt.Rectangle;

import util.Point3f;
import util.Vector3f;

public class SeekTarget {

	Point3f seekerCentre, targetCentre;
	Rectangle seekerBounds, targetBounds;
	
	Vector3f suggestedVector;
	
	public SeekTarget( Point3f sC, Rectangle sB, Point3f tC, Rectangle tB ) {
		
		this.seekerCentre = sC;
		this.seekerBounds = sB;
		this.targetCentre = tC;
		this.targetBounds = tB;
		
		this.initiateSeek();
	}
	
	private void initiateSeek() {
		
		if ( !this.checkTargetReached() ) {
			
			this.setSuggestedVector( this.computeTarget() );
		}
		else {
			
			this.setSuggestedVector( null );
		}
	}
	
	private Vector3f computeTarget() {
		
		Vector3f targetV = this.getTargetVector();
		Vector3f seekerV = this.getSeekerVector();
		
		Vector3f d = targetV.MinusVector( seekerV );
		d = d.Normal();
		
		return d;
	}
	
	private Vector3f getTargetVector() {
		
		return new Vector3f( this.getTargetCentre().getX(), this.getTargetCentre().getY(),
				this.getTargetCentre().getZ() );
	}
	
	private Vector3f getSeekerVector() {
		
		return new Vector3f( this.getSeekerCentre().getX(), this.getSeekerCentre().getY(),
				this.getSeekerCentre().getZ() );
	}
	
	private boolean checkTargetReached() {
		
		boolean intersect = false;
		if ( this.getSeekerCentre() == this.getTargetCentre() ) {
			
			intersect = true;
		}
		
		return intersect;
	}
	
	private void setSuggestedVector( Vector3f s ) {
		
		this.suggestedVector = s;
	}
	
	public Vector3f getSuggestedVector() {
		
		return this.suggestedVector;
	}
	
	private Point3f getSeekerCentre() {
		
		return this.seekerCentre;
	}
	
	private Point3f getTargetCentre() {
		
		return this.targetCentre;
	}
	
	private Rectangle getSeekerBounds() {
		
		return this.seekerBounds;
	}
	
	private Rectangle getTargetBounds() {
		
		return this.targetBounds;
	}
}
