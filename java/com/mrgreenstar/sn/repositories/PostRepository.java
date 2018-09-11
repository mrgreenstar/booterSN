package com.mrgreenstar.sn.repositories;

import com.mrgreenstar.sn.Entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PostRepository extends CrudRepository<Post, Long> {
    String findContentByUserId(int userId);
    ArrayList<Post> findPostsByUserId(int userId);
}
