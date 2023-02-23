package study.toy.everythingshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.toy.everythingshop.entity.SampleEntity;
import study.toy.everythingshop.service.SampleService;

import java.util.List;

@Controller
@RequestMapping("/sample")
@RequiredArgsConstructor
public class SampleController {
    private final SampleService sampleService;
    @GetMapping
    public String samples(Model model) {
        List<SampleEntity> samples = sampleService.findUsers();
        model.addAttribute("samples", samples);
        return "sample";
    }
}
