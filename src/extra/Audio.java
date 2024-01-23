/**
 @author: Awais Javed
 */

package extra;

import util.Constants;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {

    private Clip clip;
    public String type;

    private long time;
    private long startTime;

    public Audio() {
    }

    public void setFile( String audioState ) {

        String filename = "";
        switch ( audioState ) {

            case Constants.Menu:
                filename = "res/menu.wav"; break;

            case Constants.Fireball:
                filename = "res/fireball.wav"; break;

            case Constants.Explosion:
                filename = "res/boom.wav"; break;

            case Constants.Gameplay:
                filename = "res/gameplay.wav"; break;

            case Constants.Chase:
                filename = "res/chase.wav"; break;

            case Constants.Score:
                filename = "res/score.wav"; break;

            default:
                break;
        }
        type = audioState;

        try {

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File( filename ) );
            clip = AudioSystem.getClip();
            clip.open( audioInputStream );
        } catch ( Exception e ) {

            System.err.println( e.getMessage() );
        }
    }

    public void playSound() {

        try {

            startTime = System.currentTimeMillis();
            clip.setMicrosecondPosition( 1 );
            clip.start();
        } catch ( Exception e ) {

            System.err.println( e.getMessage() );
        }
    }

    public void stopSound( boolean closeClip ) {

        try {

            clip.stop();

            if ( closeClip )
                clip.close();
        } catch( Exception e ) {

            System.err.println( e.getMessage() );
        }
    }

    public boolean isTimeUp() {

        long elapsedTime = System.currentTimeMillis() - this.startTime;
        long elapsedSec = elapsedTime / 1000;

        if ( elapsedSec >= this.getTime() )
            return true;
        else
            return false;
    }

    public void playClipOnLoop() {

        this.clip.loop( Clip.LOOP_CONTINUOUSLY );
    }

    public void setTime( long t ) {

        this.time = t;
    }

    public long getTime() {

        return this.time;
    }
}