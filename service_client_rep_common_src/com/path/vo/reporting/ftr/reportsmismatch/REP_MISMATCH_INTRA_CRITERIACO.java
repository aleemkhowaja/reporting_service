package com.path.vo.reporting.ftr.reportsmismatch;

import java.io.Serializable;
import com.path.dbmaps.vo.REP_MISMATCH_INTRA_CRITERIAVO;
import com.path.lib.vo.BaseVO;

public class REP_MISMATCH_INTRA_CRITERIACO extends BaseVO implements Serializable
{
    private REP_MISMATCH_INTRA_CRITERIAVO repMismatchIntraCriteriaVO = new REP_MISMATCH_INTRA_CRITERIAVO();

    public REP_MISMATCH_INTRA_CRITERIAVO getRepMismatchIntraCriteriaVO()
    {
	return repMismatchIntraCriteriaVO;
    }

    public void setRepMismatchIntraCriteriaVO(REP_MISMATCH_INTRA_CRITERIAVO repMismatchIntraCriteriaVO)
    {
	this.repMismatchIntraCriteriaVO = repMismatchIntraCriteriaVO;
    }

}
