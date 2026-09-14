package co.edu.unbosque.metodologiaespiral.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Component;

import co.edu.unbosque.metodologiaespiral.entity.Estado;
import co.edu.unbosque.metodologiaespiral.entity.Proyecto;
import co.edu.unbosque.metodologiaespiral.entity.Tarea;
import co.edu.unbosque.metodologiaespiral.entity.Usuario;

@Component
public class PDFUtil {

    private static final float MARGEN = 50;
    private static final float ESPACIO_INFERIOR = 60;

    private static final float ANCHO_PAGINA =
            PDRectangle.A4.getWidth();

    private static final float ALTO_PAGINA =
            PDRectangle.A4.getHeight();

    private static final float ANCHO_UTIL =
            ANCHO_PAGINA - (MARGEN * 2);

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private PDDocument documento;
    private PDPage paginaActual;
    private PDPageContentStream contenido;

    private PDFont fuenteNormal;
    private PDFont fuenteNegrita;

    private float yActual;

    public byte[] generarPdf(Proyecto proyecto)
            throws IOException {

        ByteArrayOutputStream salida =
                new ByteArrayOutputStream();

        try (PDDocument doc = new PDDocument()) {

            this.documento = doc;

            this.fuenteNormal =
                    new PDType1Font(
                            Standard14Fonts.FontName.TIMES_ROMAN);

            this.fuenteNegrita =
                    new PDType1Font(
                            Standard14Fonts.FontName.TIMES_BOLD);

            nuevaPagina();

            // Encabezado principal
            dibujarEncabezadoPrincipal(proyecto);

            // Información del proyecto
            dibujarCajaInformacionGeneral(proyecto);

            // Tareas
            dibujarTituloSeccion("TAREAS");

            if (proyecto.getTareas() == null
                    || proyecto.getTareas().isEmpty()) {

                dibujarCajaMensaje(
                        "Este proyecto no tiene tareas registradas.");

            } else {

                for (Tarea tarea : proyecto.getTareas()) {

                    dibujarCajaTarea(tarea);
                }
            }

            // Usuarios
            dibujarTituloSeccion(
                    "USUARIOS DEL PROYECTO");

            if (proyecto.getUsuarios() == null
                    || proyecto.getUsuarios().isEmpty()) {

                dibujarCajaMensaje(
                        "Este proyecto no tiene usuarios asignados.");

            } else {

                for (Usuario usuario :
                        proyecto.getUsuarios()) {

                    dibujarCajaUsuario(usuario);
                }
            }

            if (contenido != null) {
                contenido.close();
            }

            documento.save(salida);
        }

        return salida.toByteArray();
    }


    private void nuevaPagina()
            throws IOException {

        if (contenido != null) {

            contenido.close();
        }

        paginaActual =
                new PDPage(PDRectangle.A4);

        documento.addPage(paginaActual);

        contenido =
                new PDPageContentStream(
                        documento,
                        paginaActual);

        yActual =
                ALTO_PAGINA - MARGEN;
    }


    private void verificarEspacio(
            float altoNecesario)
            throws IOException {

        if (yActual - altoNecesario
                < ESPACIO_INFERIOR) {

            nuevaPagina();
        }
    }


    private void dibujarEncabezadoPrincipal(
            Proyecto proyecto)
            throws IOException {

        float altoEncabezado = 75;

        contenido.setNonStrokingColor(
                new Color(41, 128, 185));

        contenido.addRect(
                MARGEN,
                yActual - altoEncabezado,
                ANCHO_UTIL,
                altoEncabezado);

        contenido.fill();

        escribirTexto(
                "REPORTE DEL PROYECTO",
                MARGEN + 20,
                yActual - 28,
                fuenteNegrita,
                20,
                Color.WHITE
        );

        escribirTexto(
                textoSeguro(proyecto.getNombre()),
                MARGEN + 20,
                yActual - 52,
                fuenteNormal,
                12,
                Color.WHITE
        );

        yActual -= altoEncabezado + 20;
    }

    private void dibujarCajaInformacionGeneral(
            Proyecto proyecto)
            throws IOException {

        List<String> lineasDescripcion =
                partirTexto(
                        textoSeguro(
                                proyecto.getDescripcion()),
                        fuenteNormal,
                        11,
                        ANCHO_UTIL - 30
                );

        float altoCaja =
                65
                        + (lineasDescripcion.size() * 14);

        verificarEspacio(altoCaja);

        float x = MARGEN;
        float y = yActual;

        contenido.setNonStrokingColor(
                new Color(245, 247, 250));

        contenido.addRect(
                x,
                y - altoCaja,
                ANCHO_UTIL,
                altoCaja);

        contenido.fill();

        contenido.setStrokingColor(
                new Color(189, 195, 199));

        contenido.addRect(
                x,
                y - altoCaja,
                ANCHO_UTIL,
                altoCaja);

        contenido.stroke();

        escribirTexto(
                "Nombre del proyecto:",
                x + 15,
                y - 22,
                fuenteNegrita,
                12,
                new Color(52, 73, 94)
        );

        escribirTexto(
                textoSeguro(proyecto.getNombre()),
                x + 140,
                y - 22,
                fuenteNormal,
                12,
                Color.BLACK
        );

        escribirTexto(
                "Descripción:",
                x + 15,
                y - 45,
                fuenteNegrita,
                11,
                new Color(52, 73, 94)
        );

        float yDescripcion =
                y - 62;

        for (String linea :
                lineasDescripcion) {

            escribirTexto(
                    linea,
                    x + 15,
                    yDescripcion,
                    fuenteNormal,
                    11,
                    Color.BLACK
            );

            yDescripcion -= 14;
        }

        yActual -= altoCaja + 18;
    }


    private void dibujarTituloSeccion(
            String titulo)
            throws IOException {

        float alto = 28;

        verificarEspacio(alto + 15);

        contenido.setNonStrokingColor(
                new Color(52, 152, 219));

        contenido.addRect(
                MARGEN,
                yActual - alto,
                ANCHO_UTIL,
                alto);

        contenido.fill();

        escribirTexto(
                titulo,
                MARGEN + 12,
                yActual - 19,
                fuenteNegrita,
                13,
                Color.WHITE
        );

        yActual -= alto + 14;
    }


    private void dibujarCajaMensaje(
            String mensaje)
            throws IOException {

        float alto = 40;

        verificarEspacio(alto);

        float x = MARGEN;
        float y = yActual;

        contenido.setNonStrokingColor(
                new Color(252, 243, 207));

        contenido.addRect(
                x,
                y - alto,
                ANCHO_UTIL,
                alto);

        contenido.fill();

        contenido.setStrokingColor(
                new Color(241, 196, 15));

        contenido.addRect(
                x,
                y - alto,
                ANCHO_UTIL,
                alto);

        contenido.stroke();

        escribirTexto(
                mensaje,
                x + 12,
                y - 25,
                fuenteNormal,
                11,
                new Color(102, 51, 0)
        );

        yActual -= alto + 12;
    }


    private void dibujarCajaTarea(
            Tarea tarea)
            throws IOException {

        List<String> lineasDescripcion =
                partirTexto(
                        textoSeguro(
                                tarea.getDescripcion()),
                        fuenteNormal,
                        10,
                        ANCHO_UTIL - 30
                );

        float altoCaja =
                110
                        + (lineasDescripcion.size() * 12);

        verificarEspacio(altoCaja);

        float x = MARGEN;
        float y = yActual;


        contenido.setNonStrokingColor(
                new Color(232, 244, 252));

        contenido.addRect(
                x,
                y - altoCaja,
                ANCHO_UTIL,
                altoCaja);

        contenido.fill();

        contenido.setStrokingColor(
                new Color(52, 152, 219));

        contenido.setLineWidth(1);

        contenido.addRect(
                x,
                y - altoCaja,
                ANCHO_UTIL,
                altoCaja);

        contenido.stroke();


        escribirTexto(
                textoSeguro(
                        tarea.getNombre()),
                x + 15,
                y - 20,
                fuenteNegrita,
                13,
                new Color(21, 67, 96)
        );


        escribirTexto(
                "Descripción:",
                x + 15,
                y - 42,
                fuenteNegrita,
                10,
                Color.DARK_GRAY
        );

        float posicionDescripcion =
                y - 58;

        for (String linea :
                lineasDescripcion) {

            escribirTexto(
                    linea,
                    x + 15,
                    posicionDescripcion,
                    fuenteNormal,
                    10,
                    Color.BLACK
            );

            posicionDescripcion -= 12;
        }


        float estadoY =
                posicionDescripcion - 8;

        escribirTexto(
                "Estado:",
                x + 15,
                estadoY,
                fuenteNegrita,
                10,
                Color.DARK_GRAY
        );

        String estadoTexto =
                obtenerEstadoSeguro(
                        tarea.getEstado());

        Color colorEstado =
                obtenerColorEstado(
                        tarea.getEstado());


        float estadoX = x + 75;

        float anchoEstado = 115;
        float altoEstado = 20;

        float cajaEstadoY =
                estadoY - 14;

        // Fondo del estado

        contenido.setNonStrokingColor(
                colorEstado);

        contenido.addRect(
                estadoX,
                cajaEstadoY,
                anchoEstado,
                altoEstado);

        contenido.fill();

        /*
         * Calculamos el ancho del texto
         * para centrarlo.
         */

        float tamanioEstado = 9;

        float anchoTextoEstado =
                (fuenteNegrita
                        .getStringWidth(
                                estadoTexto)
                        / 1000)
                        * tamanioEstado;

        float textoEstadoX =
                estadoX
                        + (anchoEstado
                        - anchoTextoEstado)
                        / 2;



        float textoEstadoY =
                cajaEstadoY + 6;

        escribirTexto(
                estadoTexto,
                textoEstadoX,
                textoEstadoY,
                fuenteNegrita,
                tamanioEstado,
                Color.WHITE
        );


        float fechasY =
                cajaEstadoY - 22;

        escribirTexto(
                "Fecha inicio: "
                        + fechaSegura(
                        tarea.getFechaInicio()),
                x + 15,
                fechasY,
                fuenteNormal,
                10,
                Color.BLACK
        );

        escribirTexto(
                "Fecha entrega: "
                        + fechaSegura(
                        tarea.getFechaEntrega()),
                x + 230,
                fechasY,
                fuenteNormal,
                10,
                Color.BLACK
        );

        yActual -= altoCaja + 14;
    }



    private void dibujarCajaUsuario(
            Usuario usuario)
            throws IOException {

        float altoCaja = 60;

        verificarEspacio(altoCaja);

        float x = MARGEN;
        float y = yActual;

        contenido.setNonStrokingColor(
                new Color(232, 248, 245));

        contenido.addRect(
                x,
                y - altoCaja,
                ANCHO_UTIL,
                altoCaja);

        contenido.fill();

        contenido.setStrokingColor(
                new Color(46, 204, 113));

        contenido.addRect(
                x,
                y - altoCaja,
                ANCHO_UTIL,
                altoCaja);

        contenido.stroke();

        escribirTexto(
                textoSeguro(
                        usuario.getNombre()),
                x + 15,
                y - 22,
                fuenteNegrita,
                12,
                new Color(20, 90, 50)
        );

        escribirTexto(
                "Correo: "
                        + textoSeguro(
                        usuario.getCorreo()),
                x + 15,
                y - 42,
                fuenteNormal,
                10,
                Color.BLACK
        );

        yActual -= altoCaja + 12;
    }



    private void escribirTexto(
            String texto,
            float x,
            float y,
            PDFont fuente,
            float tamanio,
            Color color)
            throws IOException {

        contenido.beginText();

        contenido.setFont(
                fuente,
                tamanio);

        contenido.setNonStrokingColor(
                color);

        contenido.newLineAtOffset(
                x,
                y);

        contenido.showText(
                textoSeguroPdf(texto));

        contenido.endText();
    }



    private List<String> partirTexto(
            String texto,
            PDFont fuente,
            float tamanio,
            float anchoMaximo)
            throws IOException {

        List<String> lineas =
                new ArrayList<>();

        if (texto == null
                || texto.trim().isEmpty()) {

            lineas.add(
                    "No disponible");

            return lineas;
        }

        String[] palabras =
                texto.split("\\s+");

        StringBuilder lineaActual =
                new StringBuilder();

        for (String palabra :
                palabras) {

            String posibleLinea;

            if (lineaActual.length() == 0) {

                posibleLinea =
                        palabra;

            } else {

                posibleLinea =
                        lineaActual
                                + " "
                                + palabra;
            }

            float anchoTexto =
                    (fuente.getStringWidth(
                            posibleLinea)
                            / 1000)
                            * tamanio;

            if (anchoTexto
                    > anchoMaximo
                    && lineaActual.length() > 0) {

                lineas.add(
                        lineaActual.toString());

                lineaActual =
                        new StringBuilder(
                                palabra);

            } else {

                if (lineaActual.length() > 0) {

                    lineaActual.append(" ");
                }

                lineaActual.append(
                        palabra);
            }
        }

        if (lineaActual.length() > 0) {

            lineas.add(
                    lineaActual.toString());
        }

        return lineas;
    }


    private String textoSeguro(
            String texto) {

        if (texto == null
                || texto.trim().isEmpty()) {

            return "No disponible";
        }

        return texto;
    }


    private String textoSeguroPdf(
            String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("\n", " ")
                .replace("\r", " ")
                .replace("\t", " ");
    }


    private String fechaSegura(
            LocalDate fecha) {

        if (fecha == null) {

            return "No disponible";
        }

        return fecha.format(
                FORMATO_FECHA);
    }


    private String obtenerEstadoSeguro(
            Estado estado) {

        if (estado == null) {

            return "SIN ESTADO";
        }

        switch (estado) {

            case PENDIENTE:
                return "PENDIENTE";

            case EN_CURSO:
                return "EN CURSO";

            case COMPLETADO:
                return "COMPLETADO";

            default:
                return "SIN ESTADO";
        }
    }

    private Color obtenerColorEstado(
            Estado estado) {

        if (estado == null) {

            // Gris
            return new Color(
                    127,
                    140,
                    141);
        }

        switch (estado) {

            case PENDIENTE:

                // Naranja
                return new Color(
                        243,
                        156,
                        18);

            case EN_CURSO:

                // Azul
                return new Color(
                        52,
                        152,
                        219);

            case COMPLETADO:

                // Verde
                return new Color(
                        39,
                        174,
                        96);

            default:

                return new Color(
                        127,
                        140,
                        141);
        }
    }
}