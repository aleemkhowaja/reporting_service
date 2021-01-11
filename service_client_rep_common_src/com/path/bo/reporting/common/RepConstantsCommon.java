package com.path.bo.reporting.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class RepConstantsCommon
{
    public static final String EMPTY_STRING = "";
    public static final String COMMA = ", ";
    public static final String COLUMN = ": ";
    public static final String ROLE_DESKTOP_ACCESS = "ROLE_DESKTOP_ACCESS";
    public static final String ROLE_FULL_ACCESS = "ROLE_FULL_ACCESS";
    public static final String DB = "DB";
    public static final String Repository = "Repository";
    public static final String financialTemp = "financial_template";
    public static final String VARCHAR_TYPE_JASPER = "java.lang.String";
    public static final String NUMBER_TYPE_JASPER = "java.math.BigDecimal";
    public static final String DATE_TYPE_JASPER = "java.util.Date";
    public static final String TIMESTAMP_TYPE_JASPER ="java.sql.Timestamp";
    public static final String SNAPSHOT_MODF_COL_LBL_FIELD = "lblField";
    public static final String SNAPSHOT_TYP_FIELD = "feType";
    public static final String SNAPSHOT_HASH_LIVE_MOD_COL = "modColHash";
    public static final String SNAPSHOT_MODIFIED = "MODIFIED";
    public static final String SNAPSHOT_DRILLDOWN = "DRILLDOWN";
    public static final String SNAPSHOT_FTR_SITCOM ="ftr_sitcom_";
    public static final String SNAPSHOT_REP_REMITTANCE ="Report Remittance";
    public static final String SNAPSHOT_COUNTRY_CODE ="Country Code";
    public static final String SNAPSHOT_BANK_REF ="Bank Reference";
    public static final String SNAPSHOT_RETREIVAL_DATE ="Retrieval Date";
    public static final String SNAPSHOT_REP_SIZE = "Metadata Main Report Size";
    public static final String SNAPSHOT_PAGE_COUNT = "Metadata Main Page Count";
    public static final String QUERIES_PROG_REF="RD00Q";
    public static final String VARCHAR_V = "V";
    public static final String DATE_D = "D";
    public static final String NUMBER_N = "N";
    public static final String YES = "yes";
    public static final String YES_CAP="YES";
    public static final String FALSE = "false";
    public static final String Y = "Y";
    public static final String N = "N";
    public static final String NO = "NO";
    public static final String SNAPSHOT_HASH_LIVE_DRIL_COL = "drilColHash";
    public static final String SNAPSHOT_INIT_MAP = "initMap";
    public static final String SNAPSHOT_INFO_INIT_MAP = "initInfoMap";
    public static final String MISMATCH_CRITERIA_COLUMN_HASH="hashCrtCol";
    public static final String MISMATCH_CRITERIA_COLUMN="misCritCol";
    public static final String MISMATCH_INTER_INTRA = "misInterIntra";
    public static final String MISMATCH_FROM_MAIN_GRID="misCol";
    public static final String MISMATCH_REL_COLS="relCols";
    public static final String MISMATCH_REL_REPORTS="relRep";
    public static final String MISMATCH_ALL_REC="lstAllRec";
    public static final String MISMATCH_LST_DEL="lstDel";
    public static final String MISMATCH_COL_TECH_NAME="colTechNameMap";
    public static final String MISMATCH_REL_CRITERIA="crt";
    public static final String IRP_VERIFIED_REPORTS = "IRP_VERIFIED_REPORTS";
    public static final Integer ENTER_COMPANY_CODE = 577;
    public static final Integer ENTER_DIVISION_CODE = 190;
    public static final Integer ENTER_DEPT_CODE = 216;
    public static final Integer INVALID_LINE_NB = 31261;
    public static final Integer PROCEDURE_FAILED = 771;
    public static final Integer INVALID_FROM_DATE = 20237;
    public static final Integer INFORMATION = 2097;
    public static final String  STOP = "STOP";
    public static final String  SKIP = "SKIP";
    public static final String  REPLACE = "REPLACE";
    public static final String 	COL_TEMP_EXP_TYPE="X";
    
    public static final String univarchar = "UNIVARCHAR";
    public static final String varchar = "VARCHAR";
    public static final String numeric = "NUMERIC";
    public static final String dateTime = "DATETIME";
    public static final String charact = "CHAR";
    public static final String int_type="INT";
    public static final String decimal="DECIMAL";
    public static final String JAVA_UTIL_COLLECTION="java.util.Collection";
    public static final String REP_BIGDECIMAL="BigDecimal";
    public static final String NESTED_TYPE_STRING="String";
    public static final String NESTED_TYPE_DATE="Date";
    
    public static final int intVarchar = 12;
    public static final int intDate = 91;
    public static final int intNumeric = 4;
    public static final int intChar = 1;

    public static final String oracleDriver = "oracle.jdbc.driver.OracleDriver";
    public static final String sybaseDriver = "com.sybase.jdbc4.jdbc.SybDriver";
    public static final String mySQLDriver = "com.mysql.jdbc.Driver";
    public static final String sqlServerDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    public static final String MAIL_SPLIT_VAR = "SCH~";
    public static final String TFA_PRODUCT_PAGE_REF = "E01MT";


    public static final BigDecimal SAVE_DATA_TYPE_LOV_TYPE = new BigDecimal(570);
    public static final BigDecimal SCHED_STATUS_LOV_TYPE = new BigDecimal(363);
    public static final BigDecimal SCHED_MAIL_RECEIVER_TYPE = new BigDecimal(364);
    public static final BigDecimal SCHED_REPORT_DATE_TYPE = new BigDecimal(367);
    public static final BigDecimal SCHED_REPORT_FROM_EMAIL_TYPE = new BigDecimal(313);
    public static final BigDecimal SCHED_REPORT_TO_EMAIL_TYPE = new BigDecimal(314);
    public static final BigDecimal SCHED_REPORT_CC_EMAIL_TYPE = new BigDecimal(315);
    public static final BigDecimal SAVE_SCHED_REPORT_AS = new BigDecimal(570);
    public static final BigDecimal SCHED_TYPE = new BigDecimal(379);
    public static final BigDecimal FCR_REPORT_GROUPING_LOV_TYPE = new BigDecimal(400);
    public static final BigDecimal FCR_REPORT_TYPE_LOV_TYPE = new BigDecimal(414);
    public static final BigDecimal FREQUENCY_SNP_LOV_TYPE = new BigDecimal(430);
    public static final BigDecimal REPORTS_MISMATCH_LOV_TYPE = new BigDecimal(466); 
    public static final BigDecimal TEMPLATE_CRITERIA_DMY = new BigDecimal(552); 
    public static final BigDecimal SNAPSHOTS_FORMULA_PB_LOV_TYPE = new BigDecimal(502); 
    public static final BigDecimal SNAPSHOTS_EXPRESSIONS_LOV_TYPE = new BigDecimal(503); 
    public static final BigDecimal TFA_PRODUCT_LOV_TYPE = new BigDecimal(131);
    public static final BigDecimal SNAPSHOTS_INF_LOV_TYPE = new BigDecimal(493);
    public static final BigDecimal SESSION_PROPERTIES = new BigDecimal(532);
    public static final BigDecimal REPORT_PROPERTIES = new BigDecimal(533);
    public static final BigDecimal REP_IMPORT_OPTIONS = new BigDecimal(568);
    public static final BigDecimal COL_CALC_DAYS = new BigDecimal(79);
    public static final BigDecimal COL_CALC_MONTHS = new BigDecimal(286);
    public static final BigDecimal TEMPLATE_MALE_FEMALE = new BigDecimal(15);
    public static final BigDecimal TEMPLATE_SCL_CRIT_LOV = new BigDecimal(151);
    public static final BigDecimal ARG_CONSTRAINT_LOV = new BigDecimal(590);
    public static final BigDecimal CRITERIA_IDT_LOV = new BigDecimal(641);
    public static final BigDecimal ARG_DATE_VALUE_LOV = new BigDecimal(650);
    public static final BigDecimal ARG_QUERY_OPTIONS_LOV = new BigDecimal(655);
    public static final String SNAPSHOT_LOCATION = "snapshots";
    public static final String SCHED_SPLIT_LOCATION = "scheduler";
    public static final String IMAGES_LOCATION = "images";
    public static final String WATERMARK_TXT = "DRAFT";
    public static final String UPDATE_MODE = "UPDATE";
    public static final String EXPORT_REP = "export report";
    public static final String IMPORT_REP = "import report";
    public static final String REP_EXT = "Rep_";
    public static final String REP_BLOB = "BLOB";
    public static final String REP_CLOB = "CLOB";
    public static final String REP_IMAGE = "image";
    public static final String REP_TEXT = "text";
    public static final String REP_IRP_QRY_TBL = "IRP_AD_HOC_QUERY";
    public static final String ROOT = "ROOT";
    
    //Encoding Types 
    public static final String ENCODING_TYPE_ASCII = "ASCII";
    
    
    
    // Types
    public static final String TYPE_FIELD = "F";
    public static final String TYPE_PARAMETER= "P";
    
    // SQL Statement type 
    public static final String SQL_TYPE_INSERT = "insert";
    public static final String SQL_TYPE_UPDATE= "update";
    public static final String SQL_TYPE_DELETE = "delete";
    
    //Modes 
    public static final String MODE_EDIT = "edit";
    public static final String MODE_ADD= "add";
    public static final String MODE_UPDATE = "update";
    public static final String MODE_DELETE= "del";
    public static final String MODE_NONE= "none";
    
    // Constants Used in JrGlobal Functions
    public static final BigDecimal QTY_DEC_PTS = new BigDecimal(4);
    public static final String FIELD_YIELD = "Yield";
    public static final String FIELD_INTEREST = "Interest";
    public static final String FIELD_PROFIT = "Profit";
    public static final String FIELD_POINTS = "Points";
    public static final String ISLAMIC_APP_TYPE = "I";
    public static final String LANG_AR_A = "A";
    public static final String LANG_AR_ARAB = "ARAB";
    public static final String LANG_AR_ARABIC = "ARABIC";
    
    // GL Types 
    public static final String GL_TYPE_R="R";
    public static final String GL_TYPE_L = "L";
    public static final String GL_TYPE_S = "S";
    public static final String GL_TYPE_A = "A";
    public static final String GL_TYPE_E = "E";
    
    //Constants used for opt axs extensions
   
    // for delete 
    public static final String OPT_DEL_D = "D";
    public static final String OPT_DEL_EN = "Delete";
    public static final String OPT_DEL_FR = "Supprim.";
    public static final String OPT_DEL_AR = "حذف";
    public static final String OPT_DEL_FA = "حذف";
    public static final String OPT_DEL_TK = "sil";
    public static final String OPT_DEL_RU = "удал.";
    
    // for modify 
    public static final String OPT_MOD_M  = "M";
    public static final String OPT_MOD_EN = "Modify";
    public static final String OPT_MOD_FR = "Modifier";
    public static final String OPT_MOD_AR = "تعديل";
    public static final String OPT_MOD_FA = "جزئيات";
    public static final String OPT_MOD_TK = "Değiştir";
    public static final String OPT_MOD_RU = "Изм-ть";
    
    //for save 
    public static final String OPT_SV_SV = "SV";
    public static final String OPT_SV_EN = "Save";
    public static final String OPT_SV_FR = "Save";
    public static final String OPT_SV_AR = "حفظ";
    public static final String OPT_SV_FA = "ذخيره";
    public static final String OPT_SV_TK = "Kaydet";
    public static final String OPT_SV_RU = "Сохранить";
    
    //for save as  
    public static final String OPT_SA_SA = "SA";
    public static final String OPT_SA_EN = "Save As";
    public static final String OPT_SA_FR = "Sauv. sous";
    public static final String OPT_SA_AR = "حفظ بإسم";
    public static final String OPT_SA_FA = " ذخيره  مانند";
    public static final String OPT_SA_TK = "Farklı Kaydet";
    public static final String OPT_SA_RU = "Сохр. как";
    
    //for Send Mail
    public static final String OPT_SM_SM =  "SM";
    public static final String OPT_SM_EN = "Send Mail";
    public static final String OPT_SM_FR = "Envoyer Un Courriel";
    public static final String OPT_SM_AR = "ارسال رسالة الكترونية";
    public static final String OPT_SM_FA = " فرستادن  پست";
    public static final String OPT_SM_TK = "Gönder Yayınla";
    public static final String OPT_SM_RU = "Отпр пис";
    
    //for Print
    public static final String OPT_PR_PR = "PR";
    public static final String OPT_PR_EN = "Print";
    public static final String OPT_PR_FR = "Impression";
    public static final String OPT_PR_AR = "طباعة";
    public static final String OPT_PR_FA = "چاپ";
    public static final String OPT_PR_TK = "Yazdır";
    public static final String OPT_PR_RU = "Печать";
    
    //for Scheduler
    public static final String OPT_SC_SC = "SC";
    public static final String OPT_SC_EN = "Scheduler";
    public static final String OPT_SC_FR = "Ordonnanceur";
    public static final String OPT_SC_AR = "المبرمج";
    public static final String OPT_SC_FA = "زمان بند";
    public static final String OPT_SC_TK = "siZamanlayıcıl";
    public static final String OPT_SC_RU = "Планировщ";

    //for snapshot
    public static final String SNP_BTN_AXS_REF="SNAP003SVSNAP";
    public static final String SNP_FREQUENCY_MONTHLY="M";
    public static final String SNP_FREQUENCY_QUARTERLY="Q";
    public static final String SNP_FREQUENCY_YEARLY="Y";
    public static final String SNP_FREQUENCY_SEMI_ANNUALLY="SA";
    
    //Constants used for AXS
    public static final String AXS_STATUS_P="P";
    public static final String AXS_TO_BE_DELETED_N="N";
    
    //Arabic name of days
    public static final String Monday_AR    = "الأثنين";
    public static final String Tuesday_AR   = "الثلاثاء";
    public static final String Wednesday_AR = "الأربعاء";
    public static final String Thursday_AR  = "الخميس";
    public static final String Friday_AR    = "الجمعة";
    public static final String Saturday_AR  = "الأسبت";
    public static final String Sunday_AR    = "الأحد";
    
    //Gregorian name of months
    public static final String JAN_Gregorian    = "يناير";
    public static final String FEB_Gregorian    = "فبراير";
    public static final String MAR_Gregorian    = "مارس";
    public static final String APR_Gregorian    = "آبريل";
    public static final String MAY_Gregorian    = "مايو";
    public static final String JUN_Gregorian    = "يونيو";
    public static final String JUL_Gregorian    = "يوليو";
    public static final String AUG_Gregorian    = "اغسطس";
    public static final String SEP_Gregorian    = "سبتمبر";
    public static final String OCT_Gregorian    = "أكتوبر";
    public static final String NOV_Gregorian    = "نوفمبر";
    public static final String DEC_Gregorian    = "ديسمبر";

    public static final BigDecimal PROC_PARAM_OUT_TYPE_LOV_TYPE=new BigDecimal(523);
    public static final String OUT_PROC_PARAM="OUT";
    public static final BigDecimal SCHED_FREQUENCY_TYPE=new BigDecimal(543);
    public static final String SCHED_FREQUENCY_ONCE ="O";
    public static final String SCHED_FREQUENCY_YEAR ="Y";
    public static final String SCHED_FREQUENCY_MONTH ="M";
    public static final String SCHED_FREQUENCY_ENDOFMONTH ="E";
    public static final String SCHED_FREQUENCY_DAILY ="D";
    public static final String SCHED_FREQUENCY_HOUR ="H";
    public static final String SCHED_CONST_ID = "sched_";
    
    //Images extensions type  
    public static final String IMAGE_EXT_BMP ="bmp";
    public static final String IMAGE_EXT_GIF ="gif";
    public static final String IMAGE_EXT_JPG ="jpg";
    public static final String IMAGE_EXT_JPEG ="jpeg";
    public static final String IMAGE_EXT_PNG ="png";
    public static final String NEW_QRY_TYPE ="new"; 
    
    public static final String FTR_LANGUAGE_ENGLISH = "E";
    public static final String FTR_LANGUAGE_ARABIC = "A";
    public static final String FTR_LANGUAGE_FRENCH = "F";
    
    //Criteria types
    public static final String CRITERIA_COUNTRIES_CTY= "CTY";
    public static final String CRITERIA_BRANCH_BR= "BR";
    public static final String CRITERIA_CBK_CIF_TYPE= "CBK";
    public static final String CRITERIA_CIF_NO= "CIF";
    public static final String CRITERIA_CIF_TYPE= "CIT";
    public static final String CRITERIA_CATEG_GOODS= "COG";
    public static final String CRITERIA_GOODS= "GDS";
    public static final String CRITERIA_PER = "PER";
    public static final String CRITERIA_MBK = "MBK";
    public static final String CRITERIA_SMART = "SMT";
    public static final String CRITERIA_VAL_L = "L";
    public static final String CRITERIA_VAL_G = "G";
    public static final String CRITERIA_VAL_SEC = "SEC";
    public static final String CRITERIA_CONTINENT = "CNT";
    public static final String CRITERIA_CIF_PROFILE = "CPR";
    public static final String CRITERIA_CIF_CREDIT_RATING = "CCR";
    public static final String CRITERIA_COUNTRY_REGION = "CRG";
    public static final String CRITERIA_TRANSACTION_PURPOSE = "TPR";
    public static final String CRITERIA_CURRENCY = "CUR";
    public static final String CRITERIA_DEAL_ECONOMIC_SEC = "DES";
    public static final String CRITERIA_EMPLOYEE_CATEGORY = "EMG";
    public static final String CRITERIA_FIXED_ASSETS_CAT = "FAC";
    public static final String CRITERIA_COLLATERAL_TYPE = "CLT";
    public static final String CRITERIA_FACILITY_TYPE = "FT";
    public static final String CRITERIA_GL_TERM = "GLT";
    public static final String CRITERIA_IBC_TYPE = "IBC";
    public static final String CRITERIA_ILC_TYPE = "ILC";
    public static final String CRITERIA_LEGAL_STATUS = "LGS";
    public static final String CRITERIA_LG_TYPE = "LG";
    public static final String CRITERIA_PRODUCT_ECO_SEC = "PES";
    public static final String CRITERIA_NATIONALITY = "NAT";
    public static final String CRITERIA_OBC_TYPE = "OBC";
    public static final String CRITERIA_ONE_OBLIGOR = "OBL";
    public static final String CRITERIA_OLC_TYPE = "OLC";
    public static final String CRITERIA_OCCUPATION_POSITION = "OPO";
    public static final String CRITERIA_SECURITY_TYPE = "STY";
    public static final String CRITERIA_PURPOSE_FINANCING = "POF";
    public static final String CRITERIA_CIF_RELATION = "CRL";
    public static final String CRITERIA_REGION_BRANCH = "RGB";
    public static final String CRITERIA_CIF_PRIORITY = "CPT";
    public static final String CRITERIA_LINK_MGMNT = "LM";
    public static final String CRITERIA_PRODUCT_CLASS = "PDC";
    public static final String CRITERIA_DEAL_PROV_CATEG = "DPC";
    public static final String CRITERIA_TRANSACTION_LABEL = "TLB";
    public static final String CRITERIA_TRANSACTION_TYPE = "TTY";
    public static final String CRITERIA_MAIN_ECO_SEC = "MES";
    public static final String CRITERIA_ECONOMIC_SEC = "ECS";
    public static final String CRITERIA_DEAL_TENURE = "DTN";
    public static final String CRITERIA_CATEG_REGION = "CCL";
    public static final String CRITERIA_COLLATERALIZED_TYPE = "CZT";
    public static final String CRITERIA_ASSET_TRANS_TYPE = "ATT";
    public static final String CRITERIA_FOREX_DEAL_TYPE = "FDT";
    public static final String CRITERIA_SEC_CATEG = "SCT";
    public static final String CRITERIA_AMT_BY_GL = "AGL";
    public static final String CRITERIA_MAT_DATE = "MAT";
    public static final String CRITERIA_DEAL_PERIOD = "DPR";
    public static final String CRITERIA_DEBIT_CREDIT = "DCS";
    public static final String CRITERIA_TOTAL_DC = "TOT";
    public static final String CRITERIA_SOURCE_USAGES = "SU";
    public static final String CRITERIA_DEAL_CAT_TYPE = "DCT";
    public static final String CRITERIA_RESIDENT = "RES";
    public static final String CRITERIA_AMOUNT = "AMT";
    public static final String CRITERIA_ORIGINAL_MATURITY = "OM";
    public static final String CRITERIA_CIF_SECURITY_ISSUER = "CSI";
    public static final String CRITERIA_SECURITY_ECO_SEC = "SER";
    public static final String CRITERIA_GENDER = "GEN";
    public static final String CRITERIA_JOB = "JOB";
    public static final String CRITERIA_SEC_CLASSIFICATION = "SCL";
    public static final String CRITERIA_GL_CODE = "GLC";
    public static final String CRITERIA_DETAIL_GL_CODE = "DGL";
    public static final String CRITERIA_WCL = "WCL";
    public static final String CRITERIA_JVT = "JVT";
    public static final String CRITERIA_IDT = "IDT";
    public static final String CRITERIA_CAF = "CAF";
    
    //criteria views
    public static final String GL_TERM_VIEW = "V_IRP_GLT";
    public static final String REGION_BR_VIEW = "V_IRP_RGB";
    public static final String MAIN_ECO_SEC_VIEW1 = "V_IRP_MES1";
    public static final String MAIN_ECO_SEC_VIEW2 = "V_IRP_MES2";
    public static final String SEC_NO_VIEW1 = "V_IRP_SEC";
    public static final String SEC_NO_VIEW2 = "V_IRP_SEC1";
    public static final String SMT_VIEW = "V_IRP_SMT";
    public static final String CIF_SEC_ISSUER="V_IRP_CSI";
    public static final String IRP_CRIT_DTLS_LOOKUP_CAP="IRP_CRIT_DTLS_LOOKUP";
    public static final String IRP_CRIT_DTLS_LOOKUP_SMALL="irp_crit_dtls_lookup";
    public static final String TEMPLATE_TYPE = "T";
    public static final String DEAL_PROV_CATEG_VIEW="V_IRP_TRS_PROVISION_CATEGORY";
    public static final String REGION_VIEW="V_IRP_REGIONS";
    public static final String ECO_SEC_CRIT_VIEW="V_IRP_SUB_ECO_SECTORS";
    public static final String DES_SUB_VIEW="V_IRP_DES_SUB";
    public static final String CRT_CAF_VIEW="V_IRP_CAF";
    public static final String CRT_CAF_VIEW_SMT="V_IRP_CAF_SMT";
    
    //import/export tables
    public static final String IRP_AD_HOC_REPORT 	= "IRP_AD_HOC_REPORT";
    public static final String FTR_EXP_XLS 		= "FTR_EXP_XLS";
    public static final String IRP_REP_ARGUMENTS 	= "IRP_REP_ARGUMENTS";
    public static final String IRP_AD_HOC_QUERY 	= "IRP_AD_HOC_QUERY";
    public static final String IRP_REPORT_QUERY 	= "IRP_REPORT_QUERY";
    public static final String IRP_SUB_REPORT 		= "IRP_SUB_REPORT";
    public static final String IRP_REP_PROC 		= "IRP_REP_PROC";
    public static final String IRP_REP_PROC_PARAMS 	= "IRP_REP_PROC_PARAMS";
    public static final String IRP_QUERY_ARG_MAPPING 	= "IRP_QUERY_ARG_MAPPING";
    public static final String OPT 			= "OPT";
    public static final String OPT_EXTENDED 		= "OPT_EXTENDED";
    public static final String IRP_FOLDER 		= "IRP_FOLDER";
    public static final String IRP_HASH_TABLE_REPORT 	= "IRP_HASH_TABLE_REPORT";
    public static final String IRP_CONNECTIONS 		= "IRP_CONNECTIONS";
    public static final String IRP_PROC 		= "IRP_PROC";
    public static final String IRP_HASH_TABLE 		= "IRP_HASH_TABLE";
    public static final String IRP_REP_HYPERLINKS	= "IRP_REP_HYPERLINKS";
    public static final String IRP_REP_ARG_CONSTRAINTS	= "IRP_REP_ARG_CONSTRAINTS";
    public static final String IRP_REPORT_SCHEDULE	= "IRP_REPORT_SCHEDULE";
    public static final String IRP_REPORT_SCHED_PARAMS	= "IRP_REPORT_SCHED_PARAMS";
    public static final String IRP_REP_SCHED_MAIL_GROUP_BY = "IRP_REP_SCHED_MAIL_GROUP_BY";
    public static final String IRP_REP_SCHED_MAIL_RECEIVERS = "IRP_REP_SCHED_MAIL_RECEIVERS";
    public static final String IRP_REP_IMAGES		= "IRP_REP_IMAGES";
    public static final String IRP_SCHED_HYPER = "schedHyper";
    
    //import/export tables columns
    public static final String JRXML_FILE_COL		= "JRXML_FILE";
    public static final String PROG_REF_COL		= "PROG_REF";
    public static final String APP_NAME_COL		= "APP_NAME";
    public static final String CONN_ID_COL		= "CONN_ID";
    public static final String REPORT_TYPE_COL		= "REPORT_TYPE";
    public static final String COMP_CODE_COL		= "COMP_CODE";
    public static final String XHTML_FILE_COL		= "XHTML_FILE";
    public static final String ARGUMENT_ID_COL		= "ARGUMENT_ID";
    public static final String QUERY_ID_COL		= "QUERY_ID";
    public static final String QUERY_ID_DEFAULT_COL	= "QUERY_ID_DEFAULT";
    public static final String VALUE_CODE_COL		= "VALUE_CODE";
    public static final String VALUE_COL		= "VALUE";
    public static final String QUERY_OBJECT_COL		= "QUERY_OBJECT";
    public static final String PROC_ID_COL		= "PROC_ID";
    public static final String HASH_TABLE_ID_COL	= "HASH_TABLE_ID";
    public static final String CLIENT_ACRONYM_COL	= "CLIENT_ACRONYM";
    public static final String IS_DEFAULT_YN_COL	= "IS_DEFAULT_YN";
    public static final String HASH_TABLE_SCRIPT_COL	= "HASH_TABLE_SCRIPT";

    
    //import/export csv
    public static final String IRP_AD_HOC_REPORT_CSV 		= "IRP_AD_HOC_REPORT.csv";
    public static final String FTR_EXP_XLS_CSV  		= "FTR_EXP_XLS.csv";
    public static final String IRP_REP_ARGUMENTS_CSV  		= "IRP_REP_ARGUMENTS.csv";
    public static final String IRP_AD_HOC_QUERY_CSV  		= "IRP_AD_HOC_QUERY.csv";
    public static final String IRP_REPORT_QUERY_CSV  		= "IRP_REPORT_QUERY.csv";
    public static final String IRP_SUB_REPORT_CSV  		= "IRP_SUB_REPORT.csv";
    public static final String IRP_REP_PROC_CSV  		= "IRP_REP_PROC.csv";
    public static final String IRP_REP_PROC_PARAMS_CSV  	= "IRP_REP_PROC_PARAMS.csv";
    public static final String IRP_QUERY_ARG_MAPPING_CSV  	= "IRP_QUERY_ARG_MAPPING.csv";
    public static final String OPT_CSV  			= "OPT.csv";
    public static final String OPT_EXTENDED_CSV  		= "OPT_EXTENDED.csv";
    public static final String IRP_FOLDER_CSV  			= "IRP_FOLDER.csv";
    public static final String IRP_HASH_TABLE_REPORT_CSV  	= "IRP_HASH_TABLE_REPORT.csv";
    public static final String IRP_CONNECTIONS_CSV  		= "IRP_CONNECTIONS.csv";
    public static final String IRP_PROC_CSV  			= "IRP_PROC.csv";
    public static final String IRP_HASH_TABLE_CSV  		= "IRP_HASH_TABLE.csv";
    public static final String TRANSLATION_CSV  		= "translation.csv";
    public static final String IRP_REP_ARG_CONSTRAINTS_CSV	= "IRP_REP_ARG_CONSTRAINTS.csv";
    public static final String REP_SNAPSHOT_PARAM_CSV		= "REP_SNAPSHOT_PARAM.csv";
    public static final String IRP_SNAPSHOT_PARAM_MAPPING_CSV	= "IRP_SNAPSHOT_PARAM_MAPPING.csv";
    public static final String CSV_EXT				= ".csv";
    public static final String STEPS_TXT  			= "steps.txt";
    public static final String ERROR_TXT			= "error.txt";
    
    //import/export opt columns size
    public static final int    PROG_DESC_ARAB_SIZE	        = 100;
    public static final int    PROG_DESC_FA_SIZE	        = 100;
    public static final int    PROG_DESC_TK_SIZE	        = 100;
    public static final int    PROG_DESC_RU_SIZE        	= 100;
    public static final int    PROG_DESC_FR_SIZE	        = 100;
    public static final int    PROG_DESC_SIZE		        = 100;
    public static final int    MENU_TITLE_ENG_SIZE	        = 100;
    public static final int    MENU_TITLE_FR_SIZE	        = 100;
    public static final int    MENU_TITLE_ARAB_SIZE	        = 100;
    public static final int    MENU_TITLE_FA_SIZE	        = 100;
    public static final int    MENU_TITLE_TK_SIZE	        = 100;
    public static final int    MENU_TITLE_RU_SIZE	        = 100;

    
    
    public static final BigDecimal REP_SYSTEM_DATE_PARAM = new BigDecimal(556);
    public static final BigDecimal REP_SCHEDULE_DATE_PARAM = new BigDecimal(557);
    public static final String ALL_UNITS = "All Units";
    
    public static final String UPLOAD_DOWNLOAD_PROG_REF = "RD00UD";
	public static final String HYPERLINK_PROG_REF = "RD00HL";

    public static final BigDecimal WHEN_NO_DATA_LOV_TYPE =BigDecimal.valueOf(569);
    public static final String WHEN_NO_DATA_DEFAULT = "1";
    public static final String WHEN_NO_DATA_ALL_SECTION_NO_DETAIL = "2";
    public static final String WHEN_NO_DATA_BLANK = "3";
    public static final String WHEN_NO_DATA_NO_DATA_SECTION = "4";
    public static final String WHEN_NO_DATA_NO_PAGES = "5";
    
    public static final String BLOB_TYPE_JASPER = "java.io.InputStream";
    public static final String blob = "BLOB";
    public static final BigDecimal TMPLT_TYPE_LOV_TYPE = BigDecimal.valueOf(574);
    public static final String CLASS_VO = "VO";

    
    public static final String VERIFIED_SNAPSHOTS ="SNAPSHOT";
    public static final String UNDERSCORE ="_";
    
    //Column template col types
    public static final String CALC_ACT_DAY_BAL_CV = "MB";
    public static final String CALC_ACT_DAY_BAL_FC = "MBF";
    public static final String CALC_ACT_MON_BAL_CV = "GM";
    public static final String CALC_ACT_MON_BAL_FC = "GMF";
    public static final String REP_ALL="All";
    public static final String EN_CUR_BRIEF_NAME = "EN";
    public static final String AR_CUR_BRIEF_NAME = "AR";
    public static final String COL_TYPE_VB = "VB";
    public static final String COL_CALC_PERCENTAGE = "PER";
    public static final String COL_TYPE_VBF = "VBF";
    public static final String COL_TYPE_AM = "AM";
    public static final String COL_TYPE_AMF = "AMF";
    public static final String COL_TYPE_BM = "BM";
    public static final String COL_TYPE_ABD = "ABD";
    
    //Column template vars
    public static final String COL_TEMP_FROM_WHERE = "fg";
    public static final String COL00MT="COL00MT";
    public static final String COL_EXP_LINE_TYPE = "L";
    public static final ArrayList COL_TEMP_FC_CALCS = new ArrayList<String>(Arrays.asList(new String[] { "VBF",
		"AGF","ABF","AMF","MBF","GMF" }));
    public static final String COLTMP_MISSING_CRITERIA="criteria.missingDetails";

    //Template calculations types
    public static final String CALC_CNT_CIF = "COC";
    public static final String CALC_INDEP_CNT_CIF = "ICC";
    public static final String CALC_TYPE_BALANCE = "AB";
    public static final String CALC_TYPE_HDG = "HDG";
    public static final String CALC_TYPE_RTV="RTV";
    public static final String CALC_TYPE_OLC="OLC";
    public static final String CALC_TYPE_ILC="ILC";
    public static final String CALC_TYPE_LG="LG";
    public static final String CALC_TYPE_OBC="OBC";
    public static final String CALC_TYPE_IBC="IBC";
    public static final String IRP_CLIENT_REPORT	= "IRP_CLIENT_REPORT";
    public static final String PTH_CLIENTS	= "PTH_CLIENTS";
    public static final String REP_SNAPSHOT_PARAM	= "REP_SNAPSHOT_PARAM";
    public static final String IRP_SNAPSHOT_PARAM_MAPPING	= "IRP_SNAPSHOT_PARAM_MAPPING";
    public static final String IRP_CLIENT_REPORT_CSV	= "IRP_CLIENT_REPORT.csv";
    public static final String PTH_CLIENTS_CSV	= "PTH_CLIENTS.csv";
    public static final String IRP_CLIENT_DEFAULT = "DEFAULT";
    public static final String TMPLT_EXPR_LINE = "L";
    
    public static final String ARGUMENTS_TAB="arguments";
    public static final String HTML_TAG="<HTML></HTML>";
    public static final String JRXML_EXT=".jrxml";
    public static final String ONE = "1";
    public static final String ZERO = "0";
    public static final String REP_HAS_DATA="hasData";
    
    //import/export hashmaps name
    public static final String OLD_NEW_REP_QUERY_MAP  	="mapRepQryIdHM";
    public static final String CONN_ID_MAP		="mapRepConIdHM";
    public static final String IRP_PROC_MAP		="mapRepProcIdHM";
    public static final String IRP_HASH_TABLE_MAP	="mapRepHashTblIdHM";
    public static final String IRP_AD_HOC_REPORT_MAP	="irpAddHocRepHM";
    public static final String OLD_NEW_REP_REPLACE_MAP	="repIdOldNewReplaceMap";
    public static final String IRP_CONNECTIONS_MAP	="irpTempIrpConnHM";
    public static final String NEW_OLD_REP_SKIP_MAP	="repIdOldNewMap";
    public static final String REP_SNAPSHOT_PARAM_MAP	="repSnpParamMap";
    public static final String IRP_SNAPSHOT_PARAM_MAPPING_MAP	="irpSnpParamMappingMap";
    public static final String FTR_EXP_XLS_MAP		="ftrExpXlsHM";
    public static final String IRP_REP_ARGUMENTS_MAP	="irpRepArgHM";
    public static final String IRP_REP_ARG_CONSTRAINTS_MAP  ="argConstrHM";
    public static final String IRP_REP_PROC_MAP	       	="irpRepProcHM";
    public static final String IRP_REP_PROC_PARAMS_MAP	="irpRepProcParamHM";
    public static final String IRP_SUB_REPORT_MAP	="irpSubRepHM";
    public static final String IRP_QUERY_ARG_MAPPING_MAP="irpQryArgMappingHM";
    public static final String PROG_REF_APP_OPT_MAP	="repOptTempHM";
    public static final String OPT_MAP			="repOptHM";
    public static final String OPT_EXTENDED_MAP		="repOptExHM";
    public static final String IRP_AD_HOC_QUERY_MAP	="irpAdHocQueryHM";
    public static final String IRP_HASH_TABLE_REPORT_MAP="repHashTblRepHM";
    public static final String IRP_CLIENT_REPORT_MAP	="irpTempClientRepHM";
    public static final String PTH_CLIENTS_MAP		="pthClientsHM";
    public static final String REP_SUB_REP_LIST_MAP	="retSubRepId"; 
    public static final String EXCEPTIONS_LIST		="exceptionsLst"; 
    public static final String IRP_AD_HOC_QUERY_TMP_MAP="irpTempAdHocQueryHM";
    public static final String IRP_PROC_TMP_MAP		="irpTempIrpProcHM";
    public static final String IRP_HASH_TBL_TMP_MAP	="irpTempHashTblHM";
    public static final String IRP_FOLDER_MAP		="repFolderHM";
    public static final String IMPORT_REPORTS_DATE	="date";
    public static final String IMPORT_FAILED_REPORTS	="failedReportsSb";
    public static final String IMPORT_FAILED_MAIN_SUB_REPORTS="failedMainReportsSb";
    public static final String IMPORT_TRANSLATION_MAP="transMap";
    public static final String LS_LOOKUPTXT="lookuptxt_";
    
    //scheduler
    public static final String PARAM_OL_ERROR_FCR="p_param_sched_OL_ERROR";
    public static final String PARAM_OS_MESSAGE_FCR="p_param_sched_OS_MESSAGE";
    public static final String OL_ERROR_FCR="OL_ERROR";
    public static final String OS_MESSAGE_FCR="OS_MESSAGE";
    public static final String BASED_ON_REP_CURRENCY_FCR="BASED_ON_REP_CURRENCY";
    public static final String PARAM_BASED_ON_REP_CURRENCY="p_param_sched_BASED_ON_REP_CURRENCY";
    public static final String SCHED_REPORT_REF = "REPORT_REF";
    public static final String SCHED_STRING="string";
    public static final String SCHED_NUMERIC="numeric";
    public static final String SCHED_DATE_FRMT="dd/MM/yyyy";
    public static final String SCHED_ROOT_SYNTAX="{\"root\":[]}";
    
    //filterCriteria
    public static final String FILTER_CRT_TAB_ID="filterCritTable";
    public static final	String IMPORT_EXPORT_FILES_LOCATION = "imported_files";
    public static final String FCR_DEFAULT_RA_FORMAT = "D";
    public static final String FCR_TEMPLATE_TYPE = "P";
    public static final String NO_HEAD_FOOT = "noHeadFoot";
    public static final String CHECKBOX_PARAM_FLAG = "__checkbox_paramsFlag";
    
    //Error validation query
    public static final String VALID_ERROR="!ERROR!";
    public static final String SYB_GET_DATE="GETDATE()";
    public static final String ORA_SYSDATE="SYSDATE";
    public static final String DB_COMPARISON_NUMBER="(-1)";
    public static final String DB_COMPARISON_STRING="'-1'";
    public static final String REP_MULTISEL="multiSel";
    public static final String MULTISELECT_ERROR_SIZE="6";
    
    //automated import
    public static final String SUCCESS_AUTO_IMP="successAutoImport";
    public static final String AUTO_IMP_WARNINGS="Warnings";
    public static final String AUTO_IMP_ERRORS="Errors";
    public static final String AUTO_IMP_LOGS="Logs";
    public static final String IMPORT_ERROR_ACTION="ERROR_ACTION";
    public static final String INVALID_PATH = "reporting.pathValidation";
    
    public static final String SUB_REP_RAW_DATA_FOLDER = "subRepRawDataFolder";
    public static final String JASPER_EXT = "jasper";
    public static final String VERSION_TXT			= "version.txt";
    public static final String VERSION_KEY			= "Version";
    public static final String VERSION_DATE_KEY			= "Date";
    public static final String VERSION_MODIFIED_KEY		= "Modified";
    public static final String EQUAL = "=";
    public static final String REPORT ="REP";

    //FCR reports type
    public static final String FCR_SUMMARIZED = "S";
    public static final String FCR_SUM = "FCRSUMUNP";
    public static final String FCR_D = "FCRDURNP";
    /*
     * OPT columns to be skipped when checking for mismatch between VO and DB
     * since it is only exist in case the client has the 'safe' application.
     */
    public static final ArrayList<String> ORIGIN_BR_LST = new ArrayList<String>(Arrays.asList(new String[] {
	    "ORIGIN_BR_D", "ORIGIN_BR_U", "ORIGIN_BR_I" }));
    //template column template processes
    public static final String COL_TEMPLPROC_ASOF = "AO";
    public static final String COL_TEMPLPROC_FROMTO="FT";
    public static final String COL_TEMPLPROC_BOTH="B";
    public static final String COL_TEMPLPROC_PERIODIC ="P";
    public static final String TIME_BUCKETS="TB00MT";
    
    public static final String DATE_VALUE_ASOF = "1";
    public static final String DATE_VALUE_FROM="2";
    public static final String DATE_VALUE_TO="3";
    public static final String NOT_FOUND_VALUE="notFoundValue";
    public static final String REP_DEPENDENCY_LIKE=" LIKE ('";
    public static final String REP_DEPENDENCY_SELECTALL="SELECT * FROM (";
    public static final String REP_WHERE_CLAUSE=")TBLE WHERE ";
    public static final String REP_ORDER_CLAUSE="ORDER";
    public static final String ARG_QRY_LIVESEARCH_WITHOUT_DESC ="1";
    public static final String ARG_QRY_LIVESEARCH_WITH_DESC ="2";
    public static final String ARG_QRY_COMBO ="3";
    public static final ArrayList CALC_TYPE_LIST = new ArrayList<String>(Arrays.asList(new String[] { "FUA", "FUD",
    "FUI" }));
 
    public static final String TMPLT_DESC_COL="TMPLT_DESC";
    public static final String ROW_TO_COL_ARG="ROW_TO_COL";
    public static final String repParamsCO_arg="repParamsCO";
    public static final String FTR_REP1_TBL="FTR_REP1";
    public static final String FTR_REP_TBL="FTR_REP";
    public static final String FTR_REP_TMP_HASH="#FTR_REP_TMP";
    public static final String FTR_REP_TEMP_HASH="#FTR_REP_TEMP";
    public static final String REP1_FTR_TEMP="REP1_FTR_TEMP";
    public static final String REP_BOB45 = "R0045";
    public static final String REP_BOB23="R0023";
    public static final String REP_FTR_R0025="R0025";
    public static final String REP_FCR_SUMMARIZED="R0020D00";
    public static final String REP_FCR_DETAILED="R00201D00";
    public static final String DYNCOL_FIELD="DYNCOL_";
    public static final String GL_TYPE_FIELD="GL_TYPE";
    public static final String FIELD_JASPER="$F";
    public static final String FCRSUM_REF="FCRSUM";

    public static final ArrayList<String> SCHED_SESSION_NAME = new ArrayList<String>(Arrays.asList("baseCurrDecPoint",
	    "baseCurrencyCode", "baseCurrencyName", "branchCode", "clientType", "companyArabName", "companyCode",
	    "companyName", "currentAppName", "exposCurCode", "exposCurName", "fiscalYear", "runningDateRET",
	    "userName", "isRTLDir", "language", "branchName","employeeId"));
    public static final String SCHED_CLUSTER_OPER_NAME="RepSCHED";
    public static final String DATE_STR="Date";
    public static final String STRING_STR="String";
    public static final String INT_STR="int";
    public static final String JASPER_PATH_ARR="pathArr";
    public static final String JASPER_PATH_HASNEXT="hasNext";
    public static final String SITCOM_IS_NEW="isNew";
    public static final String SITCOM_PATH_CNT="pathCnt";
    public static final String TXT_FILE_NBLINES_FUNC="numberOfLines()";
    public static final String TXT_FILE_DATELBL="JrFunc.string($F{";
    public static final String REP_JASPER_EXT=".jasper";
    public static final String REP_IMAGES_MAP="IMAGES_MAP";
    public static final String SNP_REPORT_ORDER="REPORT_ORDER";    
    //Report Viewer
    public static final String FOLDER="Folder";
    public static final String FILE="File";
    public static final String FILTERS_MAP="FILTERS_MAP";
    public static final String ARG_FILTERS_MAP="ARG_FILTERS_MAP";
    public static final String SUB_REP_MAP="SUB_REP_MAP";
    
    //Constant Use in CommonReportingBoimpl
    public static final String CALLED_FROM_COMMON="CALLED_FROM_COMMON";
    
    //report logging
    public static final String REPORT_LOGGING_DIR="repLogs";
    public static final String REPORT_LOGGING_FILE="repLogs.log";
    public static final String IRP_REP_METADATA_LOG	= "IRP_REP_METADATA_LOG";
    public static final String IRP_METADATA_ROOT_FOLDER = "metadata";
    public static final String REP_EXT_OUT = ".out";
    public static final String MAIN_REP_SIZE = "mainRepSize";
    public static final String MAIN_PAGE_COUNT = "mainPageCnt";
    public static final String SCHED_SESS_DATE_FORMAT="EEE MMM dd HH:mm:ss zzz yyyy";
    
    public static final BigDecimal SAVE_TYPE_ALERT = new BigDecimal(4);
    public static final String ALERT_TYPE_LOV_CODE = "6";
    public static final String REP_PARAM_SOURCE_ID = "sourceId";
    
    public static final String REP_PARAM_DECIMAL_POINT = "decimal_points";
    
    //CAF
    public static final String gv_company_code ="#gv_company_code";
    public static final String gv_branch_code ="#gv_branch_code";
    public static final String gv_currency ="#gv_currency";
    public static final String gv_fiscal_year ="#gv_fiscal_year";
    public static final String gv_cy_dec ="#gv_cy_dec";
    public static final String gv_exposure_cy ="#gv_exposure_cy";
    public static final String gi_applicationversion ="#gi_applicationversion";
    public static final String gv_rep_id ="#gv_rep_id";
    public static final String gv_company_name ="#gv_company_name";
    public static final String gv_company_name_arabic ="#gv_company_name_arabic";
    public static final String gv_userid ="#gv_userid";
    public static final String gv_window_reference ="#gv_window_reference";
    public static final String gc_cli_type ="#gc_cli_type";
    public static final String gs_base_cy_name_eng ="#gs_base_cy_name_eng";
    public static final String gv_exposure_cy_name_eng ="#gv_exposure_cy_name_eng";
    public static final String gv_application ="#gv_application";
    public static final String f_get_computer_name ="#f_get_computer_name";
    public static final String gv_system_date ="#gv_system_date";
}
