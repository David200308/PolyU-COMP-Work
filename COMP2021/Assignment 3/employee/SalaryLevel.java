package hk.edu.polyu.comp.comp2021.assignment3.employee;

/**
 * Levels of salary.
 */
enum SalaryLevel {
    ENTRY(1), JUNIOR(1.25), SENIOR(1.5), EXECUTIVE(2);

    // Add missing code here.
    private final double value;
    private SalaryLevel(double value) {
        this.value = value;
    }

    public double getScale() {
        return this.value;
    }

}