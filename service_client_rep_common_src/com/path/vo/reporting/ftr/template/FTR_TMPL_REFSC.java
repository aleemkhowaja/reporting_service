package com.path.vo.reporting.ftr.template;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class FTR_TMPL_REFSC extends GridParamsSC {

        private BigDecimal COMP_CODE;
        private BigDecimal CODE;
        private BigDecimal LINE_NBR;
        private String LANG_CODE;
        public BigDecimal getCOMP_CODE()
        {
            return COMP_CODE;
        }
        public void setCOMP_CODE(BigDecimal cOMPCODE)
        {
            COMP_CODE = cOMPCODE;
        }
        public BigDecimal getCODE()
        {
            return CODE;
        }
        public void setCODE(BigDecimal cODE)
        {
            CODE = cODE;
        }
        public BigDecimal getLINE_NBR()
        {
            return LINE_NBR;
        }
        public void setLINE_NBR(BigDecimal lINENBR)
        {
            LINE_NBR = lINENBR;
        }
        public String getLANG_CODE()
        {
            return LANG_CODE;
        }
        public void setLANG_CODE(String lANGCODE)
        {
            LANG_CODE = lANGCODE;
        }

}
