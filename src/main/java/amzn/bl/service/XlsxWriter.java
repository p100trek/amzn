package amzn.bl.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface XlsxWriter {

    Row writeRow(Workbook workbook, Sheet sheet, Row headersRow, Row rowToInsert);
}
