package testdata;

import com.codeborne.xlstest.XLS;

public class ExcelUtils {

    public static String getStringValueCell(XLS xls, int sheet, int row, int cell) {
        return xls.excel.getSheetAt(sheet).getRow(row).getCell(cell).toString();
    }

    public static double getDoubleValueCell(XLS xls, int sheet, int row, int cell) {
        return xls.excel.getSheetAt(sheet).getRow(row).getCell(cell).getNumericCellValue();
    }
}
