package ClientClass;

/**
 * Created by Mihail on 14.11.16.
 */
import java.util.ArrayList;

public interface GetListContactListener {
    void handleEvent(ArrayList<Contact> contactArrayList);
}