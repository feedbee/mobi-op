package com.feedbee.android.mobiop;

public class WidgetConfig implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	boolean showTitle;
	String customTitle;
	Integer customNetworkCode;
	String customUssd;
	OPERATOR operator;
	
	public enum OPERATOR {SIM, NETWORK};
	
	public WidgetConfig(boolean showTitle,
		String customTitle, Integer customNetworkCode,
		String customUssd, OPERATOR operator)
	{
		super();
		this.showTitle = showTitle;
		this.customTitle = customTitle;
		this.customNetworkCode = customNetworkCode;
		this.customUssd = customUssd;
		this.operator = operator;
	}

	public boolean isShowTitle() {
		return showTitle;
	}

	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}

	public String getCustomTitle() {
		return customTitle;
	}

	public void setCustomTitle(String customTitle) {
		this.customTitle = customTitle;
	}

	public Integer getCustomNetworkCode() {
		return customNetworkCode;
	}

	public void setCustomNetworkCode(Integer customNetworkCode) {
		this.customNetworkCode = customNetworkCode;
	}

	public String getCustomUssd() {
		return customUssd;
	}

	public void setCustomUssd(String customUssd) {
		this.customUssd = customUssd;
	}

	public OPERATOR getOperator() {
		return operator;
	}

	public void setOperator(OPERATOR operator) {
		this.operator = operator;
	}
}

