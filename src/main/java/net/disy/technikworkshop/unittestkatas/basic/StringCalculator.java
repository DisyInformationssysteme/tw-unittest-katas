package net.disy.technikworkshop.unittestkatas.basic;

import org.apache.commons.lang.StringUtils;

public class StringCalculator {

  public int add(final String numbers) {
    ParsedNumber parsedNumber = ParsedNumber.parse(numbers);
    String[] splittedNumbers = split(parsedNumber.getBody(), parsedNumber.getDelimiter());
    int result = 0;

    for (String numberAsString : splittedNumbers) {
      int parseInt = Integer.parseInt(numberAsString);
      if (parseInt < 0) {
        throw new IllegalArgumentException("negatives not allowed " + parseInt);
      }
      result += parseInt;
    }
    return result;
  }

  private String[] split(final String numbers, final String delimiter) {
    return StringUtils.split(numbers.replaceAll("\n", delimiter), delimiter);
  }
}
