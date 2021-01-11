<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:jr="http://jasperreports.sourceforge.net/jasperreports/components"
	xmlns:jasper="http://jasperreports.sourceforge.net/jasperreports"
	exclude-result-prefixes="jasper java jr">
	<xsl:output method="html" encoding="UTF-8" indent="no"/>
	<xsl:template name="xhtml-subdataset-include">
	 
		<xsl:for-each select="jasper:subDataset">
			<xsl:variable name="subdatasetName">
				<xsl:value-of select="@name"/>
			</xsl:variable>
			<subDataset name="{$subdatasetName}">			
				<xsl:copy-of select="./jasper:parameter"></xsl:copy-of>
				<xsl:copy-of select="./jasper:queryString"></xsl:copy-of>
				<xsl:copy-of select="./jasper:field"></xsl:copy-of>
				<xsl:copy-of select="./jasper:variable"></xsl:copy-of>
				<xsl:copy-of select="./jasper:group"></xsl:copy-of>
				<xsl:copy-of select="..//jr:table/jasper:datasetRun[@subDataset=$subdatasetName]/jasper:datasetParameter"></xsl:copy-of>
			</subDataset>
	    </xsl:for-each>

		<xsl:copy-of select="./jasper:parameter"></xsl:copy-of>
		<xsl:copy-of select="./jasper:queryString"></xsl:copy-of>
		<xsl:copy-of select="./jasper:field"></xsl:copy-of>
		<xsl:copy-of select="./jasper:variable"></xsl:copy-of>
		<xsl:apply-templates select="./jasper:group">
		</xsl:apply-templates>
		
	</xsl:template>
	
	<xsl:template match="jasper:group">
		<group name="{@name}">
			<xsl:copy-of select="jasper:groupExpression"></xsl:copy-of>
		</group>
	</xsl:template>
	
</xsl:stylesheet>