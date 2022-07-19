import java.util.ArrayList;

public class MathOperationNode extends MathOperand {

	private String operator;
	private MathOperand leftOperand;
	private MathOperand rightOperand;
	private int operationStart, operationEnd;	

	public MathOperationNode(String operator, MathOperand leftOperand, MathOperand rightOperand, int operationStart, int operationEnd, MathHelper.OPERAND_TYPE type) {
		super(type);
		this.operator = operator;
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
		this.operationStart = operationStart;
		this.operationEnd = operationEnd;
	}

	public String getOperator() {
		return operator;
	}

	public ArrayList<MathOperand> getOperands() {
		return ;
	}

	public int getOperationEnd() {
		return operationEnd;
	}

	public void setOperationEnd(int operationEnd) {
		this.operationEnd = operationEnd;
	}

	public void setOperands(ArrayList<MathOperand> operands) {
		this.operands = operands;
	}
	
	public void 
}
