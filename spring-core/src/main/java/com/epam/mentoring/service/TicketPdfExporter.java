package com.epam.mentoring.service;

import com.epam.mentoring.model.Ticket;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.Color;

import java.io.IOException;
import java.util.List;

public class TicketPdfExporter {
    private List<Ticket> ticketList;

    public TicketPdfExporter(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.ORANGE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("Ticket Id", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Event Id", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("User Id", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Category", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Place", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Ticket ticket : ticketList) {
            table.addCell(String.valueOf(ticket.getId()));
            table.addCell(String.valueOf(ticket.getEventId()));
            table.addCell(String.valueOf(ticket.getUserId()));
            table.addCell(ticket.getCategory().toString());
            table.addCell(String.valueOf(ticket.getPlace()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Tickets", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }
}
