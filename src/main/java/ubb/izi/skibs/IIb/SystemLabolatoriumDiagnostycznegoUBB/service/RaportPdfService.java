package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.service;

import org.openpdf.text.*;
import org.openpdf.text.pdf.BaseFont;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.Pacjent;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.WynikBadania;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RaportPdfService {

    private static final DateTimeFormatter DATA_FORMAT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public byte[] generujRaport(List<WynikBadania> wyniki) {

        if (wyniki == null || wyniki.isEmpty()) {
            throw new IllegalArgumentException(
                    "Nie wybrano żadnych wyników do raportu."
            );
        }

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        Document document = new Document(
                PageSize.A4,
                45,
                45,
                45,
                45
        );

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            BaseFont baseFont = BaseFont.createFont(
                    BaseFont.HELVETICA,
                    BaseFont.CP1250,
                    BaseFont.NOT_EMBEDDED
            );

            Font titleFont = new Font(
                    baseFont,
                    20,
                    Font.BOLD,
                    new Color(0, 45, 77)
            );

            Font sectionFont = new Font(
                    baseFont,
                    13,
                    Font.BOLD,
                    new Color(0, 45, 77)
            );

            Font normalFont = new Font(
                    baseFont,
                    10,
                    Font.NORMAL,
                    Color.BLACK
            );

            Font boldFont = new Font(
                    baseFont,
                    10,
                    Font.BOLD,
                    Color.BLACK
            );

            Font smallFont = new Font(
                    baseFont,
                    8,
                    Font.NORMAL,
                    new Color(90, 100, 105)
            );

            dodajNaglowek(
                    document,
                    titleFont,
                    normalFont
            );

            Pacjent pacjent = wyniki.getFirst().getPacjent();

            dodajDanePacjenta(
                    document,
                    pacjent,
                    sectionFont,
                    normalFont,
                    boldFont
            );

            dodajTabeleWynikow(
                    document,
                    wyniki,
                    sectionFont,
                    normalFont,
                    boldFont,
                    smallFont
            );

            dodajKomentarze(
                    document,
                    wyniki,
                    sectionFont,
                    normalFont,
                    boldFont
            );

            dodajStopke(
                    document,
                    smallFont
            );

        } catch (DocumentException exception) {
            throw new IllegalStateException(
                    "Nie udało się wygenerować dokumentu PDF.",
                    exception
            );
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Wystąpił błąd podczas przygotowywania PDF.",
                    exception
            );
        } finally {
            document.close();
        }

        return outputStream.toByteArray();
    }

    private void dodajNaglowek(
            Document document,
            Font titleFont,
            Font normalFont
    ) throws DocumentException {

        Paragraph title = new Paragraph(
                "Raport wyników badań laboratoryjnych",
                titleFont
        );

        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(8);

        document.add(title);

        Paragraph subtitle = new Paragraph(
                "System Laboratorium Diagnostycznego UBB",
                normalFont
        );

        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(24);

        document.add(subtitle);
    }

    private void dodajDanePacjenta(
            Document document,
            Pacjent pacjent,
            Font sectionFont,
            Font normalFont,
            Font boldFont
    ) throws DocumentException {

        Paragraph sectionTitle = new Paragraph(
                "Dane pacjenta",
                sectionFont
        );

        sectionTitle.setSpacingAfter(10);
        document.add(sectionTitle);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 2});
        table.setSpacingAfter(22);

        dodajPole(
                table,
                "Imię i nazwisko",
                pacjent.getImie() + " " + pacjent.getNazwisko(),
                boldFont,
                normalFont
        );

        dodajPole(
                table,
                "PESEL",
                bezpiecznyTekst(pacjent.getPesel()),
                boldFont,
                normalFont
        );

        dodajPole(
                table,
                "Numer karty pacjenta",
                bezpiecznyTekst(
                        pacjent.getNumerKartyPacjenta()
                ),
                boldFont,
                normalFont
        );

        dodajPole(
                table,
                "Data urodzenia",
                pacjent.getDataUrodzenia() != null
                        ? pacjent.getDataUrodzenia().toString()
                        : "Brak danych",
                boldFont,
                normalFont
        );

        document.add(table);
    }

    private void dodajTabeleWynikow(
            Document document,
            List<WynikBadania> wyniki,
            Font sectionFont,
            Font normalFont,
            Font boldFont,
            Font smallFont
    ) throws DocumentException {

        Paragraph sectionTitle = new Paragraph(
                "Wyniki badań",
                sectionFont
        );

        sectionTitle.setSpacingAfter(10);
        document.add(sectionTitle);

        PdfPTable table = new PdfPTable(6);

        table.setWidthPercentage(100);

        table.setWidths(
                new float[]{2.4f, 1.2f, 1.2f, 1.7f, 1.5f, 1.8f}
        );

        table.setHeaderRows(1);

        dodajNaglowekTabeli(table, "Badanie", boldFont);
        dodajNaglowekTabeli(table, "Wynik", boldFont);
        dodajNaglowekTabeli(table, "Jednostka", boldFont);
        dodajNaglowekTabeli(table, "Zakres", boldFont);
        dodajNaglowekTabeli(table, "Status", boldFont);
        dodajNaglowekTabeli(table, "Data", boldFont);

        for (WynikBadania wynik : wyniki) {

            String zakres = przygotujZakres(wynik);

            String status =
                    Boolean.TRUE.equals(
                            wynik.getFlagaNieprawidlowy()
                    )
                            ? "Nieprawidłowy"
                            : "Prawidłowy";

            dodajKomorke(
                    table,
                    wynik.getBadanieRodzaj().getNazwa(),
                    normalFont,
                    Color.WHITE
            );

            dodajKomorke(
                    table,
                    wynik.getWartoscLiczbowa() != null
                            ? wynik.getWartoscLiczbowa().toPlainString()
                            : bezpiecznyTekst(
                            wynik.getWartoscOpisowa()
                    ),
                    boldFont,
                    Color.WHITE
            );

            dodajKomorke(
                    table,
                    bezpiecznyTekst(wynik.getJednostka()),
                    normalFont,
                    Color.WHITE
            );

            dodajKomorke(
                    table,
                    zakres,
                    smallFont,
                    Color.WHITE
            );

            Color statusColor =
                    Boolean.TRUE.equals(
                            wynik.getFlagaNieprawidlowy()
                    )
                            ? new Color(253, 236, 235)
                            : new Color(232, 247, 242);

            dodajKomorke(
                    table,
                    status,
                    boldFont,
                    statusColor
            );

            dodajKomorke(
                    table,
                    wynik.getDataWykonania() != null
                            ? wynik.getDataWykonania()
                            .format(DATA_FORMAT)
                            : "Brak daty",
                    smallFont,
                    Color.WHITE
            );
        }

        table.setSpacingAfter(22);
        document.add(table);
    }

    private void dodajKomentarze(
            Document document,
            List<WynikBadania> wyniki,
            Font sectionFont,
            Font normalFont,
            Font boldFont
    ) throws DocumentException {

        boolean istniejeKomentarz = wyniki.stream()
                .anyMatch(wynik ->
                        wynik.getKomentarzDiagnosty() != null &&
                                !wynik.getKomentarzDiagnosty().isBlank()
                );

        if (!istniejeKomentarz) {
            return;
        }

        Paragraph sectionTitle = new Paragraph(
                "Komentarze diagnosty",
                sectionFont
        );

        sectionTitle.setSpacingAfter(10);
        document.add(sectionTitle);

        for (WynikBadania wynik : wyniki) {

            String komentarz =
                    wynik.getKomentarzDiagnosty();

            if (komentarz == null || komentarz.isBlank()) {
                continue;
            }

            Paragraph testName = new Paragraph(
                    wynik.getBadanieRodzaj().getNazwa(),
                    boldFont
            );

            testName.setSpacingBefore(8);
            document.add(testName);

            Paragraph comment = new Paragraph(
                    komentarz,
                    normalFont
            );

            comment.setSpacingAfter(8);
            document.add(comment);
        }
    }

    private void dodajStopke(
            Document document,
            Font smallFont
    ) throws DocumentException {

        Paragraph footer = new Paragraph(
                "\nDokument wygenerowany elektronicznie przez " +
                        "System Laboratorium Diagnostycznego UBB.",
                smallFont
        );

        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(20);

        document.add(footer);
    }

    private void dodajPole(
            PdfPTable table,
            String label,
            String value,
            Font boldFont,
            Font normalFont
    ) {

        dodajKomorke(
                table,
                label,
                boldFont,
                new Color(248, 250, 251)
        );

        dodajKomorke(
                table,
                value,
                normalFont,
                Color.WHITE
        );
    }

    private void dodajNaglowekTabeli(
            PdfPTable table,
            String text,
            Font font
    ) {

        PdfPCell cell = new PdfPCell(
                new Phrase(text, font)
        );

        cell.setBackgroundColor(
                new Color(220, 232, 235)
        );

        cell.setPadding(8);
        cell.setVerticalAlignment(
                Element.ALIGN_MIDDLE
        );

        table.addCell(cell);
    }

    private void dodajKomorke(
            PdfPTable table,
            String text,
            Font font,
            Color backgroundColor
    ) {

        PdfPCell cell = new PdfPCell(
                new Phrase(bezpiecznyTekst(text), font)
        );

        cell.setBackgroundColor(backgroundColor);
        cell.setPadding(8);
        cell.setVerticalAlignment(
                Element.ALIGN_MIDDLE
        );

        table.addCell(cell);
    }

    private String przygotujZakres(
            WynikBadania wynik
    ) {

        if (wynik.getBadanieRodzaj() == null) {
            return "Brak danych";
        }

        var min =
                wynik.getBadanieRodzaj().getWartoscMin();

        var max =
                wynik.getBadanieRodzaj().getWartoscMax();

        if (min != null && max != null) {
            return min.toPlainString() +
                    " - " +
                    max.toPlainString();
        }

        if (min != null) {
            return "od " + min.toPlainString();
        }

        if (max != null) {
            return "do " + max.toPlainString();
        }

        return "Brak zakresu";
    }

    private String bezpiecznyTekst(String text) {
        return text == null || text.isBlank()
                ? "Brak danych"
                : text;
    }
}