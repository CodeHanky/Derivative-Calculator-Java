import java.util.HashMap;

public class MathHelper {
	
	public static final String[] FEASIBLE_OPERATIONS = {"^","*","/","+","-"};
	public static enum OPERAND_TYPE { CONSTANT, VARIABLE, EXPRESSION };
	
	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static String reduceExponent(String constantOperand) {
		if (isNumeric(constantOperand)) {
			int exp = Integer.parseInt(constantOperand);
			switch (exp) {
				case 1, 2:
					return "";
				default:
					return String.valueOf(exp-1);
			}
		}
		else {
			return constantOperand + "-1";
		}
	}

	public static String calcSquareOfDivisor(MathOperand rightOperand) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
