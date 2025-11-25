package co.edu.umanizales.helpdesku.model;

public interface CsvPersistable {

    String toCsv();

    void fromCsv(String csvLine);

    String[] headers();
}
