package com.example.gamestoreinvoicing.util.feign;
import com.example.gamestoreinvoicing.model.Console;
import com.example.gamestoreinvoicing.model.Game;
import com.example.gamestoreinvoicing.model.TShirt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;

@FeignClient(name = "gamestore-catalog")
public interface GameStoreClient {
    @RequestMapping(value = "/tshirt", method = RequestMethod.GET)
    public List<TShirt> getTShirt(@PathVariable TShirt tShirt);

    @RequestMapping(value = "/tshirt/size/{size}", method = RequestMethod.GET)
    public List<TShirt>  getTShirtsBySize(@PathVariable String size);

    @RequestMapping(value = "/tshirt/color/{color}", method = RequestMethod.GET)
    public List<TShirt> getTShirtsByColor(@PathVariable String color);

    @RequestMapping(value = "/tshirt/{id}", method = RequestMethod.GET)
    public TShirt getTShirtsById(@PathVariable Long id);

    @RequestMapping(value = "/console", method = RequestMethod.GET)
    public String getConsole(@PathVariable List<Console> consoles);

    @RequestMapping(value = "/console/manufacturer/{manufacturer}", method = RequestMethod.GET)
    public List<Console> getConsoleByManufacturer(@PathVariable String manufacturer);

    @RequestMapping(value = "/console/model/{model}", method = RequestMethod.GET)
    public List<Console> getConsoleByModel(@PathVariable String model);

    @RequestMapping(value = "/console/{id}", method = RequestMethod.GET)
    public Console getConsoleById(@PathVariable Long id);

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public String getGame(@PathVariable List<Game> games);

    @RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
    public Game getGameById(@PathVariable Game id);

    @RequestMapping(value = "/game/title/{title}", method = RequestMethod.GET)
    public List<Game> getGameByTitle(@PathVariable String title);

    @RequestMapping(value = "/game/erbRating", method = RequestMethod.GET)
    public List<Game> getGameByErbRating(@PathVariable String erbRating);
}
