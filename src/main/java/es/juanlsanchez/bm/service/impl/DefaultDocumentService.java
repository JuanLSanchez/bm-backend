package es.juanlsanchez.bm.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import es.juanlsanchez.bm.CacheConfig;
import es.juanlsanchez.bm.domain.Income;
import es.juanlsanchez.bm.domain.Invoice;
import es.juanlsanchez.bm.domain.InvoiceLine;
import es.juanlsanchez.bm.domain.Section;
import es.juanlsanchez.bm.service.DocumentService;
import es.juanlsanchez.bm.service.IncomeService;
import es.juanlsanchez.bm.service.InvoiceService;
import es.juanlsanchez.bm.service.SectionService;
import es.juanlsanchez.bm.web.dto.QuarterDTO;

@Service
@Transactional
public class DefaultDocumentService implements DocumentService {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private final IncomeService incomeService;
  private final InvoiceService invoiceService;
  private final SectionService sectionService;

  @Inject
  public DefaultDocumentService(final IncomeService incomeService,
      final InvoiceService invoiceService, final SectionService sectionService) {
    this.incomeService = incomeService;
    this.invoiceService = invoiceService;
    this.sectionService = sectionService;
  }

  @Override
  @Cacheable(CacheConfig.SHORT_CACHE)
  public HSSFWorkbook createIncomeDocument(QuarterDTO quarterDTO) {
    Map<Integer, List<Income>> months = Maps.newHashMap();
    String timePattern = "%04d-%02d-%02d";
    for (int i = 1; i <= 3; i++) {
      int month = (quarterDTO.getQuarter() * 3) + i;
      int year = quarterDTO.getYear();
      LocalDate start = LocalDate.parse(String.format(timePattern, year, month, 1));
      LocalDate finish = LocalDate.parse(String.format(timePattern, year, month + 1, 1));
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

  @Override
  @Cacheable(CacheConfig.SHORT_CACHE)
  public HSSFWorkbook createInvoiceDocument(QuarterDTO quarterDTO) {
    Map<Integer, List<Invoice>> months = Maps.newHashMap();
    List<Section> sections = this.sectionService.findAllByPrincipalOrderByOrderAsc();
    Map<String, Integer> sectionsMap = IntStream.range(0, sections.size()).boxed()
        .collect(Collectors.toMap(index -> sections.get(index).getName(), index -> index));
    // sections.stream().collect(Collectors.toMap(Section::getName, Section::getOrder));
    String timePattern = "%04d-%02d-%02d";
    for (int i = 1; i <= 3; i++) {
      int month = (quarterDTO.getQuarter() * 3) + i;
      int year = quarterDTO.getYear();
      LocalDate start = LocalDate.parse(String.format(timePattern, year, month, 1));
      LocalDate finish = LocalDate.parse(String.format(timePattern, year, month + 1, 1));
      months.put(month, this.invoiceService
          .findAllByPrincipalAndDateBuyGreaterThanEqualAndDateBuyLessThan(start, finish));
    }

    HSSFWorkbook wb = new HSSFWorkbook();

    List<String> titlesList =
        Lists.newArrayList("Nº DE FACTURA", "DD/MM/AA", "PROVEEDOR", "N.I.F.", "TIPO OPERACIÓN");
    titlesList.addAll(sections.stream().map(Section::getName).collect(Collectors.toList()));
    titlesList.addAll(Lists.newArrayList("IVA €", "IRPF", "TOTAL FACTURA"));

    String[] titles = new String[titlesList.size()];
    titles = titlesList.toArray(titles);

    for (Integer month : months.keySet()) {
      Sheet sheet = wb.createSheet(month.toString());
      int row = 1;
      addRows(sheet, 0, titles);
      for (Invoice invoice : months.get(month)) {
        String[][] incomeLine =
            invoiceToLine(invoice, quarterDTO.getTimeZone(), titles.length, sectionsMap);
        if (incomeLine != null) {
          addRows(sheet, row, incomeLine);
          row += incomeLine.length;;
        }
      }
    }

    return wb;
  }


  // Utilities -----------------------------------------------

  private String[][] invoiceToLine(Invoice invoice, TimeZone timeZone, int length,
      Map<String, Integer> sectionsMap) {
    String[][] result = null;

    int numberOfInvoices = invoice.getInvoiceLines().size();
    if (numberOfInvoices > 0) {
      int basePosition = 5 + sectionsMap.get(invoice.getOperation().getSection().getName());

      result = new String[numberOfInvoices][length];
      result[0][0] = invoice.getNumber();
      result[0][1] = localDateToString(invoice.getDateBuy());
      result[0][2] = invoice.getSupplier().getName();
      result[0][3] = invoice.getSupplier().getNif();
      result[0][4] = invoice.getOperation().getName();
      int i = 0;
      double total = 0;
      for (InvoiceLine invoiceLine : invoice.getInvoiceLines()) {

        double ivaPerOne = Double.valueOf(invoiceLine.getIva()) / 100;
        int round = 100;
        double baseMoney = ((double) Math.round(invoiceLine.getBase() * round)) / round;
        double totalMoney = baseMoney / (1 - ivaPerOne);
        totalMoney = ((double) Math.round(totalMoney * round)) / round;
        double ivaMoney = totalMoney - baseMoney;

        result[i][basePosition] = String.format("%.2f", baseMoney);
        result[i][length - 3] = String.format("%.2f", ivaMoney);

        total += totalMoney;
        i++;
      }
      result[numberOfInvoices - 1][length - 1] = String.format("%.2f", total);

    }

    return result;
  }

  private void addRows(Sheet sheet, int firstRowNumber, String[][] rowsOfRows) {
    for (int i = 0; i < rowsOfRows.length; i++) {
      this.addRows(sheet, i + firstRowNumber, rowsOfRows[i]);
    }
  }

  private String[] incomeToLine(Income income, int position, TimeZone timeZone) {
    String[] result = new String[10];

    double ivaPerOne = Double.valueOf(income.getIva()) / 100;
    int round = 100;
    double baseMoney = ((double) Math.round(income.getBase() * round)) / round;
    double totalMoney = baseMoney / (1 - ivaPerOne);
    totalMoney = ((double) Math.round(totalMoney * round)) / round;
    double ivaMoney = totalMoney - baseMoney;

    result[0] = String.valueOf(position);
    // TODO: Transform to instants
    result[1] = localDateToString(income.getIncomeDate());
    result[2] = income.getNif();
    result[3] = income.getName();
    result[5] = String.format("%.2f", baseMoney);
    result[6] = String.format("%.2f", ivaMoney);
    result[9] = String.format("%.2f", totalMoney);

    return result;
  }

  private String localDateToString(LocalDate localDate) {
    return localDate.format(FORMATTER);
  }

  private void addRows(Sheet sheet, int rowNumber, String[] rows) {
    Row headerRow = sheet.createRow(rowNumber);
    for (int i = 0; i < rows.length; i++) {
      Cell cell = headerRow.createCell(i);
      cell.setCellValue(rows[i]);
    }
  }

}
