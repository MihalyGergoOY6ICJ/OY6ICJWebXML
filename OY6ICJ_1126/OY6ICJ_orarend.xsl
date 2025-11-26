<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:math="http://www.w3.org/2005/xpath-functions/math"
    exclude-result-prefixes="xs math"
    version="3.0">
    
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <title>Hallgatók adatai</title>
                <style>
                    table, th, td {
                    border: 1px solid black;
                    border-collapse: collapse;
                    padding: 6px;
                    }
                    th { background-color: #e0e0e0; }
                </style>
            </head>
            <body>
                <h2>Mihály Gergő Órarend – 2025. I. félév.</h2>
                
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Típus</th>
                        <th>Tárgy</th>
                        <th>Időpont</th>
                        <th>Helyszín</th>
                        <th>Oktató</th>
                        <th>Szak</th>
                    </tr>
                    
                    <xsl:for-each select="oy6icj_orarend/ora">
                        <tr>
                            <td><xsl:value-of select="@id"/></td>
                            <td><xsl:value-of select="@tipus"/></td>
                            <td><xsl:value-of select="targy"/></td>
                            <td><xsl:value-of select="idopont/nap"/>, <xsl:value-of select="idopont/tol"/>-<xsl:value-of select="idopont/ig"/></td>
                            <td><xsl:value-of select="helyszin"/></td>
                            <td><xsl:value-of select="oktato"/></td>
                            <td><xsl:value-of select="szak"/></td>
                        </tr>
                    </xsl:for-each>
                    
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>