package innowise.helpdesk.attachments.controller;

import innowise.helpdesk.attachments.dto.YandexDiskInfoDto;
import innowise.helpdesk.attachments.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/info")
    public YandexDiskInfoDto getYandexDiskInfo(String oauth) {

        return fileService.getYandexDiskInfo(oauth).getBody();
    }
}
