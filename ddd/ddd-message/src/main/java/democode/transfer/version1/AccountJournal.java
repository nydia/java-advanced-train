package democode.transfer.version1;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * @author zhengyin
 * Created on 2021/10/19
 */
@AllArgsConstructor
public class AccountJournal {
    private String type;
    private BigDecimal oldBalance;
    private BigDecimal newBalance;
}