import java.util.ArrayList;

public class MathOperationNode {

	private String operator;
	private ArrayList<String> operands = new ArrayList<>();
	
//Comment 5
	public MathOperationNode(String operator, ArrayList<String> operands) {
		super();
		this.operator = operator;
		this.operands = operands;
	}

	public String getOperator() {
		return operator;
	}

	public ArrayList<String> getOperands() {
		return operands;
	}
}
