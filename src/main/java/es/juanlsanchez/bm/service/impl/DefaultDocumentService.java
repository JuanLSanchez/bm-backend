package es.juanlsanchez.bm.service.impl;

import java.time.Instant;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import es.juanlsanchez.bm.domain.Income;
import es.juanlsanchez.bm.service.DocumentService;
import es.juanlsanchez.bm.service.IncomeService;
import es.juanlsanchez.bm.web.dto.QuarterDTO;

@Service
@Transactional
public class DefaultDocumentService implements DocumentService {

  private final IncomeService incomeService;

  @Inject
  public DefaultDocumentService(final IncomeService incomeService) {
    this.incomeService = incomeService;
  }

  @Override
  public HSSFWorkbook createIncomeDocument(QuarterDTO quarterDTO) {
    Map<Integer, List<Income>> months = Maps.newHashMap();
    String timePattern = "%04d-%02d-%02dT00:00:00.00Z";
    for (int i = 1; i <= 3; i++) {
      int month = (quarterDTO.getQuarter() * 3) + i;
      int year = quarterDTO.getYear();
      Instant start = Instant.parse(String.format(timePattern, year, month, 1));
      Instant finish = Instant.parse(String.format(timePattern, year, month + 1, 1));
      months.put(month, this.incomeService
          .findAllByPrincipalAndIncomeDateGreaterThanEqualAndIncomeDateLessThan(start, finish));
    }

    HSSFWorkbook wb = new HSSFWorkbook();

    String[] titles = {"Nº ORDEN", "DD/MM/AA", "N.I.F.", "NOMBRE Y APELLIDOS O RAZÓN SOCIAL",
        "TIPO OPERACIÓN", "B.I.", "IVA €", "R.E.", "IRPF", "TOTAL FACTURA"};

    for (Integer month : months.keySet()) {
      Sheet sheet = wb.createSheet(month.toString());
      int row = 1;
      addRows(sheet, 0, titles);
      for (Income income : months.get(month)) {
        String[] incomeLine = incomeToLine(income, row, quarterDTO.getTimeZone());
        addRows(sheet, row, incomeLine);
        row++;
      }
    }

    return wb;
  }

  private String[] incomeToLine(Income income, int position, TimeZone timeZone) {
    String[] result = new String[10];

    double ivaPerOne = Double.valueOf(income.getIva()) / 100;
    int round = 100;
    double baseMoney = Math.round(income.getBase() * round) / round;
    double totalMoney = baseMoney / (1 - ivaPerOne);
    totalMoney = Math.round(totalMoney * round) / round;
    double ivaMoney = totalMoney - baseMoney;

    result[0] = String.valueOf(position);
    Calendar date = new GregorianCalendar();
    date.setTimeInMillis(income.getIncomeDate().toEpochMilli());
    // TODO: Transform to instants
    result[1] = String.format("%02d/%02d/%04d", date.get(Calendar.DAY_OF_MONTH),
        date.get(Calendar.MONTH) + 1, date.get(Calendar.YEAR));
    result[2] = income.getNif();
    result[3] = income.getName();
    result[5] = String.format("%.2f", baseMoney);
    result[6] = String.format("%.2f", ivaMoney);
    result[9] = String.format("%.2f", totalMoney);

    return result;
  }

  private void addRows(Sheet sheet, int rowNumber, String[] rows) {
    Row headerRow = sheet.createRow(rowNumber);
    for (int i = 0; i < rows.length; i++) {
      Cell cell = headerRow.createCell(i);
      cell.setCellValue(rows[i]);
    }
  }

}
