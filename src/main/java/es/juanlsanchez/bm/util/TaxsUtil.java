package es.juanlsanchez.bm.util;

public class TaxsUtil {

  public static Double baseAndIvaToTotal(Double base, Integer iva) {
    return base * (1 + iva.doubleValue() / 100);
  }

}
