import java.util.ArrayList;

public class Derivative {

	public static String calculate(String variable, String operator, MathOperand leftOperand, MathOperand rightOperand,
			boolean expressionContainsConstant, boolean expressionContainsVariable) {
			
		String result;
		
		switch (operator) {
			case "+", "-" : /*
			* Cases:
			* a) 1+2
			* b) 1+x
			* c) 2+f(x)
			* d) x+x
			* e) x + f(x)
			* f) f(x) + f(x)
			*/
				result = dxOf(leftOperand) + operator + dxOf(rightOperand);
				break;
			case "*": 
				/*
				* Cases:
				* a) 2*1
				* b) 3*x , x*3
				* c) 3*f(x) , f(x)*3
				* d) x*x
				* e) x*f(x)
				* f) f(x)*f(x)
				*/
				if (expressionContainsConstant && expressionContainsVariable) {
					result = String.valueOf(getOperandThatIs((MathHelper.OPERAND_TYPE.CONSTANT), leftOperand, rightOperand))
							+ operator 
							+ String.valueOf(dxOf(getOperandThatIs(MathHelper.OPERAND_TYPE.VARIABLE, leftOperand, rightOperand)));
				}
				else if (expressionContainsVariable) {
					result = String.valueOf(dxOf(getOperandThatIs(MathHelper.OPERAND_TYPE.VARIABLE, leftOperand, rightOperand)));
				}
				else { // if (expressionContainsConstant)
					result = String.valueOf(getOperandThatIs((MathHelper.OPERAND_TYPE.CONSTANT), leftOperand, rightOperand));
				}
				break;
			case "^" :
				if (rightOperand.isConstant()) { 
					if (leftOperand.isVariable()) //case x^2
						result = rightOperand.getOperand() + "*" + variable + "^" + MathHelper.reduceExponent(rightOperand.getOperand());
					else if (leftOperand.isExpression()) //case: (x+2)^2
						result = rightOperand.getOperand() + "*" + "(" + dxOf(leftOperand) + ")" + "^" + MathHelper.reduceExponent(rightOperand.getOperand());
					else //case: 3^2
						result = "0";
				}
				//other cases: 3^x, x^x, 5^f(x), y^f(y) 
		}
		
		return result;
	}
	
	private static MathOperand getOperandThatIs(MathHelper.OPERAND_TYPE wantedType, MathOperand leftOperand, MathOperand rightOperand) {
		return leftOperand.getType() == wantedType ? leftOperand : rightOperand;
	}

	private static String dxOf(MathOperand operand) {
		switch (operand.getType()) {
			case VARIABLE :
				return "1";			
		}
		return null;
	}

}
