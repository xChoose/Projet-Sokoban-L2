

import java.awt.Image;
import javax.swing.ImageIcon;

public class Caisse extends Acteur {
    private final int SPACE = 30;
    

    public Caisse(int x, int y) {
        super(x, y);
        
        initCaisse();
    }
    
    private void initCaisse() {
        
        ImageIcon iicon = new ImageIcon("img/caisse1.gif");
        Image image = iicon.getImage();
        setImage(image);
    }

    public void move(int x, int y) {
        
        int dx = x() + x;
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }

    public void setImage(String imagePath) {
        ImageIcon iicon = new ImageIcon(imagePath);
        Image image = iicon.getImage();
        setImage(image);
    }

    public boolean estUneCollisionGauche(Caisse bag) {
        
        return x() - SPACE == bag.x() && y() == bag.y();
    }

    public boolean estUneCollisionDroite(Caisse bag) {
        
        return x() + SPACE == bag.x() && y() == bag.y();
    }

    public boolean estUneCollisionHausse(Caisse bag) {
        
        return y() - SPACE == bag.y() && x() == bag.x();
    }

    public boolean estUneCollisionBasse(Caisse bag) {
        
        return y() + SPACE == bag.y() && x() == bag.x();
    }

}