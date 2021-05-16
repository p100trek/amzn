import amzn.bl.service.FileHelper;
import amzn.bl.service.XlsxReader;
import amzn.bl.service.XlsxWriter;
import amzn.bl.service.impl.FileHelperImpl;
import amzn.bl.service.impl.XlsxReaderImpl;
import amzn.bl.service.impl.XlsxWriterImpl;
import amzn.configuration.properties.AppProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        boolean shouldPrompt = true;

        System.out.println(args);
        AppProperties.reconfigureAppProperties(args[0]);

        FileHelper fileHelper = new FileHelperImpl();
        XlsxReader reader = new XlsxReaderImpl(fileHelper);
        XlsxWriter writer = new XlsxWriterImpl(reader);

        while (shouldPrompt) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("input ean or lpn or smth else xd:");
            String input = scanner.nextLine();
            if (StringUtils.defaultString(input, "").equalsIgnoreCase("exit")) {
                break;
            }
            InputStream inputStream = reader.getInputStream(AppProperties.xslxReadDir());
            Workbook workbook = reader.getWorkBookByFileName(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Row rowSearchVal = reader.getRowSearchVal(sheet, input);

            if (rowSearchVal != null) {
                InputStream inputStreamToWrite = reader.getInputStream(AppProperties.xslxWriteDir());
                Workbook workbookToWrite = reader.getWorkBookByFileName(inputStreamToWrite);

                Row row = writer.writeRow(workbookToWrite, workbookToWrite.getSheetAt(0), sheet.getRow(0), rowSearchVal);
                if (row == null) {
                    System.err.println("copy row failed");
                }
                if (row != null) {
                    System.out.println("row copy success: ");
                }

            } else
                System.err.println("row by ean or asin: " + input + ", not found");
        }

    }
}
