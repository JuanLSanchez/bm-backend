package es.juanlsanchez.bm.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import es.juanlsanchez.bm.web.dto.QuarterDTO;

public interface DocumentService {

  public HSSFWorkbook createIncomeDocument(QuarterDTO quarterDTO);

  public HSSFWorkbook createInvoiceDocument(QuarterDTO quarterDTO);

}
