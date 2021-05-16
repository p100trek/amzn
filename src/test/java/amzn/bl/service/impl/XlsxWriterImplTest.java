package amzn.bl.service.impl;

import amzn.bl.service.XlsxReader;
import amzn.bl.service.XlsxWriter;
import amzn.configuration.properties.AppProperties;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class XlsxWriterImplTest {

    private XlsxReader xlsxReader;
    private XlsxWriter xlsxWriter;
    private final String ean = "LPNHK065752652";
    private String xlsxReadDir;
    private String xlsxWriteDir;

    @Before
    public void setUp() throws Exception {
        AppProperties.reconfigureAppProperties("application-test.properties");
        this.xlsxReader = new XlsxReaderImpl(new FileHelperImpl());
        this.xlsxWriter = new XlsxWriterImpl(xlsxReader);
        this.xlsxReadDir = AppProperties.xslxReadDir();
        this.xlsxWriteDir = AppProperties.xslxWriteDir();
    }

    @Test
    public void writeRow() {
        InputStream is = xlsxReader.getInputStream(xlsxReadDir);
        Workbook workbook = xlsxReader.getWorkBookByFileName(is);
        Row rowToCp = xlsxReader.getRowSearchVal(workbook.getSheetAt(0), ean);

        InputStream isToWrite = xlsxReader.getInputStream(xlsxWriteDir);
        Workbook workbookToUpdate = xlsxReader.getWorkBookByFileName(isToWrite);
        Row headersRow = workbook.getSheetAt(0).getRow(0);
        Row row = xlsxWriter.writeRow(workbookToUpdate, workbookToUpdate.getSheetAt(0), headersRow, rowToCp);
        Assertions.assertThat(row).isNotEmpty();
    }
}
