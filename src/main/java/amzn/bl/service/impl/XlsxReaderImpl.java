package amzn.bl.service.impl;

import amzn.bl.service.FileHelper;
import amzn.bl.service.XlsxReader;
import amzn.configuration.properties.AppProperties;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class XlsxReaderImpl implements XlsxReader {

    private final FileHelper fileHelper;
    private Map<String, Integer> headerIndexMapToSearch;

    public XlsxReaderImpl(FileHelper fileHelper) {
        this.fileHelper = new FileHelperImpl();
    }

    public InputStream getInputStream(String fileName){
     return fileHelper.getInputStream(fileName);
    }

    @Override
    public Workbook getWorkBookByFileName(InputStream is) {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    @Override
    public Row getRowSearchVal (Sheet sheet, String searchVal) {
        Row firstRow = sheet.getRow(0);
        this.headerIndexMapToSearch = getHeaderIndexMap(AppProperties.columnHeaderToSearch(), firstRow);

        for (Row row : sheet) {
            if (isSearchValueInRow(row, searchVal)) {
                return row;
            }
        }
        return null;
    }

    private boolean isSearchValueInRow(Row row, String searchVal) {
        for (Map.Entry<String, Integer> hi : headerIndexMapToSearch.entrySet()) {
            Cell cell = row.getCell(hi.getValue());
            if (isSearchValInCell(cell, searchVal)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSearchValInCell(Cell cell, String searchVal) {
        return cell != null && isCellContentEqualsSearchValue(cell, searchVal);
    }

    @Override
    public Map<String, Integer> getHeaderIndexMap(List<String> headers, Row row) {
        Map<String, Integer> headerIndexMap = new LinkedHashMap<>();

        for (String header : headers) {
            int cellIndexByHeader = getCellIndexByHeader(row, header);
            headerIndexMap.put(header, cellIndexByHeader);
        }

        return headerIndexMap;
    }

    public boolean isCellContentEqualsSearchValue(Cell cell, String input) {
        if (cell.getCellType().equals(CellType.STRING)) {
            return cell.getStringCellValue().trim().equalsIgnoreCase(input);
        }
        if (cell.getCellType().equals(CellType.NUMERIC)) {
            return cell.getNumericCellValue() == Double.parseDouble(input);
        }
        return false;
    }

    private int getCellIndexByHeader(Row row, String header) {
        for (Cell cell : row) {
            if (cell.getCellType().equals(CellType.STRING) && cell.getStringCellValue().trim().equalsIgnoreCase(header)) {
                return cell.getColumnIndex();
            }
        }
        return -1;
    }
}
