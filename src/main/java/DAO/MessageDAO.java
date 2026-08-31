package DAO;
import Model.Message;
import Service.MessageService;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MessageDAO {
    /**
     * Method  to retrieve all messages through a Message ArrayList.
     * @return messages arraylist if all messages could be retrieved or null if DAO is unable to.
     */
  public List<Message> getAllMessages(){
    // Creates a connection to database.
    Connection connection = ConnectionUtil.getConnection();
    // ArrayList that stores all messages in database.
    List<Message> messages = new ArrayList<>();
    try {
        // Stores select query for getting all messages.
        String sql = "select * from message";
        // Creates a PreparedStatement object that avoids SQL injections.
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // Stores results of query in a ResultSet object.
        ResultSet rs = preparedStatement.executeQuery();
        // Adds full messages to ArrayList while scanning ResultSet.
        while(rs.next()){
            Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            messages.add(message);
        }
        // If query can retrieve all messages in database.
        return messages;
    }catch(SQLException e){ // Catches exceptions that occur when querying all messages.
        System.out.println(e.getMessage());
    }
    // If query could not retrieve all messages, return null.
    return null;
  }
  /**
   * Method uses a select query to match all message tuples with specified message_id.
   * @param id message id
   * @return message object that matches message id if successfully matched or null if not matched.
   */
  public Message getMessageById(int id){
    Connection connection = ConnectionUtil.getConnection();
    try {
        // Query for getting all tuples that match message_id.
        String sql = "select * from message where message_id=?;";
        // Creates a PreparedStatement object that avoids SQL injections.
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // Binds message id to a placeholder.
        preparedStatement.setInt(1, id);
        //Stores results of query in a ResultSet object.
        ResultSet rs = preparedStatement.executeQuery();
        // If a message in ResultSet is matched with message id, then it is returned.
        while(rs.next()){
            Message m = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            return m;
        }
    }catch(SQLException e){ // Catches any exceptions that may occur while retrieving a message by id.
        System.out.println(e.getMessage());
    }
    // If message id cannot be matched, method returns null.
    return null;
}
/**
 * Method to insert a new message object in databasee using insert sql query.
 * @param message message object
 * @return inserted message object and its data if successfully inserted or null if not inserted.
 */
  public Message insertMessage(Message message){
    // Establishes connection with SQL database.
    Connection connection = ConnectionUtil.getConnection();
    try {
        // Stores query that will insert a new message into database.
        String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?, ?, ?);" ;
        // Creates a PreparedStatement object that avoids SQL injections and retrieves auto generated keys.
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        // Binds to first placeholder.
        preparedStatement.setInt(1, message.getPosted_by());
        // Binds to second placeholder.
        preparedStatement.setString(2, message.getMessage_text());
        // Binds to third placeholder.
        preparedStatement.setLong(3, message.getTime_posted_epoch());
        // Executes insert query.
        preparedStatement.executeUpdate();
        // Retrieves ResultSet that has matched auto-generated message id key from insert query.
        ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
        // If message id key was returned, cursor goes to first row in pkeyResultSet
        if(pkeyResultSet.next()){
            // Auto generates a message id.
            int generated_message_id = (int) pkeyResultSet.getLong(1);
            // Returns inserted message.
            return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        }
    }catch(SQLException e){ //Catches any exceptions that may occur while inserting a message.
        System.out.println(e.getMessage());
    }
    // Returns null if message can not be inserted.
    return null;
 }
 /**
  * Deletes message from database using delete sql query.
  * @param id id of message
  * @return Deleted message instance with its posted_by, message_text, time_posted, and message_id data if successful
  * or null if not successful.
  */
 public Message deleteMessage(int id){
    Connection connection = ConnectionUtil.getConnection();
    try {
        // Stores the message that will be deleted.
        Message msg=getMessageById(id);
        // Stores delete query.
        String sql = "delete from message where message_id = ?;" ;
        // Creates a PreparedStatement object that avoids SQL injections.
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // Binds id to first placeholder.
        preparedStatement.setInt(1, id);
        // Executes delete query.
        preparedStatement.executeUpdate();
        // Returns the full message that was deleted.
        return msg;
    }catch(SQLException e){ // Catches exceptions that occur when executing SQL query.
        System.out.println(e.getMessage());
    }
    // Returns null if message was not deleted.
    return null;
  }
  /**
   * This method finds a message by message_id and updates its text with update sql clause.
   * @param id message id
   * @param m message text
   * @return instance of updated message.
   */
  public Message updateMessage(int id, String m) {
    Connection connection = ConnectionUtil.getConnection();
    try {
        // Stores the update query.
        String sql = "update message set message_text=? where message_id=?;";
        // Creates a PreparedStatement object that avoids SQL injections.
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // Binds m to first placeholder.
        preparedStatement.setString(1, m);
        // Binds id to second placeholder.
        preparedStatement.setInt(2, id);
        // Executes update query.
        preparedStatement.executeUpdate();
        // Returns full updatd message for response body.
        return getMessageById(id);
    } catch (SQLException e) { //Catches exceeptions when querying database.
        System.out.println(e.getMessage());
    }
    // Returns null if messagee is not updated.
    return null;
  }
  /**
   * This method uses select clause to query all messages associated with user_id.
   * @param user_id id of user whose messages will be accessed
   * @return Message Arraylist of messages from specific user if successful or null if not
   */
  public List<Message> getAllMessagesByUser(int user_id)
  {
    // Creates ArrayList containing all messages from a specific user id.
    List<Message> messages = new ArrayList<>();
    // Establishes connection to SQL database.
    Connection connection = ConnectionUtil.getConnection();
    try {
        // SQL query to get all messages by user.
        String sql = "select * from message where posted_by=?;";
        // Creates a PreparedStatement object that avoids SQL injections.
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // Binds user_id to a placeholder.
        preparedStatement.setInt(1, user_id);
        // Stores result  of executing query in ResultSet.
        ResultSet rs = preparedStatement.executeQuery();
        // Adds full message to ArrayList as it scans ResultSet.
        while(rs.next()){
            Message m = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            messages.add(m);
        }
        // If all messages with matched user id can be found, an ArrayList is found.
        return messages;
    }catch(SQLException e){ // Catches exceptions when querying database.
        System.out.println(e.getMessage());
    }
    // If no messages match the user_id, null is returned.
    return null;
  }
  
}
