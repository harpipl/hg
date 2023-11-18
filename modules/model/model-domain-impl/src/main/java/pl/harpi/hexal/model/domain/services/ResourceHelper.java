package pl.harpi.hexal.model.domain.services;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceHelper {
  public static String templateAbsolutePath(String filePath) {
    val url = ResourceHelper.class.getClassLoader().getResource(filePath);

    if (url == null) {
      throw new NullPointerException("File '" + filePath + "' not found");
    } else {
      return url.getPath();
    }
  }
}
