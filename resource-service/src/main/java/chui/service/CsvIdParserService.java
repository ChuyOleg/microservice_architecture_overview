package chui.service;

import chui.exception.InvalidCsvException;
import java.util.ArrayList;
import java.util.List;

public class CsvIdParserService {

  private static final String INVALID_CSV_TEMPLATE_MSG
      = "Invalid ID format: '%s'. Only positive integers are allowed";
  private static final int MAX_CSV_LENGTH = 200;
  private static final String EMPTY_CSV_MSG = "CSV string cannot be empty";
  private static final String CSV_TOO_LONG_MSG
      = "CSV string is too long: received %d characters, maximum allowed is %d";

  public List<Long> parseIds(String ids) {
    if (ids == null || ids.trim().isEmpty()) {
      throw new InvalidCsvException(EMPTY_CSV_MSG);
    }
    if (ids.length() >= MAX_CSV_LENGTH) {
      throw new InvalidCsvException(String.format(CSV_TOO_LONG_MSG, ids.length(), MAX_CSV_LENGTH));
    }

    String[] idArray = ids.split(",");
    List<Long> validIds = new ArrayList<>();
    for (String idStr : idArray) {
      try {
        long id = Long.parseLong(idStr.trim());
        if (id <= 0) {
          throw new InvalidCsvException(String.format(INVALID_CSV_TEMPLATE_MSG, idStr.trim()));
        }
        validIds.add(id);
      } catch (NumberFormatException e) {
        throw new InvalidCsvException(String.format(INVALID_CSV_TEMPLATE_MSG, idStr.trim()));
      }
    }
    return validIds;
  }
}
