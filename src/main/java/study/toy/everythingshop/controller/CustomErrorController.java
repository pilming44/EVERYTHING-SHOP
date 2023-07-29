package study.toy.everythingshop.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public String handleError() {
        // 에러 페이지에 보여줄 내용을 구성하거나 권한 부족 시 보여줄 메시지를 설정합니다.
        return "error";
    }


}
