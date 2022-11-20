package comp2011.a2;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Yixin Cao (November 1, 2022)
 *
 * A binary search tree for Polyu students.
 *
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * Update on November 8: fix a bug in the main method.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 * Since we only store students, the class doesn't use generics.
 *
 */
public class PolyuTree_21093962d_JiangGuanlin { // Please change!
    /**
     * No modification to the class {@code Student} is allowed.
     * If you change anything in this class, your work will not be graded.
     */
    static class Student {
        String id;
        String name;
        public Student(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String toString() {return id + ", " + name;}
    }

    /**
     * No modification to the class {@code Node} is allowed.
     * If you change anything in this class, your work will not be graded.
     */
    private class Node {
        Student e;
        public Node lc, rc; // left child; right child

        @SuppressWarnings("unused")
        public Node(Student data) {
            this.e = data;
        }

        public String toString() {
            return e.toString();
        }
    }

    Node root;

    /**
     * Insert a new student into the tree.
     *
     * VERY IMPORTANT.
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
     * Running time: O(d). A function of d and n.
     */
    public void insert(Student s) {
        Node node = new Node(s);
        if (root == null) {
            root = node;
            return;
        }
        Node current = root;
        while (true) {
            if (s.name.compareTo(current.e.name) < 0) {
                if (current.lc == null) {
                    current.lc = node;
                    break;
                }
                current = current.lc;
            } else if (s.name.compareTo(current.e.name) >= 0){
                if (current.rc == null) {
                    current.rc = node;
                    break;
                }
                current = current.rc;
            }
        }
    }

    /**
     * Calculate the largest difference between the depths of the two subtrees of a node.
     *
     * VERY IMPORTANT.
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
     * Running time: O(d). A function of d and n.
     */
    public int maxDepthDiff() {
        int left = maxDepthDiff(root.lc);
        int right = maxDepthDiff(root.rc);
        if (left > right) {
            return left - 0;
        }
        return right - 0;
    }

    private int maxDepthDiff(Node root) {
        if (root == null) {
            return -1;
        }
        int left = maxDepthDiff(root.lc);
        int right = maxDepthDiff(root.rc);

        if (left > right) {
            return left + 1;
        } else {
            return right + 1;
        }
    }

    /**
     * Calculate the largest difference between the sizes of the two subtrees of a node.
     *
     * VERY IMPORTANT.
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
     * Running time: O(n * d). A function of d and n.
     */
    public int maxSizeDiff() {
        return maxSizeDiff(root);
    }
    private int countSize(Node root) {
        if (root == null) {
            return 0;
        }
        return 1 + countSize(root.lc) + countSize(root.rc);
    }
    private int maxSizeDiff(Node root) {
        if (root == null) {
            return -1;
        }
        return Math.abs(countSize(root.lc) - countSize(root.rc));
    }

    /**
     * Calculate the number of nodes that have only one child.
     *
     * VERY IMPORTANT.
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
     * Running time: O(nd). A function of d and n.
     */
    private int nodesWithOneChildCount = 0;
    public int nodesWithOneChild() {
        return nodesWithOneChild(root);
    }
    private int nodesWithOneChild(Node root) {
        if (root == null) {
            return nodesWithOneChildCount;
        }
        if (root.lc != null && root.rc != null) {
            nodesWithOneChild(root.lc);
            nodesWithOneChild(root.rc);
        }
        if (root.lc != null && root.rc == null) {
            nodesWithOneChildCount++;
            return nodesWithOneChild(root.lc);
        } else if (root.lc == null && root.rc != null) {
            nodesWithOneChildCount++;
            return nodesWithOneChild(root.rc);
        }
        return nodesWithOneChildCount;
    }

    /*
     * Find a student with the specified name.
     * You may return any of them if there are multiple students of this name.
     *
     * VERY IMPORTANT.
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
     * Running time: O(d). A function of d and n.
     */
    public Student searchFullname(String name) {
        return searchFullname(root, name);
    }
    private Student searchFullname(Node root, String name) {
        if (root == null) {
            return null;
        }
        if (root.e.name.equals(name)) {
            return root.e;
        } else if (root.e.name.compareTo(name) > 0) {
            return searchFullname(root.lc, name);
        } else {
            return searchFullname(root.rc, name);
        }
    }


    /*
     * Find all students with the specified surname.
     * Consider the first word as the surname.
     * Warning: Make sure "Liu Dennis" does not show when you search "Li."
     *
     * VERY IMPORTANT.
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
     * Running time: O(nd). A function of d and n.
     */
    public Student[] searchSurname(String surname) {
        return searchSurname(root, surname);
    }
    private int index = 0;
    private Student[] students = new Student[1];
    private Student[] searchSurname(Node root, String surname) {
        if (root == null) {
            return null;
        }
        if (root.e.name.split(" ")[0].equals(surname)) {
//            students[index] = root.e;
            setArray(root.e);
//            index++;
            searchSurname(root.lc, surname);
            searchSurname(root.rc, surname);
            return students;
        } else if (root.e.name.split(" ")[0].compareTo(surname) > 0) {
            root = root.lc;
        } else {
            root = root.rc;
        }
        return searchSurname(root, surname);
    }

    public void setArray(Student e) {
        if (students.length == index) {
            Student[] newStudents = new Student[students.length + 1];
            for (int i = 0; i < index; i++) {
                newStudents[i] = students[i];
            }
            students = newStudents;
        }
        students[index++] = e;
    }

    /*
     * Find all students who are taking a certain class.
     * The input is an array of student names.
     *
     * VERY IMPORTANT.
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
     * Running time: O(cd). A function of d, n, and c.
     */

    private int indexClass = 0;
    private Student[] studentsClass = new Student[1];
    public Student[] searchClass(String[] roster) {
        Student temp;
        for (int i = 0; i < roster.length; i++) {
            temp = searchFullname(roster[i]);
            if (temp != null) {
                setStudentClass(temp);
            }
        }
        return studentsClass;
    }

    public void setStudentClass(Student e) {
        if (studentsClass.length == indexClass) {
            Student[] newStudentsClass = new Student[studentsClass.length + 1];
            for (int i = 0; i < indexClass; i++) {
                newStudentsClass[i] = studentsClass[i];
            }
            studentsClass = newStudentsClass;
        }
        studentsClass[indexClass++] = e;
    }


    /**
     * You are free to make any change to this method.
     * You can even remove it if you want to test your code with other means.
     */
    public static void main(String[] args) {
        PolyuTree_21093962d_JiangGuanlin tree = new PolyuTree_21093962d_JiangGuanlin();
        /*
         * Here are 192 names you can use for testing.
         *
         * You should test your code with more names (>= 1 million). One way is to generate random names.
         * Tips: Given names can be random strings. You can assign a random surname from this list
         * https://surnam.es/hong-kong.
         */
        // String[] names = {"ea", "eb", "ee", "ed", "ec", "be", "cd", "ce", "cc", "bc", "bd", "ba"};
        String[] names = {"Chang Chi Fung", "Lee Cheuk Kwan", "Liu Tsz Ki", "Vallo David Jonathan Delos Reyes", "Jiang Han", "Park Taejoon", "Shin Hyuk", "Jung Junyoung", "Lam Wun Yiu", "Kwok Shan Shan", "Chui Cheuk Wai", "Lam Yik Chun", "Luo Yuehan", "Wang Hao", "Mansoor Haris", "Liang Wendi", "Meng Guanlin", "Wang Zhiyu", "Mak Ho Lun", "Liu Zixian", "Geng Qiyang", "Fong Chun Ming", "Cheung Chun Hei", "Lau Tsun Hang Ryan", "Cheung Cheuk Hang", "Liu Chi Hang", "Wong Yiu Nam", "Cheng Kok Yin", "Lam Wai Kit", "Liu Valerie", "Tam Chung Man", "Yan Tin Chun", "Lok Yin Chun", "Ng Ming Hei", "Lo Chun Hin", "Lam Pui Wa", "Lo Cho Hung", "Chu Tsz Fui", "Chow Ho Man", "Gao Ivan", "Ng Man Chau", "Iu Lam Ah", "Hung Wai Hin", "Tong Ka Yan", "Lo Ching Sau", "Lee Lee Ling", "Lam Ho Yin", "Sze Kin Ho", "Ng Siu Chi", "Wong Cheuk Laam", "Chan Yat Chun", "Lee Lap Shun", "Deng Chun Yung", "Fung Ki Ho", "Yeung Ting Kit", "Shiu Chi Yeung", "Kwan Yat Ming", "Chan Kin Kwan", "Leung Man Yi", "Yau Minki", "Hong Yuling", "Yung Wing Kiu", "Yuen Marco Siu Tung", "Lo Yung Sum", "Cheung Tsz Ho", "Chu Ka Hang", "Chan Man Yi", "Ng Yuet Kwan", "Lui Cheuk Lam Lily", "Tai Cheuk Hin", "Ong Chun Wa", "Yiu Pun Cham", "Cheng Ho Wing", "Wong Tsz Wai Desmond", "Lai Ho Sum", "Lee Siu Wai", "Lai Ming Hin", "Leung Hoi Ming", "To Ka Hei", "Tang Tsz Yeung", "Au Yeung Chun Yi", "Lau Ue Tin", "Yau Sin Yan", "Lam Ho Yan", "Tong Mei Chun", "Cheung Tsz Kwan", "Chiang Tin Hang", "Lai Kit Lun", "Cheung Sum Yin", "Wang Matthew Moyin", "Jiang Guanlin", "Edgar Christopher", "Liang Zhihong", "Bai Ruiyuan", "Chen Ru Bing", "Hu Wenqing", "Zhou Siyu", "Wang Yukai", "Lam Hei Yung", "Zhang Wanyu", "Wei Xianger", "Conte Chan Gabriel Alejandro", "Pratento Dylan Jefferson", "Lam Wan Yuet", "Chen Ziyang", "Jiang Zheng", "Xu Le", "He Boyan", "Liu Minghao", "Zhang Zhiyuan", "Chen Yuxuan", "Jin Cheng", "Liu Chenxi", "Qiu Siyi", "Han Wenyu", "Chan Cheuk Hei", "Ho Tsz Kan", "Du Haoxun", "Zheng Shouwen", "Ye Feng", "Yu Kaijing", "Lee Jer Tao", "Shen Ziqi", "Wang Yihe", "Liu Yanqi", "Zhang Wenxuan", "Huang Tianji", "Lu Zhoudao", "Zhang Tianyi", "Yuan Yunchen", "Liu Chengju", "Wei Siqi", "Liu Yuzhou", "Zhao Letao", "Huo Shuaining", "Li Kin Lung Anson", "Qin Qipeng", "Li Jiale", "He Rong", "Hiu Jason Kenneth", "Lam Ka Hang", "Li Tong", "Lau Choi Yu Elise", "Liu Dong", "Li Shuhang", "Zeng Yuejia", "Cai Zhenyu", "Lau Siu Hin", "Szeto Siu Chun", "Leung Cheuk Kit", "Cai Haoyu", "Ye Chenwei", "Huang Yidan", "Lee Kam Hung", "Wang Zhengyang", "Bao Yucheng", "Niyitegeka Berwa Aime Noel", "Lyateva Paulina Veselinova", "Zhang Boyu", "Chen Junru", "Fang Yuji", "Lin Qinfeng", "Tang Haichuan", "Hu Yuhang", "Zhou Taiqi", "Fang Anshu", "Wu Chao", "Zhang Haolin", "Ivanichev Mikhail", "Luo Yi", "Ieong Mei Leng", "Lee Wang Ho", "Jian Junxi", "Tam Tin", "Kjoelbro Niklas August", "Lee Hau Laam", "Pak Yi Ching", "Pang Kin Hei", "Xue Bingxin", "Lau Sin Yee", "Kwok Sze Ming", "Chan Lok Hin", "Chan Ho Yin Francis", "Chung Wai Ching", "Hu Hongjian", "Yiu Chi Chiu", "Tso Yuet Yan", "Chow Chun Wang", "Li Wun Wun", "Chen Junyu", "Kan Wai Yi", "Fong Chun Ho"};
        // String[] names = { "Tse Kay", "Ho Denise", "Yung Joey", "Tang Gloria", "Chan Eason", "Lau Andy", "Cheung Jacky"};
        SecureRandom random = new SecureRandom();
        int id = 22222222;
        for (String name : names) {
            id += random.nextInt(100);
            tree.insert(new Student(String.valueOf(id), name));
        }

        // Statistics
        System.out.println("The largest depth difference of a node is: " + tree.maxDepthDiff());
        System.out.println("The largest size difference of a node is: " + tree.maxSizeDiff());
        System.out.println(tree.nodesWithOneChild() + " nodes have a single child.");

        String[] comp2011 = {"Chang Chi Fung", "Lee Cheuk Kwan", "Liu Tsz Ki", "Vallo David Jonathan Delos Reyes", "Jiang Han", "Park Taejoon", "Shin Hyuk", "Jung Junyoung", "Lam Wun Yiu", "Kwok Shan Shan", "Chui Cheuk Wai", "Lam Yik Chun", "Luo Yuehan", "Wang Hao", "Mansoor Haris", "Liang Wendi", "Meng Guanlin", "Wang Zhiyu", "Mak Ho Lun", "Liu Zixian", "Geng Qiyang", "Fong Chun Ming", "Cheung Chun Hei", "Lau Tsun Hang Ryan", "Cheung Cheuk Hang", "Liu Chi Hang", "Wong Yiu Nam", "Cheng Kok Yin", "Lam Wai Kit", "Liu Valerie", "Tam Chung Man", "Yan Tin Chun", "Lok Yin Chun", "Ng Ming Hei", "Lo Chun Hin", "Lam Pui Wa", "Lo Cho Hung", "Chu Tsz Fui", "Chow Ho Man", "Gao Ivan", "Ng Man Chau", "Iu Lam Ah", "Hung Wai Hin", "Tong Ka Yan", "Lo Ching Sau", "Lee Lee Ling", "Lam Ho Yin", "Sze Kin Ho", "Ng Siu Chi", "Wong Cheuk Laam", "Chan Yat Chun", "Lee Lap Shun", "Deng Chun Yung", "Fung Ki Ho", "Yeung Ting Kit", "Shiu Chi Yeung", "Kwan Yat Ming", "Chan Kin Kwan", "Leung Man Yi", "Yau Minki", "Hong Yuling", "Yung Wing Kiu", "Yuen Marco Siu Tung", "Lo Yung Sum", "Cheung Tsz Ho", "Chu Ka Hang", "Chan Man Yi", "Ng Yuet Kwan", "Lui Cheuk Lam Lily", "Tai Cheuk Hin", "Ong Chun Wa", "Yiu Pun Cham", "Cheng Ho Wing", "Wong Tsz Wai Desmond", "Lai Ho Sum", "Lee Siu Wai", "Lai Ming Hin", "Leung Hoi Ming", "To Ka Hei", "Tang Tsz Yeung", "Au Yeung Chun Yi", "Lau Ue Tin", "Yau Sin Yan", "Lam Ho Yan", "Tong Mei Chun", "Cheung Tsz Kwan", "Chiang Tin Hang", "Lai Kit Lun", "Cheung Sum Yin", "Wang Matthew Moyin", "Jiang Guanlin", "Edgar Christopher", "Liang Zhihong", "Bai Ruiyuan", "Chen Ru Bing", "Hu Wenqing", "Zhou Siyu", "Wang Yukai", "Lam Hei Yung", "Zhang Wanyu", "Wei Xianger", "Conte Chan Gabriel Alejandro", "Pratento Dylan Jefferson", "Lam Wan Yuet", "Chen Ziyang", "Jiang Zheng", "Xu Le", "He Boyan", "Liu Minghao", "Zhang Zhiyuan", "Chen Yuxuan", "Jin Cheng", "Liu Chenxi", "Qiu Siyi", "Han Wenyu", "Chan Cheuk Hei", "Ho Tsz Kan", "Du Haoxun", "Zheng Shouwen", "Ye Feng", "Yu Kaijing", "Lee Jer Tao", "Shen Ziqi", "Wang Yihe", "Liu Yanqi", "Zhang Wenxuan", "Huang Tianji", "Lu Zhoudao", "Zhang Tianyi", "Yuan Yunchen", "Liu Chengju", "Wei Siqi", "Liu Yuzhou", "Zhao Letao", "Huo Shuaining", "Li Kin Lung Anson", "Qin Qipeng", "Li Jiale", "He Rong", "Hiu Jason Kenneth", "Lam Ka Hang", "Li Tong", "Lau Choi Yu Elise", "Liu Dong", "Li Shuhang", "Zeng Yuejia", "Cai Zhenyu", "Lau Siu Hin", "Szeto Siu Chun", "Leung Cheuk Kit", "Cai Haoyu", "Ye Chenwei", "Huang Yidan", "Lee Kam Hung", "Wang Zhengyang", "Bao Yucheng", "Niyitegeka Berwa Aime Noel", "Lyateva Paulina Veselinova", "Zhang Boyu", "Chen Junru", "Fang Yuji", "Lin Qinfeng", "Tang Haichuan", "Hu Yuhang", "Zhou Taiqi", "Fang Anshu", "Wu Chao", "Zhang Haolin", "Ivanichev Mikhail", "Luo Yi", "Ieong Mei Leng", "Lee Wang Ho", "Jian Junxi", "Tam Tin", "Kjoelbro Niklas August", "Lee Hau Laam", "Pak Yi Ching", "Pang Kin Hei", "Xue Bingxin", "Lau Sin Yee", "Kwok Sze Ming", "Chan Lok Hin", "Chan Ho Yin Francis", "Chung Wai Ching", "Hu Hongjian", "Yiu Chi Chiu", "Tso Yuet Yan", "Chow Chun Wang", "Li Wun Wun", "Chen Junyu", "Kan Wai Yi", "Fong Chun Ho"};
        String[] comp9999 = {"Tang Gloria", "Chan Eason"};
        Student[] ss = tree.searchClass(comp2011);
        System.out.println((comp2011.length - ss.length) + " Not Found\n" + ((ss != null)?Arrays.toString(ss):""));

        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a name for search, end in a '*' for surname search." + " q to quit");
        while (input.hasNext()) {
            String search = input.nextLine().trim();
            if (search.equals("q"))
                break;
            if (search.indexOf('*') > 0) {
                // call surname search
                search = search.substring(0, search.length()-1);
                Student[] list = tree.searchSurname(search);
                if (list == null)
                    System.out.println("Not Found");
                else
                    System.out.println(list.length + " students with surname \"" + search + "\" found:\n" + Arrays.toString(list));
            } else {
                // call full name search
                Student student = tree.searchFullname(search);
                if (student == null)
                    System.out.println("Not Found");
                else
                    System.out.println(student);
            }
        }
    }
}
