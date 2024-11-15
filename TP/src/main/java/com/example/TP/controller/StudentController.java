package com.example.TP.controller;

import com.example.TP.entity.Student;
import com.example.TP.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Sauvegarder un étudiant
     */
    @PostMapping("/save")
    public ResponseEntity<Student> save(@RequestBody Student student) {
        Student savedStudent = studentService.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED); // Retourne un ResponseEntity<Student>
    }

    /**
     * Supprimer un étudiant par ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        boolean deleted = studentService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Récupérer tous les étudiants
     */
    @GetMapping("/all")
    public ResponseEntity<List<Student>> findAll() {
        List<Student> students = studentService.findAll();
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build(); // Si aucun étudiant n'est trouvé
        }
        return ResponseEntity.ok(students); // Retourne la liste des étudiants
    }

    /**
     * Compter le nombre total d'étudiants
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countStudents() {
        long count = studentService.countStudents();
        return ResponseEntity.ok(count); // Retourne le nombre d'étudiants
    }

    /**
     * Récupérer le nombre d'étudiants par année
     */
    @GetMapping("/byYear")
    public ResponseEntity<Collection<?>> findByYear() {
        Collection<?> studentsByYear = studentService.findNbrStudentByYear();
        if (studentsByYear == null) {
            studentsByYear = new ArrayList<>();  // Assurez-vous qu'une collection vide est renvoyée si aucune donnée n'est trouvée
        }
        return new ResponseEntity<>(studentsByYear, HttpStatus.OK); // Retourne le nombre d'étudiants par année
    }

    /**
     * Méthode pour gérer les requêtes GET sur "/save"
     * (Évite les erreurs 405 Method Not Allowed)
     */
    @GetMapping("/save")
    public ResponseEntity<String> handleGetOnSave() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("Utilisez POST pour sauvegarder un étudiant.");
    }
}
