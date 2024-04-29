package com.poc.fetch.controller;

import com.poc.fetch.service.IFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchController {

    @Autowired
    private IFetchService service;

    @GetMapping("fetch")
    public ResponseEntity<?> fetch() {
        service.processFetch();
        return ResponseEntity.ok("Jalo :3");
    }


    @GetMapping("fetch-by-nombre")
    public ResponseEntity<?> fetchByNombre(@RequestParam(name = "nombre") String nombre) {
        service.processFetchByName(nombre);
        return ResponseEntity.ok("Jalo :3 el by nombre");
    }


    @GetMapping("fetch-f")
    public ResponseEntity<?> fetchF() {
        service.processFetchF();
        return ResponseEntity.ok("Jalo :3");
    }


    @GetMapping("fetch-by-nombre-f")
    public ResponseEntity<?> fetchByNombreF(@RequestParam(name = "nombre") String nombre) {
        service.processFetchByNamef(nombre);
        return ResponseEntity.ok("Jalo :3 el by nombre");
    }

    @GetMapping("memory-status")
    public ResponseEntity<?> memoryStatus() {
        service.consultMemoryStatus();
        return ResponseEntity.ok("Consulta bien :)");
    }
}
