package com.example.spring5recipeapp.controllers;

import com.example.spring5recipeapp.commands.RecipeCommand;
import com.example.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable("id") String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/show";
    }

    @GetMapping
    // this will handle the GET method for the form (its initial loading)
    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable("id") String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    // @ModelAttribute annotation is saying to Spring to bind the form post parameters to the RecipeCommand object
    // @RequestMapping(name = "recipe", method = RequestMethod.POST) // this is the same
    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedOrUpdatedCommand = recipeService.saveRecipeCommand(command);

        // this will tell Spring to redirect to specific URL
        return "redirect:/recipe/" +  + savedOrUpdatedCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteRecipeById(@PathVariable("id") String id) {
        log.debug("Deleting id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        //
        return "redirect:/";
    }
}
