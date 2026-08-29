package Controller;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController()
    {
        this.accountService= new AccountService();
        this.messageService=new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::newMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getOneMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/messages/{message_id}/messages", this::retrieveAllMessageHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account a = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(a);
        if(addedAccount!=null){
            context.json(mapper.writeValueAsString(addedAccount));
        }else{
            context.status(400);
        }
    }
    private void loginAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account a = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.login(a);
        if(addedAccount!=null){
            context.json(mapper.writeValueAsString(addedAccount));
        }else{
            context.status(401);
        }
    }
    private void newMessageHandler(Context context) {
        context.json("sample text");
    }
    private void getAllMessagesHandler(Context context) {
        context.json("sample text");
    }
    private void getOneMessageHandler(Context context) {
        context.json("sample text");
    }
    private void deleteMessageHandler(Context context) {
        context.json("sample text");
    }
    private void patchMessageHandler(Context context) {
        context.json("sample text");
    }
    private void retrieveAllMessageHandler(Context context) {
        context.json("sample text");
    }
}