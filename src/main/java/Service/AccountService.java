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
  public Account login(Account account)
  {
    return this.accountDAO.selectAccount(account);
  }
  public Account registerAccount(Account account)
  {
    if(!(account.getUsername().equals("")) && account.getPassword().equals("")) )
    {
      return this.accountDAO.addAccount(account);
    }
    return null;
  }
}
