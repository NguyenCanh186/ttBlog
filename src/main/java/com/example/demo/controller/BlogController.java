package com.example.demo.controller;

import com.example.demo.model.Blog;
import com.example.demo.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private IBlogService blogService;

//    @GetMapping("/list")
//    public ResponseEntity<Iterable<Blog>> ShowAllBlog(){
//        return new ResponseEntity<>(blogService.findAll(), HttpStatus.OK);
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<Blog> saveBlog(@RequestBody Blog blog){
//        return new ResponseEntity<>(blogService.save(blog), HttpStatus.CREATED);
//    }
//
//    @DeleteMapping("delete/{id}")
//    public ResponseEntity<Blog> deleteBlog(@PathVariable Long id) {
//        Optional<Blog> blogOptional = blogService.findById(id);
//        if (!blogOptional.isPresent()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        blogService.remove(id);
//        return new ResponseEntity<>(blogOptional.get(), HttpStatus.NO_CONTENT);
//    }
//
//
//    @PutMapping ("/edit/{id}")
//    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @RequestBody Blog blog){
//        blog.setId(id);
//        return new ResponseEntity<>(blogService.save(blog), HttpStatus.OK);
//    }
//
//    @GetMapping("findOne/{id}")
//    public ResponseEntity<Blog> findOne(@PathVariable Long id){
//        Blog blog = blogService.findById(id).get();
//        return new ResponseEntity<>(blog,HttpStatus.OK);
//    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("blog", new Blog());
        return "create";
    }

    @PostMapping("/create")
    public ModelAndView saveBlog(@ModelAttribute("blog") Blog blog) {
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("blog", new Blog());
        modelAndView.addObject("message", "New blog created successfully");
        return modelAndView;
    }

    @GetMapping("/blogs")
    public String listBlogs(Model model) {
//        ModelAndView modelAndView = new ModelAndView("/list");
//        modelAndView.addObject("blogs", blogService.findAll());
        model.addAttribute("blogs", blogService.findAll());
        return "list";
//        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        if (blog.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/edit");
            modelAndView.addObject("blog", blog.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error-404");
        }
    }

    @PostMapping("/edit")
    public ModelAndView updateBlog(@ModelAttribute("blog") Blog blog) {
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("blog", blog);
        modelAndView.addObject("message", "Blog updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        if (blog.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/delete");
            modelAndView.addObject("blog", blog.get());
            return modelAndView;

        } else {
            return new ModelAndView("/error-404");
        }
    }

    @PostMapping("/delete")
    public String deleteBlog(@ModelAttribute("blog") Blog blog) {
        blogService.remove(blog.getId());
        return "redirect:blogs";
    }
}
