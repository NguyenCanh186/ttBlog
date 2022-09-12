package com.example.demo.controller;

import com.example.demo.model.Blog;
import com.example.demo.model.BlogForm;
import com.example.demo.model.Category;
import com.example.demo.service.IBlogService;
import com.example.demo.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/blogs")
@CrossOrigin("*")
public class BlogController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    Environment env;

    @GetMapping("/cate")
    public  ResponseEntity<Iterable<Category>> showAllCate(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @ModelAttribute("categories")
    private Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("blog", new BlogForm());
        return modelAndView;
    }

//    @GetMapping("/create")
//    public String showCreateForm(Model model) {
//        model.addAttribute("blog", new BlogForm());
//        return "create";
//    }

    @PostMapping("/create")
    public ResponseEntity<Blog> saveBlog(@RequestBody BlogForm blogForm) {
        MultipartFile multipartFile = blogForm.getCover();
        String fileName =multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("upload.path");
        try {
            FileCopyUtils.copy(blogForm.getCover().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Blog blog = new Blog( blogForm.getTitle(), fileName, blogForm.getContent(), blogForm.getCategory());
        blogService.save(blog);
        return new ResponseEntity<>(blog,HttpStatus.ACCEPTED);
    }

    @GetMapping("/list")
    public String listBlogs(Model model) {
        model.addAttribute("blogs", blogService.findAll());
        return "list";

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
