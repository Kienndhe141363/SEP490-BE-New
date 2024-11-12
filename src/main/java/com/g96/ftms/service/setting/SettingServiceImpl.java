package com.g96.ftms.service.setting;
import com.g96.ftms.dto.common.PagedResponse;
import com.g96.ftms.dto.request.SettingRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.dto.response.SettingResponse;
import com.g96.ftms.entity.Generation;
import com.g96.ftms.entity.Room;
import com.g96.ftms.entity.Settings;
import com.g96.ftms.exception.AppException;
import com.g96.ftms.exception.ErrorCode;
import com.g96.ftms.repository.GenerationRepository;
import com.g96.ftms.repository.RoomRepository;
import com.g96.ftms.repository.SettingsRepository;
import com.g96.ftms.util.constants.SettingGroupEnum;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {
    private final SettingsRepository settingsRepository;
    private final ModelMapper mapper;
    private final GenerationRepository generationRepository;
    private final RoomRepository roomRepository;

    @Override
    public ApiResponse<PagedResponse<SettingResponse.SettingInfoDTO>> search(SettingRequest.SettingPagingRequest model) {
        // Tìm kiếm theo keyword và status
        Page<Settings> pages = settingsRepository.searchAndFilter(model.getKeyword(), model.getStatus(), model.getPageable());

        List<SettingResponse.SettingInfoDTO> settingInfoList = new ArrayList<>();

        // Duyệt qua các settings và lấy dữ liệu từ Room và Generation
        for (Settings setting : pages) {
            SettingResponse.SettingInfoDTO settingInfoDTO = convertSettingToInfoDto(setting);
            settingInfoList.add(settingInfoDTO);
        }
        PagedResponse<SettingResponse.SettingInfoDTO> response = new PagedResponse<>(settingInfoList, pages.getNumber(), pages.getSize(), pages.getTotalElements(), pages.getTotalPages(), pages.isLast());
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), response);
    }

    @Override
    @Transactional
    public ApiResponse<Settings> createSetting(SettingRequest.SettingAddRequest model) {
        Settings setting = mapper.map(model, Settings.class);
        Settings byDescription = settingsRepository.findByDescription(model.getDescription());
        //create setting generation with group required match type
        if (model.getSettingGroup() == SettingGroupEnum.GENERATION) {
            Generation generation = generationRepository.findByGenerationName(model.getSettingName()).orElseThrow(() 
                    -> new AppException(HttpStatus.BAD_REQUEST, ErrorCode.ROOM_NOT_FOUND));
            setting.setGeneration(generation);
            //check desc exist
            if(byDescription!=null&&byDescription.getGeneration()!=null){
                throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATE_SETTING);
            }
            settingsRepository.save(setting);
        }
        //create setting room
        if (model.getSettingGroup() == SettingGroupEnum.ROOM) {
            Room room = roomRepository.findByRoomName(model.getSettingName()).orElseThrow(()
                    -> new AppException(HttpStatus.BAD_REQUEST, ErrorCode.ROOM_NOT_FOUND));
            if(byDescription!=null&&byDescription.getRoom()!=null){
                throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATE_SETTING);
            }
            setting.setRoom(room);
            settingsRepository.save(setting);
        }
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), setting);
    }

    @Override
    public ApiResponse<Settings> updateSetting(SettingRequest.SettingEditRequest model) {
        Settings setting = settingsRepository.findById(model.getId()).orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, ErrorCode.SETTING_NOTFOUND));
        if (model.getStatus() != null) {
            setting.setStatus(model.getStatus());
        }
        setting.setDescription(model.getDescription());
        //reset group;
        setting.setRoom(null);
        if (setting.getGeneration() != null) {
            setting.setGeneration(null);
            settingsRepository.save(setting); //remove generation
            generationRepository.delete(setting.getGeneration()); //delete generation
        }
        settingsRepository.save(setting);

        //update group
        if (model.getSettingGroup() == SettingGroupEnum.GENERATION) {
            if (!generationRepository.existsByGenerationName(model.getSettingName())) {
                throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.GENERATION_NAME_SETTING_EXIST);
            }
            Generation generation = Generation.builder().generationName(model.getSettingName()
            ).build();
            generationRepository.save(generation);
            setting.setGeneration(generation);
            settingsRepository.save(setting);
        }
        //save setting room
        if (model.getSettingGroup() == SettingGroupEnum.ROOM) {
            Room room = roomRepository.findByRoomName(model.getSettingName()).orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, ErrorCode.ROOM_NOT_FOUND));
            setting.setRoom(room);
            settingsRepository.save(setting);
        }
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), setting);
    }

    @Override
    public ApiResponse<SettingResponse.SettingInfoDTO> getDetail(Long id) {
        Settings setting = settingsRepository.findById(id).orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, ErrorCode.SETTING_NOTFOUND));
        SettingResponse.SettingInfoDTO response = convertSettingToInfoDto(setting);
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), response);
    }
    public SettingResponse.SettingInfoDTO convertSettingToInfoDto(Settings setting) {
        // Duyệt qua các settings và lấy dữ liệu từ Room và Generation
        if (setting.getRoom() != null) {
            SettingResponse.SettingInfoDTO dto = new SettingResponse.SettingInfoDTO();
            dto.setId(setting.getSettingId());
            dto.setSettingName(setting.getRoom().getRoomName());
            dto.setSettingGroup(SettingGroupEnum.ROOM.name());
            dto.setDescription(setting.getDescription());
            dto.setStatus(setting.getStatus());
            return dto;
        }


        // Duyệt qua các Generation
        if (setting.getGeneration() != null) {
            SettingResponse.SettingInfoDTO dto = new SettingResponse.SettingInfoDTO();
            dto.setId(setting.getSettingId());
            dto.setSettingName(setting.getGeneration().getGenerationName());
            dto.setSettingGroup(SettingGroupEnum.GENERATION.name());
            dto.setDescription(setting.getDescription());
            dto.setStatus(setting.getStatus());
            return dto;
        }
        return null;
    }
}