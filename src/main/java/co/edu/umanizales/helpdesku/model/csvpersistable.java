package co.edu.umanizales.helpdesku.model;

public interface csvpersistable {

    String toCsv();

    void fromCsv(String csvLine);

    String[] headers();
}
