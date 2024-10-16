package democode.transfer.version0;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * 模拟一个转账场景
 * @author nydia
 * Created on 2021/10/19
 */
public class FundTransferService {

    public static void main(String[] args){
        Account account1 = new Account(1L,new BigDecimal(200));
        Account account2 = new Account(1L,new BigDecimal(100));
        account1.transferTo(account2,new BigDecimal(10));
        Assert.isTrue(account1.getAvailableBalance().compareTo(new BigDecimal(190)) == 0);
        Assert.isTrue(account2.getAvailableBalance().compareTo(new BigDecimal(110)) == 0);
        Objects.requireNonNull(account1.getJournal());
        Objects.requireNonNull(account2.getJournal());
    }
}