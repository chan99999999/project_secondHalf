package com.ezen.springmvc.domain.placemap.mapper;

import com.ezen.springmvc.domain.placemap.dto.MapDTO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MapMapper {

    @Insert("INSERT INTO place_info (id, place_name, address_name, road_address_name, x, y) VALUES (#{mapId}, #{placeName}, #{addressName}, #{roadAddressName}, #{x}, #{y})")
    void insertPlaceInfo(MapDTO mapDTO);

    @Select("SELECT * FROM place_info WHERE id = #{mapId}")
    MapDTO getPlaceInfoById(String mapId);

    @Update("UPDATE place_info SET place_name = #{placeName}, address_name = #{addressName}, road_address_name = #{roadAddressName}, x = #{x}, y = #{y} WHERE id = #{mapId}")
    void updatePlaceInfo(MapDTO mapDTO);

    @Delete("DELETE FROM place_info WHERE id = #{mapId}")
    void deletePlaceInfo(String mapId);
}