<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:jr="http://jasperreports.sourceforge.net/jasperreports/components"
	xmlns:jasper="http://jasperreports.sourceforge.net/jasperreports"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="jasper java jr">
	<xsl:output method="html" encoding="UTF-8" indent="no"/>
    <xsl:include href="XhtmlSubDataset.xsl"/>
    <xsl:include href="XhtmlInputs.xsl"/>
	<xsl:template match="jasper:jasperReport">
		
		<xsl:variable name="titleRepeated">
			<xsl:choose>
				<xsl:when test="jasper:pageHeader/jasper:band/jasper:printWhenExpression">false</xsl:when>
				<xsl:otherwise>true</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<xsl:variable name="orientation">
			<xsl:choose>
				<xsl:when test="@orientation != null">
					<xsl:value-of select="@orientation"></xsl:value-of>
				</xsl:when>
				<xsl:otherwise>Portrait</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<report name="{@name}" orientation="{$orientation}" titleRepeated="{$titleRepeated}"
			pageWidth="{@pageWidth}" pageHeight="{@pageHeight}" columnWidth="{@columnWidth}" >
			<xsl:call-template name="xhtml-subdataset-include"></xsl:call-template>
		
			<table align="center" cellpadding="0" cellspacing="0" id="root" style="border: 1px solid #e5e5e5;" 
				width="{@pageWidth}"> 
				<tbody id="tbody" style="font-size: 9px;">
					<xsl:for-each select="jasper:pageHeader/jasper:band">
						<tr id="root_title" height="{@height}">
							<td style="BACKGROUND: url(/imal_reporting_portal/ckeditor/images/title.JPG) no-repeat center" valign="top">
								<xsl:for-each select="*[ name() = 'textField' or name() = 'image' or name() = 'subreport']">
									<xsl:variable name="yPos">
										<xsl:choose>
											<xsl:when test="position() = 1">
												<xsl:value-of select="-1"></xsl:value-of>
											</xsl:when>
											<xsl:otherwise>
												<xsl:if test="name(preceding-sibling::*[last()]) = 'textField'">
													<xsl:value-of select="(preceding-sibling::*)[last()]/jasper:reportElement/@y"></xsl:value-of>
												</xsl:if>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:variable>		
									<xsl:variable name="elemPos">
										<xsl:if test="name() = 'textField'">
											<xsl:value-of select="jasper:reportElement/@y"></xsl:value-of>
										</xsl:if>
										<xsl:if test="name() = 'image'">
											<xsl:value-of select="jasper:reportElement/@y + jasper:reportElement/@height - 12"></xsl:value-of>
										</xsl:if>
									</xsl:variable>
									<xsl:if test="$elemPos != $yPos">
										<xsl:variable name="yPos">
											<xsl:if test="name() = 'textField'">
												<xsl:value-of select="jasper:reportElement/@y"></xsl:value-of>
											</xsl:if>
											<xsl:if test="name() = 'image'">
												<xsl:value-of select="jasper:reportElement/@y + jasper:reportElement/@height - 12"></xsl:value-of>
											</xsl:if>
										</xsl:variable>
										<xsl:if test="jasper:reportElement/@y > 0 and position() > 1">
											<xsl:variable name="height">
												<xsl:choose>
													<xsl:when test="(preceding-sibling::*)">
														<xsl:value-of select="jasper:reportElement/@y - (((preceding-sibling::*)[last()])/jasper:reportElement/@y + ((preceding-sibling::*)[last()])/jasper:reportElement/@height) - 21"></xsl:value-of>
													</xsl:when>
													<xsl:otherwise>
														<xsl:value-of select="jasper:reportElement/@y - 21"></xsl:value-of>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:variable>
											<xsl:if test="$height > 0">
												 <p style="height: {$height}px;">
													<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
												</p>
											</xsl:if>
										</xsl:if>
										<p>
											<xsl:for-each select="..//jasper:textField[ jasper:reportElement/@y = $yPos ]">
												<xsl:if test="jasper:reportElement/@x > 0">
													<xsl:variable name="width">
														<xsl:choose>
															<xsl:when test="(preceding-sibling::*)">
																<xsl:value-of select="jasper:reportElement/@x - (((preceding-sibling::*)[last()])/jasper:reportElement/@x + ((preceding-sibling::*)[last()])/jasper:reportElement/@width) - 6"></xsl:value-of>
															</xsl:when>
															<xsl:otherwise>
																<xsl:value-of select="jasper:reportElement/@x - 6"></xsl:value-of>
															</xsl:otherwise>
														</xsl:choose>
													</xsl:variable>
													<xsl:if test="$width > 0">
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														<span style="display:inline-block; width: {$width}px;">
															<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														</span>
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
													</xsl:if>
												</xsl:if>
												
												<xsl:call-template name="xhtml-input-include">
							 					</xsl:call-template>
											</xsl:for-each>
											
											<xsl:for-each select="..//jasper:image[ jasper:reportElement/@y + jasper:reportElement/@height - 12 = $yPos ]">
												<xsl:if test="jasper:reportElement/@x > 0">
													<xsl:variable name="width">
														<xsl:choose>
															<xsl:when test="(preceding-sibling::*)">
																<xsl:value-of select="jasper:reportElement/@x - (((preceding-sibling::*)[last()])/jasper:reportElement/@x + ((preceding-sibling::*)[last()])/jasper:reportElement/@width) - 6"></xsl:value-of>
															</xsl:when>
															<xsl:otherwise>
																<xsl:value-of select="jasper:reportElement/@x - 6"></xsl:value-of>
															</xsl:otherwise>
														</xsl:choose>
													</xsl:variable>
													<xsl:if test="$width > 0">
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														<span style="display:inline-block; width: {$width}px;">
															<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														</span>
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
													</xsl:if>
												</xsl:if>
												
												<xsl:call-template name="xhtml-img-include">
							 					</xsl:call-template>
											</xsl:for-each>
											
											
											
										
											
											
										</p>
									</xsl:if>
								</xsl:for-each>
								
								<xsl:for-each select="..//jasper:subreport">
											<xsl:call-template name="xhtml-subRepImg-include">
						 					</xsl:call-template>
									</xsl:for-each>
								
								
								<xsl:apply-templates select=".//jasper:line">
								</xsl:apply-templates>	
								
								<xsl:for-each select="*[ name() != 'textField' and name() != 'image' and name() != 'line' and name() != 'subreport' and ./jasper:reportElement]">
									<div available="false" style="BACKGROUND: url(/imal_reporting_portal/ckeditor/images/index.jpg) no-repeat center; border: 1px solid; height: {jasper:reportElement/@height}px; left: {jasper:reportElement/@x}px; top: {jasper:reportElement/@y}px; width: {jasper:reportElement/@width}px; " x="{jasper:reportElement/@x}" y="{jasper:reportElement/@y}">
										<div hidden="true">
											<xsl:text disable-output-escaping="yes"><![CDATA[<!--]]></xsl:text>
												<xsl:copy-of select="."></xsl:copy-of>
											<xsl:text disable-output-escaping="yes"><![CDATA[-->]]></xsl:text>
										</div>
									</div>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:for-each>
					
					<xsl:for-each select="jasper:group/jasper:groupHeader">
						<tr expression="{../@name}" id="group_header_{../@name}" height="{jasper:band/@height}">
							<td style="BACKGROUND: url(/imal_reporting_portal/ckeditor/images/groupHeader.JPG) no-repeat center" valign="top">
								<xsl:for-each select=".//*[ name() = 'textField' or name() = 'image' ]">
									<xsl:variable name="yPos">
										<xsl:choose>
											<xsl:when test="position() = 1">
												<xsl:value-of select="-1"></xsl:value-of>
											</xsl:when>
											<xsl:otherwise>
												<xsl:if test="name(preceding-sibling::*[last()]) = 'textField'">
													<xsl:value-of select="(preceding-sibling::*)[last()]/jasper:reportElement/@y"></xsl:value-of>
												</xsl:if>
												<xsl:if test="name(preceding-sibling::*[last()]) = 'image'">
													<xsl:value-of select="(preceding-sibling::*)[last()]/jasper:reportElement/@y + jasper:reportElement/@height - 12"></xsl:value-of>
												</xsl:if>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:variable>
									<xsl:variable name="elemPos">
										<xsl:if test="name() = 'textField'">
											<xsl:value-of select="jasper:reportElement/@y"></xsl:value-of>
										</xsl:if>
										<xsl:if test="name() = 'image'">
											<xsl:value-of select="jasper:reportElement/@y + jasper:reportElement/@height - 12"></xsl:value-of>
										</xsl:if>
									</xsl:variable>
									<xsl:if test="$elemPos != $yPos">
										<xsl:variable name="yPos">
											<xsl:if test="name() = 'textField'">
												<xsl:value-of select="jasper:reportElement/@y"></xsl:value-of>
											</xsl:if>
											<xsl:if test="name() = 'image'">
												<xsl:value-of select="jasper:reportElement/@y + jasper:reportElement/@height - 12"></xsl:value-of>
											</xsl:if>
										</xsl:variable>
										<xsl:if test="jasper:reportElement/@y > 0 and position() > 1">
											<xsl:variable name="height">
												<xsl:choose>
													<xsl:when test="(preceding-sibling::*)">
														<xsl:value-of select="jasper:reportElement/@y - (((preceding-sibling::*)[last()])/jasper:reportElement/@y + ((preceding-sibling::*)[last()])/jasper:reportElement/@height) - 21"></xsl:value-of>
													</xsl:when>
													<xsl:otherwise>
														<xsl:value-of select="jasper:reportElement/@y - 21"></xsl:value-of>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:variable>
											<xsl:if test="$height > 0">
												 <p style="height: {$height}px;">
													<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
												</p>
											</xsl:if>
										</xsl:if>
										<p>
											<xsl:for-each select="..//jasper:textField[ jasper:reportElement/@y = $yPos ]">
												<xsl:if test="jasper:reportElement/@x > 0">
													<xsl:variable name="width">
														<xsl:choose>
															<xsl:when test="(preceding-sibling::*)">
																<xsl:value-of select="jasper:reportElement/@x - (((preceding-sibling::*)[last()])/jasper:reportElement/@x + ((preceding-sibling::*)[last()])/jasper:reportElement/@width) - 6"></xsl:value-of>
															</xsl:when>
															<xsl:otherwise>
																<xsl:value-of select="jasper:reportElement/@x - 6"></xsl:value-of>
															</xsl:otherwise>
														</xsl:choose>
													</xsl:variable>
													<xsl:if test="$width > 0">
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														<span style="display:inline-block; width: {$width}px;">
															<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														</span>
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
													</xsl:if>
												</xsl:if>
												
												<xsl:call-template name="xhtml-input-include">
							 					</xsl:call-template>
											</xsl:for-each>
											
											<xsl:for-each select="..//jasper:image[ jasper:reportElement/@y + jasper:reportElement/@height - 12 = $yPos ]">
												<xsl:if test="jasper:reportElement/@x > 0">
													<xsl:variable name="width">
														<xsl:choose>
															<xsl:when test="(preceding-sibling::*)">
																<xsl:value-of select="jasper:reportElement/@x - (((preceding-sibling::*)[last()])/jasper:reportElement/@x + ((preceding-sibling::*)[last()])/jasper:reportElement/@width) - 6"></xsl:value-of>
															</xsl:when>
															<xsl:otherwise>
																<xsl:value-of select="jasper:reportElement/@x - 6"></xsl:value-of>
															</xsl:otherwise>
														</xsl:choose>
													</xsl:variable>
													<xsl:if test="$width > 0">
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														<span style="display:inline-block; width: {$width}px;">
															<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														</span>
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
													</xsl:if>
												</xsl:if>
												
												<xsl:call-template name="xhtml-img-include">
							 					</xsl:call-template>
											</xsl:for-each>
											
										
											
											
										</p>
									</xsl:if>
								</xsl:for-each>
		
								<xsl:apply-templates select=".//jasper:line">
								</xsl:apply-templates>
								
								<xsl:for-each select="*[ name() != 'textField' and name() != 'image' and name() != 'line' and ./jasper:reportElement]">
									<div available="false" style="BACKGROUND: url(/imal_reporting_portal/ckeditor/images/index.jpg) no-repeat center; border: 1px solid; height: {jasper:reportElement/@height}px; left: {jasper:reportElement/@x}px; top: {jasper:reportElement/@y}px; width: {jasper:reportElement/@width}px; " x="{jasper:reportElement/@x}" y="{jasper:reportElement/@y}">
										<div hidden="true">
											<xsl:text disable-output-escaping="yes"><![CDATA[<!--]]></xsl:text>
												<xsl:copy-of select="."></xsl:copy-of>
											<xsl:text disable-output-escaping="yes"><![CDATA[-->]]></xsl:text>
										</div>
									</div>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:for-each>
					
					<xsl:for-each select="jasper:detail/jasper:band">
						<tr id="root_detail" height="{@height}">
							<td style="BACKGROUND: url(/imal_reporting_portal/ckeditor/images/detail.JPG) no-repeat center" valign="top">
								<p x="0" y="9"></p>
								<xsl:for-each select="*[ not(starts-with(parent[name()], 'jr:')) and ( name() = 'textField' or name() = 'image') ]">
									<xsl:variable name="yPos">
										<xsl:choose>
											<xsl:when test="position() = 1">
												<xsl:value-of select="-1"></xsl:value-of>
											</xsl:when>
											<xsl:otherwise>
												<xsl:if test="name((preceding-sibling::*[ not(starts-with(parent[name()], 'jr:')) ])[last()]) = 'textField'">
													<xsl:value-of select="(preceding-sibling::*[ not(starts-with(parent[name()], 'jr:')) ])[last()]/jasper:reportElement/@y"></xsl:value-of>
												</xsl:if>
												<xsl:if test="name((preceding-sibling::*[ not(starts-with(parent[name()], 'jr:')) ])[last()]) = 'image'">
													<xsl:value-of select="(preceding-sibling::*[ not(starts-with(parent[name()], 'jr:')) ])[last()]/jasper:reportElement/@y + jasper:reportElement/@height - 12"></xsl:value-of>
												</xsl:if>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:variable>
									<xsl:variable name="elemPos">
										<xsl:if test="name() = 'textField'">
											<xsl:value-of select="jasper:reportElement/@y"></xsl:value-of>
										</xsl:if>
										<xsl:if test="name() = 'image'">
											<xsl:value-of select="jasper:reportElement/@y + jasper:reportElement/@height - 12"></xsl:value-of>
										</xsl:if>
									</xsl:variable>
									<xsl:if test="$elemPos != $yPos">
										<xsl:variable name="yPos">
											<xsl:value-of select="$elemPos"></xsl:value-of>
										</xsl:variable>
										<xsl:if test="jasper:reportElement/@y > 0 and position() > 1">
											<xsl:variable name="height">
												<xsl:choose>
													<xsl:when test="(preceding-sibling::*[ not(starts-with(parent[name()], 'jr:')) ])">
														<xsl:value-of select="jasper:reportElement/@y - (((preceding-sibling::*[ not(starts-with(parent[name()], 'jr:')) ])[last()])/jasper:reportElement/@y + ((preceding-sibling::*[ not(starts-with(parent[name()], 'jr:')) ])[last()])/jasper:reportElement/@height) - 21"></xsl:value-of>
													</xsl:when>
													<xsl:otherwise>
														<xsl:value-of select="jasper:reportElement/@y - 21"></xsl:value-of>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:variable>
											<xsl:if test="$height > 0">
												 <p style="height: {$height}px;">
													<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
												</p>
											</xsl:if>
										</xsl:if>
										<p>
											<xsl:for-each select="..//jasper:textField[ not(starts-with(parent[name()], 'jr:')) and jasper:reportElement/@y = $yPos]">
												<xsl:if test="jasper:reportElement/@x > 0">
													<xsl:variable name="width">
														<xsl:choose>
															<xsl:when test="(preceding-sibling::*[ not(starts-with(parent[name()], 'jr:')) ])">
																<xsl:value-of select="jasper:reportElement/@x - (((preceding-sibling::*[ not(starts-with(parent[name()], 'jr:')) ])[last()])/jasper:reportElement/@x + ((preceding-sibling::*[ not(starts-with(parent[name()], 'jr:')) ])[last()])/jasper:reportElement/@width) - 6"></xsl:value-of>
															</xsl:when>
															<xsl:otherwise>
																<xsl:value-of select="jasper:reportElement/@x - 6"></xsl:value-of>
															</xsl:otherwise>
														</xsl:choose>
													</xsl:variable>
													<xsl:if test="$width > 0">
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														<span style="display:inline-block; width: {$width}px;">
															<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														</span>
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
													</xsl:if>
												</xsl:if>
												
												<xsl:call-template name="xhtml-input-include">
							 					</xsl:call-template>
											</xsl:for-each>
											
											<xsl:for-each select="..//jasper:image[ not(starts-with(parent[name()], 'jr:')) and (jasper:reportElement/@y + jasper:reportElement/@height - 12 = $yPos) ]">
												<xsl:if test="jasper:reportElement/@x > 0">
													<xsl:variable name="width">
														<xsl:choose>
															<xsl:when test="(preceding-sibling::*[ not(starts-with(parent[name()], 'jr:')) ])">
																<xsl:value-of select="jasper:reportElement/@x - (((preceding-sibling::*[ not(starts-with(parent[name()], 'jr:')) ])[last()])/jasper:reportElement/@x + ((preceding-sibling::*[ not(starts-with(parent[name()], 'jr:')) ])[last()])/jasper:reportElement/@width) - 6"></xsl:value-of>
															</xsl:when>
															<xsl:otherwise>
																<xsl:value-of select="jasper:reportElement/@x - 6"></xsl:value-of>
															</xsl:otherwise>
														</xsl:choose>
													</xsl:variable>
													<xsl:if test="$width > 0">
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														<span style="display:inline-block; width: {$width}px;">
															<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														</span>
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
													</xsl:if>
												</xsl:if>
												
												<xsl:call-template name="xhtml-img-include">
							 					</xsl:call-template>
											</xsl:for-each>
										</p>
									</xsl:if>
								</xsl:for-each>
								
								<xsl:apply-templates select=".//jasper:line">
								</xsl:apply-templates>
								
								<xsl:apply-templates select=".//jr:table">							
								</xsl:apply-templates>
								
								<xsl:for-each select="..//jasper:subreport">
									<xsl:call-template name="xhtml-subRepImg-include">
							 		</xsl:call-template>
								</xsl:for-each>	
								
								<xsl:for-each select="*[ name() != 'textField' and name() != 'image' and name() != 'line' and name() != 'componentElement' and name() != 'subreport' and ./jasper:reportElement]">
									<div available="false" style="BACKGROUND: url(/imal_reporting_portal/ckeditor/images/index.jpg) no-repeat center; border: 1px solid; height: {jasper:reportElement/@height}px; left: {jasper:reportElement/@x}px; top: {jasper:reportElement/@y}px; width: {jasper:reportElement/@width}px; " x="{jasper:reportElement/@x}" y="{jasper:reportElement/@y}">
										<div hidden="true">
											<xsl:text disable-output-escaping="yes"><![CDATA[<!--]]></xsl:text>
												<xsl:copy-of select="."></xsl:copy-of>
											<xsl:text disable-output-escaping="yes"><![CDATA[-->]]></xsl:text>
										</div>
									</div>
								</xsl:for-each>
								<p x="0" y="103"></p>
							</td>
						</tr>
						
						
						
					</xsl:for-each>
					
					<xsl:for-each select="jasper:group/jasper:groupFooter">
						<tr expression="{../@name}" id="group_footer_{../@name}" height="{jasper:band/@height}">
						  <td style="BACKGROUND: url(/imal_reporting_portal/ckeditor/images/index.jpg) no-repeat center; border: 1px solid;">
   						  	<div hidden="true" x="{jasper:reportElement/@x}" y="{jasper:reportElement/@y}">
								<xsl:text disable-output-escaping="yes"><![CDATA[<!--]]></xsl:text>
									<xsl:copy-of select="."></xsl:copy-of>
								<xsl:text disable-output-escaping="yes"><![CDATA[-->]]></xsl:text>
							</div>	
						  </td>
						</tr>
					</xsl:for-each>
					
					<xsl:for-each select="jasper:pageFooter/jasper:band">
						<tr id="root_pageFooter" height="{@height}">
							<td style="BACKGROUND: url(/imal_reporting_portal/ckeditor/images/footer.JPG) no-repeat center" valign="top">
								<xsl:for-each select="*[ name() = 'textField' or name() = 'image' ]">
									<xsl:variable name="yPos">
										<xsl:choose>
											<xsl:when test="position() = 1">
												<xsl:value-of select="-1"></xsl:value-of>
											</xsl:when>
											<xsl:otherwise>
												<xsl:if test="name(preceding-sibling::*[last()]) = 'textField'">
													<xsl:value-of select="(preceding-sibling::*)[last()]/jasper:reportElement/@y"></xsl:value-of>
												</xsl:if>
												<xsl:if test="name(preceding-sibling::*[last()]) = 'image'">
													<xsl:value-of select="(preceding-sibling::*)[last()]/jasper:reportElement/@y + jasper:reportElement/@height - 12"></xsl:value-of>
												</xsl:if>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:variable>
									<xsl:variable name="elemPos">
										<xsl:if test="name() = 'textField'">
											<xsl:value-of select="jasper:reportElement/@y"></xsl:value-of>
										</xsl:if>
										<xsl:if test="name() = 'image'">
											<xsl:value-of select="jasper:reportElement/@y + jasper:reportElement/@height - 12"></xsl:value-of>
										</xsl:if>
									</xsl:variable>									
									<xsl:if test="$elemPos != $yPos">
										<xsl:variable name="yPos">
											<xsl:if test="name() = 'textField'">
												<xsl:value-of select="jasper:reportElement/@y"></xsl:value-of>
											</xsl:if>
											<xsl:if test="name() = 'image'">
												<xsl:value-of select="jasper:reportElement/@y + jasper:reportElement/@height - 12"></xsl:value-of>
											</xsl:if>
										</xsl:variable>
										<xsl:if test="jasper:reportElement/@y > 0 and position() > 1">
											<xsl:variable name="height">
												<xsl:choose>
													<xsl:when test="(preceding-sibling::*)">
														<xsl:value-of select="jasper:reportElement/@y - (((preceding-sibling::*)[last()])/jasper:reportElement/@y + ((preceding-sibling::*)[last()])/jasper:reportElement/@height) - 21"></xsl:value-of>
													</xsl:when>
													<xsl:otherwise>
														<xsl:value-of select="jasper:reportElement/@y - 21"></xsl:value-of>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:variable>
											<xsl:if test="$height > 0">
												 <p style="height: {$height}px;">
													<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
												</p>
											</xsl:if>
										</xsl:if>
										<p>
											<xsl:for-each select="..//jasper:textField[ jasper:reportElement/@y = $yPos ]">
												<xsl:if test="jasper:reportElement/@x > 0">
													<xsl:variable name="width">
														<xsl:choose>
															<xsl:when test="(preceding-sibling::*)">
																<xsl:value-of select="jasper:reportElement/@x - (((preceding-sibling::*)[last()])/jasper:reportElement/@x + ((preceding-sibling::*)[last()])/jasper:reportElement/@width) - 6"></xsl:value-of>
															</xsl:when>
															<xsl:otherwise>
																<xsl:value-of select="jasper:reportElement/@x - 6"></xsl:value-of>
															</xsl:otherwise>
														</xsl:choose>
													</xsl:variable>
													<xsl:if test="$width > 0">
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														<span style="display:inline-block; width: {$width}px;">
															<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														</span>
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
													</xsl:if>
												</xsl:if>
												
												<xsl:call-template name="xhtml-input-include">
							 					</xsl:call-template>
											</xsl:for-each>
											
											<xsl:for-each select="..//jasper:image[ jasper:reportElement/@y + jasper:reportElement/@height - 12 = $yPos ]">
												<xsl:if test="jasper:reportElement/@x > 0">
													<xsl:variable name="width">
														<xsl:choose>
															<xsl:when test="(preceding-sibling::*)">
																<xsl:value-of select="jasper:reportElement/@x - (((preceding-sibling::*)[last()])/jasper:reportElement/@x + ((preceding-sibling::*)[last()])/jasper:reportElement/@width) - 6"></xsl:value-of>
															</xsl:when>
															<xsl:otherwise>
																<xsl:value-of select="jasper:reportElement/@x - 6"></xsl:value-of>
															</xsl:otherwise>
														</xsl:choose>
													</xsl:variable>
													<xsl:if test="$width > 0">
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														<span style="display:inline-block; width: {$width}px;">
															<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
														</span>
														<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
													</xsl:if>
												</xsl:if>
												
												<xsl:call-template name="xhtml-img-include">
							 					</xsl:call-template>
											</xsl:for-each>
											
										</p>
									</xsl:if>
								</xsl:for-each>
								
								<xsl:for-each select="..//jasper:subreport">
									<xsl:call-template name="xhtml-subRepImg-include">
				 					</xsl:call-template>
								</xsl:for-each>
												
								<xsl:apply-templates select=".//jasper:line">
								</xsl:apply-templates>
								
								<xsl:for-each select="*[ name() != 'textField' and name() != 'image'  and name() != 'subreport' and name() != 'line' and ./jasper:reportElement]">
									<div available="false" style="BACKGROUND: url(/imal_reporting_portal/ckeditor/images/index.jpg) no-repeat center; border: 1px solid; height: {jasper:reportElement/@height}px; left: {jasper:reportElement/@x}px; top: {jasper:reportElement/@y}px; width: {jasper:reportElement/@width}px; " x="{jasper:reportElement/@x}" y="{jasper:reportElement/@y}">
										<div hidden="true">
											<xsl:text disable-output-escaping="yes"><![CDATA[<!--]]></xsl:text>
												<xsl:copy-of select="."></xsl:copy-of>
											<xsl:text disable-output-escaping="yes"><![CDATA[-->]]></xsl:text>
										</div>
									</div>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:for-each>	
					
					<xsl:for-each select="jasper:summary/jasper:band">
						<tr id="root_summary" height="{@height}">
						  <td style="BACKGROUND: url(/imal_reporting_portal/ckeditor/images/index.jpg) no-repeat center; border: 1px solid;">
   						  	<div hidden="true" x="{jasper:reportElement/@x}" y="{jasper:reportElement/@y}">
								<xsl:text disable-output-escaping="yes"><![CDATA[<!--]]></xsl:text>
									<xsl:copy-of select=".."></xsl:copy-of>
								<xsl:text disable-output-escaping="yes"><![CDATA[-->]]></xsl:text>
							</div>	
						  </td>
						</tr>
					</xsl:for-each> 
					
				</tbody>
			</table>
		</report>
	</xsl:template>
	
	<xsl:template match="jasper:line">
		<hr x="{jasper:reportElement/@x}" y="{jasper:reportElement/@y}" width="{jasper:reportElement/@width}" 
			height="{jasper:reportElement/@height}">
			<xsl:value-of select="java:com.path.bo.reporting.designer.jasperrules.XslInput.returnInnerHTML()"></xsl:value-of>
		</hr>
	</xsl:template>
	
	<xsl:template match="jr:table">
		<xsl:variable name="subdatasetName">
			<xsl:value-of select="jasper:datasetRun/@subDataset"/>
		</xsl:variable>	
		<table border="1" cellpadding="1" cellspacing="1" id="{$subdatasetName}" subdataset="{$subdatasetName}"
			height="{preceding-sibling::jasper:reportElement/@height}" width="{preceding-sibling::jasper:reportElement/@width}"
			x="{preceding-sibling::jasper:reportElement/@x}" y="{preceding-sibling::jasper:reportElement/@y}">
			<xsl:if test="./jr:column">
				<xsl:variable name="colWidth">
					<xsl:value-of select="./jr:column/@width"/>
				</xsl:variable>
				<tbody>
					<xsl:if test="./jr:column/jr:columnHeader">
						<xsl:variable name="headerHeight">
							<xsl:value-of select="./jr:column/jr:columnHeader/@height"/>
						</xsl:variable>
						<tr id="header" height="{$headerHeight}">
							<xsl:for-each select="./jr:column/jr:columnHeader">
								<td height="{$headerHeight}"  width="{$colWidth}" scope="col">
									<xsl:for-each select=".//jasper:textField">
										<xsl:call-template name="xhtml-input-include">
										</xsl:call-template>
									</xsl:for-each>

									<xsl:for-each select=".//jasper:image">
								 		<xsl:call-template name="xhtml-img-include">
									 		</xsl:call-template>
									 	</xsl:for-each>
								</td>
							</xsl:for-each>
						</tr>			
					</xsl:if>
					<xsl:if test="./jr:column/jr:groupHeader">
						<xsl:variable name="groupHeight">
							<xsl:value-of select="./jr:column/jr:groupHeader/@height"/>
						</xsl:variable>
						<xsl:variable name="groupName">
							<xsl:value-of select="./jr:column/jr:groupHeader/@groupName"/>
						</xsl:variable>
						<tr height="{$groupHeight}" id="group_header_{$groupName}" expression="{$groupName}">
							<xsl:for-each select="./jr:column/jr:groupHeader">
								<td height="{$groupHeight}" width="{$colWidth}" bgColor="#F0F8FF">
									<xsl:for-each select=".//jasper:textField">
										<xsl:call-template name="xhtml-input-include">
										</xsl:call-template>
									</xsl:for-each>
								</td>
							</xsl:for-each>
						</tr>
					</xsl:if>	
					<xsl:if test="./jr:column/jr:detailCell">
						<xsl:variable name="detailHeight">
							<xsl:value-of select="./jr:column/jr:detailCell/@height"/>
						</xsl:variable>
						<tr height="{$detailHeight}" id="{$subdatasetName}_detail">
							<xsl:for-each select="./jr:column/jr:detailCell">
								<td height="{$detailHeight}" width="{$colWidth}">
									<xsl:for-each select=".//jasper:textField">
										<xsl:call-template name="xhtml-input-include">
										</xsl:call-template>
									</xsl:for-each>
		
									<xsl:for-each select=".//jasper:image">
								 		<xsl:call-template name="xhtml-img-include">
								 		</xsl:call-template>
								 	</xsl:for-each>
								</td>
							</xsl:for-each>
						</tr>
					</xsl:if>
					<xsl:if test="./jr:column/jr:tableFooter">
						<xsl:variable name="footerHeight">
							<xsl:value-of select="./jr:column/jr:tableFooter/@height"/>
						</xsl:variable>					
						<tr id="footer" height="{$footerHeight}">
							<xsl:for-each select="./jr:column/jr:tableFooter">
								<td style="background: url(/imal_reporting_portal/ckeditor/images/total.JPG) no-repeat scroll center transparent; text-align: center;"  height="{$footerHeight}" width="{$colWidth}">
									<xsl:for-each select=".//jasper:textField">
										<xsl:call-template name="xhtml-input-include">
										</xsl:call-template>
									</xsl:for-each>
	
									<xsl:for-each select=".//jasper:image">
								 		<xsl:call-template name="xhtml-img-include">
								 		</xsl:call-template>
								 	</xsl:for-each>
								</td>
							</xsl:for-each>
						</tr>				
					</xsl:if>				
				</tbody>
			</xsl:if>					
		</table>
	</xsl:template>
	
</xsl:stylesheet>