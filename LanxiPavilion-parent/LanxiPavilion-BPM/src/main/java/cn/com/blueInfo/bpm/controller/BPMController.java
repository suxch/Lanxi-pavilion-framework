package cn.com.blueInfo.bpm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BPMController {

    @ResponseBody
    @RequestMapping("/testSuxch")
    public String testSuxch() {
        return "testSuxch";
    }

}
