package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinDTO {
    private Long userNum;
    private String userId;
    private String userPw;
    private String userPwConfirm;
    private String userNm;
    private Long userRoleCd;
    private String registerDt;
    private String changeDt;
}
