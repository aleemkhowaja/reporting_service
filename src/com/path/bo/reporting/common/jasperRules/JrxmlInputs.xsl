<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:java="http://xml.apache.org/xslt/java">
	<xsl:output method="xml" version="1.0" encoding="UTF-8"/>

	<xsl:template name="jrxml-input-include">
		<xsl:if test="@field = 'true' or @param = 'true'">
			<textField isStretchWithOverflow="true" isBlankWhenNull="true">
				<xsl:if test="@pattern">
					<xsl:attribute name="pattern">
						<xsl:value-of select="@pattern"></xsl:value-of>
					</xsl:attribute>
				</xsl:if>
				<xsl:call-template name="css">
				</xsl:call-template>	
															
				<textFieldExpression class="{@class}">
					<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnJrxmlTextValue(@value)" disable-output-escaping="yes"></xsl:value-of>
				</textFieldExpression>
			</textField>
		</xsl:if>
		
		<xsl:if test="@label = 'true'">
			<textField isStretchWithOverflow="true">
				<xsl:call-template name="css">
				</xsl:call-template>
																
				<textFieldExpression class="java.lang.String">
					<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnJrxmlStaticValue(@value)" disable-output-escaping="yes"></xsl:value-of>	
				</textFieldExpression>
			</textField>
		</xsl:if>
						    	
		<xsl:if test="@name = 'pageNbr'">		
			<textField isStretchWithOverflow="true">
				<xsl:call-template name="css">
				</xsl:call-template>
				
				<textFieldExpression class="java.lang.String"><![CDATA["Page "+$V{PAGE_NUMBER}+" of"]]></textFieldExpression>	
			</textField>
		</xsl:if>
		
		<xsl:if test="@name = 'pageCnt'">		
			<textField isStretchWithOverflow="true" evaluationTime="Report">
				<xsl:call-template name="css">
				</xsl:call-template>
				<textFieldExpression class="java.lang.String"><![CDATA[" " + $V{PAGE_NUMBER}]]></textFieldExpression>
			</textField>
		</xsl:if>
	</xsl:template>
	
	
	
	
		<xsl:template name="subRepImg-include">
		<xsl:variable name="expr">
			<xsl:value-of select="@expr"></xsl:value-of>
		</xsl:variable>	
		
		<subreport>
			<xsl:apply-templates select=".">
				<xsl:with-param name="height">
					<xsl:value-of select="./@height_jr"></xsl:value-of>
				</xsl:with-param>
				<xsl:with-param name="width">
					<xsl:value-of select="./@width_jr"></xsl:value-of>
				</xsl:with-param>							
				<xsl:with-param name="x">
					<xsl:value-of select="./@x"></xsl:value-of>
				</xsl:with-param>							
				<xsl:with-param name="y">
					<xsl:value-of select="./@y"></xsl:value-of>
				</xsl:with-param>							
			</xsl:apply-templates>
		  <xsl:copy-of select="ancestor::report/subreport[@expression = $expr]/subreportParameter"></xsl:copy-of>	
		  <connectionExpression><![CDATA[$P{REPORT_CONNECTION}]]></connectionExpression>
		  <subreportExpression class="java.lang.String"><![CDATA[$P{SUBREPORT_DIR} + "]]><xsl:value-of select="$expr"></xsl:value-of><![CDATA[.jasper"]]></subreportExpression>
		  													
		</subreport>
	</xsl:template>
	
	
	
	
	
	<xsl:template name="img-include">
		<image>
			<xsl:apply-templates select=".">
				<xsl:with-param name="height">
					<xsl:value-of select="./@height_jr"></xsl:value-of>
				</xsl:with-param>
				<xsl:with-param name="width">
					<xsl:value-of select="./@width_jr"></xsl:value-of>
				</xsl:with-param>							
				<xsl:with-param name="x">
					<xsl:value-of select="./@x"></xsl:value-of>
				</xsl:with-param>							
				<xsl:with-param name="y">
					<xsl:value-of select="./@y"></xsl:value-of>
				</xsl:with-param>							
			</xsl:apply-templates>
			<imageExpression class="java.lang.String">
				<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnJrxmlImagePath(./@src)" disable-output-escaping="yes"></xsl:value-of>
			</imageExpression>
		</image>
	</xsl:template>
	
	
	
	
	
	<xsl:template name="css">
		<reportElement x="{@x}" y="{@y}" width="{@width_jr}" height="{@height_jr}">
			<xsl:for-each select="ancestor::font/@*">				
				<xsl:variable name="fontColor">
					<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnJrxmlColorName(name())"/>
				</xsl:variable>
				<xsl:attribute name="{$fontColor}">
					<xsl:value-of select="."></xsl:value-of>
				</xsl:attribute>	
			</xsl:for-each>
		</reportElement>
		
		<xsl:if test="@border">
			<box>
				<pen lineWidth="{@border}"/>
				<topPen lineWidth="{@border}"/>
				<leftPen lineWidth="{@border}"/>
				<bottomPen lineWidth="{@border}"/>
				<rightPen lineWidth="{@border}"/>
			</box>
		</xsl:if>
		
		<textElement>
		
		<xsl:choose>
			<xsl:when test="ancestor::p">
				<xsl:if test="ancestor::p[position() = 1]/@style != ''">					
					<xsl:call-template name="output-tokens">
	     				<xsl:with-param name="list" select="ancestor::p[position() = 1]/@style"/>
	    			</xsl:call-template>	
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="ancestor::th">
						<xsl:if test="ancestor::th[position() = 1]/@style != ''">	
							<xsl:call-template name="output-tokens">
				     			<xsl:with-param name="list" select="ancestor::th[position() = 1]/@style"/>
				    		</xsl:call-template>
				    	</xsl:if>
					</xsl:when>
					<xsl:otherwise>
						<xsl:if test="ancestor::td[position() = 1]/@style != ''">
							<xsl:call-template name="output-tokens">
				     			<xsl:with-param name="list" select="ancestor::td[position() = 1]/@style"/>
				    		</xsl:call-template>
				    	</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
			
			<font fontName="Arial" pdfFontName="arial.ttf" isPdfEmbedded="true" pdfEncoding="Identity-H">
				<xsl:for-each select="ancestor::*">
					<xsl:if test="name() = 'u'">
						<xsl:attribute name="isUnderline">true</xsl:attribute>
					</xsl:if>
					<xsl:if test="name() = 'em'">
						<xsl:attribute name="isItalic">true</xsl:attribute>
						</xsl:if>
					<xsl:if test="name() = 'strong'">
						<xsl:attribute name="isBold">true</xsl:attribute>
					</xsl:if>
					<xsl:if test="name() = 'strike'">
						<xsl:attribute name="isStrikeThrough">true</xsl:attribute>
					</xsl:if>
				</xsl:for-each>
										
				<xsl:for-each select="ancestor::span/@*">
					<xsl:call-template name="output-tokens">
	     				<xsl:with-param name="list" select="."/>
	    			</xsl:call-template>
				</xsl:for-each>
            </font>
		</textElement>
	</xsl:template>
	
	<xsl:template name="output-tokens">
	    <xsl:param name="list"/>
	    <xsl:variable name="first" select="substring-before($list, ';')" /> 
	    <xsl:variable name="remaining" select="substring-after($list, ';')" /> 
	   
	    <xsl:if test="$first">
	    	<xsl:variable name="attName">
		    	<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnJrxmlStyleName($first)"></xsl:value-of>
		    </xsl:variable>
		    <xsl:if test="$attName != 'width' and $attName != 'margin-left' and $attName != 'border' and $attName != 'background'">
				<xsl:attribute name="{$attName}">
					<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnJrxmlStyleValue($first)"></xsl:value-of>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="$attName = 'fontName'">
				<xsl:attribute name="pdfFontName">
					<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnJrxmlPdfFont($first)"></xsl:value-of>
				</xsl:attribute>
			</xsl:if>
	    </xsl:if>
	    
	    <xsl:if test="$remaining">
	        <xsl:call-template name="output-tokens">
	        	<xsl:with-param name="list" select="$remaining" /> 
	        </xsl:call-template>
	    </xsl:if>    
	</xsl:template>
	
	<xsl:template match="img">
		<xsl:param name="height"/>
		<xsl:param name="width"/>
		<xsl:param name="x"/>
		<xsl:param name="y"/>
	<reportElement x="{$x}" y="{$y}" width="{$width}" height="{$height}"/>
	</xsl:template>
</xsl:stylesheet>	