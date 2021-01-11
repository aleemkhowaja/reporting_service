<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8"/>
	<xsl:template name="jrxml-styles-include">
		<property name="ireport.zoom" value="1.0"/>
		<property name="ireport.x" value="0"/>
		<property name="ireport.y" value="0"/>
		<import value="com.path.vo.reporting.ReportParamsCO"/>
		<import value="com.path.bo.reporting.common.jreports.JrDataFunc"/>
		<import value="com.path.lib.common.util.DateUtil"/>
		<import value="com.path.lib.common.util.NumberUtil"/>
		<import value="com.path.bo.reporting.common.jreports.JrFunc"/>
		<import value="com.path.lib.common.util.StringUtil"/>
		<import value="com.path.bo.reporting.common.jreports.JrGlobal"/>
		<!-- 
		<style name="table">
			<box>
				<pen lineWidth="1.0" lineColor="#000000"/>
			</box>
		</style>
		<style name="table_TH" mode="Opaque" backcolor="#F0F8FF">
			<box>
				<pen lineWidth="0.5" lineColor="#000000"/>
			</box>
		</style>
		<style name="table_CH" mode="Opaque" backcolor="#BFE1FF">
			<box>
				<pen lineWidth="0.5" lineColor="#000000"/>
			</box>
		</style>
		<style name="table_TD" mode="Opaque" backcolor="#FFFFFF">
			<box>
				<pen lineWidth="0.5" lineColor="#000000"/>
			</box>
		</style>
		 -->
	</xsl:template>
</xsl:stylesheet>