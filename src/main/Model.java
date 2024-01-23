/**
 @author: Awais Javed
 */

package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Environment.ManageAudio;
import Environment.StateCheck;
import Environment.TilesInteracting;
import Environment.TilesNonInteracting;
import extra.EnvironmentCollisionsCheck;
import extra.StatCheck;
import templates.Player;
import templates.Projectile;
import templates.Scorer;
import templates.Seeker;
import templates.Walker;
import util.Constants;
import util.GameObject;
import util.Point3f;
import extra.ObjectCollisionsCheck;

public class Model {

	private Controller cInstance = Controller.getInstance();

	private ManageAudio musicPlayer;
	private HashMap<String, ArrayList<GameObject>> objects;
	private ObjectCollisionsCheck colCheck;

	private EnvironmentCollisionsCheck envCol;

	private TilesInteracting TI;
	private TilesNonInteracting TnI;

	private StateCheck state;
	private StatCheck stats;

	private List< String > helpContent;
	private long startTime;

	public Model( GameObject p ) {

		objects = new HashMap<>();
		state = new StateCheck();
		stats = new StatCheck();

		musicPlayer = new ManageAudio();
	}

	public void initializeState() {

		if ( this.getState().getCurrState() != 2 )
			this.getState().stateLogic();

		if ( this.getState().getCurrState() == 1 ) {

			if ( !musicPlayer.getMusicActive().equals( Constants.Menu ) )
				musicPlayer.playMusic( Constants.Menu );

			if ( cInstance.isKeyEnterPressed() ) {

				if ( ( !this.getState().getSelectedOption().equals("Help") ) &&
						( !this.getState().getSelectedOption().equals("Exit") ) ) {

					this.getStats().setLevelWon( false );
					this.setLevel( this.getState().getSelectedOption() );
				}
			}
		} else if ( this.getState().getCurrState() == 2 ) {

			if ( !musicPlayer.getMusicActive().equals( Constants.Gameplay ) &&
					!musicPlayer.getMusicActive().equals( Constants.Chase ) )
				musicPlayer.playMusic( Constants.Gameplay );

			this.runLogic();
		} else if ( this.getState().getCurrState() == 3 ) {

			if ( !musicPlayer.getMusicActive().equals( Constants.Gameplay ) )
				musicPlayer.playMusic( Constants.Gameplay );

			if ( cInstance.isKeyEnterPressed() ) {

				if ( this.getState().getSelectedOption().equals( "UnPause" ) ) {

					this.startTime = System.currentTimeMillis();
					this.getStats().setMaxTime( this.getStats().getTimeLeft() );
					this.getState().setCurrState( 2 );
				}
			}
		} else if ( this.getState().getCurrState() == 4 ) {

			if ( !musicPlayer.getMusicActive().equals( Constants.Menu ) )
				musicPlayer.playMusic( Constants.Menu );

			if ( this.getState().getSelectedOption().equals( "Replay" ) ) {

				objects = new HashMap<>();
				this.getState().setCurrState( 1 );
			}
		}

		if ( this.getState().getSelectedOption().equals( "Help" ) )
			this.readHelpData();

		if ( this.getState().getSelectedOption().equals("Exit") ) {

			musicPlayer.removeActiveEffects( true );
			musicPlayer.stopMusic();

			System.exit(0);
		}
	}

	private void setLevel( String s ) {

		this.addPlayer();
		switch ( s ) {

			case "Practice":
				this.getStats().setWalkerCount( 1 ); this.getStats().setSeekerCount( 1 );
				this.getStats().setWinScore( 6 ); this.getStats().setMaxTime( 600 );
				this.getPlayer().setAmmo( 50 );
				break;

			case "Easy":
				this.getStats().setWalkerCount( 2 ); this.getStats().setSeekerCount( 0 );
				this.getStats().setWinScore( 15 ); this.getStats().setMaxTime( 600 );
				this.getPlayer().setAmmo( 20 );
				break;

			case "Medium":
				this.getStats().setWalkerCount( 3 ); this.getStats().setSeekerCount( 1 );
				this.getStats().setWinScore( 30 ); this.getStats().setMaxTime( 420 );
				this.getPlayer().setAmmo( 25 );
				break;

			case "Hard":
				this.getStats().setWalkerCount( 5 ); this.getStats().setSeekerCount( 2 );
				this.getStats().setWinScore( 50 ); this.getStats().setMaxTime( 300 );
				this.getPlayer().setAmmo( 30 );
				break;

			default:
				break;
		}

		this.setTileMap( s );

		for ( int i = 0; i < this.getStats().getWalkerCount(); i++ )
			this.addWalker();

		for ( int i = 0; i < this.getStats().getSeekerCount(); i++ )
			this.addSeeker();

		this.addScorer();

		this.envCol = new EnvironmentCollisionsCheck(
				this.getObjects(), this.accessInteractingTiles().getTilePositions() );
		this.colCheck = new ObjectCollisionsCheck( this.getObjects() );
		musicPlayer.removeActiveEffects( true );

		this.getStats().setReusableTimer( 0 );
		this.getStats().setTimeLeft( this.getStats().getMaxTime() );
		this.getStats().setWalkerKilled( 0 );
		this.getStats().setSeekerKilled( 0 );

		this.getState().setCurrState( 3 );
	}

	public void runLogic() {

		if ( this.getCInstance().isKeyPPressed() ) {

			this.getState().setCurrState( 3 );
		}
		if ( !this.getPlayer().doesExists() )
			this.getState().setCurrState( 4 );
		if ( this.getPlayer().getScorerTaken() >= this.getStats().getWinScore() ) {

			this.getState().setCurrState(4);
			this.getStats().setLevelWon( true );
		}
		if ( this.getStats().getTimeLeft() <= 0 ) {

			this.getStats().setLevelWon( false );
			this.getState().setCurrState( 4 );
		}

		playerLogic();
		projectileLogic();
		walkerLogic();
		seekerLogic();
		scorerLogic();
		timer();
		musicPlayer.removeActiveEffects( false );

		this.colCheck.setObjectList( this.getObjects() );
		this.colCheck.checkForCollisions();
		this.setObjects( this.colCheck.getObjectList() );

		this.envCol.setObjectList( this.getObjects() );
		this.envCol.checkForCollisions();
		this.setObjects( envCol.getObjectList() );
	}

	private void setTileMap( String s ) {

		TnI = new TilesNonInteracting();
		TI = new TilesInteracting( s );
	}

	public TilesInteracting accessInteractingTiles() {

		return this.TI;
	}

	public TilesNonInteracting accessNonInteractingTiles() {

		return this.TnI;
	}

	private void timer() {

		long elapsedTime = System.currentTimeMillis() - this.startTime;
		long elapsedSeconds = elapsedTime / 1000;

		this.getStats().setTimeLeft( this.getStats().getMaxTime() - elapsedSeconds );
	}

	private void readHelpData() {

		helpContent = Collections.emptyList();
		try {

			helpContent = Files.readAllLines( Paths.get( "res/help.txt" ) );
		} catch ( IOException e ) {

			e.getMessage();
		}
	}

	public List< String > getHelpContent() {

		return this.helpContent;
	}
	
	private void playerLogic() {
		
		this.getPlayer().playerLogic();
	}
	
	private void projectileLogic() {
		
		ArrayList< Projectile > pList;
		
		if ( ( this.getPlayer().getSpaceKey() ) && ( this.getPlayer().getAmmo() >= 1 ) ) {
			
			this.addProjectile();
			musicPlayer.addSoundEffects( Constants.Fireball );
		}

		pList = this.getProjectileList();
		this.getPlayer().setSpaceKey( false );
		
		pList.forEach( ( p ) -> {
			
			p.projectileLogic();
		} );
		
		this.updateProjectileList( pList );
	}
	
	private void walkerLogic() {
		
		ArrayList<Walker> wList = this.getWalkerList();
		boolean chase = false;
		
		if ( wList != null ) {
			
			for ( int i = 0; i < wList.size(); i++ ) {
		
				wList.get( i ).setPlayerVariables( this.getPlayer().getCentre(), this.getPlayer().getBounds() );
				wList.get( i ).walkerLogic();

				if ( wList.get( i ).getChasingTarget() )
					chase = true;
			}

			if ( chase && !musicPlayer.getMusicActive().equals( Constants.Chase ) )
				musicPlayer.playMusic( Constants.Chase );
			else if ( !chase && !musicPlayer.getMusicActive().equals( Constants.Gameplay ) )
				musicPlayer.playMusic( Constants.Gameplay );
		}
		
		this.updateWalkerList( wList );
	}
	
	private void seekerLogic() {

		ArrayList<Seeker> sList = this.getSeekerList();
		
		if ( sList != null ) {
			
			for ( int i = 0; i < sList.size(); i++ ) {
				
				sList.get( i ).setPlayerVariables( this.getPlayer().getCentre(), this.getPlayer().getBounds() );
				sList.get( i ).seekerLogic();
			}
		}
		
		this.updateSeekerList( sList );
	}
	
	private void scorerLogic() {

		ArrayList<Scorer> scrList;
		
		if ( this.getScorerList().size() == 0 )
			this.addScorer();
		else {

			scrList = this.getScorerList();
			this.updateScorerList( scrList );
		}
	}
	
	private void addPlayer() {
			
		if ( !this.getObjects().containsKey( Constants.PLAYER_KEY) ) {
			
			ArrayList<GameObject> p = new ArrayList<>(1);
			p.add( new Player() );
			this.getObjects().put( Constants.PLAYER_KEY, p );
		} else {

			this.getObjects().get( Constants.PLAYER_KEY ).add( new Player() );
		}
	}
		
	private void addWalker() {

		Walker w = new Walker( this.getState().getSelectedOption() );
		w.setPosToAvoid( this.accessInteractingTiles().getPositionsArray() );

		if ( !this.getObjects().containsKey( Constants.WALKER_KEY ) ) {
			
			ArrayList< GameObject > wAL = new ArrayList<>();
			wAL.add( w );
			this.getObjects().put( Constants.WALKER_KEY, wAL );
			
		} else {
			
			this.getObjects().get( Constants.WALKER_KEY ).add( w );
		}
	}
	
	private void updateWalkerList( ArrayList<Walker> w ) {
		
		ArrayList<GameObject> wG = new ArrayList<>();
		
		for ( Walker i: w ) {
			
			if ( i.getHealth() > 0 )
				wG.add( i );
			else if ( i.getHealth() <= 0 ) {

				this.getStats().setWalkerKilled( this.getStats().getWalkerKilled() + 1 );
				this.getPlayer().setScorerTaken( this.getPlayer().getScorerTaken() + 3 );

				this.getStats().setReusableTimer( 5 );
				this.getStats().setReusableMsg( "WALKER DEAD | +3 GEMS" );
			}
		}
		
		this.getObjects().replace( Constants.WALKER_KEY , wG );
	}
	
	private void addSeeker() {
		
		if ( !this.getObjects().containsKey( Constants.SEEKER_KEY ) ) {
			
			ArrayList< GameObject > s = new ArrayList<>();
			s.add( new Seeker( this.getState().getSelectedOption() ) );
			this.getObjects().put( Constants.SEEKER_KEY, s );
			
		} else {
			
			this.getObjects().get( Constants.SEEKER_KEY ).add( new Seeker( this.getState().getSelectedOption() ) );
		}
	}
	
	private void updateSeekerList( ArrayList<Seeker> s ) {
		
		ArrayList<GameObject> sG = new ArrayList<>();
		
		for ( Seeker i: s ) {
			
			if ( i.getHealth() > 0 )
				sG.add( i );
			else if ( i.getHealth() <= 0 ) {

				this.getStats().setSeekerKilled( this.getStats().getSeekerKilled() + 1 );
				this.getPlayer().setAmmo( this.getPlayer().getAmmo() + 8 );

				this.getStats().setReusableTimer( 5 );
				this.getStats().setReusableMsg( "SEEKER DEAD | +8 AMMO" );
			}
		}
		
		this.getObjects().replace( Constants.SEEKER_KEY, sG );
	}
		
	private void addProjectile() {

		Point3f centre = new Point3f( 
				this.getPlayer().getCentre().getX(),
				this.getPlayer().getCentre().getY(),
				this.getPlayer().getCentre().getZ() );
		
		Projectile p = new Projectile( centre, Constants.Projectile_WIDTH, 
				Constants.Projectile_HEIGHT );
		
		p.setCallingKey( this.getPlayer().previousKey() );
		p.setVelocity( this.getPlayer().getVelocity() );
		
		if ( !this.getObjects().containsKey( Constants.PROJECTILE_KEY ) ) {
			
			ArrayList< GameObject > r = new ArrayList<>();
			r.add( p );
			this.getObjects().put( Constants.PROJECTILE_KEY, r );
		
		} else {
			
			this.getObjects().get( Constants.PROJECTILE_KEY ).add( p );
		}
	}
	
	private void updateProjectileList( ArrayList<Projectile> p) {
		
		ArrayList<GameObject> pG = new ArrayList<>();
		
		for ( Projectile i: p ) {
			
			if ( i.doesExists() )
				pG.add( i );
			else
				musicPlayer.addSoundEffects( Constants.Explosion );
		}
		
		this.getObjects().replace( Constants.PROJECTILE_KEY , pG);
	}
	
	private void addScorer() {

		Scorer s = new Scorer();
		s.setPosToAvoid( this.accessInteractingTiles().getPositionsArray() );

		if ( !this.getObjects().containsKey( Constants.SCORER_KEY ) ) {
			
			ArrayList<GameObject> scAL = new ArrayList<>();
			scAL.add( s );
			this.getObjects().put( Constants.SCORER_KEY, scAL );
		}
		else {
			
			this.getObjects().get( Constants.SCORER_KEY ).add( s );
		}
	}
	
	private void updateScorerList( ArrayList<Scorer> scr ) {
		
		ArrayList<GameObject> scrG = new ArrayList<>();
		
		for ( Scorer s: scr ) {
			
			if ( s.doesExists() )
				scrG.add( s );
			else
				musicPlayer.addSoundEffects( Constants.Score );
		}
		
		this.getObjects().replace( Constants.SCORER_KEY , scrG );
	}
	
	public HashMap< String, ArrayList< GameObject > > getObjects() {
		
		return this.objects;
	}
	
	private void setObjects( HashMap<String, ArrayList<GameObject>> o ) {
		
		this.objects = o;
	}
	
	public Player getPlayer() {
		
		return ( Player ) this.getObjects().get( Constants.PLAYER_KEY ).get(0);
	}
	
	public ArrayList< Projectile > getProjectileList() {
		
		ArrayList<Projectile> p = new ArrayList<Projectile>();
		
		if ( this.getObjects().get( Constants.PROJECTILE_KEY ) != null ) {
			
			for ( int i = 0; i < this.getObjects().get( Constants.PROJECTILE_KEY ).size(); i++ ) {
				
				p.add( ( Projectile ) this.getObjects().get( Constants.PROJECTILE_KEY ).get( i ) );
			}
		}
		
		return p;
	}
	
	public ArrayList< Walker > getWalkerList() {
		
		ArrayList<Walker> w = new ArrayList<Walker>();
		
		if ( this.getObjects().get( Constants.WALKER_KEY ) != null ) {
			
			for ( int i = 0; i < this.getObjects().get( Constants.WALKER_KEY ).size(); i++ ) {
				
				w.add( ( Walker ) this.getObjects().get( Constants.WALKER_KEY ).get( i ) );
			}
		}
		
		return w;
	}
	
	public ArrayList< Seeker > getSeekerList() {
		
		ArrayList<Seeker> s = new ArrayList<Seeker>();
		
		if ( this.getObjects().get( Constants.SEEKER_KEY ) != null ) {
			
			for ( int i = 0; i < this.getObjects().get( Constants.SEEKER_KEY ).size(); i++ ) {
				
				s.add( ( Seeker ) this.getObjects().get( Constants.SEEKER_KEY ).get( i ) );
			}
		}
		
		return s;
	}
	
	public ArrayList< Scorer > getScorerList() {
		
		ArrayList<Scorer> sc = new ArrayList<Scorer>();
		
		if ( this.getObjects().get( Constants.SCORER_KEY ) != null ) {
			
			for ( int i = 0; i < this.getObjects().get( Constants.SCORER_KEY ).size(); i++ ) {
				
				sc.add( ( Scorer ) this.getObjects().get( Constants.SCORER_KEY ).get( i ) );
			}
		}
		
		return sc;
	}

	private Controller getCInstance() {

		return cInstance;
	}

	public StateCheck getState() {

		return this.state;
	}

	public StatCheck getStats() {

		return this.stats;
	}
}