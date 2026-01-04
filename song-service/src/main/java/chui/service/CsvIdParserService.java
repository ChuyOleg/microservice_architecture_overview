package chui.service;

import chui.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CsvIdParserService {

  private static final String INVALID_ID_FORMAT_MSG
      = "Invalid ID format: '%s'. Only positive integers are allowed";
  private static final String CSV_TOO_LONG_MSG
      = "CSV string is too long: received %d characters, maximum allowed is 200";

  public List<Integer> parseIds(String ids) {
    if (ids == null || ids.trim().isEmpty()) {
      throw new ValidationException(String.format(INVALID_ID_FORMAT_MSG, ""));
    }
    if (ids.length() >= 200) {
      throw new ValidationException(String.format(CSV_TOO_LONG_MSG, ids.length()));
    }
    String[] idArray = ids.split(",");
    List<Integer> validIds = new ArrayList<>();
    List<String> invalidIds = new ArrayList<>();
    for (String idStr : idArray) {
      String trimmed = idStr.trim();
      try {
        int id = Integer.parseInt(trimmed);
        if (id <= 0) {
          invalidIds.add(trimmed);
        } else {
          validIds.add(id);
        }
      } catch (NumberFormatException e) {
        invalidIds.add(trimmed);
      }
    }
    if (!invalidIds.isEmpty()) {
      throw new ValidationException(
          String.format(INVALID_ID_FORMAT_MSG, String.join(",", invalidIds)));
    }
    return validIds;
  }
}
