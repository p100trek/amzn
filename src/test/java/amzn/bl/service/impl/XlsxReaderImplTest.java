package amzn.bl.service.impl;

import amzn.bl.service.XlsxReader;
import amzn.configuration.properties.AppProperties;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;


public class XlsxReaderImplTest {

    private XlsxReader xlsxReader;
    private final String ean = "4211125424169.00";
    private final String eanTxt = "4977766793261.00";
    private final String lnpNbr = "LPNHK065916067";
    private String xlsxReadDir;

    @Before
    public void setUp() throws Exception {
        this.xlsxReader = new XlsxReaderImpl(new FileHelperImpl());
        this.xlsxReadDir = AppProperties.xslxReadDir();
    }

    @Test
    public void getWorkBookByFileName() {
        InputStream is = xlsxReader.getInputStream(xlsxReadDir);
        Workbook workBookByFileName = xlsxReader.getWorkBookByFileName(is);
        assertThat(workBookByFileName).isNotNull();
    }

    @Test
    public void getRowByEan() {
        InputStream is = xlsxReader.getInputStream(xlsxReadDir);
        Workbook workbook = xlsxReader.getWorkBookByFileName(is);
        Sheet sheet = workbook.getSheetAt(0);
        Row rowByEan = xlsxReader.getRowSearchVal(sheet, ean);
        Row rowByEanTxt = xlsxReader.getRowSearchVal(sheet, eanTxt);
        Row rowByLpn = xlsxReader.getRowSearchVal(sheet, lnpNbr);
        assertThat(rowByEan).isNotEmpty();
        assertThat(rowByEanTxt).isNotEmpty();
        assertThat(rowByLpn).isNotEmpty();
    }
}