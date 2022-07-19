
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
}
