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
   * 
   * @param m
   * @return
   */
  public Message addMessage(Message m) {
    if(m.getMessage_text().length()<=255 && m.getPosted_by()!=0&&!(m.getMessage_text().equals("")))
    {
        return this.messageDAO.insertMessage(m);
    }
    return null;
  }
  /**
   * 
   * @return
   */
  public List<Message> getAllMessages()
  {
    return this.messageDAO.getAllMessages();
  }
  /**
   * 
   * @param id
   * @return
   */
  public Message getMessageByID(int id)
  {
    return this.messageDAO.getMessageById(id);
  }
  /**
   * 
   * @param id
   * @return
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
   * @return null if message is not valid or updated message instance if valid.
   */
  public Message updateMessage(int id, String m)
  {
    if(m==null||m.isEmpty()||m.length()>255||messageDAO.getMessageById(id)==null)
    {
        return null;
    }
    messageDAO.updateMessage(id, m);
    return messageDAO.getMessageById(id);
  }
  /**
   * 
   * @param user_id
   * @return
   */
  public List<Message> getAllMessagesByUser(int user_id)
  {
    return this.messageDAO.getAllMessagesByUser(user_id);
  }
}
