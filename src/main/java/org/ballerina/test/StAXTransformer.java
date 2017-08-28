package org.ballerina.test;

import org.ballerina.state.Sources;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StAXTransformer {

    private static final Logger logger = Logger.getLogger(StAXTransformer.class.getName());

//    @Benchmark @BenchmarkMode(Mode.All) @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void transform(Sources sources) {
        try {
            StAXSource xmlSource = new StAXSource(sources.omXML.getXMLStreamReader());
            StAXSource xslSource = new StAXSource(sources.omXSL.getXMLStreamReader());

            StringWriter stringWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(stringWriter);

            Transformer transformer = TransformerFactory.newInstance().newTransformer(xslSource);
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
