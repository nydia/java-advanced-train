package democode.transfer.version0;

import lombok.Getter;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * @author nydia
 * Created on 2021/10/19
 */
@Getter
public class Account {

    private long id;

    private BigDecimal availableBalance;

    private AccountJournal journal;

    public Account(long id,BigDecimal availableBalance){
        this.id = id;
        this.availableBalance = availableBalance;
    }

    public void income(BigDecimal amount){
        Assert.isTrue(amount.compareTo(BigDecimal.ZERO) > 0 , "amount必须大于0");
        BigDecimal oldBalance = this.availableBalance;
        this.availableBalance = this.availableBalance.add(amount);
        this.journal = new AccountJournal("收入",oldBalance,this.availableBalance);
    }

    public void transferTo(Account toAccount , BigDecimal amount){
        Assert.isTrue(availableBalance.compareTo(amount) >= 0 , "availableBalance必须大于等于转账金额");
        BigDecimal oldBalance = this.availableBalance;
        this.availableBalance = this.availableBalance.subtract(amount);
        toAccount.income(amount);
        this.journal = new AccountJournal("支出",oldBalance,this.availableBalance);
    }
}
