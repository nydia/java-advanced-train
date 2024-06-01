package democode.transfer.version0;

import lombok.AllArgsConstructor;
import lombok.Data;

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