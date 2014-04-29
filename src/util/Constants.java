package util;

/**
 *
 * @author Enrique Martín Arenal
 */
public class Constants {

    // Visible game world is 16 meters wide
    public static final float VIEWPORT_WIDTH = 16.0f;
    // Visible game world is 12 meters tall
    public static final float VIEWPORT_HEIGHT = 12.0f;
    // GUI Width
    public static final float VIEWPORT_GUI_WIDTH = 800.0f;
    // GUI Height
    public static final float VIEWPORT_GUI_HEIGHT = 480.0f;
    // Location of description file for texture atlas
    public static final String TEXTURE_ATLAS_OBJECTS
            = "images/canyonbunny.pack";
    //Cards width
    public static final float CARD_WIDTH = 2.8f;
    //Cards height
    public static final float CARD_HEIGHT = 4.1f;
    //Player hit points at the start of a game
    public static final int HIT_POINTS = 30;
    //Card hit points number width
    public static final float CARD_HIT_NUMBER_WIDTH = 0.529f;
    //Card hit points number height
    public static final float CARD_HIT_NUMBER_HEIGHT = 0.6613f;
    //Card hit points number x, relative to the card x 
    public static final float CARD_HIT_NUMBER_X = 2.05f;
    //Card hit points number x, relative to the card y 
    public static final float CARD_HIT_NUMBER_Y = 1.2f;
    //Card positions on screen
    public static final String IN_MOVE_CARD = "a1";
    public static final String IN_ATTACK_CARD = "a2";
    public static final String IN_ATTACK_ENEMY = "a3";
    public static final String IN_PASS_TURN = "a4";
    public static final String OUT_MOVE_CARD = "a5";
    public static final String OUT_ATTACK_CARD = "a6";
    public static final String OUT_ATTACK_ENEMY = "a7";
    public static final String OUT_PASS_TURN = "a8";

}
