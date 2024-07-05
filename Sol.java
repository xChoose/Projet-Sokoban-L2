

import java.awt.Image;
import javax.swing.ImageIcon;

public class Sol extends Acteur {

    public Sol(int x, int y) {
        super(x, y);
        
        initSol();
    }
    
    private void initSol() {

        ImageIcon iicon = new ImageIcon("img/sol.gif");
        Image image = iicon.getImage();
        setImage(image);
    }

    
}