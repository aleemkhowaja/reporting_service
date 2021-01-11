<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:jasper="http://jasperreports.sourceforge.net/jasperreports">
    <xsl:output method="xml" indent="yes"
        cdata-section-elements="jasper:text jasper:textFieldExpression jasper:imageExpression" />
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|*|text()" />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="jasper:band">
        <xsl:copy>
            <xsl:apply-templates select="@*" />
            <xsl:apply-templates select="*[name()='printWhenExpression']" />
            <xsl:apply-templates select="*[name()!='printWhenExpression']">
                <xsl:sort 
                    select="jasper:reportElement/@y * 10000 + jasper:reportElement/@x"
                    data-type="number" order="ascending" />
            </xsl:apply-templates>
        </xsl:copy>
    </xsl:template>
    
</xsl:stylesheet>