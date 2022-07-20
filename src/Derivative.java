import java.util.ArrayList;

public class Derivative {
	//https://www.math.ucdavis.edu/~kouba/Math17BHWDIRECTORY/Derivatives.pdf

	public static String calculate(String variable, String operator, MathOperand leftOperand, MathOperand rightOperand,
			boolean expressionContainsConstant, boolean expressionContainsVariable) {
			
		String result="";
		
		switch (operator) {
			case "+", "-" : /* Formula: (a +- b)' = a' +- b'
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
			case "*": /* Formula: (a*b)' = a'*b + a*b'
				* Cases:
				* a) 2*1
				* b) 3*x , x*3
				* c) 3*f(x) , f(x)*3
				* d) x*x
				* e) x*f(x)
				* f) f(x)*f(x)
				*/
				
				result = dxOf(leftOperand) + "*" + rightOperand.getOperand()
					+ "+" + leftOperand.getOperand() + "*" + dxOf(rightOperand);
				break;
			case "/" : /* Formula: (a/b)' = (a'*b - a*b')/(b^2)
				* Cases:
				* a) 2/1
				* b) 3/x 
				* c) x/3
				* d) 3/f(x) , f(x)/3
				* d) x*x
				* e) x*f(x)
				* f) f(x)/f(x+2)
				*/
				
				result = "( " 
						+ "(" + dxOf(leftOperand) +  "*" + rightOperand.getOperand() + ") "
						+ "-" + "(" + leftOperand.getOperand() + "*" + dxOf(rightOperand) + ")"
						+ " )"
						+ "/" + "(" + MathHelper.calcSquareOfDivisor(rightOperand) + ")";
			case "^" : /* Formulas: 
			1) f(x)^g(x) (General formula, all other formulas derive from this one)
				f = e^ln(f)
				Let y = f^g = (e^ln(f))^g = e^(g*ln(f))
				From 2b: y' = e^(g*ln(f)) * lne * (g*ln(f))'
							= e^(g*ln(f)) * (g'*ln(f) + g*f'*1/f))
							= f^g * ((g*f')/f + g'*ln(f))	'
			2) x^n, n constant = n*x^(n-1)
				Cases: 
				a) x^2
				b) f(x)^2
			3) a^x, a constant = a^x * ln(a)
				Cases: 
				a) a^x
				b) a^f(x) = a^f(x) * ln(a) * f'(x)
			4) x^x, x variable
				Cases:
				a) x^f(x), From 1: (x^f(x))' = x^f(x) * ((f*x')/x + f'*ln(x))
									  = x^f(x) * (f/x + f'*ln(x))
				b) x^x  = x^x * (1+ln(x)) (From 4a for f(x)=x)
			*/
				
				result = "( " +
						"(" + leftOperand.getOperand() + "^" + rightOperand.getOperand() + ")"
						+ "*" + "("
						+ "(" + dxOf(leftOperand) + "*" + rightOperand.getOperand() + ")" + "/" + leftOperand.getOperand()
						+ " + " + dxOf(rightOperand) + "*" + "ln(" + leftOperand.getOperand() + ")" 
						+ ")" + " )";
		}
		
		return result;
	}
	
//	private static MathOperand getOperandThatIs(MathHelper.OPERAND_TYPE wantedType, MathOperand leftOperand, MathOperand rightOperand) {
//		return leftOperand.getType() == wantedType ? leftOperand : rightOperand;
//	}

	private static String dxOf(MathOperand operand) {
		switch (operand.getType()) {
			case VARIABLE :
				return "1";			
		}
		return null;
	}

}
