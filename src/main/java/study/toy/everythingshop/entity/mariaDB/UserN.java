package study.toy.everythingshop.entity.mariaDB;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserN {
    private Integer userNum;        //사용자번호
    private String userId;          //사용자ID
    private String userPw;          //비밀번호
    @NotBlank
    private String userNm;          //이름
    private String roleCd;          //역할[COM1001]
    private Integer holdingPoint;   //보유포인트
    private String accountStatusCd; //계정상태코드[COM1002]
    private String gradeCd;         //등급코드[COM1003]
    private String registerDt;      //등록일자
    private String changeDt;        //수정일자
}
