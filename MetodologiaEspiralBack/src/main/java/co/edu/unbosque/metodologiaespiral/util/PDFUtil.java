package co.edu.unbosque.metodologiaespiral.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

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


            contenido.beginText();
            contenido.setFont(negrita, 20);
            contenido.newLineAtOffset(50, y);
            contenido.showText("INFORMACION DEL PROYECTO");
            contenido.endText();

            y -= 40;



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


                    if (y < 140) {

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

                    String estado;

                    if (tarea.getEstado() == null) {

                        estado = "Sin estado";

                    } else {

                        estado =
                                tarea.getEstado().toString();
                    }

                    contenido.showText(
                            "Estado: " + estado);

                    contenido.endText();

                    y -= 16;


                    contenido.beginText();
                    contenido.setFont(normal, 10);
                    contenido.newLineAtOffset(80, y);

                    contenido.showText(
                            "Fecha inicio: "
                                    + fechaSegura(
                                    tarea.getFechaInicio()));

                    contenido.endText();

                    y -= 16;

                    // Fecha entrega

                    contenido.beginText();
                    contenido.setFont(normal, 10);
                    contenido.newLineAtOffset(80, y);

                    contenido.showText(
                            "Fecha entrega: "
                                    + fechaSegura(
                                    tarea.getFechaEntrega()));

                    contenido.endText();

                    y -= 30;
                }
            }



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

    /*
     * Metodo para evitar errores si un String
     * viene null.
     */
    private String textoSeguro(String texto) {

        if (texto == null
                || texto.trim().isEmpty()) {

            return "No disponible";
        }

        return texto;
    }

    /*
     * Metodo especial para LocalDate.
     */
    private String fechaSegura(LocalDate fecha) {

        if (fecha == null) {

            return "No disponible";
        }

        return fecha.toString();
    }
}