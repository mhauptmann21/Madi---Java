public class Loan {
    private double annualInterestRate;
    private int numberOfYears;
    private double loanAmount;

    public Loan(double annualInterestRate, int numberOfYears, double loanAmount) {
        this.annualInterestRate = annualInterestRate;
        this.numberOfYears = numberOfYears;
        this.loanAmount = loanAmount;
    }

    public double getMonthlyPayment() {
        double monthlyInterestRate = annualInterestRate / 1200;
        double monthlyPayment = loanAmount * monthlyInterestRate /
                (1 - Math.pow(1 + monthlyInterestRate, -numberOfYears * 12));
        return monthlyPayment;
    }

    public double getTotalPayment() {
        return getMonthlyPayment() * numberOfYears * 12;
    }

}
