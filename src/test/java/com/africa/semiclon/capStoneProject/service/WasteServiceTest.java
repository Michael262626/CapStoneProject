package com.africa.semiclon.capStoneProject.service;

import com.africa.semiclon.capStoneProject.dtos.request.UploadWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.response.UploadWasteResponse;
import com.africa.semiclon.capStoneProject.services.interfaces.WasteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.africa.semiclon.capStoneProject.utils.TestUtils.buildUploadWasteRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class WasteServiceTest {
    @Autowired
   private WasteService wasteService;

    @Test
    public void testToUploadWaste(){
        String fileLocation = "C:\\Users\\DELL\\Desktop\\Waste\\capStoneProject\\src\\main\\resources\\static\\download (2).jpeg";
        Path path = Paths.get(fileLocation);
        try (var inputStream = Files.newInputStream(path)) {
            UploadWasteRequest request = buildUploadWasteRequest(inputStream);
            UploadWasteResponse response = wasteService.upload(request);
            assertThat(response).isNotNull();
            assertThat(response.getUrl()).isNotNull();

        } catch (IOException exception) {
            assertThat(exception).isNull();
        }

    }
}
