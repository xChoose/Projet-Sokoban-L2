

import java.awt.Image;

public class Acteur {

    private final int SPACE = 30;

    private int x;
    private int y;
    private Image image;

    public Acteur(int x, int y) {
        
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        image = img;
    }

    public int x() {
        
        return x;
    }

    public int y() {
        
        return y;
    }

    public void setX(int x) {
        
        this.x = x;
    }

    public void setY(int y) {
        
        this.y = y;
    }

    public boolean estUneCollisionGauche(Acteur Acteur) {
        
        return x() - SPACE == Acteur.x() && y() == Acteur.y();
    }

    public boolean estUneCollisionDroite(Acteur Acteur) {
        
        return x() + SPACE == Acteur.x() && y() == Acteur.y();
    }

    public boolean estUneCollisionHausse(Acteur Acteur) {
        
        return y() - SPACE == Acteur.y() && x() == Acteur.x();
    }

    public boolean estUneCollisionBasse(Acteur Acteur) {
        
        return y() + SPACE == Acteur.y() && x() == Acteur.x();
    }
}