package mareprint.web.client.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tbaum
 * @since 27.06.2009 16:03:47
 */
public class ServerUploadStatus implements Serializable {
// ------------------------------ FIELDS ------------------------------

    public static final String SESSION_ATTR_NAME = "serverStatus";
    private static final long serialVersionUID = 477264872675096271L;

    private final Set<UploadItemStatus> items = new HashSet<UploadItemStatus>();

// -------------------------- OTHER METHODS --------------------------

    public UploadItemStatus createItem(final String fileName, final String contentType) {
        UploadItemStatus item = new UploadItemStatus(fileName, contentType);
        items.add(item);
        return item;
    }

    public UploadItemStatus getByName(String name) {
        for (UploadItemStatus item : items) {
            if (item.matches(name)) return item;
        }
        return null;
    }

    public String toString() {
        return ""+items.size()+" files \n"+items;
    }
}
