package clinica.controller;

import clinica.enums.TipoExportacao;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;

public class Exportador {

    public void exportar(File arquivo, TipoExportacao tipo, String titulo, List<String> cabecalhos, List<List<String>> dados) throws Exception {
        if (tipo == TipoExportacao.CSV) {
            exportarCSV(arquivo, cabecalhos, dados);
        } else if (tipo == TipoExportacao.PDF) {
            exportarPDF(arquivo, titulo, cabecalhos, dados);
        }
    }

    private void exportarCSV(File arquivo, List<String> cabecalhos, List<List<String>> dados) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            writer.write(String.join(",", cabecalhos));
            writer.newLine();
            for (List<String> linha : dados) {
                List<String> linhaTratada = linha.stream()
                        .map(s -> s != null ? "\"" + s.replace("\"", "\"\"") + "\"" : "")
                        .toList();
                writer.write(String.join(",", linhaTratada));
                writer.newLine();
            }
        }
    }

    private void exportarPDF(File arquivo, String titulo, List<String> cabecalhos, List<List<String>> dados) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(arquivo));
        document.open();

        Paragraph pTitulo = new Paragraph(titulo);
        pTitulo.setAlignment(Paragraph.ALIGN_CENTER);
        pTitulo.setSpacingAfter(20);
        document.add(pTitulo);

        PdfPTable table = new PdfPTable(cabecalhos.size());
        table.setWidthPercentage(100);

        for (String cabecalho : cabecalhos) {
            table.addCell(cabecalho);
        }
        for (List<String> linha : dados) {
            for (String celula : linha) {
                table.addCell(celula != null ? celula : "");
            }
        }
        document.add(table);
        document.close();
    }
}