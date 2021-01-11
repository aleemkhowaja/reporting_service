<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8"/>
 	<xsl:include href="JrxmlInputs.xsl"/>
	<xsl:template name="jrxml-subdataset-include">
	 
		<xsl:for-each select="subDataset">
			<subDataset name="{@name}">			
				<xsl:copy-of select="./parameter"></xsl:copy-of>
				<xsl:copy-of select="./queryString"></xsl:copy-of>
				<xsl:copy-of select="./field"></xsl:copy-of>
				<xsl:copy-of select="./variable"></xsl:copy-of>
				<xsl:copy-of select="./group"></xsl:copy-of>
			</subDataset>
	    </xsl:for-each>
		
		<xsl:copy-of select="parameter"></xsl:copy-of>
		<queryString language="SQL">
			<xsl:choose>
				<xsl:when test="queryString">
					<xsl:value-of select="queryString"></xsl:value-of>
				</xsl:when>
				<xsl:otherwise>
					<![CDATA[SELECT 1 FROM DUAL]]>
				</xsl:otherwise>
			</xsl:choose>
		</queryString>
		
		<xsl:copy-of select="field"></xsl:copy-of>
		<xsl:copy-of select="variable"></xsl:copy-of>
		<xsl:apply-templates select="./group">
		</xsl:apply-templates>

		<background>
			<band splitType="Stretch"/>
		</background>
		
	</xsl:template>
	
	<xsl:template match="group">
		<xsl:variable name="groupName">
			<xsl:value-of select="@name"></xsl:value-of>
		</xsl:variable>
		<xsl:variable name="groupHeaderName">group_header_<xsl:value-of select="@name"></xsl:value-of></xsl:variable>
		<xsl:variable name="groupFooterName">group_footer_<xsl:value-of select="@name"></xsl:value-of></xsl:variable>
		<group name="{$groupName}">	
			<xsl:copy-of select="./groupExpression"></xsl:copy-of>
			<xsl:for-each select="/report/table/tbody/tr[@expression = $groupName]">
				<xsl:if test="@id = $groupHeaderName">
					<groupHeader>
						<band height="{@height_jr}">
							<xsl:for-each select=".//input">
								<xsl:call-template name="jrxml-input-include">
								</xsl:call-template>
							</xsl:for-each>
							        		
							<xsl:apply-templates select=".//hr">
							</xsl:apply-templates>
							
						</band>
					</groupHeader>
				</xsl:if>
				
				<xsl:if test="@id = $groupFooterName">
					<xsl:copy-of select="td/div/*/."></xsl:copy-of>
				</xsl:if>
			</xsl:for-each>
		</group>
	</xsl:template>
	
</xsl:stylesheet>