package org.example;

import java.util.List;

public interface BlogMapper {

    List<Blog> selectBlog();

    Blog selectBlogById(Integer id);
}
