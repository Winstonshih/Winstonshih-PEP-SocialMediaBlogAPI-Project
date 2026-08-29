package Service;
import DAO.AccountDAO;
import Model.Account;
import static org.mockito.ArgumentMatchers.nullable;
public class AccountService {
  public AccountDAO accountDAO; 
  public AccountService()
  {
    accountDAO=new AccountDAO();
  }
  public AccountService(AccountDAO accountDAO)
  {
    this.accountDAO=accountDAO;
  }
  /**
   * Login social media account method.
   * @param account Social media account that will be logged into.
   * @return this.accountDAO.selectAccount(account) The persisted account if persistence is successful.
   */
  public Account login(Account account)
  {
    return this.accountDAO.selectAccount(account);
  }
  /**
   * 
   * @return 
   */
  public Account registerAccount(Account account)
  {
    if(!(account.getUsername().equals("")) && account.getPassword().length()>3)
    {
      return this.accountDAO.addAccount(account);
    }
    return null;
  }
}
