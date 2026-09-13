package co.edu.unbosque.metodologiaespiral.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Component;

import co.edu.unbosque.metodologiaespiral.entity.Proyecto;
import co.edu.unbosque.metodologiaespiral.entity.Tarea;
import co.edu.unbosque.metodologiaespiral.entity.Usuario;

@Component
public class PDFUtil {

    public byte[] generarPdf(Proyecto proyecto) throws IOException {

        ByteArrayOutputStream salida = new ByteArrayOutputStream();

        try (PDDocument documento = new PDDocument()) {

            PDPage pagina = new PDPage(PDRectangle.A4);
            documento.addPage(pagina);

            PDType1Font normal = new PDType1Font(
                    Standard14Fonts.FontName.HELVETICA);

            PDType1Font negrita = new PDType1Font(
                    Standard14Fonts.FontName.HELVETICA_BOLD);

            PDPageContentStream contenido =
                    new PDPageContentStream(documento, pagina);

            float y = 780;

            // TITULO
            contenido.beginText();
            contenido.setFont(negrita, 20);
            contenido.newLineAtOffset(50, y);
            contenido.showText("INFORMACION DEL PROYECTO");
            contenido.endText();

            y -= 40;

            // NOMBRE
            contenido.beginText();
            contenido.setFont(negrita, 14);
            contenido.newLineAtOffset(50, y);
            contenido.showText("Nombre:");
            contenido.endText();

            contenido.beginText();
            contenido.setFont(normal, 12);
            contenido.newLineAtOffset(120, y);
            contenido.showText(
                    textoSeguro(proyecto.getNombre()));
            contenido.endText();

            y -= 30;

            // DESCRIPCION
            contenido.beginText();
            contenido.setFont(negrita, 14);
            contenido.newLineAtOffset(50, y);
            contenido.showText("Descripcion:");
            contenido.endText();

            y -= 20;

            contenido.beginText();
            contenido.setFont(normal, 12);
            contenido.newLineAtOffset(50, y);
            contenido.showText(
                    textoSeguro(proyecto.getDescripcion()));
            contenido.endText();

            y -= 40;

            // TAREAS
            contenido.beginText();
            contenido.setFont(negrita, 16);
            contenido.newLineAtOffset(50, y);
            contenido.showText("TAREAS");
            contenido.endText();

            y -= 25;

            if (proyecto.getTareas() == null
                    || proyecto.getTareas().isEmpty()) {

                contenido.beginText();
                contenido.setFont(normal, 12);
                contenido.newLineAtOffset(50, y);
                contenido.showText(
                        "El proyecto no tiene tareas.");
                contenido.endText();

                y -= 25;

            } else {

                for (Tarea tarea : proyecto.getTareas()) {

                    /*
                     * Si ya no cabe contenido en la pagina,
                     * creamos una nueva.
                     */
                    if (y < 120) {

                        contenido.close();

                        pagina = new PDPage(PDRectangle.A4);
                        documento.addPage(pagina);

                        contenido =
                                new PDPageContentStream(
                                        documento,
                                        pagina);

                        y = 780;
                    }

                    contenido.beginText();
                    contenido.setFont(negrita, 12);
                    contenido.newLineAtOffset(60, y);
                    contenido.showText(
                            "- " + textoSeguro(
                                    tarea.getNombre()));
                    contenido.endText();

                    y -= 18;

                    contenido.beginText();
                    contenido.setFont(normal, 10);
                    contenido.newLineAtOffset(80, y);
                    contenido.showText(
                            "Descripcion: "
                                    + textoSeguro(
                                    tarea.getDescripcion()));
                    contenido.endText();

                    y -= 16;

                    contenido.beginText();
                    contenido.setFont(normal, 10);
                    contenido.newLineAtOffset(80, y);

                    String estado =
                            tarea.getEstado() == null
                                    ? "Sin estado"
                                    : tarea.getEstado().toString();

                    contenido.showText(
                            "Estado: " + estado);
                    contenido.endText();

                    y -= 16;

                    contenido.beginText();
                    contenido.setFont(normal, 10);
                    contenido.newLineAtOffset(80, y);
                    contenido.showText(
                            "Fecha inicio: "
                                    + textoSeguro(
                                    tarea.getFechaInicio()));
                    contenido.endText();

                    y -= 16;

                    contenido.beginText();
                    contenido.setFont(normal, 10);
                    contenido.newLineAtOffset(80, y);
                    contenido.showText(
                            "Fecha entrega: "
                                    + textoSeguro(
                                    tarea.getFechaEntrega()));
                    contenido.endText();

                    y -= 30;
                }
            }

            /*
             * Comprobamos nuevamente espacio
             * antes de imprimir usuarios.
             */
            if (y < 150) {

                contenido.close();

                pagina = new PDPage(PDRectangle.A4);
                documento.addPage(pagina);

                contenido =
                        new PDPageContentStream(
                                documento,
                                pagina);

                y = 780;
            }

            // USUARIOS
            contenido.beginText();
            contenido.setFont(negrita, 16);
            contenido.newLineAtOffset(50, y);
            contenido.showText("USUARIOS DEL PROYECTO");
            contenido.endText();

            y -= 25;

            if (proyecto.getUsuarios() == null
                    || proyecto.getUsuarios().isEmpty()) {

                contenido.beginText();
                contenido.setFont(normal, 12);
                contenido.newLineAtOffset(50, y);
                contenido.showText(
                        "El proyecto no tiene usuarios.");
                contenido.endText();

            } else {

                for (Usuario usuario :
                        proyecto.getUsuarios()) {

                    if (y < 100) {

                        contenido.close();

                        pagina =
                                new PDPage(PDRectangle.A4);

                        documento.addPage(pagina);

                        contenido =
                                new PDPageContentStream(
                                        documento,
                                        pagina);

                        y = 780;
                    }

                    contenido.beginText();
                    contenido.setFont(negrita, 12);
                    contenido.newLineAtOffset(60, y);
                    contenido.showText(
                            "- " + textoSeguro(
                                    usuario.getNombre()));
                    contenido.endText();

                    y -= 17;

                    contenido.beginText();
                    contenido.setFont(normal, 10);
                    contenido.newLineAtOffset(80, y);
                    contenido.showText(
                            "Correo: "
                                    + textoSeguro(
                                    usuario.getCorreo()));
                    contenido.endText();

                    y -= 25;
                }
            }

            contenido.close();

            documento.save(salida);
        }

        return salida.toByteArray();
    }

    private String textoSeguro(String texto) {

        if (texto == null) {
            return "No disponible";
        }

        return texto;
    }
}