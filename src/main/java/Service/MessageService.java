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
  public Message deleteMessageByID(int iD)
  {
    return this.messageDAO.deleteMessage(iD);
  }
  public Message updateMessage(int id, Message m)
  {
    if(messageDAO.getMessageById(id)==null ||m.getMessage_text().equals("")||m.getMessage_text().length()>255)
    {
        return null;
    }
    messageDAO.getMessageById(id).setMessage_text(m.getMessage_text());
    return messageDAO.updateMessage(id, m);
  }
}
