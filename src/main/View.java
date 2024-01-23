/**
 @author: Awais Javed
 */

package main;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.*;

import templates.*;
import util.Constants;
import util.Point3f;

public class View extends JPanel {

	private Model gameWorld;

	private JPanel statDisplay;
	private JLabel displayAmmo;
	private JLabel displayGemsCollected;
	private JLabel displayTimer;

	String level;
	char key = 'a', prevKey;
	private int num = 0;
	private int num2 = 0;
	private int numS = 0;
	private int numSc = 0;
	long startTime;
	
	public View ( Model g, JPanel j ) {

		this.setMinimumSize( new Dimension( Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT ) );
		this.setPreferredSize( new Dimension( Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT ) );

		this.gameWorld = g;
		this.statDisplay = j;

		this.initializeStatDisplay();

		startTime = System.currentTimeMillis();
	}

	private void initializeStatDisplay() {

		this.statDisplay.setMinimumSize( new Dimension( Constants.SCREEN_WIDTH, 50 ) );
		this.statDisplay.setPreferredSize( new Dimension( Constants.SCREEN_WIDTH, 50 ) );

		this.statDisplay.setLayout( new BoxLayout( this.statDisplay, BoxLayout.LINE_AXIS ) );
		this.statDisplay.setBorder( BorderFactory.createRaisedSoftBevelBorder() );
		this.statDisplay.setBackground( Color.ORANGE );

		displayAmmo = new JLabel();
		displayAmmo.setFont( new Font( Font.MONOSPACED, Font.BOLD, 20 ) );
		displayAmmo.setBorder( BorderFactory.createRaisedSoftBevelBorder() );
		displayAmmo.setOpaque( true );
		displayAmmo.setBackground( Color.YELLOW );

		displayTimer = new JLabel();
		displayTimer.setBorder( BorderFactory.createRaisedSoftBevelBorder() );
		displayTimer.setOpaque( true );
		displayTimer.setBackground( Color.WHITE );

		displayGemsCollected = new JLabel();
		displayGemsCollected.setFont( new Font( Font.MONOSPACED, Font.BOLD, 20 ) );
		displayGemsCollected.setBorder( BorderFactory.createRaisedSoftBevelBorder() );
		displayGemsCollected.setOpaque( true );
		displayGemsCollected.setForeground( Color.black );
		displayGemsCollected.setBackground( Color.pink );

		this.statDisplay.add( displayAmmo );
		this.statDisplay.add( Box.createHorizontalStrut( 10 ));
		this.statDisplay.add( displayGemsCollected );
		this.statDisplay.add( Box.createHorizontalGlue() );
		this.statDisplay.add( displayTimer );
	}
	
	private void doDrawing ( Graphics g ) {

		Graphics2D g2d = (Graphics2D) g;

		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		rh.put(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);


		setBackground(Color.WHITE);

		g2d.setRenderingHints(rh);

		int gameState = gameWorld.getState().getCurrState();
		if ( gameState == 1 ) {

			// draw Main Menu
			drawMenu( g2d );
		} else if ( gameState == 2 ) {

			// draw Gameplay
			startGamePlay( g2d );
		} else if ( gameState == 3 ) {

			// draw Pause Screem
			startGamePlay( g2d );
			drawMenu( g2d );
		} else if ( gameState == 4 ) {

			//  draw GameEnd Menu
			drawMenu( g2d );
		}
	}

	private void drawMenu( Graphics2D g2d ) {

		displayStats();

		int x, y = gameWorld.getState().getMenuDimensions().y;
		if ( gameWorld.getState().getSelectedOption().equals( "Help" ) )
			x = 10;
		else
			x = gameWorld.getState().getMenuDimensions().x;


		int w = gameWorld.getState().getMenuDimensions().width;
		int h = gameWorld.getState().getMenuDimensions().height;
		String l = gameWorld.getState().getSelectedOption();

		if ( l.equals( "Practice" ) || l.equals( "Easy" ) ||
				l.equals( "Medium" ) || l.equals( "Hard" ) )
			level = l;

		if ( gameWorld.getState().getCurrState() == 3 ) {

			g2d.setColor(new Color(6, 1, 16, 208));
			g2d.fillRect( 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT );

			g2d.setColor( Color.WHITE );
			g2d.drawRect( x, y, w, h );

			g2d.drawString( "Level: " + level, x, y + h + 20 );
			g2d.drawString( "Walkers Killed: " + gameWorld.getStats().getWalkerKilled(), x, y + h + 60 );
			g2d.drawString( "Seekers Killed: " + gameWorld.getStats().getSeekerKilled(), x, y + h + 80 );

			if ( level.equals( "Practice" ) )
				g2d.drawString( "Piece of cake!", x, y + h + 140 );
			else if ( level.equals( "Easy" ) )
				g2d.drawString( "Your aim may be bad, but your legs are fast. So run!",
						x, y + h + 140 );
			else if ( level.equals( "Medium" ) ) {
				g2d.drawString( "Try not to miss. Think a bit, but not too much.", x, y + h + 140 );
				g2d.drawString( "Environment is your friend, Seeker is not.", x, y + h + 160 );
			} else if ( level.equals( "Hard" ) ) {
				g2d.drawString( "Wrong choice! Everyone & everything hates you.", x, y + h + 140 );
				g2d.drawString( "Why even try. Hope has no meaning here.", x, y + h + 160 );
			}
		} else {

			g2d.setColor( Color.RED );
			g2d.fillRect( x, y, w, h );
		}

		x += ( w / 3 ); y += 50;
		g2d.setFont( new Font( Font.MONOSPACED, Font.BOLD, 30 ) );

		for ( int i = 0; i < gameWorld.getState().getCurrentMenu().length; i++ ) {

			if ( i == gameWorld.getState().getCurrOption() )
				g2d.setColor( Color.GREEN );
			else
				g2d.setColor( Color.WHITE );

			g2d.drawString( gameWorld.getState().getCurrentMenu()[ i ],
					x, y + ( i * 40 ) );
		}

		if ( gameWorld.getState().getSelectedOption().equals( "Help" ) )
			drawHelpScreen( g2d );
	}

	private void drawHelpScreen( Graphics2D g2d ) {

		int x = gameWorld.getState().getMenuDimensions().width + 30;
		int y = 20;
		int w = Constants.BOUNDARY_RIGHT - gameWorld.getState().getMenuDimensions().width - 20;
		int h = Constants.BOUNDARY_BOTTOM - 30;

		g2d.setColor( Color.BLACK );
		g2d.fillRect( x, y, w, h );

		g2d.setColor( Color.WHITE );
		g2d.setFont( new Font( Font.MONOSPACED, Font.BOLD, 15 ) );
		for ( String s: this.gameWorld.getHelpContent() ) {

			g2d.drawString( s, x + 10, y += 15 );
		}
	}

	private void startGamePlay( Graphics2D g2d ) {

			HashMap< BufferedImage, Point3f[] > ni_tPos = gameWorld.accessNonInteractingTiles().getTilePositions();
			ni_tPos.forEach( ( BufferedImage, PointsArr ) ->
			{
				for ( Point3f p: PointsArr ) {

					drawNonInteractingTiles( g2d, BufferedImage, p );
				}
			} );

			HashMap<Tile, Point3f[] > tPosition = gameWorld.accessInteractingTiles().getTilePositions();
			tPosition.forEach( ( Tile, PointsArr ) ->
			{
				for ( Point3f p: PointsArr ) {

					drawInteractingTiles( g2d, Tile, p );
				}
			} );

			drawPlayer( g2d );

			if ( gameWorld.getProjectileList() != null ) {

				gameWorld.getProjectileList().forEach( (p) -> {

					try {
						drawProjectile( g2d, p );
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} );
			}

			if ( gameWorld.getWalkerList() != null ) {

				gameWorld.getWalkerList().forEach( ( w ) -> {

					drawWalker( g2d, w );
				} );
			}

			if ( gameWorld.getSeekerList() != null ) {

				gameWorld.getSeekerList().forEach( ( s ) -> {

					drawSeeker( g2d, s, gameWorld.getSeekerList().indexOf( s ) );
				} );
			}

			if ( gameWorld.getScorerList() != null ) {

				gameWorld.getScorerList().forEach( ( sc ) -> {

					drawScorer( g2d, sc );
				});
			}
			displayStats();
	}
	
	public void updateView() {
		
		this.repaint();
	}

	private void drawNonInteractingTiles( Graphics2D g2d, BufferedImage bI, Point3f pos ) {

		int width = Constants.TILE_FLOOR1_WIDTH;
		int height = Constants.TILE_FLOOR1_HEIGHT;

		g2d.drawImage( bI, ( int ) pos.getX(), ( int ) pos.getY(), width, height, null );
	}
	private void drawInteractingTiles( Graphics2D g2d, Tile t, Point3f pos ) {

		int width = t.getWidth();
		int height = t.getHeight();

		BufferedImage bi = t.getSprites();
		g2d.drawImage( bi, ( int ) pos.getX(), ( int ) pos.getY(), width, height, null );
	}
	
	private void drawPlayer( Graphics2D g2d ) {

		int x = ( int ) gameWorld.getPlayer().getBounds().getX();
		int y = ( int ) gameWorld.getPlayer().getBounds().getY();
		
		prevKey = key;
		key = gameWorld.getPlayer().previousKey();

		int line = 0;
		line = gameWorld.getPlayer().getSpriteDirectionLine();

		if ( (prevKey == key) && (num < 11) ) {

			line = line + num;
			num++;
		}
		else {
			
			num = 0;
		}
		
		BufferedImage[] bi = gameWorld.getPlayer().getSprites();
		
		g2d.drawImage( bi[line], x, y, gameWorld.getPlayer().getWidth(), gameWorld.getPlayer().getHeight(), null );
		if ( this.gameWorld.getStats().getReusableTimer() > 0 ) {

			g2d.setFont( new Font( Font.DIALOG, Font.BOLD, 12 ) );
			g2d.setColor( Color.BLUE );
			g2d.drawString( gameWorld.getStats().getReusableMsg(), x , y );
		}
	}
	
	private void drawProjectile( Graphics2D g2d, Projectile p ) throws InterruptedException {
					
		double x = p.getBounds().getX();
		double y = p.getBounds().getY();
		BufferedImage[] bi = p.getSprites();
		AffineTransform at = new AffineTransform();
		
		at.translate( x + ( p.getWidth() / 2 ), y + ( p.getHeight() / 2 ) );
		
		if ( p.getCallingKey() == 'w' ) {
			
			at.quadrantRotate( 1 );
			//at.rotate( Math.PI / 2 );
		}
		else if ( p.getCallingKey() == 'd' ) {
			at.quadrantRotate( 2 );
			//at.rotate( Math.PI );
		}
		else if ( p.getCallingKey() == 's' ) {
			
			at.quadrantRotate( 3 );
			//at.rotate( (- Math.PI ) / 2 );
		}
		
		at.translate( -( p.getWidth() / 2 ), -( p.getHeight() / 2 ) );
		at.scale( 0.5, 0.5 );
		
		g2d.drawImage( bi[ p.getSpriteIterator() ], at, null );
	}
	
	private void drawWalker( Graphics2D g2d, Walker w ) {

		int x = ( int ) w.getBounds().getX();
		int y = ( int ) w.getBounds().getY();
		
		BufferedImage[] bi = w.getSprites();
		
		int line = w.getWalkerDirection();
		
		if ( this.num2 < 7 ) {
			
			line = line + num2;
			this.num2++;
			
		} else {
			
			this.num2 = 0;
		}
		
		g2d.drawImage( bi[ line ] , x, y, w.getWidth(), w.getHeight(), null );

		g2d.setColor( Color.RED );
		g2d.fillRect( ( x + ( w.getWidth() / 2 ) ), ( y + ( w.getHeight() / 3 ) ) - 12, 22, 14 );
		g2d.setFont( new Font( Font.SERIF, Font.BOLD, 15 ) );
		g2d.setColor( Color.WHITE );
		g2d.drawString( "" + w.getHealth(), ( x + ( w.getWidth() / 2 ) ),  ( y + ( w.getHeight() / 3 ) ) );
	}
	
	private void drawSeeker( Graphics g2d, Seeker s, int pos ) {

		int x = ( int ) s.getBounds().getX();
		int y = ( int ) s.getBounds().getY();
		
		BufferedImage[] bi = s.getSprites();
		
		int line = s.getSeekerDirection();
		if ( this.numS < 8 ) {
			
			line = line + this.numS;
			this.numS++;
		}
		else {
			
			this.numS = 0;
		}
		
		g2d.drawImage( bi[ line ], x, y, s.getWidth(), s.getHeight(), null );

		g2d.setColor( Color.RED );
		g2d.fillRect( ( x + ( s.getWidth() / 2 ) ), y - 10, 22, 14 );
		g2d.setFont( new Font( Font.SANS_SERIF, Font.BOLD, 15 ) );
		g2d.setColor( Color.WHITE );
		g2d.drawString( "" + s.getHealth(), ( x + ( s.getWidth() / 2 ) ), y + 2 );
	}
	
	private void drawScorer( Graphics2D g2d, Scorer sc ) {
		
		int x = ( int ) sc.getBounds().getX();
		int y = ( int ) sc.getBounds().getY();
		
		BufferedImage[] bi = sc.getSprites();
		
		if ( this.numSc < 7 )
			this.numSc++;
		else
			this.numSc = 0;
		
		g2d.drawImage( bi[ this.numSc ], x, y, sc.getWidth(), sc.getHeight(), null );
	}

	private void displayStats() {

		if ( gameWorld.getState().getCurrState() == 4 ) {

			displayTimer.setForeground( Color.RED );
			displayTimer.setFont( new Font( Font.MONOSPACED, Font.BOLD, 30 ) );

			if ( gameWorld.getStats().getLevelWon() ) {
				displayTimer.setText( " LEVEL WON " );
			} else {
				displayTimer.setText( " LEVEL LOST " );
			}
		} else if ( gameWorld.getState().getCurrState() != 1 ) {

			if ( gameWorld.getPlayer().getSpeedPower() )
				this.statDisplay.setBackground(Color.RED);
			else
				this.statDisplay.setBackground(Color.ORANGE);

			displayAmmo.setText(" Ammo : " + gameWorld.getPlayer().getAmmo() + " ");

			long elapsedSeconds = gameWorld.getStats().getTimeLeft() % 60;
			long elapsedMinutes = gameWorld.getStats().getTimeLeft() / 60;

			displayTimer.setFont( new Font( Font.MONOSPACED, Font.BOLD, 25 ) );
			displayTimer.setForeground( Color.BLACK );
			displayTimer.setText(" Time Left: " + elapsedMinutes + ":" + elapsedSeconds + " ");
			displayGemsCollected.setText(" Gems : " + gameWorld.getPlayer().getScorerTaken() + " " +
					"/ " + gameWorld.getStats().getWinScore() + " ");
		}
	}
	public void paintComponent( Graphics g ) {
		
		super.paintComponent( g );
		doDrawing( g );
		
		Toolkit.getDefaultToolkit().sync();
	}
}