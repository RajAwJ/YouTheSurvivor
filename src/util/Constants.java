/**
 @author: Awais Javed
 */

package util;

public interface Constants {

	/************ Screen And Boundary ***********/
	int SCREEN_WIDTH = 1100;
	int SCREEN_HEIGHT = 900;
	int BOUNDARY_LEFT = 20;
	int BOUNDARY_TOP = 20;
	int BOUNDARY_RIGHT = SCREEN_WIDTH - BOUNDARY_LEFT;
	int BOUNDARY_BOTTOM = SCREEN_HEIGHT - BOUNDARY_TOP;

	/************ Tile Map Variables ************/

		/******** Border Tile *******************/
		String TILE_KEY_BORDER = "BorderTile";
		int TILE_WIDTH = 30;
		int TILE_HEIGHT = 30;
		String TileBorderTexture = "res/tile_border.png";
		int TileBorderTextureRows = 1;
		int TileBorderTextureColumns = 1;
		int TileBorderSpritePixel = 32;

		/****************************************/
		/******** Floor Tile 1 ******************/
		String TILE_KEY_FLOOR1 = "FloorTile1";
		int TILE_FLOOR1_WIDTH = 50;
		int TILE_FLOOR1_HEIGHT = 50;
		String TileFloor1Texture = "res/tile_floor.png";
		int TileFloor1TextureRows = 1;
		int TileFloor1TextureColumns = 1;
		int TileFloor1SpritePixel = 32;

		/****************************************/
		/******** Blocks Tile *******************/
		String TILE_KEY_BLOCKS = "BlocksTile";
		int TILE_BLOCKS_WIDTH = 50;
		int TILE_BLOCKS_HEIGHT = 50;
		String TileBlocksTexture = "res/tile_blocks.png";
		int TileBlocksTextureRows = 1;
		int TileBlocksTextureColumns = 1;
		int TileBlocksSpritePixel = 32;
		/****************************************/

	/************ Player Variables ************/
	String PLAYER_KEY = "Player";
	int PLAYER_WIDTH = 120;
	int PLAYER_HEIGHT = 120;
	String PlayerTexture = "res/player_swordsman.png";
	int PlayerTextureRows = 12;
	int PlayerTextureColumns = 11;
	int PlayerSpritePixel = 128;
	int PlayerHealth = 20;
	int PlayerAmmo = 20;

	/************ Seeker Variables ************/
	String SEEKER_KEY = "Seeker";
	int Seeker_WIDTH = 80;
	int Seeker_HEIGHT = 80;
	String SeekerTexture = "res/seeker_goblin.png";
	int SeekerTextureRows = 5;
	int SeekerTextureColumns = 11;
	int SeekerSpritePixel = 64;
	int SeekerInitialHealth = 40;
	int SeekerDamage = 20;

	/************ Walker Variables ************/
	String WALKER_KEY = "Walker";
	int Walker_WIDTH = 120;
	int Walker_HEIGHT = 120;
	String WalkerTexture = "res/walker_zombie.png";
	int WalkerTextureRows = 4;
	int WalkerTextureWalkColumnsStart = 4;
	int WalkerTextureWalkColumnsEnd = 11;
	int WalkerSpritePixel = 128;
	int WalkerInitialHealth = 20;
	int WalkerDamage = 20;

	/************ Projectile Variables ************/
	String PROJECTILE_KEY = "Projectile";
	int Projectile_WIDTH = 40;
	int Projectile_HEIGHT = 40;
	String ProjectileTexture = "res/fireball.png";
	int ProjectileTextureRows = 1;
	int ProjectileTextureColumnsEnd = 8;
	int ProjectileTextureColumnsStart = 3;
	int ProjectileSpritePixel = 64;
	int ProjectileDamage = 5;

	/************ Scorer Variables ************/
	String SCORER_KEY = "Scorer";
	int Scorer_WIDTH = 20;
	int Scorer_HEIGHT = 20;
	String ScorerTexture = "res/score_crystal.png";
	int ScorerTextureRows = 1;
	int ScorerTextureColumns = 8;
	int ScorerSpritePixel = 32;

	/************ Misc. Variables ************/
	int X_DEFAULT = 500;
	int Y_DEFAULT = 400;
	
	int MENU_DELAY = 80;
	int PLAY_DELAY = 50;
	int STEP = 2;

	/************ Audio States ***************/

	String Menu = "Menu";
	String Fireball = "Fireball";
	String Explosion = "Explosion";
	String Gameplay = "Gameplay";
	String Chase = "Chase";
	String Score = "Score";

	/************ ************** *************/
}