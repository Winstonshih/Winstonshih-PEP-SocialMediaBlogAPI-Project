package Service;
import DAO.AccountDAO;
import Model.Account;
public class AccountService {
  public AccountDAO accountDAO; 
  /**
   * No parameters constructor for AccountService.
   */
  public AccountService()
  {
    accountDAO=new AccountDAO();
  }
  /**
   * Parametrized constructor for instantiating a AccountDAO instance.
   * @param accountDAO instance of AccountDAO class
   */
  public AccountService(AccountDAO accountDAO)
  {
    this.accountDAO=accountDAO;
  }
  /**
   * Login method that checks to see if an account is in Account ArrayList and returns persisted account if successful.
   * @param account Social media account that will be logged into.
   * @return The persisted account if persistence is successful or null if not.
   */
  public Account login(Account account)
  {
    return this.accountDAO.selectAccount(account);
  }
  /**
   * Register method checks to see if username and password is valid before persisting account.
   * @return Persisted account if persistenc is successful or null if not successful.
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
