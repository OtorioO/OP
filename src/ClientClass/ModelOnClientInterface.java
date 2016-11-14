package ClientClass;

/**
 * Created by Mihail on 14.11.16.
 */


public interface ModelOnClientInterface
{
    void findContact(Contact contact);
    void getListContact();
    void getListDialog(Contact contact);
    void loginMe(String login,String password);
    void registration(Contact contact);
    void sendMessage(Message message);



    void regGetListContactListener(GetListContactListener listener);
    void regGetListDialogListener(GetListDialogListener listener);
    void regRegistrationListener(RegistrationListener listener);
    void regLoginMeListener(LoginListener listener);
}