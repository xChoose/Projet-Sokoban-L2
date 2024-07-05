

import java.awt.Image;
import javax.swing.ImageIcon;

public class Mur extends Acteur {

    private Image image;

    public Mur(int x, int y) {
        super(x, y);
        
        initMur();
    }
    
    private void initMur() {
        
        ImageIcon iicon = new ImageIcon("img/mur.gif");
        image = iicon.getImage();
        setImage(image);
    }
}