package pl.harpi.hexal.model.domain.services;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.Validate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceHelper {
  public static String templateAbsolutePath(String filePath) {
    val url = ResourceHelper.class.getClassLoader().getResource(filePath);
    return Validate.notNull(url, "File '%s' not found", filePath).getPath();
  }
}
