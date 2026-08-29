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
        Account addedAccount = accountService.registerAccount(a);
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
    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        List<Message> message = messageService.getAllMessages();
        if(message!=null){
            context.json(mapper.writeValueAsString(message));
        }else{
            context.status(200);
        }
    }
    private void getOneMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id=Integer.parseInt(context.pathParam("message_id"));
        List<Message> message = messageService.getMessageByID(message_id);
        if(message!=null){
            context.json(mapper.writeValueAsString(message));
        }else{
            context.status(200);
        }
    }
    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id=Integer.parseInt(context.pathParam("message_id"));
        List<Message> message = messageService.deleteMessageByID(message_id);
        if(message!=null){
            context.json(mapper.writeValueAsString(message));
        }else{
            context.status(200);
        }
    }
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
    private void retrieveAllMessageHandler(Context context) throws JsonProcessingException{
        context.json("sample text");
    }
}