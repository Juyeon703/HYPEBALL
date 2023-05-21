package com.project.hypeball.controller;

import com.project.hypeball.domain.Store;
import com.project.hypeball.service.PointService;
import com.project.hypeball.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("map")
public class MapController {

  private final StoreService storeService;
  private final PointService pointService;

  @GetMapping("/home")
  public String map(Model model) {

    System.out.println("MapController.map");

    model.addAttribute("pointList", pointService.findAll());
    return "map";
  }

  @ResponseBody
  @PostMapping("/home")
  public List<Store> marker() {

    System.out.println("MapController.marker");

    List<Store> list = storeService.findAll();
    return list;
  }

  public String add(Model model) {
    return "map";
  }
}