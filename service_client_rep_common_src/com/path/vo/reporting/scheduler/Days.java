package com.path.vo.reporting.scheduler;

import java.io.Serializable;

public class Days implements Serializable 
{
	private boolean MON;
	private boolean TUE;
	private boolean WED;
	private boolean THU;
	private boolean FRI;
	private boolean SAT;
	private boolean SUN;
	public Days()
	{
		MON = false;
		TUE = false;
		WED = false;
		THU = false;
		FRI = false;
		SAT = false;
		SUN = false;
	}
	public boolean isMON() {
		return MON;
	}
	public void setMON(boolean mON) {
		MON = mON;
	}
	public boolean isTUE() {
		return TUE;
	}
	public void setTUE(boolean tUE) {
		TUE = tUE;
	}
	public boolean isWED() {
		return WED;
	}
	public void setWED(boolean wED) {
		WED = wED;
	}
	public boolean isTHU() {
		return THU;
	}
	public void setTHU(boolean tHU) {
		THU = tHU;
	}
	public boolean isFRI() {
		return FRI;
	}
	public void setFRI(boolean fRI) {
		FRI = fRI;
	}
	public boolean isSAT() {
		return SAT;
	}
	public void setSAT(boolean sAT) {
		SAT = sAT;
	}
	public boolean isSUN() {
		return SUN;
	}
	public void setSUN(boolean sUN) {
		SUN = sUN;
	}
}