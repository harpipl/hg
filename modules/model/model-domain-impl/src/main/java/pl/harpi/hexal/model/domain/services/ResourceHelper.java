package pl.harpi.hexal.model.domain.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceHelper {
  private static InputStream templatePath(String filePath) {
    return Validate.notNull(
            ResourceHelper.class.getClassLoader().getResourceAsStream(filePath),
            "File '%s' not found",
            filePath);
  }

  public static void copyTemplateFromResource(
          String inputFile, String outputFile, Map<String, String> parameters) {

    val inputStream = templatePath(inputFile);

    val lines =
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .toList();
    val pattern = Pattern.compile("<%=(.*?)%>");

    try {
      val outputStreamWriter = new OutputStreamWriter(new FileOutputStream(outputFile));
      val bufferedWriter = new BufferedWriter(outputStreamWriter);

      for (var line : lines) {
        val matcher = pattern.matcher(line);

        while (matcher.find()) {
          val param = parameters.get(matcher.group(1).trim());

          if (StringUtils.isNotEmpty(param)) {
            line = line.replace(matcher.group(), param);
          }
        }

        bufferedWriter.write(line);
        bufferedWriter.newLine();
      }
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
