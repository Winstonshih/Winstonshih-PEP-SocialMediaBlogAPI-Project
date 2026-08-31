package DAO;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
public class AccountDAO {
    /**
     * Method to insert a registered account into database.
     * @param account account instance that will be added to database.
     * @return new account object with its account id, username, and password or null if unable to register new account.
     */
  public Account addAccount(Account account){
    // Establishes connection with SQL database.
    Connection connection = ConnectionUtil.getConnection();
    try {
        // Insert account query.
        String sql = "insert into account (username, password) values (?, ?);" ;
        // Creates a PreparedStatement object that avoids SQL injections and retieves auto-generate account_id key.
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        // Binds username to first placeholder.
        preparedStatement.setString(1, account.getUsername());
        // Binds password to second placeholder.
        preparedStatement.setString(2, account.getPassword());
        // Executes insert query.
        preparedStatement.executeUpdate();
        // Retrieves ResultSet that has matched auto-generated account id key from insert query.
        ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
         // If account id key was returned, cursor goes to first row in pkeyResultSet
        if(pkeyResultSet.next()){
            // Auto generates account id.
            int generated_account_id = (int) pkeyResultSet.getLong(1);
            // Returns account object, its auto generated account id, username, and password in response body.
            return new Account(generated_account_id, account.getUsername(), account.getPassword());
        }
    }catch(SQLException e){ // Catches exceptions if user registration fails.
        System.out.println(e.getMessage());
    }
    // Returns null if registration fails.
    return null;
  }
  /**
   * Method to login into a specific account using a select query.
   * @param account account that will be logged into.
   * @return matched account if found with its account id, username, or password or null if not found.
   */
  public Account selectAccount(Account account)
  {
    // Establishes connection with SQL database.
    Connection connection = ConnectionUtil.getConnection();
    try {
        // Stores select account to login query.
        String sql = "select * from account where username=? and password=?;";
        // Creates a PreparedStatement object that avoids SQL injections.
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // Binds username to first placeholder.
        preparedStatement.setString(1, account.getUsername());
        // Binds password to second placeholder.
        preparedStatement.setString(2, account.getPassword());
        // Results of query is stored in ResultSet.
        ResultSet rs = preparedStatement.executeQuery();
        // If account is found in ResultSet, then account object, its id, username, and password will be returned.
        while(rs.next()){
            Account a = new Account(rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));
            return a;
        }
    }catch(SQLException e){ // Catches exceptions when logging in to account.
        System.out.println(e.getMessage());
    }
    // Returns null if account cannot be logged in.
    return null;
  } 
}
