import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;
import java.io.*;
import java.util.*;

public class HTMLKeywordSearch {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Uso: java HTMLKeywordSearch <archivo.html> <keyword>");
            return;
        }

        String filePath = args[0];
        String keyword = args[1];

        // Cargar el archivo HTML
        HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
        HTMLDocument document = (HTMLDocument) htmlEditorKit.createDefaultDocument();
        try (FileReader reader = new FileReader(filePath)) {
            htmlEditorKit.read(reader, document, 0);
        }

        // Obtener el texto plano del documento
        String plainText = document.getText(0, document.getLength());

        // Buscar la palabra clave (keyword) y almacenar posiciones
        int index = 0;
        List<Integer> posiciones = new ArrayList<>();
        while ((index = plainText.indexOf(keyword, index)) >= 0) {
            posiciones.add(index);
            index += keyword.length();
        }

        // Mostrar resultados en consola
        if (posiciones.isEmpty()) {
            System.out.println("La palabra '" + keyword + "' no se encontró en el documento.");
        } else {
            System.out.println("La palabra '" + keyword + "' se encontró en las siguientes posiciones:");
            for (int pos : posiciones) {
                System.out.println("Posición: " + pos);
            }
        }

        // Crear archivo de bitácora
        String logFileName = "file-" + keyword + ".log";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName))) {
            writer.write("Archivo HTML: " + filePath + "\n");
            if (posiciones.isEmpty()) {
                writer.write("La palabra '" + keyword + "' no se encontró en el documento.\n");
            } else {
                writer.write("La palabra '" + keyword + "' se encontró en las siguientes posiciones:\n");
                for (int pos : posiciones) {
                    writer.write("Posición: " + pos + "\n");
                }
            }
            System.out.println("Archivo de bitácora generado: " + logFileName);
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo de bitácora: " + e.getMessage());
        }
    }
}
