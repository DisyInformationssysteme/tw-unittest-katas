package net.disy.technikworkshop.unittestkatas.basic;

public class ParsedNumber {

  private static final String DEFAULT_DELIMITER = ",";
  private final String body;
  private final String delimiter;

  private ParsedNumber(String body, String delimiter) {
    super();
    this.body = body;
    this.delimiter = delimiter;
  }

  public static ParsedNumber parse(String numbers) {
    String body = numbers;
    String delimiter = DEFAULT_DELIMITER;
    String customDelimiterMarker = "//";
    if (numbers.startsWith(customDelimiterMarker)) {
      delimiter = numbers.substring(2, 3);
      customDelimiterMarker += delimiter + "\n";
      body = numbers.replace(customDelimiterMarker, "");
    }
    return new ParsedNumber(body, delimiter);
  }

  public String getBody() {
    return body;
  }

  public String getDelimiter() {
    return delimiter;
  }

}
