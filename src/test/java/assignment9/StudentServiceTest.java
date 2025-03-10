package assignment9;

import com.zs.assignments.assignment9.dao.StudentDaoImpl;
import com.zs.assignments.assignment9.models.Student;
import com.zs.assignments.assignment9.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    private StudentService studentService;
    private StudentDaoImpl studentDao;

    @BeforeEach
    void setUp() {
        studentDao = mock(StudentDaoImpl.class);
        studentService = new StudentService(studentDao);
    }

    @Test
    void testCreateStudent_Success() {
        Student student = new Student(1, "Aditya", "Barnwal");

        when(studentDao.createStudent(student)).thenReturn(student);

        Student createdStudent = studentService.createStudent(student);

        assertNotNull(createdStudent);
        assertEquals(student.getId(), createdStudent.getId());
        assertEquals(student.getFirstName(), createdStudent.getFirstName());
        assertEquals(student.getLastName(), createdStudent.getLastName());

        verify(studentDao, times(1)).createStudent(student);
    }

    @Test
    void testCreateStudent_Failure() {
        Student student = new Student(2, "Krishna", "Barnwal");

        when(studentDao.createStudent(student)).thenReturn(null);

        Student createdStudent = studentService.createStudent(student);

        assertNull(createdStudent);
        verify(studentDao, times(1)).createStudent(student);
    }

    @Test
    void testGetStudentById_Success() {
        Student student = new Student(1, "Aditya", "Barnwal");

        when(studentDao.getStudentById(1)).thenReturn(student);

        Student retrievedStudent = studentService.getStudentById(1);

        assertNotNull(retrievedStudent);
        assertEquals(1, retrievedStudent.getId());
        assertEquals("Aditya", retrievedStudent.getFirstName());
        assertEquals("Barnwal", retrievedStudent.getLastName());

        verify(studentDao, times(1)).getStudentById(1);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentDao.getStudentById(707)).thenReturn(null);

        Student retrievedStudent = studentService.getStudentById(707);

        assertNull(retrievedStudent);
        verify(studentDao, times(1)).getStudentById(707);
    }

    @Test
    void testIfCreateStudentHasNullFirstName() {
        when(studentDao.createStudent(any(Student.class))).thenReturn(null);

        Student testStudent = new Student(1, null, "abc");
        Student studentModel = studentService.createStudent(testStudent);
        assertNull(studentModel);
        verify(studentDao, times(1)).createStudent(testStudent);
    }

    @Test
    void testIfCreateStudentHasNullLastName() {
        when(studentDao.createStudent(any(Student.class))).thenReturn(null);

        Student testStudent = new Student(1, "abc", null);
        Student studentModel = studentService.createStudent(testStudent);
        assertNull(studentModel);
        verify(studentDao, times(1)).createStudent(testStudent);
    }
}
