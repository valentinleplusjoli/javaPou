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

public class Mechant {
    
    /**
     * We use this to generate a random number for starting x coordinate of the rocket.
     */
    private Random random;
 
    /**
     * X coordinate of the mechant
     */
    public int x = 200;
    /**
     * Y coordinate of the mechant
     */
    public int y = 0 ;
    
   
   
    private BufferedImage mechantImg;
    /**
     * Image of the rocket when landed.
     */
    
    public int rocketImgWidth=50;
    /**
     * Height of rocket.
     */
    public int rocketImgHeight=50;
    
    
    public Mechant()
    {
        Initialize();
        LoadContent();
        y = random.nextInt(Framework.frameHeight - rocketImgHeight)/2;
        y += Framework.frameHeight/3;
        // Now that we have rocketImgWidth we set starting x coordinate.
        x = Framework.frameWidth;
    }
    
    
    private void Initialize()
    {
        random = new Random();
        
        ResetMechant();
        
        
    }
    
    private void LoadContent()
    {
        try
        {
            URL rocketImgUrl = this.getClass().getResource("/moon_lander/resources/images/m8.png");
            mechantImg = ImageIO.read(rocketImgUrl);
            rocketImgWidth = mechantImg.getWidth();
            rocketImgHeight = mechantImg.getHeight();
            
           
        }
        catch (IOException ex) {
            Logger.getLogger(PlayerRocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Here we set up the rocket when we starting a new game.
     */
    public void ResetMechant()
    {
        
        
        x = 100;
        y = 300;
        
    }
    
    
    /**
     * Here we move the rocket.
     */
    public void Update()
    {
        // on en fait quoi du mechant ?
        x=x-3;
    }
    
    public void Draw(Graphics2D g2d)
    {
        g2d.setColor(Color.white);
        //g2d.drawString("Taille mechant " + rocketImgWidth, 15, 25);
        
        
            g2d.drawImage(mechantImg, x, y, null);
        
       
    }
    
}
