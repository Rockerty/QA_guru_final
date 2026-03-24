package tests;

import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import utils.ZipUtils;
import com.codeborne.pdftest.PDF;
import static org.junit.jupiter.api.Assertions.*;

public class PdfToZipTest {

    @Test
    public void testPackPdfToZip() throws IOException {
        InputStream pdfStream = getClass().getClassLoader().getResourceAsStream("datafiles/Book list.pdf");
        ZipUtils.packFileToZip(pdfStream, "datafiles/Book list.pdf", "src\\test\\resources\\zips\\zipPdf.zip");
        pdfStream.close();

        File zipFile = new File("src\\test\\resources\\zips\\zipPdf.zip");
        assertTrue(zipFile.exists());

        try (ZipFile zip = new ZipFile(zipFile)) {
            assertEquals(1, zip.size());
            ZipEntry entry = zip.getEntry("datafiles/Book list.pdf");
            assertNotNull(entry);

            try (InputStream zipPdfStream = zip.getInputStream(entry)) {
                PDF pdf = new PDF(zipPdfStream);
                Assertions.assertEquals("Admin", pdf.author);
                Assertions.assertEquals(9, pdf.numberOfPages);
                Assertions.assertTrue(pdf.text.contains("Список на лето"));
            }
        }
    }

    @Test
    public void testPackXlsxToZip() throws IOException {
        InputStream xlsxStream = getClass().getClassLoader().getResourceAsStream("datafiles/Price.xls");
        ZipUtils.packFileToZip(xlsxStream, "datafiles/Price.xls", "src\\test\\resources\\zips\\zipXls.zip");
        xlsxStream.close();

        File zipFile = new File("src\\test\\resources\\zips\\zipXls.zip");
        assertTrue(zipFile.exists());

        try (ZipFile zip = new ZipFile(zipFile)) {
            ZipEntry entry = zip.getEntry("datafiles/Price.xls");
            assertNotNull(entry);

            try (InputStream zipXlsStream = zip.getInputStream(entry)) {
                XLS xls = new XLS(zipXlsStream);
                String actualValue = xls.excel.getSheetAt(0).getRow(1).getCell(0).getStringCellValue();
                Assertions.assertTrue(actualValue.contains("ЛОКАЛЬНЫЙ СМЕТНЫЙ РАСЧЕТ"));
            }
        }
    }

    @Test
    public void testPackCsvToZip() throws Exception {
        InputStream csvStream = getClass().getClassLoader().getResourceAsStream("datafiles/csv_example.csv");
        ZipUtils.packFileToZip(csvStream, "datafiles/csv_example.csv", "src\\test\\resources\\zips\\zipCsv.zip");
        csvStream.close();

        File zipFile = new File("src\\test\\resources\\zips\\zipCsv.zip");
        assertTrue(zipFile.exists());

        try (ZipFile zip = new ZipFile(zipFile)) {
            ZipEntry entry = zip.getEntry("datafiles/csv_example.csv");
            assertNotNull(entry);

            try (InputStream zipCsvStream = zip.getInputStream(entry);
                 CSVReader reader = new CSVReader(new InputStreamReader(zipCsvStream))) {

                List<String[]> rows = reader.readAll();
                String[] row = rows.get(0);
                assertEquals(14, row.length);
                assertArrayEquals(new String[]{"Series_reference", "Period","Data_value","Suppressed","STATUS","UNITS","Magnitude","Subject","Group","Series_title_1","Series_title_2","Series_title_3","Series_title_4","Series_title_5"}, row);

                String cellValue = rows.get(0)[0];
                assertEquals("Series_reference", cellValue);
            }
        }
    }
}