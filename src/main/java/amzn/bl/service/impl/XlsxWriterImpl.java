package amzn.bl.service.impl;

import amzn.bl.service.XlsxReader;
import amzn.bl.service.XlsxWriter;
import amzn.configuration.properties.AppProperties;
import org.apache.poi.ss.usermodel.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class XlsxWriterImpl implements XlsxWriter {

    private XlsxReader xlsxReader;
    private Map<String, Integer> headerIndexMap;

    public XlsxWriterImpl(XlsxReader xlsxReader) {
        this.xlsxReader = xlsxReader;
    }

    @Override
    public Row writeRow(Workbook workbook, Sheet sheet, Row headersRow, Row rowToInsert) {
        this.headerIndexMap = xlsxReader.getHeaderIndexMap(AppProperties.columnHeaderToCopy(), headersRow);
        Row row = fillRow(sheet, rowToInsert);

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(AppProperties.xslxWriteDir());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return row;
    }

    private int getFirstEmptyRowIndex(Sheet sheet) {
        int rowCount = sheet.getLastRowNum();
        for (Row row : sheet) {
            if (isRowEmpty(row)) {
                rowCount = row.getRowNum();
                break;
            }
        }
        return rowCount;
    }

    private Row fillRow(Sheet sheet, Row rowToInsert) {
        int firstEmptyRowIndex = getFirstEmptyRowIndex(sheet);
        Row row = sheet.createRow(firstEmptyRowIndex);
        int newCellIndexCounter = 0;
        for (Map.Entry<String, Integer> him : headerIndexMap.entrySet()) {
            Integer index = him.getValue();
            Cell cellToInsert = rowToInsert.getCell(index);
            Cell cell = row.createCell(newCellIndexCounter);
            if (cellToInsert.getCellType().equals(CellType.STRING)) {
                cell.setCellValue(cellToInsert.getStringCellValue());
            } else if (cellToInsert.getCellType().equals(CellType.NUMERIC)) {
                cell.setCellValue(cellToInsert.getNumericCellValue());
            }
            newCellIndexCounter++;
        }

        return row;
    }

    private boolean isRowEmpty(Row row) {
        boolean isEmpty = true;
        DataFormatter dataFormatter = new DataFormatter();
        if (row != null) {
            for (Cell cell : row) {
                if (dataFormatter.formatCellValue(cell).trim().length() > 0) {
                    isEmpty = false;
                    break;
                }
            }
        }
        return isEmpty;
    }
}
