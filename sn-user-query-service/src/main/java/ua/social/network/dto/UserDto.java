package ua.social.network.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.social.network.util.ListUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString(of = {"id", "name", "lastVisit", "avatar", "friends"})
public class UserDto {
    private String id;
    private String name;
    private String lastVisit;
    private String avatar;
    private List<UserDto> friends;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        UserDto that = (UserDto) object;

        return ListUtils.isEquals(getFriends(), that.getFriends(), UserDto::equals)
                && Objects.equals(name, that.name) && Objects.equals(lastVisit, that.lastVisit)
                && Objects.equals(avatar, that.avatar);

    }
}
