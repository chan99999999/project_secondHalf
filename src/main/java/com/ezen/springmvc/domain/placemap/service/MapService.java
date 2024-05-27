package com.ezen.springmvc.domain.placemap.service;
import com.ezen.springmvc.domain.placemap.dto.MapDTO;

public interface MapService {
    MapDTO processPlaceInfo(MapDTO mapDTO);
    void savePlaceInfo(MapDTO mapDTO);
    MapDTO getPlaceInfoById(String mapId);
    void updatePlaceInfo(MapDTO mapDTO);
    void deletePlaceInfo(String mapId);
    String processPlaceInfoToJson(MapDTO mapDTO);
}