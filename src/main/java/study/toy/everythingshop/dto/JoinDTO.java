package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinDTO {
    private Long userNum;
    @Size(min = 3, message = "{Size.joinDTO.userId}")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "{Pattern.joinDTO.userId}")
    private String userId;
    @Size(min = 4, message = "{Size.joinDTO.userPw}")
    private String userPw;
    private String userPwConfirm;
    private String userNm;
    private Long userRoleCd;
    private String registerDt;
    private String changeDt;
}
