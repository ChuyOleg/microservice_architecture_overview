package chui.service;

import chui.exception.InvalidCsvException;
import java.util.ArrayList;
import java.util.List;

public class CsvIdParserService {

  private static final String INVALID_CSV_TEMPLATE_MSG
      = "Invalid ID format: 'V'. Only positive integers are allowed";

  public List<Long> parseIds(String ids) {
    if (ids == null || ids.trim().isEmpty()) {
      throw new InvalidCsvException("CSV string cannot be empty");
    }
    if (ids.length() >= 200) {
      throw new InvalidCsvException(String.format(
          "CSV string is too long: received %d characters, maximum allowed is 200", ids.length()));
    }
    String[] idArray = ids.split(",");
    List<Long> validIds = new ArrayList<>();
    for (String idStr : idArray) {
      try {
        long id = Long.parseLong(idStr.trim());
        if (id <= 0) {
          throw new InvalidCsvException(INVALID_CSV_TEMPLATE_MSG.replace("V", idStr.trim()));
        }
        validIds.add(id);
      } catch (NumberFormatException e) {
        throw new InvalidCsvException(INVALID_CSV_TEMPLATE_MSG.replace("V", idStr.trim()));
      }
    }
    return validIds;
  }
}
