

import java.awt.Image;
import javax.swing.ImageIcon;

public class Joueur extends Acteur {

    public Joueur(int x, int y) {
        super(x, y);

        initJoueur();
    }

    private void initJoueur() {

        ImageIcon iicon = new ImageIcon("img/Bas.gif");
        Image image = iicon.getImage();
        setImage(image);
    }

    public void move(int x, int y) {

        int dx = x() + x;
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }
}