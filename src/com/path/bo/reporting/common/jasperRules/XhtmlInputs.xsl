<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:java="http://xml.apache.org/xslt/java"
	xmlns:jasper="http://jasperreports.sourceforge.net/jasperreports"
	exclude-result-prefixes="jasper java jr">
	<xsl:output method="html" encoding="UTF-8" indent="no"/>

	<xsl:template name="xhtml-input-include">
		<xsl:apply-templates select="jasper:textElement"></xsl:apply-templates>
		<xsl:apply-templates select="jasper:textElement/jasper:font"></xsl:apply-templates>
		<xsl:if test="not(jasper:textElement/jasper:font)">
			<xsl:apply-templates select="jasper:reportElement"></xsl:apply-templates>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="xhtml-img-include">
		<img  style="height: {jasper:reportElement/@height}px; width:{jasper:reportElement/@width}px; left: {jasper:reportElement/@x}px; top: {jasper:reportElement/@y}px;">
			<xsl:attribute name="src">
				<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnXhtmlImagePath(./jasper:imageExpression/.)" disable-output-escaping="yes"></xsl:value-of>
			</xsl:attribute>
			<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
		</img>
	</xsl:template>
	
	<xsl:template name="xhtml-subRepImg-include">
		<img src="/imal_reporting_portal/ckeditor/plugins/subreports/image/subreport.png" name="subReportImg"  style="width: 30px; height: 30px;">
			<xsl:attribute name="id">
				<xsl:value-of select="substring-before(substring-after(jasper:subreportExpression/.,'+ &quot;'),'.jasper&quot;')"></xsl:value-of>
			</xsl:attribute>
			<xsl:attribute name="expr">
				<xsl:value-of select="substring-before(substring-after(jasper:subreportExpression/.,'+ &quot;'),'.jasper&quot;')"></xsl:value-of>
			</xsl:attribute>
			<xsl:attribute name="title">
				<xsl:value-of select="substring-before(substring-after(jasper:subreportExpression/.,'+ &quot;'),'.jasper&quot;')"></xsl:value-of>
			</xsl:attribute>
		<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
		</img>
	</xsl:template>	
	
	
	<xsl:template match="jasper:textElement">
		<xsl:if test="@*">
			<xsl:attribute name="style">
				<xsl:for-each select="@*"><xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnXhtmlStyleName(name())"></xsl:value-of>: <xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnXhtmlStyleValue(.)" ></xsl:value-of>;</xsl:for-each>
			</xsl:attribute>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="jasper:font">
		<xsl:variable name="attributes">
			<xsl:for-each select="@*"><xsl:value-of select="name()"></xsl:value-of>:<xsl:value-of select="."></xsl:value-of>;</xsl:for-each>
		</xsl:variable>
		<xsl:call-template name="css">
			<xsl:with-param name="attributesList" select="$attributes"/>
	    </xsl:call-template>
	</xsl:template>
	
	<xsl:template name="css">
		<xsl:param name="attributesList"/>
	    <xsl:variable name="first" select="substring-before($attributesList, ';')" /> 
	    <xsl:variable name="remaining" select="substring-after($attributesList, ';')" /> 
	    <xsl:if test="$first">
	    	<xsl:variable name="attName">
				<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnXhtmlAttName($first)"></xsl:value-of>
			</xsl:variable>
			<xsl:choose>
				<xsl:when test="$attName != ''">
					<xsl:choose>
						<xsl:when test=" $attName = 'u' or $attName = 'em' or $attName = 'strong' or $attName = 'strike' ">
							<xsl:element name="{$attName}">
								<xsl:choose>
									<xsl:when test="$remaining">
								        <xsl:call-template name="css">
								        	<xsl:with-param name="attributesList" select="$remaining" /> 
								        </xsl:call-template>
								    </xsl:when>
								    <xsl:otherwise>
								    	<xsl:apply-templates select="ancestor::jasper:textField/jasper:reportElement"></xsl:apply-templates>
								    </xsl:otherwise>
							    </xsl:choose>
							</xsl:element>
						</xsl:when>
						<xsl:otherwise>
							<span>
								<xsl:attribute name="style">
									<xsl:value-of select="$attName"></xsl:value-of>: <xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnXhtmlAttValue($first)"></xsl:value-of>
								</xsl:attribute>
								<xsl:choose>
									<xsl:when test="$remaining">
								        <xsl:call-template name="css">
								        	<xsl:with-param name="attributesList" select="$remaining" /> 
								        </xsl:call-template>
								    </xsl:when>
								    <xsl:otherwise>
								    	<xsl:apply-templates select="ancestor::jasper:textField/jasper:reportElement"></xsl:apply-templates>
								    </xsl:otherwise>
							    </xsl:choose>
							</span>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<xsl:choose>
						<xsl:when test="$remaining">
							<xsl:call-template name="css">
						    	<xsl:with-param name="attributesList" select="$remaining" /> 
						    </xsl:call-template>
					 	</xsl:when>
						<xsl:otherwise>
							<xsl:apply-templates select="ancestor::jasper:textField/jasper:reportElement"></xsl:apply-templates>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
	    </xsl:if>
	</xsl:template>
	
	<xsl:template match="jasper:reportElement">
		<font>
			<xsl:for-each select="@*">
				<xsl:variable name="attName">
					<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnXhtmlStyleName(name())"></xsl:value-of>
				</xsl:variable>
				<xsl:if test="$attName != ''">
					<xsl:attribute name="{$attName}">
						<xsl:value-of select="."></xsl:value-of>
					</xsl:attribute>
				</xsl:if>
			</xsl:for-each>
			<xsl:apply-templates select="ancestor::jasper:textField/jasper:textFieldExpression"></xsl:apply-templates>
		</font>
	</xsl:template>
	
	<xsl:template match="jasper:textFieldExpression">
		<xsl:variable name="fieldType">
			<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnFieldType(.)"></xsl:value-of>
		</xsl:variable>
			
		<input class="{@class}" value="{.}" readonly="true"  type="text"
			height="{ancestor::jasper:textField/jasper:reportElement/@height}" width="{ancestor::jasper:textField/jasper:reportElement/@width}" 
			style="font-size: 9px; left: {ancestor::jasper:textField/jasper:reportElement/@x}px; top: {ancestor::jasper:textField/jasper:reportElement/@y}px;">
			
			<xsl:if test="$fieldType = 'field'">
				<xsl:attribute name="field">true</xsl:attribute>
			</xsl:if>
			
			<xsl:if test="$fieldType = 'param'">
				<xsl:attribute name="param">true</xsl:attribute>
			</xsl:if>
			
			<xsl:if test="$fieldType = 'pageCnt'">
				<xsl:attribute name="name">pageCnt</xsl:attribute>
			</xsl:if>
			
			<xsl:if test="$fieldType = 'pageNbr'">
				<xsl:attribute name="name">pageNbr</xsl:attribute>
			</xsl:if>
			
			<xsl:if test="$fieldType = 'label'">
				<xsl:attribute name="label">true</xsl:attribute>
			</xsl:if>
			
			<xsl:if test="ancestor::jasper:textField/@pattern">
				<xsl:attribute name="pattern">
					<xsl:value-of select="ancestor::jasper:textField/@pattern"></xsl:value-of>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="ancestor::jasper:textField/jasper:box">
				<xsl:attribute name="border">
					<xsl:value-of select="ancestor::jasper:textField/jasper:box/jasper:pen/@lineWidth"></xsl:value-of>
				</xsl:attribute>
			</xsl:if>
			
			<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
		</input>
	</xsl:template>
	
</xsl:stylesheet>	