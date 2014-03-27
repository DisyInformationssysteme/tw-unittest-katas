package net.disy.technikworkshop.unittestkatas.basic;

import static net.java.quickcheck.generator.CombinedGeneratorsIterables.someLists;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.disy.technikworkshop.unittestkatas.basic.StringCalculator;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.CombinedGenerators;
import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.iterable.Iterables;

import org.apache.commons.lang.StringUtils;
import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Joiner;

public class StringCalculatorTest {

  private StringCalculator stringCalculator;

  @Before
  public void init() {
    stringCalculator = new StringCalculator();
  }

  @Test
  public void addEmpty() throws Exception {
    assertEquals(0, stringCalculator.add(""));
  }

  @Test
  public void addOneNumber() {
    assertEquals(1, stringCalculator.add("1"));
  }

  @Test
  public void addOneRandomNumber() throws Exception {
    int randomNumber = new Random().nextInt(Integer.MAX_VALUE);
    assertEquals(randomNumber, stringCalculator.add(String.valueOf(randomNumber)));
  }

  @Test
  public void addTwoNumbers() throws Exception {
    assertEquals(3, stringCalculator.add("1,2"));
  }

  @Test
  public void addCustomDelimiter() throws Exception {
    char[] delimiters = { ';', '{' };
    for (char delimiter : delimiters) {
      assertEquals(3, stringCalculator.add("//" + delimiter + "\n1" + delimiter + "2"));
    }
  }

  @Test
  public void addRandomCustomDelimiter() throws Exception {
    Generator<Character> generator = PrimitiveGenerators.characters();
    Character testCharacter = generator.next();
    while (Character.isDigit(testCharacter) || testCharacter.toString().equals("\n")) {
      testCharacter = generator.next();
    }
    assertEquals(3, stringCalculator.add("//" + testCharacter + "\n1" + testCharacter + "2"));
  }

  @Test
  public void addRandomCustomDelimiterQuickcheck() throws Exception {
    Generator<Character> delimiterGenerator = CombinedGenerators.excludeValues(
        PrimitiveGenerators.characters(), '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '\n');
    for (Character c : Iterables.toIterable(delimiterGenerator)) {
      assertEquals(3, stringCalculator.add("//" + c + "\n1" + c + "2"));
    }
  }

  @Test
  public void addMultipleNumbers() throws Exception {
    int sum = 0;
    int numberOfParameters = 2 + new Random().nextInt(100);
    List<Integer> numbers = new ArrayList<Integer>(numberOfParameters);
    for (int i = 0; i < numberOfParameters; i++) {
      sum += i;
      numbers.add(i);
    }
    String parameters = StringUtils.join(numbers, ",");
    assertEquals(sum, stringCalculator.add(parameters));
  }

  private int sum(Iterable<Integer> ints) {
    int sum = 0;
    for (Integer i : ints) {
      sum += i;
    }
    return sum;
  }

  @Test
  public void addMultipleNumbersQuickcheck() throws Exception {
    for (List<Integer> intlist : someLists(PrimitiveGenerators.integers(0, 100))) {
      String intsAsString = Joiner.on(",").join(intlist);
      assertEquals(sum(intlist), stringCalculator.add(intsAsString));
    }
  }

  @Test
  public void newLineBetweenNumbers() throws Exception {
    assertEquals(6, stringCalculator.add("1\n2,3"));
  }

  @Test
  public void addWithNegativeNumber() throws Exception {
    try {
      stringCalculator.add("-2,-1");
      fail("expected IllegalArgumentException not thrown");
    }
    catch (IllegalArgumentException e) {
      Assertions.assertThat(e.getMessage()).contains("negatives not allowed");
      Assertions.assertThat(e.getMessage()).contains("-2");
      Assertions.assertThat(e.getMessage()).contains("-1");
    }
  }

}
