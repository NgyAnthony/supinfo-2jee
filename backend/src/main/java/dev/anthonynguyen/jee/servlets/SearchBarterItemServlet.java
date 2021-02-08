package dev.anthonynguyen.jee.servlets;

import com.google.gson.Gson;
import dev.anthonynguyen.jee.entities.BarterItem;
import dev.anthonynguyen.jee.entities.User;
import dev.anthonynguyen.jee.services.DataService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "SearchBarterItemServlet", urlPatterns = "/api/search-items")
public class SearchBarterItemServlet extends HttpServlet {
    @Inject
    DataService dataService;

    private Gson gson = new Gson();

    @Override
    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response) throws IOException {
        String find = request.getParameter("find");

        List<BarterItem> items = dataService.findBarterItemByName(find);
        // Remove user data from response
        for (BarterItem item : items){
            User u = new User();
            u.setFirst_name(item.getUser().getFirst_name());
            item.setUser(u);
        }
        String itemsJsonString = this.gson.toJson(items);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(itemsJsonString);
        out.flush();
    }
}
