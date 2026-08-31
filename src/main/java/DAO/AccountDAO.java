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
    Connection connection = ConnectionUtil.getConnection();
    try {
        String sql = "insert into account (username, password) values (?, ?);" ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());
        preparedStatement.executeUpdate();
        ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
        if(pkeyResultSet.next()){
            int generated_account_id = (int) pkeyResultSet.getLong(1);
            return new Account(generated_account_id, account.getUsername(), account.getPassword());
        }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
  }
  /**
   * Method to login into a specific account using a select query.
   * @param account account that will be logged into.
   * @return matched account if found with its account id, username, or password or null if not found.
   */
  public Account selectAccount(Account account)
  {
    Connection connection = ConnectionUtil.getConnection();
    try {
        String sql = "select * from account where username=? and password=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Account a = new Account(rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));
            return a;
        }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
  } 
}
