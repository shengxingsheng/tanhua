package com.tanhua.server.controller;

import com.tanhua.model.dto.RecommendUserDto;
import com.tanhua.model.vo.PageResult;
import com.tanhua.model.vo.TodayBest;
import com.tanhua.server.service.TanhuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sxs
 * @create 2022-09-15 17:39
 */
@RestController
@RequestMapping("/tanhua")
public class TanhuaController {
    @Autowired
    private TanhuaService tanhuaService;
    @GetMapping("/todayBest")
    public ResponseEntity todayBest(){
        TodayBest todayBest =tanhuaService.todayBest();
        return ResponseEntity.ok(todayBest);
    }
    @GetMapping("/recommendation")
    public ResponseEntity<PageResult> recommendation(RecommendUserDto dto){
        PageResult pageResult =tanhuaService.recommendation(dto);
        return ResponseEntity.ok(pageResult);
    }
}
