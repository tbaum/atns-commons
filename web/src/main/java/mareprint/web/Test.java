package mareprint.web;

import com.thoughtworks.xstream.XStream;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tbaum
 * @since 29.06.2009 10:38:57
 */
public class Test implements Serializable {
    // ------------------------------ FIELDS ------------------------------


    private Map<String, Object> data = new HashMap<String, Object>();
    private String name;
    private static final long serialVersionUID = -1205016665633676099L;

// --------------------------- CONSTRUCTORS ---------------------------

    public Test() {
        System.err.println("new instance");
        //       data.put("test", 3124);
//        data.put("aaa", new Object());
        name = "1234515";
    }

// --------------------- GETTER / SETTER METHODS ---------------------


    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

// --------------------------- main() method ---------------------------

    public static void main(String[] args) {
 
        Test t = new Test();
        //  e.writeObject(new JButton("124"));
        t.setName("1441");
        //  t.getData().put("12354","5555") ;
        //  t.getData().put("14234232354","5555") ;

        final Map<String, Object> d = new HashMap<String, Object>();

        t.data.put("12354", "öäü5555");
        t.data.put("14234232354", "5555");
        System.err.println(t.data);

        encode(t);
    }

    private static void encode(final Test t) {
        ByteArrayOutputStream v = new ByteArrayOutputStream();
        java.beans.XMLEncoder e = new java.beans.XMLEncoder(new BufferedOutputStream(v));

        e.writeObject(t);
        e.close();
        System.err.println(new String(v.toByteArray()));


        XStream xstream = new XStream();

        String xml = xstream.toXML(t);


        Test t1 = (Test) xstream.fromXML(xml);

        String xml1 = xstream.toXML(t1);
        System.err.println(xml1);

    }
}
