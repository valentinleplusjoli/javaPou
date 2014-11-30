package moon_lander;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * The space rocket with which player will have to land.
 * 
 * @author www.gametutorial.net
 */

public class PlayerRocket {
    
    private int cpt = 0; // pour animer les persos
    private int curentImage = 0;
    private char derniereDirection = 'd';
    private final int capaciteSaut = 10;
    private int tmpSaut = 0;
    /**
     * We use this to generate a random number for starting x coordinate of the rocket.
     */
    private Random random;
 
    /**
     * X coordinate of the rocket.
     */
    public int x;
    /**
     * Y coordinate of the rocket.
     */
    public int y;
    
    /**
     * Is rocket landed?
     */
    public boolean landed;
    
    /**
     * Has rocket crashed?
     */
    public boolean crashed;
        
    /**
     * Accelerating speed of the rocket.
     */
    private int speedAccelerating;
    /**
     * Stopping/Falling speed of the rocket. Falling speed because, the gravity pulls the rocket down to the moon.
     */
    private int speedStopping;
    
    /**
     * Maximum speed that rocket can have without having a crash when landing.
     */
    public int topLandingSpeed;
    
    /**
     * How fast and to which direction rocket is moving on x coordinate?
     */
    private int speedX;
    /**
     * How fast and to which direction rocket is moving on y coordinate?
     */
    public int speedY;
            
    /**
     * Image of the rocket in air.
     */
    private BufferedImage rocketImg;
    private BufferedImage rocketImg1;
    private BufferedImage rocketImg2;
    private BufferedImage rocketImgg;
    private BufferedImage rocketImg1g;
    private BufferedImage rocketImg2g;
    /**
     * Image of the rocket when landed.
     */
    private BufferedImage rocketLandedImg;
    /**
     * Image of the rocket when crashed.
     */
    private BufferedImage rocketCrashedImg;
    /**
     * Image of the rocket fire.
     */
    private BufferedImage rocketFireImg;
    
    /**
     * Width of rocket.
     */
    public int rocketImgWidth;
   
    /**
     * Height of rocket.
     */
    public int rocketImgHeight;
  
    
    
    public PlayerRocket()
    {
        Initialize();
        LoadContent();
        
        // Now that we have rocketImgWidth we set starting x coordinate.
        x = random.nextInt(Framework.frameWidth - rocketImgWidth);
    }
    
    
    private void Initialize()
    {
        random = new Random();
        
        ResetPlayer();
        
        speedAccelerating = 2;
        speedStopping = 1;
        
        topLandingSpeed = 8;
    }
    
    private void LoadContent()
    {
        try
        {
            URL rocketImgUrl = this.getClass().getResource("/moon_lander/resources/images/hero_run0.png");
            rocketImg = ImageIO.read(rocketImgUrl);
            rocketImgUrl = this.getClass().getResource("/moon_lander/resources/images/hero_run0g.png");
            rocketImgg = ImageIO.read(rocketImgUrl);
            rocketImgWidth = rocketImg.getWidth();
            rocketImgHeight = rocketImg.getHeight();
            
            rocketImgUrl = this.getClass().getResource("/moon_lander/resources/images/hero_run1.png");
            rocketImg1 = ImageIO.read(rocketImgUrl);
            rocketImgUrl = this.getClass().getResource("/moon_lander/resources/images/hero_run1g.png");
            rocketImg1g = ImageIO.read(rocketImgUrl);
           
            
            rocketImgUrl = this.getClass().getResource("/moon_lander/resources/images/hero_run2.png");
            rocketImg2 = ImageIO.read(rocketImgUrl);
            rocketImgUrl = this.getClass().getResource("/moon_lander/resources/images/hero_run2g.png");
            rocketImg2g = ImageIO.read(rocketImgUrl);
            
            
            URL rocketLandedImgUrl = this.getClass().getResource("/moon_lander/resources/images/rocket_landed.png");
            rocketLandedImg = ImageIO.read(rocketLandedImgUrl);
            
            URL rocketCrashedImgUrl = this.getClass().getResource("/moon_lander/resources/images/rocket_crashed.png");
            rocketCrashedImg = ImageIO.read(rocketCrashedImgUrl);
            
            URL rocketFireImgUrl = this.getClass().getResource("/moon_lander/resources/images/rocket_fire.png");
            rocketFireImg = ImageIO.read(rocketFireImgUrl);
        }
        catch (IOException ex) {
            Logger.getLogger(PlayerRocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Here we set up the rocket when we starting a new game.
     */
    public void ResetPlayer()
    {
        landed = false;
        crashed = false;
        
        x = 50;
        y = 10;
        
        speedX = 0;
        speedY = 0;
    }
    public boolean colision(int X1,int Y1,int largeur1,int hauteur1,int X2,int Y2,int largeur2,int hauteur2)
    {
        if (X1 > X2+largeur2 || Y1 > Y2+hauteur2 || X2 > X1+largeur1 || Y2 > Y1+hauteur2)
        return false;
        else
        return true;
    }
    public void rechargeSautSiTouche()
    {
        if (y == Framework.frameHeight-rocketImgHeight) tmpSaut = capaciteSaut;// je touche le sol je peut resauter :D
        
    }
    /**
     * Here we move the rocket.
     */
    public void Update()
    {
        rechargeSautSiTouche();
        if ( speedX != 0 || speedY != 0 )cpt ++;
        if (cpt > 3 )//changer la frequence de tournation de l'image :)
        {
            if ( curentImage == 2 ) 
            {
                //System.out.print("2");
                curentImage=0;
                rocketImgWidth= rocketImg.getWidth();
                rocketImgHeight = rocketImg.getHeight();
            }
            else
            if ( curentImage == 1 ) 
            {
                //System.out.print("1");
                curentImage=2;
                rocketImgWidth= rocketImg2.getWidth();
                rocketImgHeight = rocketImg2.getHeight();
            }
            else
            if ( curentImage == 0 )
            {
                //System.out.print("0");
                curentImage=1;
                rocketImgWidth= rocketImg1.getWidth();
                rocketImgHeight = rocketImg1.getHeight();
            }
            
            cpt=0;
        }
        
        if(Canvas.keyboardKeyState(KeyEvent.VK_ENTER))
            ResetPlayer();
        
        // Calculating speed for moving up or down.
        if(Canvas.keyboardKeyState(KeyEvent.VK_SPACE) && !crashed && tmpSaut > 0)
        {
            speedY -= speedAccelerating;
            tmpSaut--;
        } 
        else
            speedY += speedStopping;
        
        // Calculating speed for moving or stopping to the left.
        if(Canvas.keyboardKeyState(KeyEvent.VK_LEFT)&& !crashed)
            speedX -= speedAccelerating;
            
        else if(speedX < 0)
            speedX += speedStopping;
        if ( speedX < -25 )speedX=-25;
        if ( speedX >  25 )speedX= 25;
        System.err.println(speedX);
        // Calculating speed for moving or stopping to the right.
        if(Canvas.keyboardKeyState(KeyEvent.VK_RIGHT)&& !crashed)
            speedX += speedAccelerating;
        else if(speedX > 0)
            speedX -= speedStopping;
        
        // Moves the rocket.
        x += speedX;
        if ( x<0  ) 
        {
            x =0;
            speedX=0;
        }
        if ( x > Framework.frameWidth-rocketImgWidth) 
        {
            x = Framework.frameWidth-rocketImgWidth;
            speedX=0;
        }
        if ( speedX < 0) derniereDirection='g';
        if (speedX > 0) derniereDirection='d';
                
        y += speedY;
        if ( y < 0)
        {
            y = 0;
            speedY=0;
        }
        if ( y > Framework.frameHeight-rocketImgHeight)
        {
            y = Framework.frameHeight-rocketImgHeight;
            speedY=0;
        }
    }
    
    public void Draw(Graphics2D g2d)
    {
        g2d.setColor(Color.white);
       // g2d.drawString("Coordonnées du Pou Pidou" + x + " : " + y, 5, 15);
        
        // If the rocket is landed.
        if(landed)
        {
            g2d.drawImage(rocketLandedImg, x, y, null);
        }
        // If the rocket is crashed.
        else if(crashed)
        {
            g2d.drawImage(rocketCrashedImg, x, y + rocketImgHeight - rocketCrashedImg.getHeight(), null);
        }
        // If the rocket is still in the space.
        else
        {
            // If player hold down a W key we draw rocket fire.
            //if(Canvas.keyboardKeyState(KeyEvent.VK_SPACE))
                //g2d.drawImage(rocketFireImg, x + 12, y + 66, null);
            if ( curentImage == 0 )
                if (derniereDirection=='d') g2d.drawImage(rocketImg, x, y, null);
                else g2d.drawImage(rocketImgg, x, y, null);
            if ( curentImage == 1 ) 
                if (derniereDirection=='d') g2d.drawImage(rocketImg1, x, y, null);
                else g2d.drawImage(rocketImg1g, x, y, null);
            if ( curentImage == 2 ) 
                if (derniereDirection=='d') g2d.drawImage(rocketImg2, x, y, null);
                else g2d.drawImage(rocketImg2g, x, y, null);
        }
    }
    
}
