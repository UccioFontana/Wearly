package imagesManagement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageProcessor {

    private static final int MAX_WIDTH = 800;  // Larghezza massima in pixel
    private static final int MAX_HEIGHT = 800; // Altezza massima in pixel

    public static void compressAndResizeImage(InputStream inputStream, File outputFile) throws IOException {
        // Utilizziamo una libreria come ImageIO per leggere e scrivere l'immagine
        BufferedImage image = ImageIO.read(inputStream);

        // Comprimiamo e ridimensioniamo l'immagine (qui puoi aggiungere la logica specifica per la compressione)
        int newWidth = 800; // Imposta la larghezza desiderata
        int newHeight = (int) ((double) image.getHeight() / image.getWidth() * newWidth); // Mantieni le proporzioni

        // Creazione di una nuova immagine ridimensionata
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // Scrivere l'immagine compressa nel file di destinazione
        BufferedImage bufferedResizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        bufferedResizedImage.getGraphics().drawImage(resizedImage, 0, 0, null);

        // Scriviamo l'immagine compressa nel file
        ImageIO.write(bufferedResizedImage, "jpg", outputFile); // Salva come JPG (o un altro formato)
    }
}