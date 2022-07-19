import java.util.ArrayList;

public class Derivative {

	public static String calculate(String variable, ArrayList<MathOperand> operands, String operator,
			boolean expressionContainsConstant, boolean expressionContainsVariable) {
			
		String result;
		switch (operator) {
			case "+", "-" :
				if (expressionContainsVariable) {
					result = dxOf(getOperandThatIs(MathHelper.OPERAND_TYPE.VARIABLE, operands));
				}
				else 
					result = "0";
				break;
			case "*", "/" : 
				if (expressionContainsConstant || expressionContainsVariable) {
					result = String.valueOf(getOperandThatIs((MathHelper.OPERAND_TYPE.CONSTANT),operands))
							+ operator 
							+ String.valueOf(dxOf(getOperandThatIs(MathHelper.OPERAND_TYPE.VARIABLE, operands)));
				}
				else if (expressionContainsVariable) {
					result = "2" + operator +
							String.valueOf(dxOf(getOperandThatIs(MathHelper.OPERAND_TYPE.VARIABLE, operands)));
				}
				break;
			case "^" :
				if ()
		}
		
		return result;
	}
	
	private static MathOperand getOperandThatIs(MathHelper.OPERAND_TYPE wantedType, ArrayList<MathOperand> operands) {
		for (MathOperand operand: operands) {
			if (operand.getType().equals(wantedType))
				return operand;
		}
		return null;
	}

	private static String dxOf(MathOperand operand) {
		switch (operand.getType()) {
			case VARIABLE :
				return "1";
				break;
			
		}
	}

}
