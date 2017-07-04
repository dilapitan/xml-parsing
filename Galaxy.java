import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import javax.xml.xpath.*;
import org.xml.sax.InputSource; 
import org.w3c.dom.Attr;
import java.util.*;

class Galaxy {
	public static void main(String args[]) {

		ArrayList<String> nameContainer = new ArrayList<String>();	// name attributes under <test>
		ArrayList<String> valueContainer = new ArrayList<String>();	// value attributes under <test>

		try {

			// source: https://stackoverflow.com/questions/2460592/xpath-how-to-get-all-the-attribute-names-and-values-of-an-element
			
			// set up
			XPathFactory xpf = XPathFactory.newInstance();
			XPath xpath = xpf.newXPath();
			
			// file reading
			InputSource f = new InputSource("/home/dilapitan/Desktop/test.xml");
	       
	       	// using xpath to get to <param> via traversing the nodes
	        NodeList nl = (NodeList) xpath.evaluate("/tool/tests/test/param/@*", f, XPathConstants.NODESET);
	        
	       	// traversing the <param> under <test> and retrieving its attributes
	        int nlLength = nl.getLength();
	        for (int i = 0; i < nlLength; i++) {
	        	Attr at = (Attr) nl.item(i);
	        	String name = at.getName();
	        	String value = at.getValue();

	        	nameContainer.add(name);
	        	valueContainer.add(value);
	        }

	        System.out.println("name container: " + nameContainer);
	        System.out.println("value container: " + valueContainer);

 		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}