/**
 @author: Awais Javed
 */

package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {

	private static boolean KeyAPressed = false;
	private static boolean KeyWPressed = false;
	private static boolean KeyDPressed = false;
	private static boolean KeySPressed = false;
	private static boolean KeyQPressed = false;
	private static boolean KeyPPressed = false;
	private static boolean KeySpacePressed = false;
	private static boolean KeyEnterPressed = false;
	
	private static final Controller instance = new Controller();
	
	public Controller() {
		
	}
	
	public static Controller getInstance() {
		
		return instance;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {

		switch ( e.getKeyChar() ) {

			case 'p': setKeyPPressed( true ); break;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyChar()) {
		
			case 'a': setKeyAPressed(true); break;
			case 'w': setKeyWPressed(true); break;
			case 'd': setKeyDPressed(true); break;
			case 's': setKeySPressed(true); break;
			case 'q': setKeyQPressed(true); break;
			case 'p': setKeyPPressed(true); break;
			case ' ': setKeySpacePressed(true); break;
			case 10: setKeyEnterPressed(true); break;
			default: break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		switch (e.getKeyChar()) {
		
			case 'a': setKeyAPressed(false); break;
			case 'w': setKeyWPressed(false); break;
			case 'd': setKeyDPressed(false); break;
			case 's': setKeySPressed(false); break;
			case 'q': setKeyQPressed(false); break;
			case 'p': setKeyPPressed(false); break;
			case ' ': setKeySpacePressed(false); break;
			case 10: setKeyEnterPressed(false); break;
			default: break;
		}
	}
	
	public boolean isKeyAPressed() {

		return KeyAPressed;
	}
	
	public void setKeyAPressed(boolean keyAPressed) {
		
		KeyAPressed = keyAPressed;
	}

	public boolean isKeyWPressed() {
		
		return KeyWPressed;
	}
	
	public void setKeyWPressed(boolean keyWPressed) {
		
		KeyWPressed = keyWPressed;
	}

	public boolean isKeyDPressed() {
		
		return KeyDPressed;
	}
	
	public void setKeyDPressed(boolean keyDPressed) {
		
		KeyDPressed = keyDPressed;
	}

	public boolean isKeySPressed() {
		
		return KeySPressed;
	}
	
	public void setKeySPressed(boolean keySPressed) {
		
		KeySPressed = keySPressed;
	}

	public boolean isKeyQPressed() {

		return KeyQPressed;
	}

	public void setKeyQPressed( boolean keyQPressed ) {

		KeyQPressed = keyQPressed;
	}

	public boolean isKeyPPressed() {

		return KeyPPressed;
	}

	public void setKeyPPressed( boolean keyPPressed ) {

		KeyPPressed = keyPPressed;
	}

	public boolean isSpaceKeyPressed() {
		
		return KeySpacePressed;
	}
	
	public void setKeySpacePressed(boolean keySpacePressed) {
		
		KeySpacePressed = keySpacePressed;
	}

	public boolean isKeyEnterPressed() {

		return KeyEnterPressed;
	}

	public void setKeyEnterPressed( boolean keyEnterPressed ) {

		KeyEnterPressed = keyEnterPressed;
	}
}