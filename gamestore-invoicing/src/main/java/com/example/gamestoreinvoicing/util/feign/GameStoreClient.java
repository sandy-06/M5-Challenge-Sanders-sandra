package com.example.gamestoreinvoicing.util.feign;
import com.example.gamestoreinvoicing.model.Console;
import com.example.gamestoreinvoicing.model.Game;
import com.example.gamestoreinvoicing.model.TShirt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Console;
import java.util.List;

@FeignClient(name = "gamestore-catalog")
public interface GameStoreClient {
    @RequestMapping(value = "/t-shirts", method = RequestMethod.GET)
    public String getTShirts(@PathVariable List<TShirt> tShirts);

    @RequestMapping(value = "/t-shirts/size/{size}", method = RequestMethod.GET)
    public List<TShirt>  getTShirtsBySize(@PathVariable String size);

    @RequestMapping(value = "/t-shirts/color/{color}", method = RequestMethod.GET)
    public List<TShirt> getTShirtsByColor(@PathVariable String color);

    @RequestMapping(value = "/t-Shirts/{id}", method = RequestMethod.GET)
    public TShirt getTShirtsById(@PathVariable Long id);

    @RequestMapping(value = "/consoles", method = RequestMethod.GET)
    public String getConsoles(@PathVariable List<Console> consoles);

    @RequestMapping(value = "/consoles/manufacturer/{manufacturer}", method = RequestMethod.GET)
    public List<Console> getConsoleByManufacturer(@PathVariable String manufacturer);

    @RequestMapping(value = "/consoles/model/{model}", method = RequestMethod.GET)
    public List<Console> getConsolesByModel(@PathVariable String model);

    @RequestMapping(value = "/consoles/{id}", method = RequestMethod.GET)
    public Console getConsoleById(@PathVariable Long id);

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public String getGames(@PathVariable List<Game> games);

    @RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
    public Game getGameById(@PathVariable Long id);

    @RequestMapping(value = "/games/title/{title}", method = RequestMethod.GET)
    public List<Game> getGamesByTitle(@PathVariable String title);

    @RequestMapping(value = "/games/erbRating", method = RequestMethod.GET)
    public List<Game> getGamesByErbRating(@PathVariable String erbRating);
}
