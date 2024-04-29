package com.poc.fetch.service;

import com.poc.fetch.model.entity.Estudiante;

import java.util.List;

public interface IFetchService {
    public void processFetch();
    public void processFetchByName(String name);
    public void processFetchF();
    public void processFetchByNamef(String name);
    public void consultMemoryStatus();
}
