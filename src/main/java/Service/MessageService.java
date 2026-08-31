package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.*;
public class MessageService {
  public MessageDAO messageDAO;
  /**
   * No param constructor that instantiates MessageDAO object.
   */
  public MessageService()
  {
    messageDAO=new MessageDAO();
  }
  /**
   * Parameterized constructor that instantiates MessageDAO object.
   * @param messageDAO instance of MessageDAO class
   */
  public MessageService(MessageDAO messageDAO)
  {
    this.messageDAO=messageDAO;
  }
  /**
   * Method to insert a new message.
   * @param m message instance
   * @return inserted message if valid or null if not valid.
   */
  public Message addMessage(Message m) {
    // Message is posted if test length is at most 255, posted_by value is not 0, and message text is not empty.
    if(m.getMessage_text().length()<=255 && m.getPosted_by()!=0&&!(m.getMessage_text().equals("")))
    {
        return this.messageDAO.insertMessage(m);
    }
    // Otherwises, null is returned.
    return null;
  }
  /**
   * Retrieves an ArrayList containing all messages in database.
   * @return a Message ArrayList.
   */
  public List<Message> getAllMessages()
  {
    return this.messageDAO.getAllMessages();
  }
  /**
   * Retrieves a message using message id.
   * @param id message id
   * @return a message if id is matched or null if not matched
   */
  public Message getMessageByID(int id)
  {
    return this.messageDAO.getMessageById(id);
  }
  /**
   * Method deletes messages that match a message id.
   * @param id message id
   * @return deleted message if successgul or null if not.
   */
  public Message deleteMessageByID(int id)
  {
    return this.messageDAO.deleteMessage(id);
  }
  /**
   * Method that allows message to be updated if it is not empty, is at most 255 characters, and if its posted_by 
   * refers to a real user.
   * @param id message id
   * @param m message text
   * @return null if message is not valid or updated message instance if valid ot not if not valid.
   */
  public Message updateMessage(int id, String m)
  {
    // If message is empty, has no matched id, does not exist, or os more than 255 characters, 
    if(m==null||m.isEmpty()||m.length()>255||messageDAO.getMessageById(id)==null)
    {
        // then message will not be updated and method returns null.
        return null;
    }
    // Otherwises, message is updated.
    messageDAO.updateMessage(id, m);
    // The message that is updated will be returned.
    return messageDAO.getMessageById(id);
  }
  /**
   * Method that queries database for an arraylist of messages with matched user id.
   * @param user_id user who created messages.
   * @return an arraylist containing all messages associated with user_id if valid or null ifinvalid.
   */
  public List<Message> getAllMessagesByUser(int user_id)
  {
    return this.messageDAO.getAllMessagesByUser(user_id);
  }
}
