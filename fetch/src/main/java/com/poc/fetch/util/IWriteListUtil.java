package com.poc.fetch.util;

import com.poc.fetch.model.entity.ICsvModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IWriteListUtil {
    @Autowired
    public void escribirFile(List<? extends ICsvModel> records);
    public void escribirFile(LoadFetchUtil<? extends ICsvModel> util, String headers);
}
