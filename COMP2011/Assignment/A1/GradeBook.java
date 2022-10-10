package comp2011.a1;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * 
 * @author Yixin Cao (September 14, 2022)
 *
 * A class for grade book of COMP2011.
 * 
 * There are two sessions, denoted as 1 and 2.
 * There are four possible grades, 'A', 'B', 'C', and 'D.' (The Grading System of PolyU, which can be found at https://www.polyu.edu.hk/en/gs/current-students/graduation-requirements/, is more complicated.)
 * 
 * It needs to support two views.  
 * First, the grades are displayed session by session. In each session, the students are listed in alphabetical order.
 * Second, the grades are displayed by grades, and for students with the same grade, students from session 1 are listed before session 2.
 * 
 * Read the {@code Student} class carefully before you start.
 * 
 * If your implementation is correct, elements of the same value should respect their original order,
 * e.g., for input {1.25, 0, 1.25, 2.5, 10, 2.5, 1.25, 5, 2.5}, the output should be  
 * [0.0 (1), 1.25 (0), 1.25 (2), 1.25 (6), 2.5 (3), 2.5 (5), 2.5 (8), 5.0 (7), 10.0 (4)].
 */
public class GradeBook_21093962d_JiangGuanlin{// Please change!
    /**
     * 
     * No modification to the class {@code Student} is allowed.
     * If you change anything in this class, your work will not be graded.
     *
     * You are NOT allowed to call the constructor of the class {@code Student}, except in {@code main}.
     */
    static class Student {
        String id;
        byte session; // 1 or 2
        char grade; // 'A', 'B', 'C', or 'D'
        
        public Student(String id, byte session) {
            this.id = id;
            this.session = session;
        }
        
        public void setGrade(char grade) {
            this.grade = grade;
        }
        public String toString () {
            return id + " (" +  session + "): " + grade;
        }
    }
    
    /**
     * Do not modify the signatures of this method.
     * For comparing two strings, you need the {String.compareTo()} method.
     * https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/String.html#compareTo(java.lang.String)
     * For this task, consider it as a single step (not true in general).
     * 
     * I've discussed this question with the following students:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * I've sought help from the following Internet resources and books:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * Running time: O( n^2 ). (n is the total number of students.)
     */ 
    public static void sessionView(Student[] a) {
        for (var i = 0; i < a.length; i++) {
            var min_index = i;
            for (var j = i; j < a.length; j++) {
                if (a[j].session < a[min_index].session) {
                    min_index = j;
                }
            }
            swap(a, min_index, i);
        }
        int point;
        for (point = 0; point < a.length; point++) {
            if (a[point].session == 2) {
                break;
            }
        }
        for (int i = 0; i < point - 1; i++) {
            var min_index = i;
            for (var j = i; j < point - 1; j++) {
                if (a[j].id.compareTo(a[min_index].id) < 0) {
                    min_index = j;
                }
            }
            swap(a, min_index, i);
        }

        for (int i = point; i < a.length; i++) {
            var min_index = i;
            for (var j = i; j < a.length; j++) {
                if (a[j].id.compareTo(a[min_index].id) < 0) {
                    min_index = j;
                }
            }
            swap(a, min_index, i);
        }
    }

    private static void swap(Student[] a, int index1, int index2) {
        var temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }

    /*
     * 
     * Arrange the grade book by grades, 'A' first and 'D' last.
     * For the same grade, list students from session 1 before session 2.
     *
     * My approach is to deal with sessions first, then grades.
     * The main benefit is that I can reuse the code from <code>sessionView</code>.
     * Alternatively, one can use eight counts for the number of students in each session for a different grade,
     * the number of students from session 1 with grade 'A,'
     * the number of students from session 2 with grade 'A,'
     * the number of students from session 1 with grade 'B,' etc.
     * Yet another (better) way is to do it in place.
     * Try to think about how to do it. You may get some hints from step 1.
     */
    /**
     * 1. Do not modify the signatures of this method.
     * 
     * I've discussed this question with the following students:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * I've sought help from the following Internet resources and books:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * Running time: O(   ). (n is the total number of students.)
     */ 
    public static void gradeView(Student[] a) {
        for (var i = 0; i < a.length; i++) {
            var minIndex = i;
            for (var j = i; j < a.length; j++) {
                if (a[j].grade < a[minIndex].grade) {
                    minIndex = j;
                }
            }
            swap(a, minIndex, i);
        }
    }
    
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        int n = 200;
        Student[] s = new Student[n];
        char[] g = {'A', 'B', 'C', 'D'};
        int id = 65536;
        for (int i = 0; i < n; i++) {
            id += random.nextInt(100);
            s[i] = new Student(String.valueOf(id), (byte)(id % 2 + 1));	
            s[i].setGrade(g[random.nextInt(4)]);
        }
        System.out.println(Arrays.toString(s));
        sessionView(s);
        System.out.println("\nThe session view: ");
        System.out.println(Arrays.toString(s));
        gradeView(s);
        System.out.println("\nAfter sorting by grade: ");
        System.out.println(Arrays.toString(s));
    }
}
