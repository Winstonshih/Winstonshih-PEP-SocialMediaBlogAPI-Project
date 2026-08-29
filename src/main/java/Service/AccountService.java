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
  public Account addAccount(Account a) {
    return accountDAO.insertAccount(a);
  }
  public Account login(Account account)
  {
    return this.accountDAO.selectAccount(account);
  }
  public Account newMessage()
  {
    
  }
}
