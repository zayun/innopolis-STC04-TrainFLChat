package controllers;

import models.pojo.Message;
import service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by smoldyrev on 25.02.17.
 * Приватная комната чата
 */
public class PrivateChatRoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String chatroom;
        if (req.getParameter("chatroomin") != null) {
            chatroom = req.getParameter("chatroomin");
        } else {
            chatroom = req.getParameter("chatroom");
        }

        List<Message> messages = MessageService.getAllInRoom(Integer.parseInt(chatroom));

        req.setAttribute("messages", messages);
        req.setAttribute("userFrom",req.getSession().getAttribute("idd"));


        req.setAttribute("chatroom", chatroom);

        req.getRequestDispatcher("/rooms/privatechatroom.jsp").forward(req, resp);
    }
}
