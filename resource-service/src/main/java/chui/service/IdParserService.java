package chui.service;

import chui.exception.InvalidIdException;

public class IdParserService {

  private static final String INVALID_ID_TEMPLATE_MSG
      = "Invalid value '%s' for ID. Must be a positive integer";

  public long parseId(String id) {
    long resourceId;
    try {
      resourceId = Long.parseLong(id);
    } catch (NumberFormatException e) {
      throw new InvalidIdException(String.format(INVALID_ID_TEMPLATE_MSG, id));
    }

    if (resourceId <= 0) {
      throw new InvalidIdException(String.format(INVALID_ID_TEMPLATE_MSG, id));
    }
    return resourceId;
  }
}
