package org.example;

import java.util.List;

public interface StudentMapper {

    List<Student> selectOrderedStudent(String column);
}
