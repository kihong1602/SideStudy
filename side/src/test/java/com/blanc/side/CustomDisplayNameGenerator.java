package com.blanc.side;

import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayNameGenerator;

public class CustomDisplayNameGenerator extends DisplayNameGenerator.Standard {

  @Override
  public String generateDisplayNameForClass(Class<?> testClass) {
    return testClass.getName().replace("$", "Class :: ");
  }

  @Override
  public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
    return nestedClass.getSimpleName().replace("$", "Param :: ").replaceAll("_", ", ");
  }

  @Override
  public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
    return testMethod.getName().replace("$", "Value :: ").replaceAll("_", ", ");
  }
}
