package DAO;
import Model.Message;
import Service.MessageService;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MessageDAO {
    /**
     * Method 
     * @return
     */
  public List<Message> getAllMessages(){
    Connection connection = ConnectionUtil.getConnection();
    List<Message> messages = new ArrayList<>();
    try {
        String sql = "select * from message";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            messages.add(message);
        }
        return messages
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
  }
  /**
   * 
   * @param id
   * @return
   */
  public Message getMessageById(int id){
    Connection connection = ConnectionUtil.getConnection();
    try {
        String sql = "select * from message where message_id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message m = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            return m;
        }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
}
/**
 * 
 * @param message
 * @return
 */
  public Message insertMessage(Message message){
    Connection connection = ConnectionUtil.getConnection();
    try {
        String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?, ?, ?);" ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, message.getPosted_by());
        preparedStatement.setString(2, message.getMessage_text());
        preparedStatement.setLong(3, message.getTime_posted_epoch());
        preparedStatement.executeUpdate();
        ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
        if(pkeyResultSet.next()){
            int generated_message_id = (int) pkeyResultSet.getLong(1);
            return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
 }
 /**
  * 
  * @param id
  * @return
  */
 public Message deleteMessage(int id){
    Connection connection = ConnectionUtil.getConnection();
    try {
        Message msg=getMessageById(id);
        String sql = "delete from message where message_id = ?;" ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        return msg;
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
  }
  /**
   * 
   * @param id
   * @param m
   * @return
   */
  public Message updateMessage(int id, String m) {
    Connection connection = ConnectionUtil.getConnection();
    try {
        String sql = "update message set message_text=? where message_id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, m);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
        return getMessageById(id);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return null;
  }
  /**
   * 
   * @param user_id
   * @return
   */
  public List<Message> getAllMessagesByUser(int user_id)
  {
    List<Message> messages = new ArrayList<>();
    Connection connection = ConnectionUtil.getConnection();
    try {
        String sql = "select * from message where posted_by=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user_id);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message m = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            messages.add(m);
        }
        return messages;
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
  }
  
}
