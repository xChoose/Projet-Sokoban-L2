

import java.awt.Image;
import javax.swing.ImageIcon;

public class But extends Acteur {

    public But(int x, int y) {
        super(x, y);
        
        initBut();
    }
    
    private void initBut() {

        ImageIcon iicon = new ImageIcon("img/but.gif");
        Image image = iicon.getImage();
        setImage(image);
    }
}