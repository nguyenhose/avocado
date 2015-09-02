<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : avcXSL.xsl
    Created on : August 15, 2015, 3:19 PM
    Author     : nguyen
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>  

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="leftPage" page-height='8.5in'
                                       page-width='11in' margin-top='0.5in'
                                       margin-left='1in' margin-right='1in'>
                    <fo:region-body margin-top='0.5in'/>
                    <fo:region-before extent='1in'/>
                    <fo:region-after extent='.75in'/>
                </fo:simple-page-master>
                <fo:simple-page-master master-name="rightPage" page-height='8.5in'
                                       page-width='11in' margin-top='0.5in'
                                       margin-left='1in' margin-right='1in'>
                    <fo:region-body margin-top='0.5in'/>
                    <fo:region-before extent='1in'/>
                    <fo:region-after extent='.75in'/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence-master master-reference="contents">
                <fo:repeatable-page-master-alternatives>
                    <fo:conditional-page-master-reference
                        master-name="leftPage"
                        odd-or-even="even"/>
                    <fo:conditional-page-master-reference
                        master-name="rightPage"
                        odd-or-even="odd"/>
                </fo:repeatable-page-master-alternatives>
            </fo:page-sequence-master>
            
            <fo:page-sequence master-name='leftPage'> 
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block font-size='14pt'>
                        Avocado
                    </fo:block>
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block>
                        End
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block>
                        <xsl:for-each select="albums/album">
                            <xsl:for-each select ="word">
                                <fo:block >
                                    <fo:inline font-weight='bold' baseline-shift='50%'>
                                        <xsl:value-of select="name" line-height='24pt'/>
                                    </fo:inline>
                                </fo:block>
                            </xsl:for-each>
                        </xsl:for-each>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence> 
            <fo:page-sequence master-name='rightPage'> 
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block font-size='14pt'>
                        Avocado
                    </fo:block>
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block>
                        End
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block>
                        <xsl:for-each select="albums/album">
                            <xsl:for-each select ="word">
                                <fo:block >
                                    <fo:inline font-weight='bold' baseline-shift='50%'>
                                        <xsl:value-of select="definition" line-height='24pt'/>
                                    </fo:inline>
                                </fo:block>
                            </xsl:for-each>
                        </xsl:for-each>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>  
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
