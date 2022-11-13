package hk.edu.polyu.comp.comp2021.assignment3.inheritance;

class A {

    public String name;

    public A(){
        this.name = "a";
    }

    public A(String name){
        this.name = name;
    }

    public String sendMsg(String msg){
        return this.name+msg;
    }
}

class B extends A {

    private String name;

    public B(){
        this.name = "b";
    }

    public B(String name){
        //Task3: Complete the constructor with one or more statements;
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String sendMsg(String msg) {
        // Task 4: Complete the return statement;

        return this.getName() + this.getClass().getSimpleName() + msg;
    }
}

class C extends B {

    public C(String name){
        // Task 5: Complete the constructor with one or more statements;
        super(name);
    }

    public String sendMsg(String msg) {
        // Task 6: Complete the return statement;
        B b = new B(getName());
        return b.getName() + msg;
    }
}

class D extends B {
    String name;
    public D(String name){
        //Task 7: Complete the constructor with one or more statements;
        super(name + "D");
    }

    public String sendMsg(String msg) {
        // Task 8: Complete the return statement;
        B b = new B(getName());
        return this.getName().substring(0, getName().length() - 1) + this.getName().substring(0, getName().length() - 1) + "D" + msg;
    }
}
