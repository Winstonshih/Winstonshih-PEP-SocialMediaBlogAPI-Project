package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.*;
public class MessageService {
  public MessageDAO messageDAO;
  public MessageService()
  {
    messageDAO=new MessageDAO();
  }
  public MessageService(MessageDAO messageDAO)
  {
    this.messageDAO=new MessageDAO();
  }
  public Message addMessage(Message m) {
    if(m.getMessage_text().length()<=255 && m.getPosted_by()!=0&&!(m.getMessage_text().equals("")))
    {
        return this.messageDAO.insertMessage(m);
    }
    return null;
  }
  public List<Message> getAllMessages()
  {
    return messageDAO.getAllMessages();
  }
  public Message getMessageByID(int id)
  {
    return this.messageDAO.getMessageById(id);
  }
  public Message deleteMessageByID(int id)
  {
    return this.messageDAO.deleteMessage(id);
  }
  public Message updateMessage(int id, String m)
  {
    if(m==null||m.isEmpty()||m.length()>255||messageDAO.getMessageById(id)==null)
    {
        return null;
    }
    messageDAO.updateMessage(id, m);
    return messageDAO.getMessageById(id);
  }
}
