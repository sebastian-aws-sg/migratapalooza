<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:import href="common/addressBook.xsl" />
    <xsl:output method="html" />

    <xsl:template match="/">
        <xsl:apply-templates select="stxx" />
    </xsl:template>

    <xsl:template match="stxx">
        <html>
            <body>
                <table width="75%" border="1" align="center">
                    <tr>
                      <td colspan="2" bgcolor="lightblue" align="center">
                      	<br/>
                        <h4><a href="./index.do"><xsl:value-of select="/stxx/applicationResources/key[@name='example.toIndex']"/></a></h4>
                   	  </td>
                    </tr>
                    <xsl:apply-templates select="addressBook" />
                </table>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>