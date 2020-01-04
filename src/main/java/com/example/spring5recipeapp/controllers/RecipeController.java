package com.example.spring5recipeapp.controllers;

import com.example.spring5recipeapp.commands.RecipeCommand;
import com.example.spring5recipeapp.exceptions.NotFoundException;
import com.example.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable("id") String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/show";
    }

    // this will handle the GET method for the form (its initial loading)
    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable("id") String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    // @ModelAttribute annotation is saying to Spring to bind the form post parameters to the RecipeCommand object
    // @RequestMapping(name = "recipe", method = RequestMethod.POST) // this is the same
    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedOrUpdatedCommand = recipeService.saveRecipeCommand(command);

        // this will tell Spring to redirect to specific URL
        return "redirect:/recipe/" +  + savedOrUpdatedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteRecipeById(@PathVariable("id") String id) {
        log.debug("Deleting id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        //
        return "redirect:/";
    }

    // @ExceptionHandler - we are saying that we are using NotFoundException class
    @ExceptionHandler(NotFoundException.class)
    // normally this method takes higher precedence than the exception class
    // so we have to "override" it (in order to run successful the test RecipeControllerTest.testGetRecipeNotFound)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling Not Found Exception");
        log.error(exception.getMessage());

        ModelAndView mav = new ModelAndView();
        // set the view name
        mav.setViewName("404error");
        mav.addObject("exception", exception);

        return mav;
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNumberFormatException(Exception exception) {
        log.error("Handling Number Format Exception");
        log.error(exception.getMessage());

        ModelAndView mav = new ModelAndView();
        // set the view name
        mav.setViewName("400error");
        mav.addObject("exception", exception);

        return mav;
    }
}
