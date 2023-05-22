public class ContaBancaria {
    private String titular;
    private double saldo;
    private boolean contaPoupanca;

    public ContaBancaria(String titular, double saldo, boolean contaPoupanca) {
        this.titular = titular;
        this.saldo = saldo;
        this.contaPoupanca = contaPoupanca;
    }

    public void depositar(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Não é permitido realizar depósitos com valores negativos.");
        }

        saldo += valor;
    }

    public void sacar(double valor) {
        if (contaPoupanca) {
            throw new UnsupportedOperationException("Não é possível realizar saques em conta poupança.");
        }

        if (valor > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar o saque.");
        }

        saldo -= valor;
    }

    public void transferir(ContaBancaria destino, double valor) {
        if (!titular.equals(destino.getTitular())) {
            throw new IllegalArgumentException("Não é permitido realizar transferências para contas com titularidades diferentes.");
        }

        if (valor < 0) {
            throw new IllegalArgumentException("Não é permitido realizar transferências com valores negativos.");
        }

        if (valor > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a transferência.");
        }

        saldo -= valor;
        destino.depositar(valor);
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean isContaPoupanca() {
        return contaPoupanca;
    }

    public static void main(String[] args) {
        ContaBancaria conta1 = new ContaBancaria("João", 100.0, false);
        ContaBancaria conta2 = new ContaBancaria("Maria", 200.0, false);
        ContaBancaria contaPoupanca = new ContaBancaria("José", 500.0, true);

        try {
            conta1.transferir(conta2, 150.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na transferência: " + e.getMessage());
        }

        try {
            conta1.transferir(contaPoupanca, 50.0);
        } catch (UnsupportedOperationException e) {
            System.out.println("Erro na transferência: " + e.getMessage());
        }

        System.out.println("Saldo da conta1: " + conta1.getSaldo());
        System.out.println("Saldo da conta2: " + conta2.getSaldo());
        System.out.println("Saldo da conta poupança: " + contaPoupanca.getSaldo());
    }
}
