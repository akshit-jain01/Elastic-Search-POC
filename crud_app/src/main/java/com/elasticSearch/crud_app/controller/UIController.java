package com.elasticSearch.crud_app.controller;

import com.elasticSearch.crud_app.entity.Product;
import com.elasticSearch.crud_app.repository.ElasticSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class UIController {

    @Autowired
    private ElasticSearchRepository elasticRepository;

    @GetMapping("/")
    public String viewHomePage(Model model) throws IOException {
        model.addAttribute("listProductDocuments", elasticRepository.searchAllDocuments());
        return "index";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product) throws IOException {
        elasticRepository.createOrUpdateDocument(product);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") String id, Model model) throws IOException {

        Product product = elasticRepository.getDocumentById(id);
        model.addAttribute("product", product);
        return "updateProductDocument";
    }

    @GetMapping("/showNewProductForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Product product = new Product();
        model.addAttribute("product", product);
        return "newProductDocument";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value = "id") String id) throws IOException {

        this.elasticRepository.deleteDocumentById(id);
        return "redirect:/";
    }
}
