

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.io.*; 
import javax.swing.ImageIcon;

public class Plateau extends JPanel {

    private final int OFFSET = 30;
    private final int DISTANCE = 30;
    private final int COLLISION_GAUCHE = 1;
    private final int COLLISION_DROITE = 2;
    private final int COLLISION_HAUTE = 3;
    private final int COLLISION_BASSE = 4;

    private ArrayList<Mur> Murs;
    private ArrayList<Caisse> Caisses;
    private ArrayList<But> Buts;
    private ArrayList<Sol> Sol; 
    
    private Joueur soko;
    private int w = 0;
    private int h = 0;
    
    private boolean fini = false;
    public int niveauactuel = 0 ;

    private int nbreMouvements = 0 ;

    public ImageIcon imageJoueur1 = new ImageIcon("img/Bas.gif");
    public ImageIcon imageJoueur2 = new ImageIcon("img/Droite.gif");
    public ImageIcon imageJoueur3 = new ImageIcon("img/Gauche.gif");
    public ImageIcon imageJoueur4 = new ImageIcon("img/Haut.gif");

    public ImageIcon[] JoueurImages = {imageJoueur1, imageJoueur2, imageJoueur3, imageJoueur4};
    
    public int JoueurImageIndex = 0;

 
    private String prepareNiveau(int NiveauNum) throws IOException {
            try {
                BufferedReader lecteur = new BufferedReader(new FileReader("Niveau"+NiveauNum+".txt"));
                String ligne = null;
                StringBuilder sb = new StringBuilder();
                while ((ligne = lecteur.readLine()) != null) {
                    sb.append(ligne).append("\n");
                }
                lecteur.close();
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                throw e ; 
            }
    }
           

    public Plateau() {

        initPlateau();
    }

    private void initPlateau() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        
        try {
            initMonde();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public int getPlateauWidth() {
        return this.w;
    }

    public int getPlateauHeight() {
        return this.h;
    }

    private void initMonde() throws IOException {
        
        Murs = new ArrayList<>();
        Caisses = new ArrayList<>();
        Buts = new ArrayList<>();
        

        int x = OFFSET;
        int y = OFFSET;

        Mur Mur;
        Caisse b;
        But a;
        

        String Niveau = null; 
        try {
            Niveau = prepareNiveau(niveauactuel);
        } catch (IOException e){
            e.printStackTrace();
        }

        for (int i = 0; i < Niveau.length(); i++) {

            char item = Niveau.charAt(i);

            switch (item) {

                case '\n':
                    y += DISTANCE;

                    if (this.w < x) {
                        this.w = x;
                    }

                    x = OFFSET;
                    break;

                case '#':
                    Mur = new Mur(x, y);
                    Murs.add(Mur);
                    x += DISTANCE;
                    break;

                case '$':
                    b = new Caisse(x, y);
                    Caisses.add(b);
                    x += DISTANCE;
                    break;

                case '.':
                    a = new But(x, y);
                    Buts.add(a);
                    x += DISTANCE;
                    break;

                case '@':
                    soko = new Joueur(x, y);
                    x += DISTANCE;
                    break;

                case ' ':
                    x += DISTANCE;
                    break;
                
                case '/': 
                    x+= DISTANCE;
                    break; 

                default:
                    break;
            }

            h = y;
        }
    }

    private void construitJeu(Graphics g) {

        g.setColor(new Color(255,255 , 255));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        ArrayList<Acteur> jeu = new ArrayList<>();

        jeu.addAll(Murs);
        jeu.addAll(Buts);
        jeu.addAll(Caisses);
    
        jeu.add(soko);

        for (int i = 0; i < jeu.size(); i++) {

            Acteur item = jeu.get(i);

            if (item instanceof Joueur) {
                
                g.drawImage(JoueurImages[JoueurImageIndex].getImage(), item.x() + 2, item.y() + 2, this);
            } else {
                
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }

            if (fini) {
                
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        construitJeu(g);
    }

    private class TAdapter extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {

            if (fini) {

                niveauactuel++;

                try {
                    initMonde();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                fini = false;
                return;
            }

            int key = e.getKeyCode();

            switch (key) {
                
                case KeyEvent.VK_LEFT:
                    JoueurImageIndex=2;
                    
                    if (verifieMurCollision(soko, COLLISION_GAUCHE)) {
                        return;
                    }
                    
                    if (verifieCaisseCollision(COLLISION_GAUCHE)) {
                        return;
                    }

                    soko.move(-DISTANCE, 0);
                    nbreMouvements++;
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    JoueurImageIndex=1;
                    if (verifieMurCollision(soko, COLLISION_DROITE)) {
                        return;
                    }
                    
                    if (verifieCaisseCollision(COLLISION_DROITE)) {
                        return;
                    }
                    
                    soko.move(DISTANCE, 0);
                    nbreMouvements++;
                    break;
                    
                case KeyEvent.VK_UP:
                    JoueurImageIndex=3;
                    if (verifieMurCollision(soko, COLLISION_HAUTE)) {
                        return;
                    }
                    
                    if (verifieCaisseCollision(COLLISION_HAUTE)) {
                        return;
                    }                    
                    
                    soko.move(0, -DISTANCE);
                    nbreMouvements++;
                    break;
                    
                case KeyEvent.VK_DOWN:
                    JoueurImageIndex=0;
                    if (verifieMurCollision(soko, COLLISION_BASSE)) {
                        return;
                    }
                    
                    if (verifieCaisseCollision(COLLISION_BASSE)) {
                        return;
                    }
                    
                    soko.move(0, DISTANCE);
                    nbreMouvements++;
                    break;
                    
                case KeyEvent.VK_R:
                    
                    restartNiveau();
                    
                    break;
                    
                default:
                    break;
            }

            repaint();
        }
    }

    private boolean verifieMurCollision(Acteur Acteur, int type) {

        switch (type) {
            
            case COLLISION_GAUCHE:
                
                for (int i = 0; i < Murs.size(); i++) {
                    
                    Mur Mur = Murs.get(i);
                    
                    if (Acteur.estUneCollisionGauche(Mur)) {
                        
                        return true;
                    }
                }
                
                return false;
                
            case COLLISION_DROITE:
                
                for (int i = 0; i < Murs.size(); i++) {
                    
                    Mur Mur = Murs.get(i);
                    
                    if (Acteur.estUneCollisionDroite(Mur)) {
                        return true;
                    }
                }
                
                return false;
                
            case COLLISION_HAUTE:
                
                for (int i = 0; i < Murs.size(); i++) {
                    
                    Mur Mur = Murs.get(i);
                    
                    if (Acteur.estUneCollisionHausse(Mur)) {
                        
                        return true;
                    }
                }
                
                return false;
                
            case COLLISION_BASSE:
                
                for (int i = 0; i < Murs.size(); i++) {
                    
                    Mur Mur = Murs.get(i);
                    
                    if (Acteur.estUneCollisionBasse(Mur)) {
                        
                        return true;
                    }
                }
                
                return false;
                
            default:
                break;
        }
        
        return false;
    }

    private boolean verifieCaisseCollision(int type) {

        switch (type) {
            
            case COLLISION_GAUCHE:
                
                for (int i = 0; i < Caisses.size(); i++) {

                    Caisse bag = Caisses.get(i);
                     

                    if (soko.estUneCollisionGauche(bag)) {

                        for (int j = 0; j < Caisses.size(); j++) {
                            
                            Caisse item = Caisses.get(j);
                            
                            if (!bag.equals(item)) {
                                
                                if (bag.estUneCollisionGauche(item)) {
                                    return true;
                                }
                            }
                            
                            if (verifieMurCollision(bag, COLLISION_GAUCHE)) {
                                return true;
                            }
                        }
                        
                        bag.move(-DISTANCE, 0);
                        fini();
                        
                    }
                }
                
                return false;
                
            case COLLISION_DROITE:
                
                for (int i = 0; i < Caisses.size(); i++) {

                    Caisse bag = Caisses.get(i);
                   
                    if (soko.estUneCollisionDroite(bag)) {
                        
                        for (int j = 0; j < Caisses.size(); j++) {

                            Caisse item = Caisses.get(j);
                            
                            if (!bag.equals(item)) {
                                
                                if (bag.estUneCollisionDroite(item)) {
                                    return true;
                                }
                            }
                            
                            if (verifieMurCollision(bag, COLLISION_DROITE)) {
                                return true;
                            }
                        }
                        
                        bag.move(DISTANCE, 0);
                        fini();
                        
                    }
                }
                return false;
                
            case COLLISION_HAUTE:
                
                for (int i = 0; i < Caisses.size() ; i++) {

                    Caisse bag = Caisses.get(i);
                    

                    if (soko.estUneCollisionHausse(bag)) {
                        
                        for (int j = 0; j < Caisses.size(); j++) {

                            Caisse item = Caisses.get(j);

                            if (!bag.equals(item)) {
                                
                                if (bag.estUneCollisionHausse(item)) {
                                    return true;
                                }
                            }
                            
                            if (verifieMurCollision(bag, COLLISION_HAUTE)) {
                                return true;
                            }
                        }
                        
                        
                        bag.move(0, -DISTANCE);
                        fini();
                        
                    }
                }

                return false;
                
            case COLLISION_BASSE:
                
                for (int i = 0; i < Caisses.size(); i++) {

                    Caisse bag = Caisses.get(i);
                    
                    if (soko.estUneCollisionBasse(bag)) {
                        
                        for (int j = 0; j < Caisses.size(); j++) {

                            Caisse item = Caisses.get(j);
                            
                            if (!bag.equals(item)) {
                                
                                if (bag.estUneCollisionBasse(item)) {
                                    return true;
                                }
                            }
                            
                            if (verifieMurCollision(bag,COLLISION_BASSE)) {
                                
                                return true;
                            }
                        }
                        
                        bag.move(0, DISTANCE);
                        fini();
                        
                    }
                }
                
                break;
                
            default:
                break;
        }

        return false;
    }

    public Boolean fini() {

        int nOfBags = Caisses.size();
        int finishedBags = 0;

        for (int i = 0; i < nOfBags; i++) {
            
            Caisse bag = Caisses.get(i);
            
            for (int j = 0; j < nOfBags; j++) {
                
                But But =  Buts.get(j);
                
                if (bag.x() == But.x() && bag.y() == But.y()) {
                    bag.setImage("img/caisse2.gif");
                    finishedBags += 1;
                }
            }
        }

        if (finishedBags == nOfBags) {
            
            fini = true;
            repaint();
        }
        return fini; 
    }

    public void restartNiveau() {

        Buts.clear();
        Caisses.clear();
        Murs.clear();
        //sols.clear();
        
        try {
            initMonde();
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        if (fini) {
            fini = false;
        }
    }
}