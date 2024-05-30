package engcalc;

import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PrimaryController {
  @FXML
  Text currentText;

  @FXML
  Text lastText;

  @FXML
  ScrollPane currentScroll;

  @FXML
  VBox history;

  String current = "";
  String operator = "";
  String last = "";
  Boolean isNew;

  HashSet<String> solveTwoOperators = new HashSet<>() {
    {
      add("+");
      add("-");
      add("x");
      add("÷");
      add("^");
      add("mod");
    }
  };

  HashSet<String> endOperators = new HashSet<String>() {
    {
      add("mod");
      add("+");
      add("-");
      add("x");
      add("÷");
      add("^");
      add("!");
      add("²");
    }
  };
  HashSet<String> startOperators = new HashSet<String>() {
    {
      add("√");
      add("e^");
    }
  };
  HashSet<String> bracketsOperators = new HashSet<String>() {
    {
      add("ln");
      add("log");
      add("sin");
      add("cos");
      add("tan");
      add("ctn");
      add("sec");
      add("abs");
      add("neg");
    }
  };

  public void updateLast(String operator) {
    if (current.isEmpty()) {
      if (endOperators.contains(operator)) {
        lastText.setText(last + operator);
      } else if (startOperators.contains(operator)) {
        lastText.setText(operator + last);
      } else if (bracketsOperators.contains(operator)) {
        lastText.setText(operator + "(" + last + ")");
      }
    } else {
      if (solveTwoOperators.contains(operator)) {
        lastText.setText(last + operator + current + "=");
      } else if (endOperators.contains(operator)) {
        lastText.setText(last + operator);
      } else if (startOperators.contains(operator)) {
        lastText.setText(operator + last + "=");
      } else if (bracketsOperators.contains(operator)) {
        lastText.setText(operator + "(" + last + ")=");
      }
    }
  }

  public void updateLast() {
    updateLast(operator);
  }

  public void scrollToEnd() {
    currentScroll.setHvalue(1.0);
  }

  public void scrollToStart() {
    currentScroll.setHvalue(0.0);
  }

  private Double solveLastAndCurrent() {
    double currentDouble = Double.parseDouble(currentText.getText());
    double lastDouble = Double.parseDouble(last);
    double answer;
    switch (operator) {
      case "+":
        answer = Solving.add(lastDouble, currentDouble);
        break;
      case "-":
        answer = Solving.subtract(lastDouble, currentDouble);
        break;
      case "x":
        answer = Solving.multiply(lastDouble, currentDouble);
        break;
      case "÷":
        answer = Solving.divide(lastDouble, currentDouble);
        break;
      case "mod":
        answer = Solving.mod(lastDouble, currentDouble);
        break;
      case "^":
        answer = Solving.power(lastDouble, currentDouble);
        break;
      default:
        answer = 0.0;
    }
    return answer;
  }

  private Double solveCurrent(String operator) {
    double currentDouble = Double.parseDouble(currentText.getText());
    switch (operator) {
      case "√":
        return Solving.sqrt(currentDouble);
      case "!":
        return Solving.factorial(currentDouble);
      case "²":
        return Solving.power(currentDouble, 2.0);
      case "ln":
        return Solving.log(currentDouble);
      case "log":
        return Solving.log10(currentDouble);
      case "sin":
        return Solving.sin(currentDouble);
      case "cos":
        return Solving.cos(currentDouble);
      case "tan":
        return Solving.tan(currentDouble);
      case "ctn":
        return Solving.ctn(currentDouble);
      case "sec":
        return Solving.sec(currentDouble);
      case "abs":
        return Solving.abs(currentDouble);
      case "neg":
        return Solving.neg(currentDouble);
      case "e^":
        return Solving.exponentialFunction(currentDouble);
      default:
        return Double.NEGATIVE_INFINITY;
    }
  }

  private void addNumber(String number) {
    try {
      Integer.parseInt(number);
      currentText.setText(currentText.getText() + number);
      isNew = false;
    } catch (Exception __) {
    }
    scrollToEnd();
  }

  private void addOperator(String operator) {
    if (currentText.getText().isEmpty()) {
      if (operator.equals("-")) {
        currentText.setText(operator);
      } else if (!lastText.getText().isEmpty()) {
        this.operator = operator;
        updateLast();
      }
    } else if (!currentText.getText().isEmpty()) {
      if (currentText.getText() == "-") {
        return;
      } else if (!solveTwoOperators.contains(operator)) {
        if (lastText.getText().isEmpty() || isNew) {
          last = currentText.getText();
          current = solveCurrent(operator).toString();
          currentText.setText(current);
          isNew = true;
          updateLast(operator);
          addToHistory(lastText.getText() + currentText.getText());
        } else {
          currentText.setText(solveCurrent(operator).toString());
        }
      } else {
        if (solveTwoOperators.contains(this.operator)) {
          last = solveLastAndCurrent().toString();
          addToHistory(lastText.getText() + currentText.getText() + "=" + last);
        } else {
          last = currentText.getText();
          isNew = true;
        }
        this.operator = operator;
        currentText.setText("");
        current = "";
        updateLast();
      }
    }
  }

  public void keyHandler(KeyEvent e) {
    switch (e.getCode()) {
      case NUMPAD0:
      case DIGIT0:
        addNumber("0");
        break;
      case NUMPAD1:
      case DIGIT1:
        addNumber("1");
        break;
      case NUMPAD2:
      case DIGIT2:
        addNumber("2");
        break;
      case NUMPAD3:
      case DIGIT3:
        addNumber("3");
        break;
      case NUMPAD4:
      case DIGIT4:
        addNumber("4");
        break;
      case NUMPAD5:
      case DIGIT5:
        addNumber("5");
        break;
      case NUMPAD6:
      case DIGIT6:
        addNumber("6");
        break;
      case NUMPAD7:
      case DIGIT7:
        addNumber("7");
        break;
      case NUMPAD8:
      case DIGIT8:
        addNumber("8");
        break;
      case NUMPAD9:
      case DIGIT9:
        addNumber("9");
        break;
      case P:
        PIHandler();
        break;
      case E:
        EHandler();
        break;
      case PERIOD:
        dotHandler();
        break;
      case PLUS:
      case EQUALS:
      case ADD:
        addOperator("+");
        break;
      case MINUS:
        addOperator("-");
        break;
      case MULTIPLY:
        addOperator("x");
        break;
      case DIVIDE:
        addOperator("÷");
        break;
      case ENTER:
        solveHandler();
        break;
      case BACK_SPACE:
        if (e.isShiftDown()) {
          ACHandler();
        } else {
          delHandler();
        }
        break;
    }
  }

  public void numberHandler(ActionEvent e) {
    addNumber(((Button) e.getSource()).getText());
  }

  public void operatorHandler(ActionEvent e) {
    addOperator(((Button) e.getSource()).getText());
  }

  public void dotHandler() {
    if (!currentText.getText().contains(".")) {
      currentText.setText(currentText.getText() + ".");
    }
  }

  public void PIHandler() {
    if (currentText.getText().isEmpty())
      currentText.setText(currentText.getText() + Math.PI);
  }

  public void EHandler() {
    if (currentText.getText().isEmpty())
      currentText.setText(currentText.getText() + Math.E);
  }

  public void delHandler() {
    if (!currentText.getText().isEmpty()) {
      if (isNew) {
        currentText.setText("");
        lastText.setText("");
        this.operator = "";
      } else
        currentText.setText(currentText.getText().substring(0, currentText.getText().length() - 1));
    } else if (!lastText.getText().isEmpty()) {
      if (!lastText.getText().isEmpty()) {
        this.operator = "";
        last = "";
        lastText.setText("");
      }
    } else {
      history.getChildren().clear();
    }
  }

  public void ACHandler() {

  }

  public void solveHandler() {
    if (solveTwoOperators.contains(this.operator)) {
      current = currentText.getText();
      currentText.setText(solveLastAndCurrent().toString());
      updateLast();
      addToHistory(lastText.getText() + currentText.getText());
      isNew = true;
      this.operator = "";
      scrollToStart();
    }
  }

  public void addToHistory(String s) {
    HBox box = new HBox();
    String text[] = s.split("=");
    Label label = new Label(text[0] + "=");
    Button btn1 = new Button(text[1]);
    btn1.setOnAction(e -> {
      currentText.setText(btn1.getText());
    });
    btn1.setStyle("-fx-background-color: #333333; -fx-text-fill: #FFFFFF; -fx-padding: 1px;");
    box.getChildren().add(label);
    box.getChildren().add(btn1);
    label.textFillProperty().setValue(Color.ALICEBLUE);
    history.getChildren().add(0, box);
  }

  public void setCurrentText(ActionEvent e) {
    currentText.setText(((Button) e.getSource()).getText());
  }

}
