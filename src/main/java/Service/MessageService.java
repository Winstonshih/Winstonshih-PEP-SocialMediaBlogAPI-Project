package Service;
import DAO.MessageDAO;
import Model.Message;
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
    if(m.getMessage_text().length()<=255 || m.getPosted_by()!=0)
    {
        return null;
    }
    messageDAO.insertMessage(m);
    return m;
}
}
