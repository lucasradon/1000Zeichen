
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Run {

    static String[] stringbild = new String[33];
    static char[] chararray = new char[3300];
    static Region[][] regions = new Region[33][100];
    static int g = 0;

    public static void main(String[] args) throws IOException {
        readin("aufgabenblatt10_bild.txt");
        Ausgabe();
        Vereinigung();
        GrosseRegions();
        Ausgabe();
        System.out.println("Die Größte Region ist "+biggest.getZeichen()+" mit einer Anzahl von "+biggest.getAnzahl()+" zusammenhängenden Zeichen.");
    }

    /**
     * Methode zum Einlesen des Bildes.txt, Ausgabe der Zeichen und Umsetzen in ein Array aus char
     *
     * @param name
     * @throws IOException
     */
    static void readin(String name) throws IOException {
        FileReader fr = new FileReader(name);
        BufferedReader br = new BufferedReader(fr);
        //i von 0 bis 32
        for (int i = 0; i < stringbild.length; i++) {
            stringbild[i] = br.readLine();

            //j von 0 bis 100
            //g von 0 bis 3300
            for (int j = 0; j < stringbild[i].length(); j++) {
                chararray[g] = stringbild[i].charAt(j);
                g++;
                regions[i][j] = new Region(stringbild[i].charAt(j));
            }

        }
        br.close();
        System.out.println("Das Bild stellt eine Katze dar");
    }

    /**
     * Methode zur Pfadkompression
     * @param startregion
     * @return
     */
    static Region compress(Region startregion) {
        startregion.setElternregion(compressrek(startregion));
        return startregion.getElternregion();
    }

    static Region compressrek(Region region) {
        if (region.getElternregion() == region) {
            return region;
        } else {
            return compressrek(region.getElternregion());
        }
    }

    /**
     * Methode zum Vereinigen benachbarter Regionen
     */
    static void Vereinigung() {

        for (int i = 0; i < regions.length; i++) {
            for (int j = 0; j < regions[i].length; j++) {
                //getNachbarn
                getNachbarn(i, j);
                Region aktuelleRegion = regions[i][j];
                //Nachbarregionen duchegehen
                for (int k = 0; k < nachbarregionen.length; k++) {
                    //Prüfen ob Nachbarregionen nicht null und gleich ist //Stichwort Rand
                    if (nachbarregionen[k] != null && bgleich(aktuelleRegion, nachbarregionen[k])) {
                        //Wurzelregionen finden
                        Region Waktuell = compress(aktuelleRegion);
                        Region Wnachbar = compress(nachbarregionen[k]);
                        //Wenn Wurzelregionen unterschiedlich sind
                        if (!Waktuell.getElternregion().equals(Wnachbar.getElternregion())) {
                            //Häng an den größeren
                            if (Waktuell.getAnzahl() >= Wnachbar.getAnzahl()) {
                                Wnachbar.setElternregion(Waktuell.getElternregion());
                                //Anzahl der Zeichen innerhalb der Region erhöhen
                                Waktuell.setAnzahl(Waktuell.getAnzahl() + Wnachbar.getAnzahl());
                            } else {
                                Waktuell.setElternregion(Wnachbar.getElternregion());
                                Wnachbar.setAnzahl(Wnachbar.getAnzahl() + Waktuell.getAnzahl());
                            }
                        }
                    }
                }
            }
        }
    }

    static Region nachbar1, nachbar2, nachbar3, nachbar4;
    static Region[] nachbarregionen = new Region[4];

    /**
     *          nachbar1
     * nachbar2 Region   nachbar3
     *          nachbar3
     *
     * @param i
     * @param j
     */
    static void getNachbarn(int i, int j) {
        try {
            nachbar1 = regions[i - 1][j];
        } catch (ArrayIndexOutOfBoundsException e) {
            nachbar1 = null;
        }
        try {
            nachbar2 = regions[i][j - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            nachbar2 = null;
        }
        try {
            nachbar3 = regions[i + 1][j];
        } catch (ArrayIndexOutOfBoundsException e) {
            nachbar3 = null;
        }
        try {
            nachbar4 = regions[i][j + 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            nachbar4 = null;
        }
        nachbarregionen[0] = nachbar1;
        nachbarregionen[1] = nachbar2;
        nachbarregionen[2] = nachbar3;
        nachbarregionen[3] = nachbar4;
    }

    /**
     * Methode zum Überprüfen ob 2 Zellen gleiches Zeiches besitzen
     * @param a
     * @param b
     * @return
     */
    static boolean bgleich(Region a, Region b) {
        return a.getZeichen() == b.getZeichen();
    }

    /**
     * Buchstabenzähler
     */
    private static int o = 65; // A
    /**
     * Aktuell größte ReferenRegion
     */
    private static Region biggest = null;

    /**
     * Nur Regions mit einer Anzahl von über 20 Zeichen werden dargestellt
     */
    static void GrosseRegions() {
        //Alle Regions durchegehen
        for (Region[] regions1 : regions) {
            for (Region region : regions1) {
                //Wenn Wurzelknoten größer 20 (Wird nun Elternknoten) und Elternkonten gleich aktueller Region ist
                if (compress(region).getAnzahl() >= 20 && region.getElternregion().equals(region)) {
                    region.setZeichen((char) (o));
                    //erhöhe Buchstabenzähle
                    o++;
                    //Lass doofe Sonderzeichen nach Majuskeln aus
                    if (o >= 91)
                        o = 97;
                }
                //Überschreibe akteull größte Region, wenn andere größer ist
                if (biggest == null || region.getAnzahl() > biggest.getAnzahl()) {
                    biggest = region;
                }
            }
        }
        //Alle Regions durchegehen
        for (Region[] regions1 : regions) {
            for (Region region : regions1) {
                //Wenn Elternregion, mehr als 20 Zeichen hat, nimm das Zeichen an
                if(region.getElternregion().getAnzahl() >= 20)
                    region.setZeichen(region.getElternregion().getZeichen());
                else
                    //Ansonsten leerzeichen
                    region.setZeichen(' ');
            }
        }
    }

    //Ausgabe des 2D Region Arrays
    static void Ausgabe() {
        for (Region[] regions1 : regions) {
            for (Region region : regions1) {
                System.out.print(region.getZeichen());
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
}
