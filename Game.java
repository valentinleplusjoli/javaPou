package moon_lander;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.Clock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Random;

/**
 * Actual game.
 * 
 * @author www.gametutorial.net
 */

public class Game {

    /**
     * The space rocket with which player will have to land.
     */
    private PlayerRocket playerRocket;
    
    private Random random;
    
    /**
     * Landing area on which rocket will have to land.
     */
    private LandingArea landingArea;
    
    private ArrayList <Mechant> listeDeMousse = new ArrayList<Mechant>();
    
    
    /**
     * Game background image.
     */
    private BufferedImage backgroundImg;
    
    /**
     * Red border of the frame. It is used when player crash the rocket.
     */
    private BufferedImage redBorderImg;
    

    public Game()
    {
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        
        random = new Random();
        
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent();
                
                Framework.gameState = Framework.GameState.PLAYING;
            }
        };
        threadForInitGame.start();
    }
    
    
   /**
     * Set variables and objects for the game.
     */
    private void Initialize()
    {
        playerRocket = new PlayerRocket();
        landingArea  = new LandingArea();
       
       
            listeDeMousse.add(new Mechant());
        
    };
    
    /**
     * Load game files - images, sounds, ...
     */
    private void LoadContent()
    {
        try
        {
            URL backgroundImgUrl = this.getClass().getResource("/moon_lander/resources/images/background.png");
            backgroundImg = ImageIO.read(backgroundImgUrl);
            
            URL redBorderImgUrl = this.getClass().getResource("/moon_lander/resources/images/red_border.png");
            redBorderImg = ImageIO.read(redBorderImgUrl);
        }
        catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Restart game - reset some variables.
     */
    public void RestartGame()
    {
        playerRocket.ResetPlayer();
    }
    
    public boolean colision(int X1,int Y1,int largeur1,int hauteur1,int X2,int Y2,int largeur2,int hauteur2)
    {
        if (X1 > X2+largeur2 || Y1 > Y2+hauteur2 || X2 > X1+largeur1 || Y2 > Y1+hauteur2)
        return false;
        else
        return true;
    }
           
           
    
    /**
     * Update game logic.
     * 
     * @param gameTime gameTime of the game.
     * @param mousePosition current mouse position.
     */
    public void UpdateGame(long gameTime, Point mousePosition)
    {
        // Move the rocket
        playerRocket.Update();
        
        if ( random.nextInt(95) == 40 ) 
            listeDeMousse.add(new Mechant());
        
        
        // verifions que le joueur n'a pas tape le mechant
        for ( int i =0 ; i < listeDeMousse.size();i++)
        {
          if(colision(playerRocket.x,playerRocket.y, playerRocket.rocketImgWidth, playerRocket.rocketImgHeight, listeDeMousse.get(i).x, listeDeMousse.get(i).y, listeDeMousse.get(i).rocketImgWidth, listeDeMousse.get(i).rocketImgHeight))
            {
                playerRocket.crashed = true;  
             }
          // on en profite pour faire glisser la mousse
          
          listeDeMousse.get(i).Update();
          if (listeDeMousse.get(i).x + listeDeMousse.get(i).rocketImgWidth< 0)
              listeDeMousse.remove(i);
        }
        
        
        // Checks where the player rocket is. Is it still in the space or is it landed or crashed?
        // First we check bottom y coordinate of the rocket if is it near the landing area.
        if(playerRocket.y + playerRocket.rocketImgHeight - 10 > landingArea.y)
        {
            
            
            // Here we check if the rocket is over landing area.
            if((playerRocket.x > landingArea.x) && (playerRocket.x < landingArea.x + landingArea.landingAreaImgWidth - playerRocket.rocketImgWidth))
            {
                // Here we check if the rocket speed isn't too high.
                if(playerRocket.speedY <= playerRocket.topLandingSpeed)
                    playerRocket.landed = true;
                //else
                    //playerRocket.crashed = true;
            }
            //else
               // playerRocket.crashed = true;
                
            if ( playerRocket.crashed) Framework.gameState = Framework.GameState.GAMEOVER;
        }
        
    }
    
    /**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void Draw(Graphics2D g2d, Point mousePosition)
    {
        g2d.drawImage(backgroundImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
        
        landingArea.Draw(g2d);
        
        playerRocket.Draw(g2d);
       
        for ( int i =0 ; i < listeDeMousse.size();i++)
        {
            listeDeMousse.get(i).Draw(g2d);
        }
    }
    
    
    /**
     * Draw the game over screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition Current mouse position.
     * @param gameTime Game time in nanoseconds.
     */
    public void DrawGameOver(Graphics2D g2d, Point mousePosition, long gameTime)
    {
        Draw(g2d, mousePosition);
        
        g2d.drawString("Recommence !", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3 + 70);
        
        if(playerRocket.landed)
        {
            g2d.drawString("Invasion réussie :D", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3);
            g2d.drawString("Atterissage en" + gameTime / Framework.secInNanosec + " secondes.", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3 + 20);
        }
        else
        {
            g2d.setColor(Color.red);
            g2d.drawString("Invasion échouée >.<", Framework.frameWidth / 2 - 95, Framework.frameHeight / 3);
            g2d.drawImage(redBorderImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
        }
    }
}
