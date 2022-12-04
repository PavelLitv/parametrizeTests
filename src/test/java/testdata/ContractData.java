package testdata;

import com.codeborne.xlstest.XLS;

import java.util.List;

import static testdata.ExcelUtils.getDoubleValueCell;
import static testdata.ExcelUtils.getStringValueCell;

public class ContractData {
    String fullName,
    numberContract,
    address,
    currentReadings;

    public ContractData(XLS xls) {
        fullName = getStringValueCell(xls, 0, 0, 1);
        numberContract = getStringValueCell(xls, 0, 1, 1);
        address = getStringValueCell(xls, 0, 5, 1);
        currentReadings = ((int)getDoubleValueCell(xls, 0, 7, 17)) + "";
    }

    public static List<String> getDataContractForVerifyXLSX(ContractData cd) {
        return List.of(cd.fullName, cd.numberContract, cd.address, cd.currentReadings);
    }
}
