package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDTO extends SuperClass {

    private String searchSelect;    //id 또는 이름
    private String searchText;      //검색창


}
