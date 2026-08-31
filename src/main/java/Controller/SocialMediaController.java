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
 * This is a class that defines endpoints and handlers for a social media controller. 
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    /**
     * No params constructor that instantiates AccountService and MessageService objects that will be used to construct 
     * eendpoints for REST API.
     */
    public SocialMediaController()
    {
        this.accountService= new AccountService();
        this.messageService=new MessageService();
    }
    /**
     * Method to build and configure Javalin Social Media app by registering every route for each Social Media function.
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
        app.get("/accounts/{account_id}/messages", this::retrieveAllMessagesByUserHandler);
        return app;
    }

    /**
     * This is a register account handler for a POST endpoint. It returns 400 if new account cannot be registered.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account a = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.registerAccount(a);
        if(addedAccount!=null){
            context.json(mapper.writeValueAsString(addedAccount));
        }else{
            context.status(400);
        }
    }
    /**
     * This is a login account handler for a POST endpoint. It returns 401 if account cannot be logged in.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
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
    /**
     * This is a process new message handler for a POST endpoint. It returns 400 if new message cannot be created.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void newMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message m = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(m);
        if(addedMessage!=null){
            context.json(mapper.writeValueAsString(addedMessage));
        }else{
            context.status(400);
        }
    }
    /**
     * This is a get all messages handler for a GET endpoint. It returns 200 if all messages can not be returned.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        List<Message> message = messageService.getAllMessages();
        if(message!=null){
            context.json(mapper.writeValueAsString(message));
        }else{
            context.status(200);
        }
    }
    /**
     * This is a get message by id account handler for a GET endpoint. If a message cannot be retrieved by its id, status code is
     * 200.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getOneMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id=Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageByID(message_id);
        if(message!=null){
            context.json(mapper.writeValueAsString(message));
        }else{
            context.status(200);
        }
    }
    /**
     * This is a delete message handler for a DELETE endpoint. 200 is status code that is returned if message cannot be removed.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id=Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessageByID(message_id);
        if(message!=null){
            context.json(mapper.writeValueAsString(message));
        }else{
            context.status(200);
        }
    }
    /**
     * This is a patch message by message id handler for a PATCH endpoint. It returns 400 if message cannot be patched.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void patchMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message m = mapper.readValue(context.body(), Message.class);
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message patchedMessage= messageService.updateMessage(message_id, m.getMessage_text());
        if(patchedMessage == null){
            context.status(400);
        }else{
            context.json(patchedMessage);
        }
    }
    /**
     * This is a get all messages from user handler for a GET endpoint. It returns 200 if all messages from a specific user can
     * not be retrieved.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void retrieveAllMessagesByUserHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int user_id=Integer.parseInt(context.pathParam("account_id"));
        List<Message> message = messageService.getAllMessagesByUser(user_id);
        if(message!=null){
            context.json(mapper.writeValueAsString(message));
        }else{
            context.status(200);
        }
    }
}