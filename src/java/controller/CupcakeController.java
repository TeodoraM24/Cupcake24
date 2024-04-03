package controller;

import entities.Cupcake;
import exceptions.DatabaseException;
import io.javalin.Javalin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import persistence.ConnectionPool;
import persistence.CupcakeMapper;
import service.CupcakeService;

import java.util.List;

@Controller
public class CupcakeController {

    private CupcakeService cupcakeService;

    public CupcakeController(CupcakeService cupcakeService) {
        this.cupcakeService = cupcakeService;
    }

    @GetMapping("/cupcakes")
    public String getCupcakes(Model model) throws DatabaseException {
        List<Cupcake> cupcakes = cupcakeService.getCupcakes();
        model.addAttribute("cupcakes", cupcakes);
        return "cupcake-form";
    }

}


