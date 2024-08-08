package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.models.Waste;
import com.africa.semiclon.capStoneProject.data.repository.WasteRepository;
import com.africa.semiclon.capStoneProject.dtos.request.UploadWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.response.UploadWasteResponse;
import com.africa.semiclon.capStoneProject.exception.WasteUploadFailedException;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import com.africa.semiclon.capStoneProject.services.interfaces.WasteService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.africa.semiclon.capStoneProject.data.models.Category.PLASTIC;

@Service
@Slf4j
@AllArgsConstructor
public class WasteServiceImpl implements WasteService {
    private final ModelMapper modelMapper;
    private final Cloudinary cloudinary;
    private final WasteRepository wasteRepository;
    private final UserService userService;
    @Override
    public UploadWasteResponse upload(UploadWasteRequest request) {
        User user= userService.getById(request.getUserId());

        try{

            Uploader uploader = cloudinary.uploader();
            Map<?,?> response = uploader.upload(request.getMediaFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            log.info("cloudinary upload response:: {}", response);
            String url = response.get("url").toString();
            Waste waste =  modelMapper.map(request, Waste.class);
            waste.setUrl(url);
            waste.setUploader(user);
            waste = wasteRepository.save(waste);
            return modelMapper.map(waste, UploadWasteResponse.class);
        }catch(Exception exception){
            throw new WasteUploadFailedException(exception.getMessage());

        }

    }
}
