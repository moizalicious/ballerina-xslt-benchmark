package org.ballerina.state;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.openjdk.jmh.annotations.*;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

@State(Scope.Benchmark)
public class Sources {

    private static final Logger logger = Logger.getLogger(Sources.class.getName());

    public OMElement omXML;
    public OMElement omXSL;

    @Setup(Level.Trial)
    public void setup() {
        try {
            String xml = new String(Files.readAllBytes(Paths.get("xml-files/small.xml")));
            String xsl = new String(Files.readAllBytes(Paths.get("xml-files/stylesheet.xml")));
            omXML = AXIOMUtil.stringToOM(xml);
            omXSL = AXIOMUtil.stringToOM(xsl);
        } catch (IOException e) {
            logger.log(java.util.logging.Level.SEVERE, "IOException Occurred", e);
        } catch (XMLStreamException e) {
            logger.log(java.util.logging.Level.SEVERE, "XMLStreamException Occurred", e);
        } catch (Throwable e) {
            logger.log(java.util.logging.Level.SEVERE, "Error Occurred", e);
        }
    }

    @TearDown(Level.Trial)
    public void teardown() {
        omXML = null;
        omXSL = null;
    }

}
