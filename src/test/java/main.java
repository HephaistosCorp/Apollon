import ch.hephaistos.utilities.apollon.Apollon;

public class main {

    public static void main (String args[]){
        Thread.setDefaultUncaughtExceptionHandler(
                new Apollon("123", "123", "123", "123"));
        int arr[] = { 0, 0};
        System.out.println(arr[3]);
    }
}
