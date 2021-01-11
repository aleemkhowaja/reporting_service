package com.path.dao.reporting.ftr.csvitems.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.path.dao.reporting.ftr.csvitems.CsvItemsDAO;
import com.path.dbmaps.vo.csvitems.CsvItemsCO;
import com.path.dbmaps.vo.csvitems.CsvItemsSC;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.lib.common.util.DateUtil;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * CsvItemsDAOImpl.java used to
 */
public class CsvItemsDAOImpl extends BaseDAO implements CsvItemsDAO
{

    public List<CsvItemsCO> retCsvItemsList(CsvItemsSC csvItemsSC) throws DAOException
    {

	DAOHelper.fixGridMaps(csvItemsSC, getSqlMap(), "csvItemsMapper.retCsvItemsListMap");
	return getSqlMap().queryForList("csvItemsMapper.retCsvItemsList", csvItemsSC,csvItemsSC.getRecToskip(), csvItemsSC.getNbRec());

    }

    public int retCsvItemsCount(CsvItemsSC csvItemsSC) throws DAOException
    {
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("csvItemsMapper.retCsvItemsListCount", csvItemsSC)).intValue();
	return nb;
    }

    public CsvItemsCO applyDependencyByRepRef(CsvItemsSC csvItemsSC) throws DAOException 
    {
	return (CsvItemsCO) (getSqlMap().queryForObject("csvItemsMapper.applyDependencyByRepRef", csvItemsSC));
    }
    
    public List<CsvItemsCO> retCsvItemsByRepList(CsvItemsSC csvItemsSC) throws DAOException
    {

	DAOHelper.fixGridMaps(csvItemsSC, getSqlMap(), "csvItemsMapper.retCsvItemsByRepListMap");
	return getSqlMap().queryForList("csvItemsMapper.retCsvItemsByRepList", csvItemsSC,csvItemsSC.getRecToskip(), csvItemsSC.getNbRec());

    }

    public int retCsvItemsByRepCount(CsvItemsSC csvItemsSC) throws DAOException
    {
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("csvItemsMapper.retCsvItemsByRepCount", csvItemsSC)).intValue();
	return nb;
    }
    
    public CsvItemsCO retrieveRepName(CsvItemsSC csvItemsSC) throws DAOException
    {

	DAOHelper.fixGridMaps(csvItemsSC, getSqlMap(), "csvItemsMapper.retrieveRepNameMap");
	return (CsvItemsCO) getSqlMap().queryForObject("csvItemsMapper.retrieveRepName", csvItemsSC);

    }
  
    public void deleteAllCsvItemByRep(String repRef,BigDecimal compCode)throws DAOException
    {
	    List<CsvItemsCO> lstCsvItems = new ArrayList<CsvItemsCO>();
	    CsvItemsCO csvItemsCO = new CsvItemsCO();
	    csvItemsCO.getCbkRptLineVO().setCOMP_CODE(compCode);
	    csvItemsCO.getCbkRptLineVO().setREP_REF(repRef);
	    lstCsvItems.add(csvItemsCO);
	    getSqlMap().delete("csvItemsMapper.deleteCsvItems", lstCsvItems.get(0));
    }
    public Integer saveCsvItems(List<CsvItemsCO> lstCsvItems,String repRef,BigDecimal compCode,Date dateUpdated)throws DAOException
    {
	
	CsvItemsSC csvItemsSC = new CsvItemsSC();
	csvItemsSC.setREP_REF(repRef);
	csvItemsSC.setCompCode(compCode);
	ArrayList<CsvItemsCO> myLst = (ArrayList<CsvItemsCO>) getSqlMap().queryForList("csvItemsMapper.retCsvItemsByRepList", csvItemsSC);

	
	Long oldTime = null;
	Long newTime = null;

	if(dateUpdated  != null)
	{
		try 
		{
			oldTime = dateUpdated.getTime();
			if(csvItemsSC.getIsOracle()==1)
			{
				 newTime = myLst.get(0).getCbkRptLineVO().getDATE_UPDATED().getTime();
			}
			else
			{
				String dteUpdStr=DateUtil.format(myLst.get(0).getCbkRptLineVO().getDATE_UPDATED(), "dd/MM/yyyy HH:mm:ss");
				Date dteUpd = DateUtil.parseDate(dteUpdStr, "dd/MM/yyyy HH:mm:ss");
				newTime=dteUpd.getTime();
			}
			if(oldTime != null && !newTime.equals(oldTime))
			{
				return null;
			}
		}
		catch (Exception e) 
		{
				log.error(e,"");
		}

	}	
	
	if(lstCsvItems.isEmpty())
	{
	    CsvItemsCO csvItemsCO = new CsvItemsCO();
	    csvItemsCO.getCbkRptLineVO().setCOMP_CODE(compCode);
	    csvItemsCO.getCbkRptLineVO().setREP_REF(repRef);
	    lstCsvItems.add(csvItemsCO);
	    getSqlMap().delete("csvItemsMapper.deleteCsvItems", lstCsvItems.get(0));
	    return 1;
	}
	lstCsvItems.get(0).getCbkRptLineVO().setCOMP_CODE(compCode);
	lstCsvItems.get(0).getCbkRptLineVO().setREP_REF(repRef);
	 getSqlMap().delete("csvItemsMapper.deleteCsvItems", lstCsvItems.get(0));
	 for(int i=0;i<lstCsvItems.size();i++)
	 {
	     lstCsvItems.get(i).getCbkRptLineVO().setCOMP_CODE(compCode);
	     lstCsvItems.get(i).getCbkRptLineVO().setREP_REF(repRef);
	     getSqlMap().insert("csvItemsMapper.saveCsvItems", lstCsvItems.get(i));
	 }
	return 1;
    }

}
