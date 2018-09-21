package com.zuulDemo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@Component
public class RestController {

    private String image;

    @PostMapping("/api/image")
	public String imageRequest(@RequestBody String body) {
		return body;
	}

}