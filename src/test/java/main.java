import ch.hephaistos.utilities.apollon.libhandler.Apollon;

public class main {

    public static void main (String args[]){
        Thread t = new Thread(new Apollon());
        t.start();
    }
}
