package com.mpt.journal.repository;

import com.mpt.journal.model.StudentModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Repository

//Репозиторий отвечает за хранение и управление данными студентов в памяти. Он предоставляет методы для выполнения операций(обычные CRUD действия с данными)
public class InMemoryStudentRepository {
    private List<StudentModel> students = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1); // Генерация уникального ID

    public StudentModel addStudent(StudentModel student) {
        student.setId(idCounter.getAndIncrement()); // Установка уникального ID
        students.add(student);
        return student;
    }

    public StudentModel updateStudent(StudentModel student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student);
                return student;
            }
        }
        return null; // Студент не найден
    }

    public void deleteStudent(int id) {
        students.removeIf(student -> student.getId() == id);
    }

    public List<StudentModel> findAllStudents() {
        return new ArrayList<>(students);
    }

    public StudentModel findStudentById(int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
