package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {  //SpringUI에 있는 model 에 데이터를 실어서 view에 넘긴다
        model.addAttribute("data", "hello!");
        return "hello"; //return == 화면 이름, 자동으로 .html이 붙음
    }
}
