import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String expressionStr="";
		String variable="";
		MathExpression expression;
		
		/*
		 * do { System.out.println("Type the letter of the variable: "); variable =
		 * scanner.nextLine(); } while (variable.length()!=1 ||
		 * MathHelper.isNumeric(variable));
		 * 
		 * do { System.out.println("Type an expression to find the derivative of: ");
		 * expressionStr = scanner.nextLine(); } while (expressionStr.length()<1 ||
		 * !expressionStr.contains(variable));
		 * 
		 * scanner.close();
		 * 
		 * expression = new MathExpression(expressionStr,variable);
		 */
		
		//expression = new MathExpression("2 ^ x ", "x");
		expression = new MathExpression("3*x^2 + 2*x/4 ", "x");
		expression = new MathExpression("2+2*x/4 ", "x");
	}
}
