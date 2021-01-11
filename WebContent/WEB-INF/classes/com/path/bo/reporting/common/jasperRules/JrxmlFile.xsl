<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:java="http://xml.apache.org/xslt/java">
	<xsl:output method="xml" version="1.0" encoding="UTF-8"/>
	<xsl:include href="JrxmlStyles.xsl"/>
    <xsl:include href="JrxmlSubDataset.xsl"/>
	<xsl:template match="report">
		
		<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" 
			name="{@name}" orientation="{@orientation}"
			pageWidth="{@pageWidth}"
			pageHeight="{@pageHeight}"
			columnWidth="{@columnWidth}" 
			leftMargin="20" rightMargin="20" topMargin="20" bottomMargin="20" whenNoDataType="AllSectionsNoDetail">
			
        <xsl:call-template name="jrxml-styles-include"></xsl:call-template>
        <xsl:call-template name="jrxml-subdataset-include"></xsl:call-template>
		
		<xsl:variable name="titleRepeated">
			<xsl:value-of select="@titleRepeated"/>
		</xsl:variable>	
			
			<xsl:for-each select="table/tbody/tr[@id='root_title']">
				<pageHeader>
					<band height="{@height_jr}" splitType="Stretch">
						<xsl:if test="$titleRepeated = 'false'">
							<printWhenExpression><![CDATA[$V{PAGE_NUMBER}==1]]></printWhenExpression>
						</xsl:if>
							
						<xsl:for-each select=".//input">
							<xsl:call-template name="jrxml-input-include">
							</xsl:call-template>
						</xsl:for-each>
					  	
					 	<xsl:for-each select=".//img">
					 		
					 		 <xsl:choose>
						        <xsl:when test="@name = 'subReportImg'">
						       		 <xsl:call-template name="subRepImg-include">
					 				 </xsl:call-template>
						        </xsl:when>
						        <xsl:otherwise>
						             <xsl:call-template name="img-include">
					 				 </xsl:call-template>
						        </xsl:otherwise>
						      </xsl:choose>
					 		
					 	</xsl:for-each>
					 	

						<xsl:apply-templates select=".//hr">
						</xsl:apply-templates>
						
						<xsl:for-each select=".//div[@available]">
							<xsl:copy-of select="div/*/."></xsl:copy-of>
						</xsl:for-each>
					</band>
				</pageHeader>
			</xsl:for-each>
			
			<xsl:for-each select="table/tbody/tr[@id='root_detail']">
				<detail>
					<band height="{@height_jr}" splitType="Stretch">
						<xsl:for-each select="./td/p">
							<xsl:for-each select=".//input">
								<xsl:call-template name="jrxml-input-include">
								</xsl:call-template>
							</xsl:for-each>
						</xsl:for-each>
						
					 	<xsl:for-each select=".//img">
					 	 <xsl:choose>
					        <xsl:when test="@name = 'subReportImg'">
					       		 <xsl:call-template name="subRepImg-include">
				 				 </xsl:call-template>
					        </xsl:when>
					        <xsl:otherwise>
					             <xsl:call-template name="img-include">
				 				 </xsl:call-template>
					        </xsl:otherwise>
					      </xsl:choose>
					 	</xsl:for-each>
							
						<xsl:apply-templates select=".//hr">
						</xsl:apply-templates>
						
						<xsl:apply-templates select=".//table">							
						</xsl:apply-templates>
						
						<xsl:for-each select=".//div[@available]">
							<xsl:copy-of select="div/*/."></xsl:copy-of>
						</xsl:for-each>
					</band>
				</detail>
			</xsl:for-each>

			<xsl:for-each select="table/tbody/tr[@id='root_pageFooter']">
				<pageFooter>
					<band height="{@height_jr}" splitType="Stretch">
						<xsl:for-each select=".//input">
							<xsl:call-template name="jrxml-input-include">
							</xsl:call-template>
						</xsl:for-each>

					 	<xsl:for-each select=".//img">
					 		<xsl:choose>
						        <xsl:when test="@name = 'subReportImg'">
						       		 <xsl:call-template name="subRepImg-include">
					 				 </xsl:call-template>
						        </xsl:when>
						        <xsl:otherwise>
						             <xsl:call-template name="img-include">
					 				 </xsl:call-template>
						        </xsl:otherwise>
						      </xsl:choose>
					 	</xsl:for-each>
						
						<xsl:apply-templates select=".//hr">
						</xsl:apply-templates>
						
						<xsl:for-each select=".//div[@available]">
							<xsl:copy-of select="div/*/."></xsl:copy-of>
						</xsl:for-each>
					</band>
				</pageFooter>					
			</xsl:for-each>	 
			
			<xsl:for-each select="table/tbody/tr[@id='root_summary']">
				<xsl:copy-of select="td/div/*/."></xsl:copy-of>
			</xsl:for-each>	
	 
	</jasperReport> 	
	
	</xsl:template>
	
	<xsl:template match="hr">
		<line>
			<reportElement x="{@x}" y="{@y}" width="{@width_jr}" height="{@height_jr}" />
		</line>
	</xsl:template>
	
	<xsl:template match="table">
		<xsl:variable name="subdatasetName">
			<xsl:value-of select="@subdataset"/>
		</xsl:variable>	
		<componentElement>
		<reportElement key="table 2" x="{@x}" y="{@y}" width="{@width_jr}" height="{@height_jr}"/>
		<jr:table xmlns:jr="http://jasperreports.sourceforge.net/jasperreports/components" whenNoDataType="AllSectionsNoDetail">
		<xsl:attribute name="xsi:schemaLocation">http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd</xsl:attribute>
			<datasetRun subDataset="{$subdatasetName}">
				<xsl:copy-of select="ancestor::report/subDataset[@name = $subdatasetName]/datasetParameter"></xsl:copy-of>
				<connectionExpression><![CDATA[$P{REPORT_CONNECTION}]]></connectionExpression>
			</datasetRun>
			
			<xsl:for-each select="tbody/tr[@id='header']/td">
				<xsl:variable name="pos"><xsl:value-of select="position()"></xsl:value-of></xsl:variable>
				<jr:column width="{@width_jr}">
					<xsl:if test="../../../tbody/tr[@id='footer']/td">
						<jr:tableFooter height="{../../../tbody/tr[@id='footer']/td/@height_jr}">
							<xsl:for-each select="../../../tbody/tr[@id='footer']/td[position() = $pos]/.//input">
								<xsl:call-template name="jrxml-input-include">
								</xsl:call-template>
							</xsl:for-each>
							
							
							<xsl:for-each select="../../../tbody/tr[@id='footer']/td[position() = $pos]/.//img">
								<xsl:call-template name="img-include">
					 			</xsl:call-template>
							</xsl:for-each>
					
							
							
						</jr:tableFooter>
					</xsl:if>
					
					<xsl:for-each select="../../../tbody/tr[@expression]">
						<xsl:if test="./td">
							<jr:groupHeader>
							<xsl:attribute name="groupName">
								<xsl:value-of select="@expression"></xsl:value-of>
							</xsl:attribute>
								<jr:cell height="{./td/@height_jr}" rowSpan="1">
								<xsl:for-each select="./td[position() = $pos]/.//input">
									<xsl:call-template name="jrxml-input-include">
									</xsl:call-template>
								</xsl:for-each>
								</jr:cell>
							</jr:groupHeader>
						</xsl:if>
					</xsl:for-each>
					<jr:columnHeader height="{@height_jr}">
						<xsl:for-each select=".//input">
							<xsl:call-template name="jrxml-input-include">
							</xsl:call-template>
						</xsl:for-each>
						
						<xsl:for-each select=".//img">
					 		<xsl:call-template name="img-include">
					 		</xsl:call-template>
					 	</xsl:for-each>
					</jr:columnHeader>
										
					<xsl:if test="../../../tbody/tr[contains(@id , '_detail')]/td">
						<jr:detailCell height="{../../../tbody/tr[contains(@id , '_detail')]/td/@height_jr}">
							<xsl:for-each select="../../../tbody/tr[contains(@id , '_detail')]/td[position() = $pos]/.//input">
								<xsl:call-template name="jrxml-input-include">
								</xsl:call-template>
							</xsl:for-each>
							
							<xsl:for-each select="../../../tbody/tr[contains(@id , '_detail')]/td[position() = $pos]/.//img">
								<xsl:call-template name="img-include">
						 		</xsl:call-template>
							</xsl:for-each>
							
						</jr:detailCell>
					</xsl:if>
				</jr:column>
			</xsl:for-each>			
					
		</jr:table>
		</componentElement>
	</xsl:template>
	
</xsl:stylesheet>