package org.ballerina.state;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.openjdk.jmh.annotations.*;

import javax.xml.stream.XMLStreamException;
import java.util.logging.Logger;

@State(Scope.Benchmark)
public class Sources {

    private static final Logger logger = Logger.getLogger(Sources.class.getName());

    public OMElement omXML;
    public OMElement omXSL;

    @Setup(Level.Trial)
    public void setup() {
        try {
            omXML = AXIOMUtil.stringToOM("<product pid=\"100-201-01\">\n" +
                    "   <description>\n" +
                    "      <name>Ice Scraper, Windshield 4 inch</name>\n" +
                    "      <details>Basic Ice Scraper 4 inches wide, foam handle</details>\n" +
                    "      <price>3.99</price>\n" +
                    "   </description>\n" +
                    "</product>");
            omXSL = AXIOMUtil.stringToOM("<xsl:stylesheet version=\"1.0\"\n" +
                    "                xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                    "<xsl:output method=\"html\"/>\n" +
                    "   <xsl:template match=\"/\">\n" +
                    "       <html>\n" +
                    "            <body>\n" +
                    "                <h1><xsl:value-of select=\"/product/description/name\"/></h1>\n" +
                    "                <table border=\"1\">\n" +
                    "                        <th>\n" +
                    "       <xsl:apply-templates select=\"product\"/>\n" +
                    "                        </th>\n" +
                    "                     </table>\n" +
                    "            </body>\n" +
                    "       </html>\n" +
                    "   </xsl:template>\n" +
                    "     <xsl:template match=\"product\">\n" +
                    "        <tr>\n" +
                    "                   <td width=\"80\">product ID</td>\n" +
                    "                   <td><xsl:value-of select=\"@pid\"/></td>\n" +
                    "              </tr>\n" +
                    "              <tr>\n" +
                    "                   <td width=\"200\">product name</td>\n" +
                    "                   <td><xsl:value-of select=\"/product/description/name\"/></td>\n" +
                    "              </tr>\n" +
                    "              <tr>\n" +
                    "                   <td width=\"200\">price</td>\n" +
                    "                   <td>$<xsl:value-of select=\"/product/description/price\"/></td>\n" +
                    "              </tr>\n" +
                    "              <tr>\n" +
                    "                   <td width=\"50\">details</td>\n" +
                    "                   <td><xsl:value-of select=\"/product/description/details\"/></td>\n" +
                    "              </tr>\n" +
                    "  </xsl:template>\n" +
                    "</xsl:stylesheet>");
        } catch (XMLStreamException e) {
            logger.log(java.util.logging.Level.SEVERE, "XMLStreamException Occurred", e);
        } catch (Throwable e) {
            logger.log(java.util.logging.Level.SEVERE, "Error Occured", e);
        }
    }

    @TearDown(Level.Trial)
    public void teardown() {
        omXML = null;
        omXSL = null;
    }

}
