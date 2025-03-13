package field;

/**
 * Module qui définit d'un incendie
 * 2 attributs position et life
 * position de type Case la position sur laquelle est 
 * l'incendie
 * life de type int qui définit l'intensité du feu
 */
public class Incendie {
    private Case position;
    private int life;

    /**
     * Constructeur d'un incendie
     * @param pos Position à laquelle est le feu
     * @param life Intensité du feu
     */
    public Incendie(Case pos, int life) {
        this.position = pos;
        this.life = life;
    }

    /**
     * Réduction du feu du volume
     * @param vol Volume d'eau qui diminue le feu
     */
    public void setLife(int vol) {
        this.life -= vol;
    }

    /**
     * 
     * @return Case de l'incendie
     */
    public Case getCase() {
        return this.position;
    }

    /**
     * 
     * @return La vie restante du feu
     */
    public int getLife() {
        return this.life;
    }

    @Override
    public Incendie clone() {
        return new Incendie(this.position, this.life);
    }
}
