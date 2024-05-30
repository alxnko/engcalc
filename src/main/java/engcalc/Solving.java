package engcalc;

public class Solving {
  public static double add(double a, double b) {
    return a + b;
  }

  public static double subtract(double a, double b) {
    return a - b;
  }

  public static double multiply(double a, double b) {
    return a * b;
  }

  public static double divide(double a, double b) {
    return a / b;
  }

  public static double sqrt(double a) {
    return Math.sqrt(a);
  }

  public static double exponentialFunction(double a) {
    return Math.exp(a);
  }

  public static double factorial(double a) {
    double result = 1;
    for (int i = 1; i <= a; i++) {
      result *= i;
    }
    return result;
  }

  public static double log(double a) {
    return Math.log(a);
  }

  public static double log10(double a) {
    return Math.log10(a);
  }

  public static double cos(double a) {
    return Math.cos(a);
  }

  public static double sin(double a) {
    return Math.sin(a);
  }

  public static double tan(double a) {
    return Math.tan(a);
  }

  public static double ctn(double a) {
    return 1 / Math.tan(a);
  }

  public static double sec(double a) {
    return 1 / Math.cos(a);
  }

  public static double abs(double a) {
    return Math.abs(a);
  }

  public static double neg(double a) {
    return -a;
  }

  public static double power(double a, double b) {
    return Math.pow(a, b);
  }

  public static double mod(double a, double b) {
    return a % b;
  }
}
