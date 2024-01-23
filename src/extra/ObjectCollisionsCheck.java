/**
 @author: Awais Javed
 */

package extra;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import util.Constants;
import util.GameObject;

public class ObjectCollisionsCheck {

	private HashMap<String, ArrayList<GameObject>> objectList;
	
	public ObjectCollisionsCheck( HashMap<String, ArrayList<GameObject>> oL ) {

		objectList = oL;
	}
	
	public HashMap<String, ArrayList<GameObject>> getObjectList() {
		
		return this.objectList;
	}
	
	public void setObjectList( HashMap<String, ArrayList<GameObject>> oL ) {
		
		this.objectList = oL;
	}
	
	private void updateObjectList( String s, ArrayList<GameObject> oL ) {
		
		this.getObjectList().replace( s, oL );
	}
	
	public void checkForCollisions() {
		
		if ( this.getObjectList().get( Constants.WALKER_KEY ) != null )
			this.checkWalkerPlayerCollision();
		
		if ( ( this.getObjectList().get( Constants.WALKER_KEY ) != null ) &&
			( this.getObjectList().get( Constants.PROJECTILE_KEY ) != null ) )
			this.checkWalkerProjectileCollision();
		
		if ( ( this.getObjectList().get( Constants.SEEKER_KEY ) ) != null )
			this.checkSeekerPlayerCollision();
		
		if ( ( this.getObjectList().get( Constants.SEEKER_KEY ) != null ) &&
				( this.getObjectList().get( Constants.PROJECTILE_KEY ) != null ) )
			this.checkSeekerProjectileCollision();
		
		if ( ( this.getObjectList().get( Constants.SCORER_KEY ) ) != null )
			this.checkScorerPlayerCollision();
	}

	private void checkWalkerPlayerCollision() {
		
		ArrayList<GameObject> pL = this.getObjectList().get( Constants.PLAYER_KEY );
		ArrayList<GameObject> wL = this.getObjectList().get( Constants.WALKER_KEY );
		
		boolean b = false;
		
		for ( int i = 0; i < pL.size(); i++ ) {
			
			for ( int j = 0; j < wL.size(); j++ ) {
				
				Rectangle plB = pL.get( i ).getBounds();
				plB.grow( ( int ) -( plB.width * 0.40 ), ( int ) -( plB.height * 0.40 ) );
				
				Rectangle wlB = wL.get( j ).getBounds();
				wlB.grow( ( int ) -( wlB.width * 0.40 ), ( int ) -( wlB.height * 0.40 ) );
				
				if ( plB.intersects( wlB.getBounds() ) ) {
					
					b = true;
					pL.get( i ).setColliding( b );
					wL.get( i ).setColliding( b );
				}
			}
		}
		
		this.updateObjectList( Constants.PLAYER_KEY, pL );
		this.updateObjectList( Constants.WALKER_KEY , wL );
		if ( b )
			this.resolveWalkerPlayerCollision();
	}
	
	private void checkWalkerProjectileCollision() {
		
		ArrayList<GameObject> projL = this.getObjectList().get( Constants.PROJECTILE_KEY );
		ArrayList<GameObject> wL = this.getObjectList().get( Constants.WALKER_KEY );
		
		boolean b = false;
		
		for ( int i = 0; i < wL.size(); i++ ) {
			
			for ( int j = 0; j < projL.size(); j++ ) {
				
				if ( !projL.get( j ).getColliding() ) {
					
					Rectangle projlB = projL.get( j ).getBounds();
					projlB.grow( ( int ) -( projlB.width * 0.40 ), ( int ) -( projlB.height * 0.40 ) );
					
					Rectangle wlB = wL.get( i ).getBounds();
					wlB.grow( ( int ) -( wlB.width * 0.40 ), ( int ) -( wlB.height * 0.40 ) );
					
					if ( wlB.intersects( projlB.getBounds() ) ) {
						
						b = true;
						wL.get( i ).setColliding( b );
						projL.get( j ).setColliding( b );
					}
				}
			}
		}
		
		this.updateObjectList( Constants.PROJECTILE_KEY, projL );
		this.updateObjectList( Constants.WALKER_KEY , wL );
		if ( b )
			this.resolveWalkerProjectileCollision();
	}
	
	private void checkSeekerPlayerCollision() {
		
		ArrayList<GameObject> skr = this.getObjectList().get( Constants.SEEKER_KEY );
		ArrayList<GameObject> pL = this.getObjectList().get( Constants.PLAYER_KEY );
		
		boolean b = false;
		
		for ( int i = 0; i < pL.size(); i++ ) {
			
			for ( int j = 0; j < skr.size(); j++ ) {
				
				Rectangle skrB = skr.get( j ).getBounds();
				skrB.grow( ( int ) -( skrB.width * 0.40 ), ( int ) -( skrB.height * 0.40 ) );
				
				Rectangle plB = pL.get( i ).getBounds();
				plB.grow( ( int ) -( plB.width * 0.40 ), ( int ) -( plB.height * 0.40 ) );
				
				if ( skrB.intersects( plB.getBounds() ) ) {
					
					b = true;
					skr.get( j ).setColliding( b );
					pL.get( i ).setColliding( b );
				}
			}
		}
		
		this.updateObjectList( Constants.SEEKER_KEY, skr );
		this.updateObjectList( Constants.PLAYER_KEY, pL );
		if ( b )
			this.resolveSeekerPlayerCollision();
	}
	
	private void checkSeekerProjectileCollision() {
		
		ArrayList<GameObject> skr = this.getObjectList().get( Constants.SEEKER_KEY );
		ArrayList<GameObject> projL = this.getObjectList().get( Constants.PROJECTILE_KEY );
		
		boolean b = false;
		
		for ( int i = 0; i < skr.size(); i++ ) {
			
			for ( int j = 0; j < projL.size(); j++ ) {
				
				if ( !projL.get( j ).getColliding() ) {
				
					Rectangle skrB = skr.get( i ).getBounds();
					skrB.grow( ( int ) -( skrB.width * 0.40 ), ( int ) -( skrB.height * 0.40 ) );
					
					Rectangle projlB = projL.get( j ).getBounds();
					projlB.grow( ( int ) -( projlB.width * 0.40 ), ( int ) -( projlB.height * 0.40 ) );
					
					if ( skrB.intersects( projlB.getBounds() ) ) {
						
						b = true;
						skr.get( i ).setColliding( b );
						projL.get( j ).setColliding( b );
					}
				}
			}
		}
		
		this.updateObjectList( Constants.SEEKER_KEY, skr );
		this.updateObjectList( Constants.PROJECTILE_KEY, projL );
		if ( b )
			this.resolveSeekerProjectileCollision();
	}
	
	private void checkScorerPlayerCollision() {
		
		ArrayList<GameObject> scr = this.getObjectList().get( Constants.SCORER_KEY );
		ArrayList<GameObject> pl = this.getObjectList().get( Constants.PLAYER_KEY );
		
		boolean b = false;
		
		for ( int i = 0; i < pl.size(); i++ ) {
			
			for ( int j = 0; j < scr.size(); j++ ) {
				
				Rectangle plB = pl.get( i ).getBounds();
				plB.grow( ( int ) -( plB.width * 0.40 ), ( int ) -( plB.height * 0.30 ) );
				
				Rectangle scrB = scr.get( j ).getBounds();
				scrB.grow( ( int ) -( scrB.width * 0.10 ), ( int ) ( scrB.height * 0.10 ) );
				
				if ( plB.intersects( scrB.getBounds() ) ) {
					
					b = true;
					pl.get( i ).setColliding( b );
					scr.get( j ).setColliding( b );
				}
			}
		}
		
		this.updateObjectList( Constants.SCORER_KEY, scr );
		this.updateObjectList( Constants.PLAYER_KEY, pl );
		if ( b )
			this.resolveScorerPlayerCollision();
	}
	
	private void resolveWalkerPlayerCollision() {
		
		ArrayList<GameObject> pL = this.getObjectList().get( Constants.PLAYER_KEY );
		ArrayList<GameObject> wL = this.getObjectList().get( Constants.WALKER_KEY );
		
		for ( int i = 0; i < pL.size(); i++ ) {
		
			for ( int j = 0; j < wL.size(); j++ ) {
				
				if ( ( pL.get( i ).getColliding() ) && ( wL.get( j ).getColliding() ) ) {
					
					pL.get( i ).setHealth( pL.get( i ).getHealth() - wL.get( j ).getDamageCaused() );
					wL.get( j ).setColliding( false );
					wL.get( j ).setHealth( 0 );
					
					if ( pL.get( i ).getHealth() == 0 )
						pL.get( i ).isExists( false );
				}
			}
		}
		
		this.updateObjectList( Constants.PLAYER_KEY, pL );
		this.updateObjectList( Constants.WALKER_KEY , wL );
	}
	
	private void resolveWalkerProjectileCollision() {
		
		ArrayList<GameObject> projL = this.getObjectList().get( Constants.PROJECTILE_KEY );
		ArrayList<GameObject> wL = this.getObjectList().get( Constants.WALKER_KEY );
		
		for ( int i = 0; i < wL.size(); i++ ) {
			
			for ( int j = 0; j < projL.size(); j++ ) {
				
				if ( ( wL.get( i ).getColliding() ) && ( projL.get( j ).getColliding() ) ) {

					wL.get( i ).setHealth( wL.get( i ).getHealth() - projL.get( j ).getDamageCaused() );
					wL.get( i ).setColliding( false );
				}
			}
		}
		
		this.updateObjectList( Constants.PROJECTILE_KEY , projL );
		this.updateObjectList( Constants.WALKER_KEY , wL );
	}
	
	private void resolveSeekerPlayerCollision() {
		
		ArrayList<GameObject> skr = this.getObjectList().get( Constants.SEEKER_KEY );
		ArrayList<GameObject> pL = this.getObjectList().get( Constants.PLAYER_KEY );
		
		for ( int i = 0; i < pL.size(); i++ ) {
			
			for ( int j = 0; j < skr.size(); j++ ) {
				
				if ( ( pL.get( i ).getColliding() ) && ( skr.get( j ).getColliding() ) ) {
					
					pL.get( i ).setHealth( pL.get( i ).getHealth() - skr.get( j ).getDamageCaused() );
					skr.get( j ).setColliding( false );
					skr.get( j ).setHealth( 0 );

					if ( pL.get( i ).getHealth() == 0 )
						pL.get( i ).isExists( false );
				}
			}
		}
		
		this.updateObjectList( Constants.SEEKER_KEY, skr );
		this.updateObjectList( Constants.PLAYER_KEY, pL );
	}
	
	private void resolveSeekerProjectileCollision() {
		
		ArrayList<GameObject> skr = this.getObjectList().get( Constants.SEEKER_KEY );
		ArrayList<GameObject> projL = this.getObjectList().get( Constants.PROJECTILE_KEY );
		
		for ( int i = 0; i < skr.size(); i++ ) {
			
			for ( int j = 0; j < projL.size(); j++ ) {
				
				if ( ( skr.get( i ).getColliding() ) && ( projL.get( j ).getColliding() ) ) {
					
					skr.get( i ).setHealth( skr.get( i ).getHealth() - projL.get( j ).getDamageCaused() );
					skr.get( i ).setColliding( false );
				}
			}
		}
		
		this.updateObjectList( Constants.SEEKER_KEY, skr );
		this.updateObjectList( Constants.PROJECTILE_KEY, projL );
	}
	
	private void resolveScorerPlayerCollision() {
		
		ArrayList<GameObject> scr = this.getObjectList().get( Constants.SCORER_KEY );
		ArrayList<GameObject> pl = this.getObjectList().get( Constants.PLAYER_KEY );
		
		for ( int i = 0; i < pl.size(); i++ ) {
			
			for ( int j = 0; j < scr.size(); j++ ) {
				
				if ( ( pl.get( i ).getColliding() ) && ( scr.get( j ).getColliding() ) ) {
					
					scr.get( j ).isExists( false );
				}
			}
		}
		
		this.updateObjectList( Constants.SCORER_KEY, scr );
		this.updateObjectList( Constants.PLAYER_KEY, pl );
	}
}