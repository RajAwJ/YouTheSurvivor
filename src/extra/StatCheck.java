/**
 @author: Awais Javed
 */

package extra;

public class StatCheck {

    private int walkerCount;
    private int walkerKilled;

    private int seekerCount;
    private int seekerKilled;

    private int winScore;
    private long maxTime;
    private long timeLeft;

    private long reusableTimer;
    private String reusableMsg;

    private boolean levelWon;

    public StatCheck() {

        this.setLevelWon( false );
        this.setWalkerKilled( 0 );
        this.setSeekerKilled( 0 );
        this.setTimeLeft( 0 );
        this.setReusableTimer( 0 );
        this.setReusableMsg( "" );
    }

    public void setWalkerCount( int c ) {

        this.walkerCount = c;
    }

    public int getWalkerCount() {

        return this.walkerCount;
    }

    public void setWalkerKilled( int k ) {

        this.walkerKilled = k;
    }

    public int getWalkerKilled() {

        return this.walkerKilled;
    }

    public void setSeekerCount( int c ) {

        this.seekerCount = c;
    }

    public int getSeekerCount() {

        return this.seekerCount;
    }

    public void setSeekerKilled( int k ) {

        this.seekerKilled = k;
    }

    public int getSeekerKilled() {

        return this.seekerKilled;
    }

    public void setWinScore( int w ) {

        this.winScore = w;
    }

    public int getWinScore() {

        return this.winScore;
    }

    public void setMaxTime( long t ) {

        this.maxTime = t;
    }

    public long getMaxTime() {

        return this.maxTime;
    }

    public void setTimeLeft( long t ) {

        if ( ( this.getTimeLeft() > t ) && ( this.getReusableTimer() > 0 ) )
                this.setReusableTimer( this.getReusableTimer() - 1 );
        this.timeLeft = t;
    }

    public long getTimeLeft() {

        return this.timeLeft;
    }

    public void setLevelWon( boolean b ) {

        this.levelWon = b;
    }

    public boolean getLevelWon() {

        return this.levelWon;
    }

    public void setReusableTimer( long t ) {

        this.reusableTimer = t;
    }

    public long getReusableTimer() {

        return this.reusableTimer;
    }

    public void setReusableMsg( String s ) {

        this.reusableMsg = s;
    }

    public String getReusableMsg() {

        return this.reusableMsg;
    }
}