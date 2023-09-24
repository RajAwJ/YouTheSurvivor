/**
 @author: Awais Javed
 Student Number: 11543303
 */

package Environment;

import extra.Audio;
import util.Constants;

import java.util.ArrayList;

public class ManageAudio {

    private Audio music;
    private String musicActive;
    private ArrayList< Audio > activeSoundEffects;

    public ManageAudio() {

        this.setMusicActive( "" );
        activeSoundEffects = new ArrayList<>();
    }

    public void playMusic( String audioState ) {

        if ( audioState.equals( Constants.Menu ) || audioState.equals( Constants.Chase ) ||
                audioState.equals( Constants.Gameplay ) ) {

            if ( !this.getMusicActive().equals( audioState ) )
                this.stopMusic();

            this.setMusicActive( audioState );
        }
        music = new Audio();
        music.setFile( audioState );
        music.playClipOnLoop();
        music.playSound();
    }

    public void stopMusic() {

        if ( this.getMusicActive().equals( Constants.Menu ) || this.getMusicActive().equals( Constants.Gameplay ) ||
            this.getMusicActive().equals( Constants.Chase ) ) {

            music.stopSound( true );
            this.setMusicActive( "" );
        }
    }

    public void addSoundEffects( String audioState ) {

        if ( audioState.equals( Constants.Fireball ) || audioState.equals( Constants.Explosion )
                || audioState.equals( Constants.Score ) ) {

            Audio a = new Audio();
            a.setFile( audioState );
            a.setTime( 2 );
            a.playSound();
            activeSoundEffects.add( a );
        }
    }

    public void removeActiveEffects( boolean emptyArray ) {

        if ( emptyArray ) {

            activeSoundEffects.clear();
            activeSoundEffects = new ArrayList<>();
        } else if ( activeSoundEffects.size() != 0 ) {

            ArrayList< Audio > tmp = new ArrayList<>( activeSoundEffects );
            activeSoundEffects.clear();
            for ( Audio a : tmp ) {

                if ( a.isTimeUp() )
                    a.stopSound( true );
                else
                    activeSoundEffects.add( a );
            }
        }
    }

    public void setMusicActive( String musicType ) {

        this.musicActive = musicType;
    }

    public String getMusicActive() {

        return this.musicActive;
    }
}