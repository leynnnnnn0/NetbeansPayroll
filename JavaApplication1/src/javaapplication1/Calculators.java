package javaapplication1;

public interface Calculators {
    static double grossPayCalculator(double salaryRate, int daysWorked, int hoursOvertime, int regularHolidays, int specialHolidays, int hoursLate, double advancedPay) {
        // Additions
        double regularPay = salaryRate * daysWorked;
        double overtimePay = hoursOvertime * ((salaryRate / 8) * 1.1);
        double regularHolidayPay = (salaryRate + (salaryRate * 0.3)) * regularHolidays;
        double specialHolidayPay = salaryRate * specialHolidays;
        // deduction
        double lateDeduction = (salaryRate / 8) * hoursLate;
        return regularPay + overtimePay + regularHolidayPay + specialHolidayPay - lateDeduction - advancedPay;
    }
    // Function to calculate the government deductions
    static double governmentDeductionsCalculator(double grossPay) {
        double TIN = 0;
        double SSS = grossPay * 0.1;
        double PHIL_HEALTH = 100;
        double PAG_IBIG = 100;
        return TIN + SSS + PHIL_HEALTH + PAG_IBIG;
    }
    static double netPayCalculator(double grossPay, double deductions) {
        return grossPay - deductions;
    }
}
