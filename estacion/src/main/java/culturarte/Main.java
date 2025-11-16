package culturarte;

import culturarte.webservices.Publicador;
import culturarte.graficos.MainFrame;

public class Main {

    public static void main(String[] args) {


        try {
            System.out.println("Publicando WebServices...");
            Publicador.main(args);
            System.out.println("WebServices publicados correctamente.");
        } catch (Exception e) {
            System.err.println("Error publicando WebServices:");
            e.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
