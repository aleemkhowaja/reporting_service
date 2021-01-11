package com.path.vo.alert.notification;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlType;

/**
 * @AutoGenerated Code
 * @description class Account
 */

@XmlType(propOrder={})
public class Account 
{
	private BigDecimal branch;
	private BigDecimal currency;
	private BigDecimal accGl;
	private BigDecimal serialNo;
	private BigDecimal cifNo;
	private String additionalRef;
	private String ibanAccNo;

	public void setBranch(BigDecimal branch)
	{
	   this.branch = branch;
	}
	public BigDecimal getBranch()
	{
	   return branch;
	}
	public void setCurrency(BigDecimal currency)
	{
	   this.currency = currency;
	}
	public BigDecimal getCurrency()
	{
	   return currency;
	}
	public void setAccGl(BigDecimal accGl)
	{
	   this.accGl = accGl;
	}
	public BigDecimal getAccGl()
	{
	   return accGl;
	}
	public void setSerialNo(BigDecimal serialNo)
	{
	   this.serialNo = serialNo;
	}
	public BigDecimal getSerialNo()
	{
	   return serialNo;
	}
	public void setCifNo(BigDecimal cifNo)
	{
	   this.cifNo = cifNo;
	}
	public BigDecimal getCifNo()
	{
	   return cifNo;
	}
	public void setAdditionalRef(String additionalRef)
	{
	   this.additionalRef = additionalRef;
	}
	public String getAdditionalRef()
	{
	   return additionalRef;
	}
	public void setIbanAccNo(String ibanAccNo)
	{
	   this.ibanAccNo = ibanAccNo;
	}
	public String getIbanAccNo()
	{
	   return ibanAccNo;
	}
}
