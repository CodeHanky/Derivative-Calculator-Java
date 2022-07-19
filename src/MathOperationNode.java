import java.util.ArrayList;

public class MathOperationNode extends MathOperand {

	private String operator;
	private MathOperand leftOperand;
	private MathOperand rightOperand;
	private int operationStart, operationEnd;	

	public MathOperationNode(String operator, ArrayList<MathOperand> operands, int operationStart, int operationEnd, MathHelper.OPERAND_TYPE type) {
		super(type);
		this.operator = operator;
		this.leftOperand = operands.get(0);
		this.rightOperand = (operands.size()==2) ? operands.get(1) : null;
		this.operationStart = operationStart;
		this.operationEnd = operationEnd;
	}

	public String getOperator() {
		return operator;
	}

	public MathOperand getLeftOperand() {
		return leftOperand;
	}
	
	public MathOperand getRightOperand() {
		return rightOperand;
	}

	public int getOperationEnd() {
		return operationEnd;
	}

	public void setOperationEnd(int operationEnd) {
		this.operationEnd = operationEnd;
	}

	/*public void setOperands(ArrayList<MathOperand> operands) {
		this.operands = operands;
	}*/
	
	//public void 
}
