import java.util.ArrayList;
import java.util.Arrays;

public class MathExpression {
	private String cleanExpression;
	
	public MathExpression(String expressionStr, String variable) {
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
					
					/*
					 * int expressionStart = -1; 
					 * int expressionEnd = -1;
					 * 
					 * 
					
					while(true) {
						if (expressionStrCopy.charAt(leftBracketStart)=='}') 
							leftBracketStart = findClosingBracket(leftBracketStart, expressionStrCopy, '}');
					
						if (Arrays.asList(MathHelper.FEASIBLE_OPERATIONS).contains(String.valueOf(expressionStrCopy.charAt(leftBracketStart)))) {
							expressionStart = leftBracketStart + 1;
							expressionStrCopy = insertBracket(expressionStrCopy,expressionStart, '{');
							fixOperationStartIndexes(operatorLocations, expressionStart, operator);
							break;
						}
						else if (leftBracketStart==0) {
							expressionStart = leftBracketStart;
							expressionStrCopy = insertBracket(expressionStrCopy,expressionStart, '{');
							fixOperationStartIndexes(operatorLocations, expressionStart, operator);
							break;
						}
						
						if (leftBracketStart>=0) 
							leftBracketStart--; 
						else 
							leftBracketStart=0;
					}
					
					while(true) {
						if (expressionStrCopy.charAt(rightBracketEnd)=='{') 
							rightBracketEnd = findClosingBracket(rightBracketEnd, expressionStrCopy, '{');
					
						if (rightBracketEnd<expressionStrCopy.length()) {
							if (Arrays.asList(MathHelper.FEASIBLE_OPERATIONS).contains(String.valueOf(expressionStrCopy.charAt(rightBracketEnd)))) {
								expressionEnd = rightBracketEnd;
								expressionStrCopy = insertBracket(expressionStrCopy,expressionEnd, '}');
								fixOperationStartIndexes(operatorLocations, expressionEnd, operator);
								break;
							}
							else if (rightBracketEnd==expressionStrCopy.length()-1) {
								expressionEnd = rightBracketEnd + 1;
								expressionStrCopy = insertBracket(expressionStrCopy,expressionEnd, '}');
								fixOperationStartIndexes(operatorLocations, expressionEnd, operator);
								break;
							}
						}
						else {
							expressionStrCopy = insertBracket(expressionStrCopy,expressionStrCopy.length(),'}');
							expressionEnd=expressionStrCopy.length()-1;
							break;
						}
						
						if (rightBracketEnd<expressionStrCopy.length()) 
							rightBracketEnd++; 
						else 
							rightBracketEnd=expressionStrCopy.length()-1;
					}
					*/
					
					this.cleanExpression = expressionStrCopy;
				}
			}
		}
		System.out.println("Program will read expression as " + System.lineSeparator() + this.cleanExpression);
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
				return i>0 ? i+step : 0;
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

	public void printOrderOfOperations() {
		boolean isFirstTerm = true;

	}
	
}
