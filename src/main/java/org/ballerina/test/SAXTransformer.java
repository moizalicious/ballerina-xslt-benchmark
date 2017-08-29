package org.ballerina.test;

import org.ballerina.state.Sources;
import org.openjdk.jmh.annotations.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SAXTransformer {

    private static final Logger logger = Logger.getLogger(SAXTransformer.class.getName());

    @Benchmark @BenchmarkMode(Mode.All) @OutputTimeUnit(TimeUnit.MILLISECONDS) @Threads(1)
    public void transform(Sources sources) {
        try {
            SAXSource xmlSource = sources.omXML.getSAXSource(true);
            SAXSource xslSource = sources.omXSL.getSAXSource(true);

            StringWriter stringWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(stringWriter);

            Transformer transformer = SAXTransformerFactory.newInstance().newTransformer(xslSource);
            transformer.setOutputProperty("omit-xml-declaration", "yes");
            transformer.transform(xmlSource, streamResult);
        } catch (TransformerConfigurationException e) {
            logger.log(Level.SEVERE, "TransformerConfigurationException Occurred", e);
        } catch (TransformerException e) {
            logger.log(Level.SEVERE, "TransformerException Occurred", e);
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Error Occured", e);
        }
    }

}
