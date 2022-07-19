import java.util.ArrayList;
import java.util.Arrays;

public class MathExpression {
	private String cleanExpression;
	private MathOperationNode rootNode;
	private String variable;
	
	public MathExpression(String expressionStr, String variable) {
		
		this.variable = variable;
		
		this.cleanExpression = expressionStr.replace(" ", "");
		for(String operator: MathHelper.FEASIBLE_OPERATIONS) {
			if (cleanExpression.contains(String.valueOf(operator))) {
				ArrayList<Integer> operatorLocations = findLocationsOfOperator(cleanExpression, operator);
				
				for (int location: operatorLocations) {
					String expressionStrCopy = this.cleanExpression;
					
					int leftBracketStart = location - 1;
					int rightBracketEnd = location + 2;
					
					expressionStrCopy = determineLeftBracketPosition(expressionStrCopy, leftBracketStart, operatorLocations, operator);
					expressionStrCopy = determineRightBracketPosition(expressionStrCopy, rightBracketEnd, operatorLocations, operator);
					
					this.cleanExpression = expressionStrCopy;
				}
			}
		}
		System.out.println("Program will read expression as " + System.lineSeparator() + this.cleanExpression);
		
		this.rootNode = getOperations(0);
		this.rootNode.setOperationEnd(this.cleanExpression.length());
	}

	private MathOperationNode getOperations(int operationStart) {
		
		ArrayList<MathOperand> expressionOperands = new ArrayList<>();
		int i = operationStart;
		int operationEnd = -1;
		String operator = "";
		
		while (i<this.cleanExpression.length()) {
			if (this.cleanExpression.charAt(i)=='{') {
				MathOperationNode innerExpression = getOperations(i+1);
				i = innerExpression.getOperationEnd() + 1;
				expressionOperands.add(innerExpression); 
				continue;
			}
			else if (this.cleanExpression.charAt(i)=='}') {
				operationEnd = i;
				break;
			}
			else if (Arrays.asList(MathHelper.FEASIBLE_OPERATIONS).contains(String.valueOf(this.cleanExpression.charAt(i)))) {
				operator = String.valueOf(this.cleanExpression.charAt(i));
			}
			else {
				expressionOperands.add(determineTypeOfOperand(String.valueOf(this.cleanExpression.charAt(i))));
			}
			
			i++;
		}
		
		return new MathOperationNode(operator, expressionOperands, operationStart, operationEnd, MathHelper.OPERAND_TYPE.EXPRESSION);
				//(operator, expressionOperands, operationStart, operationEnd, MathHelper.OPERAND_TYPE.EXPRESSION);
	}

	private MathOperand determineTypeOfOperand(String operand) {
		
		if (operand.equals(this.variable)) {
			return new MathOperand(operand, MathHelper.OPERAND_TYPE.VARIABLE);
		}
		else {
			return new MathOperand(operand, MathHelper.OPERAND_TYPE.CONSTANT);
		}
	}

	private String determineLeftBracketPosition(String expressionStrCopy, int leftBracketStart, ArrayList<Integer> operatorLocations, String operator) {
		return determineBorderPosition(expressionStrCopy, leftBracketStart, '{', '}', operatorLocations, operator, -1);
	}
	
	private String determineRightBracketPosition(String expressionStrCopy, int rightBracketEnd, ArrayList<Integer> operatorLocations, String operator) {
		return determineBorderPosition(expressionStrCopy, rightBracketEnd	, '}', '{', operatorLocations, operator, 1);
	}

	private String determineBorderPosition(String expressionStr, int bracketFinalPosition, char bracket, char bracketMatch, 
			ArrayList<Integer> operatorLocations, String operator, int positionCheckStep) {
				
		while(true) {
			if (expressionStr.charAt(bracketFinalPosition)==bracketMatch) 
				bracketFinalPosition = findClosingBracket(bracketFinalPosition, expressionStr, bracketMatch);
			
			if (Arrays.asList(MathHelper.FEASIBLE_OPERATIONS).contains(String.valueOf(expressionStr.charAt(bracketFinalPosition)))) {
				int expressionBorder = determineExpressionBorderPosition(bracket, bracketFinalPosition, 0);
			    expressionStr = insertNewBracket(expressionStr,expressionBorder,bracket,operatorLocations,operator);
				break;
			}
			else if (checkExpressionEdgeBorderCondition(bracketFinalPosition, bracket, expressionStr)) {
				int expressionBorder = determineExpressionBorderPosition(bracket, bracketFinalPosition, 1);
			    expressionStr = insertNewBracket(expressionStr,expressionBorder,bracket,operatorLocations,operator);
				break;
			}
			
			bracketFinalPosition+=positionCheckStep;
		}
		
		return expressionStr;
	}
	
	private boolean checkExpressionEdgeBorderCondition(int bracketFinalPosition, char bracket, String expressionStr) {
		return bracket=='{' ? bracketFinalPosition==0 : bracketFinalPosition == expressionStr.length()-1;
	}

	private int determineExpressionBorderPosition(char bracket, int bracketFinalPosition, int isExpressionEdgeBorder) {
		return bracket=='{' ? bracketFinalPosition + (1-isExpressionEdgeBorder) : bracketFinalPosition + isExpressionEdgeBorder;
	}
	
	private String insertNewBracket(String expressionStr, int position, char bracket,
			ArrayList<Integer> operatorLocations, String operator) {
		
		expressionStr = insertBracket(expressionStr, position, bracket);
		fixOperationStartIndexes(operatorLocations, position, operator);
		
		return expressionStr;
	}

	private String insertBracket(String expressionStr, int position, char bracket) {
		StringBuffer strBuffer = new StringBuffer(expressionStr);
		strBuffer.insert(position, bracket);
		return strBuffer.toString();
	}

	private ArrayList<Integer> findLocationsOfOperator(String cleanExpression, String operator) {
		ArrayList<Integer> indexes = new ArrayList<>();
		
		int index = cleanExpression.indexOf(operator, 0);
		while (index!=-1) {
			indexes.add(index);
			index = cleanExpression.indexOf(operator, index+1);
		} 
		
		return indexes;
	}
	
	private int findClosingBracket(int currentPosition, String expressionStr, char bracket) {
		char match = 0;
		int rangeStart = -1;
		int rangeEnd = -1;
		int step = 0;
		
		switch(bracket) {
			case '{' : 
				match = '}';
				rangeStart = currentPosition + 1;
				rangeEnd = expressionStr.length();
				step = 1;
				break;
			case '}' :
				match = '{';
				rangeStart = currentPosition - 1;
				rangeEnd = -1;
				step = -1;
				break;
		}
		
		int i = rangeStart;
		while(i!=rangeEnd) {
			if (expressionStr.charAt(i)==match)
				return i>0 ? (i!=rangeEnd-1 ? i+step : i) : 0;
			else if (expressionStr.charAt(i)==bracket)
				i = findClosingBracket(i, expressionStr, bracket);
			else
				i+=step;
		}
		
		return 0;
	}
	
	private void fixOperationStartIndexes(ArrayList<Integer> operatorLocations, int position, String operator) {
		for (int i=0; i < operatorLocations.size(); i++) {
			int operatorLocation = operatorLocations.get(i);
			if (operatorLocation>=position)
				operatorLocations.set(i, operatorLocation+1);
		}		
	}

	public String printOrderOfOperations(MathOperationNode currentNode, String returnString) {
		boolean isFirstTerm = true;
		MathOperand leftOperand = currentNode.getLeftOperand();
		MathOperand rightOperand = currentNode.getRightOperand();
		
		for (MathOperand operand : Arrays.asList(leftOperand, rightOperand)) {
			if (operand==null) continue;
			
			if (isFirstTerm) {
				isFirstTerm = false;
			}
			else {
				returnString+=currentNode.getOperator();
			}
			
			if (operand.getType().equals(MathHelper.OPERAND_TYPE.EXPRESSION)) {
				String returnedString = printOrderOfOperations((MathOperationNode) operand, "");
				returnString+="("+returnedString+")";
			}
			else { 
				returnString+=operand.getOperand();
			}
		}
		
		System.out.println(returnString);
		return returnString;
	}

	public MathOperationNode getRootNode() {
		return this.rootNode;
	}

	public String calculateDerivative(MathOperationNode currentNode) {
		boolean expressionContainsConstant = false;
		boolean expressionContainsVariable = false;
		String operator = currentNode.getOperator();
		MathOperand leftOperand = currentNode.getLeftOperand();
		MathOperand rightOperand = currentNode.getRightOperand();
		
		switch (leftOperand.getType()) {
			case EXPRESSION:
				leftOperand.setDerivative(calculateDerivative((MathOperationNode) leftOperand));
				break;
			case VARIABLE:
				expressionContainsVariable = true;
				break;
			case CONSTANT:
				expressionContainsConstant = true;
				break;
		}
		
		if (rightOperand!=null) {
			switch (rightOperand.getType()) {
				case EXPRESSION:
					rightOperand.setDerivative(calculateDerivative((MathOperationNode) rightOperand));
					break;
				case VARIABLE:
					expressionContainsVariable = true;
					break;
				case CONSTANT:
					expressionContainsConstant = true;
					break;
			}
		}
		
		
		
		//return "";
		return Derivative.calculate(this.variable, operator, leftOperand, rightOperand, expressionContainsConstant, expressionContainsVariable);
		
	}
	
}
