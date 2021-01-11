package com.path.vo.reporting.designer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.path.struts2.lib.common.GridParamsSC;
import com.path.vo.reporting.IRP_FIELDSCO;

public class IRP_ENTITIESSC extends GridParamsSC
{
    boolean isGrid = true;
    private String ENTITY_DB_NAME;
    private String ENTITY_ALIAS;
    private String FIELD_DB_NAME;
    private String FIELD_ALIAS;
    private String FIELD_DATA_TYPE;
    private String OBJECT_DB_NAME;
    private String COLUMN_NAME;
    private String COLUMN_TYPE;
    private String OBJECT_TYPE;
    private boolean isNewEntity = true;
    private String searchType = "E";
    private LinkedHashMap allFields	=	new LinkedHashMap();
    private LinkedHashMap addedFields	=	new LinkedHashMap();
    private HashMap deletedFields	=	new HashMap();
    private HashMap updatedFields	=	new HashMap(); 
    private List<String> delFieldsList  = 	new ArrayList<String>();
    private List<IRP_FIELDSCO> addFieldsList  = 	new ArrayList<IRP_FIELDSCO>();
    private List<String> updFieldsList  = 	new ArrayList<String>();
    private List<String> addedFieldsListStr  = 	new ArrayList<String>();
    private int addFieldsListCount;
    private boolean isLoadAll;
    private HashMap<String ,IRP_FIELDSCO> oldFieldsMap = new HashMap<String ,IRP_FIELDSCO>();
    
    public IRP_ENTITIESSC()
    {
	    super.setSearchCols(new String[] { "OBJECT_DB_NAME", "OBJECT_TYPE","OBJECT_DB_NAME", "OBJECT_TYPE","ENTITY_DB_NAME","ENTITY_ALIAS" });    
    }
    
    public IRP_ENTITIESSC(String searchType){
	if("E".equals(searchType))
	{
	    super.setSearchCols(new String[] { "ENTITY_DB_NAME", "ENTITY_ALIAS" });
	}
	else
	{
	    if("F".equals(searchType))
	    {
		super.setSearchCols(new String[] { "FIELD_DB_NAME", "FIELD_ALIAS" });
	    }
	    else
	    {
		if("FL".equals(searchType))
		    {
			super.setSearchCols(new String[] { "COLUMN_NAME", "COLUMN_TYPE" });
		    }
		else{
		    super.setSearchCols(new String[] { "OBJECT_DB_NAME", "OBJECT_TYPE" });    
		}
		
	    }
	}
    }
    
    public boolean isGrid()
    {
	return isGrid;
    }

    public void setGrid(boolean isGrid)
    {
	this.isGrid = isGrid;
    }

    public String getENTITY_DB_NAME()
    {
	return ENTITY_DB_NAME;
    }

    public void setENTITY_DB_NAME(String eNTITYDBNAME)
    {
	ENTITY_DB_NAME = eNTITYDBNAME;
    }

    public String getENTITY_ALIAS()
    {
	return ENTITY_ALIAS;
    }

    public void setENTITY_ALIAS(String eNTITYALIAS)
    {
	ENTITY_ALIAS = eNTITYALIAS;
    }

    public String getFIELD_DB_NAME()
    {
	return FIELD_DB_NAME;
    }

    public void setFIELD_DB_NAME(String fIELDDBNAME)
    {
	FIELD_DB_NAME = fIELDDBNAME;
    }

    public String getFIELD_ALIAS()
    {
	return FIELD_ALIAS;
    }

    public void setFIELD_ALIAS(String fIELDALIAS)
    {
	FIELD_ALIAS = fIELDALIAS;
    }

    public String getFIELD_DATA_TYPE()
    {
	return FIELD_DATA_TYPE;
    }

    public void setFIELD_DATA_TYPE(String fIELDDATATYPE)
    {
	FIELD_DATA_TYPE = fIELDDATATYPE;
    }

    public String getOBJECT_DB_NAME()
    {
	return OBJECT_DB_NAME;
    }

    public void setOBJECT_DB_NAME(String oBJECTDBNAME)
    {
	OBJECT_DB_NAME = oBJECTDBNAME;
    }

    public String getOBJECT_TYPE()
    {
	return OBJECT_TYPE;
    }

    public void setOBJECT_TYPE(String oBJECTTYPE)
    {
	OBJECT_TYPE = oBJECTTYPE;
    }

    public String getCOLUMN_NAME()
    {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String cOLUMNNAME)
    {
        COLUMN_NAME = cOLUMNNAME;
    }

    public String getCOLUMN_TYPE()
    {
        return COLUMN_TYPE;
    }

    public void setCOLUMN_TYPE(String cOLUMNTYPE)
    {
        COLUMN_TYPE = cOLUMNTYPE;
    }

    public boolean isNewEntity()
    {
        return isNewEntity;
    }
    public void setNewEntity(boolean isNewEntity)
    {
        this.isNewEntity = isNewEntity;
    }
    public String getSearchType()
    {
	return searchType;
    }

    public void setSearchType(String searchType)
    {
	this.searchType = searchType;
    }
    public LinkedHashMap getAllFields()
    {
        return allFields;
    }
    public void setAllFields(LinkedHashMap allFields)
    {
        this.allFields = allFields;
    }
    public LinkedHashMap getAddedFields()
    {
        return addedFields;
    }
    public void setAddedFields(LinkedHashMap addedFields)
    {
        this.addedFields = addedFields;
    }
    public HashMap getDeletedFields()
    {
        return deletedFields;
    }
    public void setDeletedFields(HashMap deletedFields)
    {
        this.deletedFields = deletedFields;
    }
    public HashMap getUpdatedFields()
    {
        return updatedFields;
    }
    public void setUpdatedFields(HashMap updatedFields)
    {
        this.updatedFields = updatedFields;
    }
    public List<String> getDelFieldsList()
    {
        return delFieldsList;
    }
    public void setDelFieldsList(List<String> delFieldsList)
    {
        this.delFieldsList = delFieldsList;
    }
    public List<IRP_FIELDSCO> getAddFieldsList()
    {
        return addFieldsList;
    }
    public void setAddFieldsList(List<IRP_FIELDSCO> addFieldsList)
    {
        this.addFieldsList = addFieldsList;
    }
    
    
    public List<String> getUpdFieldsList()
    {
        return updFieldsList;
    }
    public void setUpdFieldsList(List<String> updFieldsList)
    {
        this.updFieldsList = updFieldsList;
    }
    public int getAddFieldsListCount()
    {
        return addFieldsListCount;
    }
    public void setAddFieldsListCount(int addFieldsListCount)
    {
        this.addFieldsListCount = addFieldsListCount;
    }
	public List<String> getAddedFieldsListStr() {
		return addedFieldsListStr;
	}
	public void setAddedFieldsListStr(List<String> addedFieldsListStr) {
		this.addedFieldsListStr = addedFieldsListStr;
	}
	public boolean isLoadAll() {
		return isLoadAll;
	}
	public void setLoadAll(boolean isLoadAll) {
		this.isLoadAll = isLoadAll;
	}

	public HashMap<String, IRP_FIELDSCO> getOldFieldsMap() {
		return oldFieldsMap;
	}

	public void setOldFieldsMap(HashMap<String, IRP_FIELDSCO> oldFieldsMap) {
		this.oldFieldsMap = oldFieldsMap;
	}
    
}
