package uitests.workwithfiles;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;
import testdata.ContractData;
import testdata.dto.Parking;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static testdata.ContractData.getDataContractForVerifyXLSX;

public class WorkWithFilesTests {
    ClassLoader cl = getClass().getClassLoader();

    @Test
    void verifyFilesInZipFile() throws IOException {
        SelenideLogger.addListener("allure", new AllureSelenide());
        List<String> expected = List.of("contract30613.XLSX", "currencies.csv", "act_93.pdf");
        List<String> actual = new ArrayList<>();

        try (
                InputStream is = cl.getResourceAsStream("filefortests.zip");
                ZipInputStream zis = new ZipInputStream(is)
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                actual.add(entry.getName());
            }
        }
        assertEquals(expected, actual);
    }

    @Test
    void verifyXlsFileInZipFile() throws IOException {
        SelenideLogger.addListener("allure", new AllureSelenide());
        List<String> expected = List.of("Иванов Сергей Игоревич", "Э100030613", "625029, обл Тюменская, г Тюмень", "23880");
        XLS content = null;

        try (
                InputStream is = cl.getResourceAsStream("filefortests.zip");
                ZipInputStream zis = new ZipInputStream(is)
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".XLSX")) {
                    content = new XLS(zis);
                    break;
                }
            }
        }
        assertEquals(expected, getDataContractForVerifyXLSX(new ContractData(content)));
    }

    @Test
    void verifyPdfFileInZipFile() throws IOException {
        String producer = "www.ilovepdf.com";
        PDF content = null;

        try (
                InputStream is = cl.getResourceAsStream("filefortests.zip");
                ZipInputStream zis = new ZipInputStream(is)
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".pdf")) {
                    content = new PDF(zis);
                    break;
                }
            }
        }
        assertEquals(producer, content.producer);
    }

    @Test
    void verifyCsvFileInZipFile() throws Exception {
        List<String[]> content = new ArrayList<>();
        String[] expect = {"Белорусский рубль", "BYN", "р."};

        try (
                InputStream is = cl.getResourceAsStream("filefortests.zip");
                ZipInputStream zis = new ZipInputStream(is)
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".csv")) {
                    try (CSVReader cr = new CSVReader(new InputStreamReader(zis))) {
                        content = cr.readAll();
                    }
                    break;
                }
            }
        }
        assertArrayEquals(content.get(1), expect);
    }

    @Test
    void jsonParseTest() throws IOException {
        String[] actualBMW = {"320", "X3", "X5"};
        ObjectMapper mapper = new ObjectMapper();

        try (
                InputStream resource = cl.getResourceAsStream("example.json");
                InputStreamReader reader = new InputStreamReader(resource)
        ) {
            Parking parking = mapper.readValue(reader, Parking.class);
            assertArrayEquals(actualBMW, parking.getCars()[1].getModels());
        }
    }
}
