package com.path.vo.reporting.scheduler;

import java.io.Serializable;

public class Months implements Serializable {
	private boolean JAN;
	private boolean FEB;
	private boolean MAR;
	private boolean APR;
	private boolean MAY;
	private boolean JUN;
	private boolean JUL;
	private boolean AUG;
	private boolean SEP;
	private boolean OCT;
	private boolean NOV;
	private boolean DEC;
	
	public boolean isJAN() {
		return JAN;
	}
	public void setJAN(boolean jAN) {
		JAN = jAN;
	}
	public boolean isFEB() {
		return FEB;
	}
	public void setFEB(boolean fEB) {
		FEB = fEB;
	}
	public boolean isMAR() {
		return MAR;
	}
	public void setMAR(boolean mAR) {
		MAR = mAR;
	}
	public boolean isAPR() {
		return APR;
	}
	public void setAPR(boolean aPR) {
		APR = aPR;
	}
	public boolean isMAY() {
		return MAY;
	}
	public void setMAY(boolean mAY) {
		MAY = mAY;
	}
	public boolean isJUN() {
		return JUN;
	}
	public void setJUN(boolean jUN) {
		JUN = jUN;
	}
	public boolean isJUL() {
		return JUL;
	}
	public void setJUL(boolean jUL) {
		JUL = jUL;
	}
	public boolean isAUG() {
		return AUG;
	}
	public void setAUG(boolean aUG) {
		AUG = aUG;
	}
	public boolean isSEP() {
		return SEP;
	}
	public void setSEP(boolean sEP) {
		SEP = sEP;
	}
	public boolean isOCT() {
		return OCT;
	}
	public void setOCT(boolean oCT) {
		OCT = oCT;
	}
	public boolean isNOV() {
		return NOV;
	}
	public void setNOV(boolean nOV) {
		NOV = nOV;
	}
	public boolean isDEC() {
		return DEC;
	}
	public void setDEC(boolean dEC) {
		DEC = dEC;
	}
	public Months()
	{
		JAN=false;
		FEB=false;
		MAR=false;
		APR=false;
		MAY=false;
		JUN=false;
		JUL=false;
		AUG=false;
		SEP=false;
		OCT=false;
		NOV=false;
		DEC=false;
	}
}
