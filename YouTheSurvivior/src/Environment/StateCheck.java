/**
 @author: Awais Javed
 Student Number: 11543303
 */

package Environment;

import main.Controller;
import util.Constants;

import java.awt.*;

public class StateCheck {

    private Controller cInstance = Controller.getInstance();
    private Rectangle menuDimensions;
    private int currState;
    private String[] State1Menu;
    private String[] State3Menu;
    private String[] State4Menu;
    private int currOption;
    private String selectedOption;

    public StateCheck() {

        this.setMenuDimensions( ( Constants.BOUNDARY_RIGHT / 2 ) - 150, 100, 300, 400  );
        this.setCurrState( 1 );
        this.setCurrOption( 0 );
        State1Menu = new String[] { "Practice", "Easy", "Medium", "Hard", "Help", "Exit" };
        State3Menu = new String[] { "UnPause", "Help", "Exit" };
        State4Menu = new String[] { "Replay", "Help", "Exit" };

        this.setSelectedOption( this.getCurrentMenu()[ this.getCurrOption() ] );
    }

    public void stateLogic() {

        if ( cInstance.isKeyWPressed() ) {

            if ( ( this.getCurrOption() - 1 ) < 0 ) {

                this.setCurrOption( this.getCurrentMenu().length - 1 );
            } else {

                this.setCurrOption( this.getCurrOption() - 1 );
            }

        } else if ( cInstance.isKeySPressed() ) {

            if ( ( this.getCurrOption() + 1 ) >= this.getCurrentMenu().length ) {

                this.setCurrOption( 0 );
            } else {

                this.setCurrOption( this.getCurrOption() + 1 );
            }

        } else if (cInstance.isKeyEnterPressed() ) {

            this.setSelectedOption( this.getCurrentMenu()[ this.getCurrOption() ] );
        }
    }

    private void setMenuDimensions( int xpos, int ypos, int w, int h ) {

        this.menuDimensions = new Rectangle( xpos, ypos, w, h);
    }

    public Rectangle getMenuDimensions() {

        return this.menuDimensions;
    }

    public void setCurrState( int i ) {

        this.currState = i;
        this.setCurrOption( 0 );
    }

    public int getCurrState() {

        return this.currState;
    }

    private void setCurrOption( int i ) {

        this.currOption = i;
    }

    public int getCurrOption() {

        return this.currOption;
    }

    private void setSelectedOption( String s ) {

        this.selectedOption = s;
    }

    public String getSelectedOption() {

        return this.selectedOption;
    }

    public String[] getCurrentMenu() {

        String[] state = null;

        switch ( this.getCurrState() ) {

            case 1: state = this.State1Menu; break;
            case 3: state = this.State3Menu; break;
            case 4: state = this.State4Menu; break;
            default: break;
        }

        return state;
    }
}
