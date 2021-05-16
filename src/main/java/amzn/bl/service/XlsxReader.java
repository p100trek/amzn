package amzn.bl.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface XlsxReader {

    InputStream getInputStream(String fileName);

    Workbook getWorkBookByFileName(InputStream is);

    Row getRowSearchVal(Sheet sheet, String searchVal);

    Map<String, Integer> getHeaderIndexMap(List<String> headers, Row row);
}
