
public class MathOperand {
	private String operand;
	private MathHelper.OPERAND_TYPE type;
	private String derivative;
	
	public MathOperand(MathHelper.OPERAND_TYPE type) {
		this.type = type;
	}
	
	public MathOperand(String operand, MathHelper.OPERAND_TYPE type) {
		this.operand = operand;
		this.type = type;
	}

	public MathHelper.OPERAND_TYPE getType() {
		return type;
	}

	public String getOperand() {
		return operand;
	}

	public void setDerivative(String derivative) {
		this.derivative = derivative;
	}
}
