package com.github.patu11.mafiaroller;

import com.github.patu11.mafiaroller.dto.RoomDTO;
import com.github.patu11.mafiaroller.dto.UserDTO;
import com.github.patu11.mafiaroller.room.RoomService;
import com.github.patu11.mafiaroller.user.User;
import com.github.patu11.mafiaroller.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@RestController
public class GreetingController {
    
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(User user) {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(user.getUsername()) + "!");
    }

}
