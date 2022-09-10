package com.example.demo.repo;

import com.example.demo.model.Blog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogRepo extends CrudRepository<Blog, Long> {
}
