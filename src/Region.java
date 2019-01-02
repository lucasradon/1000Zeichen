public class Region {
    private char zeichen;
    private int anzahl;
    private Region elternregion;

    public Region(char Z){
        zeichen =Z;
        anzahl =1;
        elternregion =this;
    }

    public void setZeichen(char zeichen) {
        this.zeichen = zeichen;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public void setElternregion(Region elternregion) {
        this.elternregion = elternregion;
    }

    public Region getElternregion() {
        return elternregion;
    }

    public char getZeichen() {
        return zeichen;
    }

    public int getAnzahl() {
        return anzahl;
    }

}
