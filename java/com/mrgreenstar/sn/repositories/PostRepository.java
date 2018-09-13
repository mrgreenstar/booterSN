package com.mrgreenstar.sn.repositories;

import com.mrgreenstar.sn.Entity.Post;
import com.mrgreenstar.sn.Entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PostRepository extends CrudRepository<Post, Long> {
    ArrayList<Post> findPostsByUserId(Long userId);
}
