package com.nydia.dddmessage.domain.aggregate.message.entity.valueobject;

import com.nydia.dddmessage.domain.shared.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * @author nydia
 * Created on 2021/7/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements ValueObject<User> {
    private int userId;
    private String nickname;
    private String photo;

    @Override
    public boolean sameValueAs(User other) {
        return other != null && new EqualsBuilder().
                append(this.userId, other.userId).
                append(this.nickname, other.nickname).
                append(this.photo, other.photo).
                isEquals();
    }
}