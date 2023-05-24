package study.toy.everythingshop.entity.h2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/**
 * fileName : UserMEntity
 * author   : pilming
 * date     : 2023-03-08
 */
@Data
@Builder //상속받는 클래스가 없으므로 SuperBuilder가 아닌 builder사용
@NoArgsConstructor
@AllArgsConstructor
public class UserMEntity {
    private Long userNum;
    private String userId;
    private String userPw;

    @NotBlank
    private String userNm;
    private String userRoleCd;
    private String registerDt;
    private String changeDt;
}
